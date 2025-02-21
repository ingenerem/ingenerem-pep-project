package Controller;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.AccountService;
import Service.MessageService;
import Model.Account;
import Model.Message;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     AccountService accountService;
     MessageService messageService;


    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();

    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::registerUserHandler);
        app.post("login", this::LoginUserHandler);
        app.post("messages",this::createMessage);
        app.get("messages",this::retrieveAllMessages);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");

    }

    private void registerUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper oMapper = new ObjectMapper();
        Account newUser = oMapper.readValue(ctx.body(), Account.class);
        Account account = accountService.register(newUser);
        if(account == null){
            ctx.status(400);
        }
        else{
            ctx.status(200);
            ctx.json(oMapper.writeValueAsString(account));
        }
    }

    private void LoginUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper oMapper = new ObjectMapper();
        Account newUser = oMapper.readValue(ctx.body(), Account.class);
        Account account = accountService.login(newUser);
        if(account == null){
            ctx.status(401);
        }
        else{
            ctx.status(200);
            ctx.json(oMapper.writeValueAsString(account));
        }
    }

    private void createMessage(Context ctx) throws JsonProcessingException{
        ObjectMapper oMapper = new ObjectMapper();
        Message message = oMapper.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if(newMessage == null){
            ctx.status(400);
        }
        else{
            ctx.json(oMapper.writeValueAsString(newMessage));
        }
    }

    private void retrieveAllMessages(Context ctx) throws JsonProcessingException{
        ObjectMapper oMapper = new ObjectMapper();
        ArrayList<Message> messages = messageService.retrieveAllMessages();
        ctx.json(oMapper.writeValueAsString(messages));
    }


}