package com.mobileleader.image.db.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedJdbcTypes(value={JdbcType.INTEGER})
@MappedTypes(value=Boolean.class)
public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

	@Override
	  public void setNonNullParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType)
	      throws SQLException {
		int value = parameter == true ? 1 : 0;
	    ps.setInt(i, value);
	  }

	  @Override
	  public Boolean getNullableResult(ResultSet rs, String columnName)
	      throws SQLException {
		  boolean result = rs.getInt(columnName) > 0 ? true : false; 
		  return result;
	  }

	  @Override
	  public Boolean getNullableResult(ResultSet rs, int columnIndex)
	      throws SQLException {
		  boolean result = rs.getInt(columnIndex) > 0 ? true : false; 
		  return result;
	  }

	  @Override
	  public Boolean getNullableResult(CallableStatement cs, int columnIndex)
	      throws SQLException {
		  boolean result = cs.getInt(columnIndex) > 0 ? true : false; 
		  return result;
	  }

}
