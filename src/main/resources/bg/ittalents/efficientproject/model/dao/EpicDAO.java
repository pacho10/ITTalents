package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IEpicDAO;
import bg.ittalents.efficientproject.model.pojo.Epic;;

public class EpicDAO  extends AbstractDBConnDAO implements IEpicDAO {
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_EPIC_INTO_DB = "INSERT into epics values(null,?,?,?,?)";
	
	public int createEpic(Epic epic) throws EffPrjDAOException, DBException {
		if (epic == null) {
			throw new EffPrjDAOException("epic can not be null");
		}
		
		try {
			PreparedStatement ps = getCon().prepareStatement(INSERT_EPIC_INTO_DB, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, epic.getName());
			ps.setInt(2, epic.getEstimate());
			ps.setString(3, epic.getDescription());
			ps.setInt(4, epic.getProject().getId());
			//Project project = IProjectDAO.getDAO(SOURCE_DATABASE).getProjectByID(epic.getProject().getId());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			
			return rs.getInt(1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DBException("epic can not be added!");
		}
	}

	public Epic getEpicById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
