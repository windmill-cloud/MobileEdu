package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class Main2Activity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    Picasso.with(this).load(new File(getExternalFilesDir(null), "ImageTagExplorer-ed1350b7-ebf7-44bd-8923-33bfe90d0ba5.jpg")).into((ImageView)findViewById(R.id.imageView));
  }
}
