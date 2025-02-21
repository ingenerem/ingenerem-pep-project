package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;

public class MessageDao {
    
    public Message insertMessage(Message msg){

        Connection conn = ConnectionUtil.getConnection();

        try{

            String sql = "insert into Message (posted_by,message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement pStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, msg.getPosted_by());
            pStatement.setString(2, msg.getMessage_text());
            pStatement.setLong(3, msg.time_posted_epoch);

            pStatement.executeUpdate();
            ResultSet res = pStatement.getGeneratedKeys();

            if(res.next()){
                int message_id = res.getInt(1);
                msg.setMessage_id(message_id);
                return msg;
            }

        }
        catch(SQLException ex){
            System.out.println(ex);
        }

        return null;
    }

    public ArrayList<Message> retrieveAllMessages(){
        Connection conn = ConnectionUtil.getConnection();
        ArrayList<Message> msgList = new ArrayList<>();

        try{

            String sql = "select * from Message";

            PreparedStatement pStatement = conn.prepareStatement(sql);
            ResultSet rSet = pStatement.executeQuery();

            while(rSet.next()){
                Message newMsg = new Message(rSet.getInt(1), rSet.getInt(2), 
                rSet.getString(3), rSet.getLong(4));
                msgList.add(newMsg);

            }

        }

        catch(SQLException ex){
            System.out.println(ex);
        }

        return msgList;
    
    }
}
