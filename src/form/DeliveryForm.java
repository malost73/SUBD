package form;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Entities.Delivery;
import Entities.Car;
import Entities.Customer;
import Entities.Provider;

import javax.swing.JComboBox;

public class DeliveryForm extends JFrame {	
	public int idSelected;
	private Connection connection = null;

	private JPanel panel;
	private JTextField textFieldDate;
	private JComboBox comboBoxProvider;
	private JComboBox comboBoxCar;
	private ArrayList<Car> car;
	private ArrayList<Provider> provider;

	
	public DeliveryForm(Connection connection) throws SQLException{
		initialize();
		this.idSelected = -1;
		this.connection = connection;
		
		Provider pr = new Provider();
		provider = new ArrayList<>(pr.getTable(connection));
		comboBoxProvider.removeAllItems();
		for (int i = 0; i < provider.size(); i++) {
			comboBoxProvider.addItem("" + provider.get(i).getSurname());
		}
		Car c = new Car();
		car = new ArrayList<>(c.getTable(connection));
		comboBoxCar.removeAllItems();
		for (int i = 0; i < car.size(); i++) {
			comboBoxCar.addItem("" + car.get(i).getModel());
		}
	}
	
	/**
	 * Launch the application.
	 * @wbp.parser.constructor
	 */
	public DeliveryForm(int id, Connection connection)throws SQLException  {
		initialize();
		this.idSelected = id;
		this.connection = connection;
		int pro_id = 0;
		int car_id = 0;
		Delivery d = new Delivery();
		ArrayList<Delivery> delivery = new ArrayList<>(d.getTable(connection));
		d = null;
		for (int i = 0; i < delivery.size(); i++) {
			if (id == delivery.get(i).getId()) {
				d = delivery.get(i);
				break;
			}
		}
		Provider pr = new Provider();
		provider = new ArrayList<>(pr.getTable(connection));
		comboBoxProvider.removeAllItems();
		for (int i = 0; i < provider.size(); i++) {
			comboBoxProvider.addItem("" + provider.get(i).getSurname());
			if (d.getProviderid() == provider.get(i).getId()) {
				pro_id = i;
			}
		}
		Car c = new Car();
		car = new ArrayList<>(c.getTable(connection));
		comboBoxCar.removeAllItems();
		for (int i = 0; i < car.size(); i++) {
			comboBoxCar.addItem("" + car.get(i).getModel());
			if (d.getCarid() == car.get(i).getId()) {
				car_id = i;
			}

		}
		textFieldDate.setText(d.getDate());
		comboBoxProvider.setSelectedItem(provider.get(pro_id).getSurname());
		comboBoxCar.setSelectedItem(car.get(car_id).getModel());

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		panel = new JPanel();
		setContentPane(panel);
		getContentPane().setLayout(null);
		
		JLabel label_1 = new JLabel("Поставщик");
		label_1.setBounds(29, 76, 73, 16);
		panel.add(label_1);
		
	    comboBoxProvider = new JComboBox();
		comboBoxProvider.setBounds(138, 76, 236, 22);
		panel.add(comboBoxProvider);
		
		JLabel label_2 = new JLabel("Автомобиль");
		label_2.setBounds(29, 118, 97, 16);
		panel.add(label_2);
		
		comboBoxCar = new JComboBox();
		comboBoxCar.setBounds(138, 118, 236, 22);
		panel.add(comboBoxCar);
		
		JButton buttonSave = new JButton("Сохранить");
		buttonSave.setBounds(34, 186, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Delivery delivery = null;
				if (textFieldDate.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					delivery = new Delivery( comboBoxProvider.getSelectedIndex(), comboBoxCar.getSelectedIndex() , textFieldDate.getText());
					if (idSelected < 0) {
						delivery.addElement(  provider.get(comboBoxProvider.getSelectedIndex()).getId(),  car.get(comboBoxCar.getSelectedIndex()).getId(),textFieldDate.getText(), connection);
					} else {
						delivery.refreshElement(idSelected, provider.get(comboBoxProvider.getSelectedIndex()).getId(),  car.get(comboBoxCar.getSelectedIndex()).getId(), textFieldDate.getText(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		getContentPane().add(buttonSave);
		
		JButton buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(158, 186, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		getContentPane().add(buttonCancel);
		
		JLabel label = new JLabel("Дата");
		label.setBounds(29, 39, 56, 16);
		panel.add(label);
		
		textFieldDate = new JTextField();
		textFieldDate.setText((String) null);
		textFieldDate.setColumns(10);
		textFieldDate.setBounds(138, 36, 193, 22);
		panel.add(textFieldDate);
	
	}
}
