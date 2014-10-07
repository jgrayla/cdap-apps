package co.cask.cdap.app.caskbot.plugins;

import com.google.gson.Gson;

/**
 * Plugin Ideas:
 * <ul>
 *   <li>Logger</li>
 *   <li>Pastebin</li>
 *   <li>Seen</li>
 *   <li>Jira</li>
 *   <li>Admin</li>
 *   <li>Copycat</li>
 *   <li>SendLater</li>
 *   <li>Google</li>
 *   <li>CaskDoc</li>
 *   <li>SMS</li>
 *   <li>Notify</li>
 *   <li>CDAP</li>
 *   <li>Coopr</li>
 * </ul>
 */
public class PluginUtil {

  public static Gson gson;

  public static final char ACTION_PREFIX = '!';

  public static boolean isAction(String msg, String action) {
    String trailingSpace = msg.contains(" ") ? " " : "";
    if (msg.startsWith("!" + action + trailingSpace)) {
      return true;
    }
    return false;
  }

  public static String getActionMsg(String msg) {
    String [] split = msg.split(" ", 2);
    if (split.length <= 1) {
      return "";
    }
    return split[1];
  }

  public static <T> T jsonToType(String json, Class<T> clazz) {
    if (json == null || json.isEmpty()) {
      return null;
    }
    return getGson().fromJson(json, clazz);
  }

  public static String typeToJson(Object obj) {
    return getGson().toJson(obj);
  }

  public static Gson getGson() {
    if (gson == null) {
      gson = new Gson();
    }
    return gson;
  }
}
