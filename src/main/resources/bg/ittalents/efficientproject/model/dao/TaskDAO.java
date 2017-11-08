package bg.ittalents.efficientproject.model.dao;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.activation.UnsupportedDataTypeException;

import org.apache.commons.lang3.StringEscapeUtils;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IEpicDAO;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.interfaces.ITypeDAO;
import bg.ittalents.efficientproject.model.interfaces.IUserDAO;
import bg.ittalents.efficientproject.model.pojo.Element;
import bg.ittalents.efficientproject.model.pojo.Epic;
import bg.ittalents.efficientproject.model.pojo.Sprint;
import bg.ittalents.efficientproject.model.pojo.Task;
import bg.ittalents.efficientproject.model.pojo.Type;
import bg.ittalents.efficientproject.model.pojo.User;

public class TaskDAO extends AbstractDBConnDAO implements ITaskDAO {
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_USER_INTO_DB = "INSERT into tasks values(null,?,?,?,?,?,null,null,null,null,null,?,null,?);";
	private static final String SELECT_FROM_TASKS_BY_ID = "Select * from tasks where id=?;";
	private static final String GET_ALL_TASKS_BY_USER = "SELECT	* from tasks where assignee=? and finished_date is null;";
	private static final String GET_ALL_TASKS_FROM_SPRINT = "SELECT * from tasks where sprint_id=?;";
	private static final String GET_ALL_TASKS_FROM_PROJECT = "select t.id from projects p join epics e on p.id=e.project_id join tasks t on e.id=t.epic_id where p.id=?";
	private static final String GET_ALL_NONNFINISHED_TASKS_FROM_PROJECT = "select t.id from projects p join epics e on p.id=e.project_id join tasks t on e.id=t.epic_id where p.id=? and t.finished_date is null and sprint_id is null";

	private static final String CHECK_IF_TASK_IS_NOT_TAKEN = "select id from tasks where assigned_date is  null and id=?;";
	private static final String WORKER_ASSIGNE_TASK = "UPDATE tasks SET assigned_date=?, assignee=? WHERE id=?;";
	private static final String FINISH_TASK = "UPDATE tasks SET finished_date=? WHERE id=?;";
	private static final String ADD_TASK_TO_SPRINT = "UPDATE tasks SET sprint_id=? WHERE id=?;";
	private static final String GET_ALL_TASKS_FROM_ALL_SPRINTS = "SELECT count(*), sprint_id FROM tasks t join sprints s on t.sprint_id=s.id where s.project_id=? group by sprint_id;";
	private static final String GET_ALL_TASKS_FROM_EPIC = "SELECT * from tasks where epic_id=?;";

