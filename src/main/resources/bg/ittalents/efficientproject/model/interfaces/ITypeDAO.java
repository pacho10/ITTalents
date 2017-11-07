package bg.ittalents.efficientproject.model.interfaces;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.TypeDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.pojo.Type;

public interface ITypeDAO {
	public static TypeDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new TypeDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	Type getTypeById(int TypeId) throws DBException, EfficientProjectDAOException;

	List<Type> getAllTypes() throws DBException;
}
