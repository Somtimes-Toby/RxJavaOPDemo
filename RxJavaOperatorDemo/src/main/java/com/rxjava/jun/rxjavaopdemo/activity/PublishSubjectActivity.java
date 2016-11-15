package com.rxjava.jun.rxjavaopdemo.activity;

import android.os.Bundle;

import com.rxjava.jun.rxjavaopdemo.R;
import com.rxjava.jun.rxjavaopdemo.fragment.PublishSubjectFragment;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

public class PublishSubjectActivity extends RxFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_subject);
        initFragment();
    }
    private void initFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_content,new PublishSubjectFragment(), PublishSubjectFragment.class.getName())
                .commit();
    }
}
