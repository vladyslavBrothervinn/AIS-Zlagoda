package models;

public class Product extends Table{
    private Integer idProduct;
    private Integer categoryNumber;
    private String productName;
    private String characteristics;
    public Product(Integer idProduct, Integer categoryNumber, String productName, String characteristics){
        this.setIdProduct(idProduct);
        this.setCategoryNumber(categoryNumber);
        this.setProductName(productName);
        this.setCharacteristics(characteristics);
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(Integer categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    @Override
    public String[] getFieldsValuesAsStringArray() {
        return new String[]{getIdProduct().toString(), getCategoryNumber().toString(),
                "'"+getProductName()+"'", "'"+getCharacteristics()+"'"};
    }
    @Override
    public String[] getKeyValues(){
        return new String[]{idProduct.toString()};
    }
}
