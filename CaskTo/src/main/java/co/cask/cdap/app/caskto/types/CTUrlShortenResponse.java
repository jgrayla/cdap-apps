package co.cask.cdap.app.caskto.types;


public class CTUrlShortenResponse {
  public String shortname;
  public boolean error;
  public String msg;
  public boolean existed;
  public CTUrlShortenResponse(String shortname, String successMsg, boolean existed) {
    this.shortname = shortname;
    this.error = false;
    this.msg = successMsg;
    this.existed = existed;
  }
  public CTUrlShortenResponse(String errorMsg) {
    this.error = true;
    this.msg = errorMsg;
    this.existed = false;
  }
}