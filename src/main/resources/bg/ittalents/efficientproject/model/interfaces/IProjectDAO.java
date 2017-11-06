package bg.ittalents.efficientproject.model.interfaces;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.ProjectDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EfficientProjectDAOException;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.User;

public interface IProjectDAO {
		public static ProjectDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
			if (storage.equals(DAOStorageSourse.DATABASE)) {
				return new ProjectDAO();
			}
			throw new UnsupportedDataTypeException();
		}


		Project getProjectByID(int projectId) throws DBException, EfficientProjectDAOException, UnsupportedDataTypeException;

		List<Project> getAllProjectsFromOrganization(int organizationId)
				throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException;

		List<User> getAllWorkersWorkingOnAProject(int projectId)
				throws DBException, UnsupportedDataTypeException, EfficientProjectDAOException;

		int addProject(Project project, int adminId) throws EfficientProjectDAOException, DBException;


		boolean isThisProjectOfThisUser(int projectId, int userId) throws DBException;


		boolean isProjectFinished(int projectId) throws EfficientProjectDAOException, DBException;

		
}