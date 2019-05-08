package project.view.others;

import android.app.Application;

import com.example.imagegaleryapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//      .setDefaultFontPath("iransans.ttf")
      .setDefaultFontPath("fonts/shn.ttf")
      .setFontAttrId(R.attr.fontPath)
      .build()
    );
  }
}
