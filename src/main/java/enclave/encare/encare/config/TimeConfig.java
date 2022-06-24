package enclave.encare.encare.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConfig {
    public static String getTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static Date getDate(String string){
        Date date = new Date();
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(string);
        } catch (Exception e){
            return null;
        }
        return date;
    }
}
