package co.cask.cdap.app.caskbot.data;

import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.dataset.table.Table;

/**
 * Implements a persistence layer for storing/retrieving plugin configuration.
 * <p>
 * A thin library on top of a Table dataset.
 */
public class CaskBotMetrics {

  private static final byte [] ROW_PLUGIN_PREFIX = Bytes.toBytes("p_");

  private static final byte [] COLUMN_CONFIG = Bytes.toBytes("config");

  private Table table;

  public CaskBotMetrics(Table table) {
    this.table = table;
  }

  /**
   * Stores the specified configuration string for the specified plugin. 
   * @param plugin name of plugin
   * @param config configuration string
   */
  public void storePluginConfig(String plugin, String config) {
    table.put(Bytes.add(ROW_PLUGIN_PREFIX, Bytes.toBytes(plugin)),
        COLUMN_CONFIG, Bytes.toBytes(config));
  }

  /**
   * Retrieves the configuration string for the specified plugin. Returns null
   * if no configuration was found, or the configuration was empty.
   * @param plugin name of plugin
   * @return configuration string for plugin, null if no configuration
   */
  public String retrievePluginConfig(String plugin) {
    byte [] value = table.get(
        Bytes.add(ROW_PLUGIN_PREFIX, Bytes.toBytes(plugin)), COLUMN_CONFIG);
    if (value == null || value.length == 0) {
      return null;
    }
    return Bytes.toString(value);
  }
}
