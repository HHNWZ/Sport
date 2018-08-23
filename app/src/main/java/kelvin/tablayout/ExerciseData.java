package kelvin.tablayout;

public class ExerciseData {
    public String start_time;
    public String end_time;
    public double distance;
    public String duration;
    public int mean_heart_rate;
    public int calorie;
    public double incline_distance;
    public double decline_distance;
    public int max_heart_rate;
    public int max_altitude;
    public int min_altitude;
    public double mean_speed;
    public double max_speed;

    public ExerciseData(){

    }

    public  ExerciseData(String start_time,String end_time,double distance,String duration,int mean_heart_rate,int calorie,double incline_distance,double decline_distance,int max_heart_rate,int max_altitude,int min_altitude,double mean_speed,double max_speed){
        this.start_time =start_time;
        this.end_time = end_time;
        this.distance=distance;
        this.duration=duration;
        this.mean_heart_rate=mean_heart_rate;
        this.calorie=calorie;
        this.incline_distance=incline_distance;
        this.decline_distance=decline_distance;
        this.max_heart_rate=max_heart_rate;
        this.max_altitude=max_altitude;
        this.min_altitude=min_altitude;
        this.mean_speed=mean_speed;
        this.max_speed=max_speed;


    }
}
