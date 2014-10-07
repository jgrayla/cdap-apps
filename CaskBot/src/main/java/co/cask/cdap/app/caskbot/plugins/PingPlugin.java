package co.cask.cdap.app.caskbot.plugins;

import co.cask.cdap.app.caskbot.AbstractCaskBotPlugin;
import co.cask.cdap.app.caskbot.CaskBot;
import co.cask.cdap.app.caskbot.data.CaskBotConfig;
import co.cask.cdap.app.caskbot.data.CaskBotMetrics;

/**
 * Simple ping plugin which responds to the !ping action in a room or chat with pong!
 */
public class PingPlugin extends AbstractCaskBotPlugin {

  private CaskBot bot;

  public PingPlugin() {}

  @Override
  public void initialize(CaskBot bot, CaskBotConfig config,
      CaskBotMetrics metrics) {
    this.bot = bot;
  }

  @Override
  public String getName() {
    return "PingPlugin";
  }

  @Override
  public String getDescription() {
    return "Simple Ping/Pong Plugin";
  }

  @Override
  public void onRoomMessage(String room, String nick, String msg) {
    if (msg.equals("!ping")) {
      this.bot.sendRoomMessage(room, "pong!");
    }
  }

  @Override
  public void onPrivateMessage(String nick, String msg) {
    if (msg.equals("!ping")) {
      this.bot.sendPrivateMessage(nick, "pong!");
    }
  }

}
