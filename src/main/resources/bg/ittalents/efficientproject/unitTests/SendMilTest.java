package bg.ittalents.efficientproject.unitTests;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import org.junit.Test;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
import bg.ittalents.efficientproject.util.SendingMails;

public class SendMilTest {

	@Test
	public void testGetAMapOfTasksPerSprints() throws UnsupportedDataTypeException, EfficientProjectDAOException, DBException {
		assertTrue(SendingMails.sendEmail("qnince_to@abv.bg", "spam", "spam"));
		System.out.println("done");
	
	}

}
