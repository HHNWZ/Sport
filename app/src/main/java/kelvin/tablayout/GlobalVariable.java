package kelvin.tablayout;

import android.app.Application;

public class GlobalVariable  extends Application {
    private String Word;     //要傳送的字串
    private String Task;
    private String Task_reg;
    private String keyDay;
    private String KeyHour;
    private String chat_id_send;
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
}
