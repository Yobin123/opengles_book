package yobin_he.com.opengles_book;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import yobin_he.com.opengles_book.chapter_2.Chapter2Activity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private Button mBtnChapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        initView();
        initListener();
    }


    private void initView() {
        mBtnChapter2 = findViewById(R.id.btn_chapter_2);
    }
    private void initListener() {
        mBtnChapter2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chapter_2:
                startActivity(new Intent(mContext,Chapter2Activity.class));
                break;
        }

    }
}
