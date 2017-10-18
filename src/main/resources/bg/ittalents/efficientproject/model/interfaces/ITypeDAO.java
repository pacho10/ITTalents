package bg.ittalents.efficientproject.model.interfaces;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.TypeDAO;

public interface ITypeDAO {
	public static TypeDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new TypeDAO();
		}
		throw new UnsupportedDataTypeException();
	}
}
