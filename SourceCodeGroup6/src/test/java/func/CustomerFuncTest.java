package func;

import controller.StudentController;
import entity.Customer;
import entity.Vaccine;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import view.CustomerView;
import view.LoginView;
import view.RegisterView;
import view.VaccineView;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerFuncTest {
    private CustomerFunc customerFunc;
    private List<Customer> initialCustomers;

    @BeforeEach
    void setUp() {
        // Khởi tạo một danh sách
        initialCustomers = new ArrayList<>();
        initialCustomers.add(new Customer(1, "Someone", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        initialCustomers.add(new Customer(2, "Someone1", (byte) 20, "Tp HCM", "0982345656", new ArrayList<>()));

        customerFunc = new CustomerFunc();
        customerFunc.setListStudents(initialCustomers);
    }

    @Test
    void add() {
        int initialSize = initialCustomers.size();
        Customer newCustomer = new Customer(3, "Back", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>());

        customerFunc.add(newCustomer);

        List<Customer> updatedCustomers = customerFunc.getListStudents();
        assertEquals(initialSize + 1, updatedCustomers.size());
        assertTrue(updatedCustomers.contains(newCustomer));
    }

    @Test
    public void testAddNewVaccine() {
        int initialVaccineCount = initialCustomers.get(0).getVaccines().size();

        // Vaccine mới
        Vaccine newVaccine = new Vaccine(1, "Lao", 50.0, new Date(), new Date());

        // Thêm vaccine mới cho khách hàng có ID là 1
        customerFunc.addVaccine(1, newVaccine);

        // Lấy danh sách khách hàng đã được cập nhật
        List<Customer> updatedCustomers = customerFunc.getListStudents();

        // Lấy khách hàng có ID là 1 từ danh sách đã cập nhật
        Customer updatedCustomer = updatedCustomers.stream()
                .filter(customer -> customer.getId() == 1)
                .findFirst()
                .orElse(null);

        // Kiểm tra xem vaccine đã được thêm thành công cho khách hàng có ID là 1 hay không
        assertEquals(initialVaccineCount + 1, updatedCustomer.getVaccines().size());
        assertTrue(updatedCustomer.getVaccines().contains(newVaccine));
    }

    @Test
    public void testEditVaccine() {
        // Vaccine cũ của khách hàng có ID là 1
        Vaccine oldVaccine = new Vaccine(1, "Lao", 50.0, new Date(), new Date());

        // Vaccine mới
        Vaccine newVaccine = new Vaccine(1, "Lao", 70.0, new Date(), new Date());

        // Chỉnh sửa vaccine của khách hàng có ID là 1
        customerFunc.editVaccine(1, newVaccine);

        // Lấy danh sách khách hàng đã được cập nhật
        List<Customer> updatedCustomers = customerFunc.getListStudents();

        // Lấy khách hàng có ID là 1 từ danh sách đã cập nhật
        Customer updatedCustomer = updatedCustomers.stream()
                .filter(customer -> customer.getId() == 1)
                .findFirst()
                .orElse(null);

        // Kiểm tra xem vaccine đã được chỉnh sửa thành công cho khách hàng có ID là 1 hay không
        assertTrue(updatedCustomer.getVaccines().contains(newVaccine));
        assertFalse(updatedCustomer.getVaccines().contains(oldVaccine));
    }

    @Test
    public void testDeleteVaccine() {
        // Vaccine cần xóa của khách hàng có ID là 1
        Vaccine vaccineToDelete = new Vaccine(1, "Moderna", 50.0, new Date(), new Date());

        // Xóa vaccine của khách hàng có ID là 1
        customerFunc.deleteVaccine(1, vaccineToDelete);

        // Lấy danh sách khách hàng đã được cập nhật
        List<Customer> updatedCustomers = customerFunc.getListStudents();

        // Lấy khách hàng có ID là 1 từ danh sách đã cập nhật
        Customer updatedCustomer = updatedCustomers.stream()
                .filter(customer -> customer.getId() == 1)
                .findFirst()
                .orElse(null);

        // Kiểm tra xem vaccine đã được xóa thành công cho khách hàng có ID là 1 hay không
        assertFalse(updatedCustomer.getVaccines().contains(vaccineToDelete));
    }
    @Test
    public void testEditCustomer() {
        // Thông tin khách hàng mới
        Customer newCustomerInfo = new Customer(1, "Back", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>());

        // Chỉnh sửa thông tin của khách hàng có ID là 1
        customerFunc.edit(newCustomerInfo);

        // Lấy danh sách khách hàng đã được cập nhật
        List<Customer> updatedCustomers = customerFunc.getListStudents();

        // Lấy thông tin của khách hàng có ID là 1 từ danh sách đã cập nhật
        Customer updatedCustomer = updatedCustomers.stream()
                .filter(customer -> customer.getId() == 1)
                .findFirst()
                .orElse(null);

        // Kiểm tra xem thông tin của khách hàng đã được chỉnh sửa thành công hay không
        assertEquals(newCustomerInfo.getName(), updatedCustomer.getName());
        assertEquals(newCustomerInfo.getAge(), updatedCustomer.getAge());
        assertEquals(newCustomerInfo.getAddress(), updatedCustomer.getAddress());
        assertEquals(newCustomerInfo.getPhone(), updatedCustomer.getPhone());
    }

    @Test
    public void testDeleteCustomer() {
        // Khách hàng cần xóa có ID là 2
        Customer customerToDelete = new Customer(2, "Someone1", (byte) 20, "Tp HCM", "0982345656", new ArrayList<>());

        // Xóa khách hàng có ID là 2
        boolean isDeleted = customerFunc.delete(customerToDelete);

        // Lấy danh sách khách hàng đã được cập nhật
        List<Customer> updatedCustomers = customerFunc.getListStudents();

        // Kiểm tra xem khách hàng có ID là 2 đã được xóa thành công hay không
        assertFalse(updatedCustomers.contains(customerToDelete));
        assertTrue(isDeleted);
    }

    @Test
    public void testFindCustomerById() {
        // Tìm kiếm khách hàng có ID là 1
        Customer foundCustomer = customerFunc.findById(1);

        // Kiểm tra xem khách hàng đã được tìm thấy hay không
        assertEquals(1, foundCustomer.getId());
        assertEquals("Someone", foundCustomer.getName());
    }

    @Test
    public void testFindCustomerByIdNotFound() {
        // Tìm kiếm khách hàng có ID không tồn tại
        Customer foundCustomer = customerFunc.findById(3);

        // Kiểm tra xem khách hàng có ID không tồn tại đã trả về null hay không
        assertNull(foundCustomer);
    }

    @Test
    public void testFindAllCustomersByName() {
        List<Customer> foundCustomers = customerFunc.findAllByName("Someone1");

        assertEquals(1, foundCustomers.size());
        assertEquals("Someone1", foundCustomers.get(0).getName());
    }

    @Test
    public void testFindAllCustomersByNameNotFound() {
        List<Customer> foundCustomers = customerFunc.findAllByName("Charlie");

        assertTrue(foundCustomers.isEmpty());
    }

    @Test
    public void testSortCustomerByName() {
        // Sắp xếp danh sách khách hàng theo tên
        customerFunc.sortCustomerByName();

        // Lấy danh sách khách hàng đã được sắp xếp
        List<Customer> sortedCustomers = customerFunc.getListStudents();

        // Kiểm tra xem danh sách khách hàng đã được sắp xếp đúng theo tên hay không
        assertEquals("Someone", sortedCustomers.get(0).getName());
        assertEquals("Someone1", sortedCustomers.get(1).getName());
    }

    @Test
    public void testSortCustomerByID() {
        // Sắp xếp danh sách khách hàng theo ID
        customerFunc.sortCustomerByID();

        // Lấy danh sách khách hàng đã được sắp xếp
        List<Customer> sortedCustomers = customerFunc.getListStudents();

        // Kiểm tra xem danh sách khách hàng đã được sắp xếp đúng theo ID hay không
        assertEquals(1, sortedCustomers.get(0).getId());
        assertEquals(2, sortedCustomers.get(1).getId());
    }
}