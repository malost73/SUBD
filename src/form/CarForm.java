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

import Entities.Car;
import Entities.Provider;

public class CarForm extends JFrame implements FormInterface{	
	public int idSelected;
	private Connection connection = null;

	private JPanel panel;

	private JTextField textFieldVIN;
	private JTextField textFieldBrand;
	private JTextField textFieldModel;
	private JTextField textFieldCreateYear;
	private JTextField textFieldMileage;

	/**
	 * Launch the application.
	 * @wbp.parser.constructor
	 */
	public CarForm(Connection connection) {
		initialize();
		this.idSelected = -1;
		this.connection = connection;
	}
	
	public CarForm(int id, Connection connection)throws SQLException  {
		initialize();
		this.idSelected = id;
		this.connection = connection;
		Car c = new Car();
		ArrayList<Car> car = new ArrayList<>(c.getTable(connection));
		c = null;
		for (int i = 0; i < car.size(); i++) {
			if (id == car.get(i).getId()) {
				c = car.get(i);
				break;
			}
		}
		textFieldVIN.setText(c.getVIN());
		textFieldBrand.setText(c.getBrand());
		textFieldModel.setText(c.getModel());
		textFieldCreateYear.setText(c.getCreateYear());
		textFieldMileage.setText(c.getMileage());

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
		
		JLabel lblVin = new JLabel("VIN");
		lblVin.setBounds(25, 13, 118, 16);
		getContentPane().add(lblVin);
		
		JLabel label_1 = new JLabel("Марка");
		label_1.setBounds(25, 42, 106, 16);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Модель");
		label_2.setBounds(25, 80, 56, 16);
		getContentPane().add(label_2);
		
		textFieldVIN = new JTextField();
		textFieldVIN.setBounds(139, 10, 116, 22);
		getContentPane().add(textFieldVIN);
		textFieldVIN.setColumns(10);
		
		textFieldBrand = new JTextField();
		textFieldBrand.setBounds(139, 42, 116, 22);
		getContentPane().add(textFieldBrand);
		textFieldBrand.setColumns(10);
		
		textFieldModel = new JTextField();
		textFieldModel.setBounds(139, 77, 116, 22);
		getContentPane().add(textFieldModel);
		textFieldModel.setColumns(10);
		
		JButton buttonSave = new JButton("Сохранить");
		buttonSave.setBounds(34, 186, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Car car = null;
				if (textFieldVIN.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка. Заполните информацию об автомобиле");
				}
				try {
					car = new Car(textFieldVIN.getText(), textFieldBrand.getText(), textFieldModel.getText(), textFieldCreateYear.getText(), textFieldMileage.getText());
					if (idSelected < 0) {
						car.addElement(textFieldVIN.getText(), textFieldBrand.getText(), textFieldModel.getText(), textFieldCreateYear.getText(), textFieldMileage.getText(), connection);
					} else {
						car.refreshElement(idSelected,textFieldVIN.getText(), textFieldBrand.getText(), textFieldModel.getText(), textFieldCreateYear.getText(), textFieldMileage.getText(), connection);
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
		
		JLabel label_3 = new JLabel("Год выпуска");
		label_3.setBounds(25, 115, 118, 16);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("Пробег");
		label_4.setBounds(25, 150, 106, 16);
		panel.add(label_4);
		
		textFieldCreateYear = new JTextField();
		textFieldCreateYear.setColumns(10);
		textFieldCreateYear.setBounds(139, 112, 116, 22);
		panel.add(textFieldCreateYear);
		
		textFieldMileage = new JTextField();
		textFieldMileage.setColumns(10);
		textFieldMileage.setBounds(139, 147, 116, 22);
		panel.add(textFieldMileage);
		
		
		
		
	}

}
