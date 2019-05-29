package form;

import java.awt.EventQueue;

import javafx.scene.control.ComboBox;

import javax.swing.JFrame;
import javax.swing.JPanel;
import form.FormInterface;
import Entities.EntitiesInterface;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;



import com.sun.glass.events.WindowEvent;

import Entities.Customer;
import Entities.Purchase;
import Entities.Provider;
import Entities.Car;
import Entities.Delivery;
import form.FormNewConnection;


public class MainForm {
	JComboBox ComboBox;
	private JFrame frame;
	private JTable table;
	Connection connection = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		String[] str = { "Заказчик", "Покупка", "Автомобиль", "Поставка", "Поставщик" };
		frame = new JFrame();
		frame.setBounds(100, 100, 679, 312);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		

	
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuConnection = new JMenu("Подлючение");
		menuBar.add(menuConnection);
		
		JMenuItem menuItemNew = new JMenuItem("Новое подключение");
		menuItemNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormNewConnection form = new FormNewConnection(frame);
				connection = form.getConnection();
				if (connection != null) {
					
				}
			}
		});
		menuConnection.add(menuItemNew);
		
		
		JMenuItem menuItemShutdown = new JMenuItem("Отключение");
		menuItemShutdown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Disconnect();
				for( int i=0; i< table.getRowCount(); i++){
					
				}
			}
		});
		
		menuConnection.add(menuItemShutdown);
		
		JMenuItem menuItemExit = new JMenuItem("Выход");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
			}
		});
		menuConnection.add(menuItemExit);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 661, 250);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(12, 13, 523, 218);
		panel.add(table);
		
		ComboBox = new JComboBox(str);
		ComboBox.setBounds(547, 9, 102, 22);
		ComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		panel.add(ComboBox);
		
		
		JButton buttonAdd = new JButton("Добавить");
		buttonAdd.setBounds(547, 61, 97, 25);
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (connection != null) {
					EntitiesInterface entitiesInterface;
					FormInterface formInterface;
					switch ((String) ComboBox.getSelectedItem()) {
					case "Заказчик":
						try {
							CustomerForm form1 = new CustomerForm(connection);
							form1.setVisible(true);
							Customer cu =  new Customer();
							table.setModel(cu.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Покупка":
						try {
							PurchaseForm form2 = new PurchaseForm(connection);
							form2.setVisible(true);
							Purchase or = new Purchase();
							table.setModel(or.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Поставщик":
						try {
							ProviderForm form3 = new ProviderForm(connection);
							form3.setVisible(true);
							Provider p = new Provider();
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Автомобиль":
						try {
							formInterface = new CarForm(connection);
							formInterface.setVisible(true);
							entitiesInterface = new Car();
							table.setModel(entitiesInterface.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Поставка":
						try {
							DeliveryForm form5 = new DeliveryForm(connection);
							form5.setVisible(true);
							Delivery d = new Delivery();
							table.setModel(d.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					}
				}
			}
		});
		panel.add(buttonAdd);
		
		JButton buttonDel = new JButton("Удалить");
		buttonDel.setBounds(547, 99, 97, 25);
		buttonDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0 && connection != null) {
					int idEl = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					switch ((String) ComboBox.getSelectedItem()) {
					case "Заказчик":
						try {
							Customer cu = new Customer();
							cu.removeElement(idEl, connection);
							table.setModel(cu.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Покупка":
						try {
							Purchase pu = new Purchase();
							pu.removeElement(idEl, connection);
							table.setModel(pu.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Поставщик":
						try {
							Provider p = new Provider();
							p.removeElement(idEl, connection);
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Автомобиль":
						try {
							Car c = new Car();
							c.removeElement(idEl, connection);
							table.setModel(c.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Поставка":
						try {
							Delivery d = new Delivery();
							d.removeElement(idEl, connection);
							table.setModel(d.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					}
				} else if (connection != null) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
			}
		});
		panel.add(buttonDel);
		
		JButton buttonCh = new JButton("Изменить");
		buttonCh.setBounds(547, 137, 97, 25);
		buttonCh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() >= 0 && connection != null) {
					int idEl = (table.getSelectedRow());
					switch ((String) ComboBox.getSelectedItem()) {
					case "Заказчик":
						try {
							Customer cu = new Customer();
							int i = cu.getTable(connection).get(idEl).getId();
							CustomerForm form1 = new CustomerForm(i, connection);
							form1.setVisible(true);
							Customer c = new Customer();
							table.setModel(c.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Покупка":
						try {
							Purchase pu = new Purchase();
							int i = pu.getTable(connection).get(idEl).getId();
							PurchaseForm form2 = new PurchaseForm(i, connection);
							form2.setVisible(true);
							Purchase p = new Purchase();
							table.setModel(p.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Поставщик":
						try {
							Provider p = new Provider();
							int i = p.getTable(connection).get(idEl).getId();
							ProviderForm form3 = new ProviderForm(i, connection);
							form3.setVisible(true);
							Provider pr = new Provider();
							table.setModel(pr.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Автомобиль":
						try {
							Car c = new Car();
							int i = c.getTable(connection).get(idEl).getId();
							CarForm form4 = new CarForm(i, connection);
							form4.setVisible(true);
							Car car = new Car();
							table.setModel(car.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					case "Поставка":
						try {
							Delivery d = new Delivery();
							int i = d.getTable(connection).get(idEl).getId();
							DeliveryForm form5 = new DeliveryForm(i, connection);
							form5.setVisible(true);
							Delivery de = new Delivery();
							table.setModel(de.TableModel(connection));
						} catch (SQLException ex) {
							Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
						}
						break;
					}
				} else if (connection != null) {
					JOptionPane.showMessageDialog(null, "Ошибка");
				}
			}
		});
		
		panel.add(buttonCh);
		JButton buttonUpd = new JButton("Обновить");
		buttonUpd.setBounds(547, 175, 97, 25);
		buttonUpd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		
		panel.add(buttonUpd);
		}
		private void Disconnect() {
			if (connection != null) {
				connection = null;
				JOptionPane.showMessageDialog(null, "Соединение закрыто");
			}
		}

		private void refresh() {
			if (connection != null) {
				switch ((String) ComboBox.getSelectedItem()) {
				case "Заказчик":
					
					try {
						Customer cu = new Customer();
						table.setModel(cu.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				case "Покупка":
					try {
						Purchase pu = new Purchase();
						table.setModel(pu.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				case "Поставщик":
					try {
						Provider p = new Provider();
						table.setModel(p.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				case "Автомобиль":
					try {
						Car c = new Car();
						table.setModel(c.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				case "Поставка":
					try {
						Delivery d = new Delivery();
						table.setModel(d.TableModel(connection));
					} catch (SQLException ex) {
						Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
					}
					break;
				}
			}
			
	}
}
