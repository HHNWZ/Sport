package kelvin.tablayout;

import android.app.Application;

import java.util.Calendar;

public class Time extends Application {
    public static  String get_duration_time(long duration_milliseconds){
        String durationTime = "";

        Calendar duration_time = Calendar.getInstance();
        duration_time.setTimeInMillis(duration_milliseconds);
        int duration_hour = duration_time.get(Calendar.HOUR);//12小时制
        int duration_minute = duration_time.get(Calendar.MINUTE);
        int duration_second = duration_time.get(Calendar.SECOND);

        if(duration_milliseconds<3600000){
            durationTime=""+duration_minute+"分鐘"+duration_second+"秒";
        }
        if(duration_milliseconds>=3600000){
            durationTime=""+duration_hour+"時"+duration_minute+"分鐘"+duration_second+"秒";
        }
        return durationTime;
    }

    public static String get_start_time(long start_milliseconds){
        String startTime ="";
        Calendar start_calendar=Calendar.getInstance();
        start_calendar.setTimeInMillis(start_milliseconds);
        int start_year = start_calendar.get(Calendar.YEAR);
        int start_month= start_calendar.get(Calendar.MONTH)+1;
        int start_day =start_calendar.get(Calendar.DAY_OF_MONTH);
        int start_hour = start_calendar.get(Calendar.HOUR_OF_DAY);
        int start_minute=start_calendar.get(Calendar.MINUTE);
        int start_second=start_calendar.get(Calendar.SECOND);
        String start_week=Week.getWeek(start_milliseconds);
        startTime=""+start_year+"年"+start_month+"月"+start_day+"日 週"+start_week+" "+start_hour+"時 "+start_minute+"分鐘 "+start_second+"秒 ";

        return startTime;
    }

    public static String get_end_time(long end_milliseconds){
        String endTime = "";
        Calendar end_calendar=Calendar.getInstance();
        end_calendar.setTimeInMillis(end_milliseconds);
        int end_year = end_calendar.get(Calendar.YEAR);
        int end_month= end_calendar.get(Calendar.MONTH)+1;
        int end_day =end_calendar.get(Calendar.DAY_OF_MONTH);
        int end_hour = end_calendar.get(Calendar.HOUR_OF_DAY);
        int end_minute=end_calendar.get(Calendar.MINUTE);
        int end_second=end_calendar.get(Calendar.SECOND);
        String end_week=Week.getWeek(end_milliseconds);
        endTime=""+end_year+"年"+end_month+"月"+end_day+"日 週"+end_week+" "+end_hour+"時 "+end_minute+"分鐘 "+end_second+"秒 ";

        return endTime;
    }

    public static String getToDate(long Datemilliseconds){
        String Todate ="";
        Calendar date=Calendar.getInstance();
        date.setTimeInMillis(Datemilliseconds);
        int y=date.get(Calendar.YEAR);
        int m=date.get(Calendar.MONTH)+1;
        int d=date.get(Calendar.DAY_OF_MONTH);
        String DateWeek=Week.getWeek(Datemilliseconds);
        Todate=""+y+"年"+m+"月"+d+"日 週"+DateWeek ;
        return  Todate;
    }

    public static  String getTime(long Timemilliseconds){
        String Time ="";
        Calendar time=Calendar.getInstance();
        time.setTimeInMillis(Timemilliseconds);
        int H=time.get(Calendar.HOUR_OF_DAY);
        int m=time.get(Calendar.MINUTE);
        int s=time.get(Calendar.SECOND);
        Time=""+H+"時"+m+"分"+s+"秒";
        return Time;
    }

