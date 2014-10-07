/**
 * 
 */
package co.cask.cdap.app.caskbot.irc;

import java.io.IOException;
import java.util.List;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.cask.cdap.app.caskbot.AbstractCaskBot;
import co.cask.cdap.app.caskbot.CaskBotPlugin;
import co.cask.cdap.app.caskbot.data.CaskBotConfig;
import co.cask.cdap.app.caskbot.data.CaskBotMetrics;

/**
 * 
 */
public class CaskBotPircBotX extends AbstractCaskBot {
  public static Logger LOG = LoggerFactory.getLogger(CaskBotPircBotX.class);

  private final String nick;
  private final String pass;
  private final String host;
  private final int port;

  private CaskBotConfig config;
  private CaskBotMetrics metrics;

  private List<CaskBotPlugin> plugins;

  private PircBotX bot;

  public CaskBotPircBotX(String nick, String pass, String host, int port) {
    this.nick = nick;
    this.pass = pass;
    this.host = host;
    this.port = port;
  }

  @Override
  public void initialize(CaskBotConfig config, CaskBotMetrics metrics,
      List<CaskBotPlugin> plugins) {
    this.config = config;
    this.metrics = metrics;
    this.plugins = plugins;
    for (CaskBotPlugin plugin : plugins) {
      plugin.initialize(this, this.config, this.metrics);
    }
  }

  @Override
  public void run() {
    CaskPircBotXListenerAdapter listener = new CaskPircBotXListenerAdapter();
    this.bot = new PircBotX(getConfiguration(listener, nick, pass, host, port));
    listener.initialize(this);
    try {
      this.bot.startBot();
    } catch (IOException e) {
      LOG.error("IOException in bot run startBot", e);
    } catch (IrcException e) {
      LOG.error("IrcException in bot run startBot", e);
    }
  }

  @Override
  protected List<CaskBotPlugin> getPlugins() {
    return this.plugins;
  }

  @Override
  public String getNick() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void sendRoomMessage(String room, String msg) {
    this.bot.sendIRC().message(room, msg);
  }

  @Override
  public void sendPrivateMessage(String nick, String msg) {
    this.bot.sendIRC().message(nick, msg);
  }

  @Override
  public void opUser(String room, String nick) {
    this.bot.sendIRC().mode(room, "+o " + nick);
  }

  @Override
  public void deopUser(String room, String nick) {
    this.bot.sendIRC().mode(room, "-o " + nick);
  }

  @Override
  public void kickUser(String room, String nick, String msg) {
    this.bot.sendRaw().rawLine("KICK " + room + " " + nick + " :" +
        msg == null ? "" : msg);
  }

  @Override
  public void voiceUser(String room, String nick) {
    this.bot.sendIRC().mode(room, "+v " + nick);
  }

  @Override
  public void devoiceUser(String room, String nick) {
    this.bot.sendIRC().mode(room, "-v " + nick);
  }

  @Override
  public void setTopic(String room, String topic) {
    String rawLine = "TOPIC " + room + " :" +
        (topic == null ? "" : topic);
    LOG.info("Setting topic with raw line: " + rawLine);
    this.bot.sendRaw().rawLine(rawLine);
  }

  public static Configuration<PircBotX> getConfiguration(Listener<PircBotX> listener,
      String nick, String pass, String host, int port) {
    Configuration<PircBotX> config = new Configuration.Builder<PircBotX>()
        .setName(nick)
        .setNickservPassword(pass)
        .setAutoNickChange(true)
        .setAutoReconnect(true)
        .setLogin(nick)
        .setRealName("The Cask Bot http://cask.co")
        .setServer(host, port)
        .addListener(listener)
        .buildConfiguration();
    for (String channel : CaskBotIrcService.IRC_DEFAULT_CHANNELS) {
      config = new Configuration.Builder<PircBotX>(config)
          .addAutoJoinChannel(channel)
          .buildConfiguration();
    }
    return config;
  }

}
