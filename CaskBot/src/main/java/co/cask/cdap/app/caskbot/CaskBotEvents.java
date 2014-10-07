package co.cask.cdap.app.caskbot;

public interface CaskBotEvents {

  public void sendRoomMessage(String room, String msg);

  public void sendPrivateMessage(String nick, String msg);

  public void opUser(String room, String nick);
  
  public void deopUser(String room, String nick);

  public void voiceUser(String room, String nick);
  
  public void devoiceUser(String room, String nick);
  
  public void kickUser(String room, String nick, String msg);

  public void setTopic(String room, String topic);

}
