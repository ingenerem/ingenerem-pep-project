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

    
}