package co.cask.cdap.app.caskto.types;


public class CTUserCreateResponse {
  public CTUser user;
  public boolean success;
  public String msg;
  public CTUserCreateResponse(CTUser user, boolean success, String msg) {
    this.user = user;
    this.success = success;
    this.msg = msg;
  }
}