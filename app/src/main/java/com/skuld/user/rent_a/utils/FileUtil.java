package com.skuld.user.rent_a.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Base64;
import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {

    private final String TAG = FileUtil.class.getSimpleName();
    private final String directoryName = "BEEP";
    private final Context mContext;

    public FileUtil(Context context) {
        this.mContext = context;
    }

    public Uri generateImageFileUri() {
        return Uri.fromFile(generateImageFile());
    }

    public File generateImageFile() {
        return new File(generateDirectoryFile().getPath() + File.separator + "IMG_" + DateUtil.getTimeStamp()
                + ".jpg");
    }

    private File generateDirectoryFile() {
        File mediaStorageDir = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), directoryName);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed to create " + directoryName + " directory");
                return null;
            }
        }
        return mediaStorageDir;
    }

    @SuppressLint("NewApi")
    public String getRealPathFromURI_API19(Uri uri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        String id = wholeID.split(":")[1];
        String[] column = {MediaStore.Images.Media.DATA};

        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = mContext.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel, new String[]{id},
                        null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @SuppressLint("NewApi")
    public String getRealPathFromURI_API11to18(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(mContext, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static Bitmap imageToBitmap(String imagePath){
        File imagefile = new File(imagePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeStream(fis);
    }

    public static byte[] imageBitmapToByte(Bitmap imageBitmap, int imageRotation){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (imageRotation != 0)
            imageBitmap = getBitmapRotatedByDegree(imageBitmap, imageRotation);

        imageBitmap = Bitmap.createScaledBitmap(imageBitmap,(int)(imageBitmap.getWidth()*0.5), (int)(imageBitmap.getHeight()*0.5), true);
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50 , baos);
        imageBitmap.recycle(); //to cleanup memory resourced by bitmap
        return baos.toByteArray();
    }

    public  static String byteToBase64String(byte[] imageByte){
        return new String(Base64.encode(imageByte, Base64.NO_WRAP));
    }





    private static int getImageRotation(final File imageFile) {

        ExifInterface exif = null;
        int exifRotation = 0;

        try {
            exif = new ExifInterface(imageFile.getPath());
            exifRotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exif == null)
            return 0;
        else
            return exifToDegrees(exifRotation);
    }

    private static int exifToDegrees(int rotation) {
        if (rotation == ExifInterface.ORIENTATION_ROTATE_90)
            return 90;
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_180)
            return 180;
        else if (rotation == ExifInterface.ORIENTATION_ROTATE_270)
            return 270;

        return 0;
    }

    private static Bitmap getBitmapRotatedByDegree(Bitmap bitmap, int rotationDegree) {
        Matrix matrix = new Matrix();
        matrix.preRotate(rotationDegree);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
