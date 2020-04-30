package com.fayarretype.mymobilekitchen.layers.entitites;

public class FoodEntity extends BaseEntity {

    private String foodName;
    private String preparationText;
    private String cookingTime;
    private String preparationTime;
    private String howManyPerson;
    private int categoryID;

    public FoodEntity() {

    }

    public FoodEntity(int id) {
        super(id);
    }

    public FoodEntity(int id, String foodName) {
        super(id);
        this.setFoodName(foodName);
    }

    public FoodEntity(int id, String foodName, String preparationText, String cookingTime,
                      String preparationTime, String howManyPerson, int categoryID) {
        super(id);
        this.setFoodName(foodName);
        this.setPreparationText(preparationText);
        this.setCookingTime(cookingTime);
        this.setPreparationTime(preparationTime);
        this.setHowManyPerson(howManyPerson);
        this.setCategoryID(categoryID);
    }

    public FoodEntity(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPreparationText() {
        return preparationText;
    }

    public void setPreparationText(String preparationText) {
        this.preparationText = preparationText;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getHowManyPerson() {
        return howManyPerson;
    }

    public void setHowManyPerson(String howManyPerson) {
        this.howManyPerson = howManyPerson;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
