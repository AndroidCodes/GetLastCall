import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class Functions {

	Statement st;
	int IncrementWhile;
	ResultSet rs;
	Connection conn = null;
	String S;
	public String[][] DBValues;
	public String[][] DBStatus;
	public String[][] DBFrom;

	String s1;
	DataClass FODS = new DataClass();

	public Connection DoConnect() {
		// TODO Auto-generated method stub

		try {

			String userName = "db_root";
			// String password = "iax5tahw3A";
			String password = "3a5n70Az";

			Properties connInfo = new Properties();
			connInfo.put("user", "db_root");
			connInfo.put("password", "3a5n70Az");

			// connInfo.put("charSet", "Cp1251");
			// connInfo.put("CODEPAGEID", "C9"); // Êîäèðîâêà Cp866

			connInfo.put("useUnicode", "true");
			connInfo.put("characterEncoding", "KOI8_R");

			// Connection conn = DriverManager.getConnection(dbURL, props);

			// String url = "jdbc:mysql://localhost/first";
			// String url = "jdbc:mysql://79.174.76.43:3306/test1";
			String url = "jdbc:mysql://79.174.76.43:3306/CRM";
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			// conn = DriverManager.getConnection(url, userName, password);
			conn = DriverManager.getConnection(url, connInfo);

			System.out.println("Database connection established");
		} catch (Exception e) {
			System.err.println("Cannot connect to database server");
			e.printStackTrace();
		}
		return conn;
	}

	public void Disconnect() {

		if (conn != null) {
			try {
				conn.close();
				System.out.println("Database connection terminated");
			} catch (Exception e) {
			}

		}

	}

	public void Ini() {

		FODS.Inicialize();

	}

	public void Test() {

		s1 = FODS.GetName(0);

	}

	public String[][] CRMQuery(Connection conn, int i) {

		int IDtoSTR;
		int STtoSTR;
		int FRtoSTR;

		String[][] DBValues = new String[i][10];
		IncrementWhile = 0;
		System.out.println("Increment before before: " + IncrementWhile);

		s1 = FODS.GetName(0);
		System.out.println("ÂÛÏÎËÍßÅÒÑß CRMQUERY");

		try {
			st = conn.createStatement();
			// rs = st.executeQuery("SELECT * FROM CRM.Clients ");
			rs = st.executeQuery("SELECT * FROM CRM.Clients WHERE CRM.User = 1");

			while (rs.next()) {

				System.out.println("ID: " + rs.getInt("ID_Clients") + "\t" + "Name: " + rs.getString("Name"));
				// S = rs.getString("Name");
				// System.out.println(S);

				FODS.AddID(IncrementWhile, rs.getInt("ID_Clients"));
				FODS.AddPhone((IncrementWhile - 0), rs.getString("Phone"));
				FODS.AddEmail(IncrementWhile, rs.getString("Email"));
				FODS.AddFrom(IncrementWhile, rs.getInt("ClientFrom"));
				FODS.Add((IncrementWhile - 0), rs.getString("Name"));
				FODS.AddBirthday(IncrementWhile, rs.getDate("Birthday").toString());
				FODS.AddStatus(IncrementWhile, rs.getInt("Status"));

				s1 = FODS.GetName(IncrementWhile);
				System.out.println("FODS NAME ->" + s1);

				IncrementWhile = IncrementWhile + 1;
				System.out.println("Increment before: " + IncrementWhile);

				IDtoSTR = rs.getInt("ID_Clients");
				FRtoSTR = rs.getInt("ClientFrom");
				STtoSTR = rs.getInt("Status");

				DBValues[IncrementWhile][1] = Integer.toString(IDtoSTR);
				DBValues[IncrementWhile][2] = rs.getString("Name");
				DBValues[IncrementWhile][3] = rs.getString("Phone");
				DBValues[IncrementWhile][4] = rs.getString("Email");
				DBValues[IncrementWhile][5] = Integer.toString(FRtoSTR);
				DBValues[IncrementWhile][6] = "12/05/1981";
				DBValues[IncrementWhile][7] = Integer.toString(STtoSTR);

				System.out.println("Cell: " + DBValues[IncrementWhile][1]);
				System.out.println("Increment: " + IncrementWhile);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DBValues[1][1] = S;
		System.out.println("Elements count: " + DBValues.length);
		return DBValues;

	}

	public int CRMQuery_EntriesCount(Connection conn) {

		IncrementWhile = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM CRM.Clients");
			while (rs.next()) {

				IncrementWhile = IncrementWhile + 1;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return IncrementWhile;

	}

	// public String [] CRMUserRequest(Connection conn) {
	public int CRMUserRequest(Connection conn, String Log, String Pass) {

		// String[] UsersinFuncData = null;
		// String[] UsersinFuncData = new String[10];
		IncrementWhile = 0;
		int ID;

		ID = 0;

		String L = Log;
		String P = Pass;
		String sqlString = "SELECT ID FROM CRM.Users WHERE Login='" + L + "' AND Password='" + P + "'";
		System.out.println(sqlString);
		try {
			st = conn.createStatement();
			// rs = st.executeQuery("SELECT Name, Login, Password FROM
			// CRM.Users");
			rs = st.executeQuery(sqlString);

			// SELECT ID FROM `Users` WHERE `Login`=2 AND `Password`=2

			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("ID") + "\t");
				ID = rs.getInt("ID");
				IncrementWhile = IncrementWhile + 1;

				// UsersinFuncData[1] = rs.getString("Name");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (IncrementWhile == 0) {

			ID = 0;

		}

		return ID;

	}

	public void LoginFileWrite(String L, String P) {

		try {
			PrintWriter pw = new PrintWriter(new File("C:\\Loginfo.dat"));
			pw.println(L);
			pw.println(P);
			pw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<String> LoginFileRead() {

		ArrayList<String> list = new ArrayList<String>();

		Scanner in;
		try {
			in = new Scanner(new File("C:\\Loginfo.dat"));
			while (in.hasNextLine())
				list.add(in.nextLine());
			// String[] array = list.toArray(new String[0]);
			in.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < list.size(); i++) {
			System.out.print((list.get(i)).toString());
		}

		return list;

	}

	public String[] CRMUserFullData(Connection conn, String Log, String Pass) {

		// String[] UserFullData = null;
		String[] UserFullData = new String[10];
		IncrementWhile = 0;
		// int ID;

		// ID = 0;

		String L = Log;
		String P = Pass;
		String sqlString = "SELECT * FROM CRM.Users WHERE Login='" + L + "' AND Password='" + P + "'";

		try {
			st = conn.createStatement();
			// rs = st.executeQuery("SELECT Name, Login, Password FROM
			// CRM.Users");
			rs = st.executeQuery(sqlString);

			while (rs.next()) {

				UserFullData[0] = rs.getString("Name");
				UserFullData[1] = rs.getString("Login");
				UserFullData[2] = rs.getString("Email");

				Format formatter = new SimpleDateFormat("dd-MM-yyyy");
				String UserBirthdayToString = formatter.format(rs.getDate("Birthday"));
				String UserRegDateToString = formatter.format(rs.getDate("RegDate"));
				String UserLastVisitDateToString = formatter.format(rs.getDate("LastVisitDate"));
				UserFullData[3] = UserBirthdayToString;
				UserFullData[4] = UserRegDateToString;
				UserFullData[5] = UserLastVisitDateToString;

				IncrementWhile = IncrementWhile + 1;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return UserFullData;

	}

	public int CRMQueryStatus_EntriesCount(Connection conn) {

		IncrementWhile = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM CRM.STATUS");
			while (rs.next()) {

				IncrementWhile = IncrementWhile + 1;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DBValues[1][1] = S;
		System.out.println("Elements count in STATUS table: " + IncrementWhile);
		return IncrementWhile;

	}

	public void Test2() {
		String sPh;
		String sEm;
		String sBD;
		int sID;
		int sFr;
		int sST;

		System.out.println("¹ ID  Name          Phone        Email     From     BDay     Status");
		IncrementWhile = 0;
		for (int k = 0; k < 3; k++) {

			sID = FODS.GetID(IncrementWhile);
			s1 = FODS.GetName(IncrementWhile);
			sPh = FODS.GetPhone(IncrementWhile);
			sEm = FODS.GetEmail(IncrementWhile);
			sBD = FODS.GetBithday(IncrementWhile);
			sBD = "12/05/81";
			sFr = FODS.GetFrom(IncrementWhile);
			sST = FODS.GetStatus(IncrementWhile);

			System.out.println("#" + IncrementWhile + " " + sID + " " + s1 + " " + sPh + " " + sEm + " " + sFr + " "
					+ sBD + " " + sST);

			IncrementWhile = IncrementWhile + 1;

		}

	}

	public DataClass DataTrans() {

		return FODS;

	}

	public void CRMQuery2(Connection conn, int i) {

		IncrementWhile = 0;
		System.out.println("=================Çàïóñòèëè CRMQuery2=================");
		s1 = FODS.GetName(0);

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM CRM.Clients WHERE User = 1");
			while (rs.next()) {

				FODS.AddID(IncrementWhile, rs.getInt("ID_Clients"));
				FODS.AddPhone((IncrementWhile - 0), rs.getString("Phone"));
				FODS.AddEmail(IncrementWhile, rs.getString("Email"));
				FODS.AddFrom(IncrementWhile, rs.getInt("ClientFrom"));
				FODS.Add((IncrementWhile - 0), rs.getString("Name"));
				FODS.AddBirthday(IncrementWhile, rs.getDate("Birthday").toString());

				FODS.AddStatus(IncrementWhile, rs.getInt("Status"));

				System.out.println("¹ ID  Name          Phone        Email     From     BDay     Status");
				System.out.println("#" + IncrementWhile + " " + rs.getInt("ID_Clients") + " " + rs.getString("Name")
						+ " " + rs.getString("Phone") + " " + rs.getString("Email"));

				s1 = FODS.GetName(IncrementWhile);
				IncrementWhile = IncrementWhile + 1;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String[][] CRMQueryStatus(Connection conn, int i) {

		int IDtoSTR;

		String[][] DBStatus = new String[i][3];
		// System.out.println("Index of Bounds: " + i);
		IncrementWhile = 0;
		// System.out.println("Increment before before: " + IncrementWhile);

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM CRM.STATUS");
			while (rs.next()) {

				// System.out.println("Increment before: " + IncrementWhile);

				IDtoSTR = rs.getInt("ID_STATUS");
				// System.out.println("IDtoSTR: " + IDtoSTR);

				// DBStatus[IncrementWhile][1] = "1";
				DBStatus[IncrementWhile][1] = Integer.toString(IDtoSTR);
				// System.out.println("Status Type is - " +
				// rs.getString("STATUS_TYPE"));
				DBStatus[IncrementWhile][2] = rs.getString("STATUS_TYPE");

				// System.out.println("Array content " + IncrementWhile + " is "
				// + DBStatus[IncrementWhile][1] + " - "
				// + DBStatus[IncrementWhile][2]);

				IncrementWhile = IncrementWhile + 1;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DBStatus;

	}

	public String[][] CRMQueryFrom(Connection conn, int i) {

		int IDtoSTR;

		String[][] DBFrom = new String[i][3];

		IncrementWhile = 0;

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM CRM.From_Type_Table");
			while (rs.next()) {

				IDtoSTR = rs.getInt("ID_FROM");

				DBFrom[IncrementWhile][1] = Integer.toString(IDtoSTR);

				DBFrom[IncrementWhile][2] = rs.getString("FROM_TYPE");

				IncrementWhile = IncrementWhile + 1;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DBValues[1][1] = S;
		// System.out.println("Elements count: " + DBValues.length);
		return DBFrom;

	}

	public int CRMQueryFRom_EntriesCount(Connection conn) {

		IncrementWhile = 0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM CRM.From_Type_Table");
			while (rs.next()) {

				IncrementWhile = IncrementWhile + 1;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DBValues[1][1] = S;
		// System.out.println("Elements count in FROM table: " +
		// IncrementWhile);
		return IncrementWhile;

	}

	public void CRM_AddEntry(Connection conn, String clientname, String clientphone, String clientmail,
			String clientBirthDay, String clientstatus, String clienfrom) {

		System.out.println("Çàøëè â ôóíêöèþ äîáàâëåíèÿ");
		System.out.println("Name = " + clientname);
		String adduser = "1";

		String query = "INSERT INTO CRM.Clients (Name, Phone, Email, ClientFrom, Birthday, Status, User) VALUES (?, ?, ?, ?, ?, ?, ?)";

		// INSERT INTO `CRM`.`Clients` (`ID_Clients`, `Name`, `Phone`, `Email`,
		// `ClientFrom`, `Birthday`, `Status`, `User`) VALUES (NULL, 'rrrr',
		// '33333', 'eeeeeee', '1', '2016-07-06', '2', '1');

		try {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, clientname);
			statement.setString(2, clientphone);
			statement.setString(3, clientmail);
			statement.setString(4, clienfrom);
			statement.setString(5, clientBirthDay);
			statement.setString(6, clientstatus);
			statement.setString(7, adduser);
			statement.executeUpdate();
			statement.close();

			System.out.println("Çàïèñü äîáàâëåíà!!!!!!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CRM_DeleteEntry(Connection conn, String ID) {

		// String query = "INSERT INTO CRM.Clients (Name, Phone, Email,
		// ClientFrom, Birthday, Status) VALUES (?, ?, ?, ?, ?, ?)";
		String query = "DELETE FROM CRM.Clients WHERE Clients.ID_Clients = ?";

		try {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, ID);

			statement.executeUpdate();
			statement.close();

			System.out.println("Çàïèñü ÓÄÀËÅÍÀ!!!!!!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CRM_EditeEntryName(Connection conn, String ClientName, String ID) {

		String query = "UPDATE CRM.Clients SET Name = ? WHERE Clients.ID_Clients = ?";

		try {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, ClientName);
			statement.setString(2, ID);

			statement.executeUpdate();
			statement.close();

			System.out.println("Çàïèñü ÎÁÍÎÂËÅÍÀ!!!!!!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CRM_EditEntryPhone(Connection conn, String ClientPhone, String ID) {

		String query = "UPDATE CRM.Clients SET Phone = ? WHERE Clients.ID_Clients = ?";

		try {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, ClientPhone);
			statement.setString(2, ID);

			statement.executeUpdate();
			statement.close();

			System.out.println("Çàïèñü ÎÁÍÎÂËÅÍÀ!!!!!!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CRM_EditEmailPhone(Connection conn, String ClientEmail, String ID) {

		String query = "UPDATE CRM.Clients SET Email = ? WHERE Clients.ID_Clients = ?";

		try {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, ClientEmail);
			statement.setString(2, ID);

			statement.executeUpdate();
			statement.close();

			System.out.println("Çàïèñü ÎÁÍÎÂËÅÍÀ!!!!!!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CRM_EditBirthday(Connection conn, String ClientBirthday, String ID) {

		String query = "UPDATE CRM.Clients SET Birthday = ? WHERE Clients.ID_Clients = ?";

		try {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, ClientBirthday);
			statement.setString(2, ID);

			statement.executeUpdate();
			statement.close();

			System.out.println("Çàïèñü ÎÁÍÎÂËÅÍÀ!!!!!!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CRM_EditStatus(Connection conn, String ClientStatus, String ID) {

		String query = "UPDATE CRM.Clients SET Status = ? WHERE Clients.ID_Clients = ?";

		try {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, ClientStatus);
			statement.setString(2, ID);

			statement.executeUpdate();
			statement.close();

			System.out.println("Çàïèñü ÎÁÍÎÂËÅÍÀ!!!!!!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CRM_EditFrom(Connection conn, String ClientFrom, String ID) {

		String query = "UPDATE CRM.Clients SET ClientFrom = ? WHERE Clients.ID_Clients = ?";

		try {

			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, ClientFrom);
			statement.setString(2, ID);

			statement.executeUpdate();
			statement.close();

			System.out.println("Çàïèñü ÎÁÍÎÂËÅÍÀ!!!!!!");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
