package bg.ittalents.efficientproject.model.interfaces;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.UserDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.pojo.User;

public interface IUserDAO {
	
	public static UserDAO getDAO(String storage) throws UnsupportedDataTypeException {
		if (storage.equalsIgnoreCase("db")) {
			return new UserDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	User getUserById(int userID) throws UnsupportedDataTypeException, EffPrjDAOException, DBException;

	User getUserByEmail(String email) throws UnsupportedDataTypeException, EffPrjDAOException, DBException;

	int addUser(User user) throws EffPrjDAOException, DBException;
}
