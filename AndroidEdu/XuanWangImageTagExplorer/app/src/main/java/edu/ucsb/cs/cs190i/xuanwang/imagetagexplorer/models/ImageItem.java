package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models;

/**
 * Created by xuanwang on 5/7/17.
 */

public class ImageItem {
  String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  String path;

  public ImageItem(String id, String path){
    this.id = id;
    this.path = path;
  }
}
