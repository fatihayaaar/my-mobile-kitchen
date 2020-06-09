package com.fayarretype.mymobilekitchen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fayarretype.mymobilekitchen.activities.AboutActivity;
import com.fayarretype.mymobilekitchen.activities.FoodsActivity;
import com.fayarretype.mymobilekitchen.activities.ProfileActivity;
import com.fayarretype.mymobilekitchen.activities.RecipeBookActivity;
import com.fayarretype.mymobilekitchen.activities.StockActivity;
import com.fayarretype.mymobilekitchen.activities.WizardFoodActivity;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.FoodManager;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.ImageEntity;
import com.fayarretype.mymobilekitchen.tools.utils.ServiceControl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static ImageView imageView;
    private static ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.main_activity_screen_loading);
        scrollView = findViewById(R.id.main_activity_screen);

        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(this);
        FoodManager foodManager = (FoodManager) dataProcessingFactory.getManager(ManagerName.FOOD_MANAGER);

        Date now = new Date();

        @SuppressLint("SimpleDateFormat") DateFormat dfH = new SimpleDateFormat("HH");
        String nowHStr = dfH.format(now);

        if (Integer.valueOf(nowHStr) % 3 == 0 || foodManager.getEntitiesByType(FoodEntity.INTERNET_FOOD).isEmpty()) {
            if (ServiceControl.networkConnection(this)) {
                new GetData(this, getWindow().getDecorView()).execute();
            } else {
                loadCardView();
                imageView.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        } else {
            loadCardView();
            imageView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void loadCardView() {
        androidx.cardview.widget.CardView foodsCardView = findViewById(R.id.foodsCardView);
        foodsCardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FoodsActivity.class);
                FoodsActivity.setMode(FoodsActivity.NORMAL_MODE);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView wizardFoodOptions = findViewById(R.id.wizard_food_activity);
        wizardFoodOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WizardFoodActivity.class);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView profileOptions = findViewById(R.id.profileOptionsCardView);
        profileOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView stockOptions = findViewById(R.id.stockOptions);
        stockOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), StockActivity.class);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView recipeBookOptions = findViewById(R.id.recipeBooksCardView);
        recipeBookOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RecipeBookActivity.class);
                startActivity(intent);
            }
        });

        androidx.cardview.widget.CardView aboutOptions = findViewById(R.id.aboutActivityCardView);
        aboutOptions.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private class GetData extends AsyncTask<Void, Void, Void> {

        private final String URL = "https://www.nefisyemektarifleri.com";
        private Context context;
        private View view;
        private ArrayList<String> foodNames;
        private ArrayList<String> preparationTexts;
        private ArrayList<String> cookingTimes;
        private ArrayList<String> preparationTimes;
        private ArrayList<String> howManyPersons;
        private ArrayList<ArrayList<String>> imgURLs;
        private ImageEntity[][] imageEntities;

        public GetData(Context context, View view) {
            this.context = context;
            this.view = view;
            foodNames = new ArrayList<>();
            preparationTexts = new ArrayList<>();
            cookingTimes = new ArrayList<>();
            preparationTimes = new ArrayList<>();
            howManyPersons = new ArrayList<>();
            imgURLs = new ArrayList<>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            scrollView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            Glide.with(view).load(R.raw.loading).into(imageView);

            if (Build.VERSION.SDK_INT < 16) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        }

        @Override
        protected void onPostExecute(Void mVoid) {
            super.onPostExecute(mVoid);

            loadCardView();
            imageView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(context);
            FoodManager foodManager = (FoodManager) dataProcessingFactory.getManager(ManagerName.FOOD_MANAGER);
            ArrayList<FoodEntity> deleteEntities = foodManager.getEntitiesByType(FoodEntity.INTERNET_FOOD);

            for (FoodEntity entity : deleteEntities) {
                foodManager.delete(entity.getID());
            }

            try {
                Document doc = Jsoup.connect(URL).timeout(30 * 1000).get();
                Elements foodName = doc.select("a[rel=bookmark]");

                for (int i = 0; i < foodName.size(); i++) {
                    foodNames.add(foodName.get(i).text());

                    String foodDetailURL = foodName.get(i).attr("href");
                    Document docFoodDetail = Jsoup.connect(foodDetailURL).timeout(30 * 1000).get();

                    Elements preparationText = docFoodDetail.select("ol[itemprop=recipeInstructions] > li");
                    Elements cookingTime = docFoodDetail.select("strong");
                    Elements preparationTime = docFoodDetail.select("strong");
                    Elements howManyPerson = docFoodDetail.select("span[itemprop=recipeYield]");
                    Elements imgURL = docFoodDetail.select("img[data-lazy-src][class=size-medium][alt]");

                    StringBuilder preparationTextStr = new StringBuilder();
                    preparationTextStr.append("- ");
                    for (Element element : preparationText) {
                        preparationTextStr.append(element.text()).append("\n- ");
                    }
                    if (preparationTextStr.length() >= 20) {
                        preparationTextStr.deleteCharAt(preparationTextStr.length() - 2);
                    }
                    preparationTexts.add(preparationTextStr.toString().trim());

                    StringBuilder cookingTimeStr = new StringBuilder();
                    try {
                        cookingTimeStr.append(cookingTime.get(10).text());
                        if (!(cookingTimeStr.toString().equals(""))) {
                            Integer.valueOf(String.valueOf(cookingTimeStr.toString().charAt(0)));
                            cookingTimes.add(cookingTimeStr.toString());
                        } else {
                            cookingTimes.add(FoodEntity.NULL);
                        }
                    } catch (Exception e) {
                        cookingTimes.add(FoodEntity.NULL);
                    }

                    StringBuilder preparationTimeStr = new StringBuilder();
                    try {
                        preparationTimeStr.append(preparationTime.get(9).text());
                        if (!(preparationTimeStr.toString().equals(""))) {
                            Integer.valueOf(String.valueOf(preparationTimeStr.toString().charAt(0)));
                            preparationTimes.add(preparationTimeStr.toString());
                        } else {
                            preparationTimes.add(FoodEntity.NULL);
                        }
                    } catch (Exception e) {
                        preparationTimes.add(FoodEntity.NULL);
                    }

                    if (howManyPerson.size() > 0) {
                        howManyPersons.add(howManyPerson.get(0).text());
                    } else {
                        howManyPersons.add(FoodEntity.NULL);
                    }

                    ArrayList<String> urls = new ArrayList<>();
                    for (int j = 0; j < imgURL.size(); j++) {
                        String imgSrc = imgURL.get(j).attr("data-lazy-src");
                        if (!(imgSrc.equals(""))) {
                            if (urls.size() < ImageEntity.MAX_IMAGE) {
                                urls.add(imgSrc);
                            }
                        }
                    }
                    imgURLs.add(urls);

                    imageEntities = new ImageEntity[imgURLs.size()][];
                    for (int x = 0; x < imageEntities.length; x++) {
                        imageEntities[x] = new ImageEntity[imgURLs.get(x).size()];
                        for (int j = 0; j < imageEntities[x].length; j++) {
                            imageEntities[x][j] = new ImageEntity();
                            java.net.URL url = null;
                            try {
                                url = new URL(imgURLs.get(x).get(j));
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                            Log.i("Image URL : ", url.toString());

                            Bitmap foodImageBitmap = null;
                            try {
                                foodImageBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            @SuppressLint("SimpleDateFormat")
                            String key = new SimpleDateFormat("yyddHHmmss").format(new Date());

                            imageEntities[x][j].setImageID("web" + key + x + j);
                            imageEntities[x][j].setImage(foodImageBitmap);
                        }

                    }
                    for (int y = 0; y < foodNames.size(); y++) {
                        if (!(preparationTexts.get(y).trim().length() <= 20)) {
                            FoodEntity foodEntity = new FoodEntity();

                            @SuppressLint("SimpleDateFormat")
                            String key = "web" + new SimpleDateFormat("yyddHHmmss").format(new Date()) + i;

                            foodEntity.setID(key);
                            foodEntity.setFoodName(foodNames.get(y));
                            foodEntity.setPreparationText(preparationTexts.get(y));
                            foodEntity.setCookingTime(cookingTimes.get(y));
                            foodEntity.setPreparationTime(preparationTimes.get(y));
                            foodEntity.setHowManyPerson(howManyPersons.get(y));
                            foodEntity.setCategoryID(33);
                            foodEntity.setType(FoodEntity.INTERNET_FOOD);

                            for (int j = 0; j < imageEntities[y].length; j++) {
                                imageEntities[y][j].setFoodID(key);
                            }
                            foodEntity.setImage(imageEntities[y]);

                            foodManager.add(foodEntity);
                        }
                    }
                    foodNames = new ArrayList<>();
                    preparationTexts = new ArrayList<>();
                    cookingTimes = new ArrayList<>();
                    preparationTimes = new ArrayList<>();
                    howManyPersons = new ArrayList<>();
                    imgURLs = new ArrayList<>();
                    System.gc();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            dataProcessingFactory.saveChanges();

            return null;
        }

    }
}
