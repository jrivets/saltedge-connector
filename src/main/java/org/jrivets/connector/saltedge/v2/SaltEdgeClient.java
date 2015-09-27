package org.jrivets.connector.saltedge.v2;

import java.io.Closeable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SaltEdgeClient extends Closeable {

    List<String> getCountryCodes();
    
    Client getClientInfo();
    
    Provider getProviderByCode(String providerCode);
    
    Result<Provider> getProviders(int startId, int limit, Date fromDate);
    
    /**
     * Creates new customer. It returns the new customer identifier, if created successfully
     * @return
     */
    Customer createCustomer();
    
    /**
     * Returns list of customers beginning from provided id
     * @param startId
     * @param limit
     * @return
     */
    Result<Customer> getCustomers(int startId, int limit);
    
    boolean deleteCustomer(String customerId);
    
    /** 
     * Creates new login. Please refer to SaltEdge doc for details: https://docs.saltedge.com/reference/#logins-create 
     * @param login
     * @return
     */
    Login createLogin(Login login);
    
    Login reconnectLogin(Login login);
    
    Login interactiveLogin(Login login);
    
    List<Login> getLogins(int startId, int limit);
    
    Login getLogin(int loginId);
    
    boolean deleteLogin(int loginId);
    
    List<Account> getAccounts(int loginId);
    
    List<String> getCurrencies();
    
    Map<String, List<String>> getCategories();
    
    Result<Transaction> getTransactions(TransactionQuery txQuery);
}
