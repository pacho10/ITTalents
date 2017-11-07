package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;

public class OrganizationDAO extends AbstractDBConnDAO implements IOrganizationDAO {

	private static final String INSERT_INTO_ORGANIZATIONS = "INSERT into organizations values (null,?);";
	private static final String SELECT_ORGANIZATION_BY_ID = "SELECT *  from organizations where id=?;";
	private static final String SELECT_ORGANIZATION_BY_NAME = "SELECT *  from organizations where name=?;";

	@Override
	public int addOrganization(Organization organization) throws EfficientProjectDAOException, DBException {
		if (organization == null) {
			throw new EfficientProjectDAOException("There is no organization to add!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(INSERT_INTO_ORGANIZATIONS,
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, organization.getName());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			throw new DBException("The organization cannot be add right now!Try again later!", e);
		}
		throw new EfficientProjectDAOException("Could not add!");
	}

	@Override
	public Organization getOrgById(int orgId) throws EfficientProjectDAOException, DBException, UnsupportedDataTypeException {
		if (orgId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(SELECT_ORGANIZATION_BY_ID);

			ps.setInt(1, orgId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Organization(rs.getInt(1), rs.getString(2));
			}

		} catch (SQLException e) {
			throw new DBException("No  organization for this id !Try again later!", e);
		}
		return null;

	}

	@Override
	public boolean isThereSuchOrganization(String name) throws EfficientProjectDAOException, DBException {
		if (name == null) {
			throw new EfficientProjectDAOException("There is no name input!");
		}
		PreparedStatement ps;
		try {
			ps = getCon().prepareStatement(SELECT_ORGANIZATION_BY_NAME);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new DBException("No  organization found!", e);
		}
	}

}
