package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class Filters extends AppCompatActivity {

    ImageView imageSelector;
    Button blurBtn, negativeBtn, bwBtn;

    private String Document_img1 = "";

    public static boolean storagePermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        imageSelector = findViewById(R.id.IdProfResult);
        blurBtn = findViewById(R.id.blur_btn);
        negativeBtn = findViewById(R.id.negative_btn);
        bwBtn = findViewById(R.id.bw_btn);

        storagePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (!storagePermission)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        }

        imageSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(Filters.this);
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo"))
                        {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            try {
                                startActivityForResult(takePictureIntent, 1);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (options[item].equals("Choose from Gallery"))
                        {
                            Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 2);
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });

        blurBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processImage("blur_picture");

            }
        });

        bwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processImage("black_white_picture");

            }
        });

        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processImage("negative_picture");

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageBitmap = getResizedBitmap(imageBitmap, 400);
                imageSelector.setImageBitmap(imageBitmap);
                BitMapToString(imageBitmap);

            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                thumbnail=getResizedBitmap(thumbnail, 400);

                Log.w("GalleryPath: ", picturePath+"");
                imageSelector.setImageBitmap(thumbnail);

                BitMapToString(thumbnail);
            }
        }

    }

    private void processImage(String route) {

        String url = MainActivity.apiURL + route;

        RequestQueue requestQueue = Volley.newRequestQueue(Filters.this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("base64", Document_img1);
            Log.e("myObject", postData.toString());
            Toast.makeText(Filters.this, "Processing..", Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
            Toast.makeText(Filters.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Log.e("Guess Result", response.toString());
                //Toast.makeText(Classify.this, response.toString(), Toast.LENGTH_LONG).show();

                String imgKey = null;
                try {
                    imgKey = response.get("result").toString();
                    Log.e("imgKey", imgKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                byte[] imageBytes = Base64.decode(imgKey, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                //Alert Dialog here

                LayoutInflater li = LayoutInflater.from(Filters.this);
                View promptsView = li.inflate(R.layout.result_image, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Filters.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final ImageView resultImg = (ImageView) promptsView.findViewById(R.id.IdProfResult);
                bmp = getResizedBitmap(bmp, 400);
                resultImg.setImageBitmap(bmp);
                final Bitmap saveBmp = bmp;

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("SAVE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // save to device
                                        saveImage(saveBmp);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Filters.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString() + "/myFilters/";
        Log.e("root", root);
        File myDir = new File(root);
        myDir.mkdirs();

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);

        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        MainActivity.savedImages.add(file.getPath());
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(Filters.this, "Saved..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Filters.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 85, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                storagePermission = true;
            }
        }
    }

}