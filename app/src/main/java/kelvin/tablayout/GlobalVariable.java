package kelvin.tablayout;

import android.app.Application;

public class GlobalVariable  extends Application {
    private String Word;     //要傳送的字串
    private String Task;
    private String Task_reg;
    private String keyDay;
    private String KeyHour;
    private String chat_id_send;
    private String file_name;
    private int my_squats_count;
    private int friend_squats_count;
    private  String friend_id;
    private String food_note;
    private int squats_today_count;
    //修改 變數字串
    public void setWord(String word){
        this.Word = word;
    }
    //顯示 變數字串
    public String getWord() {
        return Word;
    }

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public String getTask_reg() {
        return Task_reg;
    }

    public void setTask_reg(String task_reg) {
        Task_reg = task_reg;
    }

    public String getKeyDay() {
        return keyDay;
    }

    public void setKeyDay(String keyDay) {
        this.keyDay = keyDay;
    }

    public String getKeyHour() {
        return KeyHour;
    }

    public void setKeyHour(String keyHour) {
        KeyHour = keyHour;
    }

    public String getChat_id_send() {
        return chat_id_send;
    }

    public void setChat_id_send(String chat_id_send) {
        this.chat_id_send = chat_id_send;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getMy_squats_count() {
        return my_squats_count;
    }

    public void setMy_squats_count(int my_squats_count) {
        this.my_squats_count = my_squats_count;
    }

    public int getFriend_squats_count() {
        return friend_squats_count;
    }

    public void setFriend_squats_count(int friend_squats_count) {
        this.friend_squats_count = friend_squats_count;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getFood_note() {
        return food_note;
    }

    public void setFood_note(String food_note) {
        this.food_note = food_note;
    }

    public int getSquats_today_count() {
        return squats_today_count;
    }

    public void setSquats_today_count(int squats_today_count) {
        this.squats_today_count = squats_today_count;
    }
}
