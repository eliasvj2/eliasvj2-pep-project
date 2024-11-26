package Service;
import DAO.*;
import Model.Message;
import java.util.*;

public class MessageService {
    public AccountDAO accountDAO;
    public MessageDAO messageDAO;
    public MessageService(){
        this.accountDAO = new AccountDAO();
        this.messageDAO = new MessageDAO();
    }
    public Message insertMessage(Message message){
         if(!message.getMessage_text().equals("") && message.getMessage_text().length() <= 255 && messageDAO.checkUser(message) == message.getPosted_by()){
            return messageDAO.addMessage(message);
         }
         return null;
     }
     public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
     }
     public static Message getMessageById(String messageID){
        int messageid = Integer.parseInt(messageID);
        return MessageDAO.getMessageById(messageid);
     }
     public static Message deleteMessageById(String messageID){
        int messageid = Integer.parseInt(messageID);
        return MessageDAO.deleteMessageById(messageid);
     }
}
