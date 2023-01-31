package com.mobileleader.edoc.db.typehandler;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.sql.TIMESTAMP;


@MappedTypes(value = Calendar.class)
public class CalendarTypeHandler implements TypeHandler<Calendar> {
	private static final Logger logger = LoggerFactory.getLogger(CalendarTypeHandler.class);
	
	//@Override
	public void setParameter(PreparedStatement ps, int i, Calendar parameter, JdbcType jdbcType) throws SQLException {
		
		final Calendar calendar = (Calendar)parameter;
		if(calendar == null) {
			ps.setNull(i, Types.TIMESTAMP);
		}
		else if (JdbcType.DATE.equals(jdbcType))
		{
			ps.setDate(i, new Date(calendar.getTimeInMillis()), calendar);
		}
		else if (JdbcType.TIME.equals(jdbcType))
		{
			ps.setTime(i, new Time(calendar.getTimeInMillis()), calendar);
		}
		else if (JdbcType.TIMESTAMP.equals(jdbcType))
		{
			ps.setTimestamp(i, new Timestamp(calendar.getTimeInMillis()), calendar);
		}
		else
		{
			// 무조건 jdbcType == null로 넘어 온다. 그래서 이렇게 처리함.
			ps.setTimestamp(i, new Timestamp(calendar.getTimeInMillis()), calendar);
			//throw new SQLException("The " + this.getClass() + " typehandler can handle only DATE, TIME, TIMESTAMP types not: " + jdbcType);
		}
	}
	
	protected Calendar createCalendar(final Object sqlDate) throws SQLException
	{
		//logger.info("sqlDate class name: " + sqlDate.getClass().getName());
		//TIMESTAMP a = new TIMESTAMP();
		
		if(sqlDate == null)
			return null;
			
		Calendar calendar;
		
		if ((sqlDate instanceof Long))
		{
			long lll = (Long)sqlDate;
			logger.info("sqlDate: " + lll);
			
			calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			calendar.setTime(new Date(lll));
			logger.info("------ Long");
		}
		else if ((sqlDate instanceof TIMESTAMP)) {  
			logger.info("sqlDate instanceof TIMESTAMP: ");
			
			calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			calendar.setTime(((TIMESTAMP) sqlDate).dateValue());
			logger.info("------ TIMESTAMP");
		}
		else if (!(sqlDate instanceof java.util.Date))
		{
			//logger.info("sqlDate instanceof java.util.Date");
			calendar = Calendar.getInstance();
		}
		else {
			logger.info("sqlDate: else");
			calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			calendar.setTime((java.util.Date) sqlDate);
		}

		return calendar;
	}
//	@Override
	public Calendar getResult(ResultSet rs, String columnName) throws SQLException {
		return this.createCalendar(rs.getObject(columnName));
	}
//	@Override
	public Calendar getResult(ResultSet rs, int columnIndex) throws SQLException {
		return this.createCalendar(rs.getObject(columnIndex));
	}
//	@Override
	public Calendar getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return this.createCalendar(cs.getObject(columnIndex));
	}

}
