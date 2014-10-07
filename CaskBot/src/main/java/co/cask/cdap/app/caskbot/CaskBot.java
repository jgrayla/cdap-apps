package co.cask.cdap.app.caskbot;

import java.util.List;

import co.cask.cdap.app.caskbot.data.CaskBotConfig;
import co.cask.cdap.app.caskbot.data.CaskBotMetrics;
import co.cask.cdap.app.caskbot.irc.CaskBotPircBotX;

/**
 * Interface for implementing a CaskBot.
 * <p>
 * Currently implemented for IRC as {@link CaskBotPircBotX}.  HipChat version
 * is under development as {@link CaskBotHipChat}.
 */
public interface CaskBot extends Runnable, CaskBotCallbacks, CaskBotEvents {

  /* Global constants */

  /** Name used for the app, the service, etc. */
  public static final String NAME = "CaskBot";
  public static final String DESC = "CaskBot Chat Bot";
  
  public static final String TABLE_CONFIG = "config";
  public static final String TABLE_METRICS = "metrics";

  public abstract String getNick();

  /**
   * Initialize the CaskBot.
   * @param config
   * @param metrics
   */
  public void initialize(CaskBotConfig config, CaskBotMetrics metrics,
      List<CaskBotPlugin> plugins);

  /**
   * Runs the CaskBot.
   * <p>
   * This method should block for the lifetime of the bot, and return when
   * the bot is no longer running.
   * <p>
   * This method will be called from within a new thread that is created.
   */
  @Override
  public void run();
}
