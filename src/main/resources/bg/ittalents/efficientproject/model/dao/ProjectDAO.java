package bg.ittalents.efficientproject.model.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.User;

public class ProjectDAO extends AbstractDBConnDAO implements IProjectDAO {
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_PROJECT_INTO_DB = "INSERT into projects values(null,?,?,?);";
	private static final String GET_PROJECT_BY_ID = "SELECT * FROM projects WHERE id =?;";
	private static final String GET_ALLPROJECTS_FROM_0RGANIZATION = "SELECT * from projects WHERE organization_id=?";
	private static final String GET_ALLUSERS_FROM_PROJECT = "Select * from users_projects_history where project_id=?;"; 
	private static final String INSERT_INTO_USERS_PROJECTS_HISTORY = "insert into users_projects_history values (?,?);";
	private static final String SELECT_USER_PROJECT="select * from  users_projects_history where project_id=? and users_id=?;"; 

	@Override
	public int addProject(Project project, int adminId) throws EffPrjDAOException, DBException {
		if (project == null) {
			throw new EffPrjDAOException("project can not be null!");
		}
		try {
			getCon().setAutoCommit(false);
			
			PreparedStatement ps = getCon().prepareStatement(INSERT_PROJECT_INTO_DB,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, project.getName());
			ps.setDate(2, project.getDeadline());
			ps.setInt(3, project.getOrganization().getId());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int projectId= rs.getInt(1);
				
				// add to projects_workers_history:
				PreparedStatement ps2 = getCon().prepareStatement(INSERT_INTO_USERS_PROJECTS_HISTORY);
				ps2.setInt(1, adminId);
				ps2.setInt(2, projectId);
				ps2.executeUpdate();
				return projectId;
			}
			getCon().rollback();
			throw new EffPrjDAOException("Could not add!");
		} catch (SQLException e) {
			try {
				getCon().rollback();
			} catch (SQLException e1) {
				throw new DBException("Transaction is being rolled back", e1);
			}
			throw new DBException("project can not be added now!", e);
		} finally {
			try {
				getCon().setAutoCommit(true);
			} catch (SQLException e) {
				throw new DBException("Autocommit failed", e);
			}
		}
	}

	@Override
	public Project getProjectByID(int projectId) throws DBException, EffPrjDAOException, UnsupportedDataTypeException {
		if (projectId < 0) {
			throw new EffPrjDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_PROJECT_BY_ID);
			ps.setInt(1, projectId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Organization org = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(4));
				return new Project(rs.getInt(1), rs.getString(2), rs.getDate(3), org);
			}
		} catch (SQLException e) {
			throw new DBException("Cannot check for user right now!Try again later", e);
		}
		return null;
	}

	@Override
	public List<Project> getAllProjectsFromOrganization(int organizationId)
			throws DBException, UnsupportedDataTypeException, EffPrjDAOException {
		if (organizationId < 0) {
			throw new EffPrjDAOException("Invalid input!");
		}
		List<Project> projects = new ArrayList<>();

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALLPROJECTS_FROM_0RGANIZATION);
			ps.setInt(1, organizationId);

			ResultSet rs = ps.executeQuery();
			Organization organization = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(organizationId);
			while (rs.next()) {
				//String name = URLEncoder.encode(rs.getString(2), "ISO-8859-1");
				String name = URLDecoder.decode(rs.getString(2), "UTF-8");
				projects.add(new Project(rs.getInt(1), name, rs.getDate(3), organization));
			}

		} catch (SQLException | UnsupportedEncodingException e) {
			throw new DBException("projects can not be selected!", e);
		}
		return projects;
	}

	@Override
	public List<User> getAllWorkersWorkingOnAProject(int projectId)
			throws DBException, UnsupportedDataTypeException, EffPrjDAOException {
		if (projectId < 0) {
			throw new EffPrjDAOException("Invalid input!");
		}
		List<User> workers = new ArrayList<>();
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALLUSERS_FROM_PROJECT);
			ps.setInt(1, projectId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User worker = IUserDAO.getDAO(SOURCE_DATABASE).getUserById(rs.getInt(1));
				workers.add(worker);
			}
		} catch (SQLException e) {
			throw new DBException("projects can not be selected!",e);
		}
		return workers;
	}

	@Override
	public boolean isThisProjectOfThisUser(int projectId,int userId) throws DBException {
		try {
			PreparedStatement ps = getCon().prepareStatement(SELECT_USER_PROJECT);
			ps.setInt(1, projectId);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new DBException("check later", e);
		}
	}


}
