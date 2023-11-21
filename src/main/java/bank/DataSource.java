package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
 //connect to the database
  public static Connection connect() {
    //create a String (db_file) and make it equal to the path to the database file
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    //structure to connect to the database using the DriverManager
    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("we're connected");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  // method to query the database for the customer information
  public static Customer getCustomer(String username) {
    String sql = "Select * from customers where username = ?";
    Customer customer = null;

    // structure to make the call to connect from the getCustomer method
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_Id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customer;
  }

  public static void main(String[] args) {
    Customer customer = getCustomer("dwhittingtoncz@mozilla.org");
    System.out.println(customer.getName());
  }
}
