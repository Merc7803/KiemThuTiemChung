package controller;

import entity.User;
import func.UserFunc;
import view.LoginView;
import view.RegisterView;
import view.CustomerView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {

    @Mock
    private UserFunc userDao;

    @Mock
    private LoginView loginView;

    @Mock
    private RegisterView registerView;

    @InjectMocks
    private RegisterController registerController;

    private User invalidUser;

    @BeforeEach
    public void setUp() {
        invalidUser = new User("invalid_username", "invalid_password");
    }

    @Test
    public void testRegisterValiUser() {
        invalidUser.setUserName("Something");
        invalidUser.setPassword("12345678");
        when(registerView.getUser()).thenReturn(invalidUser);

        registerController.new RegisterListener().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        verify(registerView, times(1)).showMessage("Đăng ký thành công!");
    }

    @Test
    public void testRegisterInvalidUser_UsernameEmpty() {
        invalidUser.setUserName("");
        invalidUser.setPassword("12345678");
        when(registerView.getUser()).thenReturn(invalidUser);

        registerController.new RegisterListener().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        verify(registerView, times(1)).showMessage("username hoặc password đang bị trùng hoặc để trống");
        verify(userDao, never()).add(any(User.class));
        verify(loginView, never()).setVisible(true);
        verify(registerView, never()).setVisible(false);
    }

    @Test
    public void testRegisterInvalidUser_UsernameExists() {
        invalidUser.setUserName("Giang");
        invalidUser.setPassword("1234");
        boolean check = false;
        when(registerView.getUser()).thenReturn(invalidUser);

        registerController.new RegisterListener().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        verify(registerView, times(1)).showMessage("username hoặc password đang bị trùng hoặc để trống");
        verify(userDao, never()).add(any(User.class));
        verify(loginView, never()).setVisible(true);
        verify(registerView, never()).setVisible(false);
    }

    @Test
    public void testRegisterInvalidUser_PasswordEmpty() {
        invalidUser.setUserName("Something");
        invalidUser.setPassword("");
        when(registerView.getUser()).thenReturn(invalidUser);

        registerController.new RegisterListener().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        verify(registerView, times(1)).showMessage("username hoặc password đang bị trùng hoặc để trống");
        verify(userDao, never()).add(any(User.class));
        verify(loginView, never()).setVisible(true);
        verify(registerView, never()).setVisible(false);
    }

    @Test
    public void testRegisterInvalidUser_UsernameAndPasswordEmpty() {
        invalidUser.setUserName("");
        invalidUser.setPassword("");
        when(registerView.getUser()).thenReturn(invalidUser);

        registerController.new RegisterListener().actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));

        verify(registerView, times(1)).showMessage("username hoặc password đang bị trùng hoặc để trống");
        verify(userDao, never()).add(any(User.class));
        verify(loginView, never()).setVisible(true);
        verify(registerView, never()).setVisible(false);
    }

    @Test
    void testRegisterController() {
        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();
        RegisterController registerController = new RegisterController(loginView, registerView);
        assertNotNull(registerController);
    }
}
