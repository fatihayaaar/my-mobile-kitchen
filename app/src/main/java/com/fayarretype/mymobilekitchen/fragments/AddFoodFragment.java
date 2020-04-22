package com.fayarretype.mymobilekitchen.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.fayarretype.mymobilekitchen.R;
import com.fayarretype.mymobilekitchen.layers.bl.ManagerName;
import com.fayarretype.mymobilekitchen.layers.bl.DataProcessingFactory;
import com.fayarretype.mymobilekitchen.layers.bl.MaterialManager;
import com.fayarretype.mymobilekitchen.layers.entitites.FoodEntity;
import com.fayarretype.mymobilekitchen.layers.pl.CategoryAdapter;
import com.fayarretype.mymobilekitchen.tools.Convert;
import com.fayarretype.mymobilekitchen.tools.ServiceControl;
import com.fayarretype.mymobilekitchen.tools.dialogbox.DialogBox;
import com.fayarretype.mymobilekitchen.tools.dialogbox.DialogBoxContainer;
import com.fayarretype.mymobilekitchen.tools.dialogbox.DialogBoxName;
import com.fayarretype.mymobilekitchen.tools.validations.NumberValidate;
import com.fayarretype.mymobilekitchen.tools.validations.TextValidate;

public class AddFoodFragment extends Fragment {

    public static final ThreadLocal<AddFoodFragment> ADD_FOOD_FRAGMENT = new ThreadLocal<>();
    private final static int IMAGE_VIEWS_COUNT = 5;
    private final static int CAMERA_REQUEST_CODE = 1000;
    private final static int GALLERY_REQUEST_CODE = 1001;
    private Context context;
    private View view;
    private FragmentActivity fragmentActivity;
    private DialogBox photoOptionsAddBox;
    private ImageView[] imageView;
    private EditText foodNameEditText;
    private EditText foodPreparationEditText;
    private EditText foodCookingTimeEditText;
    private EditText foodPreparationTimeEditText;
    private EditText foodHowManyPersonEditText;
    private Button saveButton;
    private Spinner categoryAddSpinner;
    private String foodName;
    private String foodPreparation;
    private int selectedImageViewElement;
    private int foodCookingTime;
    private int foodPreparationTime;
    private int foodHowManyPerson;

    public AddFoodFragment(Context context) {
        super();
        ADD_FOOD_FRAGMENT.set(this);
        this.context = context;
        this.fragmentActivity = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_food, container, false);

        init();
        loadValues();

