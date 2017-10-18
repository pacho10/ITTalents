package bg.ittalents.efficientproject.model.dao;

import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.interfaces.IEpicDAO;
import bg.ittalents.efficientproject.model.pojo.Epic;

public class EpicDAO  extends AbstractDBConnDAO implements IEpicDAO {
	private static final DAOStorageSourse SOURCE_DATABASE = DAOStorageSourse.DATABASE;

	public Epic getEpicById(int int1) {
		// TODO Auto-generated method stub
		return null;
	}
}
