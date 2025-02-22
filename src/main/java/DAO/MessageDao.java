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


    
    public Message retrieVeMessageByID(int message_id){
        Connection conn = ConnectionUtil.getConnection();

        try{
            String sql = "select posted_by, message_text, time_posted_epoch from Message where message_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1,message_id);

            ResultSet res = pStatement.executeQuery();

            if(res.next()){

                Message message = new Message(message_id, res.getInt(1), 
                res.getString(2), res.getLong(3));
                return message;

            }

        }
        catch(SQLException ex){
            System.out.println(ex);
        }
     

        return null;
    }

    public ArrayList<Message> retrieveAllMessagesForUser(int user_id){
        ArrayList<Message> msgList = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "select * from Message where posted_by = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, user_id);

            ResultSet res = pStatement.executeQuery();

            while(res.next()){
                Message msg = new Message(res.getInt(1), res.getInt(2),
                res.getString(3), res.getLong(4));
                msgList.add(msg);
            }

        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        return msgList;
    }


    public Boolean deleteMessageByMessageID(int message_id){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = " delete from Message where message_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, message_id);

            int res = pStatement.executeUpdate();
            if(res >0){
                return true;
            }

        }
        catch(SQLException ex){
            System.out.println(ex);
        }

        return false;

    }


    public boolean updateMessageText(int message_id, String text){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = " update Message set message_text = ? where message_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, text);
            pStatement.setInt(2, message_id);

            int res = pStatement.executeUpdate();
            if(res >0){
                return true;
            }

        }
        catch(SQLException ex){
            System.out.println(ex);
        }

        return false;
    }
}
