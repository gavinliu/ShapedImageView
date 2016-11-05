package cn.gavinliu.android.lib.shapedimageview.demo;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by Gavin on 2016/11/05.
 */

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new Adapter());
        }
    }


    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(ListActivity.this).inflate(R.layout.item_list, null));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String url = "http://img.xiami.net/images/album/img48/3048/3714265921440496645.jpg";
            int imageSize = getResources().getDimensionPixelSize(R.dimen.image_size);
            Glide.with(ListActivity.this).load(url)
                    .override(imageSize, imageSize)
                    .centerCrop()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ShapedImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ShapedImageView) itemView.findViewById(R.id.image);
            mImageView.setExtension(new CDPathExtension());
        }

        class CDPathExtension implements ShapedImageView.PathExtension {

            @Override
            public void onLayout(Path path, int width, int height) {
                path.reset();
                path.addCircle(width / 2, height / 2, width / 8, Path.Direction.CW);
            }
        }
    }

}
