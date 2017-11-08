package bg.ittalents.efficientproject.model.interfaces;

import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.SprintDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.pojo.Sprint;

public interface ISprintDAO {
	public static SprintDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new SprintDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	Sprint getCurrentSprint(int projectId) throws DBException, EfficientProjectDAOException;

	Sprint getSprintBId(int sprintId) throws DBException, EfficientProjectDAOException;

	int createSprint(Sprint sprint) throws DBException, EfficientProjectDAOException;

	Map<String, Integer> tasksPerSprints(int projectId) throws EfficientProjectDAOException, DBException;
}
