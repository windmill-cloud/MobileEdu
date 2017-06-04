
package edu.ucsb.cs.cs190i.xuanwang.geofencing.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceDetail {

    @SerializedName("html_attributions")
    @Expose
    private List<Object> htmlAttributions = null;
    @SerializedName("result")
    @Expose
    private DetailResult result;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<Object> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public DetailResult getResult() {
        return result;
    }

    public void setResult(DetailResult result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
