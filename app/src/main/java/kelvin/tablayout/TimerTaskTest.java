package kelvin.tablayout;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimerTaskTest extends java.util.TimerTask{
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    @Override
    public void run() {
        // TODO Auto-genera method stub
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mUserRef.child("exercise_count").child("walking").child("today_record").setValue(0);
        mUserRef.child("exercise_count").child("running").child("today_record").setValue(0);
        mUserRef.child("exercise_count").child("crunches").child("today_record").setValue(0);
        mUserRef.child("exercise_count").child("squats").child("today_record").setValue(0);
        mUserRef.child("exercise_count").child("walking").child("today_record").setValue(0);
        String getWeek= Week.getWeek(System.currentTimeMillis());
        if(getWeek.equals("三")){
            mUserRef.child("exercise_count").child("walking").child("week_record").setValue(0);
            mUserRef.child("exercise_count").child("running").child("week_record").setValue(0);
            mUserRef.child("exercise_count").child("crunches").child("week_record").setValue(0);
            mUserRef.child("exercise_count").child("squats").child("week_record").setValue(0);
            mUserRef.child("exercise_count").child("walking").child("week_record").setValue(0);
            Log.i("刪除時間",""+Time.get_start_time(System.currentTimeMillis()));
        }

    }
}
