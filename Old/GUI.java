import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

public class GUI {

	public JComboBox cBoxOWN;
	public JComboBox cBoxOWN2;
	public List<ComboBoxDemo.Country> countries1;
	public List<ComboBoxDemo.Country> countries2;
	static boolean Addstatus = false;
	static boolean LoginStatus = false;
	static boolean ConnectStatus = false;
	static boolean EditMode = false;
	static String List_StatusItemSelected;

	String RetVal1;
	static String p1;
	String[][] RetValArr;
	static String[] AdminPanelData;

	static String[][] StatusColumn;
	static String[][] FromColumn;

	Date CurDate = new Date();
	private JFrame frame;
	public JTextField Name_textField;

	private JTextField CurrentEnty_textField;

	private JLabel EntriesCountlbl;
	private JLabel Name_Label;
	private JLabel Phone_Label;
	private JLabel email_Label;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;

	// Лейбелы-подписи админской части на первой вкладке

	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_51;
	private JLabel lblNewLabel_52;
	private JLabel lblNewLabel_53;
	private JLabel lblNewLabel_54;
	private JLabel lblNewLabel_55;
	private JLabel lblNewLabel_56;

	// ===================================================

	private JSeparator separator_2;

	private JButton btnNewButton_1;
	private JButton btnNewButton;

	private JButton PrevEntryButton;
	private JButton LastEntryButton;
	private JButton FirstEntryButton;
	private JButton btnEdit;
	private JButton DeleteButton;
	private JButton NextEntryButton;
	private JButton AddEntryButton;
	private JPanel panel4;
	static JPanel panel;
	private JTabbedPane tabbedPane;

	public JFormattedTextField Phone_formattedTextField;
	public JTextField email_textField;
	public JDateChooser dateChooser;
	// public Connection conn3 = null;
	static Connection conn3 = null;
	static int EC1; // количество элементов в основной таблице
	static int StatusTableEntriesCount; // количество элементов в таблице Status
	static int StatusTableFromCount; // количество элементов в таблице From
	static int CurrentEntry;
	static String CurrentEntrySTR;

	static DataClass F = new DataClass();
	// public static Functions con3;

	// Addstatus = false;
	static Functions con3 = new Functions();
	private JTextField LoginField;
	private JTextField PasswordField;
	private JButton btnNewButton_3;
	private JTextField AdminLogin;
	private JTextField AdminName;
	private JTextField AdminEmail;
	private JTextField AdminBirthday;
	private JTextField AdminRegDate;
	private JTextField AdminLastVisitDate;
	private JTextField AdminClientsCount;

	// public Connection conn3;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {

		DB_Initialize();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
					FirstConnect FC = window.new FirstConnect();
					ConnectStatus = true;
					FC.FirstEntry();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public GUI() {

		CurrentEnty_textField = new JTextField();
		Name_textField = new JTextField();

		EntriesCountlbl = new JLabel("New label");
		initialize();
		EntriesCountlbl.setText(Integer.toString(EC1));

		if (LoginStatus == true) {

			System.out.println("LoginStatus == true");
			AdminPartOn();
			btnNewButton.setEnabled(false);

		} else {

			btnNewButton_3.setEnabled(false);

			tabbedPane.setEnabledAt(1, false);
			// UnableForm();
			AdminPartOff();

		}

	}

	public void StatusAndFromUpdate(String T1, String s) {

		int iUpdateID;
		int Status_Temp_var;
		String sUpdateID;
		int TempEntry;

		if (EditMode == true) {

			if (s != "NA") {

				if (T1 == "Status") {

					Status_Temp_var = F.GetStatus(CurrentEntry - 1);
					String s1 = String.valueOf(Status_Temp_var);
					String s2 = s;

					System.out.println("s1 = " + s1);
					System.out.println("s2 = " + s2);
					System.out.println("CurrentEntry сейчас равен" + CurrentEntry);

					iUpdateID = F.GetID(CurrentEntry - 1);
					sUpdateID = String.valueOf(iUpdateID);

					TempEntry = CurrentEntry;

					if ((s1.equals(s2)) == false) {

						System.out.println("Обновлен список СТАТУС");

						con3.CRM_EditStatus(conn3, s2, sUpdateID);

						Refresh_GoNEntry(TempEntry);

						CurrentEntry = TempEntry;

					}

				} else {

					Status_Temp_var = F.GetFrom(CurrentEntry - 1);
					String s1 = String.valueOf(Status_Temp_var);
					String s2 = s;

					iUpdateID = F.GetID(CurrentEntry - 1);
					sUpdateID = String.valueOf(iUpdateID);

					TempEntry = CurrentEntry;

					if ((s1.equals(s2)) == false) {

						System.out.println("Обновлен список FROM");
						con3.CRM_EditFrom(conn3, s2, sUpdateID);

						Refresh_GoNEntry(TempEntry);

						CurrentEntry = TempEntry;

					}

				}

			} else {

				System.out.println("Addstatus" + Addstatus);
				if (Addstatus != true) {
					JOptionPane.showMessageDialog(frame, "Поля СТАТУС и ОТКУДА КЛИЕНТ должны содержать значения!");
				}

			}
		}

	}

