package co.cask.cdap.app.caskto.types;

import co.cask.cdap.app.caskto.CaskTo;

/**
 * A cask.to event.  All types of events are represented by this.
 */
public class CTEvent {

  public CTEventType type;

  public CTEventSource source;

  public CTUser user;

  public CTEventInfo info;

  public CTEvent(CTEventType type, CTEventSource source, CTEventInfo info) {
    this.type = type;
    this.source = source;
    this.info = info;
  }

  public static enum CTEventType {
    REDIRECT,
    CREATE_NEW,
    CREATE_DUPE,
    LIST_URLS,
  }

  public static enum CTEventSource {
    PROCEDURE,
    WEBLOG,
  }

  @Override
  public String toString() {
    return CaskTo.objectToJsonString(this);
  }
}
