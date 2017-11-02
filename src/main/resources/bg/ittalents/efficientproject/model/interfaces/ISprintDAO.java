package bg.ittalents.efficientproject.model.interfaces;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.SprintDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.pojo.Sprint;

public interface ISprintDAO {
	public static SprintDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new SprintDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	Sprint getCurrentSprint(int projectId) throws DBException, EffPrjDAOException;

	Sprint getSprintBId(int sprintId) throws DBException, EffPrjDAOException;

	int createSprint(Sprint sprint) throws DBException, EffPrjDAOException;
}
