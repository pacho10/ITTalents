package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.interfaces.ISprintDAO;
import bg.ittalents.efficientproject.model.pojo.Sprint;

public class SprintDAO  extends AbstractDBConnDAO implements ISprintDAO{
	private static final String CREATE_SPRINT = "INSERT into sprints values(null,?,?,?,?);";
	private static final String GET_SPRINT_BY_ID = "SELECT * from sprints where id=?;";
	private static final String HAS_CURRENT_SPRINT = "SELECT * from sprints where project_id=? and curdate() <= date_add(start_date, interval duration day);";
	
	public int createSprint(Sprint sprint) throws DBException {
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
			e.printStackTrace();
			
			throw new DBException("sprint can not be created");
		}
	}
	
	public Sprint getSprintBId(int id) throws DBException {
		Sprint sprint = null;
		
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_SPRINT_BY_ID);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				sprint = new Sprint(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4),rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new DBException("sprint can not be found");
		}
		
		return sprint;
	}
	
	public Sprint getCurrentSprint(int projectId) throws DBException {
		try {
			PreparedStatement ps = getCon().prepareStatement(HAS_CURRENT_SPRINT);
			ps.setInt(1, projectId);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return new Sprint(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4), rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new DBException("can not find current sprint");
		}
		
		return null;
	}
}
