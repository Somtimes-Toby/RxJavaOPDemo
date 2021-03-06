package com.rxjava.jun.rxjavaopdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rxjava.jun.rxjavaopdemo.R;
import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by jun on 2016/11/14.
 */

public class PublishSubjectBottomFragment extends RxFragment{
    @Bind(R.id.tv_result)
    TextView tv_result;

    private final PublishSubject<String> publishSubject;

    public PublishSubjectBottomFragment(PublishSubject<String> publishSubject) {
        this.publishSubject = publishSubject;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish_bottom, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        /**
         * 这里先订阅事件：
         *               接收到通知后，产生响应
         *
         */
        publishSubject.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                tv_result.setText("收到上级的秘密召令："+s);
            }
        });
    }
}
