package cn.gavinliu.android.lib.shapedimageview.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by Gavin on 2016/11/02.
 */

public class DemoActivity extends AppCompatActivity {

    ShapedImageView image1, image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        image1 = (ShapedImageView) findViewById(R.id.image1);
        image2 = (ShapedImageView) findViewById(R.id.image2);

        new Task().execute();
    }

    class Task extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... params) {
            return BitmapFactory.decodeResource(getResources(), R.drawable.an);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
            TransitionDrawable td = new TransitionDrawable(new Drawable[]{new ColorDrawable(0xDEDEDE), bd});
            image1.setImageDrawable(td);
            td.startTransition(1000);

            BitmapDrawable bd2 = new BitmapDrawable(getResources(), bitmap);
            TransitionDrawable td2 = new TransitionDrawable(new Drawable[]{new ColorDrawable(0xDEDEDE), bd2});
            image2.setImageDrawable(td2);
            td2.startTransition(1000);
        }
    }
}
