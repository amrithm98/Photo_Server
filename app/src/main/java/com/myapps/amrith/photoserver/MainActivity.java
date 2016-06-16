package com.myapps.amrith.photoserver;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static final int RESULT_LOAD_IMAGE=1;
    private static final String Server="http://mycraze.comuf.com/savepic.php";
    ImageView upimage,downimage;
    Button up,down;
    EditText upimg,downimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        upimage=(ImageView)findViewById(R.id.upimage);
        downimage=(ImageView)findViewById(R.id.downimage);
        up=(Button)findViewById(R.id.up);
        Button fb=(Button)findViewById(R.id.fb);
        upimg=(EditText)findViewById(R.id.upimg);
        upimage.setOnClickListener(this);
        up.setOnClickListener(this);

            }
  /*  private void sharePhotoToFacebook(){
       /* Bitmap image = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Give me my codez or I will ... you know, do that thing you don't like!")
                .build();
        Log.d("Image","Fn here");
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);

    }*/



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
            switch(v.getId()){
                case R.id.upimage:
                    Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                    break;
                case R.id.up:
                    Bitmap image=((BitmapDrawable)upimage.getDrawable()).getBitmap();
                    image=Bitmap.createScaledBitmap(image,300,300,true);
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String encodedstr= Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    RequestParams params=new RequestParams();
                    params.put("name",upimg.getText().toString());
                    params.put("image", encodedstr);
                    Log.d("Photo", encodedstr);
                    AsyncHttpClient client=new AsyncHttpClient();
                    client.post(Server, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_SHORT).show();

                        }
                    });
                    break;
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode==RESULT_OK && data!=null){
            Uri selectedimage=data.getData();
            upimage.setImageURI(selectedimage);

        }
    }

}
