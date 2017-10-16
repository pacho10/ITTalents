package bg.ittalents.efficientproject.model.interfaces;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.pojo.Task;

public interface ITaskDAO {

	int addTask(Task task) throws EffPrjDAOException, DBException;

}
