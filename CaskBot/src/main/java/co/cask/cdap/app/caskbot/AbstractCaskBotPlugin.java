package co.cask.cdap.app.caskbot;

/**
 * Base {@link CaskBotPlugin} that provide no-ops for callbacks. 
 */
public abstract class AbstractCaskBotPlugin implements CaskBotPlugin {

  @Override
  public void onConnected(String nick) {}

  @Override
  public void onRoomMessage(String room, String nick, String msg) {}

  @Override
  public void onPrivateMessage(String nick, String msg) {}

  @Override
  public void onUserJoinRoom(String room, String nick) {}

  @Override
  public void onUserLeaveRoom(String room, String nick) {}

  @Override
  public void onRoomOp(String nick) {}

  @Override
  public void onRoomDeop(String nick) {}

  @Override
  public void onRoomKick(String nick, String msg) {}

  @Override
  public void onRoomTopicChange(String room, String nick, String topic) {}

}
