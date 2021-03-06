package project.picture;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.imagegaleryapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureListAdapter extends RecyclerView.Adapter<PictureListAdapter.ViewHolder> {
  private final Context context;
  private final List<Pic> picList;
  ImageLoader imageLoader;

  public PictureListAdapter(Context context, List<Pic> picList) {
    this.context = context;
    this.picList = picList;

    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
      .build();
    // Initialize ImageLoader with configuration.
    ImageLoader.getInstance().init(config);
    imageLoader = ImageLoader.getInstance(); // Get singleton instance
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_pic_list, null, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
    final Pic pic = picList.get(i);

//    RequestOptions requestOptions = new RequestOptions();
//    requestOptions = requestOptions.transforms(new CenterInside(),
//      new RoundedCorners((int) context.getResources().getDimension(R.dimen.view_xxlarg)));
//    GlideApp.with(context)
//      .load(pic.getPicLink())
//      .placeholder(context.getResources()
//        .getDrawable(R.drawable.error_background))
////      .apply(requestOptions)
//      .into(holder.imgPic);

    // Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
//	which implements ImageAware interface)
    DisplayImageOptions options = new DisplayImageOptions.Builder()
      .showImageForEmptyUri(R.drawable.layout_background) // resource or drawable
      .showImageOnFail(R.drawable.error_background) // resource or drawable
      .cacheInMemory(true).build();
    imageLoader.displayImage(pic.getPicLink(), holder.imgPic,options);

    holder.llay_adapter_pic_list.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        EventBus.getDefault().post(new PictureListEventListener(pic.getPicLink()));
      }
    });
  }

  @Override
  public int getItemCount() {
    return picList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgPic)
    ImageView imgPic;
    @BindView(R.id.llay_adapter_pic_list)
    CardView llay_adapter_pic_list;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
