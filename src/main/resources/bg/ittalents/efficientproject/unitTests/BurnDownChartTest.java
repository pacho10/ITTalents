package bg.ittalents.efficientproject.unitTests;

import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import org.junit.Test;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;

public class BurnDownChartTest {

	@Test
	public void testGetAMapOfTasksPerSprints() throws UnsupportedDataTypeException, EfficientProjectDAOException, DBException {
		int projectId=3;
		Map<String,Integer> values=ISprintDAO.getDAO(DAOStorageSourse.DATABASE).tasksPerSprints(projectId);
		System.out.println(values);
	}

	@Test
	public void testReturnAllTasksOfAProjectCount() throws UnsupportedDataTypeException, EfficientProjectDAOException, DBException {
		int projectId=3;
		int allTasksProject =ISprintDAO.getDAO(DAOStorageSourse.DATABASE).countTasksOfAProject(projectId);
		System.out.println(allTasksProject);
	}
	@Test
	public void testBurnDownChartData() throws UnsupportedDataTypeException, EfficientProjectDAOException, DBException {
		int projectId=3;
		Map<String,Integer> values=ISprintDAO.getDAO(DAOStorageSourse.DATABASE).burnDownChart(projectId);
		System.out.println(values);
	}
}
