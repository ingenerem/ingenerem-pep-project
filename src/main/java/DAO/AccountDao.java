package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

/**
 * This DAO class handles database operation for Account table
 */
public class AccountDao {

    /**
     * A method to insert a new user account into the database
     * @param acc Account object
     * @return Inserted account, null if failed insertion
     */
    public Account insertUser(Account acc){

        //Get the database connection
        Connection conn = ConnectionUtil.getConnection();
        try{
            // Insert the provided account into the database
            String sql = "insert into Account (username, password) values (?,?)";
            PreparedStatement pStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, acc.getUsername());
            pStatement.setString(2, acc.getPassword());
            
           
           pStatement.executeLargeUpdate();
            //Process the query results
            ResultSet rSet = pStatement.getGeneratedKeys();
            if(rSet.next()){
                int id = rSet.getInt(1);
                acc.setAccount_id(id);
            }
            return acc;

        }
        //Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return null;

    }

    /**
     * A method to retrieve the given user account from the database
     * @param acc Account object
     * @return Retrieved account, null if account not found
     */
    public Account geAccount(Account acc){

        //Get the database connection
        Connection conn = ConnectionUtil.getConnection();
        try{
            //Retrieve the specified account from the database
            String sql = "select account_id, username, password from Account where username = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, acc.getUsername());
           
           ResultSet res = pStatement.executeQuery();

           //Process the data returned from the database
           while(res.next()){
            int account_id = res.getInt(1);
            String userName = res.getString(2);
            String password = res.getString(3);
            if(userName.equals(acc.username) && password.equals(acc.getPassword())){
                acc.setAccount_id(account_id);
                return acc;
            }
           }
        }
        //Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return null;

    }

    /**
     * A method to check if the user account with given ID exists in the database
     * @param account_id User account ID
     * @return true if the user was found, false otherwise
     */
    public Boolean isValidUser(int account_id) {

        //Get the database connection
        Connection conn = ConnectionUtil.getConnection();

        try{
            //Check if an account with the specified ID exists in the database
            String sql = "select account_id from Account where account_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, account_id);

            ResultSet res = pStatement.executeQuery();

            //Process the query results
            if(res.next()){
                return true;
            }
        }
        //Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
        
    }
    
    
}
