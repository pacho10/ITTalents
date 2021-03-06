package bg.ittalents.efficientproject.model.interfaces;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.OrganizationDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.pojo.Organization;

public interface IOrganizationDAO {
	public static OrganizationDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
		if (storage.equals(DAOStorageSourse.DATABASE)) {
			return new OrganizationDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	Organization getOrgById(int orgId) throws EfficientProjectDAOException, DBException, UnsupportedDataTypeException;

	int addOrganization(Organization organization) throws EfficientProjectDAOException, DBException;

	boolean isThereSuchOrganization(String name) throws EfficientProjectDAOException, DBException;
}
