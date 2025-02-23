package Service;
import Model.Account;
import DAO.AccountDao;

/**
 * This is a Service class to handle business logic related to Account database entity
 */
public class AccountService {

    //Account Dao Object
    AccountDao accountDao;

    //A zero argument constructor to initialize the AccountDao object 
    public AccountService(){
        accountDao = new AccountDao();
    }

    /**
     * A method to create a new user account
     * @param acc The account to be created
     * @return The account that was created or null if failed
     */
    public Account register(Account acc){
        if(!isValidAccount(acc))
          return null;
        return accountDao.insertUser(acc) ;
    }

    /**
     * A method to check if a given account is valid
     * @param acc account to be validated
     * @return True if the account details constitute a valid account
     */
    private boolean isValidAccount(Account acc){
        if(acc == null || acc.getUsername()==null || acc.getUsername().length()==0 || acc.getPassword()==null || acc.getPassword().length()<4 )
          return false;
        return true;

    }

    /**
     * A method to login the user
     * @param acc User account to be logged in
     * @return The logged in account or null if login failed
     */
    public Account login(Account acc){

      if(!isValidAccount(acc))
        return null;
      return accountDao.geAccount(acc);

    }

    /**
     * A method to check if a given user_id belongs to a valid account in the database
     * @param postedBy The user_id to verify
     * @return True if user account found in the database, or false otherwise
     */
    public Boolean isValidUser(int postedBy){

      if(postedBy <1)
        return false;

      return accountDao.isValidUser(postedBy);


    }
    
}