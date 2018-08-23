package necowneco.tablayout;

/**
 * Created by 黃小黃 on 2017/12/14.
 */

public class ArtListItem {
    private String myTitle;
    private String myAut;
    private String myCon;
    private int myGoodnum;
    private int myResnum;
    private int myClass;
    public ArtListItem(String theTitle,String theAut,String theCon,int theGoodnum,int theResnum,int theClass){
        this.myTitle=theTitle;
        this.myAut=theAut;
        this.myCon=theCon;
        this.myGoodnum=theGoodnum;
        this.myResnum=theResnum;
        this.myClass=theClass;
    }
    public String getMyTitle(){
        return myTitle;
    }
    public void setMyTitle(String theTitle){
        this.myTitle=theTitle;
    }
    public String getMyAut(){
        return myAut;
    }
    public void setMyAut(String theAut){
        this.myAut=theAut;
    }
    public String getMyCon(){return myCon;}
    public void setMyCon(String theCon){this.myCon=theCon;}
    public int getMyGoodnum(){return myGoodnum;}
    public void setMyGoodnum(int theGoodnum){this.myGoodnum=theGoodnum;}
    public int getMyResnum(){return myResnum;}
    public void setMyResnum(int theResnum){this.myResnum=theResnum;}
    public int getMyClass(){return myClass;}
    public void setMyClass(int theClass){this.myClass=theClass;}
}
