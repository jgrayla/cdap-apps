package co.cask.cdap.app.caskto.types;


public class CTUrlShortenRequest {
  public long userid;
  public String url;
  public CTUrlShortenRequest(long userid, String url) {
    this.userid = userid;
    this.url = url;
  }
}
