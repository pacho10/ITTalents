package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.Project;

public class ProjectDAO  extends AbstractDBConnDAO implements IProjectDAO{
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_PROJECT_INTO_DB = "INSERT into projects values(null,?,?,?);";
	private static final String GET_USER_BY_ID = "SELECT * FROM projects WHERE id =?;";
	
	public int addProject(Project project) throws EffPrjDAOException, DBException {
		if (project == null) {
			throw new EffPrjDAOException("project can not be null!");
		}
		
		try {
			PreparedStatement ps = super.getCon().prepareStatement(INSERT_PROJECT_INTO_DB, 
					PreparedStatement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, project.getName());
			ps.setDate(2, project.getDeadline());
			ps.setInt(3, project.getOrganization().getId());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			
			return rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DBException("project can not be added now!");
		}
	}
	
	public Project getProjectByID(int id) throws DBException, EffPrjDAOException, UnsupportedDataTypeException {
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_USER_BY_ID);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Organization org = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(3));
				return new Project(rs.getString(1), rs.getDate(2), org);
			}else {
				throw new EffPrjDAOException("this project not exist!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new DBException("Cannot check for user right now!Try again later", e);
		} 		
	}
}
