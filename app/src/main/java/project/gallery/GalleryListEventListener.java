package project.gallery;

import java.util.List;

import project.picture.Pic;

public class GalleryListEventListener {
  private int id;
  private String title;
  private String thumbPic;
  private List<Pic> pics;

  public GalleryListEventListener(Gallery gallery) {
    this.id = gallery.getId();
    this.title = gallery.getTitle();
    this.thumbPic = gallery.getThumbPic();
    this.pics = gallery.getPics();
  }
  public int getId() {
    return id;
  }
  public String getTitle() {
    return title;
  }
  public String getThumbPic() {
    return thumbPic;
  }
  public List<Pic> getPics() {
    return pics;
  }
}