	public static void DB_Initialize() {

		conn3 = con3.DoConnect(); // коннектимся

		int ID;

		ArrayList<String> list = new ArrayList<String>();
		String l;
		String p;

		list = con3.LoginFileRead();

		if (list.size() == 2) {

			l = list.get(0);
			p = list.get(1);

			System.out.println("Логин из файла = " + l);
			System.out.println("Пароль из файла = " + p);

			ID = con3.CRMUserRequest(conn3, l, p);
			AdminPanelData = con3.CRMUserFullData(conn3, l, p);

			if (ID != 0) {

				System.out.println("ID Юзера = " + ID);
				LoginStatus = true;

			}
		}

		EC1 = con3.CRMQuery_EntriesCount(conn3); // запрашиваем кол-во записей
		StatusTableEntriesCount = con3.CRMQueryStatus_EntriesCount(conn3);

		StatusTableFromCount = con3.CRMQueryFRom_EntriesCount(conn3);

		System.out.println("Entries count(Из модуля GYU): " + EC1);

		if (EC1 > 0) {
			CurrentEntry = 1;
			System.out.println("Все ОК, текущая запись - " + CurrentEntry);

		} else {

			System.out.println("Выскакивает обработчик ошибок");

		}

		con3.Ini();
		con3.CRMQuery2(conn3, EC1 + 1);

		StatusColumn = con3.CRMQueryStatus(conn3, StatusTableEntriesCount);
		FromColumn = con3.CRMQueryFrom(conn3, StatusTableFromCount);

	}

	public class FirstConnect {
		public void FirstEntry() {

			int CurStatus = F.GetStatus(CurrentEntry - 1);

			int CurFrom = F.GetFrom(CurrentEntry - 1);

			if (LoginStatus != false) {

				Name_textField.setText(F.GetName(CurrentEntry - 1));
				Phone_formattedTextField.setText(F.GetPhone(CurrentEntry - 1));
				email_textField.setText(F.GetEmail(CurrentEntry - 1));

				// ============================================================================================================================
				// == ФОРМАТИРУЕМ ДАТУ ==
				// ============================================================================================================================

				CurDate = DateMajic.GetDate(F.GetBithday(CurrentEntry - 1));

				// ============================================================================================================================

				// Birthday_formattedTextField.setText(F.GetBithday(CurrentEntry
				// -
				// 1));
				dateChooser.setDate(CurDate);

				cBoxOWN.setSelectedIndex(CurStatus);
				cBoxOWN2.setSelectedIndex(CurFrom);

			} else {

				Name_textField.setText("-");
				Phone_formattedTextField.setText("");
				email_textField.setText("-");

			}

		}
	}

	public void FirstEntry2() {

		int CurStatus = F.GetStatus(CurrentEntry - 1);

		int CurFrom = F.GetFrom(CurrentEntry - 1);

		Name_textField.setText(F.GetName(CurrentEntry - 1));

		System.out.println(F.GetName(CurrentEntry - 1));

		Phone_formattedTextField.setText(F.GetPhone(CurrentEntry - 1));
		email_textField.setText(F.GetEmail(CurrentEntry - 1));

		// ============================================================================================================================
		// == ФОРМАТИРУЕМ ДАТУ ==
		// ============================================================================================================================

		CurDate = DateMajic.GetDate(F.GetBithday(CurrentEntry - 1));

		// ============================================================================================================================

		dateChooser.setDate(CurDate);

		cBoxOWN.setSelectedIndex(CurStatus);
		cBoxOWN2.setSelectedIndex(CurFrom);

	}

	public void NEntry(int Entry) {

		int CurStatus = F.GetStatus(Entry - 1);

		int CurFrom = F.GetFrom(Entry - 1);

		System.out.println("NENTRY, текущая запись - " + Entry);

		Name_textField.setText(F.GetName(Entry - 1));
		Phone_formattedTextField.setText(F.GetPhone(Entry - 1));
		email_textField.setText(F.GetEmail(Entry - 1));

		// ============================================================================================================================
		// == ФОРМАТИРУЕМ ДАТУ ==
		// ============================================================================================================================

		CurDate = DateMajic.GetDate(F.GetBithday(Entry - 1));
		System.out.println("Взяли Дату = " + CurDate);

		// ============================================================================================================================

		// Birthday_formattedTextField.setText(F.GetBithday(Entry - 1));
		dateChooser.setDate(CurDate);

		// cBoxOWN.setSelectedIndex(CurStatus);
		// cBoxOWN2.setSelectedIndex(CurFrom);

	}

	public boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	public void Refresh_GoFirstEntry() {

		con3.Disconnect();
		F.ClearAll();
		EC1 = 0;

		DB_Initialize();
		CurrentEntry = 1;
		String CurrentEntrySTR = Integer.toString(CurrentEntry);

		CurrentEnty_textField.setText(CurrentEntrySTR);
		FirstEntry2();

	}

	public void Refresh_GoNEntry(int CE) {

		con3.Disconnect();
		F.ClearAll();
		EC1 = 0;

		DB_Initialize();
		// CurrentEntry = 1;

		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("       CE    =    " + CE);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		String CurrentEntrySTR = Integer.toString(CE);

		CurrentEnty_textField.setText(CurrentEntrySTR);

		NEntry(CE);

	}

	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	public static String phoneformatting(String s) {

		String newphone;
		char c;
		newphone = "";

		for (int x = 0; x < s.length(); x++) {

			c = s.charAt(x);

			if (c == '-') {

			} else {

				newphone = newphone + c;

			}

		}

		return newphone;

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		Date CurDate1 = new Date();

		frame = new JFrame();
		panel = new JPanel();

		frame.setBounds(100, 100, 816, 649);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JComboBox From_comboBox = new JComboBox();

		From_comboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
		From_comboBox.setEditable(true);
		From_comboBox.setMaximumRowCount(2);
		frame.getContentPane().add(From_comboBox);

		ComboBoxDemo CMD1 = new ComboBoxDemo();
		ComboBoxDemo CMD2 = new ComboBoxDemo();
		countries1 = CMD1.createListfromArray(StatusColumn);
		countries2 = CMD2.createListfromArray(FromColumn);

		cBoxOWN = CMD1.createComboBox("Status", countries1);
		cBoxOWN2 = CMD2.createComboBox("From", countries2);

		cBoxOWN.setBounds(109, 190, 638, 20);

		cBoxOWN2.setBounds(109, 115, 638, 20);

		panel.add(cBoxOWN);

		panel.add(cBoxOWN2);

		Collection col = System.getProperties().values();
		ArrayList arrayList = new ArrayList(col);
		ArrayListComboBoxModel model = new ArrayListComboBoxModel(arrayList);

		String CurrentEntrySTR = Integer.toString(CurrentEntry);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 250, 780, 361);
		frame.getContentPane().add(tabbedPane);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\u0412\u0445\u043E\u0434", null, panel_1, null);

