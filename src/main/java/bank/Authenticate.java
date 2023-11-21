package bank;

import javax.security.auth.login.LoginException;

public class Authenticate {

  // Method to login. Requires username and password.
  // Returns a customer object to represent the user.
  // Throws login exception if a valid customer object is not created
  public static Customer login(String username, String password) throws LoginException {
    Customer customer = DataSource.getCustomer(username);

    // Verify a valid customer object is created
    if (customer == null) {
      throw new LoginException("Username not found");
    }

    // Verify password
    // .equals verifys the value of the object
    // == compares the memory locations
    if (password.equals(customer.getPassword())) {
      customer.setAuthenticated(true);
      return customer;
    }

    else
      throw new LoginException("Incorrect password");
  }

  // Method to Log out. Requires a customer object.
  public static void logout(Customer customer) {
    customer.setAuthenticated(false);
  }
}
