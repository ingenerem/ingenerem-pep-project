package Service;

import java.util.ArrayList;

import DAO.MessageDao;
import Model.Message;

/**
 * This is a Service class to handle business logic related to Message database entity
 */
public class MessageService {

    //Message DAO object
    MessageDao messageDao;

    //Account Service object
    AccountService accountService;

    //A zero parameter constructor to initialize required objects
    public MessageService(){     
        messageDao = new MessageDao();
        accountService = new AccountService();
    }

    /**
     * A method to create a new message into the database
     * @param msg The message object to create
     * @return The created message, or null if failed
     */
    public Message createMessage(Message msg){

        if(isValidaMessage(msg.getMessage_text()) && isValidUser(msg.getPosted_by()))
           return messageDao.insertMessage(msg);
        return null;

    }

    /**
     * A method to check if validated the message text
     * @param text Text to be validated
     * @return true if the text is valid and false otherwise
     */
    public Boolean isValidaMessage(String text){
        return (text!=null && !text.equals("") && text.length()<=255);
    }

    /**
     * A method to check if the poster's user_id exists in the database 
     * @param posted_by The user_id to verify
     * @return true if the user_id is found and false otherwise
     */
    private Boolean isValidUser(int posted_by){
        return accountService.isValidUser(posted_by);
    }

    /**
     * A method to retrieve all messages available in the database
     */
    public ArrayList<Message> retrieveAllMessages(){
        return messageDao.retrieveAllMessages();
      }

    /**
     * A method to retrieve a message given the message_id
     * @param message_id The message_id for the message to be retrieved
     * @return The retrieved message or null if not found
     */
    public Message retrieveMessageByID(int message_id){
        return messageDao.retrieVeMessageByID(message_id);
    }

    /**
     * A method to retrieve all messages posted by a given user
     * @param user_id The user's account_id
     * @return a list of all messages posted by the given user
     */
    public ArrayList<Message> retrieveAllMessagesForUser(int user_id){
        return messageDao.retrieveAllMessagesForUser(user_id);
    }

    /**
     * A method to delete a given message given it's message_id
     * @param message_id the message_id for the message to delete
     * @return The deleted message, or null if the message wasn't found in the database
     */
    public Message deleteMessageByMessageID(int message_id){
        Message message = retrieveMessageByID(message_id);
        if(message!= null && messageDao.deleteMessageByMessageID(message_id)){
            return message;

        }
        return null;
    }

    /**
     * A method to update a specific message text in the database
     * @param message_id the message_id of the message to be updated
     * @param text new text
     * @return the updated message or null if failed
     */
    public Message UpdateMessageText(int message_id, String text){
        Message message = null;
        if(isValidaMessage(text)){
              Boolean isUpdated = messageDao.updateMessageText(message_id, text);
           if(isUpdated){
                message = retrieveMessageByID(message_id);
                System.out.println(message);
               return message;

             }
        }
        return null;
       
    }

    
}