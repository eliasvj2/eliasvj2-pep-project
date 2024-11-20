package Service;
import DAO.*;
import Model.Account;


public class AccountService {
    public AccountDAO accountDAO;
    public MessageDAO messageDAO;
    public AccountService(){
        this.accountDAO = new AccountDAO();
        this.messageDAO = new MessageDAO();
    }

    public Account registerAccount(Account account){
        return accountDAO.addAccount(account);
    }
}