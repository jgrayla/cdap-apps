package co.cask.cdap.app.caskto.types;


public class CTUrlRedirectResponse {
  public String url;
  public boolean error;
  public String msg;
  public CTUrlRedirectResponse(String url, String successMsg) {
    this.url = url;
    this.error = false;
    this.msg = successMsg;
  }
  public CTUrlRedirectResponse(String errorMsg) {
    this.error = true;
    this.msg = errorMsg;
  }
}
