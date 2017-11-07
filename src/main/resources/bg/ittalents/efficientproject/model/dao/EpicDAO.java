package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import org.apache.commons.lang3.StringEscapeUtils;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IEpicDAO;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;
import bg.ittalents.efficientproject.model.pojo.Epic;
import bg.ittalents.efficientproject.model.pojo.Project;;

public class EpicDAO extends AbstractDBConnDAO implements IEpicDAO {
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
	private static final String INSERT_EPIC_INTO_DB = "INSERT into epics values(null,?,?,?,?);";
	private static final String GET_ALL_EPICS_BY_PROJECT = "SELECT * from epics where project_id=?;";
	private static final String GET_EPIC_BY_ID = "SELECT * from epics where id=?;";

	@Override
	public int createEpic(Epic epic) throws EfficientProjectDAOException, DBException {
		if (epic == null) {
			throw new EfficientProjectDAOException("epic can not be null");
		}

		try {
			PreparedStatement ps = getCon().prepareStatement(INSERT_EPIC_INTO_DB,
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, epic.getName());
			ps.setInt(2, epic.getEstimate());
			ps.setString(3, epic.getDescription());
			ps.setInt(4, epic.getProject().getId());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DBException("epic can not be added!", e);
		}
		throw new EfficientProjectDAOException("epic can not be added!");
	}

	@Override
	public List<Epic> getAllEpicsByProject(int projectId)
			throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException {
		if (projectId < 0) {
			throw new EfficientProjectDAOException("invalid input!");
		}
		List<Epic> epics = new ArrayList<>();
		Project project = IProjectDAO.getDAO(SOURCE_DATABASE).getProjectByID(projectId);

		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALL_EPICS_BY_PROJECT);
			ps.setInt(1, projectId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				epics.add(new Epic(rs.getInt(1), StringEscapeUtils.escapeHtml4(rs.getString(2)), rs.getInt(3), rs.getString(4), project));
			}
		} catch (SQLException e) {
			throw new DBException("can not find epics for this project",e);
		}
		return epics;
	}
	
	@Override
	public Epic getEpicById(int epicId) throws UnsupportedDataTypeException, DBException, EfficientProjectDAOException {
		if (epicId < 0) {
			throw new EfficientProjectDAOException("invalid input!");
		}
		
		Epic epic = null;
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_EPIC_BY_ID);
			ps.setInt(1, epicId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Project project = IProjectDAO.getDAO(SOURCE_DATABASE).getProjectByID(rs.getInt(5));
				epic = new Epic(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), project);
			}
		} catch (SQLException e) {
			throw new DBException("can not find this epic",e);
		}
		return epic;
	}
}
