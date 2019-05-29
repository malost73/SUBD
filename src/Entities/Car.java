package Entities;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Car implements EntitiesInterface{
	private int id;
	private String vin;
	private String brand;
	private String model;
	private String create_year;
	private String mileage;
	
	public Car(int id, String vin, String brand, String model, String  create_year, String mileage){
		this.id = id;
		this.vin = vin;
		this.brand = brand;
		this.model = model;
		this.create_year = create_year;
		this.mileage = mileage;
	}
	
	public Car(String vin, String brand, String model, String  create_year, String mileage){
		this.vin = vin;
		this.brand = brand;
		this.model = model;
		this.create_year = create_year;
		this.mileage = mileage;
	}
	
	public Car(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getVIN(){
		return vin;
	}
	
	public void setVIN(String vin){
		this.vin = vin;
	}
	
	public String getCreateYear(){
		return create_year;
	}
	
	public void setCreateYear(String create_year){
		this.create_year = create_year;
	}
	
	public String getMileage(){
		return mileage;
	}
	
	public void setMileage(String mileage){
		this.mileage = mileage;
	}
	
	public String getBrand(){
		return brand;
	}
	
	public void setBrand(String brand){
		this.brand = brand;
	}
	
	public String getModel(){
		return model;
	}
	
	public void setModel(String model){
		this.model = model;
	}
	
	public Vector<Object> setData(Connection conection) throws SQLException {
		Vector<Object> data = new Vector<Object>();
		data.add(id);
		data.add(vin);
		data.add(brand);
		data.add(model);
		data.add(create_year);
		data.add(mileage);
		return data;
	}

	public void addElement(String vin, String brand, String model, String  create_year, String mileage, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("insert into car values(nextval('seq_car'),'" + vin + "', '" + brand + "', '" + model + "', '" + create_year + "', '" + mileage + "');");
	}

	public void removeElement(int id, Connection connection) throws SQLException {
		Statement statement = null;
		statement = connection.createStatement();
		statement.executeUpdate("delete from car where car_id = " + id + ";");
	}

	public void refreshElement(int id, String vin, String brand, String model, String  create_year, String mileage, Connection connection) throws SQLException {
		Statement stmt = null;
		stmt = connection.createStatement();
		stmt.executeUpdate("update car set vin = '" + vin + "', brand = '" + brand + "', model = '" + model + "', create_year = '" + create_year + "', mileage = '" + mileage + "' where car_id = " + id + ";");
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
		columnNames.add("VIN");
		columnNames.add("Марка");
		columnNames.add("Модель");
		columnNames.add("Год выпуска");
		columnNames.add("Пробег");
		return columnNames;
	}

	public ArrayList<Car> getTable(Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from car;");
		ArrayList<Car> res = new ArrayList<>();
		while (rs.next()) {
			res.add(new Car( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),  rs.getString(6)));
		}
		return res;
	}


}
