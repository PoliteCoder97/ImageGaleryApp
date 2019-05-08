package project.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imagegaleryapp.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import project.classes.Consts;
import project.gallery.Gallery;
import project.gallery.GalleryListAdapter;
import project.picture.Pic;
import project.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GalleryListActivity extends AppCompatActivity {

  //widgets
  @BindView(R.id.rclvGallery)
  XRecyclerView rclvGallery;
  @BindView(R.id.app_lay_loading)
  LinearLayout app_lay_loading;
  @BindView(R.id.txtTitle)
  TextView txtTitle;
  @BindView(R.id.imgLeft)
  ImageView imgLeft;
  @BindView(R.id.imgRight)
  ImageView imgRight;

  //filds
  GalleryListAdapter galleryListAdapter;
  boolean waiting = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gallery_list);
    ButterKnife.bind(this);
    initWidgets();
    getDataFromNet();
  }//onCreate

  @Override
  public void onStart() {
    super.onStart();
//    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
//    EventBus.getDefault().unregister(this);
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    // any time,when you finish your activity or fragment,call this below
    if (rclvGallery != null) {
      rclvGallery.destroy(); // this will totally release XR's memory
      rclvGallery = null;
    }
    super.onDestroy();
  }

  //************************************** INITIAL FILDS ***********************************
  private void initWidgets() {
    //set toolbar fields ui params
//    txtTitle.setGravity(Gravity.RIGHT);
//    imgRight.setVisibility(View.GONE);
//    txtTitle.setText("آلبوم ها");
    imgLeft.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_black_24dp));
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  //************************************** EXECUTE PROGRAM ******************************
  @BindView(R.id.app_error)
  LinearLayout app_error;
  @BindView(R.id.btnRetry)
  Button btnRetry;
  @BindView(R.id.txtError)
  TextView txtError;

  private void getDataFromNet() {
    app_lay_loading.setVisibility(View.VISIBLE);
    app_error.setVisibility(View.GONE);

    if (waiting)
      return;

    waiting = true;

    Ion.with(GalleryListActivity.this)
      .load(Utils.checkSdkVersionAndSetBAseUri(Consts.GET_GALLERY_URI))//get the image of galleries
      .asString()
      .setCallback(new FutureCallback<String>() {
        @Override
        public void onCompleted(Exception e, String result) {
          waiting = false;
          app_lay_loading.setVisibility(View.GONE);
          if (e != null) {
            e.printStackTrace();
            app_error.setVisibility(View.VISIBLE);
            txtError.setText("خطایی در دریافت اطلاعات از سرور لطفا مجددا تلاش کنید");
            btnRetry.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                getDataFromNet();
              }
            });
            return;
          }
          try {
            JSONObject jsonObject = new JSONObject(result);
            Log.i("RESULT", "result: " + jsonObject.toString(4));
            if (jsonObject.getBoolean("error")) {
              Toast.makeText(GalleryListActivity.this,
                " " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
              return;
            }//end if

            List<Gallery> galleries = new ArrayList<>();
            galleries.clear();
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jo = jsonArray.getJSONObject(i);
              Gallery gallery = new Gallery();
              gallery.setId(jo.getInt("id"));
              gallery.setTitle(jo.getString("title"));
              gallery.setThumbPic(jo.getString("thumbPic"));

              //create a array from pics
              List<Pic> picList = new ArrayList<>();
              picList.clear();
              String pics = jo.getString("pics");
              JSONArray picJA = new JSONArray(pics);
              for (int j = 0; j < picJA.length(); j++) {
                Pic pic = new Pic();
                pic.setPicLink(picJA.get(j).toString());
                picList.add(pic);
              }//end for
              gallery.setPics(picList);

              galleries.add(gallery);
            }
            initXRecyclerView(galleries);
          } catch (JSONException e1) {
            e1.printStackTrace();
          }
        }
      });

  }

  private void initXRecyclerView(List<Gallery> galleryList) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(GalleryListActivity.this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rclvGallery.setLayoutManager(layoutManager);

    rclvGallery.setPullRefreshEnabled(false);
    rclvGallery.setLoadingMoreEnabled(false);

    GalleryListAdapter adapter = new GalleryListAdapter(this, galleryList);
    rclvGallery.setAdapter(adapter);
    rclvGallery.refresh();
  }

  // This method will be called when a MessageEvent is posted (in the UI thread)
//  @Subscribe(threadMode = ThreadMode.MAIN)
//  public void onGalleryEvent(GalleryListEventListener event) {
//    Toast.makeText(this, "id: " + event.getId(), Toast.LENGTH_SHORT).show();
//    Intent intent = new Intent(GalleryListActivity.this, PictureListActivity.class);
//    JSONObject jsonObject = new JSONObject((Map) event.getPics());
//    intent.putExtra("title", event.getTitle());
//    intent.putExtra("pics", jsonObject.toString());
//    this.startActivity(intent);
//  }

  //************************************** CLICK EVENTS ON WIDGETS *********************
  @OnClick(R.id.imgLeft)
  void imgLeftClicked(View v) {
    Toast.makeText(this, "menu clicked", Toast.LENGTH_SHORT).show();
  }

  @OnClick(R.id.imgRight)
  void imgRightClicked(View v) {

  }
}
