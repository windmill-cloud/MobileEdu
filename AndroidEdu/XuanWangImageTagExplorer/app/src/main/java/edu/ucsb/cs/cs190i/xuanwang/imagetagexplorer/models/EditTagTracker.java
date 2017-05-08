package edu.ucsb.cs.cs190i.xuanwang.imagetagexplorer.models;

/**
 * Created by xuanwang on 5/7/17.
 */

public class EditTagTracker {
  public static final int NEW = 0;
  public static final int OLD = 1;
  public static final int DELETE = 2;

  private String id = "";
  private int type = 1;

  public EditTagTracker(String id, int type){
    this.id = id;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
