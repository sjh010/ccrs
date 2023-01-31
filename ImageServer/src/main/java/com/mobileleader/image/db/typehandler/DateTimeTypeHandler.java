package com.mobileleader.image.db.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.joda.time.DateTime;

/**
 * org.joda.time.DateTime을 데이터 베이스 타입으로 변경해주는 타입 핸들러 클래스
 * 
 * <!-- DateTime -->
		<dependency>
		    <groupId>joda-time</groupId>
		    <artifactId>joda-time</artifactId>
		    <version>2.1</version>
		</dependency>
 * 
 */
@MappedTypes(DateTime.class)
public class DateTimeTypeHandler implements TypeHandler<DateTime> {

    //@Override
    public void setParameter(PreparedStatement ps, int i, DateTime parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter != null) {
            ps.setTimestamp(i, new Timestamp(parameter.getMillis()));
        } else {
            ps.setTimestamp(i, null);
        }
    }

    //@Override
    public DateTime getResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnName);
        if (ts != null)
            return new DateTime(ts.getTime());
        else
            return null;
    }

    //@Override
    public DateTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp ts = cs.getTimestamp(columnIndex);
        if (ts != null)
            return new DateTime(ts.getTime());
        else
            return null;
    }

    //@Override
    public DateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnIndex);
        if (ts != null)
            return new DateTime(ts.getTime());
        else
            return null;
    }

}
