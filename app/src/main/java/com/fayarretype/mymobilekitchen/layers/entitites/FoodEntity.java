package com.fayarretype.mymobilekitchen.layers.entitites;

public class FoodEntity extends BaseEntity {

    public static int INTERNET_FOOD = 0;
    public static int USER_FOOD = 1;
    private String foodName;
    private String preparationText;
    private String cookingTime;
    private String preparationTime;
    private String howManyPerson;
    private int categoryID;
    private ImageEntity[] image;
    private int type;

    public FoodEntity() {

    }

    public FoodEntity(String id) {
        super(id);
    }

    public FoodEntity(String id, String foodName) {
        super(id);
        this.setFoodName(foodName);
    }

    public FoodEntity(String id, String foodName, String preparationText, String cookingTime,
                      String preparationTime, String howManyPerson, int categoryID) {
        this(id);
        this.setFoodName(foodName);
        this.setPreparationText(preparationText);
        this.setCookingTime(cookingTime);
        this.setPreparationTime(preparationTime);
        this.setHowManyPerson(howManyPerson);
        this.setCategoryID(categoryID);
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

    public ImageEntity[] getImage() {
        return image;
    }

    public void setImage(ImageEntity[] image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
