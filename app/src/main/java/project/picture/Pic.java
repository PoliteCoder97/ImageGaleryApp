package project.picture;

import java.io.Serializable;

public class Pic implements Serializable {
  private String picLink;
  public Pic() {
  }
  public String getPicLink() {
    return picLink;
  }
  public void setPicLink(String picLink) {
    this.picLink = picLink;
  }
}
