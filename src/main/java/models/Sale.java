package models;

public class Sale {
    private String upc;
    private String checkNumber;
    private Integer productNumber;
    private Double sellingPrice;

    public Sale(String upc, String checkNumber, Integer productNumber,
                Double sellingPrice) {
        this.setUpc(upc);
        this.setCheckNumber(checkNumber);
        this.setProductNumber(productNumber);
        this.setSellingPrice(sellingPrice);
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}