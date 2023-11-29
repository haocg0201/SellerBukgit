package com.haocg.ourduan1nhom8.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageUtil {

    private Context context;

    public ImageUtil(Context context) {
        this.context = context;
    }

    private static final int PICK_IMAGE_REQUEST = 1;

    public String getPathFromUri(Uri uri){
        String[] protection = {MediaStore.Images.Media.DATA};
        Cursor cs = (context).getContentResolver().query(uri,protection,null,null,null);
        if(cs != null && cs.moveToFirst()){
            int columnIndex = cs.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cs.getString(columnIndex);
            cs.close();
            return filePath;
        }
        return null;
    }

    // lưu ảnh vào thư mục của ứng dụng ở đây
    public File saveImageToApp(Bitmap bitmap){
        File director = new File(context.getFilesDir(),"images");
        if(!director.exists()){
            director.mkdirs();
        }

        String fileName = UUID.randomUUID().toString() + ".jpg";;
        File imageFile = new File(director,fileName);
        try {
            // mở luồng đầu ra để ghi dữ liệu, ở đây sử dụng FileOutputStream
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return imageFile;
    }

    public void deleteOldImage(String path){
        /// lay path anh tu sql cu
        String oldImagepath = path;

        // kiem tra va xoa anh cu di
        if(oldImagepath != null){
            File oldImageFile = new File(oldImagepath);
            if(oldImageFile.exists()){
                oldImageFile.delete();
            }
        }
    }
}
