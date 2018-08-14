package kelvin.tablayout;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class writeNewExerciseData extends Application{
    public static DatabaseReference mDatabase;
    public static FirebaseAuth mAuth;





    public static void setNewExerciseData(String exercise_type,String start_time,String end_time,double distance,String duration,int mean_heart_rate,int calorie,double incline_distance,double decline_distance,int max_heart_rate,int max_altitude,int min_altitude,double mean_speed,double max_speed,String Todate,
            String Totime){
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        ExerciseData exerciseData = new ExerciseData(start_time,end_time,distance,duration,mean_heart_rate,calorie,incline_distance,decline_distance,max_heart_rate,max_altitude,min_altitude,mean_speed,max_speed);
        mDatabase.child("exercise").child(exercise_type).child(Todate).child(Totime).setValue(exerciseData);
    }

    public static void  setNewExerciseData2(String exercise_type,String start_time,String end_time,String duration,int mean_heart_rate,int calorie,int max_heart_rate,String Todate,String Totime){
        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        ExerciseData2 exerciseData2 =new ExerciseData2(start_time,end_time,duration,mean_heart_rate,calorie,max_heart_rate);
        mDatabase.child("exercise").child(exercise_type).child(Todate).child(Totime).setValue(exerciseData2);

    }
}
