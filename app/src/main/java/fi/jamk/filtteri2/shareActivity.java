package fi.jamk.filtteri2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class shareActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

       /* Intent intent = getIntent();
        String imageUri = intent.getStringExtra("imagePath");
        Uri fileUri = Uri.parse(imageUri);
        imageView.setImageURI(fileUri);*/

       Intent intent = getIntent();
       Bitmap bitmap = (Bitmap) intent.getParcelableExtra("image");
        imageView.setImageBitmap(bitmap);
    }
}
