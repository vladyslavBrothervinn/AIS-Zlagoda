package models;

public class Category extends Table{
    private Integer categoryNumber;
    private String categoryName;
    public Category(int categoryNumber, String categoryName){
        this.setCategoryNumber(categoryNumber);
        this.setCategoryName(categoryName);
    }

    public Integer getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(int categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String[] getFieldsValuesAsStringArray(){
        return new String[]{getCategoryNumber().toString(), "'"+getCategoryName()+"'"};
    }
    @Override
    public String[] getKeyValues(){
        return new String[]{categoryNumber.toString()};
    }
}
