package models;

import java.sql.Date;

public class Employee extends Table{
    private String idEmployee;
    private String emplSurname;
    private String emplName;
    private String emplPatron;
    private String emplRole;
    private Double salary;
    private Date dateOfBirth;
    private Date dateOfStart;
    private String phoneNumber;
    private String city;
    private String street;
    private String zipCode;
    public Employee(String idEmployee, String emplSurname, String emplName,
                    String emplPatron, String emplRole, Double salary, Date dateOfBirth,
                    Date dateOfStart, String phoneNumber, String city,
                    String street, String zipCode){
        this.setIdEmployee(idEmployee);
        this.setEmplName(emplName);
        this.setEmplSurname(emplSurname);
        this.setEmplPatron(emplPatron);
        this.setEmplRole(emplRole);
        this.setSalary(salary);
        this.setDateOfBirth(dateOfBirth);
        this.setDateOfStart(dateOfStart);
        this.setPhoneNumber(phoneNumber);
        this.setCity(city);
        this.setStreet(street);
        this.setZipCode(zipCode);
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getEmplSurname() {
        return emplSurname;
    }

    public void setEmplSurname(String emplSurname) {
        this.emplSurname = emplSurname;
    }

    public String getEmplName() {
        return emplName;
    }

    public void setEmplName(String emplName) {
        this.emplName = emplName;
    }

    public String getEmplPatron() {
        return emplPatron;
    }

    public void setEmplPatron(String emplPatron) {
        this.emplPatron = emplPatron;
    }

    public String getEmplRole() {
        return emplRole;
    }

    public void setEmplRole(String emplRole) {
        this.emplRole = emplRole;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String[] getFieldsValuesAsStringArray() {
        return new String[]{"'"+getIdEmployee()+"'", "'"+getEmplSurname()+"'", "'"+getEmplName()+"'",
                "'"+getEmplPatron()+"'", "'"+getEmplRole()+"'",
                getSalary().toString(), "'"+getDateOfBirth()+"'", "'"+getDateOfStart()+"'",
                "'"+getCity()+"'", "'"+getStreet()+"'", "'"+getZipCode()+"'"};
    }
}
