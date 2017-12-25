import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateMajic {

	public static Date GetDate(String s) {

		// String s = "2008-10-30";
		Date date = new Date();
		String f1 = null;
		SimpleDateFormat f = new SimpleDateFormat();
		f.applyPattern("yyyy-MM-dd");
		Date docd = new Date();

		try {

			docd = f.parse(s);
			date = f.parse(s);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		f = new SimpleDateFormat("dd.MM.yyyy");
		f1 = f.format(docd);
		// System.out.println("BEER - " + f1);

		return date;

	}

	public static String GetDateInsideOut(Date s) {

		// String s = "2008-10-30";
		Date date = new Date();
		String f1 = null;
		SimpleDateFormat f = new SimpleDateFormat();
		f.applyPattern("yyyy-MM-dd");
		// Date docd = new Date();

		// try {

		// docd = f.parse(s);
		// date = f.parse(s);

		// } catch (ParseException e1) {
		// e1.printStackTrace();
		// }
		f = new SimpleDateFormat("yyyy-MM-dd");
		f1 = f.format(s);
		System.out.println("Date inside out = " + f1);

		return f1;

	}

}
