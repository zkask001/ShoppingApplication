package com.example.shoppingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SupermarketActivity extends AppCompatActivity {

    //variable to retrieve floorplan view on create
    ImageView floorplan;
    //variable to retrieve location pin view on create
    ImageView location_pin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket);

        floorplan = (ImageView) findViewById(R.id.floorplan);
        location_pin = (ImageView) findViewById(R.id.locationPin);

        addTouchListener();

//        ImageView imageView=(ImageView) findViewById(R.id.floorplan);
//        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.BLACK);
//        canvas.drawColor(Color.TRANSPARENT);
//        canvas.drawCircle(50, 50, 10, paint);
//        imageView.setImageBitmap(bitmap);
    }

    private void addTouchListener(){
        ImageView floorplan = (ImageView) findViewById(R.id.floorplan);
        floorplan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                float x = motionEvent.getX();
                float y = motionEvent.getY();

                Toast.makeText(SupermarketActivity.this, "Coordinates: x= " + x + " y= " + y,Toast.LENGTH_SHORT).show();

                //run method to place location pin at coordinates
                placeImage(x,y);
                return false;
            }
        });
    }

    private void placeImage(float X, float Y){
        int touchX = (int) X;
        int touchY = (int) Y;

        //placing at center of touch
        int viewWidth = location_pin.getWidth();
        int viewHeight = location_pin.getHeight();
        viewWidth = viewWidth / 2;
        viewHeight = viewHeight / 2;

        location_pin.layout(touchX - viewWidth, touchY - viewHeight, touchX + viewWidth, touchY + viewHeight);
        location_pin.setVisibility(View.VISIBLE);
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity2.class));
        finish();
    }
}