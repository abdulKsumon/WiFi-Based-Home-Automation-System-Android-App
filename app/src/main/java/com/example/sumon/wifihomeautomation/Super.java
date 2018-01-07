package com.example.sumon.wifihomeautomation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import static android.R.attr.left;
import static android.R.attr.right;
import static com.example.sumon.wifihomeautomation.R.id.bottom;
import static com.example.sumon.wifihomeautomation.R.id.image;
import static com.example.sumon.wifihomeautomation.R.id.top;

public class Super extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super);
        imageView=(ImageView)findViewById(R.id.imageView);
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        //marginParams.setMargins(left, top, right, bottom);
    }
}
