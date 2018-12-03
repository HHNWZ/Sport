package kelvin.tablayout;

public class ExerciseTitle {
    int exercise_image;
    String exercise_name;
    String exercise_video_length;


    public ExerciseTitle(int exercise_image, String exercise_name,String exercise_video_length) {
        this.exercise_image = exercise_image;
        this.exercise_name = exercise_name;
        this.exercise_video_length=exercise_video_length;
    }

    public int getExercise_image() {
        return exercise_image;
    }

    public void setExercise_image(int exercise_image) {
        this.exercise_image = exercise_image;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getExercise_video_length() {
        return exercise_video_length;
    }

    public void setExercise_video_length(String exercise_video_length) {
        this.exercise_video_length = exercise_video_length;
    }
}
