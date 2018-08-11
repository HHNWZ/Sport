package kelvin.tablayout;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ExercisePost {
    public double all_record;
    public double long_distance;
    public double short_distance;
    public double today_distance;
    public double today_record;
    public Map<String, Boolean> stars = new HashMap<>();


    public ExercisePost(){

    }

    public ExercisePost(double all_record,double long_distance,double short_distance,double today_distance,double today_record){
        this.all_record =all_record;
        this.long_distance = long_distance;
        this.short_distance=short_distance;
        this.today_distance=today_distance;
        this.today_record=today_record;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("all_record",all_record);
        result.put("long_distance",long_distance);
        result.put("short_distance",short_distance);
        result.put("today_distance",today_distance);
        result.put("today_record",today_record);

        return result;
    }
}
