package com.haocg.ourduan1nhom8.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public String dateNow(String regex){
        SimpleDateFormat ft = new SimpleDateFormat(regex);
        return ft.format(new Date()).toString();
    }
}