        return view;
    }

    private void init() {
        initComponents();
        initPhotoOptionsBox();
        initComponentsEvents();
    }

    private void initComponents() {
        foodNameEditText = view.findViewById(R.id.foodNameEditText);
        foodPreparationEditText = view.findViewById(R.id.foodPreparationTextEditText);
        foodCookingTimeEditText = view.findViewById(R.id.cookingTimeEditText);
        foodPreparationTimeEditText = view.findViewById(R.id.preparationTimeEditText);
        foodHowManyPersonEditText = view.findViewById(R.id.howManyPersonEditText);
        categoryAddSpinner = view.findViewById(R.id.category_add_spinner);
        saveButton = view.findViewById(R.id.saveButton);

        initImageViewList();
    }

    private void initImageViewList() {
        imageView = new ImageView[IMAGE_VIEWS_COUNT];
        imageView[0] = view.findViewById(R.id.first_imageView);
        imageView[1] = view.findViewById(R.id.second_imageView);
        imageView[2] = view.findViewById(R.id.third_imageView);
        imageView[3] = view.findViewById(R.id.fourth_imageView);
        imageView[4] = view.findViewById(R.id.fifth_imageView);

        setSelectedImageViewElement(0);
    }

    private void initComponentsEvents() {
        initEventsImageView();
        initEventsButtons();
    }

    private void initEventsImageView() {
        imageView[getSelectedImageViewElement()].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoOptionsAddBox.show();
            }
        });
    }

    private void initEventsButtons() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButtonOnClick();
            }
        });
    }

    private void initPhotoOptionsBox() {
        photoOptionsAddBox = DialogBoxContainer.getInstance(context, fragmentActivity
                .getSupportFragmentManager())
                .getDialogBox(DialogBoxName.PHOTO_ADD_OPTIONS_DIALOG_BOX);
    }

    private void loadValues() {
        new LoadValuesCategory().start();
        new LoadValuesMaterial().start();
    }

    private void bindToCategorySpinner() {
        CategoryAdapter categoryAdapter = CategoryAdapter.newInstance(context);
        categoryAddSpinner.setAdapter(categoryAdapter);
        categoryAddSpinner.setSelection(categoryAdapter.getCount() - 1);
    }

    private void bindToMaterialsMultiAutoCompleteTextView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                R.layout.materials_row, R.id.materialTextView,
                Convert.getStringArray(((MaterialManager) DataProcessingFactory.getInstance(context)
                        .getManager(ManagerName.MATERIAL_MANAGER)).getNames()));

        MultiAutoCompleteTextView multiAutoCompleteTextView = view
                .findViewById(R.id.materialsFoodAddMultiAutoCompleteTextView);

        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setAdapter(adapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }

    public void pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    imageView[getSelectedImageViewElement()].setImageURI(selectedImage);
                    break;
                case CAMERA_REQUEST_CODE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView[getSelectedImageViewElement()].setImageBitmap(imageBitmap);
                    break;
            }

            photoOptionsAddBox.destroyed();
            setSelectedImageViewElement(getSelectedImageViewElement() + 1);
            loadAddImageIcon();
            initEventsImageView();
        }
    }

    private void loadAddImageIcon() {
        if (getSelectedImageViewElement() == IMAGE_VIEWS_COUNT - 1) {
            return;
        }
        imageView[selectedImageViewElement].setImageDrawable(ContextCompat
                .getDrawable(context, R.drawable.photo_add_image_view));
    }

    private boolean validateControl() {
        boolean c = true;

        if (TextValidate.isEmpty(foodName)) {
            foodNameEditText.setError("Boş geçilemez");
            c = false;
        } else if (!TextValidate.isFormatValidate(foodName)) {
            foodNameEditText.setError("+_=/* gibi karakterler içeremez");
            c = false;
        }

        if (TextValidate.isEmpty(foodPreparation)) {
            foodPreparationEditText.setError("Boş geçilemez");
            c = false;
        } else if (!TextValidate.isRequestedLengthRange(foodPreparation, 20, 1000)) {
            foodPreparationEditText.setError("Çok kısa, en az 20 karakter olmalı");
            c = false;
        }

        if (NumberValidate.isNumberBetween(foodCookingTime, 0, 1000)) {
            foodCookingTimeEditText.setError("0 ile 1000 arasında değer giriniz");
            c = false;
        }

        if (NumberValidate.isNumberBetween(foodPreparationTime, 0, 1000)) {
            foodPreparationTimeEditText.setError("0 ile 1000 arasında değer giriniz");
            c = false;
        }

        if (NumberValidate.isNumberBetween(foodHowManyPerson, 0, 1000)) {
            foodHowManyPersonEditText.setError("0 ile 1000 arasında değer giriniz");
            c = false;
        }

        return c;
    }

    private void saveButtonOnClick() {
        foodName = foodNameEditText.getText().toString();
        foodPreparation = foodPreparationEditText.getText().toString();

        foodCookingTime = TextValidate
                .isEmpty(foodCookingTimeEditText.getText().toString()) ? 0
                : Integer.valueOf(foodCookingTimeEditText.getText().toString());

        foodPreparationTime = TextValidate
                .isEmpty(foodPreparationTimeEditText.getText().toString()) ? 0
                : Integer.valueOf(foodPreparationTimeEditText.getText().toString());

        foodHowManyPerson = TextValidate
                .isEmpty(foodHowManyPersonEditText.getText().toString()) ? 0
                : Integer.valueOf(foodHowManyPersonEditText.getText().toString());

        DialogBox dialogBox = DialogBoxContainer
                .getInstance(context, fragmentActivity.getSupportFragmentManager())
                .getDialogBox(DialogBoxName.ERROR_DIALOG_BOX);

        if (validateControl()) {
            if (!ServiceControl.networkConnection(context)) {
                DialogBoxContainer
                        .getInstance(context, fragmentActivity.getSupportFragmentManager())
                        .getDialogBox(DialogBoxName.INTERNET_CONNECTION_ERROR_DIALOG_BOX)
                        .show();
            } else {
                saveFood();
            }
        } else {
            dialogBox.setTitle("Uyarı");
            dialogBox.setPreparation("Hatalı alanları düzeltiniz.");
            dialogBox.show();
        }

        ScrollView foodAddScrollView = this.view.findViewById(R.id.foodAddScrollView);
        foodAddScrollView.pageScroll(View.FOCUS_UP);
    }

    private void saveFood() {
        FoodEntity food = new FoodEntity();
        food.setFoodName(foodName);
        food.setPreparationText(foodPreparation);
        food.setCookingTime(String.valueOf(foodCookingTime));
        food.setPreparationTime(String.valueOf(foodPreparationTime));
        food.setHowManyPerson(String.valueOf(foodHowManyPerson));
        food.setCategoryID(categoryAddSpinner.getSelectedItemPosition());
        food.setImage(null);

        saveImage();

        DataProcessingFactory dataProcessingFactory = DataProcessingFactory.getInstance(context);
        dataProcessingFactory.getManager(ManagerName.FOOD_MANAGER).add(food);
        dataProcessingFactory.saveChanges();
    }

    private void saveImage() {

    }

    public int getSelectedImageViewElement() {
        return selectedImageViewElement;
    }

    public void setSelectedImageViewElement(int selectedImageViewElement) {
        if (selectedImageViewElement < IMAGE_VIEWS_COUNT) {
            this.selectedImageViewElement = selectedImageViewElement;
        }
    }

    private class LoadValuesCategory implements Runnable {
        Thread thread;

        @Override
        public void run() {
            bindToCategorySpinner();
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }

    private class LoadValuesMaterial implements Runnable {
        Thread thread;

        @Override
        public void run() {
            bindToMaterialsMultiAutoCompleteTextView();
        }

        void start() {
            if (thread == null) {
                thread = new Thread(this);
                thread.run();
            }
        }
    }
}