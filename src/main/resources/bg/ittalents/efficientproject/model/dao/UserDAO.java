package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			// encripting the password with sha 256:
			ps.setString(4, Encrypter.encrypt(user.getPassword()));
			ps.setString(5, user.getAvatarPath());
			ps.setBoolean(6, user.isAdmin());
			ps.setBoolean(7, user.isEmployed());

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("The user cannot be added right now!Try again later!", e);
		}
	}

	public int updateUser(int id) {
		return 0;

	}

	@Override
	public User getUserById(int userID) throws UnsupportedDataTypeException, EffPrjDAOException, DBException {

		try {

			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_USERS_BY_ID);
			ps.setInt(1, userID);

			ResultSet rs = ps.executeQuery();
			Organization organization = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(8));

			if (rs.next()) {
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
		System.out.println("dao param: " + email);
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

}
