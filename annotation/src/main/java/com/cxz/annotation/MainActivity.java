package com.cxz.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.cxz.annotation.annotations.MyFindView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    @MyFindView(R.id.tv1)
    private TextView textView;

    @MyFindView(R.id.btn1)
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //通过注解生成view
        getAllAnnotationView();
    }

    /**
     * 解析注解，获取控件
     */
    private void getAllAnnotationView() {
        //获取成员变量
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            //确定注解类型
            if (field.getAnnotations() != null) {
                //允许修改反射属性
                field.setAccessible(true);
                MyFindView findView = field.getAnnotation(MyFindView.class);
                try {
                    //findViewById将注解的id，找到View注入成员变量中
                    field.set(this, findViewById(findView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
