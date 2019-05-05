package project.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.imagegaleryapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import project.gallery.GalleryListEventListener;

public class GalleryListActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gallery_list);


  }

  // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onGalleryEvent(GalleryListEventListener event) {
    Toast.makeText(this, event.getTitle(), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    EventBus.getDefault().unregister(this);
    super.onStop();
  }
}
