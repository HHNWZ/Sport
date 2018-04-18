package qwer;

import java.util.ArrayList;

/**
 * Created by 黃小黃 on 2018/4/18.
 */

public class theDate {
    public String Diary;
    public ArrayList<Integer> BK_list=new ArrayList<Integer>();
    public ArrayList<Integer> LH_list=new ArrayList<Integer>();
    public ArrayList<Integer> DN_list=new ArrayList<Integer>();
    public theDate(String diary){
        this.Diary=diary;
    }

    public void eat(String foodType,int foodID) {
        switch (foodType){
            case "BK":
                BK_list.add(foodID);
                break;
            case "LH":
                LH_list.add(foodID);
                break;
            case "DN":
                DN_list.add(foodID);
                break;
        }
    }
    public int getfoodnum(String foodType,int foodID){
        int num = 0;
        switch (foodType){
            case "BK":
                for(int i=0;i<BK_list.size();i++){
                    if(BK_list.get(i)==foodID){
                        num++;
                    }
                }
                break;
            case "LH":
                for(int i=0;i<LH_list.size();i++){
                    if(LH_list.get(i)==foodID){
                        num++;
                    }
                }
                break;
            case "DN":
                for(int i=0;i<DN_list.size();i++){
                    if(DN_list.get(i)==foodID){
                        num++;
                    }
                }
                break;
        }
        return num;
    }
}
