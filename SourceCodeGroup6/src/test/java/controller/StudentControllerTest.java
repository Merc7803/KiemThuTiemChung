package controller;

import entity.Customer;
import entity.Vaccine;
import func.CustomerFunc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import view.CustomerView;
import view.LoginView;
import view.RegisterView;
import view.VaccineView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private CustomerFunc studentDao;

    @Mock
    private CustomerView customerView;

    @Mock
    private VaccineView vaccineView;

    @InjectMocks
    private StudentController studentController;

    private Customer customer;
    private Vaccine vaccine;

    @BeforeEach
    void setUp() {
        customer = new Customer(7, "Someone", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>());
        vaccine = new Vaccine(1, "Covid-19", 100.123 ,new Date(), new Date());
    }

    @Test
    void testAdd() {
        when(customerView.getStudentInfo()).thenReturn(customer);
        studentController.showStudentView();
        studentController.new AddStudentListener().actionPerformed(null);

        verify(customerView).showMessage("Thêm thành công!"); // Verify that the success message is displayed
    }

    @Test
    void testEdit() {
        customer = new Customer(4, "Someone", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>());
        when(customerView.getStudentInfo()).thenReturn(customer);
        studentController.showStudentView();
        studentController.new EditStudentListener().actionPerformed(null);

        verify(customerView).showMessage("Cập nhật thành công!");
    }

    @Test
    void testDelete() {
        customer = new Customer(4, "Someone", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>());
        when(customerView.getStudentInfo()).thenReturn(customer);
        studentController.showStudentView();
        studentController.new DeleteStudentListener().actionPerformed(null);

        verify(customerView).showMessage("Xóa thành công!");
    }

    @Test
    void testClear() {
        customer = new Customer(4, "Someone", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>());
        customerView.fillCustomerFromSelectedRow();
        studentController.showStudentView();
        studentController.new ClearStudentListener().actionPerformed(null);

        assertEquals(null, customerView.getName());
    }

    @Test
    void testSortByName() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "Alice", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(2, "Merc", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(3, "Bane", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(4, "Back", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));

        when(studentDao.getListStudents()).thenReturn(customers);
        studentController.showStudentView();
        studentController.new SortStudentNameListener().actionPerformed(null);

        List<Customer> sortedCustomers = studentDao.getListStudents();
        assertEquals("Alice", sortedCustomers.get(0).getName());
        assertEquals("Back", sortedCustomers.get(1).getName());
        assertEquals("Bane", sortedCustomers.get(2).getName());
        assertEquals("Merc", sortedCustomers.get(3).getName());
    }

    @Test
    void testSearch() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "Alice", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(2, "Merc", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(3, "Bane", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(4, "Back", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));

        when(studentDao.getListStudents()).thenReturn(customers);
        studentController.showStudentView();
        customerView.searchCustomerListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = "B";
                customerView.showListCustomers(studentDao.findAllByName(name));

            }
        });

        List<Customer> sortedCustomers = studentDao.getListStudents();
        assertEquals("Bane", sortedCustomers.get(2).getName());
        assertEquals("Back", sortedCustomers.get(3).getName());
    }

    @Test
    void testShowAllCustomer() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "Alice", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(2, "Merc", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(3, "Bane", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));
        customers.add(new Customer(4, "Back", (byte) 20, "Ha Noi", "0982367598", new ArrayList<>()));

        studentController.showStudentView();
        studentController.new showAllCustomerListener().actionPerformed(null);

        assertEquals("Alice", customers.get(0).getName());
        assertEquals("Merc", customers.get(1).getName());
        assertEquals("Bane", customers.get(2).getName());
        assertEquals("Back", customers.get(3).getName());
    }

    @Test
    public void testShowVaccine() {
        int validID = 1;
        List<Vaccine> nonEmptyVaccineList = new ArrayList<>();
        nonEmptyVaccineList.add(new Vaccine(1, "Covid-19", 100.123 ,new Date(), new Date()));
        when(customerView.getidCus()).thenReturn(validID);

        studentController.new showVacBtnListener().actionPerformed(null);

        verify(vaccineView).setVisible(true);
        verify(customerView).setVisible(false);
    }

    @Test
    void testAddVaccine() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Lao");
        vaccine.setPrice(200.0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 10);
        Date injectAgainDate = cal.getTime();
        vaccine.setInjectAgain(injectAgainDate);

        when(vaccineView.getVaccineInfo()).thenReturn(vaccine);
        when(customerView.getIdCustomer()).thenReturn(1);

        Customer customer = new Customer();
        List<Vaccine> vaccineList = new ArrayList<>();
        vaccineList.add(vaccine);
        customer.setVaccines(vaccineList);

        studentController.new AddVaccineListener().actionPerformed(null);

        verify(vaccineView).showMessage("Thêm Vaccine thành công!");
    }

    @Test
    void testAddVaccine_FailDate() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Lao");
        vaccine.setPrice(200.0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10);
        Date injectAgainDate = cal.getTime();
        vaccine.setInjectAgain(injectAgainDate);

        when(vaccineView.getVaccineInfo()).thenReturn(vaccine);
        when(customerView.getIdCustomer()).thenReturn(1);

        Customer customer = new Customer();
        List<Vaccine> vaccineList = new ArrayList<>();
        vaccineList.add(vaccine);
        customer.setVaccines(vaccineList);

        studentController.new AddVaccineListener().actionPerformed(null);

        verify(vaccineView).showMessage("Không thể thêm vì ngày tiêm lại trước ngày hiện tại");
    }

    @Test
    void testEditVaccine() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Lao");
        vaccine.setPrice(200.0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 10);
        Date injectAgainDate = cal.getTime();
        vaccine.setInjectAgain(injectAgainDate);

        when(vaccineView.getVaccineInfo()).thenReturn(vaccine);
        when(customerView.getIdCustomer()).thenReturn(1);

        Customer customer = new Customer();
        List<Vaccine> vaccineList = new ArrayList<>();
        vaccineList.add(vaccine);
        customer.setVaccines(vaccineList);

        studentController.new EditVaccineListener().actionPerformed(null);

        verify(vaccineView).showMessage("Cập nhật thành công!");
    }

    @Test
    void testEditVaccine_FailDate() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Lao");
        vaccine.setPrice(200.0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -10);
        Date injectAgainDate = cal.getTime();
        vaccine.setInjectAgain(injectAgainDate);

        when(vaccineView.getVaccineInfo()).thenReturn(vaccine);
        when(customerView.getIdCustomer()).thenReturn(1);

        Customer customer = new Customer();
        List<Vaccine> vaccineList = new ArrayList<>();
        vaccineList.add(vaccine);
        customer.setVaccines(vaccineList);

        studentController.new EditVaccineListener().actionPerformed(null);

        verify(vaccineView).showMessage("Không thể sửa vì ngày tiêm lại trước ngày hiện tại!");
    }

    @Test
    void testDeleteVaccine() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Lao");
        vaccine.setPrice(200.0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 10);
        Date injectAgainDate = cal.getTime();
        vaccine.setInjectAgain(injectAgainDate);

        when(vaccineView.getVaccineInfo()).thenReturn(vaccine);
        when(customerView.getIdCustomer()).thenReturn(1);

        Customer customer = new Customer();
        List<Vaccine> vaccineList = new ArrayList<>();
        vaccineList.add(vaccine);
        customer.setVaccines(vaccineList);

        studentController.new DeleteVaccineListener().actionPerformed(null);

        verify(vaccineView).showMessage("Xóa thành công!");
    }

    @Test
    void testDeleteVaccine_FailCustomer() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Lao");
        vaccine.setPrice(200.0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 10);
        Date injectAgainDate = cal.getTime();
        vaccine.setInjectAgain(injectAgainDate);

        when(vaccineView.getVaccineInfo()).thenReturn(vaccine);
        when(customerView.getIdCustomer()).thenReturn(-1);

        Customer customer = new Customer();
        List<Vaccine> vaccineList = new ArrayList<>();
        vaccineList.add(vaccine);
        customer.setVaccines(vaccineList);

        studentController.new DeleteVaccineListener().actionPerformed(null);

        verify(vaccineView).showMessage("Không thể xóa vì chưa biết xóa cho customer nào!");
    }

    @Test
    void testClearVaccine() {
        Vaccine vaccine = new Vaccine();
        vaccine.setName("Lao");
        vaccine.setPrice(200.0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 10);
        Date injectAgainDate = cal.getTime();
        vaccine.setInjectAgain(injectAgainDate);

        Customer customer = new Customer();
        List<Vaccine> vaccineList = new ArrayList<>();
        vaccineList.add(vaccine);
        customer.setVaccines(vaccineList);

        studentController.new ClearVaccineListener().actionPerformed(null);

        assertEquals(null, vaccineView.getName());
    }

    @Test
    public void testCloseVaccine() {
        studentController.new closeBtnListener().actionPerformed(null);

        verify(vaccineView).setVisible(false);
        verify(customerView).setVisible(true);
    }
}