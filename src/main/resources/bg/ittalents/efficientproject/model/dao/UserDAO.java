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

	@Override
	public int addUserAdmin(User user)
			throws EffPrjDAOException, DBException, UnsupportedDataTypeException, SQLException {
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
			getCon().commit();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				getCon().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DBException("The user cannot be added right now!Try again later!", e);
		} finally {
			getCon().setAutoCommit(true);
		}
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
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("The user cannot be added right now!Try again later!", e);
		}
	}

	@Override
	public User getUserById(int userID) throws UnsupportedDataTypeException, EffPrjDAOException, DBException {

		try {

			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_USERS_BY_ID);
			ps.setInt(1, userID);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Organization organization = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(8));

				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getBoolean(7), organization, rs.getBoolean(9));

			}
			return null;// TODO throw exception!

		} catch (SQLException e) {
			e.printStackTrace();

			throw new DBException("Cannot check for user right now!Try again later", e);

		}
	}

	@Override
	public User getUserByEmail(String email) throws UnsupportedDataTypeException, EffPrjDAOException, DBException {
		// System.out.println("dao param: " + email);
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
			return null;// TODO throw exception!

		} catch (SQLException e) {
			e.printStackTrace();

			throw new DBException("Cannot check for user right now!Try again later", e);

		}

	}

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
			e.printStackTrace();
			throw new DBException("Cannot check for user right now!Try again later", e);

		}

	}

	public boolean updateUsersDetails(User user) throws DBException {
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
			e.printStackTrace();
			throw new DBException("Cannot update users details right now!Try again later", e);
		}

	}

	public boolean employWorker(User user) throws DBException {
		PreparedStatement ps;
		try {
			ps = getCon().prepareStatement(UPDATE_WORKER_STATE_TO_EMPLOYED);
			ps.setInt(1, user.getId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Cannot update users details right now!Try again later", e);
		}
	}

	public boolean unemployWorker(User user) throws DBException {
		PreparedStatement ps;
		try {
			ps = getCon().prepareStatement(UPDATE_WORKER_STATE_TO_UNEMPLOYED);
			ps.setInt(1, user.getId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Cannot update users details right now!Try again later", e);
		}
	}

	public void addUserToProject(User user, int project_id)
			throws UnsupportedDataTypeException, EffPrjDAOException, DBException, SQLException {
		if (user == null && project_id > 0) {
			throw new EffPrjDAOException("Ivalid user or project_id");
		}
		try {
			// transaction!
			getCon().setAutoCommit(false);
			// add to projects_workers_history:
			PreparedStatement ps = getCon().prepareStatement(INSERT_WORKER_INTO_PROJECTS_WORKERS_HISTORY);
			ps.setInt(1, user.getId());
			ps.setInt(2, project_id);
			ps.executeUpdate();

			// change state to eployeed:
			employWorker(user);

			// removefrom the workers list:
			removeWorkerFromUnemployedWorkers(user);

			getCon().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				System.err.print("Transaction is being rolled back");
				getCon().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DBException("The user cannot be added right now!Try again later!", e);
		} finally {
			getCon().setAutoCommit(true);
		}

	}

	public int returnCurrentWorkersProject(User user) throws EffPrjDAOException, DBException {
		try {

			PreparedStatement ps = getCon().prepareStatement(RETURN_CURRENT_PROJECT_OF_WORKER);
			ps.setInt(1, user.getId());
			System.out.println(user.getId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
			throw new EffPrjDAOException("no found results");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Cannot check for user right now!Try again later", e);
		}
	}

	// TODO validation and synchronization!!!!!!!<--------> proper collections
	// choice
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
			e.printStackTrace();
		}
	}

	public Collection<User> getAllUnemployedWorkers() {
		return Collections.unmodifiableCollection(allUnemployedWorkers);
	}

	public boolean addWorkerToUnemployedWorkers(User worker) throws EffPrjDAOException {
		if (worker != null) {
			synchronized (allUnemployedWorkers) {
				allUnemployedWorkers.add(worker);
			}
			return true;
		}
		throw new EffPrjDAOException("Not valid user input!");
	}

	public boolean removeWorkerFromUnemployedWorkers(User worker) throws EffPrjDAOException {
		if (worker != null) {
			synchronized (allUnemployedWorkers) {
				allUnemployedWorkers.remove(worker);
			}
			return true;
		}
		throw new EffPrjDAOException("Not valid user input!");

	}
}
