package cn.gavinliu.android.lib.shapedimageview.demo;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by Gavin on 2016/11/02.
 */

public class ExtensionDemoActivity extends AppCompatActivity {

    ShapedImageView image1, image2, image3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_extension);

        image1 = (ShapedImageView) findViewById(R.id.image1);
        image2 = (ShapedImageView) findViewById(R.id.image2);
        image3 = (ShapedImageView) findViewById(R.id.image3);

        image1.setExtension(new CDPathExtension());
        image2.setExtension(new TicketPathExtension(false));
        image3.setExtension(new TicketPathExtension(true));

    }

    class CDPathExtension implements ShapedImageView.PathExtension {

        @Override
        public void onLayout(Path path, int width, int height) {
            path.reset();
            path.addCircle(width / 2, height / 2, width / 8, Path.Direction.CW);
        }
    }

    class TicketPathExtension implements ShapedImageView.PathExtension {

        private boolean isTop;

        public TicketPathExtension(boolean isTop) {
            this.isTop = isTop;
        }

        @Override
        public void onLayout(Path path, int width, int height) {
            int y = height;
            if (isTop) {
                y = 0;
            }

            int circle_big_r = getResources().getDimensionPixelSize(R.dimen.big_circle_r);
            int circle_big_offset = getResources().getDimensionPixelSize(R.dimen.big_circle_offset);

            int circle_small_r = getResources().getDimensionPixelSize(R.dimen.small_circle_r);
            int circle_small_offset = getResources().getDimensionPixelSize(R.dimen.small_circle_offset);
            int circle_small_spacing = getResources().getDimensionPixelSize(R.dimen.small_circle_spacing);

            path.reset();
            path.addCircle(0 - circle_big_offset, y, circle_big_r, Path.Direction.CW);
            path.addCircle(width + circle_big_offset, y, circle_big_r, Path.Direction.CW);
            for (int i = circle_small_offset; i < width - circle_small_offset; i += circle_small_spacing) {
                path.addCircle(i, y, circle_small_r, Path.Direction.CW);
            }
        }
    }

}
