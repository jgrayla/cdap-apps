package co.cask.cdap.app.caskbot;

import co.cask.cdap.app.caskbot.data.CaskBotConfig;
import co.cask.cdap.app.caskbot.data.CaskBotMetrics;

public interface CaskBotPlugin extends CaskBotCallbacks {

  public void initialize(CaskBot bot,
      CaskBotConfig config, CaskBotMetrics metrics);

  public String getName();

  public String getDescription();

}
