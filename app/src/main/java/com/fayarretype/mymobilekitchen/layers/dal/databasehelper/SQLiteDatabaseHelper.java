package com.fayarretype.mymobilekitchen.layers.dal.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fayarretype.mymobilekitchen.layers.dal.repositories.EntityName;
import com.fayarretype.mymobilekitchen.layers.entitites.BaseEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;
import com.fayarretype.mymobilekitchen.tools.Convert;

import java.util.ArrayList;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper implements IDatabaseHelper<BaseEntity> {

    public static final String FOOD_AREA_ID = "foodID";
    public static final String FOOD_AREA_CATEGORY_ID = "categoryID";
    public static final String CATEGORY_AREA_ID = "categoryID";
    public static final String MATERIAL_AREA_ID = "materialID";
    private static final String FOOD_TABLE_NAME = "Food";
    private static final String CATEGORY_TABLE_NAME = "Category";
    private static final String MATERIAL_TABLE_NAME = "Material";
    private static final String IMAGES_TABLE_NAME = "FoodImages";
    private static final String FOOD_AREA_NAME = "foodName";
    private static final String FOOD_AREA_PREPARATION = "preparationText";
    private static final String FOOD_AREA_COOKING_TIME = "cookingTime";
    private static final String FOOD_AREA_PREPARATION_TIME = "preparationTime";
    private static final String FOOD_AREA_HOW_MANY_PERSON = "howManyPerson";
    private static final String CATEGORY_AREA_NAME = "categoryName";
    private static final String MATERIAL_AREA_NAME = "materialName";
    private static final String IMAGES_AREA_IMAGES_ID = "imagesID";
    private static final String IMAGES_AREA_FOOD_ID = "foodID";
    private static final String IMAGES_AREA_IMAGE_URL = "imageURL";
    private static SQLiteDatabaseHelper databaseHelper;
    private final int CONTENT_VALUES_INSERT = 0;
    private final int CONTENT_VALUES_UPDATE = 1;
    private final int CONTENT_VALUES_CATEGORY = 0;
    private final int CONTENT_VALUES_MATERIAL = 1;
    private final int CONTENT_VALUES_FOOD = 2;
    private ArrayList<ArrayList<ArrayList<ContentValues>>> contentValues;
    private ArrayList<ArrayList<Integer>> deleteIDList;
    private ArrayList<ArrayList<Integer>> updateIDList;
    private SQLiteDatabase database;

    private SQLiteDatabaseHelper(Context context) {
        super(context, "my_mobile_kitchen.db", null, 1);
        init();
    }

    public static SQLiteDatabaseHelper getInstance(Context context) {
        if (databaseHelper == null)
            databaseHelper = new SQLiteDatabaseHelper(context);
        return databaseHelper;
    }

    private void init() {
        contentValues = new ArrayList<>();
        contentValues.add(CONTENT_VALUES_INSERT, new ArrayList<ArrayList<ContentValues>>());
        contentValues.add(CONTENT_VALUES_UPDATE, new ArrayList<ArrayList<ContentValues>>());
        contentValues.get(CONTENT_VALUES_INSERT).add(CONTENT_VALUES_CATEGORY, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_INSERT).add(CONTENT_VALUES_MATERIAL, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_INSERT).add(CONTENT_VALUES_FOOD, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_UPDATE).add(CONTENT_VALUES_CATEGORY, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_UPDATE).add(CONTENT_VALUES_MATERIAL, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_UPDATE).add(CONTENT_VALUES_FOOD, new ArrayList<ContentValues>());
        deleteIDList = new ArrayList<>();
        deleteIDList.add(CONTENT_VALUES_CATEGORY, new ArrayList<Integer>());
        deleteIDList.add(CONTENT_VALUES_MATERIAL, new ArrayList<Integer>());
        deleteIDList.add(CONTENT_VALUES_FOOD, new ArrayList<Integer>());
        updateIDList = new ArrayList<>();
        updateIDList.add(CONTENT_VALUES_CATEGORY, new ArrayList<Integer>());
        updateIDList.add(CONTENT_VALUES_MATERIAL, new ArrayList<Integer>());
        updateIDList.add(CONTENT_VALUES_FOOD, new ArrayList<Integer>());
    }

    public void finish() {
        init();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
                CATEGORY_AREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CATEGORY_AREA_NAME + " TEXT NOT NULL );");

        db.execSQL("CREATE TABLE " + MATERIAL_TABLE_NAME + " (" +
                MATERIAL_AREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MATERIAL_AREA_NAME + " TEXT NOT NULL );");

        db.execSQL("CREATE TABLE " + FOOD_TABLE_NAME + " ( " +
                FOOD_AREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FOOD_AREA_NAME + " TEXT NOT NULL," +
                FOOD_AREA_PREPARATION + " TEXT," +
                FOOD_AREA_COOKING_TIME + " TEXT," +
                FOOD_AREA_PREPARATION_TIME + " TEXT," +
                FOOD_AREA_HOW_MANY_PERSON + " TEXT," +
                FOOD_AREA_CATEGORY_ID + " INTEGER );");

       // db.execSQL("CREATE TABLE " + IMAGES_TABLE_NAME + " (" +
         //       );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void add(BaseEntity entity) {
        ContentValues contentValues = new ContentValues();

        if (entity.getClass() == CategoryEntity.class) {
            contentValues.put(CATEGORY_AREA_NAME, ((CategoryEntity) entity).getCategoryName());
            this.contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_CATEGORY).add(contentValues);
        } else if (entity.getClass() == MaterialEntity.class) {
            contentValues.put(MATERIAL_AREA_NAME, ((MaterialEntity) entity).getMaterialName());
            this.contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_MATERIAL).add(contentValues);
        } else if (entity.getClass() == FoodEntity.class) {
            contentValues.put(FOOD_AREA_NAME, ((FoodEntity) entity).getFoodName());
            contentValues.put(FOOD_AREA_PREPARATION, ((FoodEntity) entity).getPreparationText());
            contentValues.put(FOOD_AREA_COOKING_TIME, ((FoodEntity) entity).getCookingTime());
            contentValues.put(FOOD_AREA_PREPARATION_TIME, ((FoodEntity) entity).getPreparationTime());
            contentValues.put(FOOD_AREA_HOW_MANY_PERSON, ((FoodEntity) entity).getHowManyPerson());
            contentValues.put(FOOD_AREA_CATEGORY_ID, entity.getID());
            this.contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_FOOD).add(contentValues);
        }
    }

    @Override
    public void delete(int id, Class entity) {
        if (entity.getName().equals(EntityName.CATEGORY_ENTITY_CLASS.getName()))
            deleteIDList.get(CONTENT_VALUES_CATEGORY).add(id);
        else if (entity.getName().equals(EntityName.MATERIAL_ENTITY_CLASS.getName()))
            deleteIDList.get(CONTENT_VALUES_MATERIAL).add(id);
        else if (entity.getName().equals(EntityName.FOOD_ENTITY_CLASS.getName()))
            deleteIDList.get(CONTENT_VALUES_FOOD).add(id);
    }

    @Override
    public void update(BaseEntity entity, int id) {
        ContentValues contentValues = new ContentValues();

        if (entity.getClass() == CategoryEntity.class) {
            contentValues.put(CATEGORY_AREA_NAME, ((CategoryEntity) entity).getCategoryName());
            this.contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_CATEGORY).add(contentValues);
            updateIDList.get(CONTENT_VALUES_CATEGORY).add(id);
        } else if (entity.getClass() == MaterialEntity.class) {
            contentValues.put(MATERIAL_AREA_NAME, ((MaterialEntity) entity).getMaterialName());
            this.contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_MATERIAL).add(contentValues);
            updateIDList.get(CONTENT_VALUES_MATERIAL).add(id);
        } else if (entity.getClass() == FoodEntity.class) {
            contentValues.put(FOOD_AREA_NAME, ((FoodEntity) entity).getFoodName());
            contentValues.put(FOOD_AREA_PREPARATION, ((FoodEntity) entity).getPreparationText());
            contentValues.put(FOOD_AREA_COOKING_TIME, ((FoodEntity) entity).getCookingTime());
            contentValues.put(FOOD_AREA_PREPARATION_TIME, ((FoodEntity) entity).getPreparationTime());
            contentValues.put(FOOD_AREA_HOW_MANY_PERSON, ((FoodEntity) entity).getHowManyPerson());
            contentValues.put(FOOD_AREA_CATEGORY_ID, entity.getID());
            this.contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_FOOD).add(contentValues);
            updateIDList.get(CONTENT_VALUES_FOOD).add(id);
        }
    }

    @Override
    public ArrayList<BaseEntity> list(Class entity) {
        return list(entity, null, null);
    }

    @Override
    public ArrayList<BaseEntity> list(Class entity, String selectionValue, String selectionValue2) {
        ArrayList<BaseEntity> records = new ArrayList<>();
        database = this.getReadableDatabase();

        ArrayList<String> columns = new ArrayList<>();
        String selection, groupBy, having, orderBy;

        if (selectionValue != null || selectionValue2 != null)
            selection = selectionValue + " = " + selectionValue2;
        else selection = "";

        groupBy = "";
        having = "";
        orderBy = "";

        if (entity == EntityName.FOOD_ENTITY_CLASS) {
            columns.add(FOOD_AREA_ID);
            columns.add(FOOD_AREA_NAME);

            Cursor cursor = database.query(FOOD_TABLE_NAME, Convert.getStringArray(columns),
                    selection, new String[]{}, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                records.add(cursor.getColumnIndex(FOOD_AREA_ID),
                        new FoodEntity(cursor.getColumnIndex(FOOD_AREA_ID),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_NAME)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_PREPARATION)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_COOKING_TIME)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_PREPARATION_TIME)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_HOW_MANY_PERSON)),
                                cursor.getColumnIndex(FOOD_AREA_CATEGORY_ID)));
            }

        } else if (entity == EntityName.CATEGORY_ENTITY_CLASS) {
            columns.add(CATEGORY_AREA_ID);
            columns.add(CATEGORY_AREA_NAME);

            Cursor cursor = database.query(CATEGORY_TABLE_NAME, Convert.getStringArray(columns),
                    selection, new String[]{}, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                records.add(cursor.getColumnIndex(CATEGORY_AREA_ID),
                        new CategoryEntity(cursor.getColumnIndex(CATEGORY_AREA_ID),
                                cursor.getString(cursor.getColumnIndex(CATEGORY_AREA_NAME))));
            }

        } else if (entity == EntityName.MATERIAL_ENTITY_CLASS) {
            columns.add(MATERIAL_AREA_ID);
            columns.add(MATERIAL_AREA_NAME);

            Cursor cursor = database.query(MATERIAL_TABLE_NAME, Convert.getStringArray(columns),
                    selection, new String[]{}, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                records.add(cursor.getColumnIndex(MATERIAL_AREA_ID),
                        new MaterialEntity(cursor.getColumnIndex(MATERIAL_AREA_ID),
                                cursor.getString(cursor.getColumnIndex(MATERIAL_AREA_NAME))));
            }

        } else records.add(-1, new CategoryEntity(-1));

        disconnect();

        return records;
    }

    @Override
    public void connect() {
        database = this.getWritableDatabase();
    }

    @Override
    public void disconnect() {
        database.close();
    }

    @Override
    public void categorySave() {
        connect();
        try {
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_CATEGORY).size(); i++) {
                database.insert(CATEGORY_TABLE_NAME, null,
                        contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_CATEGORY).get(i));
            }
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_CATEGORY).size(); i++) {
                database.update(CATEGORY_TABLE_NAME, contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_CATEGORY).get(i),
                        CATEGORY_AREA_ID + " = " + updateIDList.get(CONTENT_VALUES_CATEGORY).get(i), null);
            }
            for (int i = 0; i < deleteIDList.get(CONTENT_VALUES_CATEGORY).size(); i++) {
                database.delete(CATEGORY_TABLE_NAME,
                        CATEGORY_AREA_ID + " = " + deleteIDList.get(CONTENT_VALUES_CATEGORY).get(i), null);
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void materialSave() {
        connect();
        try {
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_MATERIAL).size(); i++) {
                database.insert(MATERIAL_TABLE_NAME, null,
                        contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_MATERIAL).get(i));
            }
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_MATERIAL).size(); i++) {
                database.update(MATERIAL_TABLE_NAME, contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_MATERIAL).get(i),
                        MATERIAL_AREA_ID + " = " + updateIDList.get(CONTENT_VALUES_MATERIAL).get(i), null);
            }
            for (int i = 0; i < deleteIDList.get(CONTENT_VALUES_MATERIAL).size(); i++) {
                database.delete(MATERIAL_TABLE_NAME,
                        MATERIAL_AREA_ID + " = " + deleteIDList.get(CONTENT_VALUES_MATERIAL).get(i), null);
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void foodSave() {
        connect();
        try {
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_FOOD).size(); i++) {
                database.insert(FOOD_TABLE_NAME, null,
                        contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_FOOD).get(i));
            }
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_FOOD).size(); i++) {
                database.update(FOOD_TABLE_NAME, contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_FOOD).get(i),
                        FOOD_AREA_ID + " = " + updateIDList.get(CONTENT_VALUES_FOOD).get(i), null);
            }
            for (int i = 0; i < deleteIDList.get(CONTENT_VALUES_FOOD).size(); i++) {
                database.delete(FOOD_TABLE_NAME,
                        FOOD_AREA_ID + " = " + deleteIDList.get(CONTENT_VALUES_FOOD).get(i), null);
            }
        } finally {
            disconnect();
        }
    }
}
