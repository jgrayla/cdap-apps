package co.cask.cdap.app.caskto.types;

import java.util.List;


public class CTUrlListResponse {
  public List<MappedUrl> urls;
  public boolean error;
  public String msg;
  public CTUrlListResponse(List<MappedUrl> urls) {
    this.urls = urls;
    this.error = false;
    this.msg = "Success";
  }
  public CTUrlListResponse(String errorMsg) {
    this.error = true;
    this.msg = errorMsg;
  }

  public static class MappedUrl {
    public String url;
    public String shortname;
    public MappedUrl(String url, String shortname) {
      this.url = url;
      this.shortname = shortname;
    }
  }
}