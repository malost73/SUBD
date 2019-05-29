package form;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class FormNewConnection extends JDialog{

	private JFrame frame;
	private JTextField textFieldPassword;
	private JTextField textFieldLogin;
	private JTextField textFieldDBName;
	private JTextField textFieldHost;
	private Connection connection = null;
	private JTextField textFieldPort;
	
	public FormNewConnection(JFrame frame) {
		super(frame, true);
		connect();
		setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void connect() {
		this.setBounds(100, 100, 450, 286);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		
		JLabel label = new JLabel("Хост");
		label.setBounds(12, 50, 56, 16);
		this.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Имя БД");
		label_1.setBounds(12, 120, 56, 16);
		this.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("Логин");
		label_2.setBounds(12, 155, 56, 16);
		this.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("Пароль");
		label_3.setBounds(12, 184, 56, 16);
		this.getContentPane().add(label_3);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setText("0808");
		textFieldPassword.setBounds(80, 181, 116, 22);
		this.getContentPane().add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		textFieldLogin = new JTextField();
		textFieldLogin.setText("postgres");
		textFieldLogin.setBounds(80, 152, 116, 22);
		this.getContentPane().add(textFieldLogin);
		textFieldLogin.setColumns(10);
		
		textFieldDBName = new JTextField();
		textFieldDBName.setText("new_postgres");
		textFieldDBName.setBounds(80, 120, 116, 22);
		this.getContentPane().add(textFieldDBName);
		textFieldDBName.setColumns(10);
		
		textFieldHost = new JTextField();
		textFieldHost.setText("localhost");
		textFieldHost.setBounds(80, 47, 116, 22);
		this.getContentPane().add(textFieldHost);
		textFieldHost.setColumns(10);
		
		JButton buttonConnect = new JButton("Подключение");
		buttonConnect.setBounds(239, 175, 132, 25);
		buttonConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JOptionPane.showMessageDialog(
							null,
							Connect(textFieldHost.getText(),
									textFieldPort.getText(),
									textFieldDBName.getText(),
									textFieldLogin.getText(),
									textFieldPassword.getText()));
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		this.getContentPane().add(buttonConnect);
		
		textFieldPort = new JTextField();
		textFieldPort.setText("5432");
		textFieldPort.setBounds(80, 85, 116, 22);
		this.getContentPane().add(textFieldPort);
		textFieldPort.setColumns(10);
		
		JLabel label_5 = new JLabel("Порт");
		label_5.setBounds(12, 88, 56, 16);
		this.getContentPane().add(label_5);
		
		JButton buttonCancel = new JButton("Отмена");
		buttonCancel.setBounds(239, 201, 132, 25);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		this.getContentPane().add(buttonCancel);
		
		
	}
	public String Connect(String host, String port, String bd, String user,
			String password) {
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://" + host + ":" + port + "/" + bd;
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			if (connection != null) {
				return "Вы успешно подключились к БД";
			} else {
				return "Ошибка при подключении к БД";
			}
		} catch (ClassNotFoundException ex) {
			return "Драйвер не найден";
		} catch (SQLException e) {
			return "Ошибка подключения";
		}
	}

	public void Disconnect() {
		try {
			connection.close();
			JOptionPane.showMessageDialog(null, "Соеденение закрыто");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ошибка");
		}
	}

	public Connection getConnection() {
		return connection;
	}

}
