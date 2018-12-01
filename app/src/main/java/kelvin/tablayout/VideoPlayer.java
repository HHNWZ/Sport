package kelvin.tablayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.a888888888.sport.MainActivity;
import com.example.a888888888.sport.R;
import com.onesignal.OneSignal;

public class VideoPlayer extends AppCompatActivity implements  View.OnClickListener{
    ProgressDialog mDialog;
    VideoView videoView;
    Button btnPlayerPause;

    String videoURL="http://media.w3.org/2010/05/sintel/trailer.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new MainActivity.ExampleNotificationOpenedHandler())
                .init();
        Toolbar activity_video_player_toolbar=findViewById(R.id.activity_video_player_toolbar);
        setSupportActionBar(activity_video_player_toolbar);
        ActionBar activity_video_player_action_bar = getSupportActionBar();
        if (activity_video_player_action_bar != null) {
            activity_video_player_action_bar.setTitle("運動姿勢調整");
            activity_video_player_action_bar.setDisplayHomeAsUpEnabled(true);
        }

        videoView=findViewById(R.id.videoView);
        btnPlayerPause=findViewById(R.id.btn_play_pause);
        btnPlayerPause.setOnClickListener(this);

        
    }
    @Override
    public void onClick(View v){
        mDialog=new ProgressDialog(VideoPlayer.this);
        mDialog.setMessage("請求等待......");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        try {
            if (!videoView.isPlaying()) {
                Uri uri = Uri.parse(videoURL);
                videoView.setVideoURI(uri);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        btnPlayerPause.setText("播放");

                    }
                });
            }else {
                videoView.pause();
                btnPlayerPause.setText("播放");
            }
        }
        catch (Exception ex){

        }
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @Override
            public void onPrepared(MediaPlayer mp){
                mDialog.dismiss();
                mp.setLooping(true);
                videoView.start();
                btnPlayerPause.setText("停止");
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==android.R.id.home){
            Intent intent = new Intent(VideoPlayer.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(VideoPlayer.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
