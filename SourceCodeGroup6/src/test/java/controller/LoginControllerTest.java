package controller;

import entity.User;
import func.UserFunc;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import view.LoginView;
import view.RegisterView;
import view.CustomerView;
import view.VaccineView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private LoginView loginView;

    @Mock
    private RegisterView registerView;

    @Mock
    private UserFunc userDao;

    @Mock
    private CustomerView customerView;

    @Mock
    private VaccineView vaccineView;

    @Mock
    private StudentController studentController;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        // Reset any mock interactions before each test
        reset(loginView, registerView, userDao, customerView, vaccineView, studentController);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLoginListener_checkValidUser() {
        // Arrange
        entity.User user = new entity.User("user1", "password1");
        when(loginView.getUser()).thenReturn(user);
        when(userDao.checkUser(user)).thenReturn(true);

        // Act
        loginController.showLoginView();
        ActionEvent actionEvent = mock(ActionEvent.class);
        loginController.new LoginListener().actionPerformed(actionEvent);

        // Asser
        assertFalse(loginView.isVisible());
        verify(customerView, never()).setVisible(false);
    }

    @Test
    public void testLoginListener_checkInvalidUser() {
        // Arrange
        entity.User user = new entity.User("Nothing", "12345678");
        when(loginView.getUser()).thenReturn(user);
        when(userDao.checkUser(user)).thenReturn(false);

        // Act
        loginController.showLoginView();
        ActionEvent actionEvent = mock(ActionEvent.class);
        loginController.new LoginListener().actionPerformed(actionEvent);

        // Asser
        verify(customerView, never()).setVisible(true);
        verify(loginView).showMessage("username hoặc password không đúng.");
    }

    @Test
    public void testLoginListener_checkEmptyUser() {
        // Arrange
        entity.User user = new entity.User("", "12345678");
        when(loginView.getUser()).thenReturn(user);
        when(userDao.checkUser(user)).thenReturn(false);

        // Act
        loginController.showLoginView();
        ActionEvent actionEvent = mock(ActionEvent.class);
        loginController.new LoginListener().actionPerformed(actionEvent);

        // Asser
        verify(customerView, never()).setVisible(true);
        verify(loginView).showMessage("username hoặc password không đúng.");
    }

    @Test
    public void testLoginListener_checkEmptyPassword() {
        // Arrange
        entity.User user = new entity.User("user1", "");
        when(loginView.getUser()).thenReturn(user);
        when(userDao.checkUser(user)).thenReturn(false);

        // Act
        loginController.showLoginView();
        ActionEvent actionEvent = mock(ActionEvent.class);
        loginController.new LoginListener().actionPerformed(actionEvent);

        // Asser
        verify(customerView, never()).setVisible(true);
        verify(loginView).showMessage("username hoặc password không đúng.");
    }

    @Test
    public void testLoginListener_checkEmptyUserAndPassword() {
        // Arrange
        entity.User user = new entity.User("", "");
        when(loginView.getUser()).thenReturn(user);
        when(userDao.checkUser(user)).thenReturn(false);

        // Act
        loginController.showLoginView();
        ActionEvent actionEvent = mock(ActionEvent.class);
        loginController.new LoginListener().actionPerformed(actionEvent);

        // Asser
        verify(customerView, never()).setVisible(true);
        verify(loginView).showMessage("username hoặc password không đúng.");
    }

    @Test
    void testLoginController() {
        LoginView loginView = new LoginView();
        RegisterView registerView = new RegisterView();
        LoginController logincontroller = new LoginController(loginView, registerView);
        assertNotNull(logincontroller);
    }
}


