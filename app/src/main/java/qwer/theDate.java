package qwer;

/**
 * Created by 黃小黃 on 2018/4/18.
 */

public class theDate {
    public String Diary;
    public int[][] eated=new int[3][9];
    private int[] food_KLL=new int[]{225,120,150,140,270,206,65,239,106};
    //public ArrayList<Integer> BK_list=new ArrayList<Integer>();
    //public ArrayList<Integer> LH_list=new ArrayList<Integer>();
   //public ArrayList<Integer> DN_list=new ArrayList<Integer>();
    public theDate(String diary){
        this.Diary=diary;
        for(int i=0;i<3;i++){
            for(int j=0;j<9;j++){
                eated[i][j]=0;
            }
        }
    }

    public void setEated(int foodType,int foodID,int theNum) {
        this.eated[foodType][foodID]=theNum;
    }
    public void addEated( int foodType, int foodID,int theNum){
        this.eated[foodType][foodID]+=theNum;
    }
    public int getEated(int foodType,int foodID) {
        return this.eated[foodType][foodID];
    }
    public int getfoodnum(int foodType,int foodID){
        return this.eated[foodType][foodID];
    }
    public int todayKLL(){
        int countKLL=0;
        for (int i=0;i<3;i++){
            for(int j=0;j<9;j++){
                countKLL+=eated[i][j]*food_KLL[j];
            }
        }
        return countKLL;
    }
    public String getDiary(){
        return this.Diary;
    }
}
