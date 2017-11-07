package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
import bg.ittalents.efficientproject.model.pojo.Sprint;

public class SprintDAO extends AbstractDBConnDAO implements ISprintDAO {
	private static final String CREATE_SPRINT = "INSERT into sprints values(null,?,?,?,?);";
	private static final String GET_SPRINT_BY_ID = "SELECT * from sprints where id=?;";
	private static final String HAS_CURRENT_SPRINT = "SELECT * from sprints where project_id=? and curdate() <= date_add(start_date, interval duration day);";

	private static final String TASKS_PER_PRINT_OF_PROJECT_1 = "select  p.id,s.name, count(*) from tasks t\r\n"
			+ "join epics e on e.id=t.epic_id\r\n" + "join projects p on e.project_id=p.id \r\n"
			+ "join sprints s on s.id=t.sprint_id\r\n" + "group by t.sprint_id\r\n" + "having p.id=";
	private static final String TASKS_PER_PRINT_OF_PROJECT_2 = " order by s.start_date;";
	private static final String COUNT_TASKS_FROM_PROJECT = "select count(*) from tasks t\r\n"
			+ "join epics e on e.id=t.epic_id\r\n" + "join projects p on e.project_id=p.id \r\n" + "group by p.id\r\n"
			+ "having p.id=";

	@Override
	public int createSprint(Sprint sprint) throws DBException, EfficientProjectDAOException {
		if (sprint == null) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(CREATE_SPRINT, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, sprint.getName());
			ps.setDate(2, sprint.getStartDate());
			ps.setInt(3, sprint.getDuration());
			ps.setInt(4, sprint.getProject_id());

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();

			return rs.getInt(1);
		} catch (SQLException e) {
			throw new DBException("sprint can not be created");
		}
	}

	@Override
	public Sprint getSprintBId(int sprintId) throws DBException, EfficientProjectDAOException {
		if (sprintId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_SPRINT_BY_ID);
			ps.setInt(1, sprintId);

			ResultSet rs = ps.executeQuery();

			Sprint sprint = null;
			if (rs.next()) {
				sprint = new Sprint(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4), rs.getInt(5));
			}
			return sprint;// TODO handle in servlet
		} catch (SQLException e) {
			throw new DBException("sprint cannot be found");
		}
	}

	@Override
	public Sprint getCurrentSprint(int projectId) throws DBException, EfficientProjectDAOException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(HAS_CURRENT_SPRINT);
			ps.setInt(1, projectId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return new Sprint(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4), rs.getInt(5));
			}
		} catch (SQLException e) {
			throw new DBException("cannot find current sprint");
		}
		return null;
	}

	@Override
	public Map<String, Integer> tasksPerSprints(int projectId) throws EfficientProjectDAOException, DBException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			Map<String, Integer> tasksPerSprints = new LinkedHashMap<>();
			Statement st = getCon().createStatement();
			ResultSet rs = st.executeQuery(TASKS_PER_PRINT_OF_PROJECT_1 + projectId + TASKS_PER_PRINT_OF_PROJECT_2);
			while (rs.next()) {
				tasksPerSprints.put(rs.getString(2), rs.getInt(3));
			}
			return tasksPerSprints;//TODO mozhe da vyrne prazen map---->?ili po dobre da hvyrli exceprion??????

		} catch (SQLException e) {
			throw new DBException("cannot find project", e);
		}
	}

	public int countTasksOfAProject(int projectId) throws EfficientProjectDAOException, DBException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("Invalid input!");
		}
		try {
			Statement st = getCon().createStatement();
			ResultSet rs = st.executeQuery(COUNT_TASKS_FROM_PROJECT + projectId);
			if (rs.next()) {
				return rs.getInt(1);
			}
			throw new EfficientProjectDAOException("No tasks found for this project");

		} catch (SQLException e) {
			throw new DBException("cannot find current sprint", e);
		}
	}

	public Map<String, Integer> burnDownChart(int projectId) throws EfficientProjectDAOException, DBException {
		Map<String, Integer> tasksPerSprints = tasksPerSprints(projectId);
		int allTasksProject=countTasksOfAProject(projectId);
		
	}

}
