package form;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Entities.Car;
import Entities.Customer;
import Entities.Purchase;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class PurchaseForm extends JFrame {

	private Connection connection = null;
	public int idSelected;
	private JPanel contentPane;

	private JPanel panel;
	private JTextField textFieldDate;
	private JComboBox comboBoxCustomer;
	private JComboBox comboBoxCar;
	private ArrayList<Car> car;
	private ArrayList<Customer> customer;
	private JButton buttonSave;
	private JButton buttonCancel;



	public PurchaseForm(Connection connection) throws SQLException  {
		initialize();
		this.connection = connection;
		this.idSelected = -1;
		
		Customer cu = new Customer();
		customer = new ArrayList<>(cu.getTable(connection));
		comboBoxCustomer.removeAllItems();
		for (int i = 0; i < customer.size(); i++) {
			comboBoxCustomer.addItem("" + customer.get(i).getSurname());
		}
		Car c = new Car();
		car = new ArrayList<>(c.getTable(connection));
		comboBoxCar.removeAllItems();
		for (int i = 0; i < car.size(); i++) {
			comboBoxCar.addItem("" + car.get(i).getModel());
		}
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public PurchaseForm(int id, Connection connection) throws SQLException  {
		initialize();
		int cus_id = 0;
		int car_id = 0;
		this.connection = connection;
		this.idSelected = id;
		Purchase p = new Purchase();
		ArrayList<Purchase >purchase = new ArrayList<>(p.getTable(connection));
		p = null;
		for (int i = 0; i < purchase.size(); i++) {
			if (id == purchase.get(i).getId()) {
				p = purchase.get(i);
				break;
			}
		}
		
		Customer cu = new Customer();
		customer = new ArrayList<>(cu.getTable(connection));
		comboBoxCustomer.removeAllItems();
		for (int i = 0; i < customer.size(); i++) {
			comboBoxCustomer.addItem("" + customer.get(i).getSurname());
			if (p.getCustomerid() == customer.get(i).getId()) {
				cus_id = i;
			}
		}
		Car c = new Car();
		car = new ArrayList<>(c.getTable(connection));
		comboBoxCar.removeAllItems();
		for (int i = 0; i < car.size(); i++) {
			comboBoxCar.addItem("" + car.get(i).getModel());
			if (p.getCarid() == car.get(i).getId()) {
				car_id = i;
			}

		}
		textFieldDate.setText(p.getDate());
		comboBoxCustomer.setSelectedItem(customer.get(cus_id).getSurname());
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
		
		JLabel label_1 = new JLabel("Дата");
		label_1.setBounds(12, 34, 56, 16);
		getContentPane().add(label_1);
		
		JLabel label_3 = new JLabel("Заказчик");
		label_3.setBounds(12, 71, 56, 16);
		getContentPane().add(label_3);
		
		JLabel label_4 = new JLabel("Автомобиль");
		label_4.setBounds(12, 113, 97, 16);
		getContentPane().add(label_4);
		
		textFieldDate = new JTextField();
		textFieldDate.setBounds(121, 31, 193, 22);
		getContentPane().add(textFieldDate);
		textFieldDate.setColumns(10);
		
		comboBoxCustomer = new JComboBox();
		comboBoxCustomer.setBounds(121, 71, 236, 22);
		getContentPane().add(comboBoxCustomer);
		
		comboBoxCar = new JComboBox();
		comboBoxCar.setBounds(121, 113, 236, 22);
		getContentPane().add(comboBoxCar);
		
		buttonSave = new JButton("Сохранить");
		buttonSave.setBounds(58, 179, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Purchase purchase = null;
				if (textFieldDate.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					purchase = new Purchase( (comboBoxCustomer.getSelectedIndex()), (comboBoxCar.getSelectedIndex()), textFieldDate.getText());
					if (idSelected < 0) {
						purchase.addElement(customer.get(comboBoxCustomer.getSelectedIndex()).getId(),	car.get(comboBoxCar.getSelectedIndex()).getId(),textFieldDate.getText(), connection);
					} else {
						purchase.refreshElement(idSelected, customer.get(comboBoxCustomer.getSelectedIndex()).getId(), car.get(comboBoxCar.getSelectedIndex()).getId(), textFieldDate.getText(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					Logger.getLogger(PurchaseForm.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		});

		getContentPane().add(buttonSave);
		
		buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(217, 179, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		getContentPane().add(buttonCancel);
	}

}
