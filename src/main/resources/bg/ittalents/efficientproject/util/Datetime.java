package bg.ittalents.efficientproject.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Datetime {

	public static String timestampAsString(Timestamp timestamp) {
		Date date = new Date();
		date.setTime(timestamp.getTime());
		return new SimpleDateFormat("yyyy.MM.dd HH:mm").format(date);
	}

}
