package com.example.dals;

import static com.example.k234112eapp.LoginActivity.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.models.Category;

import java.util.ArrayList;
public class CategoryDAO {
    public static final String DATABASE_NAME = "K234112ESales.sqlite";
    public static final String TABLE_NAME = "Category";
    public static SQLiteDatabase database = null;
    public static ArrayList<Category> getCategories(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        database = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            String cateId = cursor.getString(1);
            String cateName = cursor.getString(2);
            String cateDesc = cursor.getString(3);
            Category c = new Category(cateId, cateName, cateDesc);
            categories.add(c);
        }
        cursor.close();
        return categories;
    }
}
