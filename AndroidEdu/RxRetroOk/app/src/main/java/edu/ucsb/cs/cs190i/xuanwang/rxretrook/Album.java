/*
 *  Copyright (c) 2017 - present, Xuan Wang
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree.
 *
 */

package edu.ucsb.cs.cs190i.xuanwang.rxretrook;

/**
 * Created by xuanwang on 4/29/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Album {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("artist")
  @Expose
  private String artist;
  @SerializedName("url")
  @Expose
  private String url;
  @SerializedName("image")
  @Expose
  private String image;
  @SerializedName("thumbnail_image")
  @Expose
  private String thumbnailImage;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getThumbnailImage() {
    return thumbnailImage;
  }

  public void setThumbnailImage(String thumbnailImage) {
    this.thumbnailImage = thumbnailImage;
  }

}