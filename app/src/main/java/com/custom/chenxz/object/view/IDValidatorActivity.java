package com.custom.chenxz.object.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.custom.chenxz.object.R;
import com.custom.chenxz.object.utils.apputils.ToastUtils;
import com.custom.chenxz.object.utils.id_validator.IDValidator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IDValidatorActivity extends Activity {

    @BindView(R.id.et_idValidator)
    EditText etIdValidator;
    @BindView(R.id.btn_queryID)
    Button btnQueryID;
    @BindView(R.id.btn_makeID)
    Button btnMakeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idvalidator);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_queryID, R.id.btn_makeID})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_queryID:
                String idNum = etIdValidator.getText().toString().trim();
                if (TextUtils.isEmpty(idNum)) {
                    ToastUtils.showShort(this, "请输入正确的ID!");
                    return;
                } else {
                    if (IDValidator.isValid(idNum)) {
                        ToastUtils.showShort(this, "验证成功!");
                    } else {
                        ToastUtils.showShort(this, "该ID为无有效身份证号!");
                    }
                }
                break;
            case R.id.btn_makeID:
                etIdValidator.setText(IDValidator.makeID(false));
                break;
        }
    }
}
