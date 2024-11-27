package com.project2.banhangmypham.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

import kotlin.UByteArray;

public class Utils {
    public static byte[] convertBitmapToByteArray(Bitmap input){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        input.compress(Bitmap.CompressFormat.PNG, 100,stream);
        return stream.toByteArray();
    }
    public static Bitmap convertFromByteArrayToBitMap(byte[] data){
        return BitmapFactory.decodeByteArray(data,0,data.length);
    }

    public static Spannable convertColorText(String input, int start , int end ){
        Spannable spannable = new SpannableStringBuilder(input);
        spannable.setSpan(new ForegroundColorSpan(Color.RED),start, end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static String convertToMoneyVN(long input){
        // Create a NumberFormat instance for Vietnam
        Locale vietnam = new Locale("vi", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(vietnam);
        currencyFormatter.setMaximumFractionDigits(0);
        return currencyFormatter.format(input);
    }
}
