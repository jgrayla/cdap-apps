package co.cask.cdap.app.caskbot;

import java.util.List;

public abstract class AbstractCaskBot implements CaskBot {

  protected abstract List<CaskBotPlugin> getPlugins();

  @Override
  public void onConnected(String nick) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onConnected(nick);
    }
  }

  @Override
  public void onRoomMessage(String room, String nick, String msg) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onRoomMessage(room, nick, msg);
    }
  }

  @Override
  public void onPrivateMessage(String nick, String msg) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onPrivateMessage(nick, msg);
    }
  }

  @Override
  public void onUserJoinRoom(String room, String nick) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onUserJoinRoom(room, nick);
    }
  }

  @Override
  public void onUserLeaveRoom(String room, String nick) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onUserLeaveRoom(room, nick);
    }
  }

  @Override
  public void onRoomOp(String nick) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onRoomOp(nick);
    }
  }

  @Override
  public void onRoomDeop(String nick) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onRoomDeop(nick);
    }
  }

  @Override
  public void onRoomKick(String nick, String msg) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onRoomKick(nick, msg);
    }
  }

  @Override
  public void onRoomTopicChange(String room, String nick, String topic) {
    for (CaskBotPlugin plugin : getPlugins()) {
      plugin.onRoomTopicChange(room, nick, topic);
    }
  }

}
