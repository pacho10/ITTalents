package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.User;
import bg.ittalents.efficientproject.util.Encrypter;

public class UserDAO extends AbstractDBConnDAO implements IUserDAO {

	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_ADMIN_INTO_DB = "INSERT into users values(null,?,?,?,?,?,?,?,?);";
	private static final String INSERT_USER_INTO_DB = "INSERT into users values(null,?,?,?,?,?,?,null,?);";
	private static final String SELECT_FROM_USERS_BY_EMAIL = "Select * from users where email=?;";
	private static final String SELECT_FROM_USERS_BY_ID = "Select * from users where id=?;";
	private static final String UPDATE_USER_DETAILS = "update users set first_name =? , last_name=? ,email=? , password=? , avatar_path=? where id=?;";
	private static final String SELECT_ALL_UNEMPLOYED_WORKRS = "Select * from users where admin =0 and is_employed =0 ;";
	private static final String UPDATE_WORKER_STATE_TO_EMPLOYED = "UPDATE users SET `is_employed`='1' WHERE `id`=?;";
	private static final String UPDATE_WORKER_STATE_TO_UNEMPLOYED = "UPDATE users SET `is_employed`='0' WHERE `id`=?;";
	private static final String INSERT_WORKER_INTO_PROJECTS_WORKERS_HISTORY = "insert into users_projects_history values (?,?);";
	private static final String RETURN_CURRENT_PROJECT_OF_WORKER = "select h.project_id from users_projects_history h join projects p on p.id=h.project_id where h.users_id=? and p.deadline > CURDATE();";

	private static final Set<User> allUnemployedWorkers = new HashSet<>();

