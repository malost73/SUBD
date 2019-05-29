package form;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;

import Entities.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CustomerForm extends JFrame {
	
	public int idSelected;
	private Connection connection = null;

	private JPanel panel;
	private JTextField textFieldName;
	private JTextField textFieldSurname;
	private JTextField textFieldPatronymic;

	/**
	 * Launch the application.
	 * @wbp.parser.constructor
	 */
	public CustomerForm(Connection connection) {
		initialize();
		this.idSelected = -1;
		this.connection = connection;
	}
	
	public CustomerForm(int id, Connection connection)throws SQLException  {
		initialize();
		this.idSelected = id;
		this.connection = connection;
		Customer cu = new Customer();
		ArrayList<Customer> customer = new ArrayList<>(cu.getTable(connection));
		cu = null;
		for (int i = 0; i < customer.size(); i++) {
			if (id == customer.get(i).getId()) {
				cu = customer.get(i);
				break;
			}
		}
		textFieldSurname.setText(cu.getSurname());
		textFieldName.setText(cu.getName());
		textFieldPatronymic.setText(cu.getPatronymic());

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
		
		JButton buttonSave = new JButton("Сохранить");
		buttonSave.setBounds(66, 171, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Customer customer = null;
				if (textFieldSurname.getText().length() == 0 && textFieldName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					customer = new Customer(textFieldSurname.getText(), textFieldName.getText(), textFieldPatronymic.getText());
					if (idSelected < 0) {
						customer.addElement(textFieldSurname.getText(), textFieldName.getText(), textFieldPatronymic.getText(), connection);
					} else {
						customer.refreshElement(idSelected, textFieldSurname.getText(), textFieldName.getText(), textFieldPatronymic.getText(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		panel.add(buttonSave);
		
		JButton buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(221, 171, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		panel.add(buttonCancel);
		
		JLabel label = new JLabel("Фамилия");
		label.setBounds(40, 49, 56, 16);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Имя");
		label_1.setBounds(40, 84, 56, 16);
		panel.add(label_1);
		
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(143, 81, 116, 22);
		panel.add(textFieldName);
		
		JLabel label_2 = new JLabel("Отчество");
		label_2.setBounds(40, 116, 56, 16);
		panel.add(label_2);
		
		textFieldPatronymic = new JTextField();
		textFieldPatronymic.setColumns(10);
		textFieldPatronymic.setBounds(143, 113, 116, 22);
		panel.add(textFieldPatronymic);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setColumns(10);
		textFieldSurname.setBounds(143, 46, 116, 22);
		panel.add(textFieldSurname);
		
	}
}
