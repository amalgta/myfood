package com.hani.myfood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.hani.myfood.model.Recipe;

import java.io.FileNotFoundException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView1, imageView2, imageView3;
    Bitmap bitmap1, bitmap2, bitmap3;
    Uri imageUri1, imageUri2, imageUri3;
    Button buttonSave;
    EditText editTextTitle;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    void loadImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    void doSave() {
        String title, uri1 = "", uri2 = "", uri3 = "";
        boolean isVeg;
        title = editTextTitle.getText().toString();
        isVeg = radioGroup.getCheckedRadioButtonId() != R.id.radioNonVeg;
        if (imageUri1 != null)
            uri1 = imageUri1.getPath();
        if (imageUri2 != null)
            uri2 = imageUri2.getPath();
        if (imageUri3 != null)
            uri3 = imageUri3.getPath();

        DatabaseHandler db = new DatabaseHandler(this);
        db.addRecipe(new Recipe(title, isVeg, uri1, uri2, uri3));
        Log.d("Reading: ", "Reading all contacts..");
        List<Recipe> contacts = db.getAllRecipes();

        for (Recipe cn : contacts) {
            Log.d("GTA: ", cn.getTitle() + cn.getImagePath1());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                switch (requestCode) {
                    case 1:
                        bitmap1 = bitmap;
                        imageUri1 = targetUri;
                        imageView1.setImageBitmap(bitmap1);
                        break;
                    case 2:
                        bitmap2 = bitmap;
                        imageUri2 = targetUri;
                        imageView2.setImageBitmap(bitmap2);
                        break;
                    case 3:
                        bitmap3 = bitmap;
                        imageUri3 = targetUri;
                        imageView3.setImageBitmap(bitmap3);
                        break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSave:
                doSave();
                break;
            case R.id.imageView1:
                loadImage(1);
                break;
            case R.id.imageView2:
                loadImage(2);
                break;
            case R.id.imageView3:
                loadImage(3);
                break;
        }
    }
}
