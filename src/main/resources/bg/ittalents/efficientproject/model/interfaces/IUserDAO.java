package bg.ittalents.efficientproject.model.interfaces;

import java.sql.SQLException;
import java.util.Collection;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.UserDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.pojo.User;

public interface IUserDAO {

	public static UserDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new UserDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	User getUserById(int userID) throws UnsupportedDataTypeException, EffPrjDAOException, DBException;

	User getUserByEmail(String email) throws UnsupportedDataTypeException, EffPrjDAOException, DBException;

	int addUserAdmin(User user) throws EffPrjDAOException, DBException, UnsupportedDataTypeException, SQLException;

	int addUserWorker(User user) throws EffPrjDAOException, DBException, UnsupportedDataTypeException;

	boolean isThereSuchAnUser(String email) throws DBException, EffPrjDAOException;

	boolean updateUsersDetails(User user) throws DBException, EffPrjDAOException;

	boolean unemployWorker(User user) throws DBException, EffPrjDAOException;

	boolean addUserToProject(int userId, int projectId)
			throws UnsupportedDataTypeException, EffPrjDAOException, DBException, SQLException;

	int returnCurrentWorkersProject(User user) throws EffPrjDAOException, DBException;

	boolean removeWorkerFromUnemployedWorkers(User worker) throws EffPrjDAOException;

	boolean addWorkerToUnemployedWorkers(User worker) throws EffPrjDAOException;

	Collection<User> getAllUnemployedWorkers();

	
}
