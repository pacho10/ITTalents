package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IOrganizationDAO;
import bg.ittalents.efficientproject.model.interfaces.ITaskDAO;
import bg.ittalents.efficientproject.model.pojo.Epic;
import bg.ittalents.efficientproject.model.pojo.Organization;
import bg.ittalents.efficientproject.model.pojo.Sprint;
import bg.ittalents.efficientproject.model.pojo.Task;
import bg.ittalents.efficientproject.model.pojo.User;

public class TaskDAO extends AbstractDBConnDAO implements ITaskDAO {
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_USER_INTO_DB = "INSERT into tasks values(null,?,?,?,?,?,null,null,null,null,null,?,null,?);";
	private static final String SELECT_FROM_TASKS_BY_ID = "Select * from tasks where id=?;";
	
	@Override
	public int addTask(Task task) throws EffPrjDAOException, DBException {
		if (task == null) {
			throw new EffPrjDAOException("There is no user to add!");
		}
		try {
			PreparedStatement ps = getCon().prepareStatement(INSERT_USER_INTO_DB,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1,task.getType() );
			ps.setString(2,task.getSummary());
			ps.setString(3,task.getDescripion()  );
			ps.setFloat(4, task.getEstimate());
			ps.setTimestamp(5, task.getCreationDate());
			ps.setInt(6, task.getReporter().getId());
			ps.setInt(7, task.getEpic().getId());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("The task cannot be added right now!Try again later!", e);
		}
	}

	public Task getTaskById(int id) {

		try {

			PreparedStatement ps = getCon().prepareStatement(SELECT_FROM_TASKS_BY_ID);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
//			Organization organization =IOrganizationDAO.getDAO(SOURCE_DATABASE).getOrgById(rs.getInt(8));
			Sprint sprint;
			User reporter;
			User assignee;
			Epic epic;
			if (rs.next()) {
				return new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5),rs.getTimestamp(6),
						rs.getTimestamp(7), rs.getTimestamp(8), rs.getTimestamp(9), rs.getTimestamp(10),sprint,reporter,assignee,epic);

			}
			return null;//TODO throw exception!

		} catch (SQLException e) {
			e.printStackTrace();

			throw new DBException("Cannot check for user right now!Try again later", e);

		}
	}
	
	public boolean addTaskToSprint(Task task) {
		return false;
		
	}

	public boolean assignTask(int taskId, int userId) {
		return false;
		
	}

	public boolean finishTask(int taskId) {
		return false;
		
	}
	
	public boolean closeTask(int taskId) {
		return false;
		
	}

	public boolean updateTask(int taskId) {
		return false;
		
	}
}
