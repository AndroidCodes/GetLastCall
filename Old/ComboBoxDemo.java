
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

public class ComboBoxDemo {

	public List<Country> countries;
	public String ID_Detected;
	public String Type;
	private JComboBox cBox;

	public ComboBoxDemo() {
		countries = createCountryList();

	}

	// public static void JComboBox1() {

	// System.out.println("Beer");

	// }

	// private JComboBox createComboBox(List<Country> countries) {

	public int GetMyItem(int i_ID, List<Country> countries) {
		final JComboBox comboBox = new JComboBox(countries.toArray());

		// Boolean trigger;
		// trigger = false;
		for (int i100 = 0; i100 < 7; ++i100) {

			Country country = (Country) comboBox.getItemAt(i100);

			String extractedID = country.getIso();
			String extractedName = country.getName();
			// System.out.println("************ Item #" + i100 + " - > " +
			// extractedID + " - > " + extractedName);
		}
		// if (extractedID == "1") {
		// trigger = true;
		// System.out.println("GOT YOU");
		int s = 1;
		return s;

	}

	public JComboBox createComboBox(String T, List<Country> countries) {
		final JComboBox comboBox = new JComboBox(countries.toArray());
		comboBox.setRenderer(new ComboBoxRenderer());
		Type = T;
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Country country = (Country) comboBox.getSelectedItem();
					// System.out.println("Вошли. ID элемента равен = " +
					// country.getIso());
					// System.out.println("TYPE - " + Type);

					ID_Detected = country.getIso();

					GUI obj = new GUI();
					obj.StatusAndFromUpdate(Type, ID_Detected);

				}
			}
		});
		return comboBox;
	}

	// private class ComboBoxRenderer extends DefaultListCellRenderer {
	public class ComboBoxRenderer extends DefaultListCellRenderer {

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Country country = (Country) value;
			label.setText(country.getName());
			return label;
		}
	}

	// private List<Country> createCountryList() {
	public List<Country> createCountryList() {
		List<Country> list = new ArrayList<>();

		list.add(new Country("Afghanistan", "AF"));
		list.add(new Country("Åland Islands", "AX"));
		list.add(new Country("Albania", "AL"));

		return list;
	}

	public String GetOwnListItem() {

		System.out.println("ID_Detected -----------> " + ID_Detected);
		return ID_Detected;

	}

	public List<Country> createListfromArray(String[][] SourceArray) {

		int lenght;
		// int lenght2;
		lenght = SourceArray.length;
		// lenght2 = SourceArray[0].length;

		// System.out.println("Количество строк -----------> " + lenght);
		// System.out.println("Количество столбцов -----------> " + lenght2);
		// lenght = lenght + 1;
		List<Country> list = new ArrayList<>();

		list.add(new Country("Не выбрано", "NA"));

		for (int i = 0; i < lenght; ++i) {

			// System.out.println("#" + i + " = " + SourceArray[i][1]);
			list.add(new Country(SourceArray[i][2], SourceArray[i][1]));

		}

		return list;
	}

	public class Country {

		private String name;
		private String iso;

		public Country(String name, String iso) {
			this.name = name;
			this.iso = iso;
		}

		public String getName() {
			return name;
		}

		public String getIso() {
			return iso;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// new ComboBoxDemo();
			}
		});

	}
}