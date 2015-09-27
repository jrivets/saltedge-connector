package org.jrivets.connector.saltedge.v2;

public class DefaultSaltEdgeClientTest {

    public void countriesTest() {
        SaltEdgeClient sec = new SaltEdgeClientBuilder().clientId("client id").serviceSecret("service secret")
                .privateKeyFileName("") // optional for test accounts
                .build();
        System.out.println(sec.getClientInfo());
        System.out.println(sec.getCountryCodes());
        System.out.println(sec.getProviderByCode("fakebank_simple_xf"));
        Result<Provider> providers = sec.getProviders(323, 3, null);
        System.out.println(providers);
        System.out.println(sec.createCustomer());
        System.out.println(sec.deleteCustomer("cust_id"));
        System.out.println(sec.getCustomers(1, 10));
        System.out.println(sec.getLogins(1, 10));
        System.out.println(sec.getLogin(54792));

        Login l = new Login();
        l.country_code = "XF";
        l.provider_code = "fakebank_simple_xf";
        l.customer_id = "cust_id";
        l.credentials = Utils.list2Map("login", "username", "password", "secret");
        System.out.println(sec.createLogin(l));

        l = new Login();
        l.id = 55617;
        l.credentials = Utils.list2Map("login", "username", "password", "secret");
        System.out.println(sec.reconnectLogin(l));

        System.out.println(sec.deleteLogin(55617));
        System.out.println(sec.getAccounts(54792));

        System.out.println(sec.getCategories());
        System.out.println(sec.getCurrencies());
        System.out.println(sec.getTransactions(new TransactionQuery()));
    }

}
