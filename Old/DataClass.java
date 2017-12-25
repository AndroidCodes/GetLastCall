import java.util.ArrayList;

public class DataClass {

	public String[] s;
	String p1;
	static ArrayList<Integer> DBID = new ArrayList();
	static ArrayList<String> DBName = new ArrayList();
	static ArrayList<String> DBPhone = new ArrayList();
	static ArrayList<String> DBEmail = new ArrayList();
	static ArrayList<Integer> DBFROM = new ArrayList();
	static ArrayList<String> DBBirthday = new ArrayList();
	static ArrayList<Integer> DBStatus = new ArrayList();
	private Boolean Inicialized;

	public DataClass() {

	}

	public void Add(int z, String t) {

		if (z == 0) {
			DBName.set(z, t);
			p1 = DBName.get(z).toString();
			// System.out.println(" Зашли в If по Ваське - >>>> " + p1);
			Inicialized = true;
			// System.out.println("Поменяли, на хуй, на True = " + Inicialized);

		} else {
			// System.out.println(" Зашли в If НЕ по Ваське - >>>> " + p1);
			DBName.add(t);
			p1 = DBName.get(z).toString();
			// System.out.println(p1);
		}
	}

	public void AddID(int z, int t) {

		if (Inicialized == false) {
			DBID.set(0, t);

			Inicialized = true;

		} else {

			DBID.add(t);

		}
	}

	public void AddPhone(int z, String t) {

		if (z == 0) {
			DBPhone.set(0, t);

			Inicialized = true;

		} else {
			DBPhone.add(t);
		}

	}

	public void AddEmail(int z, String t) {

		if (z == 0) {
			DBEmail.set(0, t);

			Inicialized = true;

		} else {
			DBEmail.add(t);

		}

	}

	public void AddFrom(int z, int t) {

		if (z == 0) {
			DBFROM.set(0, t);

			Inicialized = true;

		} else {

			DBFROM.add(t);

		}
	}

	public void AddBirthday(int z, String t) {

		if (z == 0) {
			DBBirthday.set(0, t);

			Inicialized = true;

		} else {
			DBBirthday.add(t);

		}

	}

	public void AddStatus(int z, int t) {

		if (z == 0) {
			DBStatus.set(0, t);

			Inicialized = true;

		} else {
			DBStatus.add(t);

		}

	}

	public String GetName(int p) {

		p1 = DBName.get(p).toString();
		// System.out.println(p1);

		return p1;
	}

	public int GetID(int p) {

		int i10;
		i10 = DBID.get(p);

		return i10;
	}

	public String GetPhone(int p) {

		p1 = DBPhone.get(p).toString();
		// System.out.println(p1);

		return p1;
	}

	public String GetEmail(int p) {

		p1 = DBEmail.get(p).toString();
		// System.out.println(p1);

		return p1;
	}

	public int GetFrom(int p) {
		int i10;
		i10 = DBFROM.get(p);

		return i10;
	}

	public int GetStatus(int p) {
		int i10;

		i10 = DBStatus.get(p);

		return i10;
	}

	public String GetBithday(int p) {

		p1 = DBBirthday.get(p).toString();
		// System.out.println(p1);

		return p1;
	}

	public void Inicialize() {

		DBID.add(1);
		DBName.add("Васька");
		DBPhone.add("Васька");
		DBEmail.add("Васька");
		DBFROM.add(1);
		DBBirthday.add("Васька");
		DBStatus.add(1);
		Inicialized = false;

	}

	public void ClearAll() {

		DBID.clear();
		DBName.clear();
		DBPhone.clear();
		DBEmail.clear();
		DBFROM.clear();
		DBBirthday.clear();
		DBStatus.clear();
		Inicialized = false;

	}

}
