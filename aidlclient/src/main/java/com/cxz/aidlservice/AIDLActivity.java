package com.cxz.aidlservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */

public class AIDLActivity extends AppCompatActivity {
    //由AIDL文件生成的java类
    private BookManager bookManager = null;
    //标志当前与服务端连接状态的布尔值，false为未连接，TRUE 为连接状态
    private boolean mBound = false;
    //包含Book对象的List
    private List<Book> mBooks;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            bookManager = BookManager.Stub.asInterface(iBinder);
            mBound = true;
            if (bookManager != null) {
                try {
                    mBooks = bookManager.getBooks();
                    Log.e("CXZ", "类名:AIDLActivity  方法名: onServiceConnected " + mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("CXZ", "类名:AIDLActivity  方法名: onServiceDisconnected " + "service disconnected");
            mBound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
    }

    /**
     * 点击之后调用服务端的addBoolIn方法
     *
     * @param view
     */
    public void addBook(View view) {
        //如果与服务端处于未连接的状态，则尝试进行重新连接
        if (!mBound) {
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (bookManager == null) {
            return;
        }
        Book book = new Book();
        book.setName("AIDLClient1");
        book.setPrice(1111);
        try {
            bookManager.addBook(book);
            Log.e("CXZ", "类名:AIDLActivity  方法名: addBook " + book.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.cxz.aidl");
        intent.setPackage("com.cxz.aidlservice");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(serviceConnection);
            mBound = false;
        }
    }
}
