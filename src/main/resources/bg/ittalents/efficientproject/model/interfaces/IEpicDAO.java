package bg.ittalents.efficientproject.model.interfaces;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.EpicDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.pojo.Epic;

public interface IEpicDAO {
	public static EpicDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new EpicDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	int createEpic(Epic epic) throws EfficientProjectDAOException, DBException;

	List<Epic> getAllEpicsByProject(int projectId) throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException;

	Epic getEpicById(int epicId) throws UnsupportedDataTypeException, DBException, EfficientProjectDAOException;
}
