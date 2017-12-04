package fi.jamk.filtteri2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class start extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 0;
    private static final int MY_PERMISSION_REQUEST = 1;
    private final int TAKE_PICTURE = 1;
    Button b_load, b_take;
    String currentFileName = "";
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (ContextCompat.checkSelfPermission(start.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(start.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(start.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

            } else {
                ActivityCompat.requestPermissions(start.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        } else {
            // ei mitään
        }


        b_load = (Button) findViewById(R.id.b_load);
        b_take = (Button) findViewById(R.id.b_take);

        b_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);*/
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);

            }
        });

        b_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null) {

                    File file = new File(Environment.getExternalStorageDirectory(), fileName());
                    imageUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    startActivityForResult(intent, TAKE_PICTURE);
                }


            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            /*

            kuva = BitmapFactory.decodeFile(picturePath);
            photoHeight = kuva.getHeight();
            photoWidth = kuva.getWidth();
            imageView.setImageBitmap(kuva);

            b_popup.setEnabled(true);
            b_popup2.setEnabled(true);
            */

        }


        //lisättyä koodia ->
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            //try {

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("imagePath", imageUri.toString());
                startActivity(intent);



                /*
                File file = new File(Environment.getExternalStorageDirectory(), currentFileName);

                //Bundle extras = data.getExtras();
                //Bitmap photo = (Bitmap) extras.get("data");
                Bitmap immutableBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));

                //changing immutablebitmap to mutable bitmap by making a copy of it
                Bitmap photo = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("BitmapImage", photo);
                */

                //valokuvasta tulee vanmanen kun sitä skaalaa :(
                //photo.setHeight(1280);
                //photo.setWidth(720);



/*
            } catch (FileNotFoundException e) {
                Toast.makeText(this,"Captured image not found! ",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this,"Captured image not found! ",Toast.LENGTH_LONG).show();
            }*/
        }
        //loppuu,
    }
    public String fileName(){ //tämä luo uniikin tiedostonnimen ajan mukaan...
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        currentFileName = imageFileName;
        return imageFileName;
    }
}
