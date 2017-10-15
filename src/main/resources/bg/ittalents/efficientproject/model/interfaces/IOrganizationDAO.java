package bg.ittalents.efficientproject.model.interfaces;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.OrganizationDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.pojo.Organization;

public interface IOrganizationDAO {
	public static OrganizationDAO getDAO(String storage) throws UnsupportedDataTypeException {
		if (storage.equalsIgnoreCase("db")) {
			return new OrganizationDAO();
		}
		throw new UnsupportedDataTypeException();
	}

	Organization getOrgById(int orgId) throws EffPrjDAOException, DBException, UnsupportedDataTypeException;

	int addOrganization(Organization organization) throws EffPrjDAOException, DBException;
}
