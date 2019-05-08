package project.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imagegaleryapp.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.gallery.Gallery;
import project.picture.Pic;
import project.picture.PictureListAdapter;
import project.picture.PictureListEventListener;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PictureListActivity extends AppCompatActivity {

  //widgets
  @BindView(R.id.imgLeft)
  ImageView imgLeft;
  @BindView(R.id.txtTitle)
  TextView txtTitle;
  @BindView(R.id.rclvPics)
  XRecyclerView rclvPics;
  @BindView(R.id.app_lay_loading)
  LinearLayout app_lay_loading;
  @BindView(R.id.app_error)
  LinearLayout app_error;

  //filds
  Gallery gallery;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_picture_list);
    ButterKnife.bind(this);

    gallery = new Gallery();
    // To retrieve object in second Activity
    gallery = (Gallery) getIntent().getSerializableExtra("Gallery");

    initWidgets();

    if (gallery.getPics() != null)
      initXRecyclerView(gallery);

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

  @Override
  protected void onDestroy() {
    // any time,when you finish your activity or fragment,call this below
    if (rclvPics != null) {
      rclvPics.destroy(); // this will totally release XR's memory
      rclvPics = null;
    }
    super.onDestroy();
  }

  //************************************** INITIAL FILDS ***********************************
  private void initWidgets() {
    //set toolbar fields ui params
    imgLeft.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_arrow_back));
    if (gallery != null)
      txtTitle.setText("" + gallery.getTitle());
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  //************************************** EXECUTE PROGRAM ******************************
  private void initXRecyclerView(Gallery gallery) {
    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    GridLayoutManager layoutManager = new GridLayoutManager(PictureListActivity.this, 2);
    rclvPics.setLayoutManager(manager);
//    rclvPics.setLayoutManager(layoutManager);

    rclvPics.setPullRefreshEnabled(false);
    rclvPics.setLoadingMoreEnabled(false);

    for (int i = 0; i < gallery.getPics().size(); i++) {
      Pic pic = gallery.getPics().get(i);
      Log.i("PICS", "pic#" + i + ": " + pic.getPicLink());
    }

    PictureListAdapter adapter = new PictureListAdapter(PictureListActivity.this, gallery.getPics());
    rclvPics.setAdapter(adapter);
    rclvPics.refresh();
  }

  // This method will be called when a MessageEvent is posted (in the UI thread)
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onGalleryEvent(PictureListEventListener event) {
    Intent intent = new Intent(PictureListActivity.this, ShowPictureWithZoomActivity.class);
    intent.putExtra("picLink", event.getPicLink());
    this.startActivity(intent);
  }

  //************************************** CLICK EVENTS ON WIDGETS *********************
  @OnClick(R.id.imgLeft)
  void imgLeftClicked(View v) {
    this.onBackPressed();
  }
}
