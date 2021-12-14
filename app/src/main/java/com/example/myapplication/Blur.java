package com.example.myapplication;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class Blur extends AppCompatActivity {

    ImageView IDProfBlur;
    Button processBlur;
    TextView type;

    private String Document_img1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        IDProfBlur = findViewById(R.id.IdProfResult);
        processBlur = findViewById(R.id.processBlurBtn);
        type = findViewById(R.id.tv_filterType);

        IDProfBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(Blur.this);
                builder.setTitle("Add Photo!");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Take Photo"))
                        {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            try {
                                startActivityForResult(takePictureIntent, 1);
                                //File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                                //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
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

        processBlur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //send to api
                //wait for response
                //show alert dialog with response and save button

                Intent i = getIntent();
                Bundle b = i.getExtras();
                String u = b.getString("filterType");

                type.setText(u + " Filter");

                String route = "";

                switch(u) {
                    case "Blur":
                        route = "blur_picture";
                        break;
                    case "Black & White":
                        route = "black_white_picture";
                        break;
                    default:
                        route = "negative_picture";
                }

                String url = MainActivity.apiURL + route;

                RequestQueue requestQueue = Volley.newRequestQueue(Blur.this);

                JSONObject postData = new JSONObject();
                try {
                    postData.put("base64", Document_img1);
                    Log.e("myObject", postData.toString());
                    Toast.makeText(Blur.this, "Processing..", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error", e.toString());
                    Toast.makeText(Blur.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
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

                        LayoutInflater li = LayoutInflater.from(Blur.this);
                        View promptsView = li.inflate(R.layout.result_image, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Blur.this);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        //final EditText userInput = (EditText) promptsView.findViewById(R.id.ed_api_dialog);

                        final ImageView resultImg = (ImageView) promptsView.findViewById(R.id.IdProfResult);
                        bmp = getResizedBitmap(bmp, 400); //un-necessary ?
                        resultImg.setImageBitmap(bmp);
                        final Bitmap saveBmp = bmp;

                        // set dialog message
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("SAVE",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                // save to device
                                                saveImage(saveBmp, "test");
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
                        Toast.makeText(Blur.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);

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
                IDProfBlur.setImageBitmap(imageBitmap);
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
                IDProfBlur.setImageBitmap(thumbnail);

                BitMapToString(thumbnail);
            }
        }

    }

    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = Environment.getExternalStorageDirectory().toString() + "/myFilters";
        Log.e("root", root);
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(Blur.this, "Saved..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Blur.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
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

}