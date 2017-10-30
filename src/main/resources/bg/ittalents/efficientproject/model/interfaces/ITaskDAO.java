package bg.ittalents.efficientproject.model.interfaces;

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



}
