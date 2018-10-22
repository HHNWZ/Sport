package kelvin.tablayout;

import android.app.Application;

public class GlobalVariable  extends Application {
    private String Word;     //要傳送的字串
    private String Task;
    private String Task_reg;
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
}
