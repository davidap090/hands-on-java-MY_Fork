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

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return connection;
  }

  // getCustomer method to query the database for the customer information
  public static Customer getCustomer(String username) {
    String sql = "Select * from customers where username = ?";
    // Create a customer object to initialize w/ database data later
    Customer customer = null;

    // structure to make the call to connect from the getCustomer method
    try (Connection connection = connect();
    //create a prepared statement object     
    PreparedStatement statement = connection.prepareStatement(sql)) {

      //executes the sql query using the sql statement and returns the resultset
      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        //initialize the previously made customer object w/ the parameters from the database
        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_Id"));
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }
    return customer;
  }

  // getAccount Method to query the database for Account info
  public static Account getAccount(int account_Id){
    String sql = "select * from accounts where id = ?";
   //create an account object to initialize w/ data from the database later
    Account account = null;
  try(Connection connection = connect();
  //use a prepared statement to execute the sql query for the account_Id
  PreparedStatement statement = connection.prepareStatement(sql)){
    statement.setInt(1, account_Id);
    try(ResultSet resultSet = statement.executeQuery()){
     
      //initialize the previously created object with database data
      account = new Account(
            resultSet.getInt("id"),
            resultSet.getString("type"),
            resultSet.getDouble("balance")); }
  }catch(SQLException e){
    e.printStackTrace();
  }  
  return account;
}
  

  public static void main(String[] args) {
    Customer customer = getCustomer("dwhittingtoncz@mozilla.org");
    Account account = getAccount(customer.getAccountId());
    System.out.println(account.getBalance());
  }
}
