package co.cask.cdap.app.caskbot.irc;
//import org.pircbotx.PircBotX;

//package co.cask.cdap.app.caskbot.irc;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.TreeMap;
//
//import org.pircbotx.PircBotX;
//import org.pircbotx.exception.IrcException;
//import org.pircbotx.hooks.events.MessageEvent;
//import org.pircbotx.hooks.events.PrivateMessageEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import co.cask.cdap.app.caskbot.CaskBot;
//
//public class CaskBotPircBotXOld implements CaskBot {
//  public static Logger LOG = LoggerFactory.getLogger(CaskBotPircBotX.class);
//
//  private final CaskPircBotX bot;
//
//  private final Map<String, String> config;
//
//  public CaskBotPircBotX(CaskPircBotX bot, Map<String,String> config) {
//    this.bot = bot;
//    this.config = new TreeMap<String, String>();
//    if (config != null) {
//      this.config.putAll(config);
//    }
//  }
//
//  public void botEvent(String eventType, String eventText,
//      MessageEvent<PircBotX> event) {
//    eventType = eventType.toLowerCase();
//    if (eventType.equals("ping")) {
//      bot.sendMessage("pong!", event);
//    } else if (eventType.equals("say")) {
//      bot.sendMessage(eventText, event);
//    } else {
//      bot.sendMessage(event.getUser().getNick() +
//          ": Unknown event type '" + eventType + "'", event);
//    }
//  }
//
//  public void botSentMsg(String userNick, String eventMsg,
//      MessageEvent<PircBotX> event) {
//    bot.sendMessage(userNick + ": " + eventMsg, event);
//  }
//
//  public void ownerSpoke(String msg, MessageEvent<PircBotX> event) {
//    LOG.info("Owner spoke: " + msg);
//  }
//
//  public void ownerPrivate(String msg, PrivateMessageEvent<PircBotX> event) {
//    bot.sendMessage(event.getUser().getNick(), "As you wish (" + msg + ")");
//  }
//}
//
//if (msg.startsWith(EVENT_PREFIX)) {
//  String [] split = msg.split(" ", 2);
//  split[0] = split[0].substring(1);
//  caskBot.botEvent(split[0], split.length > 1 ? split[1] : "", event);
//} else if (msg.startsWith(nick + ": ")) {
//  String [] split2 = msg.split(" ", 2);
//  caskBot.botSentMsg(event.getUser().getNick(), split2.length > 1 ? split2[1] : "", event);
//} else if (event.getUser().getNick().equals(OWNER)) {
//  caskBot.ownerSpoke(msg, event);
//} else {
//  LOG.info("No action taken on channel message");
//}
//
//
//public static class BotRunnable implements Runnable {
//
//  boolean running = false;
//
//  final PircBotX bot;
//
//  public BotRunnable(PircBotX bot) {
//    this.bot = bot;
//  }
//  
//  @Override
//  public void run() {
//    running = true;
//    try {
//      this.bot.startBot();
//    } catch (IOException e) {
//      LOG.error("IOException in bot runnable start", e);
//    } catch (IrcException e) {
//      LOG.error("IrcException in bot runnable start", e);
//    }
//    running = false;
//  }
//  
//}
//
//
/////**
////* @param args
////*/
////public static void main(String[] args) throws Exception {
////CaskPircBotX bot = new CaskPircBotX();
////bot.start();
////Thread.sleep(10000);
////while (bot.isConnected()) {
////  System.out.println("Bot still connected");
////  Thread.sleep(5000);
////}
////System.out.println("Exiting");
////}
//
//
//
//  public void start() {
//    if (this.bot != null) {
//      throw new RuntimeException("Already started");
//    }
//
//    CaskPircBotXListenerAdapter listener = new CaskPircBotXListenerAdapter();
//    this.bot = new PircBotX(getConfiguration(listener, nick, pass, host, port));
//    listener.initialize(this);
//    this.botRunnable = new BotRunnable(this.bot);
//    this.botThread = new Thread(this.botRunnable);
////    IdentServer.startServer();
//    this.botThread.start();
//  }