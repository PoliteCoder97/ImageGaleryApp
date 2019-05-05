package project.gallery;

import java.util.ArrayList;
import java.util.List;

import project.picture.Pic;

public class Gallery {
  private int id;
  private String title;
  private String thumbPic;
  private List<Pic> pics;
  public Gallery() {
    pics = new ArrayList<>();
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public String getThumbPic() {
    return thumbPic;
  }
  public void setThumbPic(String thumbPic) {
    this.thumbPic = thumbPic;
  }
  public List<Pic> getPics() {
    return pics;
  }
  public void setPics(List<Pic> pics) {
    this.pics = pics;
  }
}
