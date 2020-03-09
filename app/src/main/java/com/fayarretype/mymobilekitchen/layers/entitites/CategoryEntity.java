package com.fayarretype.mymobilekitchen.layers.entitites;

public class CategoryEntity extends BaseEntity {

    private String categoryName;
    private int categoryImageNo;

    public CategoryEntity() {

    }

    public CategoryEntity(int id) {
        super(id);
    }

    public CategoryEntity(int id, String categoryName) {
        super(id);
        this.categoryName = categoryName;
    }

    public CategoryEntity(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryImageNo() {
        return categoryImageNo;
    }

    public void setCategoryImageNo(int categoryImageNo) {
        this.categoryImageNo = categoryImageNo;
    }

}
