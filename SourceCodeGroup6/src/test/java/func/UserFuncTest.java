package func;

import entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserFuncTest {
    private UserFunc userFunc;

    @BeforeEach
    public void setUp() {
        // Khởi tạo một đối tượng UserFunc
        userFunc = new UserFunc();

        // Tạo một danh sách người
        List<User> users = new ArrayList<>();
        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));

        // Ghi danh sách người dùng vào file
        userFunc.writeListStudents(users);
    }

    @Test
    public void testCheckUserExisting() {
        // Tạo một người dùng có tên đăng nhập và mật khẩu chính xác
        User existingUser = new User("user1", "password1");

        assertTrue(userFunc.checkUser(existingUser));
    }

    @Test
    public void testCheckUserNonExisting() {
        // Tạo một người dùng có tên đăng nhập hoặc mật khẩu không chính xác
        User nonExistingUser = new User("nonexistentuser", "incorrectpassword");

        assertFalse(userFunc.checkUser(nonExistingUser));
    }

    @Test
    public void testCheckUserNameValid() {
        // Tạo một người dùng với tên đăng nhập hợp lệ
        User newUser = new User("newuser", "password");

        assertTrue(userFunc.checkUserName(newUser));
    }

    @Test
    public void testCheckUserNameEmpty() {
        // Tạo một người dùng với tên đăng nhập rỗng
        User emptyUserNameUser = new User("", "password");

        assertFalse(userFunc.checkUserName(emptyUserNameUser));
    }

    @Test
    public void testCheckUserNameDuplicate() {
        // Tạo một người dùng với tên đăng nhập đã tồn tại
        User existingUserNameUser = new User("user1", "password");

        assertFalse(userFunc.checkUserName(existingUserNameUser));
    }

    @Test
    public void testAddNewUser() {
        User newUser = new User("newuser", "password");

        userFunc.add(newUser);

        assertTrue(userFunc.checkUser(newUser));
    }
}
