package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import entity.Customer;
import entity.Vaccine;
import func.CustomerFunc;
import java.util.Calendar;
import java.util.Date;
import view.CustomerView;
import view.VaccineView;

public class StudentController {
    private CustomerFunc studentDao;
    private CustomerView customerView;
    private VaccineView vaccineView;

    public StudentController(CustomerView view,VaccineView view1) {
        this.customerView = view;
        
        this.vaccineView = view1;
        studentDao = new CustomerFunc();

        view.addAddStudentListener(new AddStudentListener());
        view.searchCustomerListener(new searchCustomerListener());
        view.showAllCustomerListener(new showAllCustomerListener());
        view.showVacBtnListener(new showVacBtnListener());
        view1.addAddVaccineListener(new AddVaccineListener());

        view.addEdiStudentListener(new EditStudentListener());
        view1.addEdiVaccineListener(new EditVaccineListener());

        view.addDeleteStudentListener(new DeleteStudentListener());
        view1.addDeleteVaccineListener(new DeleteVaccineListener());
        view1.closeBtnListener(new closeBtnListener());
        view.addClearListener(new ClearStudentListener());
        view1.addClearVaccineListener(new ClearVaccineListener());

        view.addSortStudentNameListener(new SortStudentNameListener());
        view.addListStudentSelectionListener(new ListStudentSelectionListener());
        view1.addListVaccineSelectionListener(new ListVaccineSelectionListener());
    }

    public void showStudentView() {
        List<Customer> customerList = studentDao.getListStudents();
        customerView.setVisible(true);
        customerView.showListCustomers(customerList);
    }

    /**
     * Lớp AddStudentListener
     * chứa cài đặt cho sự kiện click button "Add"
     *
     * @author viettuts.vn
     */
    class AddStudentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Customer customer = customerView.getStudentInfo();
            if (customer != null) {
                studentDao.add(customer);
                customerView.showStudent(customer);
                customerView.showListCustomers(studentDao.getListStudents());
                customerView.showMessage("Thêm thành công!");
            }
        }
    }

    class searchCustomerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = customerView.getNameSearch();
            if (name != null) {
                customerView.showListCustomers(studentDao.findAllByName(name));
            } else {
                customerView.showListCustomers(studentDao.getListStudents());
            }
        }
    }

    class showAllCustomerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            customerView.showListCustomers(studentDao.getListStudents());

        }
    }
    class closeBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            customerView.setVisible(true);
            vaccineView.setVisible(false);
        }
    }
    class showVacBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            
            vaccineView.setVisible(true);
            customerView.setVisible(false);
            CustomerFunc customerFunc = new CustomerFunc();
        // lấy chỉ số của hàng được chọn 
            int id = customerView.getidCus();

            List<Vaccine> vaccines = customerFunc.findById(id).getVaccines();
            vaccineView.showListVaccine(vaccines);
        }
    }
    class AddVaccineListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Vaccine vaccine = vaccineView.getVaccineInfo();
            int idCustomer = customerView.getIdCustomer();
            Date date=java.util.Calendar.getInstance().getTime();
            if (vaccine != null && idCustomer != -1 && vaccine.getInjectAgain().compareTo(date) >= 0 ) {
                studentDao.addVaccine(idCustomer, vaccine);
                System.out.print(vaccine.getInjectAgain().compareTo(date));
                vaccineView.showListVaccine(studentDao.findById(idCustomer).getVaccines());

                vaccineView.showMessage("Thêm Vaccine thành công!");
            } else {
                vaccineView.showMessage("Không thể thêm vì ngày tiêm lại trước ngày hiện tại");

            }
        }
    }

    /**
     * Lớp EditStudentListener
     * chứa cài đặt cho sự kiện click button "Edit"
     *
     * @author viettuts.vn
     */
    class EditStudentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Customer customer = customerView.getStudentInfo();
            if (customer != null) {
                studentDao.edit(customer);
                customerView.showStudent(customer);
                customerView.showListCustomers(studentDao.getListStudents());
                customerView.showMessage("Cập nhật thành công!");
            }
        }
    }

    class EditVaccineListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Vaccine vaccine = vaccineView.getVaccineInfo();
            Date date=java.util.Calendar.getInstance().getTime();

            int idCustomer = customerView.getIdCustomer();
            if (vaccine != null && idCustomer != -1 && vaccine.getInjectAgain().compareTo(date) >= 0) {
                studentDao.editVaccine(idCustomer, vaccine);
                vaccineView.showListVaccine(studentDao.findById(idCustomer).getVaccines());
                vaccineView.showMessage("Cập nhật thành công!");
            } else {
                vaccineView.showMessage("Không thể sửa vì ngày tiêm lại trước ngày hiện tại!");
            }
        }
    }

    /**
     * Lớp DeleteStudentListener
     * chứa cài đặt cho sự kiện click button "Delete"
     *
     * @author viettuts.vn
     */
    class DeleteStudentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Customer customer = customerView.getStudentInfo();
            if (customer != null) {
                studentDao.delete(customer);
                customerView.clearStudentInfo();
                customerView.showListCustomers(studentDao.getListStudents());
                customerView.showMessage("Xóa thành công!");
            }
        }
    }

    class DeleteVaccineListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Vaccine vaccine = vaccineView.getVaccineInfo();
            int idCustomer = customerView.getIdCustomer();
            if (vaccine != null && idCustomer != -1) {
                studentDao.deleteVaccine(idCustomer, vaccine);
                vaccineView.clearVaccineInfo();
                vaccineView.showListVaccine(studentDao.findById(idCustomer).getVaccines());
                vaccineView.showMessage("Xóa thành công!");
            } else {
                vaccineView.showMessage("Không thể xóa vì chưa biết xóa cho customer nào!");
            }
        }
    }

    /**
     * Lớp ClearStudentListener
     * chứa cài đặt cho sự kiện click button "Clear"
     *
     * @author viettuts.vn
     */
    class ClearStudentListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            customerView.clearStudentInfo();
        }
    }

    class ClearVaccineListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            vaccineView.clearVaccineInfo();
        }
    }

    /**
     * Lớp SortStudentGPAListener
     * chứa cài đặt cho sự kiện click button "Sort By GPA"
     *
     * @author viettuts.vn
     */
    class SortStudentGPAListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
//            studentDao.sortStudentByGPA();
//            customerView.showListStudents(studentDao.getListStudents());
        }
    }

    /**
     * Lớp SortStudentGPAListener
     * chứa cài đặt cho sự kiện click button "Sort By Name"
     *
     * @author viettuts.vn
     */
    class SortStudentNameListener implements ActionListener {
        private boolean sortByName = true;

        public void actionPerformed(ActionEvent e) {
            if (sortByName) {
                studentDao.sortCustomerByName();
                customerView.showListCustomers(studentDao.getListStudents());
            } else {
                studentDao.sortCustomerByID();
                customerView.showListCustomers(studentDao.getListStudents());
            }

            sortByName = !sortByName;
        }
    }

    /**
     * Lớp ListStudentSelectionListener
     * chứa cài đặt cho sự kiện chọn student trong bảng student
     *
     * @author viettuts.vn
     */
    class ListStudentSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            customerView.fillCustomerFromSelectedRow();
        }
    }

    class ListVaccineSelectionListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            vaccineView.fillVaccineFromSelectedRow();
        }
    }
}