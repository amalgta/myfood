package com.hani.myfood;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hani.myfood.model.Recipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView1, imageView2, imageView3;
    Bitmap bitmap1, bitmap2, bitmap3;
    Uri imageUri1, imageUri2, imageUri3;
    Button buttonSave;
    EditText editTextTitle;
    RadioGroup radioGroup;
    TextView textViewLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLog = (TextView) findViewById(R.id.textViewLog);
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
        getLog();
    }

    void getLog() {
        String logText = "";
        DatabaseHandler db = new DatabaseHandler(this);
        for (Recipe thisRecipe : db.getAllRecipes()) {
            logText += thisRecipe.toString() + "\n";
        }
        textViewLog.setText(logText);
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    Uri photoURI;

    void loadImage(int requestCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ignored) {
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, "com.hani.myfood", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestCode);
            }
        }
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
        getLog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            switch (requestCode) {
                case 1:
                    bitmap1 = bitmap;
                    imageUri1 = photoURI;
                    imageView1.setImageBitmap(bitmap1);
                    break;
                case 2:
                    bitmap2 = bitmap;
                    imageUri2 = photoURI;
                    imageView2.setImageBitmap(bitmap2);
                    break;
                case 3:
                    bitmap3 = bitmap;
                    imageUri3 = photoURI;
                    imageView3.setImageBitmap(bitmap3);
                    break;
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
