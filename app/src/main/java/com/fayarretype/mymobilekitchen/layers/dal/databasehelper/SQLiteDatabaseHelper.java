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
import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialByFoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;
import com.fayarretype.mymobilekitchen.tools.utils.Convert;

import java.util.ArrayList;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper implements IDatabaseHelper<BaseEntity> {

    public static final String FOOD_AREA_ID = "foodID";
    public static final String FOOD_AREA_CATEGORY_ID = "categoryID";
    public static final String CATEGORY_AREA_ID = "categoryID";
    public static final String MATERIAL_AREA_ID = "materialID";
    public static final String MATERIAL_AREA_IS_STOCK = "isStock";
    public static final String IMAGES_AREA_ID = "imagesID";
    public static final String IMAGES_AREA_FOOD_ID = "foodID";
    public static final String FOOD_AREA_TYPE = "foodType";
    public static final String MATERIAL_AREA_NAME = "materialName";
    public static final String MATERIAL_BY_FOOD_ID = "materialByFoodID";
    public static final String MATERIAL_BY_FOOD_FOOD_ID = "materialByFoodFoodID";
    public static final String IMAGES_AREA_IMAGE_ID = "imageID";
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
    private static final String MATERIAL_AREA_COUNT = "materialCount";
    private static final String MATERIAL_BY_FOOD_TABLE_NAME = "materialByFood";
    private static final String MATERIAL_BY_FOOD_MATERIAL_ID = "materialByFoodMaterialID";
    private static SQLiteDatabaseHelper databaseHelper;
    private final int CONTENT_VALUES_INSERT = 0;
    private final int CONTENT_VALUES_UPDATE = 1;
    private final int CONTENT_VALUES_CATEGORY = 0;
    private final int CONTENT_VALUES_MATERIAL = 1;
    private final int CONTENT_VALUES_FOOD = 2;
    private final int CONTENT_VALUES_IMAGE = 3;
    private final int CONTENT_VALUES_MATERIAL_BY_FOOD = 4;
    private ArrayList<ArrayList<ArrayList<ContentValues>>> contentValues;
    private ArrayList<ArrayList<String>> deleteIDList;
    private ArrayList<ArrayList<String>> updateIDList;
    private SQLiteDatabase database;

    private SQLiteDatabaseHelper(Context context) {
        super(context, "my_mobile_kitchen.db", null, 29);
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
        contentValues.get(CONTENT_VALUES_INSERT).add(CONTENT_VALUES_IMAGE, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_INSERT).add(CONTENT_VALUES_MATERIAL_BY_FOOD, new ArrayList<ContentValues>());

        contentValues.get(CONTENT_VALUES_UPDATE).add(CONTENT_VALUES_CATEGORY, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_UPDATE).add(CONTENT_VALUES_MATERIAL, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_UPDATE).add(CONTENT_VALUES_FOOD, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_UPDATE).add(CONTENT_VALUES_IMAGE, new ArrayList<ContentValues>());
        contentValues.get(CONTENT_VALUES_UPDATE).add(CONTENT_VALUES_MATERIAL_BY_FOOD, new ArrayList<ContentValues>());

        deleteIDList = new ArrayList<>();
        deleteIDList.add(CONTENT_VALUES_CATEGORY, new ArrayList<String>());
        deleteIDList.add(CONTENT_VALUES_MATERIAL, new ArrayList<String>());
        deleteIDList.add(CONTENT_VALUES_FOOD, new ArrayList<String>());
        deleteIDList.add(CONTENT_VALUES_IMAGE, new ArrayList<String>());
        deleteIDList.add(CONTENT_VALUES_MATERIAL_BY_FOOD, new ArrayList<String>());

        updateIDList = new ArrayList<>();
        updateIDList.add(CONTENT_VALUES_CATEGORY, new ArrayList<String>());
        updateIDList.add(CONTENT_VALUES_MATERIAL, new ArrayList<String>());
        updateIDList.add(CONTENT_VALUES_FOOD, new ArrayList<String>());
        updateIDList.add(CONTENT_VALUES_IMAGE, new ArrayList<String>());
        updateIDList.add(CONTENT_VALUES_MATERIAL_BY_FOOD, new ArrayList<String>());
    }

    public void finish() {
        init();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                CATEGORY_AREA_ID + " TEXT," +
                CATEGORY_AREA_NAME + " TEXT NOT NULL );");

        db.execSQL("CREATE TABLE " + MATERIAL_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                MATERIAL_AREA_ID + " TEXT," +
                MATERIAL_AREA_NAME + " TEXT NOT NULL," +
                MATERIAL_AREA_COUNT + " TEXT," +
                MATERIAL_AREA_IS_STOCK + " TEXT);");

        db.execSQL("CREATE TABLE " + FOOD_TABLE_NAME + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                FOOD_AREA_ID + " TEXT," +
                FOOD_AREA_NAME + " TEXT NOT NULL," +
                FOOD_AREA_PREPARATION + " TEXT," +
                FOOD_AREA_COOKING_TIME + " TEXT," +
                FOOD_AREA_PREPARATION_TIME + " TEXT," +
                FOOD_AREA_HOW_MANY_PERSON + " TEXT," +
                FOOD_AREA_CATEGORY_ID + " INTEGER," +
                FOOD_AREA_TYPE + " TEXT );");

        db.execSQL("CREATE TABLE " + IMAGES_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                IMAGES_AREA_ID + " TEXT," +
                IMAGES_AREA_FOOD_ID + " INTEGER NOT NULL," +
                IMAGES_AREA_IMAGE_ID + " INTEGER NOT NULL);");

        db.execSQL("CREATE TABLE " + MATERIAL_BY_FOOD_TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                MATERIAL_BY_FOOD_ID + " TEXT," +
                MATERIAL_BY_FOOD_MATERIAL_ID + " TEXT NOT NULL," +
                MATERIAL_BY_FOOD_FOOD_ID + " TEXT NOT NULL);");

        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(0, '1', 'meze')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(1, '2', 'bebek maması')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(2, '3', 'hamur işi')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(3, '4', 'içecek')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(4, '5', 'kahvaltı')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(5, '6', 'kek')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(6, '7', 'pasta')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(7, '8', 'tavuk')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(8, '9', 'kurabiye')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(9, '10', 'diyet')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(10, '11', 'şekerlemeler')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(11, '12', 'balık')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(12, '13', 'hamburger')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(13, '14', 'dondurma')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(14, '15', 'reçel')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(15, '16', 'köfte')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(16, '17', 'zeytin yağı')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(17, '18', 'turşu')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(18, '19', 'turta')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(19, '20', 'pizza')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(20, '21', 'pilav')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(21, '22', 'salata')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(22, '23', 'sandviç')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(23, '24', 'abur cubur')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(24, '25', 'çorba')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(25, '26', 'spagetti')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(26, '27', 'biftek')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(27, '28', 'yöresel')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(28, '29', 'vegan')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(29, '30', 'sebzeler')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(30, '31', 'vejeteryan')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(31, '32', 'dünya sofrası')");
        db.execSQL("INSERT INTO " + CATEGORY_TABLE_NAME + " VALUES(32, '33', 'diğer')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void add(BaseEntity entity) {
        ContentValues contentValues = new ContentValues();

        if (entity.getClass() == CategoryEntity.class) {
            contentValues.put(CATEGORY_AREA_ID, entity.getID());
            contentValues.put(CATEGORY_AREA_NAME, ((CategoryEntity) entity).getCategoryName());
            this.contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_CATEGORY).add(contentValues);
        } else if (entity.getClass() == MaterialEntity.class) {
            contentValues.put(MATERIAL_AREA_ID, entity.getID());
            contentValues.put(MATERIAL_AREA_NAME, ((MaterialEntity) entity).getMaterialName());
            contentValues.put(MATERIAL_AREA_COUNT, ((MaterialEntity) entity).getMaterialCount());
            contentValues.put(MATERIAL_AREA_IS_STOCK, ((MaterialEntity) entity).getIsItInStock());
            this.contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_MATERIAL).add(contentValues);
        } else if (entity.getClass() == FoodEntity.class) {
            contentValues.put(FOOD_AREA_ID, entity.getID());
            contentValues.put(FOOD_AREA_NAME, ((FoodEntity) entity).getFoodName());
            contentValues.put(FOOD_AREA_PREPARATION, ((FoodEntity) entity).getPreparationText());
            contentValues.put(FOOD_AREA_COOKING_TIME, ((FoodEntity) entity).getCookingTime());
            contentValues.put(FOOD_AREA_PREPARATION_TIME, ((FoodEntity) entity).getPreparationTime());
            contentValues.put(FOOD_AREA_HOW_MANY_PERSON, ((FoodEntity) entity).getHowManyPerson());
            contentValues.put(FOOD_AREA_CATEGORY_ID, ((FoodEntity) entity).getCategoryID());
            contentValues.put(FOOD_AREA_TYPE, ((FoodEntity) entity).getType());
            this.contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_FOOD).add(contentValues);
        } else if (entity.getClass() == EntityName.IMAGE_ENTITY_CLASS) {
            contentValues.put(IMAGES_AREA_ID, entity.getID());
            contentValues.put(IMAGES_AREA_FOOD_ID, ((ImageEntity) entity).getFoodID());
            contentValues.put(IMAGES_AREA_IMAGE_ID, ((ImageEntity) entity).getImageID());
            this.contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_IMAGE).add(contentValues);
        } else if (entity.getClass() == EntityName.MATERIAL_BY_FOOD_CLASS) {
            contentValues.put(MATERIAL_BY_FOOD_ID, entity.getID());
            contentValues.put(MATERIAL_BY_FOOD_MATERIAL_ID, ((MaterialByFoodEntity) entity).getMaterialID());
            contentValues.put(MATERIAL_BY_FOOD_FOOD_ID, ((MaterialByFoodEntity) entity).getFoodID());
            this.contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_MATERIAL_BY_FOOD).add(contentValues);
        }
    }

    @Override
    public void delete(String id, Class entity) {
        if (entity.getName().equals(EntityName.CATEGORY_ENTITY_CLASS.getName()))
            deleteIDList.get(CONTENT_VALUES_CATEGORY).add(id);
        else if (entity.getName().equals(EntityName.MATERIAL_ENTITY_CLASS.getName()))
            deleteIDList.get(CONTENT_VALUES_MATERIAL).add(id);
        else if (entity.getName().equals(EntityName.FOOD_ENTITY_CLASS.getName()))
            deleteIDList.get(CONTENT_VALUES_FOOD).add(id);
        else if (entity.getName().equals(EntityName.IMAGE_ENTITY_CLASS.getName()))
            deleteIDList.get(CONTENT_VALUES_IMAGE).add(id);
        else if (entity.getName().equals(EntityName.MATERIAL_BY_FOOD_CLASS.getName()))
            deleteIDList.get(CONTENT_VALUES_MATERIAL_BY_FOOD).add(id);
    }

    @Override
    public void update(BaseEntity entity, String id) {
        ContentValues contentValues = new ContentValues();

        if (entity.getClass() == CategoryEntity.class) {
            contentValues.put(CATEGORY_AREA_ID, entity.getID());
            contentValues.put(CATEGORY_AREA_NAME, ((CategoryEntity) entity).getCategoryName());
            this.contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_CATEGORY).add(contentValues);
            updateIDList.get(CONTENT_VALUES_CATEGORY).add(id);
        } else if (entity.getClass() == MaterialEntity.class) {
            contentValues.put(MATERIAL_AREA_ID, entity.getID());
            contentValues.put(MATERIAL_AREA_NAME, ((MaterialEntity) entity).getMaterialName());
            contentValues.put(MATERIAL_AREA_COUNT, ((MaterialEntity) entity).getMaterialCount());
            contentValues.put(MATERIAL_AREA_IS_STOCK, ((MaterialEntity) entity).getIsItInStock());
            this.contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_MATERIAL).add(contentValues);
            updateIDList.get(CONTENT_VALUES_MATERIAL).add(id);
        } else if (entity.getClass() == FoodEntity.class) {
            contentValues.put(FOOD_AREA_ID, entity.getID());
            contentValues.put(FOOD_AREA_NAME, ((FoodEntity) entity).getFoodName());
            contentValues.put(FOOD_AREA_PREPARATION, ((FoodEntity) entity).getPreparationText());
            contentValues.put(FOOD_AREA_COOKING_TIME, ((FoodEntity) entity).getCookingTime());
            contentValues.put(FOOD_AREA_PREPARATION_TIME, ((FoodEntity) entity).getPreparationTime());
            contentValues.put(FOOD_AREA_HOW_MANY_PERSON, ((FoodEntity) entity).getHowManyPerson());
            contentValues.put(FOOD_AREA_CATEGORY_ID, ((FoodEntity) entity).getCategoryID());
            this.contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_FOOD).add(contentValues);
            updateIDList.get(CONTENT_VALUES_FOOD).add(id);
        } else if (entity.getClass() == EntityName.IMAGE_ENTITY_CLASS) {
            contentValues.put(IMAGES_AREA_ID, entity.getID());
            contentValues.put(IMAGES_AREA_FOOD_ID, ((ImageEntity) entity).getFoodID());
            contentValues.put(IMAGES_AREA_IMAGE_ID, ((ImageEntity) entity).getImageID());
            this.contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_IMAGE).add(contentValues);
            updateIDList.get(CONTENT_VALUES_IMAGE).add(id);
        } else if (entity.getClass() == EntityName.MATERIAL_BY_FOOD_CLASS) {
            contentValues.put(MATERIAL_BY_FOOD_ID, entity.getID());
            contentValues.put(MATERIAL_BY_FOOD_MATERIAL_ID, ((MaterialByFoodEntity) entity).getMaterialID());
            contentValues.put(MATERIAL_BY_FOOD_FOOD_ID, ((MaterialByFoodEntity) entity).getFoodID());
            this.contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_MATERIAL_BY_FOOD).add(contentValues);
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

        if (selectionValue != null || selectionValue2 != null) {
            //selection = selectionValue + " = " + selectionValue2;
            //if (selectionValue.equals(MATERIAL_AREA_NAME) || selectionValue.equals(MATERIAL_AREA_IS_STOCK)) {
            selection = selectionValue + " = '" + selectionValue2 + "'";
            //}
        } else selection = "";

        groupBy = "";
        having = "";
        orderBy = "";

        if (entity == EntityName.FOOD_ENTITY_CLASS) {
            columns.add(FOOD_AREA_ID);
            columns.add(FOOD_AREA_NAME);
            columns.add(FOOD_AREA_PREPARATION);
            columns.add(FOOD_AREA_COOKING_TIME);
            columns.add(FOOD_AREA_PREPARATION_TIME);
            columns.add(FOOD_AREA_HOW_MANY_PERSON);
            columns.add(FOOD_AREA_CATEGORY_ID);
            columns.add(FOOD_AREA_TYPE);

            Cursor cursor = database.query(FOOD_TABLE_NAME, Convert.getStringArray(columns),
                    selection, new String[]{}, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                records.add(cursor.getColumnIndex((FOOD_AREA_ID)),
                        new FoodEntity(cursor.getString(cursor.getColumnIndex(FOOD_AREA_ID)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_NAME)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_PREPARATION)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_COOKING_TIME)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_PREPARATION_TIME)),
                                cursor.getString(cursor.getColumnIndex(FOOD_AREA_HOW_MANY_PERSON)),
                                cursor.getInt(cursor.getColumnIndex(FOOD_AREA_CATEGORY_ID)),
                                cursor.getInt(cursor.getColumnIndex(FOOD_AREA_TYPE))));
            }

        } else if (entity == EntityName.CATEGORY_ENTITY_CLASS) {
            columns.add(CATEGORY_AREA_ID);
            columns.add(CATEGORY_AREA_NAME);

            Cursor cursor = database.query(CATEGORY_TABLE_NAME, Convert.getStringArray(columns),
                    selection, new String[]{}, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                records.add(cursor.getColumnIndex(CATEGORY_AREA_ID),
                        new CategoryEntity(cursor.getString(cursor.getColumnIndex(CATEGORY_AREA_ID)),
                                cursor.getString(cursor.getColumnIndex(CATEGORY_AREA_NAME))));
            }

        } else if (entity == EntityName.MATERIAL_ENTITY_CLASS) {
            columns.add(MATERIAL_AREA_ID);
            columns.add(MATERIAL_AREA_NAME);
            columns.add(MATERIAL_AREA_COUNT);
            columns.add(MATERIAL_AREA_IS_STOCK);

            Cursor cursor = database.query(MATERIAL_TABLE_NAME, Convert.getStringArray(columns),
                    selection, new String[]{}, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                records.add(cursor.getColumnIndex(MATERIAL_AREA_ID),
                        new MaterialEntity(cursor.getString(cursor.getColumnIndex(MATERIAL_AREA_ID)),
                                cursor.getString(cursor.getColumnIndex(MATERIAL_AREA_NAME)),
                                cursor.getString(cursor.getColumnIndex(MATERIAL_AREA_COUNT)),
                                cursor.getString(cursor.getColumnIndex(MATERIAL_AREA_IS_STOCK))));
            }

        } else if (entity == EntityName.IMAGE_ENTITY_CLASS) {
            columns.add(IMAGES_AREA_ID);
            columns.add(IMAGES_AREA_FOOD_ID);
            columns.add(IMAGES_AREA_IMAGE_ID);

            Cursor cursor = database.query(IMAGES_TABLE_NAME, Convert.getStringArray(columns),
                    selection, new String[]{}, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                records.add(cursor.getColumnIndex(IMAGES_AREA_ID),
                        new ImageEntity(cursor.getString(cursor.getColumnIndex(IMAGES_AREA_ID)),
                                cursor.getString(cursor.getColumnIndex(IMAGES_AREA_FOOD_ID)),
                                cursor.getString(cursor.getColumnIndex(IMAGES_AREA_IMAGE_ID))));
            }

        } else if (entity == EntityName.MATERIAL_BY_FOOD_CLASS) {
            columns.add(MATERIAL_BY_FOOD_ID);
            columns.add(MATERIAL_BY_FOOD_MATERIAL_ID);
            columns.add(MATERIAL_BY_FOOD_FOOD_ID);

            Cursor cursor = database.query(MATERIAL_BY_FOOD_TABLE_NAME, Convert.getStringArray(columns),
                    selection, new String[]{}, groupBy, having, orderBy);

            while (cursor.moveToNext()) {
                records.add(cursor.getColumnIndex(MATERIAL_BY_FOOD_ID),
                        new MaterialByFoodEntity(cursor.getString(cursor.getColumnIndex(MATERIAL_BY_FOOD_ID)),
                                cursor.getString(cursor.getColumnIndex(MATERIAL_BY_FOOD_MATERIAL_ID)),
                                cursor.getString(cursor.getColumnIndex(MATERIAL_BY_FOOD_FOOD_ID))));
            }

        } else records.add(-1, new CategoryEntity("-1"));

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
                        FOOD_AREA_ID + " = '" + deleteIDList.get(CONTENT_VALUES_FOOD).get(i) + "'", null);
            }
        } finally {
        }
    }

    @Override
    public void imageSave() {
        connect();
        try {
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_IMAGE).size(); i++) {
                database.insert(IMAGES_TABLE_NAME, null,
                        contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_IMAGE).get(i));
            }
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_IMAGE).size(); i++) {
                database.update(IMAGES_TABLE_NAME, contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_IMAGE).get(i),
                        IMAGES_AREA_ID + " = " + updateIDList.get(CONTENT_VALUES_IMAGE).get(i), null);
            }
            for (int i = 0; i < deleteIDList.get(CONTENT_VALUES_IMAGE).size(); i++) {
                database.delete(IMAGES_TABLE_NAME,
                        IMAGES_AREA_ID + " = " + deleteIDList.get(CONTENT_VALUES_IMAGE).get(i), null);
            }
        } finally {
            disconnect();
        }
    }

    @Override
    public void materialByFoodSave() {
        connect();
        try {
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_MATERIAL_BY_FOOD).size(); i++) {
                database.insert(MATERIAL_BY_FOOD_TABLE_NAME, null,
                        contentValues.get(CONTENT_VALUES_INSERT).get(CONTENT_VALUES_MATERIAL_BY_FOOD).get(i));
            }
            for (int i = 0; i < contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_MATERIAL_BY_FOOD).size(); i++) {
                database.update(MATERIAL_BY_FOOD_TABLE_NAME, contentValues.get(CONTENT_VALUES_UPDATE).get(CONTENT_VALUES_MATERIAL_BY_FOOD).get(i),
                        MATERIAL_BY_FOOD_ID + " = " + updateIDList.get(CONTENT_VALUES_MATERIAL_BY_FOOD).get(i), null);
            }
            for (int i = 0; i < deleteIDList.get(CONTENT_VALUES_MATERIAL_BY_FOOD).size(); i++) {
                database.delete(MATERIAL_BY_FOOD_TABLE_NAME,
                        MATERIAL_BY_FOOD_ID + " = " + deleteIDList.get(CONTENT_VALUES_MATERIAL_BY_FOOD).get(i), null);
            }
        } finally {
            disconnect();
        }
    }
}
