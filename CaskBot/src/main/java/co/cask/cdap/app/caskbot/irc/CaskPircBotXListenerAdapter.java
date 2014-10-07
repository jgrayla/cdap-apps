package co.cask.cdap.app.caskbot.irc;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.TopicEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaskPircBotXListenerAdapter extends ListenerAdapter<PircBotX> {
  public static Logger LOG = LoggerFactory.getLogger(CaskPircBotXListenerAdapter.class);

  private CaskBotPircBotX bot;

  public void initialize(CaskBotPircBotX bot) {
    this.bot = bot;
  }

  public void onConnect(ConnectEvent<PircBotX> event) {
    LOG.info("Connected: " + event.toString());
  }

  @Override
  public void onMessage(MessageEvent<PircBotX> event) throws Exception {
    super.onMessage(event);
    String room = event.getChannel().getName();
    String msg = event.getMessage();
    String nick = event.getUser().getNick();
    LOG.debug("Received room message: [" + room + "] (" + nick + ") " + msg);
    System.err.println("Received message: [" + room + "] (" + nick + ") " + msg);
    this.bot.onRoomMessage(room, nick, msg);
  }

  @Override
  public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
    super.onPrivateMessage(event);
    String msg = event.getMessage();
    String nick = event.getUser().getNick();
    LOG.debug("Received private message: (" + nick + ") " + msg);
    System.err.println("Received private message: (" + nick + ") " + msg);
    this.bot.onPrivateMessage(nick, msg);
  }

  @Override
  public void onJoin(JoinEvent<PircBotX> event) throws Exception {
    super.onJoin(event);
    String room = event.getChannel().getName();
    String nick = event.getUser().getNick();
    LOG.debug("User joined channel: [" + room + "] (" + nick + ")");
    System.err.println("User joined channel: [" + room + "] (" + nick + ")");
    this.bot.onUserJoinRoom(room, nick);
  }

  @Override
  public void onPart(PartEvent<PircBotX> event) throws Exception {
    super.onPart(event);
    String room = event.getChannel().getName();
    String nick = event.getUser().getNick();
    LOG.debug("User parted channel: [" + room + "] (" + nick + ")");
    System.err.println("User parted channel: [" + room + "] (" + nick + ")");
    this.bot.onUserLeaveRoom(room, nick);
  }
  
  @Override
  public void onTopic(TopicEvent<PircBotX> event) throws Exception {
    super.onTopic(event);
    String room = event.getChannel().getName();
    String nick = event.getUser().getNick();
    String topic = event.getTopic();
    LOG.debug("User changed channel topic: [" + room + "] (" + nick + ")");
    System.err.println("User changed channel topic: [" + room + "] (" + nick + ")");
    this.bot.onRoomTopicChange(room, nick, topic);
  }
}
