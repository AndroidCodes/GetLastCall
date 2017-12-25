import java.sql.Connection;

public class MainModule {

	public String[][] ExecuteF() {
		// TODO Auto-generated method stub
		// String RetVal;
		Connection conn1 = null;
		String[][] DBString;
		int EC;
		// String[] DBData;

		Functions con2 = new Functions();

		conn1 = con2.DoConnect(); // коннектимся
		System.out.println(conn1);
		EC = con2.CRMQuery_EntriesCount(conn1); // запрашиваем кол-во записей
		EC = EC + 1;
		System.out.println("Entries count: " + EC);

		con2.Ini();
		DBString = con2.CRMQuery(conn1, EC);
		con2.Test2();

		// for (int k = 1; k < 4; k++) {
		// System.out.println(DBString[k][1] + " " + DBString[k][2] + " " +
		// DBString[k][3] + " " + DBString[k][4] + " "
		// + DBString[k][5] + " " + DBString[k][6] + " " + DBString[k][7]);

		// }
		// выполняем запрос, заполняем
		// массив
		// DBData[1] = con2.CRMQuery(conn1);
		// DBString = "1";
		// con2.Disconnect(); // отсоединяемся
		// RetVal = "Database connection done";
		return DBString;

	}

}
