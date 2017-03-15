package com.yunhetong.sdk.tool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

/**
 * base64字符串和bitmap相互转换
 */
public class ImageBase64Util {
    private static final String TAG = "ImageBase64Util";

    /**
     * bitmap to base64
     *
     * @param bitmap
     * @return
     */
    public static String BitmapToBase64(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        String encoded;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } finally {
            try {
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.flush();
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                return null;
            }
        }
        YhtLog.d(TAG, encoded);
        return encoded;
    }

    /**
     * base64 string to bitmap
     *
     * @param encodedImage 经base64转换的图片String
     * @param sample       压缩级别
     * @return
     */
    public static Bitmap Base64ToBitmap(String encodedImage, int sample) {
        if (encodedImage == null)
            return null;
        byte[] decodedString;
        try {
            decodedString = Base64.decode(encodedImage, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }
        if (decodedString == null || decodedString.length == 0)
            return null;
        Options opt = new Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length, opt);
//		int w = opt.outWidth;
        int h = opt.outHeight;
        if (h <= 200) {
            sample = 2;
        }
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = sample;
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length, opt);
        return bitmap;
    }


    public static Drawable Base64ToDrawble(String encodedImage) {

        Bitmap bitmap = ImageBase64Util.Base64ToBitmap(encodedImage, 4);
        return new BitmapDrawable(bitmap);
    }
}