		// LoginButton

		btnNewButton = new JButton("\u0412\u0445\u043E\u0434");
		btnNewButton.setBounds(263, 285, 226, 37);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int ID;

				ID = con3.CRMUserRequest(conn3, LoginField.getText(), PasswordField.getText());

				System.out.println("       Login    =    " + LoginField.getText());
				System.out.println("       ID    =    " + ID);

				if (ID == 0) {

					LoginField.setText("Не верный пароль / логин");
					LoginField.setForeground(Color.RED);
					PasswordField.setText("Не верный пароль / логин");
					PasswordField.setForeground(Color.RED);

				} else {

					String L = LoginField.getText();
					String P = PasswordField.getText();
					System.out.println("ВОШЛИ");

					con3.LoginFileWrite(L, P);
					LoginStatus = true;

					MainTab();

					CurrentEntry = 1;

					btnNewButton_3.setEnabled(true);
					btnNewButton.setEnabled(false);
					tabbedPane.setSelectedIndex(1);

					// NEntry(2);
					FirstEntry3();

					tabbedPane.repaint();
					tabbedPane.revalidate();
					tabbedPane.setSelectedIndex(0);
					tabbedPane.setSelectedIndex(1);
					panel.repaint();
					panel.revalidate();

					frame.repaint();
					frame.revalidate();

					AdminPartOn();

				}

			}
		});
		panel_1.setLayout(null);
		panel_1.add(btnNewButton);

		LoginField = new JTextField();
		LoginField.setHorizontalAlignment(SwingConstants.CENTER);
		LoginField.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		LoginField.setText("\u041B\u043E\u0433\u0438\u043D");
		LoginField.setToolTipText("\u041B\u043E\u0433\u0438\u043D");
		LoginField.setBounds(205, 50, 538, 37);
		panel_1.add(LoginField);
		LoginField.setColumns(10);

		PasswordField = new JTextField();
		PasswordField.setHorizontalAlignment(SwingConstants.CENTER);
		PasswordField.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		PasswordField.setText("\u041F\u0430\u0440\u043E\u043B\u044C");
		PasswordField.setToolTipText("\u041F\u0430\u0440\u043E\u043B\u044C");
		PasswordField.setBounds(205, 121, 538, 37);
		panel_1.add(PasswordField);
		PasswordField.setColumns(10);

		Image img1 = new ImageIcon(this.getClass().getResource("/Login.png")).getImage();

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 11, 183, 177);
		lblNewLabel.setIcon(new ImageIcon(img1));
		panel_1.add(lblNewLabel);

		String tLogPage = "Для использования программы Вам необходима учетная запись Havana-CRM. Получить ее можно, пройдя регистрацию "
				+

				"<html>dddddddd<br> rrrrr \n rrrrrr</html>";

		separator_2 = new JSeparator();
		separator_2.setBounds(10, 182, 755, 13);
		panel_1.add(separator_2);

		lblNewLabel_1 = new JLabel(
				"\u041F\u043E\u043B\u0443\u0447\u0438\u0442\u044C \u0435\u0435 \u043C\u043E\u0436\u043D\u043E, \u043F\u0440\u043E\u0439\u0434\u044F \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044E \u043D\u0430 \u0441\u0430\u0439\u0442\u0435 www.havana-crm.su");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 214, 765, 14);
		panel_1.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("www.havana-crm.su");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setForeground(Color.BLUE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(10, 233, 755, 14);
		panel_1.add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel(
				"\u0421 \u044D\u0442\u043E\u0433\u043E \u0436\u0435 \u0441\u0430\u0439\u0442\u0430 \u0412\u044B \u043C\u043E\u0436\u0435\u0442\u0435 \u043F\u043E\u043B\u0443\u0447\u0438\u0442\u044C \u0434\u043E\u0441\u0442\u0443\u043F \u043A \u0441\u0432\u043E\u0438\u043C \u0434\u0430\u043D\u043D\u044B\u043C \u0447\u0435\u0440\u0435\u0437 WEB-\u0432\u0435\u0440\u0441\u0438\u044E \u0438 \u0441\u043A\u0430\u0447\u0430\u0442\u044C \u0441\u0432\u0435\u0436\u0438\u0435 \u043E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u044F \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u044B.");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(10, 260, 755, 14);
		panel_1.add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel(
				"\u0414\u043B\u044F \u0438\u0441\u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u043D\u0438\u044F \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u044B \u0412\u0430\u043C \u043D\u0435\u043E\u0431\u0445\u043E\u0434\u0438\u043C\u0430 \u0443\u0447\u0435\u0442\u043D\u0430\u044F \u0437\u0430\u043F\u0438\u0441\u044C Havana-CRM. ");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(10, 189, 755, 14);
		panel_1.add(lblNewLabel_4);

		// =======================================================================================================================
		// ADD MAIN PANEL
		// =======================================================================================================================

		tabbedPane.addTab("\u041A\u043B\u0438\u0435\u043D\u0442", null, panel,
				"\u041A\u043E\u043D\u0442\u0430\u043A\u0442\u043D\u044B\u0435 \u0434\u0430\u043D\u043D\u044B\u0435 \u043A\u043B\u0438\u0435\u043D\u0442\u0430");
		panel.setLayout(null);

		// Name_textField = new JTextField();
		Name_textField.setBounds(73, 11, 674, 20);
		panel.add(Name_textField);

		Name_textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int iUpdateID;
				String sUpdateID;
				int TempEntry;

				if (EditMode == true) {

					String s1 = F.GetName(CurrentEntry - 1);

					String s2 = Name_textField.getText();

					System.out.println("s1 = " + s1);
					System.out.println("s2 = " + s2);

					iUpdateID = F.GetID(CurrentEntry - 1);
					sUpdateID = String.valueOf(iUpdateID);

					TempEntry = CurrentEntry;

					if ((s1.equals(s2)) == false) {

						System.out.println("СУКА БЛЯ");
						// CRM_EditeEntryName(Connection conn, String
						// ClientName,
						// String ID)
						con3.CRM_EditeEntryName(conn3, s2, sUpdateID);

						Refresh_GoNEntry(TempEntry);

						CurrentEntry = TempEntry;

					}
				}

			}

		});

		Name_textField.setHorizontalAlignment(SwingConstants.CENTER);
		Name_textField.setColumns(10);

		Phone_formattedTextField = new JFormattedTextField(createFormatter("#-###-###-##-##"));
		Phone_formattedTextField.setBounds(73, 42, 674, 23);
		panel.add(Phone_formattedTextField);
		Phone_formattedTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				int iUpdateID;
				String sUpdateID;
				int TempEntry;

				if (EditMode == true) {

					String s1 = F.GetPhone(CurrentEntry - 1);
					String s2 = Phone_formattedTextField.getText();

					String EditQphone = phoneformatting(s2);

					System.out.println("s1 = " + s1);
					System.out.println("s2 = " + s2);
					System.out.println("EditQphone = " + EditQphone);

					iUpdateID = F.GetID(CurrentEntry - 1);
					sUpdateID = String.valueOf(iUpdateID);

					TempEntry = CurrentEntry;

					if ((s1.equals(EditQphone)) == false) {

						System.out.println("СУКА БЛЯ PHONE");

						con3.CRM_EditEntryPhone(conn3, EditQphone, sUpdateID);

						Refresh_GoNEntry(TempEntry);

						CurrentEntry = TempEntry;

					}
				}

			}
		});
		Phone_formattedTextField.setHorizontalAlignment(SwingConstants.CENTER);

		email_textField = new JTextField();
		email_textField.setBounds(73, 76, 674, 20);
		panel.add(email_textField);
		email_textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				int iUpdateID;
				String sUpdateID;
				int TempEntry;

				if (EditMode == true) {

					String s1 = F.GetEmail(CurrentEntry - 1);
					String s2 = email_textField.getText();

					System.out.println("s1 = " + s1);
					System.out.println("s2 = " + s2);

					iUpdateID = F.GetID(CurrentEntry - 1);
					sUpdateID = String.valueOf(iUpdateID);

					TempEntry = CurrentEntry;

					if ((s1.equals(s2)) == false) {

						System.out.println("СУКА БЛЯ EMAIL");

						con3.CRM_EditEmailPhone(conn3, s2, sUpdateID);

						Refresh_GoNEntry(TempEntry);

						CurrentEntry = TempEntry;

					}
				}

			}
		});
		email_textField.setHorizontalAlignment(SwingConstants.CENTER);
		email_textField.setColumns(10);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(489, 146, 258, 23);
		panel.add(dateChooser);

		dateChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {

				int iUpdateID;
				String sUpdateID;
				int TempEntry;

				if (EditMode == true) {

					String s1 = F.GetBithday(CurrentEntry - 1);

					String s2 = DateMajic.GetDateInsideOut(dateChooser.getDate());

					System.out.println("s1 = F.GetBithday... = " + s1);
					System.out.println("s2 = DateMajic.GetDateInsideOut(... = " + s2);

					iUpdateID = F.GetID(CurrentEntry - 1);
					sUpdateID = String.valueOf(iUpdateID);

					TempEntry = CurrentEntry;

					if ((s1.equals(s2)) == false) {

						con3.CRM_EditBirthday(conn3, s2, sUpdateID);

						Refresh_GoNEntry(TempEntry);

						CurrentEntry = TempEntry;

					}

				}

			}
		});
		dateChooser.setDate(CurDate1);

		Name_Label = new JLabel("\u0418\u043C\u044F");
		Name_Label.setBounds(10, 13, 180, 17);
		panel.add(Name_Label);

		Phone_Label = new JLabel("\u0422\u0435\u043B\u0435\u0444\u043E\u043D");
		Phone_Label.setBounds(10, 46, 206, 14);
		panel.add(Phone_Label);

		email_Label = new JLabel("E-mail");
		email_Label.setBounds(10, 79, 206, 14);
		panel.add(email_Label);

		JLabel Birthday_Label = new JLabel("\u0414\u0435\u043D\u044C \u0440\u043E\u0436\u0434\u0435\u043D\u0438\u044F");
		Birthday_Label.setBounds(10, 155, 231, 14);
		panel.add(Birthday_Label);

		JLabel From_Label = new JLabel("\u041E\u0442\u043A\u0443\u0434\u0430 \u043A\u043B\u0438\u0435\u043D\u0442");
		From_Label.setBounds(10, 118, 206, 14);
		panel.add(From_Label);

		JLabel Status_Label = new JLabel(
				"\u0422\u0435\u043A\u0443\u0449\u0438\u0439 \u0441\u0442\u0430\u0442\u0443\u0441");
		Status_Label.setBounds(10, 193, 206, 14);
		panel.add(Status_Label);

		PrevEntryButton = new JButton("<<=");
		PrevEntryButton.setBounds(152, 242, 89, 23);
		panel.add(PrevEntryButton);

		CurrentEnty_textField.setBounds(272, 243, 258, 20);
		panel.add(CurrentEnty_textField);
		CurrentEnty_textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					String st = CurrentEnty_textField.getText();
					if (isNumeric(st) == true) {

						int i = Integer.valueOf(st);

						if ((i >= 1) && (i <= EC1)) {

							CurrentEntry = i;
							NEntry(i);

						}
					}
				}

			}
		});
		CurrentEnty_textField.setHorizontalAlignment(SwingConstants.CENTER);

		CurrentEnty_textField.setText(CurrentEntrySTR);
		CurrentEnty_textField.setColumns(10);

		NextEntryButton = new JButton("= >>");
		NextEntryButton.setBounds(548, 242, 89, 23);
		panel.add(NextEntryButton);

		AddEntryButton = new JButton(
				"\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C \u0437\u0430\u043F\u0438\u0441\u044C");
		AddEntryButton.setBounds(35, 276, 122, 23);
		panel.add(AddEntryButton);

		btnEdit = new JButton("\u041F\u0440\u0430\u0432\u0438\u0442\u044C \u0437\u0430\u043F\u0438\u0441\u044C");
		btnEdit.setBounds(408, 274, 147, 23);
		panel.add(btnEdit);

		DeleteButton = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C \u0437\u0430\u043F\u0438\u0441\u044C");
		DeleteButton.setBounds(611, 276, 136, 23);
		panel.add(DeleteButton);

		JLabel Lbl_EntriesCount = new JLabel(
				"\u041A\u043E\u043B-\u0432\u043E \u0437\u0430\u043F\u0438\u0441\u0435\u0439:");
		Lbl_EntriesCount.setBounds(0, 319, 103, 14);
		panel.add(Lbl_EntriesCount);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(185, 319, 15, 14);
		panel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 319, 772, 14);
		panel.add(separator_1);

		// EntriesCountlbl = new JLabel("New label");
		EntriesCountlbl.setHorizontalAlignment(SwingConstants.RIGHT);
		EntriesCountlbl.setBounds(99, 319, 82, 14);
		panel.add(EntriesCountlbl);

		FirstEntryButton = new JButton("|<<=");
		FirstEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (FirstEntryButton.isEnabled() != false) {

					NEntry(1);
					CurrentEnty_textField.setText("1");
					CurrentEntry = 1;

				}

			}
		});
		FirstEntryButton.setBounds(35, 242, 89, 23);
		panel.add(FirstEntryButton);

		LastEntryButton = new JButton("=>>|");
		LastEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (LastEntryButton.isEnabled() != false) {
					System.out.println("Записей всего = " + EC1);
					NEntry(EC1);
					CurrentEnty_textField.setText(Integer.toString(EC1));
					CurrentEntry = EC1;

				}

			}
		});
		LastEntryButton.setBounds(658, 242, 89, 23);
		panel.add(LastEntryButton);
		DeleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int iDeleteID;
				String sDeleteID;

				int dialogButton = JOptionPane.YES_NO_OPTION;
				// int dialogResult = JOptionPane.showConfirmDialog(this, "Your
				// Message", "Title on Box", dialogButton);

				iDeleteID = F.GetID(CurrentEntry - 1);
				sDeleteID = String.valueOf(iDeleteID);

				int dialogResult = JOptionPane.showConfirmDialog(frame, "Удалить запись?", "Удаление записи",
						dialogButton);
				if (dialogResult == 0) {
					System.out.println("Yes option");
					con3.CRM_DeleteEntry(conn3, sDeleteID);
					Refresh_GoFirstEntry();

				} else {
					System.out.println("No Option");
				}

			}
		});
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("Addstatus = " + Addstatus);
				System.out.println("ConnectStatus = " + ConnectStatus);
				System.out.println("EditMode Before = " + EditMode);

				if (Addstatus != true) {

					if (ConnectStatus == true) {

						if (EditMode == false) {

							EditMode = true;
							btnEdit.setText("Save Edited");
							System.out.println("EditMode After in IF (Edit Mode is IN)= " + EditMode);
							PrevEntryButton.setEnabled(false);
							NextEntryButton.setEnabled(false);
							DeleteButton.setEnabled(false);
							AddEntryButton.setEnabled(false);
							// btnC.setEnabled(false);
							CurrentEnty_textField.setEnabled(false);

						} else {
							System.out.println("EditMode After in IF (Edit Mode is OUT)= " + EditMode);
							EditMode = false;
							btnEdit.setText("Edit");
							PrevEntryButton.setEnabled(true);
							NextEntryButton.setEnabled(true);
							DeleteButton.setEnabled(true);
							AddEntryButton.setEnabled(true);
							// btnC.setEnabled(true);
							CurrentEnty_textField.setEnabled(true);

						}

						System.out.println("EditMode After IF = " + EditMode);

					}
				}

			}
		});
		AddEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ButtonStat;
				ButtonStat = AddEntryButton.getText();

				if (ButtonStat == "Добавить") {

					String clientname;
					Addstatus = true;

					Name_textField.setText("");
					Phone_formattedTextField.setText("");
					email_textField.setText("");
					// Birthday_formattedTextField.setText("");
					clientname = Name_textField.getText();
					Date CDate = new Date();
					dateChooser.setDate(CDate);

					cBoxOWN.setSelectedIndex(0);
					cBoxOWN2.setSelectedIndex(0);
					CurrentEnty_textField.setText("Режим добавления");
					CurrentEnty_textField.setEnabled(false);
					CurrentEnty_textField.setEditable(false);

					// FinalAddEntryButton.setEnabled(true);
					PrevEntryButton.setEnabled(false);

					LastEntryButton.setEnabled(false);
					FirstEntryButton.setEnabled(false);
					btnEdit.setEnabled(false);
					DeleteButton.setEnabled(false);
					NextEntryButton.setEnabled(false);
					AddEntryButton.setText("Сохранить!");
					btnNewButton_1.setText("Отмена");

				} else {

					Boolean checked = true;
					Boolean a = true;
					Boolean b = true;
					Boolean c = true;

					if (Name_textField.getText().isEmpty() == true) {

						checked = false;
						System.out.println("ПИЗДЕЦ");
						JOptionPane.showMessageDialog(frame, "Поле ИМЯ должно быть обязательно заполненным!");

					}

					a = Phone_formattedTextField.getText().isEmpty();
					b = email_textField.getText().isEmpty();
					c = a & b;
					System.out.println("c = " + c);
					if (c == true) {

						checked = false;

						System.out.println("Поле телефон пустое = " + Phone_formattedTextField.getText().isEmpty());
						System.out.println(email_textField.getText().isEmpty());

						JOptionPane.showMessageDialog(frame,
								"Одно из полей: Телефон или E-mail должно быть обязательно заполненным!");

					}

					if (CMD1.GetOwnListItem() == "NA") {

						checked = false;
						System.out.println("ПИЗДЕЦ");
						JOptionPane.showMessageDialog(frame, "Выберите Статус клиента");

					}
					if (CMD2.GetOwnListItem() == "NA") {

						checked = false;
						System.out.println("ПИЗДЕЦ");
						JOptionPane.showMessageDialog(frame, "Выберите Источник клиента");

					}

					if (checked == true) {

						checked = false;
						// FinalAddEntryButton.setEnabled(false);

						String AddQname = Name_textField.getText();

						String PreAddQphone = Phone_formattedTextField.getText();
						String AddQphone = phoneformatting(PreAddQphone);

						String AddQmail = email_textField.getText();
						String AddQStatus = CMD1.GetOwnListItem();
						String AddQFrom = CMD2.GetOwnListItem();
						String AddQDate;

						AddQDate = DateMajic.GetDateInsideOut(dateChooser.getDate());
						con3.CRM_AddEntry(conn3, AddQname, AddQphone, AddQmail, AddQDate, AddQStatus, AddQFrom);

						Refresh_GoFirstEntry();

						Addstatus = false;

						PrevEntryButton.setEnabled(true);

						LastEntryButton.setEnabled(true);
						FirstEntryButton.setEnabled(true);
						btnEdit.setEnabled(true);
						DeleteButton.setEnabled(true);
						NextEntryButton.setEnabled(true);
						AddEntryButton.setText("Добавить");
						CurrentEnty_textField.setEnabled(true);
						CurrentEnty_textField.setEditable(true);

					}

				}

			}
		});
		NextEntryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (NextEntryButton.isEnabled() != false) {

					System.out.println("Add status - " + Addstatus);
					System.out.println("CurrentEntry - " + CurrentEntry);
					System.out.println("EC1 - " + EC1);

					if (CurrentEntry < EC1) {

						System.out.println("Пиписька");
						CurrentEntry = CurrentEntry + 1;
						System.out.println(CurrentEntry);

						int CurStatus = F.GetStatus(CurrentEntry - 1);

						int CurFrom = F.GetFrom(CurrentEntry - 1);
						cBoxOWN.setSelectedIndex(CurStatus);
						cBoxOWN2.setSelectedIndex(CurFrom);

						Name_textField.setText(F.GetName(CurrentEntry - 1));

						System.out.println("SUKA   " + F.GetName(CurrentEntry - 1));

						Phone_formattedTextField.setText(F.GetPhone(CurrentEntry - 1));
						email_textField.setText(F.GetEmail(CurrentEntry - 1));
						// Birthday_formattedTextField.setText(F.GetBithday(CurrentEntry
						// - 1));

						CurDate = DateMajic.GetDate(F.GetBithday(CurrentEntry - 1));
						dateChooser.setDate(CurDate);

						String CurrentEntrySTR = Integer.toString(CurrentEntry);

						CurrentEnty_textField.setText(CurrentEntrySTR);

					} else {
						System.out.println(CurrentEntry);
					}

					System.out.println("CURRENT ID - " + F.GetID(CurrentEntry - 1));

				}

			}
		});
		PrevEntryButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (PrevEntryButton.isEnabled() != false) {

					System.out.println("CurrentEntry Back - " + CurrentEntry);
					System.out.println("EC1 Back - " + EC1);

					// if (Addstatus == false) {

					// System.out.println("ADD не нажат!");

					if (CurrentEntry > 1) {

						System.out.println("Пиписька назад");
						CurrentEntry = CurrentEntry - 1;
						int CurStatus = F.GetStatus(CurrentEntry - 1);
						int CurFrom = F.GetFrom(CurrentEntry - 1);
						// CurStatus = CurStatus - 1;
						cBoxOWN.setSelectedIndex(CurStatus);
						cBoxOWN2.setSelectedIndex(CurFrom);

						Name_textField.setText(F.GetName(CurrentEntry - 1));
						Phone_formattedTextField.setText(F.GetPhone(CurrentEntry - 1));
						email_textField.setText(F.GetEmail(CurrentEntry - 1));
						// Birthday_formattedTextField.setText(F.GetBithday(CurrentEntry
						// - 1));

						String CurrentEntrySTR = Integer.toString(CurrentEntry);
						CurrentEnty_textField.setText(CurrentEntrySTR);

						CurDate = DateMajic.GetDate(F.GetBithday(CurrentEntry - 1));
						dateChooser.setDate(CurDate);

					} else {
						// CurrentEntry = CurrentEntry + 1;
						System.out.println("Пиписечка все - " + CurrentEntry);
					}

					// } else {

					// System.out.println("ADD нажат!");

					// Name_textField.setText(F.GetName(EC1 - 1));

					// Addstatus = false;

					// }

				}

			}
		});

		// JButton btnNewButton_1 = new
		// JButton("\u0412\u044B\u0445\u043E\u0434");
		btnNewButton_1 = new JButton("\u0412\u044B\u0445\u043E\u0434");

		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ButtonStat;
				ButtonStat = btnNewButton_1.getText();

				if (ButtonStat == "Выход") {

					System.exit(1);

				} else {

					Refresh_GoFirstEntry();

					Addstatus = false;

					PrevEntryButton.setEnabled(true);

					LastEntryButton.setEnabled(true);
					FirstEntryButton.setEnabled(true);
					btnEdit.setEnabled(true);
					DeleteButton.setEnabled(true);
					NextEntryButton.setEnabled(true);
					AddEntryButton.setText("Добавить");
					CurrentEnty_textField.setEnabled(true);
					CurrentEnty_textField.setEditable(true);
					btnNewButton_1.setText("Выход");

				}

			}
		});
		btnNewButton_1.setBounds(201, 276, 156, 23);
		panel.add(btnNewButton_1);

		Image img = new ImageIcon(this.getClass().getResource("/01.jpg")).getImage();

		JLabel Image1 = new JLabel("New label");
		Image1.setBounds(10, 11, 780, 228);
		frame.getContentPane().add(Image1);
		Image1.setIcon(new ImageIcon(img));

		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// PrevEntryButton.setEnabled(false);
				// LastEntryButton.setEnabled(false);
				// FirstEntryButton.setEnabled(false);
				// btnEdit.setEnabled(false);
				// DeleteButton.setEnabled(false);
				//// NextEntryButton.setEnabled(false);
				// Name_textField.setEnabled(false);
				/// Name_Label.setEnabled(false);
				// Phone_Label.setEnabled(false);
				// Phone_formattedTextField.setEnabled(false);
				// Phone_formattedTextField.setText("");
				// email_Label.setEnabled(false);
				// email_textField.setEnabled(false);
				// email_textField.setText("-");
				// CurrentEnty_textField.setEnabled(false);
				// CurrentEnty_textField.setText("-");
				// cBoxOWN.setEditable(false);
				// cBoxOWN.setEnabled(false);
				// cBoxOWN2.setEnabled(false);
				// cBoxOWN2.setEditable(false);
				// dateChooser.setEnabled(false);
				// btnNewButton_1.setEnabled(false);
				// AddEntryButton.setEnabled(false);
				// EntriesCountlbl.setText("-");

				UnableForm();

			}
		});
		btnNewButton_2.setBounds(20, 292, 89, 23);
		panel_1.add(btnNewButton_2);

		btnNewButton_3 = new JButton(
				"\u0412\u044B\u0439\u0442\u0438 \u0438\u0437 \u0443\u0447\u0435\u0442\u043D\u043E\u0439 \u0437\u0430\u043F\u0438\u0441\u0438");
		btnNewButton_3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// System.gc();
				File file = new File("C:\\Loginfo.dat");
				final String FILE_PATH = "C:\\Loginfo.dat";

				try {
					deleteFile(FILE_PATH);

					System.out.println("C:\\Loginfo.dat файл удален");
					LoginField.setText("Логин");
					LoginField.setForeground(Color.black);
					PasswordField.setText("Пароль");
					PasswordField.setForeground(Color.black);

					LoginStatus = false;

					tabbedPane.remove(1);
					AdminPartOff();
					btnNewButton.setEnabled(true);
					btnNewButton_3.setEnabled(false);
					tabbedPane.repaint();
					tabbedPane.revalidate();

					frame.repaint();
					frame.revalidate();

				} catch (IOException e1) {
					e1.printStackTrace();
				}

				// try {
				// file.delete();
				// } catch (NoSuchFileException x) {
				// System.err.format("%s: no such" + " file or directory%n");
				// } catch (DirectoryNotEmptyException x) {
				// System.err.format("%s not empty%n");
				// } catch (IOException x) {
				// File permission problems are caught here.
				// System.err.println(x);
				// }

				// if (file.delete()) {

				// } else

				// System.out.println("Файла C:\\Loginfo.dat не обнаружено");

			}
		});

		btnNewButton_3.setBounds(499, 285, 192, 37);

		panel_1.add(btnNewButton_3);

		AdminLogin = new JTextField();
		AdminLogin.setBounds(318, 19, 425, 20);
		panel_1.add(AdminLogin);
		AdminLogin.setColumns(10);

		AdminName = new JTextField();
		AdminName.setBounds(318, 49, 425, 20);
		panel_1.add(AdminName);
		AdminName.setColumns(10);

		AdminEmail = new JTextField();
		AdminEmail.setBounds(318, 79, 425, 20);
		panel_1.add(AdminEmail);
		AdminEmail.setColumns(10);

		AdminBirthday = new JTextField();
		AdminBirthday.setBounds(318, 109, 425, 20);
		panel_1.add(AdminBirthday);
		AdminBirthday.setColumns(10);

		AdminRegDate = new JTextField();
		AdminRegDate.setBounds(318, 139, 425, 20);
		panel_1.add(AdminRegDate);
		AdminRegDate.setColumns(10);

		AdminLastVisitDate = new JTextField();
		AdminLastVisitDate.setBounds(318, 173, 425, 20);
		panel_1.add(AdminLastVisitDate);
		AdminLastVisitDate.setColumns(10);

		AdminClientsCount = new JTextField();
		AdminClientsCount.setBounds(318, 203, 425, 20);
		panel_1.add(AdminClientsCount);
		AdminClientsCount.setColumns(10);

		lblNewLabel_5 = new JLabel("\u041B\u043E\u0433\u0438\u043D: ");
		lblNewLabel_5.setBounds(182, 23, 134, 13);
		panel_1.add(lblNewLabel_5);

		lblNewLabel_51 = new JLabel("\u0418\u043C\u044F:");
		lblNewLabel_51.setBounds(182, 53, 134, 13);
		panel_1.add(lblNewLabel_51);

		lblNewLabel_52 = new JLabel("E-mail: ");
		lblNewLabel_52.setBounds(182, 83, 134, 13);
		panel_1.add(lblNewLabel_52);

		lblNewLabel_53 = new JLabel("\u0414\u0430\u0442\u0430 \u0440\u043E\u0436\u0434\u0435\u043D\u0438\u044F: ");
		lblNewLabel_53.setBounds(182, 113, 134, 13);
		panel_1.add(lblNewLabel_53);

		lblNewLabel_54 = new JLabel(
				"\u0414\u0430\u0442\u0430 \u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u0438: ");
		lblNewLabel_54.setBounds(182, 143, 134, 13);
		panel_1.add(lblNewLabel_54);

		lblNewLabel_55 = new JLabel(
				"\u041F\u043E\u0441\u043B\u0435\u0434\u043D\u0438\u0439 \u0432\u0438\u0437\u0438\u0442: ");
		lblNewLabel_55.setBounds(182, 173, 134, 13);
		panel_1.add(lblNewLabel_55);

		lblNewLabel_56 = new JLabel(
				"\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u043A\u043B\u0438\u0435\u043D\u0442\u043E\u0432: ");
		lblNewLabel_56.setBounds(182, 203, 134, 13);
		panel_1.add(lblNewLabel_56);

	}

	public static void deleteFile(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		Files.delete(path);
	}

	public void AdminPartOff() {

		AdminLogin.setVisible(false);
		AdminName.setVisible(false);
		AdminBirthday.setVisible(false);
		AdminClientsCount.setVisible(false);
		AdminEmail.setVisible(false);
		AdminRegDate.setVisible(false);
		AdminLastVisitDate.setVisible(false);

		LoginField.setVisible(true);
		PasswordField.setVisible(true);
		lblNewLabel_1.setVisible(true);
		lblNewLabel_2.setVisible(true);
		lblNewLabel_3.setVisible(true);
		lblNewLabel_4.setVisible(true);
		separator_2.setVisible(true);

		lblNewLabel_5.setVisible(false);
		lblNewLabel_51.setVisible(false);
		lblNewLabel_52.setVisible(false);
		lblNewLabel_53.setVisible(false);
		lblNewLabel_54.setVisible(false);
		lblNewLabel_55.setVisible(false);
		lblNewLabel_56.setVisible(false);

	}

	public void AdminPartOn() {

		AdminLogin.setVisible(true);
		AdminLogin.setEditable(false);
		// AdminLogin.setText("fvfvv");
		AdminLogin.setText(AdminPanelData[1]);
		AdminLogin.setHorizontalAlignment(JTextField.CENTER);

		AdminName.setVisible(true);
		AdminName.setEditable(false);
		AdminName.setText(AdminPanelData[0]);
		AdminName.setHorizontalAlignment(JTextField.CENTER);

		AdminBirthday.setEditable(false);
		AdminBirthday.setVisible(true);
		AdminBirthday.setText(AdminPanelData[3]);
		AdminBirthday.setHorizontalAlignment(JTextField.CENTER);

		AdminClientsCount.setEditable(false);
		AdminClientsCount.setVisible(true);
		AdminEmail.setEditable(false);
		AdminEmail.setVisible(true);
		AdminEmail.setText(AdminPanelData[2]);
		AdminEmail.setHorizontalAlignment(JTextField.CENTER);

		AdminRegDate.setEditable(false);
		AdminRegDate.setVisible(true);
		AdminRegDate.setText(AdminPanelData[4]);
		AdminRegDate.setHorizontalAlignment(JTextField.CENTER);

		AdminLastVisitDate.setEditable(false);
		AdminLastVisitDate.setVisible(true);
		AdminLastVisitDate.setText(AdminPanelData[5]);
		AdminLastVisitDate.setHorizontalAlignment(JTextField.CENTER);

		LoginField.setVisible(false);
		PasswordField.setVisible(false);
		lblNewLabel_1.setVisible(false);
		lblNewLabel_2.setVisible(false);
		lblNewLabel_3.setVisible(false);
		lblNewLabel_4.setVisible(false);
		separator_2.setVisible(false);

		lblNewLabel_5.setVisible(true);
		lblNewLabel_51.setVisible(true);
		lblNewLabel_52.setVisible(true);
		lblNewLabel_53.setVisible(true);
		lblNewLabel_54.setVisible(true);
		lblNewLabel_55.setVisible(true);
		lblNewLabel_56.setVisible(true);

	}

	public void UnableForm() {

		FirstEntry3();
	}

	public void MainTab() {

		tabbedPane.addTab("\u041A\u043B\u0438\u0435\u043D\u0442", null, panel,
				"\u041A\u043E\u043D\u0442\u0430\u043A\u0442\u043D\u044B\u0435 \u0434\u0430\u043D\u043D\u044B\u0435 \u043A\u043B\u0438\u0435\u043D\u0442\u0430");
		tabbedPane.setEnabledAt(1, true);
		panel.setLayout(null);

		System.out.println("Создали вкладку");

	}

	public void FirstEntry3() {
		System.out.println("Пиписька 77777");
		CurrentEntry = 1;

		Name_textField.setText(F.GetName(CurrentEntry - 1));

		System.out.println("FRom First Entry 3 = > " + F.GetName(CurrentEntry - 1));

		Phone_formattedTextField.setText(F.GetPhone(CurrentEntry - 1));
		email_textField.setText(F.GetEmail(CurrentEntry - 1));

		CurDate = DateMajic.GetDate(F.GetBithday(CurrentEntry - 1));
		dateChooser.setDate(CurDate);

		String CurrentEntrySTR = Integer.toString(CurrentEntry);

		CurrentEnty_textField.setText(CurrentEntrySTR);

		cBoxOWN.setSelectedIndex(F.GetStatus(CurrentEntry - 1));
		cBoxOWN2.setSelectedIndex(F.GetFrom(CurrentEntry - 1));

	}

}
