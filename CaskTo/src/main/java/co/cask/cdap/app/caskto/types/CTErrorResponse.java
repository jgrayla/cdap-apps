package co.cask.cdap.app.caskto.types;

public class CTErrorResponse {

  public boolean error;
  public String msg;

  public CTErrorResponse(String msg) {
    this.error = true;
    this.msg = msg;
  }
}