    public static String changeYogaTime(long yogaMilliseconds){
        String yogaTime="";
        Calendar yoga_time = Calendar.getInstance();
        yoga_time.setTimeInMillis(yogaMilliseconds);
        int yoga_hour = yoga_time.get(Calendar.HOUR);//12小时制
        int yoga_minute = yoga_time.get(Calendar.MINUTE);
        int yoga_second = yoga_time.get(Calendar.SECOND);
        if(yogaMilliseconds<60000){
            yogaTime=""+yoga_second+"秒";
        }else if(yogaMilliseconds>=60000||yogaMilliseconds<3600000){
            yogaTime=""+yoga_minute+"分鐘"+yoga_second+"秒";
        }else if(yogaMilliseconds>=3600000){
            yogaTime=""+yoga_hour+"時"+yoga_minute+"分鐘"+yoga_second+"秒";
        }
        return yogaTime;


    }

    public static float yogaWeekminute(long yogaMilliseconds){
        double yogaTime1=yogaMilliseconds/60000d;
        float yogaTime=(float)yogaTime1;

        return yogaTime;
    }

    public static String get_Chat_date(long chat_milliseconds){
        String endTime = "";
        Calendar end_calendar=Calendar.getInstance();
        end_calendar.setTimeInMillis(chat_milliseconds);
        int end_year = end_calendar.get(Calendar.YEAR);
        int end_month= end_calendar.get(Calendar.MONTH)+1;
        int end_day =end_calendar.get(Calendar.DAY_OF_MONTH);
        int end_hour = end_calendar.get(Calendar.HOUR_OF_DAY);
        int end_minute=end_calendar.get(Calendar.MINUTE);
        //int end_second=end_calendar.get(Calendar.SECOND);
        String end_week=Week.getWeek(chat_milliseconds);
        endTime=""+end_year+"年"+end_month+"月"+end_day+"日 週"+end_week+" "+end_hour+"時 "+end_minute+"分鐘  ";

        return endTime;
    }

    public static String getWalking_data(long walking_data){
        String walkingData="";
        Calendar walking_data2=Calendar.getInstance();
        walking_data2.setTimeInMillis(walking_data);
        int walkingdata =walking_data2.get(Calendar.DAY_OF_MONTH);

        if(walkingdata==1||walkingdata==7||walkingdata==13||walkingdata==19||walkingdata==25){
            walkingData="5";
        }
        if(walkingdata==2||walkingdata==8||walkingdata==14||walkingdata==20||walkingdata==26){
            walkingData="6";
        }
        if(walkingdata==3||walkingdata==9||walkingdata==15||walkingdata==21||walkingdata==27){
            walkingData="7";
        }
        if(walkingdata==4||walkingdata==10||walkingdata==16||walkingdata==22||walkingdata==28){
            walkingData="8";
        }
        if(walkingdata==5||walkingdata==11||walkingdata==17||walkingdata==23||walkingdata==29){
            walkingData="9";
        }
        if(walkingdata==6||walkingdata==12||walkingdata==18||walkingdata==24||walkingdata==30||walkingdata==31){
            walkingData="10";
        }
        return walkingData;
    }

    public static String getRunning_data(long running_data){
        String runningData="";
        Calendar running_data2=Calendar.getInstance();
        running_data2.setTimeInMillis(running_data);
        int runningdata =running_data2.get(Calendar.DAY_OF_MONTH);

        if(runningdata==1||runningdata==7||runningdata==13||runningdata==19||runningdata==25){
            runningData="5";
        }
        if(runningdata==2||runningdata==8||runningdata==14||runningdata==20||runningdata==26){
            runningData="6";
        }
        if(runningdata==3||runningdata==9||runningdata==15||runningdata==21||runningdata==27){
            runningData="7";
        }
        if(runningdata==4||runningdata==10||runningdata==16||runningdata==22||runningdata==28){
            runningData="8";
        }
        if(runningdata==5||runningdata==11||runningdata==17||runningdata==23||runningdata==29){
            runningData="9";
        }
        if(runningdata==6||runningdata==12||runningdata==18||runningdata==24||runningdata==30||runningdata==31){
            runningData="10";
        }
        return runningData;
    }






}
