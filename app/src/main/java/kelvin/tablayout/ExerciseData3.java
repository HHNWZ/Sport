package kelvin.tablayout;

public class ExerciseData3 {
    public String start_time;
    public String end_time;
    public String duration;
    public int mean_heart_rate;
    public int calorie;
    public int max_heart_rate;
    public int count;

    public ExerciseData3(){

    }

    public  ExerciseData3(String start_time,String end_time,String duration,int mean_heart_rate,int calorie,int max_heart_rate,int count){
        this.start_time =start_time;
        this.end_time = end_time;
        this.duration=duration;
        this.mean_heart_rate=mean_heart_rate;
        this.calorie=calorie;
        this.max_heart_rate=max_heart_rate;
        this.count=count;



    }
}
