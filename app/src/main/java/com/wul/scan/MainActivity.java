package com.wul.scan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wul.scan.api.HttpResultSubscriber;
import com.wul.scan.api.HttpServiceIml;
import com.wul.scan.data.BindDianChiRequest;
import com.wul.scan.data.BindGuochengRequest;
import com.wul.scan.data.GuoChengBo;
import com.wul.scan.data.OrderBO;
import com.wul.scan.data.OrderResult;
import com.wul.scan.data.PageBO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.order_num)
    ScanEditText orderNum;
    @BindView(R.id.product_num)
    TextView productNum;
    @BindView(R.id.describe_num)
    TextView describeNum;
    @BindView(R.id.order_size)
    TextView orderSize;
    @BindView(R.id.ed_guocheng)
    ScanEditText edGuocheng;
    @BindView(R.id.ed_dianchi)
    ScanEditText edDianchi;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.bt_update)
    TextView btUpdate;

    private int pageNum = 0;
    private OrderBO orderBO;
    private String GuochengId;
    private boolean isHasFouce = true;

    private String strOrderNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        getOrderNum();
        btUpdate.setOnClickListener(this);
        setListener();
        requestPermission();
    }


    private void setListener() {
        edGuocheng.setScanResultListener(result -> bindGuocheng());
        edDianchi.setScanResultListener(result -> bindDianChi());
        edDianchi.setOnFocusChangeListener((view, b) -> {
            if (b) {
                if (TextUtils.isEmpty(GuochengId)) {
                    ToastUtils.showShort("请先绑定过程标签！");
                    edDianchi.setText("");
                    edGuocheng.requestFocus();
                }
            }
        });
        edGuocheng.setOnFocusChangeListener((view, b) -> {
            if (b) {
                if (!isHasFouce) {
                    edGuocheng.setText("");
                    GuochengId = null;
                    isHasFouce = true;
                }
            } else {
                isHasFouce = false;
            }
        });
//        orderNum.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                if (!StringUtils.isEmpty(orderNum.getText().toString())) {
//                    orderNum.setText(orderNum.getText().toString());// 添加这句后实现效果
//                    orderNum.selectAll();
//                }
//            }
//        });
        orderNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isEmpty(s.toString())) {
                    productNum.setText("");
                    describeNum.setText("");
                    orderSize.setText("");
                }
            }
        });
    }


    /**
     * 获取工单单号
     */
    private void getOrderNum() {
        pageNum++;
        PageBO pageBO = new PageBO();
        pageBO.pageNum = pageNum;
        pageBO.pageSize = 1;
        HttpServiceIml.getOrderNum(pageBO).subscribe(new HttpResultSubscriber<List<OrderBO>>() {
            @Override
            public void onSuccess(List<OrderBO> o) {
                if (o != null && o.size() > 0) {
                    orderBO = o.get(0);
                    orderNum.setText(orderBO.getOrderNum());
                    productNum.setText(orderBO.getProductCode());
                    describeNum.setText(orderBO.getProductName());
                    orderSize.setText(orderBO.getProductNum() + "");
                } else {
                    ToastUtils.showShort("没有别的订单了！");
                    if (pageNum != 0) {
                        pageNum = 0;
                        getOrderNum();
                    }
                }
            }

            @Override
            public void onFiled(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 搜索工单编号
     */
    private void selectOrderNum() {
        OrderResult result = new OrderResult();
        result.setOrderNum(strOrderNum);
        HttpServiceIml.selectOrderNum(result).subscribe(new HttpResultSubscriber<List<OrderBO>>() {
            @Override
            public void onSuccess(List<OrderBO> o) {
                if (o != null && o.size() > 0) {
                    orderBO = o.get(0);
                    productNum.setText(orderBO.getProductCode());
                    describeNum.setText(orderBO.getProductName());
                    orderSize.setText(orderBO.getProductNum() + "");
                    edGuocheng.requestFocus();
                } else {
                    ToastUtils.showShort("没有此订单！");
                }
            }

            @Override
            public void onFiled(String message) {
                ToastUtils.showShort(message);
            }
        });
    }


    /**
     * 绑定过程编号
     */
    private void bindGuocheng() {
        if (orderBO == null) {
            Toast.makeText(MainActivity.this, "请先获取工单！", Toast.LENGTH_SHORT).show();
            return;
        }
        BindGuochengRequest request = new BindGuochengRequest();
        request.setOrderId(orderBO.getOrderId());
        request.setProcessLabel(edGuocheng.getText().toString().trim());
        request.setImei(getIMEI());
        HttpServiceIml.bindGuocheng(request).subscribe(new HttpResultSubscriber<GuoChengBo>() {
            @Override
            public void onSuccess(GuoChengBo s) {
                Toast.makeText(MainActivity.this, "过程标签绑定成功！", Toast.LENGTH_SHORT).show();
                GuochengId = s.getProcessLabelId() + "";
                num.setText(s.getBatteryNum() + "");
                edDianchi.requestFocus();
            }

            @Override
            public void onFiled(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 绑定电池芯
     */
    private void bindDianChi() {
        if (orderBO == null) {
            Toast.makeText(MainActivity.this, "请先获取订单！", Toast.LENGTH_SHORT).show();
            return;
        }
        BindDianChiRequest request = new BindDianChiRequest();
        request.setBatteryLabel(edDianchi.getText().toString().trim());
        request.setImei(getIMEI());
        request.setOrderId(orderBO.getOrderId());
        request.setProcessLabelId(GuochengId);
        HttpServiceIml.bindDianChi(request).subscribe(new HttpResultSubscriber<String>() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.showShort("绑定成功！");
                num.setText(s);
                edDianchi.setSelectAllOnFocus(true);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        edDianchi.setText("");
//                    }
//                }, 2000);
            }

            @Override
            public void onFiled(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                edDianchi.setSelectAllOnFocus(true);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        edDianchi.setText("");
//                    }
//                }, 2000);
            }
        });
    }


    @SuppressLint("MissingPermission")
    private String getIMEI() {
        TelephonyManager tm = (TelephonyManager) getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (!TextUtils.isEmpty(imei)) {
            return imei;
        }
        return null;
    }


    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
                // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    //解释原因，并且引导用户至设置页手动授权
                    new AlertDialog.Builder(this)
                            .setMessage("因程序业务需求\n需要同意app权限申请！")
                            .setPositiveButton("去授权", (dialog, which) -> {
                                //引导用户至设置页手动授权
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            })
                            .setNegativeButton("取消", (dialog, which) -> {
                                //引导用户手动授权，权限请求失败
                                prissiosFiled();
                            }).setOnCancelListener(dialog -> {
                        //引导用户手动授权，权限请求失败
                        prissiosFiled();
                    }).show();

                } else {
                    //权限请求失败，但未选中“不再提示”选项
                    prissiosFiled();
                }
                break;
            }
        }
        if (hasAllGranted) {
            //权限请求成功
        }
    }


    private void prissiosFiled() {
        ToastUtils.showShort("应用权限被拒绝！");
        finish();
        System.exit(0);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_update:
                strOrderNum = orderNum.getText().toString().trim();
                if (StringUtils.isEmpty(strOrderNum)) {
                    ToastUtils.showShort("请输入工单单号！");
                    return;
                }
//                getOrderNum();
                selectOrderNum();
                edGuocheng.setText("");
                edDianchi.setText("");
                num.setText("");
                break;
        }
    }
}
