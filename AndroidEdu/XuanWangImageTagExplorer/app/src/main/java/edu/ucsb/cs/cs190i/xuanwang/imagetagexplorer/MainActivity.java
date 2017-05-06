package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageTagDatabaseHelper.Initialize(this);

        final TextView textView = (TextView)findViewById(R.id.textView);
        final ImageView imageView = (ImageView)findViewById(R.id.imageView);

        TaggedImageRetriever.getNumImages(new TaggedImageRetriever.ImageNumResultListener() {
            @Override
            public void onImageNum(int num) {
                textView.setText(textView.getText() + "\n\n" + num);
            }
        });

        TaggedImageRetriever.getTaggedImageByIndex(0, new TaggedImageRetriever.TaggedImageResultListener() {
            @Override
            public void onTaggedImage(TaggedImageRetriever.TaggedImage image) {
                if (image != null) {
                    try (FileOutputStream stream = openFileOutput("Test.jpg", Context.MODE_PRIVATE)){
                        image.image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        image.image.recycle();
                    } catch (IOException e) {
                    }
                    Picasso.with(MainActivity.this).load(getFileStreamPath("Test.jpg")).resize(500,500).centerCrop().into(imageView);
                    // imageView.setImageBitmap(image.image);
                    StringBuilder tagList = new StringBuilder();
                    for (String p : image.tags) {
                        tagList.append(p + "\n");
                    }
                    textView.setText(textView.getText() + "\n\n" + tagList.toString());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageTagDatabaseHelper.GetInstance().close();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Show your dialog here (this is called right after onActivityResult)
    }
}