package com.example.jan_paul.handpickedandroidclient.Presentation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by tobia on 25-5-2018.
 */

public class ProductGridItemLayout extends LinearLayout {
    public ProductGridItemLayout(Context context) {
        super(context);
    }

    public ProductGridItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductGridItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}
