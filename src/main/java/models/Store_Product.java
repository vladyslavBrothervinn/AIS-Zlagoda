package models;

public class Store_Product {
    private String upc;
    private String upcProm;
    private Integer idProduct;
    private Double sellingPrice;
    private Integer productsNumber;
    private Boolean promotionalProduct;
    public Store_Product(String upc, String upcProm, Integer idProduct,
                         Double sellingPrice, Integer productsNumber, Boolean promotionalProduct){
        this.setUpc(upc);
        this.setUpcProm(upcProm);
        this.setIdProduct(idProduct);
        this.setSellingPrice(sellingPrice);
        this.setProductsNumber(productsNumber);
        this.setPromotionalProduct(promotionalProduct);
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getUpcProm() {
        return upcProm;
    }

    public void setUpcProm(String upcProm) {
        this.upcProm = upcProm;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getProductsNumber() {
        return productsNumber;
    }

    public void setProductsNumber(Integer productsNumber) {
        this.productsNumber = productsNumber;
    }

    public Boolean getPromotionalProduct() {
        return promotionalProduct;
    }

    public void setPromotionalProduct(Boolean promotionalProduct) {
        this.promotionalProduct = promotionalProduct;
    }
}
