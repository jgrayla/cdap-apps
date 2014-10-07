package co.cask.cdap.app.caskbot.plugins;

import java.util.Map;
import java.util.TreeMap;

import co.cask.cdap.app.caskbot.AbstractCaskBotPlugin;
import co.cask.cdap.app.caskbot.CaskBot;
import co.cask.cdap.app.caskbot.data.CaskBotConfig;
import co.cask.cdap.app.caskbot.data.CaskBotMetrics;
import co.cask.cdap.app.caskbot.irc.CaskBotIrcService;
import co.cask.cdap.app.caskbot.plugins.TopicLockPlugin.TopicLockConfig.TopicLock;

/**
 * Simple plugin that sets and locks the topic of a channel.
 */
public class TopicLockPlugin extends AbstractCaskBotPlugin {

  private CaskBot bot;

  private CaskBotConfig configStore;

  private TopicLockConfig config;

  public TopicLockPlugin() {}

  @Override
  public void initialize(CaskBot bot, CaskBotConfig config,
      CaskBotMetrics metrics) {
    this.bot = bot;
    this.configStore = config;
//    this.config = PluginUtil.jsonToType(
//        configStore.retrievePluginConfig(getName()), TopicLockConfig.class);
    if (this.config == null) {
      this.config = new TopicLockConfig();
    }
  }

  @Override
  public String getName() {
    return "TopicLockPlugin";
  }

  @Override
  public String getDescription() {
    return "Channel Topic Lock Plugin";
  }

  @Override
  public void onRoomMessage(String room, String nick, String msg) {
    if (PluginUtil.isAction(msg, "topic")) {
      String topic = PluginUtil.getActionMsg(msg);
      // Only perform if user is allowed
      if (userAllowed(nick)) {
        if (topic.isEmpty()) {
          if (this.config.map.containsKey(room)) {
            this.config.map.remove(room);
            saveConfig();
            this.bot.sendRoomMessage(room, "Topic is now unlocked");
          } else {
            this.bot.sendRoomMessage(room, "Topic was not locked");
          }
        }
        this.bot.setTopic(room, topic);
        this.config.map.put(room, new TopicLock(topic, nick, System.currentTimeMillis()));
        saveConfig();
      } else {
        this.bot.sendRoomMessage(room, "Sorry " + nick + ", you are not allowed");
      }
    }
  }

  private void saveConfig() {
//    this.configStore.storePluginConfig(getName(),
//        PluginUtil.typeToJson(this.config));
  }

  public boolean userAllowed(String nick) {
    for (String OWNER : CaskBotIrcService.IRC_DEFAULT_OWNERS) {
      if (nick.equals(OWNER)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void onRoomTopicChange(String room, String nick, String topic) {
    if (config.map.containsKey(room)) {
      TopicLock lock = config.map.get(room);
      if (topic.equals(lock.topic)) {
        return;
      }
      this.bot.sendRoomMessage(room, "Sorry " + nick +
          ", the topic has been locked by " + lock.nick);
      this.bot.setTopic(room, lock.topic); 
    }
  }

//  @Override
//  public void onUserJoinRoom(String room, String nick) {
//    if (nick.equals(this.bot.getNick())) {
//      if (this.config.map.containsKey(room)) {
//        String existingTopic = this.bot.getTopic(room);
//        TopicLock lock = this.config.map.get(room);
//        String newTopic = lock.topic;
//        if (!existingTopic.equals(newTopic)) {
//          this.bot.sendRoomMessage(room, "The topic has been locked by " +
//              lock.nick);
//          this.bot.setTopic(room, newTopic); 
//        }
//      }
//    }
//  }

  public static class TopicLockConfig {

    /** Map of channel to topic lock */
    public Map<String, TopicLock> map;

    public TopicLockConfig() {
      this(new TreeMap<String, TopicLock>());
    }

    public TopicLockConfig(Map<String, TopicLock> map) {
      this.map = map;
    }

    public static class TopicLock {

      public String topic;
      public String nick;
      public long stamp;

      public TopicLock(String topic, String nick, long stamp) {
        this.topic = topic;
        this.nick = nick;
        this.stamp = stamp;
      }
    }
  }
}
