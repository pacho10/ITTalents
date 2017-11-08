package bg.ittalents.efficientproject.unitTests;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import org.junit.Test;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.User;

public class AddUserTest {

	@Test
	public void testAddUserWorker() throws UnsupportedDataTypeException, EfficientProjectDAOException, DBException {
		User user=new User("brym","brym","aaa@aa.a","12345q",false);
		int userId=IUserDAO.getDAO(DAOStorageSourse.DATABASE).addUserWorker(user);
		System.out.println(userId);
		
		User userRetrievedById=IUserDAO.getDAO(DAOStorageSourse.DATABASE).getUserById(userId);
		assertTrue(IUserDAO.getDAO(DAOStorageSourse.DATABASE).getAllUnemployedWorkers().contains(userRetrievedById));
		
		assertTrue(IUserDAO.getDAO(DAOStorageSourse.DATABASE).removeUser(userId));
		assertTrue(IUserDAO.getDAO(DAOStorageSourse.DATABASE).removeWorkerFromUnemployedWorkers(user));
	}



}
