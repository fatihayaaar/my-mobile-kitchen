package com.fayarretype.mymobilekitchen.layers.dal;

import android.content.Context;

import com.fayarretype.mymobilekitchen.layers.entitites.CategoryEntity;
import com.fayarretype.mymobilekitchen.layers.entitites.MaterialEntity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class XMLPullParserHandler {

    private static XMLPullParserHandler xmlPullParserHandler;
    private XmlPullParserFactory factory;
    private XmlPullParser parser;
    private MaterialEntity materialEntity;
    private CategoryEntity categoryEntity;
    private String text;
    private Context context;

    private XMLPullParserHandler(Context context) {
        this.context = context;
    }

    public static XMLPullParserHandler getInstance(Context context) {
        if (xmlPullParserHandler == null)
            xmlPullParserHandler = new XMLPullParserHandler(context);
        return xmlPullParserHandler;
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        Writer writer = new StringWriter();

        char[] buffer = new char[2048];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is,
                    "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        String text = writer.toString();
        return text;
    }

    public ArrayList<CategoryEntity> getCategoryEntities() {
        ArrayList<CategoryEntity> categoryEntities = new ArrayList<>();

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(context.getAssets().open("categories.xml"), null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("Category"))
                            categoryEntity = new CategoryEntity();
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("Category"))
                            categoryEntities.add(categoryEntity);
                        else if (tagName.equalsIgnoreCase("CategoryName"))
                            categoryEntity.setCategoryName(text);
                        else if (tagName.equalsIgnoreCase("CategoryImageNo"))
                            categoryEntity.setCategoryImageNo(Integer.valueOf(text));
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryEntities;
    }

    public ArrayList<MaterialEntity> getMaterialEntities() {
        ArrayList<MaterialEntity> materialEntities = new ArrayList<>();

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(context.getAssets().open("materials.xml"), null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("Material"))
                            materialEntity = new MaterialEntity();
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("Material"))
                            materialEntities.add(materialEntity);
                        else if (tagName.equalsIgnoreCase("MaterialName"))
                            materialEntity.setMaterialName(text);
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return materialEntities;
    }
}