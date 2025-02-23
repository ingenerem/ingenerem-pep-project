package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;

/**
 * This DAO class handles database operation for Message table
 */
public class MessageDao {
    
    /**
     * A method to insert a new message into the database
     * @param msg The message to be inserted into the database
     * @return The message that got inserted into the database, or null if failed
     */
    public Message insertMessage(Message msg){

        //Get the database connection
        Connection conn = ConnectionUtil.getConnection();

        try{

            //Insert the given message into the database
            String sql = "insert into Message (posted_by,message_text, time_posted_epoch) values (?,?,?)";
            PreparedStatement pStatement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, msg.getPosted_by());
            pStatement.setString(2, msg.getMessage_text());
            pStatement.setLong(3, msg.time_posted_epoch);

            pStatement.executeUpdate();
            ResultSet res = pStatement.getGeneratedKeys();

            //Process the query results
            if(res.next()){
                int message_id = res.getInt(1);
                msg.setMessage_id(message_id);
                return msg;
            }

        }

        ///Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex);
        }

        return null;
    }

    /**
     * A method to retrieve all available messages from the databasa
     * @return A list containing all messages retrieved from the database
     */
    public ArrayList<Message> retrieveAllMessages(){

        //Get the database connection
        Connection conn = ConnectionUtil.getConnection();

        //An List to store the messages
        ArrayList<Message> msgList = new ArrayList<>();

        try{

            //Retrieve all messages from the database
            String sql = "select * from Message";

            PreparedStatement pStatement = conn.prepareStatement(sql);
            ResultSet rSet = pStatement.executeQuery();

            //Process the data returned from the database
            while(rSet.next()){
                Message newMsg = new Message(rSet.getInt(1), rSet.getInt(2), 
                rSet.getString(3), rSet.getLong(4));
                msgList.add(newMsg);

            }

        }

        //Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex);
        }

        return msgList;
    
    }


    /**
     * A method to retrieve a message given its ID
     * @param message_id The message_id for the record to be retrieved
     * @return The retrieved message, or null if record not found
     */    
    public Message retrieVeMessageByID(int message_id){

        //Get the database connection
        Connection conn = ConnectionUtil.getConnection();

        try{
            //Retrieve a message given its ID
            String sql = "select posted_by, message_text, time_posted_epoch from Message where message_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1,message_id);

            ResultSet res = pStatement.executeQuery();

            //Process data returned from the database
            if(res.next()){

                Message message = new Message(message_id, res.getInt(1), 
                res.getString(2), res.getLong(3));
                return message;

            }

        }
        //Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex);
        }
     

        return null;
    }

    /**
     * A method to retrieve all messages posted by a given user
     * @param user_id The account_id for the user
     * @return A list of messages posted by the given user, or null if none found
     */
    public ArrayList<Message> retrieveAllMessagesForUser(int user_id){

        //Create the list to store retrieved data
        ArrayList<Message> msgList = new ArrayList<>();

        //Get the database connection
        Connection conn = ConnectionUtil.getConnection();
        try{
            //Retrieve all messages given the posted_by parameter
            String sql = "select * from Message where posted_by = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, user_id);

            ResultSet res = pStatement.executeQuery();

            //Process data returned from the database
            while(res.next()){
                Message msg = new Message(res.getInt(1), res.getInt(2),
                res.getString(3), res.getLong(4));
                msgList.add(msg);
            }

        }
        //Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex);
        }
        return msgList;
    }


    /**
     * A method to delete a specific message
     * @param message_id The message ID for the message to be deleted
     * @return true if the message was deleted and false otherwise
     */
    public Boolean deleteMessageByMessageID(int message_id){

        //Get the database connection
        Connection conn = ConnectionUtil.getConnection();
        try{
            //Delete a message from the database given the message_id
            String sql = " delete from Message where message_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, message_id);

            int res = pStatement.executeUpdate();

            //Process the query results
            if(res >0){
                return true;
            }

        }
        //Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex);
        }

        return false;

    }


    /**
     * A method to update a specific message text for a specific message ID
     * @param message_id The message ID for the message to be updated
     * @param text New message text
     * @return true if the message was updated and false otherwise
     */
    public boolean updateMessageText(int message_id, String text){

         //Get the database connection
        Connection conn = ConnectionUtil.getConnection();
        try{
            //Update the message_text in the database given the message_id
            String sql = " update Message set message_text = ? where message_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, text);
            pStatement.setInt(2, message_id);

            //Process the query results
            int res = pStatement.executeUpdate();
            if(res >0){
                return true;
            }

        }
        //Handle SQL exeption
        catch(SQLException ex){
            System.out.println(ex);
        }

        return false;
    }
}
