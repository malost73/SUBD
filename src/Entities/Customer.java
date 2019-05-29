package Entities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Customer {
	private int id;
	private String surname;
	private String name;
	private String patronymic;
	
	public Customer(int id, String surname, String name,String patronymic){
		this.id = id;
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
	}
	
	public Customer(String surname, String name,String patronymic){
		this.surname = surname;
		this.name = name;
		this.patronymic = patronymic;
	}
	
	public Customer(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public String getName(){
		return name;
	}
	public String getPatronymic(){
		return patronymic;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPatronymic(String patronymic){
		this.patronymic = patronymic;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public Vector <Object> setData(Connection connection) throws SQLException{
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(surname);
		data.add(name);
		data.add(patronymic);
		return data;
	}
	
	public void addElement(String surname, String name,String patronymic, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("insert into customer values( nextval('seq_customer'), '" + surname + "', '" + name + "', '" + patronymic + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from customer where customer_id = " + id + ";");
	}
	
	public void refreshElement(int id,  String surname, String name,String patronymic, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("update customer set surname = '" + surname + "', name = '" + name + "', patronymic = '" + patronymic + "' where customer_id = " + id + ";");
	}
	
	public DefaultTableModel TableModel(Connection connection) throws SQLException {
		Vector<String> columnNames = null;
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		DefaultTableModel tableModel = new DefaultTableModel();
		columnNames = getTitles();
		for (int i = 0; i <= getTable(connection).size() - 1; i++) {
			data.add(getTable(connection).get(i).setData(connection));
		}
		tableModel.setDataVector(data, columnNames);
		return tableModel;
	}

	public Vector<String> getTitles() {
		Vector<String> columnNames = new Vector<String>();
		columnNames.add("id");
		columnNames.add("Фамилия");
		columnNames.add("Имя");
		columnNames.add("Отчество");
		return columnNames;
	}

	public ArrayList<Customer> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from customer;");
		ArrayList<Customer> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Customer((int) rs.getObject(1), rs.getObject(2).toString(), rs.getObject(3).toString(),rs.getObject(4).toString()));
		}
		return res;
	}



}
