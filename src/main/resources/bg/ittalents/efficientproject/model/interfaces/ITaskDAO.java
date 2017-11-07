package bg.ittalents.efficientproject.model.interfaces;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.TaskDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.pojo.Task;

public interface ITaskDAO {
	public static TaskDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new TaskDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	int addTask(Task task) throws EfficientProjectDAOException, DBException;

	Task getTaskById(int id) throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException;

	boolean closeTask(int taskId);

	boolean updateTask(int taskId);

	boolean finishTask(int taskId) throws DBException, EfficientProjectDAOException;

	boolean assignTask(int taskId, int userId) throws DBException, EfficientProjectDAOException;

	List<Task> getProjectBackLog(int projectId) throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException;

	List<Task> getAllTasksOfProject(int projectId) throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException;

	List<Task> getAllTasksFromSprint(int sprintId) throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException;

	List<Task> getAllTasksByUser(int userId) throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException;

	boolean addTaskToSprint(int taskId, int sprintId) throws DBException, EfficientProjectDAOException;

	boolean checkIfTaskIsNotTaken(int taskId) throws EfficientProjectDAOException, DBException;

	List<Task> allEpicsTasks(int epicId) throws UnsupportedDataTypeException, DBException, EfficientProjectDAOException;

	



}
