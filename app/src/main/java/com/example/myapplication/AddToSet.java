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
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class AddToSet extends AppCompatActivity {

    ImageView IDProf;
    Button Upload_Btn;

    //EditText label;

    private String Document_img1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_set);

        IDProf=(ImageView)findViewById(R.id.IdProf);
        Upload_Btn=(Button)findViewById(R.id.UploadBtn);
        //label= findViewById(R.id.edLab_add);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String u = b.getString("user");

        //label.setHint(u);

        IDProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(AddToSet.this);
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

                            //File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                            //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            //startActivityForResult(intent, 1);
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

        Upload_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Document_img1.equals("")) {
                    ContextThemeWrapper ctw = new ContextThemeWrapper( AddToSet.this, R.style.Widget_MaterialComponents_TimePicker);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                    alertDialogBuilder.setTitle("Id Prof Can't Empty ");
                    alertDialogBuilder.setMessage("Id Prof Can't empty please select any one document");
                    alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    alertDialogBuilder.show();
                    return;
                }
                else{

                    //Upload here

                    String url = "http://197.1.111.148/upload_save_picture";

                    RequestQueue requestQueue = Volley.newRequestQueue(AddToSet.this);

                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("base64", Document_img1);
                        postData.put("label", u);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(AddToSet.this, "Upload Complete", Toast.LENGTH_SHORT).show();
                            System.out.println(response);
                            Log.e("UPLOAD", response.toString());
                            Acceuil.pictureData.add(Document_img1);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    requestQueue.add(jsonObjectRequest);

                }

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
                IDProf.setImageBitmap(imageBitmap);
                BitMapToString(imageBitmap);

                //Perfect !

                /*File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    bitmap=getResizedBitmap(bitmap, 400);
                    IDProf.setImageBitmap(bitmap);
                    BitMapToString(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

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
                IDProf.setImageBitmap(thumbnail);

                BitMapToString(thumbnail);
            }
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