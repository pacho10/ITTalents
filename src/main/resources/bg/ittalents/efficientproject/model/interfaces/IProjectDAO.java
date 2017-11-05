package bg.ittalents.efficientproject.model.interfaces;

import java.util.List;

import javax.activation.UnsupportedDataTypeException;

import bg.ittalents.efficientproject.model.dao.ProjectDAO;
import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.exception.EffPrjDAOException;
import bg.ittalents.efficientproject.model.pojo.Project;
import bg.ittalents.efficientproject.model.pojo.User;

public interface IProjectDAO {
		public static ProjectDAO getDAO(DAOStorageSourse storage) throws UnsupportedDataTypeException {
			if (storage.equals(DAOStorageSourse.DATABASE)) {
				return new ProjectDAO();
			}
			throw new UnsupportedDataTypeException();
		}


		Project getProjectByID(int projectId) throws DBException, EffPrjDAOException, UnsupportedDataTypeException;

		List<Project> getAllProjectsFromOrganization(int organizationId)
				throws DBException, UnsupportedDataTypeException, EffPrjDAOException;

		List<User> getAllWorkersWorkingOnAProject(int projectId)
				throws DBException, UnsupportedDataTypeException, EffPrjDAOException;

		int addProject(Project project, int adminId) throws EffPrjDAOException, DBException;


		boolean isThisProjectOfThisUser(int projectId, int userId) throws DBException;


		boolean isProjectFinished(int projectId) throws EffPrjDAOException, DBException;

		
}