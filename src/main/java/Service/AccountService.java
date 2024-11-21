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
        if(accountDAO.getAccountByUsername(account) == null && account.getPassword().length() >= 4 && !account.getUsername().equals("")){
            return accountDAO.addAccount(account);
        }
        return null;
        
    }

    public Account loginAccount(Account account){ 
        return accountDAO.login(account);
    }
}