package bg.ittalents.efficientproject.model.dao;

import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IProjectDAO;

public class ProjectDAO  extends AbstractDBConnDAO implements IProjectDAO{
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;
}
