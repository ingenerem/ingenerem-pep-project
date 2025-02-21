package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDao {

    public Account insertUser(Account acc){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "insert into Account (username, password) values (?,?)";
            PreparedStatement pStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, acc.getUsername());
            pStatement.setString(2, acc.getPassword());
            
           
           pStatement.executeLargeUpdate();
            
            ResultSet rSet = pStatement.getGeneratedKeys();
            if(rSet.next()){
                int id = rSet.getInt(1);
                acc.setAccount_id(id);
            }
            return acc;

        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return null;

    }

    public Account geAccount(Account acc){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "select account_id, username, password from Account where username = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, acc.getUsername());
           
           ResultSet res = pStatement.executeQuery();

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
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return null;

    }

    public Boolean isValidUser(int account_id) {

        Connection conn = ConnectionUtil.getConnection();

        try{
            String sql = "select account_id from Account where account_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, account_id);

            ResultSet res = pStatement.executeQuery();

            if(res.next()){
                return true;
            }

            
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
        
    }
    
}
