package org.jrivets.connector.saltedge.v2;

import java.util.Date;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SerializerTest {

    private Serializer serializer;

    @BeforeMethod
    public void init() {
        serializer = new Serializer();
    }

    @Test
    public void providerSerializationTest() {
        Provider p1 = new Provider();
        p1.code = "abc";
        p1.refresh_timeout = 10;
        p1.mode = ProviderMode.FILE;
        p1.status = ProviderStatus.DISABLED;
        p1.created_at = new Date();
        String json = serializer.toJson(p1);
        Provider p2 = serializer.fromJson(json, Provider.class);
        assertProvsEquals(p1, p2);
        String json2 = serializer.toJson(p2);
        assertEquals(json2, json);
    }

    @Test
    public void pureProviderTest() {
        Provider p1 = new Provider();
        String json = serializer.toJson(p1);
        Provider p2 = serializer.fromJson(json, Provider.class);
        assertProvsEquals(p1, p2);
        String json2 = serializer.toJson(p2);
        assertEquals(json2, json);
    }

    @Test
    public void loginSerializationTest() {
        Login l1 = new Login();
        l1.created_at = new Date();
        l1.last_fail_error_class = Error.ClientNotFound;
        l1.status = LoginStatus.DISABLED;

        String json = serializer.toJson(l1);
        Login l2 = serializer.fromJson(json, Login.class);
        assertLoginEquals(l1, l2);
        String json2 = serializer.toJson(l2);
        assertEquals(json2, json);
    }

    @Test
    public void pureLoginSerializationTest() {
        Login l1 = new Login();

        String json = serializer.toJson(l1);
        Login l2 = serializer.fromJson(json, Login.class);
        assertLoginEquals(l1, l2);
        String json2 = serializer.toJson(l2);
        assertEquals(json2, json);
    }

    @Test
    public void providerFieldTest() {
        ProviderField pf1 = new ProviderField();
        pf1.nature = FieldNature.PASSWORD;

        String json = serializer.toJson(pf1);
        ProviderField pf2 = serializer.fromJson(json, ProviderField.class);

        assertEquals(pf2.nature, pf1.nature);
        assertEquals(serializer.toJson(pf2), json);
    }

    @Test
    public void errorResponceTest() {
        String msg = "Login with id: '987' was not found.";
        String json = "{ \"error_class\": \"LoginNotFound\","
                + "\"message\": \"" + msg + "\", \"request\": { \"login_id\": 987 } }";
        ErrorResponse resp = serializer.fromJson(json, ErrorResponse.class);
        assertNotNull(resp);
        assertEquals(resp.error_class, Error.LoginNotFound);
        assertEquals(resp.message, msg);
    }

    public void assertProvsEquals(Provider p1, Provider p2) {
        assertEquals(p1.code, p2.code);
        assertEquals(p1.refresh_timeout, p2.refresh_timeout);
        assertEquals(p1.mode, p2.mode);
        assertEquals(p1.status, p2.status);
        assertEquals(p1.created_at, p2.created_at);
    }

    public void assertLoginEquals(Login l1, Login l2) {
        assertEquals(l1.created_at, l2.created_at);
        assertEquals(l1.last_fail_error_class, l2.last_fail_error_class);
        assertEquals(l1.status, l2.status);
    }

}
