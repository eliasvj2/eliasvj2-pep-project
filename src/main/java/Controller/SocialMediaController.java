package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Service.MessageService;
import Model.Account;
import Model.Message;
import Service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.*;

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
    public AccountService accountService;
    public MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registration );
        app.post("/login", this::login);
        app.post("/messages", this::messageHandler);
        app.get("/messages",this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::messageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::patchMessageHandler);
        app.get("/accounts/{account_id}/messages", this::userMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    private void registration (Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount)).status(200);
        }else{
            ctx.status(400);
        }
    }

    private void login (Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.loginAccount(account);
        if(addedAccount!=null){
            ctx.json(mapper.writeValueAsString(addedAccount)).status(200);
        }else{
            ctx.status(401);
        }

    }

    private void messageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.insertMessage(message);
        if(addedMessage!=null){
            ctx.json(mapper.writeValueAsString(addedMessage)).status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages).status(200);
    }

    private void messageByIdHandler(Context ctx) throws JsonProcessingException{
        Message ans = MessageService.getMessageById(ctx.pathParam("message_id"));
        if (ans != null){
            ctx.json(ans).status(200);
        }else{
            ctx.status(200);
        }
        
    }
    private void deleteMessageHandler(Context ctx){
        Message ans = MessageService.deleteMessageById(ctx.pathParam("message_id"));
        if (ans != null){
            ctx.json(ans).status(200);
        }else{
            ctx.status(200);
        }
    }

    private void patchMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int messageID = Integer.parseInt(ctx.pathParam("message_id"));
        message.setMessage_id(messageID);
        Message updatedMessage = MessageService.updateMessage(message);
        if(updatedMessage!=null){
            ctx.json(mapper.writeValueAsString(updatedMessage)).status(200);
        }else{
            ctx.status(400);
        }
    }
    private void userMessageHandler(Context ctx){
        List<Message> messages = messageService.getAllMessagesByUser(ctx.pathParam("account_id"));
        ctx.json(messages).status(200);
    }



}