	@Override
	public int addTask(Task task) throws EfficientProjectDAOException, DBException {
		if (task == null) {
			throw new EfficientProjectDAOException("There is no user to add!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(INSERT_USER_INTO_DB,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, task.getType().getId());
			ps.setString(2, task.getSummary());
			ps.setString(3, task.getDescription());
			ps.setFloat(4, task.getEstimate());
			ps.setTimestamp(5, task.getCreationDate());
			ps.setInt(6, task.getReporter().getId());
			ps.setInt(7, task.getEpic().getId());

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new DBException("The task cannot be added right now!Try again later!", e);
		}
	}

	@Override
	public Task getTaskById(int taskId) throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException {
		if (taskId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_TASKS_BY_ID);
			ps.setInt(1, taskId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Type type = ITypeDAO.getDAO(SOURCE_DATABASE).getTypeById(rs.getInt(2));
				Sprint sprint = ISprintDAO.getDAO(SOURCE_DATABASE).getSprintBId(rs.getInt(11));
				User reporter = IUserDAO.getDAO(SOURCE_DATABASE).getUserById(rs.getInt(12));
				User assignee = null;
				if (rs.getInt(13) > 0) {
					assignee = IUserDAO.getDAO(SOURCE_DATABASE).getUserById(rs.getInt(13));
				}
				Epic epic = IEpicDAO.getDAO(SOURCE_DATABASE).getEpicById(rs.getInt(14));
				String summary = StringEscapeUtils.escapeHtml4(rs.getString(3));
				try {
					summary = URLEncoder.encode(summary, "ISO-8859-1");
					summary = URLDecoder.decode(summary, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					throw new DBException("Cannot check for task right now!Try again later", e);
				}
				
				
				return new Task(rs.getInt(1), type, summary, 
						StringEscapeUtils.escapeHtml4(rs.getString(4)), rs.getFloat(5),
						rs.getTimestamp(6), rs.getTimestamp(7), rs.getTimestamp(8), rs.getTimestamp(9),
						rs.getTimestamp(10), sprint, reporter, assignee, epic);

			}
			throw new EfficientProjectDAOException("No task was found");
		} catch (SQLException e) {
			throw new DBException("Cannot check for task right now!Try again later", e);
		}
	}

	@Override
	public List<Task> getAllTasksByUser(int userId)
			throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException {
		if (userId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		List<Task> tasks = new ArrayList<>();

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALL_TASKS_BY_USER);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tasks.add(getTaskById(rs.getInt(1)));
			}
		} catch (SQLException e) {
			throw new DBException("can not find task for this user", e);
		}
		return tasks;
	}

	@Override
	public List<Task> getAllTasksFromSprint(int sprintId)
			throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException {
		if (sprintId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		List<Task> tasks = new ArrayList<>();

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALL_TASKS_FROM_SPRINT);
			ps.setInt(1, sprintId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tasks.add(getTaskById(rs.getInt(1)));
			}
		} catch (SQLException e) {
			throw new DBException("cannot find tasks for this sprint", e);
		}
		return tasks;
	}

	@Override
	public List<Task> getAllTasksOfProject(int projectId)
			throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		List<Task> tasks = new ArrayList<>();

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALL_TASKS_FROM_PROJECT);
			ps.setInt(1, projectId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tasks.add(getTaskById(rs.getInt(1)));
			}
		} catch (SQLException e) {
			throw new DBException("could not find the tasks", e);
		}
		return tasks;
	}

	@Override
	public List<Task> getProjectBackLog(int projectId)
			throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		List<Task> tasks = new ArrayList<>();

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALL_NONNFINISHED_TASKS_FROM_PROJECT);
			ps.setInt(1, projectId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tasks.add(getTaskById(rs.getInt(1)));
			}
		} catch (SQLException e) {
			throw new DBException("could not find the tasks", e);
		}
		return tasks;
	}

	@Override
	public boolean addTaskToSprint(int taskId, int sprintId) throws DBException, EfficientProjectDAOException {
		if (taskId < 0 || sprintId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(ADD_TASK_TO_SPRINT);

			ps.setInt(1, sprintId);
			ps.setInt(2, taskId);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DBException("task can not be addded to sprint", e);
		}
	}

	@Override
	public boolean checkIfTaskIsNotTaken(int taskId) throws EfficientProjectDAOException, DBException {
		if (taskId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(CHECK_IF_TASK_IS_NOT_TAKEN);
			ps.setInt(1, taskId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new DBException("task can not be checked", e);
		}
	}

	@Override
	public boolean assignTask(int taskId, int userId) throws DBException, EfficientProjectDAOException {
		if (taskId < 0 || userId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			if (checkIfTaskIsNotTaken(taskId)) {
				PreparedStatement ps = getCon().prepareStatement(WORKER_ASSIGNE_TASK);
				ps.setTimestamp(1, new Timestamp(new Date().getTime()));
				ps.setInt(2, userId);
				ps.setInt(3, taskId);
				ps.executeUpdate();
				return true;
			}
			return false;// TODO ???
		} catch (SQLException e) {
			throw new DBException("task can not be assigned", e);
		}
	}

	@Override
	public boolean finishTask(int taskId) throws DBException, EfficientProjectDAOException {
		if (taskId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(FINISH_TASK);
			ps.setTimestamp(1, new Timestamp(new Date().getTime()));
			ps.setInt(2, taskId);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			throw new DBException("task can not be finished", e);
		}
	}

	public List<Element> getAllTaskFromAllSprints(int projectId) throws DBException {
		List<Element> elements = new ArrayList<>();

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALL_TASKS_FROM_ALL_SPRINTS);
			ps.setInt(1, projectId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				try {
					Sprint sprint = ISprintDAO.getDAO(SOURCE_DATABASE).getSprintBId(rs.getInt(2));
					if (sprint != null) {
						String sName = sprint.getName();
						Element element = new Element(rs.getInt(1), sName);

						elements.add(element);
					}

				} catch (UnsupportedDataTypeException e) {
					e.printStackTrace();

					throw new DBException("can not find tasks for statistics", e);
				} catch (EfficientProjectDAOException e) {
					e.printStackTrace();

					throw new DBException("can not find tasks for statistics", e);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			throw new DBException("can not find tasks for statistics");
		}

		return elements;
	}

	@Override
	public boolean closeTask(int taskId) {
		return false;
		// TODO
	}

	@Override
	public boolean updateTask(int taskId) {
		return false;
		// TODO
	}

	@Override
	public List<Task> allEpicsTasks(int epicId)
			throws UnsupportedDataTypeException, DBException, EfficientProjectDAOException {
		if (epicId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		List<Task> tasks = new ArrayList<>();

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALL_TASKS_FROM_EPIC);
			ps.setInt(1, epicId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				tasks.add(getTaskById(rs.getInt(1)));
			}
		} catch (SQLException e) {
			throw new DBException("cannot find tasks for this epic", e);
		}
		// System.out.println(tasks);
		return tasks;
	}
}