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
   
        return null;

    }
    
}
