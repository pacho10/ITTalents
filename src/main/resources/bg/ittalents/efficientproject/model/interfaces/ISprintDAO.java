package bg.ittalents.efficientproject.model.interfaces;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.SprintDAO;

public interface ISprintDAO {
	public static SprintDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new SprintDAO();
		}
		throw new UnsupportedDataTypeException();
	}
}
