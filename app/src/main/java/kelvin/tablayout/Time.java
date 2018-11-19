package kelvin.tablayout;

import android.app.Application;

import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time extends Application {
    private static final String formaterCalender ="yyyy年MMMd日 EEE";
    public static  String get_duration_time(long duration_milliseconds){
        String durationTime = "";
        String duration_minute_str;
        String duration_hour_str;
        String duration_second_str;
        Calendar duration_time = Calendar.getInstance();
        duration_time.setTimeInMillis(duration_milliseconds);
        int duration_hour = duration_time.get(Calendar.HOUR);//12小时制
        int duration_minute = duration_time.get(Calendar.MINUTE);
        int duration_second = duration_time.get(Calendar.SECOND);
        duration_hour_str=String.format("%02d",duration_hour);
        duration_second_str=String.format("%02d",duration_second);
        duration_minute_str=String.format("%02d",duration_minute);
        if(duration_milliseconds<3600000){
            durationTime=""+duration_minute_str+"分鐘"+duration_second_str+"秒";
        }
        if(duration_milliseconds>=3600000){
            durationTime=""+duration_hour_str+"時"+duration_minute_str+"分鐘"+duration_second_str+"秒";
        }
        return durationTime;
    }

    public static String get_duration_hour(long duration_milliseconds){
        String duration_Hour="";

        Calendar duration_time = Calendar.getInstance();
        duration_time.setTimeInMillis(duration_milliseconds);
        int duration_hour = duration_time.get(Calendar.HOUR);//12小时制
        duration_Hour=""+duration_hour;

        return duration_Hour;

    }

    public static String get_duration_minute(long duration_milliseconds){
        String duration_Minute="";

        Calendar duration_time = Calendar.getInstance();
        duration_time.setTimeInMillis(duration_milliseconds);
        int duration_minute = duration_time.get(Calendar.MINUTE);

        duration_Minute=""+duration_minute;

        return duration_Minute;
    }

    public static String get_duration_second(long duration_milliseconds){
        String duration_Second="";
        String duration_second_str;
        Calendar duration_time = Calendar.getInstance();
        duration_time.setTimeInMillis(duration_milliseconds);
        int duration_second = duration_time.get(Calendar.SECOND);
        duration_second_str=String.format("%02d",duration_second);
        duration_Second=""+duration_second_str;

        return duration_Second;
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
        startTime=""+start_year+"年"+String.format("%02d",start_month)+"月"+String.format("%02d",start_day)+"日 週"+start_week+" "+String.format("%02d",start_hour)+"時 "+String.format("%02d",start_minute)+"分鐘 "+String.format("%02d",start_second)+"秒 ";

        return startTime;
    }
    public static String get_start_time_year(long start_milliseconds){
        String StartTimeYear="";
        Calendar start_calendar=Calendar.getInstance();
        start_calendar.setTimeInMillis(start_milliseconds);
        int start_year = start_calendar.get(Calendar.YEAR);
        StartTimeYear=String.format("%02d",start_year);

        return StartTimeYear;
    }

    public static String get_start_time_month(long start_milliseconds){
        String StartTimeMonth="";
        Calendar start_calendar=Calendar.getInstance();
        start_calendar.setTimeInMillis(start_milliseconds);
        int start_month = start_calendar.get(Calendar.MONTH)+1;
        StartTimeMonth=String.format("%02d",start_month);

        return StartTimeMonth;
    }

    public static String get_start_time_day(long start_milliseconds){
        String StartTimeDay="";
        Calendar start_calendar=Calendar.getInstance();
        start_calendar.setTimeInMillis(start_milliseconds);
        int start_day = start_calendar.get(Calendar.DAY_OF_MONTH);
        StartTimeDay=String.format("%02d",start_day);

        return StartTimeDay;
    }

    public static String get_start_time_hour(long start_milliseconds){
        String StartTimeHour="";
        Calendar start_calendar=Calendar.getInstance();
        start_calendar.setTimeInMillis(start_milliseconds);
        int start_hour = start_calendar.get(Calendar.HOUR_OF_DAY);
        StartTimeHour=String.format("%02d",start_hour);

        return StartTimeHour;
    }

    public static String get_start_time_minute(long start_milliseconds){
        String StartTimeMinute="";
        Calendar start_calendar=Calendar.getInstance();
        start_calendar.setTimeInMillis(start_milliseconds);
        int start_minute = start_calendar.get(Calendar.MINUTE);
        StartTimeMinute=String.format("%02d",start_minute);

        return StartTimeMinute;
    }

    public static String get_start_time_second(long start_milliseconds){
        String StartTimeSecond="";
        Calendar start_calendar=Calendar.getInstance();
        start_calendar.setTimeInMillis(start_milliseconds);
        int start_second = start_calendar.get(Calendar.MINUTE);
        StartTimeSecond=String.format("%02d",start_second);

        return StartTimeSecond;
    }

    public static String get_start_week(long start_milliseconds){
        String start_week=Week.getWeek(start_milliseconds);

        return start_week;
    }

    public static String get_finish_week(long start_milliseconds){
        String end_week=Week.getWeek(start_milliseconds);

        return end_week;
    }

    public static String get_finish_time_year(long end_milliseconds){
        String EndTimeYear="";
        Calendar end_calendar=Calendar.getInstance();
        end_calendar.setTimeInMillis(end_milliseconds);
        int end_year = end_calendar.get(Calendar.YEAR);
        EndTimeYear=""+end_year;

        return EndTimeYear;
    }

    public static String get_finish_time_month(long end_milliseconds){
        String EndTimeMonth="";
        Calendar end_calendar=Calendar.getInstance();
        end_calendar.setTimeInMillis(end_milliseconds);
        int end_month = end_calendar.get(Calendar.MONTH)+1;
        EndTimeMonth=""+end_month;

        return EndTimeMonth;
    }

    public static String get_finish_time_day(long end_milliseconds){
        String EndTimeDay="";
        Calendar end_calendar=Calendar.getInstance();
        end_calendar.setTimeInMillis(end_milliseconds);
        int end_day = end_calendar.get(Calendar.DAY_OF_MONTH);
        EndTimeDay=""+end_day;

        return EndTimeDay;
    }

    public static String get_finish_time_hour(long end_milliseconds){
        String EndTimeHour="";
        Calendar end_calendar=Calendar.getInstance();
        end_calendar.setTimeInMillis(end_milliseconds);
        int end_hour = end_calendar.get(Calendar.HOUR_OF_DAY);
        EndTimeHour=""+end_hour;

        return EndTimeHour;
    }

    public static String get_finish_time_minute(long end_milliseconds){
        String EndTimeMinute="";
        Calendar end_calendar=Calendar.getInstance();
        end_calendar.setTimeInMillis(end_milliseconds);
        int end_minute = end_calendar.get(Calendar.MINUTE);
        EndTimeMinute=""+end_minute;

        return EndTimeMinute;
    }

    public static String get_finish_time_second(long end_milliseconds){
        String EndTimeSecond="";
        Calendar end_calendar=Calendar.getInstance();
        end_calendar.setTimeInMillis(end_milliseconds);
        int end_second = end_calendar.get(Calendar.MINUTE);
        EndTimeSecond=""+end_second;

        return EndTimeSecond;
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
    public static String getToDate2(long CalendarMillisecond){
        String Todate ="";
        Calendar date=Calendar.getInstance();
        date.setTimeInMillis(CalendarMillisecond);
        int y=date.get(Calendar.YEAR);
        int m=date.get(Calendar.MONTH)+1;
        int d=date.get(Calendar.DAY_OF_MONTH);
        String DateWeek=Week.getWeek(CalendarMillisecond);
        Todate=""+y+"-"+m+"-"+d+"" ;
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

    public static String getYoga_data(long yoga_data){
        String yogaData="";
        Calendar yoga_data2=Calendar.getInstance();
        yoga_data2.setTimeInMillis(yoga_data);
        int yogadata =yoga_data2.get(Calendar.DAY_OF_MONTH);

        if(yogadata==1||yogadata==7||yogadata==13||yogadata==19||yogadata==25){
            yogaData="10";
        }
        if(yogadata==2||yogadata==8||yogadata==14||yogadata==20||yogadata==26){
            yogaData="12";
        }
        if(yogadata==3||yogadata==9||yogadata==15||yogadata==21||yogadata==27){
            yogaData="14";
        }
        if(yogadata==4||yogadata==10||yogadata==16||yogadata==22||yogadata==28){
            yogaData="16";
        }
        if(yogadata==5||yogadata==11||yogadata==17||yogadata==23||yogadata==29){
            yogaData="18";
        }
        if(yogadata==6||yogadata==12||yogadata==18||yogadata==24||yogadata==30||yogadata==31){
            yogaData="20";
        }
        return yogaData;
    }
    public static String getSquats_data(long squats_data){
        String squatsData="";
        Calendar squats_data2=Calendar.getInstance();
        squats_data2.setTimeInMillis(squats_data);
        int squatsdata =squats_data2.get(Calendar.DAY_OF_MONTH);

        if(squatsdata==1||squatsdata==7||squatsdata==13||squatsdata==19||squatsdata==25){
            squatsData="50";
        }
        if(squatsdata==2||squatsdata==8||squatsdata==14||squatsdata==20||squatsdata==26){
            squatsData="60";
        }
        if(squatsdata==3||squatsdata==9||squatsdata==15||squatsdata==21||squatsdata==27){
            squatsData="70";
        }
        if(squatsdata==4||squatsdata==10||squatsdata==16||squatsdata==22||squatsdata==28){
            squatsData="80";
        }
        if(squatsdata==5||squatsdata==11||squatsdata==17||squatsdata==23||squatsdata==29){
            squatsData="90";
        }
        if(squatsdata==6||squatsdata==12||squatsdata==18||squatsdata==24||squatsdata==30||squatsdata==31){
            squatsData="100";
        }
        return squatsData;
    }

    public static String getCrunches_data(long crunches_data){
        String crunchesData="";
        Calendar crunches_data2=Calendar.getInstance();
        crunches_data2.setTimeInMillis(crunches_data);
        int crunchesdata =crunches_data2.get(Calendar.DAY_OF_MONTH);

        if(crunchesdata==1||crunchesdata==7||crunchesdata==13||crunchesdata==19||crunchesdata==25){
            crunchesData="50";
        }
        if(crunchesdata==2||crunchesdata==8||crunchesdata==14||crunchesdata==20||crunchesdata==26){
            crunchesData="60";
        }
        if(crunchesdata==3||crunchesdata==9||crunchesdata==15||crunchesdata==21||crunchesdata==27){
            crunchesData="70";
        }
        if(crunchesdata==4||crunchesdata==10||crunchesdata==16||crunchesdata==22||crunchesdata==28){
            crunchesData="80";
        }
        if(crunchesdata==5||crunchesdata==11||crunchesdata==17||crunchesdata==23||crunchesdata==29){
            crunchesData="90";
        }
        if(crunchesdata==6||crunchesdata==12||crunchesdata==18||crunchesdata==24||crunchesdata==30||crunchesdata==31){
            crunchesData="100";
        }
        return crunchesData;
    }

    public static long change_minuteToLong(String minute){

        int minuteLong=Integer.parseInt(minute);
        long minutelong=minuteLong*60000;
        return minutelong;
    }

    public static String formatCalendar(long milliseconds) {
        Date date = new Date(milliseconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formaterCalender);
        return simpleDateFormat.format(date);
    }

    public static String get_post_time(long post_milliseconds){
        String postTime ="";
        Calendar post_calendar=Calendar.getInstance();
        post_calendar.setTimeInMillis(post_milliseconds);
        int post_year = post_calendar.get(Calendar.YEAR);
        int post_month= post_calendar.get(Calendar.MONTH)+1;
        int post_day =post_calendar.get(Calendar.DAY_OF_MONTH);
        int post_hour = post_calendar.get(Calendar.HOUR_OF_DAY);
        int post_minute=post_calendar.get(Calendar.MINUTE);
        int post_second=post_calendar.get(Calendar.SECOND);
        String post_week=Week.getWeek(post_milliseconds);
        postTime=""+post_year+"年"+post_month+"月"+post_day+"日 週"+post_week+" "+post_hour+"時 "+post_minute+"分鐘 "+post_second+"秒 ";

        return postTime;
    }







}
