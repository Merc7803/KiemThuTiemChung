package view;

import entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class LoginViewTest {
    @Test
    public void testgetUser() {
        LoginView loginView = new LoginView();
        String username = "admin";
        String password = "12345678";
        loginView.userNameField.setText(username);
        loginView.passwordField.setText(password);

        User user = loginView.getUser();

        assertEquals(username, user.getUserName());
        assertEquals(password, user.getPassword());
    }
}