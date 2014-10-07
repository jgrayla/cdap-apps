package co.cask.cdap.app.caskto.types;

public class CTStatsResponse {
  public long shortened;
  public long redirected;
  public CTStatsResponse(long shortened, long redirected) {
    this.shortened = shortened;
    this.redirected = redirected;
  }
}
