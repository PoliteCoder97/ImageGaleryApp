package project.utils;

import android.os.Build;

public class Utils {
  public static String checkSdkVersionAndSetBAseUri(String uri) {
    String base = "";
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
      base = "https://politecoder97.ir/";
    else
      base = "http://politecoder97.ir/";

    return base + uri;
  }

}
