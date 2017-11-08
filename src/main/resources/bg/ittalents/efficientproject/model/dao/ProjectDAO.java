package bg.ittalents.efficientproject.model.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.UnsupportedDataTypeException;

import org.apache.commons.lang3.StringEscapeUtils;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.Task;
import bg.ittalents.efficientproject.model.pojo.Task.TaskState;
import bg.ittalents.efficientproject.model.pojo.User;

public class ProjectDAO extends AbstractDBConnDAO implements IProjectDAO {
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_PROJECT_INTO_DB = "INSERT into projects values(null,?,?,?,?);";
	private static final String GET_PROJECT_BY_ID = "SELECT * FROM projects WHERE id =?;";
	private static final String GET_ALLPROJECTS_FROM_0RGANIZATION = "SELECT * from projects WHERE organization_id=?";
	private static final String GET_ALLUSERS_FROM_PROJECT = "Select * from users_projects_history where project_id=?;";
	private static final String INSERT_INTO_USERS_PROJECTS_HISTORY = "insert into users_projects_history values (?,?);";
	private static final String SELECT_USER_PROJECT = "select * from  users_projects_history where project_id=? and users_id=?;";
	private static final String RETURN_PROJECT_DEADLINE = "select deadline from projects where id=";

	@Override
	public boolean isProjectFinished(int projectId) throws EfficientProjectDAOException, DBException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("illegal input!");
		}
		try {
			Statement st = getCon().createStatement();
			ResultSet rs = st.executeQuery(RETURN_PROJECT_DEADLINE + projectId);
			while (rs.next()) {
				LocalDate deadline = rs.getDate(1).toLocalDate();
				return deadline.isAfter(LocalDate.now()) ? false : true;
			}
			throw new EfficientProjectDAOException("no project found");

		} catch (SQLException e) {
			throw new DBException("Transaction is being rolled back", e);
		}
	}

	@Override
	public int addProject(Project project, int adminId) throws EfficientProjectDAOException, DBException {
		if (project == null) {
			throw new EfficientProjectDAOException("project can not be null!");
		}
		try {
			getCon().setAutoCommit(false);

			PreparedStatement ps = getCon().prepareStatement(INSERT_PROJECT_INTO_DB,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, project.getName());
			ps.setDate(2, Date.valueOf(project.getDeadline()));
			ps.setInt(3, project.getOrganization().getId());
			ps.setDate(4, Date.valueOf(project.getStartDate()));
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int projectId = rs.getInt(1);

				// add to projects_workers_history:
				PreparedStatement ps2 = getCon().prepareStatement(INSERT_INTO_USERS_PROJECTS_HISTORY);
				ps2.setInt(1, adminId);
				ps2.setInt(2, projectId);
				ps2.executeUpdate();
				return projectId;
			}
			getCon().rollback();
			throw new EfficientProjectDAOException("Could not add!");
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
	public Project getProjectByID(int projectId) throws DBException, EfficientProjectDAOException, UnsupportedDataTypeException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_PROJECT_BY_ID);
			ps.setInt(1, projectId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Organization org = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(4));
				return new Project(rs.getInt(1), rs.getString(2), rs.getDate(3).toLocalDate(), org,
						rs.getDate(5).toLocalDate());
			}
		} catch (SQLException e) {
			throw new DBException("Cannot check for user right now!Try again later", e);
		}
		return null;
	}

	@Override
	public List<Project> getAllProjectsFromOrganization(int organizationId)
			throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException {
		if (organizationId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		List<Project> projects = new ArrayList<>();

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALLPROJECTS_FROM_0RGANIZATION);
			ps.setInt(1, organizationId);

			ResultSet rs = ps.executeQuery();
			Organization organization = IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(organizationId);
			while (rs.next()) {
				// String name = URLEncoder.encode(rs.getString(2), "ISO-8859-1");
				String name = rs.getString(2);
				name = StringEscapeUtils.escapeHtml4(name);
				projects.add(new Project(rs.getInt(1), name, rs.getDate(3).toLocalDate(), organization,
						rs.getDate(5).toLocalDate()));
			}

			return projects;
		} catch (SQLException e) {
			throw new DBException("projects can not be selected!", e);
		}
	}

	@Override
	public List<User> getAllWorkersWorkingOnAProject(int projectId)
			throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
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
			return workers;
		} catch (SQLException e) {
			throw new DBException("projects can not be selected!", e);
		}
	}

	@Override
	public boolean isThisProjectOfThisUser(int projectId, int userId) throws DBException {
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

	public Map<TaskState, Integer> tasksNumberPerState(int projectId)
			throws UnsupportedDataTypeException, DBException, EfficientProjectDAOException {
		List<Task> allProjectTasks = ITaskDAO.getDAO(SOURCE_DATABASE).getAllTasksOfProject(projectId);
		Map<TaskState, Integer> tasksNumberPerState = new HashMap<>();
		int open = 0;
		int inprogree = 0;
		int resolved = 0;
		for (Task t : allProjectTasks) {
			String taskState = t.getStatus().toString();
			switch (taskState) {
			case "OPEN":
				open++;
				break;
			case "IN PROGRESS":
				inprogree++;
				break;
			case "RESOLVED":
				resolved++;
				break;
			}
		}
		tasksNumberPerState.put(TaskState.OPEN, open);
		tasksNumberPerState.put(TaskState.INPROGRESS, inprogree);
		tasksNumberPerState.put(TaskState.RESOLVED, resolved);
		return tasksNumberPerState;
	}
}
