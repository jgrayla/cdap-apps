package co.cask.cdap.app.caskbot.irc;

import java.util.Arrays;
import java.util.List;

import co.cask.cdap.api.service.AbstractServiceWorker;
import co.cask.cdap.api.service.ServiceWorkerContext;
import co.cask.cdap.app.caskbot.AbstractCaskBotService;
import co.cask.cdap.app.caskbot.CaskBot;
import co.cask.cdap.app.caskbot.CaskBotPlugin;
import co.cask.cdap.app.caskbot.plugins.PingPlugin;
import co.cask.cdap.app.caskbot.plugins.TopicLockPlugin;

public class CaskBotIrcService extends AbstractCaskBotService {

  public static final String NAME = "CaskBotIRC";
  public static final String DESCRIPTION = "CaskBot IRC Service";

  public static final String IRC_DEFAULT_HOSTNAME = "irc.freenode.net";
  public static final int IRC_DEFAULT_PORT = 6667;
  public static final String IRC_DEFAULT_NICK = "TapCask";
  public static final String IRC_DEFAULT_NICK_PASS = "realtime123";

  public static final String [] IRC_DEFAULT_CHANNELS = new String [] {
    "#caskdata", "#cask", "#cdap"
  };

  public static final String [] IRC_DEFAULT_OWNERS = new String [] {
    "jgray"
  };

  public static final List<CaskBotPlugin> IRC_PLUGINS = Arrays.asList(
      new CaskBotPlugin [] {
          new PingPlugin(), new TopicLockPlugin()
      });

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

  @Override
  public AbstractServiceWorker getServiceWorker() {
    return new AbstractCaskBotServiceWorker() {

      private CaskBot bot;
      private List<CaskBotPlugin> plugins;

      @Override
      public void initialize(ServiceWorkerContext context) {
        this.bot = new CaskBotPircBotX(IRC_DEFAULT_NICK, IRC_DEFAULT_NICK_PASS,
            IRC_DEFAULT_HOSTNAME, IRC_DEFAULT_PORT);
        this.plugins = IRC_PLUGINS;
      }

      @Override
      public CaskBot getBot() {
        return this.bot;
      }

      @Override
      public List<CaskBotPlugin> getPlugins() {
        return this.plugins;
      }
      
    };
  }

}
