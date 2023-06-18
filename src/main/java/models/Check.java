package models;

import java.sql.Date;

public class Check extends Table{
    private String checkNumber;
    private String idEmployee;
    private String cardNumber;
    private Date printDate;
    private Double sumTotal;
    private Double vat;
    public Check(String checkNumber, String idEmployee, String cardNumber, Date printDate, Double sumTotal, Double vat){
        this.setCheckNumber(checkNumber);
        this.setIdEmployee(idEmployee);
        this.setCardNumber(cardNumber);
        this.setPrintDate(printDate);
        this.setSumTotal(sumTotal);
        this.setVat(vat);
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(String idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getPrintDate() {
        return printDate;
    }

    public void setPrintDate(Date printDate) {
        this.printDate = printDate;
    }

    public Double getSumTotal() {
        return sumTotal;
    }

    public void setSumTotal(Double sumTotal) {
        this.sumTotal = sumTotal;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    @Override
    public String[] getFieldsValuesAsStringArray() {
        return new String[]{"'"+getCheckNumber()+"'", "'"+getIdEmployee()+"'",
                "'"+getCardNumber()+"'", "'" +getPrintDate().toString()+"'", getSumTotal().toString(), getVat().toString()};
    }
    @Override
    public String[] getKeyValues(){
        return new String[]{checkNumber};
    }
}
