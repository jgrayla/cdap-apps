package co.cask.cdap.app.caskbot;

public interface CaskBotCallbacks {

  public void onConnected(String nick);

  public void onRoomMessage(String room, String nick, String msg);

  public void onPrivateMessage(String nick, String msg);

  public void onUserJoinRoom(String room, String nick);

  public void onUserLeaveRoom(String room, String nick);

  public void onRoomOp(String nick);

  public void onRoomDeop(String nick);
  
  public void onRoomKick(String nick, String msg);

  public void onRoomTopicChange(String room, String nick, String topic);

}
