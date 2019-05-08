package project.gallery;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagegaleryapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.view.activities.PictureListActivity;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.ViewHolder> {

  private final Context context;
  private final List<Gallery> galleryList;
  ImageLoader imageLoader;


  public GalleryListAdapter(Context context, List<Gallery> galleryList) {
    this.context = context;
    this.galleryList = galleryList;

    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
      .build();
    // Initialize ImageLoader with configuration.

    ImageLoader.getInstance().init(config);
     imageLoader = ImageLoader.getInstance(); // Get singleton instance
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return new GalleryListAdapter.ViewHolder(LayoutInflater.from(this.context)
      .inflate(R.layout.adapter_gallery_list, viewGroup, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final Gallery gallery = galleryList.get(position);

    holder.txtTitle.setText(" " + gallery.getTitle());

//    RequestOptions requestOptions = new RequestOptions();
//    requestOptions = requestOptions.transforms(new CenterInside(),
//      new RoundedCorners((int) context.getResources().getDimension(R.dimen.view_xxlarg)));
//    GlideApp.with(context)
//      .load(gallery.getThumbPic())
//      .placeholder(context.getResources()
//        .getDrawable(R.drawable.error_background))
////      .apply(requestOptions)
//      .into(holder.imgGallery);


// Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
//	which implements ImageAware interface)
    DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).build();
    imageLoader.displayImage(gallery.getThumbPic(), holder.imgGallery,options);

    holder.llay_adapter_gallery_list.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        EventBus.getDefault().post(new GalleryListEventListener(gallery));
        Intent intent = new Intent(context, PictureListActivity.class);
        intent.putExtra("Gallery", gallery);
        context.startActivity(intent);
      }
    });

  }

  @Override
  public int getItemCount() {
    return galleryList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgGallery)
    ImageView imgGallery;
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.llay_adapter_gallery_list)
    CardView llay_adapter_gallery_list;
//    FrameLayout llay_adapter_gallery_list;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
