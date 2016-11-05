package cn.gavinliu.android.lib.shapedimageview.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void demo(View view) {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }

    public void extension(View view) {
        Intent intent = new Intent(this, ExtensionDemoActivity.class);
        startActivity(intent);
    }

    public void recycler(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}
