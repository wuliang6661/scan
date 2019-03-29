package com.wul.scan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 具有扫描功能的EditText
 * Created by Administrator on 2017/8/14.
 */

public class ScanEditText extends EditText {
    /**
     * 可以接受的字符
     */
    private static final String ACCEPTABLE_CHARS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ\r\n";
    private ScanResultListener mListener;
    private OnRightTouchListener mTouchListener;
    private boolean mChecked = false;

    private void init() {
        initListener();
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setCheck(boolean check) {
        setCheck(check, false);
    }

    public void setCheck(boolean check, boolean removeDrawable) {
        setCheckDrawable(check, removeDrawable);
    }

    private void setCheckDrawable(boolean check, boolean removeDrawable) {
        mChecked = check;
        Drawable drawable = null;
        if (check) {
//            drawable = getResources().getDrawable(R.drawable.check);
        } else {
//            drawable = getResources().getDrawable(R.drawable.scan);
        }
        //设置边界
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        setCompoundDrawables(null, null, removeDrawable ? null : drawable, null);
        setEnabled(!check);
    }

    private void initListener() {
        setOnEditorAction();
        //setKeyListener(DigitsKeyListener.getInstance(ACCEPTABLE_CHARS));
        setOnTouchListener();
    }

    private void setOnTouchListener() {
        setOnTouchListener(new OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null) {
                    return false;
                }
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }
                if (event.getX() > getWidth() - getPaddingRight() - drawable.getIntrinsicWidth()) {
                    if (null != mTouchListener) {
                        mTouchListener.onTouch();
                    }
                }
                return false;
            }
        });
    }

    private void getResult(TextView textView) {
        String keyWord = textView.getText().toString().trim();
        if (null == keyWord) {
            keyWord = "";
        }
        if (null != mListener) {
            mListener.result(keyWord);
        }
    }

    private void setOnEditorAction() {
        setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH || (null != keyEvent && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                if (null != keyEvent) {
                    switch (keyEvent.getAction()) {
                        case KeyEvent.ACTION_UP:
                            getResult(textView);
                            return true;
                        default:
                            return true;
                    }
                } else {
                    getResult(textView);
                }
            }
            return true;
        });
    }

    public ScanEditText(final Context context) {
        super(context);
        init();
    }

    public ScanEditText(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScanEditText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setScanResultListener(ScanResultListener listener) {
        if (null != listener) {
            mListener = listener;
        }
    }

    public void setOnRightTouchListener(OnRightTouchListener listener) {
        if (null != listener) {
            mTouchListener = listener;
        }
    }

    public interface OnRightTouchListener {
        public void onTouch();
    }

    public interface ScanResultListener {
        public void result(String result);
    }
}
