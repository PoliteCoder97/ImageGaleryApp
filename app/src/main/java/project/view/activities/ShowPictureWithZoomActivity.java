package project.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagegaleryapp.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowPictureWithZoomActivity extends AppCompatActivity {
  @BindView(R.id.imgPhotoView)
  PhotoView imgPhotoView;
  @BindView(R.id.imgLeft)
  ImageView imgLeft;
  @BindView(R.id.txtTitle)
  TextView txtTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_picture_with_zoom);
    ButterKnife.bind(this);

    initwidgets();

    String picLink = "";
    picLink = getIntent().getStringExtra("picLink");

    ImageLoader imageLoader;
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ShowPictureWithZoomActivity.this)
      .build();
    // Initialize ImageLoader with configuration.
    ImageLoader.getInstance().init(config);
    imageLoader = ImageLoader.getInstance(); // Get singleton instance


    // Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
//	which implements ImageAware interface)
    DisplayImageOptions options = new DisplayImageOptions.Builder()
      .showImageForEmptyUri(R.drawable.layout_background) // resource or drawable
      .showImageOnFail(R.drawable.error_background) // resource or drawable
      .cacheInMemory(true).build();
    imageLoader.displayImage(picLink, imgPhotoView,options);

//    GlideApp.with(this)
//      .load(picLink)
//      .placeholder(getResources()
//        .getDrawable(R.drawable.error_background))
//      .into(imgPhotoView);

  }

  private void initwidgets() {
    imgLeft.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_back));
  }

  @OnClick(R.id.imgLeft)
  void imgLeftClicked(View v) {
    this.onBackPressed();
  }
}
