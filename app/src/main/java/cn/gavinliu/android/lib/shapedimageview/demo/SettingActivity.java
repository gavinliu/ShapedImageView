package cn.gavinliu.android.lib.shapedimageview.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by Gavin on 2017/01/07.
 */

public class SettingActivity extends AppCompatActivity {

    ShapedImageView mShapedImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mShapedImageView = (ShapedImageView) findViewById(R.id.image1);
    }

    public void stroke(View view) {
        mShapedImageView.setStroke(0xFF999999, 35);
    }

    public void strokeWidth(View view) {
        mShapedImageView.setStrokeWidth(50);
    }

    public void strokeColor(View view) {
        mShapedImageView.setStrokeColor(getResources().getColor(R.color.colorAccent));
    }


    public void round(View view) {
        mShapedImageView.setShape(ShapedImageView.SHAPE_MODE_ROUND_RECT, 50);
    }

    public void circle(View view) {
        mShapedImageView.setShape(ShapedImageView.SHAPE_MODE_CIRCLE, 50);
    }

    public void radius(View view) {
        mShapedImageView.setShapeRadius(25);
    }

}
