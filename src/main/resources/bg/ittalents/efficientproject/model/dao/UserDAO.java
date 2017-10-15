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

public class UserDAO extends AbstractDBConnDAO implements IUserDAO{

	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_USER_INTO_DB = "INSERT into users values(null,?,?,?,?,?,?);";
	private static final String SELECT_FROM_USERS_BY_EMAIL = "Select * from users where email=?;";
	private static final String SELECT_FROM_USERS_BY_ID = "Select * from users where id=?;";
	
	@Override
	public int addUser(User user) throws EffPrjDAOException, DBException {
		if (user == null) {
			throw new EffPrjDAOException("There is no user to add!");
			}
			try {
				PreparedStatement ps = getCon().prepareStatement(INSERT_USER_INTO_DB,
						PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getFullName());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getAvatarPath());
				ps.setBoolean(5, user.isAdmin());
				ps.setInt(6, user.getOrganization().getId());

				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				return rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DBException("The user cannot be added right now!Try again later!", e);
			}
		}
	
	
	public int updateUser(User user) {
		
	}
	
	@Override
	public User getUserById(int userID) throws UnsupportedDataTypeException, EffPrjDAOException, DBException {

		try {

			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_USERS_BY_ID);
			ps.setInt(1, userID);

			ResultSet rs = ps.executeQuery();
			Organization organization =IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(7));

			if (rs.next()) {
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getBoolean(6), organization);

			}
			return null;//TODO throw exception!

		} catch (SQLException e) {
			e.printStackTrace();

			throw new DBException("Cannot check for user right now!Try again later", e);

		}
	}
	
	@Override
	public User getUserByEmail(String email) throws UnsupportedDataTypeException, EffPrjDAOException, DBException {

		try {

			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_USERS_BY_EMAIL);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();
			Organization organization =IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(7));

			if (rs.next()) {
				return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getBoolean(6), organization);

			}
			return null;//TODO throw exception!

		} catch (SQLException e) {
			e.printStackTrace();

			throw new DBException("Cannot check for user right now!Try again later", e);

		}

	}
	
	
	public boolean isThereSuchAnUser(String email) {
		// TODO Auto-generated method stub
		return false;
	}

}
