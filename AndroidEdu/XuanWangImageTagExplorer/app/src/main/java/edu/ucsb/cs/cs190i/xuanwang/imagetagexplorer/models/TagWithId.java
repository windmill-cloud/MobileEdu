package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models;

/**
 * Created by xuanwang on 5/7/17.
 */

public class TagWithId {
  private String id;
  private String tag;

  public TagWithId(String id, String tag){
    this.id = id;
    this.tag = tag;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


}
