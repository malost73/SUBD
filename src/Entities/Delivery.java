package Entities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Delivery {
	private int id;
	private String date;
	private int carid;
	private int providerid;

	
	public Delivery(int id,  int providerid, int carid, String date ){
		this.id = id;
		
		this.providerid = providerid;
		this.carid = carid;
		this.date = date;
	}
	
	public Delivery( int providerid, int carid, String date){
		
		this.providerid = providerid;
		this.carid = carid;
		this.date = date;
	}
	
	public Delivery(){
		
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
	
	public int getProviderid(){
		return providerid;
	}
	
	public void setProviderid(int providerid){
		this.providerid = providerid;
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
		ResultSet rs = statement.executeQuery("select surname from provider where provider_id = " + providerid + ";");
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

	public void addElement( int providerid, int carid,String date, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("insert into delivery values(nextval('seq_delivery')," + providerid + ", " + carid + ", '" + date  + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from delivery where delivery_id = " + id + ";");
	}

	public void refreshElement(int id, int providerid, int carid, String date,  Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("update delivery set provider_id = " + providerid + ", car_id = " + carid + ", date = '" + date + "' where delivery_id = " + id + ";");
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
		columnNames.add("Поставщик");
		columnNames.add("Автомобиль");
		columnNames.add("Дата");
		return columnNames;
	}

	public ArrayList<Delivery> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from delivery;");
		ArrayList<Delivery> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Delivery((int) rs.getObject(1), (int)rs.getObject(2), (int)rs.getObject(3), rs.getObject(4).toString()));
		}
		return res;
	}
	
}
