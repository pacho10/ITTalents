package bg.ittalents.efficientproject.model.interfaces;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.TaskDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.pojo.Task;

public interface ITaskDAO {
	public static TaskDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new TaskDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	int addTask(Task task) throws EffPrjDAOException, DBException;

	Task getTaskById(int id) throws DBException, UnsupportedDataTypeException, EffPrjDAOException;

	boolean closeTask(int taskId);

	boolean updateTask(int taskId);

	boolean finishTask(int taskId) throws DBException, EffPrjDAOException;

	boolean assignTask(int taskId, int userId) throws DBException, EffPrjDAOException;

	List<Task> getProjectBackLog(int projectId) throws DBException, UnsupportedDataTypeException, EffPrjDAOException;

	List<Task> getAllTasksOfProject(int projectId) throws DBException, UnsupportedDataTypeException, EffPrjDAOException;

	List<Task> getAllTasksFromSprint(int sprintId) throws DBException, UnsupportedDataTypeException, EffPrjDAOException;

	List<Task> getAllTasksByUser(int userId) throws DBException, UnsupportedDataTypeException, EffPrjDAOException;

	boolean addTaskToSprint(int taskId, int sprintId) throws DBException, EffPrjDAOException;

	boolean checkIfTaskIsNotTaken(int taskId) throws EffPrjDAOException, DBException;

	List<Task> allEpicsTasks(int epicId) throws UnsupportedDataTypeException, DBException, EffPrjDAOException;

	



}
