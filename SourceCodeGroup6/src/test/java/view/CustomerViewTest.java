package view;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class CustomerViewTest {
    @Test
    public void testGetNameSearch_WhenSearchFieldNotEmpty() {
        CustomerView customerView = new CustomerView();
        String searchText = "John Doe";
        customerView.searchField.setText(searchText);

        String result = customerView.getNameSearch();

        assertNotNull(result);
        assertEquals(searchText, result);
    }

    @Test
    public void testGetNameSearch_WhenSearchFieldEmpty() {
        CustomerView customerView = new CustomerView();
        String searchText = null;
        customerView.searchField.setText(searchText);

        String result = customerView.getNameSearch();

        assertNull(result);
        assertEquals(searchText, result);
    }

    @Test
    public void testgetIdCustomer_WhenIDEmpty() {
        CustomerView customerView = new CustomerView();
        String id = null;
        customerView.idField.setText(id);

        String result = String.valueOf(customerView.getIdCustomer());

        assertEquals("-1", result);
    }

    @Test
    public void testgetIdCustomer_WhenIDNotEmpty() {
        CustomerView customerView = new CustomerView();
        String id = "4";
        customerView.idField.setText(id);

        String result = String.valueOf(customerView.getIdCustomer());

        assertEquals("4", result);
    }

    @Test
    public void testvalidateName_WhenNameEmpty() {
        CustomerView customerView = new CustomerView();
        String name = null;
        customerView.nameField.setText(name);

        boolean result = customerView.validateName();

        assertEquals(false, result);
    }

    @Test
    public void testvalidateName_WhenNameNotEmpty() {
        CustomerView customerView = new CustomerView();
        String name = "Alice";
        customerView.nameField.setText(name);

        boolean result = customerView.validateName();

        assertEquals(true, result);
    }

    @Test
    public void testvalidateAddress_WhenAddressNotEmpty() {
        CustomerView customerView = new CustomerView();
        String add = "Ha Noi";
        customerView.addressTA.setText(add);

        boolean result = customerView.validateAddress();

        assertEquals(true, result);
    }

    @Test
    public void testvalidateAddress_WhenAddressEmpty() {
        CustomerView customerView = new CustomerView();
        String add = null;
        customerView.addressTA.setText(add);

        boolean result = customerView.validateAddress();

        assertEquals(false, result);
    }

    @Test
    public void testvalidateAge_WhenAgeEmpty() {
        CustomerView customerView = new CustomerView();
        String age = null;
        customerView.ageField.setText(age);

        boolean result = customerView.validateAge();

        assertEquals(false, result);
    }

    @Test
    public void testvalidateAge_WhenAgeNotInRange0_100() {
        CustomerView customerView = new CustomerView();
        String age = "101";
        customerView.ageField.setText(age);
        Integer.parseInt(age);

        boolean result = customerView.validateAge();

        assertEquals(false, result);
    }

    @Test
    public void testvalidateAge_WhenAgeValid() {
        CustomerView customerView = new CustomerView();
        String age = "50";
        customerView.ageField.setText(age);
        Integer.parseInt(age);

        boolean result = customerView.validateAge();

        assertEquals(true, result);
    }

    @Test
    public void testvalidatePhone_WhenPhoneInValid() {
        CustomerView customerView = new CustomerView();
        String phone = "kisdhf897";
        customerView.phoneField.setText(phone);

        boolean result = customerView.validatePhone();

        assertEquals(false, result);
    }

    @Test
    public void testvalidatePhone_WhenPhoneNotEnough10Number() {
        CustomerView customerView = new CustomerView();
        String phone = "098765432";
        customerView.phoneField.setText(phone);

        boolean result = customerView.validatePhone();

        assertEquals(false, result);
    }

    @Test
    public void testvalidatePhone_WhenPhoneValid() {
        CustomerView customerView = new CustomerView();
        String phone = "1234566789";
        customerView.phoneField.setText(phone);

        boolean result = customerView.validatePhone();

        assertEquals(true, result);
    }
}