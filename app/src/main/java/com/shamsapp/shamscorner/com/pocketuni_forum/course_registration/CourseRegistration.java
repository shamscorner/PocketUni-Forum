package com.shamsapp.shamscorner.com.pocketuni_forum.course_registration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shamsapp.shamscorner.com.pocketuni_forum.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shamim on 24-Jun-16.
 */
public class CourseRegistration extends AppCompatActivity {

    public static final String LOGINPREF = "loginpref" ;
    private static final int CAMERA_REQUEST = 1888;
    SharedPreferences sharedpreferences;
    String username = "";

    LinearLayout regMainLayout;
    ImageView imgRegProof;

    String ba1;
    String mCurrentPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_reg_panel);

        // set the sharedpreferences
        sharedpreferences = getSharedPreferences(LOGINPREF, Context.MODE_PRIVATE);

        //get the username from the sharedpreference
        username = sharedpreferences.getString("USERNAME", "");

        regMainLayout = (LinearLayout)findViewById(R.id.reg_main_container);

        // call the course details class
        new RegistrationDetailsView(this, regMainLayout).execute(username);

        imgRegProof = (ImageView)findViewById(R.id.img_bank_receipt);

    }

    public void doneRegistration(View view) {
        //upload the picture
        upload();
        // Upload image to server
        new uploadToServer().execute();
    }
    private void upload() {
        Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, 1);
    }

    public void takePhotoForRegistration(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        Log.e("Getpath", "Cool" + mCurrentPhotoPath);
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            // Get the dimensions of the View
            int targetW = imgRegProof.getWidth();
            int targetH = imgRegProof.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            imgRegProof.setImageBitmap(bitmap);
        }
    }

    public class uploadToServer extends AsyncTask<String, Void, String> {

        private ProgressDialog pd = new ProgressDialog(CourseRegistration.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Please wait...");
            pd.show();
        }

        @Override
        protected String doInBackground(String... arg) {
            try{

                String link = "http://shamscorner001.site88.net/Uni_Forumb69c5929474a3779df762577b7cce8eb/UniForum/mCourse_Reg_complete.php";
                String data = URLEncoder.encode("imagename", "UTF-8") + "=" + URLEncoder.encode(username+System.currentTimeMillis()+".jpg", "UTF-8");
                data += "&" + URLEncoder.encode("roll_no", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("base64", "UTF-8") + "=" + URLEncoder.encode(ba1, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null){
                    sb.append(line);
                    break;
                }
                return sb.toString();

            }catch(Exception e){
                Toast.makeText(getApplicationContext(),"Error: " + e.getMessage() , Toast.LENGTH_LONG);
                return new String("Error: " + e.getMessage());
            }
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }
}
