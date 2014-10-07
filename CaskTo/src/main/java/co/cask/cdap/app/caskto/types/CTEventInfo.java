package co.cask.cdap.app.caskto.types;

public class CTEventInfo {

  public long timestamp;

  public String srcIP;

  public CTEventInfo(long timestamp, String srcIP) {
    this.timestamp = timestamp;
    this.srcIP = srcIP;
  }
}
