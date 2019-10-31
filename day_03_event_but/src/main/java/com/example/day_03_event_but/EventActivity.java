package com.example.day_03_event_but;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.day_03_event_but.bean.MyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * EventBus和ButterKnife 两个框架 注解类型的框架
 * 这两个框架都需要绑定和解绑
 * 1:EventBus 它里面用到一个设计模式 观察者模式  1：观察者  2：被观察者 3：订阅事件
 * 1:它只需要导入依赖即可
 * 2:注册到你要使用那个Activity
 * 3:两种事件：1：普通事件2：粘性事件（发送这个事件普通也能接收到）按照事件都数据类型进行匹配
 * ButterKnife
 * 1：需要在项目里面的build.grade里都dependencies这个代码快 classpath 'com.jakewharton:butterknife-gradle-plugin:8.8.1'  //添加这一行
 * 2：在app都build.grade里面加两个依赖
 *  implementation 'com.jakewharton:butterknife:8.8.1'
 *     annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
 * 3：下载一个ButterKnifeLzy
 */
public class EventActivity extends AppCompatActivity {

    @BindView(R.id.Event_Text)
    TextView EventText;
    @BindView(R.id.Event_Btn)
    Button EventBtn;
    @BindView(R.id.Event_Btn_Two)
    Button EventBtnTwo;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mUnbinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getStr(MyEvent event) {
        Log.e("Event",event.toString());
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void ss(String str){

    }

    @OnClick({R.id.Event_Btn, R.id.Event_Btn_Two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Event_Btn:
                //普通事件
//                EventBus.getDefault().post("发送事件");
                //粘性事件
                EventBus.getDefault().postSticky(new MyEvent("lvxinxin","shuai"));
                startActivity(new Intent(this,GetInfoActivity.class));
                break;
            case R.id.Event_Btn_Two:
                break;
        }
    }
}
