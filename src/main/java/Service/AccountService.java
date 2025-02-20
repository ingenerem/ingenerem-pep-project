package Service;
import Model.Account;
import DAO.AccountDao;

public class AccountService {

    AccountDao accountDao;

    public AccountService(){
        accountDao = new AccountDao();
    }

    
    public Account register(Account acc){
        if(!isValidAccount(acc))
          return null;
        return accountDao.insertUser(acc) ;
    }

    private boolean isValidAccount(Account acc){
        if(acc == null || acc.getUsername()==null || acc.getUsername().length()==0 || acc.getPassword()==null || acc.getPassword().length()<4 )
          return false;
        return true;

    }

    public Account login(Account acc){

      if(!isValidAccount(acc))
        return null;
      return accountDao.geAccount(acc);

    }
}