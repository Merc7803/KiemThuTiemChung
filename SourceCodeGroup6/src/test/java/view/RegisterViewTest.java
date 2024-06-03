package view;

import entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterViewTest {
    @Test
    public void testgetUser() {
        RegisterView registerViewView = new RegisterView();
        String username = "admin";
        String password = "12345678";
        registerViewView.userNameField.setText(username);
        registerViewView.passwordField.setText(password);

        User user = registerViewView.getUser();

        assertEquals(username, user.getUserName());
        assertEquals(password, user.getPassword());
    }
}