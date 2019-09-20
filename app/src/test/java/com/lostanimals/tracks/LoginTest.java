//package com.lostanimals.tracks;
//
//import org.json.JSONException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.io.IOException;
//
//@RunWith(JUnit4.class)
//public class LoginTest {
//
//    private static final String[] USERNAME_LOGIN = {"test", "test"};
//    private static final String[] EMAIL_LOGIN = {"test@test.com", "test"};
//
//
//
//    @Test
//    public void ServerManagerLogin_UsernameLogin() throws IOException, JSONException {
//        String postData = ConnectionManagerTestClass.postEncoder("login", USERNAME_LOGIN);
//        ConnectionManagerTestClass.processRequest("user.php", postData);
//    }
//
//    @Test
//    public void ServerManagerLogin_EmailLogin() throws IOException, JSONException {
//        String postData = ConnectionManagerTestClass.postEncoder("login", EMAIL_LOGIN);
//        ConnectionManagerTestClass.processRequest("user.php", postData);
//    }
//}
