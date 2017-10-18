package bg.ittalents.efficientproject.model.interfaces;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.ProjectDAO;

public interface IProjectDAO {
		public static ProjectDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
			if (storage.equals(DAOStorageSourse.DATABASE)) {
				return new ProjectDAO();
			}
			throw new UnsupportedDataTypeException();
		}
}