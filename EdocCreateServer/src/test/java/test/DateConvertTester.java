package test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateConvertTester {

	public static void main(String[] args) {
		
		String dateStr = "2019-09-09 10:10:55";
		
		//DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS");
//		Date d = formatter.parseDateTime(dateStr).toDate();
//		System.out.println(d);
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		String nowTime = formatter.format(new Date());
		
		System.out.println(nowTime);


	}

}