	// static initializing block to fill the collection when its loaded the first
	// time:
	static {
		try {
			Statement stm = getCon().createStatement();
			ResultSet rs = stm.executeQuery(SELECT_ALL_UNEMPLOYED_WORKRS);
			while (rs.next()) {
				User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getBoolean(7), null, rs.getBoolean(9));
				allUnemployedWorkers.add(user);
			}

		} catch (SQLException e) {
			try {
				throw new DBException("Could not initialize the collection", e);
			} catch (DBException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public int addUserAdmin(User user)
			throws EffPrjDAOException, DBException, UnsupportedDataTypeException{
		if (user == null) {
			throw new EffPrjDAOException("There is no user to add!");
		}
		try {
			getCon().setAutoCommit(false);
			PreparedStatement ps = getCon().prepareStatement(INSERT_ADMIN_INTO_DB,
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			// encripting the password with sha 256:
			ps.setString(4, Encrypter.encrypt(user.getPassword()));
			ps.setString(5, user.getAvatarPath());
			ps.setBoolean(6, user.isAdmin());

			// transaction!
			int orgId = IOrganizationDAO.getDAO(DAOStorageSourse.DATABASE).addOrganization(user.getOrganization());
			ps.setInt(7, orgId);
			ps.setBoolean(8, user.isEmployed());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				getCon().commit();
				return rs.getInt(1);
			}
			getCon().rollback();
			throw new EffPrjDAOException("Could not add!");
		} catch (SQLException e) {
			System.err.print("Transaction is being rolled back");
			try {
				getCon().rollback();
			} catch (SQLException e1) {
				throw new DBException("Transaction is being rolled back", e1);
				}
			throw new DBException("The user cannot be added right now!Try again later!", e);
		} finally {
			try {
				getCon().setAutoCommit(true);
			} catch (SQLException e) {
				throw new DBException("Autocommit failed", e);
			}}
	}

	@Override
	public int addUserWorker(User user) throws EffPrjDAOException, DBException, UnsupportedDataTypeException {
		if (user == null) {
			throw new EffPrjDAOException("There is no user to add!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(INSERT_USER_INTO_DB,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			// hashing the password with sha 256:
			ps.setString(4, Encrypter.encrypt(user.getPassword()));
			ps.setString(5, user.getAvatarPath());
			ps.setBoolean(6, user.isAdmin());
			ps.setBoolean(7, user.isEmployed());

			ps.executeUpdate();
			// adding to the enemployed workers list:
			addWorkerToUnemployedWorkers(user);// TODO transaction?
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DBException("The user cannot be added right now!Try again later!", e);
		}
		throw new EffPrjDAOException("Could not add!");
	}

	@Override
	public User getUserById(int userID) throws UnsupportedDataTypeException, EffPrjDAOException, DBException {
		if (userID < 0) {
			throw new EffPrjDAOException("Invalid input!");
		}
		try {

			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_USERS_BY_ID);
			ps.setInt(1, userID);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Organization organization = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(8));

				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getBoolean(7), organization, rs.getBoolean(9));

			}
			return null;

		} catch (SQLException e) {
			throw new DBException("Cannot check for user right now!Try again later", e);

		}
	}

	@Override
	public User getUserByEmail(String email) throws UnsupportedDataTypeException, EffPrjDAOException, DBException {
		if (email == null) {
			throw new EffPrjDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_USERS_BY_EMAIL);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Organization organization = null;
				if (rs.getBoolean(7)) {
					organization = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(8));
				}
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getBoolean(7), organization, rs.getBoolean(9));
			}
			return null;

		} catch (SQLException e) {
			throw new DBException("Cannot check for user right now!Try again later", e);

		}

	}

	@Override
	public boolean isThereSuchAnUser(String email) throws DBException, EffPrjDAOException {
		if (email == null) {
			throw new EffPrjDAOException("There is no email input!");
		}
		try {

			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_USERS_BY_EMAIL);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			throw new DBException("Cannot check for user right now!Try again later", e);
		}

	}

	@Override
	public boolean updateUsersDetails(User user) throws DBException, EffPrjDAOException {
		if (user == null) {
			throw new EffPrjDAOException("Invalid input!");
		}
		PreparedStatement ps;
		try {
			ps = getCon().prepareStatement(UPDATE_USER_DETAILS);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getAvatarPath());

			ps.setInt(6, user.getId());

			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DBException("Cannot update users details right now!Try again later", e);
		}

	}

	public boolean employWorker(int userId) throws DBException, EffPrjDAOException {
		if (userId < 0) {
			throw new EffPrjDAOException("Invalid input!");
		}
		PreparedStatement ps;
		try {
			ps = getCon().prepareStatement(UPDATE_WORKER_STATE_TO_EMPLOYED);
			ps.setInt(1, userId);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DBException("Cannot update users details right now!Try again later", e);
		}
	}

	@Override
	public boolean unemployWorker(User user) throws DBException, EffPrjDAOException {
		if (user == null) {
			throw new EffPrjDAOException("Invalid input!");
		}
		PreparedStatement ps;
		try {
			ps = getCon().prepareStatement(UPDATE_WORKER_STATE_TO_UNEMPLOYED);
			ps.setInt(1, user.getId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DBException("Cannot update users details right now!Try again later", e);
		}
	}

	@Override
	public boolean addUserToProject(int userId, int projectId)
			throws UnsupportedDataTypeException, EffPrjDAOException, DBException{
		if (userId < 0 || projectId < 0) {
			throw new EffPrjDAOException("Ivalid user or project_id");
		}
		try {
			getCon().setAutoCommit(false);
			
			// add to projects_workers_history:
			PreparedStatement ps = getCon().prepareStatement(INSERT_WORKER_INTO_PROJECTS_WORKERS_HISTORY);
			ps.setInt(1, userId);
			ps.setInt(2, projectId);
			ps.executeUpdate();

			// change state to employed:
			employWorker(userId);

			// remove from the workers list:
			if (removeWorkerFromUnemployedWorkers(getUserById(userId))) {
				getCon().commit();
				return true;
			} else {
				System.err.print("User already removed: Transaction is being rolled back");
				getCon().rollback();
				return false;
			}
		} catch (SQLException e) {
			System.err.print("Transaction is being rolled back");
			try {
				getCon().rollback();
			} catch (SQLException e1) {
				throw new DBException("Transaction is being rolled back", e1);
			}
			throw new DBException("The user cannot be added right now!Try again later!", e);
		} finally {
			try {
				getCon().setAutoCommit(true);
			} catch (SQLException e) {
				throw new DBException("Autocommit failed", e);
			}
		}

	}

	@Override
	public int returnCurrentWorkersProject(User user) throws EffPrjDAOException, DBException {
		if (user == null) {
			throw new EffPrjDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(RETURN_CURRENT_PROJECT_OF_WORKER);
			ps.setInt(1, user.getId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
			return -1;// TODO handle in the servlet!
		} catch (SQLException e) {
			throw new DBException("Try again later", e);
		}
	}

	@Override
	public Collection<User> getAllUnemployedWorkers() {
		return Collections.unmodifiableCollection(allUnemployedWorkers);
	}

	@Override
	public boolean addWorkerToUnemployedWorkers(User worker) throws EffPrjDAOException {
		if (worker != null) {
			synchronized (allUnemployedWorkers) {
				return allUnemployedWorkers.add(worker);
			}
		}
		throw new EffPrjDAOException("Not valid user input!");
	}

	@Override
	public boolean removeWorkerFromUnemployedWorkers(User worker) throws EffPrjDAOException {
		if (worker != null) {
			synchronized (allUnemployedWorkers) {
				return allUnemployedWorkers.remove(worker);
			}
		}
		throw new EffPrjDAOException("Not valid user input!");
	}
}
