package bg.ittalents.efficientproject.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bg.ittalents.efficientproject.model.exception.DBException;
import bg.ittalents.efficientproject.model.interfaces.DAOStorageSourse;
import bg.ittalents.efficientproject.model.pojo.Type;

public class TypeDAO extends AbstractDBConnDAO {
	private static final String GET_ALL_TYPES = "SELECT * from types;";
	private static final String GET_TYPE_BY_ID = "SELECT * from types where id=?;";

	public Type getTypeById(int id) throws DBException {
		Type type = null;
		
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_TYPE_BY_ID);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				type = new Type(rs.getInt(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new DBException("can not find this type");
		}
		
		return type;
	}
	
	public List<Type> getAllTypes() throws DBException {
		List<Type> types = new ArrayList<>();
		try {
			PreparedStatement ps = getCon().prepareStatement(GET_ALL_TYPES);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				types.add(new Type(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new DBException("can not find Types");
		}
		
		return types;
	}
}
