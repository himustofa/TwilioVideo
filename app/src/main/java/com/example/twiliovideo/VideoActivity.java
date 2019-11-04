package com.example.twiliovideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twilio.common.TwilioAccessManager;
import com.twilio.common.TwilioAccessManagerFactory;
import com.twilio.common.TwilioAccessManagerListener;
import com.twilio.conversations.AudioOutput;
import com.twilio.conversations.CameraCapturer;
import com.twilio.conversations.CameraCapturerFactory;
import com.twilio.conversations.CapturerErrorListener;
import com.twilio.conversations.CapturerException;
import com.twilio.conversations.Conversation;
import com.twilio.conversations.ConversationsClient;
import com.twilio.conversations.ConversationsClientListener;
import com.twilio.conversations.IncomingInvite;
import com.twilio.conversations.OutgoingInvite;
import com.twilio.conversations.TwilioConversations;
import com.twilio.conversations.TwilioConversationsException;
import com.twilio.conversations.VideoViewRenderer;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoActivity extends AppCompatActivity {

    private String mAccessToken;
    private static final String TAG = "VideoActivity";
    /*
     * Android application UI elements
     */
    private FrameLayout previewFrameLayout;
    private ViewGroup localContainer;
    private ViewGroup participantContainer;
    private FloatingActionButton callActionFab;
    private OkHttpClient client = new OkHttpClient();

    private TwilioAccessManager accessManager;
    private ConversationsClient conversationsClient;
    private CameraCapturer cameraCapturer;

    private Conversation conversation;
    private OutgoingInvite outgoingInvite;
    private Context mContext;

    /*
     * A VideoViewRenderer receives frames from a local or remote video track and renders the frames to a provided view
     */
    private VideoViewRenderer participantVideoRenderer;
    private VideoViewRenderer localVideoRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        mContext = this.getApplicationContext();

        /*
         * Load views from resources
         */
        previewFrameLayout = (FrameLayout) findViewById(R.id.previewFrameLayout);
        localContainer = (ViewGroup) findViewById(R.id.localContainer);
        participantContainer = (ViewGroup) findViewById(R.id.participantContainer);

        getCapabilityToken();
    }

    private void getCapabilityToken() {
        try {
            run("http://[your-ngrok-url].ngrok.io/token", new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String token = response.body().string();
                        JSONObject obj = new JSONObject(token);
                        mAccessToken = obj.getString("token");
                        Log.d(TAG, token);
                        initializeTwilioSdk(mAccessToken);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Call run(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call response = client.newCall(request);
        response.enqueue(callback);
        return response;
    }

    private void initializeTwilioSdk(final String accessToken) {
        TwilioConversations.setLogLevel(TwilioConversations.LogLevel.DEBUG);

        if(!TwilioConversations.isInitialized()) {
            TwilioConversations.initialize(this.mContext, new TwilioConversations.InitListener() {
                @Override
                public void onInitialized() {
                    accessManager = TwilioAccessManagerFactory.createAccessManager(accessToken, accessManagerListener());
                    conversationsClient = TwilioConversations.createConversationsClient(accessManager, conversationsClientListener());
                    // Specify the audio output to use for this conversation client
                    conversationsClient.setAudioOutput(AudioOutput.SPEAKERPHONE);

                    // Initialize the camera capturer and start the camera preview
                    cameraCapturer = CameraCapturerFactory.createCameraCapturer(VideoActivity.this, CameraCapturer.CameraSource.CAMERA_SOURCE_FRONT_CAMERA, previewFrameLayout, capturerErrorListener());
                    startPreview();
                    // Register to receive incoming invites
                    conversationsClient.listen();
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(VideoActivity.this, "Failed to initialize the Twilio Conversations SDK", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private TwilioAccessManagerListener accessManagerListener() {
        return new TwilioAccessManagerListener() {
            @Override
            public void onAccessManagerTokenExpire(TwilioAccessManager twilioAccessManager) {
            }
            @Override
            public void onTokenUpdated(TwilioAccessManager twilioAccessManager) {
            }
            @Override
            public void onError(TwilioAccessManager twilioAccessManager, String s) {
            }
        };
    }

    private ConversationsClientListener conversationsClientListener() {
        return new ConversationsClientListener() {
            @Override
            public void onStartListeningForInvites(ConversationsClient conversationsClient) {
            }

            @Override
            public void onStopListeningForInvites(ConversationsClient conversationsClient) {
            }

            @Override
            public void onFailedToStartListening(ConversationsClient conversationsClient, TwilioConversationsException e) {
            }

            @Override
            public void onIncomingInvite(ConversationsClient conversationsClient, IncomingInvite incomingInvite) {

            }

            @Override
            public void onIncomingInviteCancelled(ConversationsClient conversationsClient, IncomingInvite incomingInvite) {

            }
        };
    }

    private CapturerErrorListener capturerErrorListener() {
        return new CapturerErrorListener() {
            @Override
            public void onError(CapturerException e) {
                Log.e(TAG, "Camera capturer error:" +  e.getMessage());
            }
        };
    }

    private void startPreview() {
        cameraCapturer.startPreview();
    }

    private void stopPreview() {
        if(cameraCapturer != null && cameraCapturer.isPreviewing()) {
            cameraCapturer.stopPreview();
        }
    }
}
