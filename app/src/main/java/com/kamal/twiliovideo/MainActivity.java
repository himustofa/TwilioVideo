package com.kamal.twiliovideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_MIC_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkPermissionForCameraAndMicrophone()) {
            requestPermissionForCameraAndMicrophone();
        } else {
            //createAudioAndVideoTracks();
            //setAccessToken();
            startActivity(new Intent(this, VideoActivity.class));
            Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkPermissionForCameraAndMicrophone() {
        int resultCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int resultMic = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return (resultCamera == PackageManager.PERMISSION_GRANTED) && (resultMic == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissionForCameraAndMicrophone() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this, "Camera and Microphone permissions needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, CAMERA_MIC_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
            boolean cameraAndMicPermissionGranted = true;
            for (int grantResult : grantResults) {
                cameraAndMicPermissionGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
            }
            if (cameraAndMicPermissionGranted) {
                //createAudioAndVideoTracks();
                //setAccessToken();
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, VideoActivity.class));
            } else {
                Toast.makeText(this, "permissions needed", Toast.LENGTH_LONG).show();
            }
        }
    }

    /*public void getAccessToken() {
        // Required for all types of tokens
        String twilioAccountSid = "ACxxxxxxxxxxxx";
        String twilioApiKey = "SKxxxxxxxxxxxx";
        String twilioApiSecret = "xxxxxxxxxxxxxx";

        String identity = "alice";

        // Create Video grant
        VideoGrant grant = new VideoGrant();
        grant.setRoom("DailyStandup");

        // Create access token
        AccessToken token = new AccessToken.Builder(
                twilioAccountSid,
                twilioApiKey,
                twilioApiSecret
        ).identity(identity).grant(grant).build();

        System.out.println(token.toJwt());
    }*/

}
