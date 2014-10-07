package co.cask.cdap.app.caskto;

import org.apache.commons.codec.binary.Base64;

import co.cask.cdap.app.caskto.types.CTEvent;

import com.continuuity.api.common.Bytes;
import com.google.gson.Gson;

/**
 * Utility class that contains static variables and helper methods for the
 * Cask.To URL Shortener.
 */
public class CaskTo {

  // Temp

  /** Userid until users are implemented */
  public static final long TEMP_USERID = 1L;

  //
  // Stream Names
  //

  /** Stream for Redirect Events */
  public static final String STREAM_REDIRECT_EVENTS = "redirect-events";

  /** Stream for Non-Redirect User Events */
  public static final String STREAM_EVENTS = "user-events";

  /** Stream for Raw Web Logs */
  public static final String STREAM_WEB_LOGS = "web-logs";

  //
  // Dataset Names
  //

  public static final String DATASET_USERS = "users";

  public static final String DATASET_URLS = "urls";

  public static final String DATASET_CUBE = "cube";

  public static final String DATASET_ANALYTICS = "analytics";

  public static final String DATASET_SECURITY_BLACKLIST = "sec-blacklist";

  public static final String DATASET_SECURITY_RULES = "sec-rules";

  //
  // Flow Names
  //

  

  //
  // Procedure Names
  //

  //
  // Helper Methods
  //

  // JSON stuff

  private static final ThreadLocal<Gson> GSON_TL = new ThreadLocal<Gson>();

  public static Gson getGson() {
    Gson gson = GSON_TL.get();
    if (gson == null) {
      gson = new Gson();
      GSON_TL.set(gson);
    }
    return gson;
  }

  public static <T> T jsonStringToObject(String jsonString, Class<T> destClass) {
    return getGson().fromJson(jsonString, destClass);
  }

  public static String objectToJsonString(Object srcObject) {
    return getGson().toJson(srcObject);
  }

  // String representations of URL IDs for Short URLs

  public static String idToString(long id) {
    return Base64.encodeBase64URLSafeString(Bytes.toBytes(Long.toString(id)));
  }

  public static long stringToId(String str) {
    return Long.valueOf(Bytes.toString(Base64.decodeBase64(str)));
  }
}
