package com.hani.myfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hani.myfood.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "recipeManager";

    // Contacts table name
    private static final String TABLE_RECIPE = "recipe";

    // Contacts Table Columns names
    private static final String KEY_ID = "Id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IS_VEG = "isVeg";
    private static final String KEY_IMAGEPATH1 = "imagePath1";
    private static final String KEY_IMAGEPATH2 = "imagePath2";
    private static final String KEY_IMAGEPATH3 = "imagePath3";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, recipe.getTitle());
        values.put(KEY_IS_VEG, (recipe.isVeg() ? "TRUE" : "FALSE"));
        values.put(KEY_IMAGEPATH1, recipe.getImagePath1());
        values.put(KEY_IMAGEPATH2, recipe.getImagePath2());
        values.put(KEY_IMAGEPATH3, recipe.getImagePath3());
        db.insert(TABLE_RECIPE, null, values);
        db.close();
    }

    public Recipe getRecipe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE, new String[]{KEY_ID,
                        KEY_TITLE, KEY_IS_VEG, KEY_IMAGEPATH1, KEY_IMAGEPATH2, KEY_IMAGEPATH3}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Recipe recipe = new Recipe(Integer.parseInt(cursor.getString(0)), cursor.getString(1), (cursor.getString(2).equals("TRUE")), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        return recipe;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECIPE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setId(Integer.parseInt(cursor.getString(0)));
                recipe.setTitle(cursor.getString(1));
                recipe.setVeg((cursor.getString(2).equals("TRUE")));
                recipe.setImagePath1(cursor.getString(3));
                recipe.setImagePath2(cursor.getString(4));
                recipe.setImagePath3(cursor.getString(5));
            } while (cursor.moveToNext());
        }
        return recipes;
    }

    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPE, KEY_ID + " = ?",
                new String[]{String.valueOf(recipe.getId())});
        db.close();
    }

    public int updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, recipe.getTitle());
        values.put(KEY_IS_VEG, (recipe.isVeg() ? "TRUE" : "FALSE"));
        values.put(KEY_IMAGEPATH1, recipe.getImagePath1());
        values.put(KEY_IMAGEPATH2, recipe.getImagePath3());
        values.put(KEY_IMAGEPATH3, recipe.getImagePath3());
        return db.update(TABLE_RECIPE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(recipe.getId())});
    }

    public int getRecipeCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RECIPE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_RECIPE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_IS_VEG + " BOOLEAN,"
                + KEY_IMAGEPATH1 + " TEXT,"
                + KEY_IMAGEPATH2 + " TEXT,"
                + KEY_IMAGEPATH3 + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        onCreate(db);
    }
}
