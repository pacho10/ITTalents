package bg.ittalents.efficientproject.model.interfaces;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.EpicDAO;

public interface IEpicDAO {
	public static EpicDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new EpicDAO();
		}
		throw new UnsupportedDataTypeException();
	}
}
