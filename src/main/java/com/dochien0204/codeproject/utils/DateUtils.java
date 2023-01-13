package com.dochien0204.codeproject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
  public static String  toDate(Long time) {
    SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy HH:mm");
    Date resultDate = new Date(time);
    return sdf.format(resultDate);
  }
}
