package Entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.TableModel;

public interface EntitiesInterface {

	TableModel TableModel(Connection connection) throws SQLException;
}
