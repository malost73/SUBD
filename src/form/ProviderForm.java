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
import Entities.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProviderForm extends JFrame {
	
	public int idSelected;
	private Connection connection = null;

	private JPanel panel;
	private JTextField textFieldSurname;
	private JTextField textFieldName;
	private JTextField textFieldPatronymic;
	private JTextField textFieldCompany;
	private JLabel label_4;

	/**
	 * Launch the application.
	 */
	public ProviderForm(Connection connection) {
		initialize();
		this.idSelected = -1;
		this.connection = connection;
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ProviderForm(int id, Connection connection)throws SQLException  {
		initialize();
		this.idSelected = id;
		this.connection = connection;
		Provider p = new Provider();
		ArrayList<Provider> provider = new ArrayList<>(p.getTable(connection));
		p = null;
		for (int i = 0; i < provider.size(); i++) {
			if (id == provider.get(i).getId()) {
				p = provider.get(i);
				break;
			}
		}
		textFieldCompany.setText(p.getCompany());
		
		textFieldSurname.setText(p.getSurname());
		textFieldName.setText(p.getName());
		textFieldPatronymic.setText(p.getPatronymic());
		
		

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
		buttonSave.setBounds(55, 171, 97, 25);
		buttonSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Provider provider = null;
				if (textFieldName.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
				try {
					provider = new Provider(textFieldCompany.getText(), textFieldSurname.getText(), textFieldName.getText(), textFieldPatronymic.getText() );
					if (idSelected < 0) {
						provider.addElement(textFieldCompany.getText(), textFieldSurname.getText(), textFieldName.getText(), textFieldPatronymic.getText(), connection);
					} else {
						provider.refreshElement(idSelected,textFieldCompany.getText(), textFieldSurname.getText(), textFieldName.getText(), textFieldPatronymic.getText(), connection);
					}
					setVisible(false);
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});

		panel.add(buttonSave);
		
		JButton buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(232, 171, 97, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		panel.add(buttonCancel);
		
		JLabel label_2 = new JLabel("Компания");
		label_2.setBounds(22, 31, 84, 16);
		panel.add(label_2);
		JLabel label = new JLabel("Фамилия");
		label.setBounds(22, 63, 84, 16);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Имя");
		label_1.setBounds(22, 98, 56, 16);
		panel.add(label_1);
		
		JLabel label_4;
		label_4 = new JLabel("Отчество");
		label_4.setBounds(22, 130, 56, 16);
		panel.add(label_4);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setColumns(10);
		textFieldSurname.setBounds(143, 60, 160, 22);
		panel.add(textFieldSurname);
		
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(143, 95, 160, 22);
		panel.add(textFieldName);
		
		textFieldPatronymic = new JTextField();
		textFieldPatronymic.setColumns(10);
		textFieldPatronymic.setBounds(143, 127, 160, 22);
		panel.add(textFieldPatronymic);
		
		textFieldCompany = new JTextField();
		textFieldCompany.setColumns(10);
		textFieldCompany.setBounds(143, 28, 160, 22);
		panel.add(textFieldCompany);
	}
}
