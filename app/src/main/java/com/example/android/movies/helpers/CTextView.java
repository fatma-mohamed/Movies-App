//package com.example.android.movies.helpers;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.util.AttributeSet;
//import android.widget.TextView;
//
///**
// * Created by Fatma on 17-Nov-16.
// */
//
//public class CTextView extends TextView {
//    public CTextView(Context context) {
//        super(context);
//    }
//
//    public CTextView(Context context, AttributeSet attrs)
//    {
//        this(context, attrs, android.R.attr.textViewStyle);
//    }
//
//    public CTextView(Context context, AttributeSet attrs, int defStyle)
//    {
//        super(context, attrs, defStyle);
//
//        applyCustomFont(this, attrs, defStyle);
//    }
//
//    private void applyCustomFont(Context context, AttributeSet attrs) {
//        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);
//
//        Typeface customFont = selectTypeface(context, textStyle);
//        setTypeface(customFont);
//    }
//}
