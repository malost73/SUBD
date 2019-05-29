package Entities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Purchase {
	private int id;
	private String date;
	private int customerid;
	private int carid;
	
	public Purchase(int id, int customerid, int carid, String date){
		this.id = id;
		
		this.customerid = customerid;
		this.carid = carid;
		this.date = date;
	}
	
	public Purchase(int customerid, int carid, String date){
		
		this.customerid = customerid;
		this.carid = carid;
		this.date = date;
	}
	
	public Purchase(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public int getCustomerid(){
		return customerid;
	}
	
	public void setCustomerid(int customerid){
		this.customerid = customerid;
	}
	
	public int getCarid(){
		return carid;
	}
	
	public void setCarid(int carid){
		this.carid = carid;
	}
		
	public Vector<Object> setData(Connection connection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select surname from customer where customer_id = " + customerid + ";");
		while (rs.next()) {
			data.add(rs.getString("surname"));		
		}
		rs = statement.executeQuery("select model from car where car_id = " + carid + ";");
		while (rs.next()) {
			data.add(rs.getString("model"));
		}

		data.add(date);
		return data;

	}

	public void addElement(int customerid, int carid, String date,  Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("insert into purchase values(nextval('seq_purchase')," + customerid + ", " + carid +  ", '" + date + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("delete from purchase where purchase_id = " + id + ";");
	}

	public void refreshElement(int id, int customerid, int carid, String date, Connection connection) throws SQLException {		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("update purchase set customer_id = " + customerid + ", car_id = " + carid + ", date = '" + date + "' where purchase_id = " + id + ";");
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
		columnNames.add("Заказчик");
		columnNames.add("Автомобиль");
		columnNames.add("Дата");
		return columnNames;
	}

	public ArrayList<Purchase> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from purchase;");
		ArrayList<Purchase> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Purchase((int) rs.getObject(1), (int)rs.getObject(2), (int) rs.getObject(3), rs.getObject(4).toString()));
		}
		return res;
	}


	
}
