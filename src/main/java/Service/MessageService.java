package Service;

import java.util.ArrayList;

import DAO.MessageDao;
import Model.Message;

public class MessageService {

    MessageDao messageDao;
    AccountService accountService;

    public MessageService(){
        
        messageDao = new MessageDao();
        accountService = new AccountService();
    }

    public Message createMessage(Message msg){

        if(isValidaMessage(msg.getMessage_text()) && isValidUser(msg.getPosted_by()))
           return messageDao.insertMessage(msg);
        return null;

    }

    public Boolean isValidaMessage(String text){
        return (text!=null && !text.equals("") && text.length()<=255);
    }

    private Boolean isValidUser(int posted_by){
        return accountService.isValidUser(posted_by);
    }


    public ArrayList<Message> retrieveAllMessages(){
        return messageDao.retrieveAllMessages();
      }


    public Message retrieveMessageByID(int message_id){
        return messageDao.retrieVeMessageByID(message_id);
    }


    public ArrayList<Message> retrieveAllMessagesForUser(int user_id){
        return messageDao.retrieveAllMessagesForUser(user_id);
    }

    public Message deleteMessageByMessageID(int message_id){
        Message message = retrieveMessageByID(message_id);
        if(message!= null && messageDao.deleteMessageByMessageID(message_id)){
            return message;

        }
        return null;
    }

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