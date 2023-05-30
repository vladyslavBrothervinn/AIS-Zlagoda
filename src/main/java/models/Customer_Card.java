package models;

public class Customer_Card {
    private String cardNumber;
    private String custSurname;
    private String custName;
    private String custPatronymic;
    private String phoneNumber;
    private String city;
    private String street;
    private String zipCode;
    private Integer percent;
    public Customer_Card(String cardNumber, String custSurname, String custName,
                         String custPatronymic, String phoneNumber, String city,
                         String street, String zipCode, Integer percent){
        this.setCardNumber(cardNumber);
        this.setCustSurname(custSurname);
        this.setCustName(custName);
        this.setCustPatronymic(custPatronymic);
        this.setPhoneNumber(phoneNumber);
        this.setCity(city);
        this.setStreet(street);
        this.setZipCode(zipCode);
        this.setPercent(percent);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCustSurname() {
        return custSurname;
    }

    public void setCustSurname(String custSurname) {
        this.custSurname = custSurname;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustPatronymic() {
        return custPatronymic;
    }

    public void setCustPatronymic(String custPatronymic) {
        this.custPatronymic = custPatronymic;
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

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }
}
