package cn.judgchen.cisp.utils;

import java.time.LocalDateTime;
import java.util.Random;

public class DifferenceTime {

    public static long difference(LocalDateTime localDateTime){

        long currentTime = localDateTime.getMonthValue()*30*24*3600+localDateTime.getDayOfYear()*24*3600+localDateTime.getHour()*3600+localDateTime.getMinute()*60+localDateTime.getSecond();

        return currentTime;
    }

    public static String getOrder(){

        Random random = new Random();
        long randomNum = random.nextInt(1000);
        String orderNum = DateUtils.getDateSequence()+randomNum;

        return orderNum;
    }
}
