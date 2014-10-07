package co.cask.cdap.app.caskto;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.continuuity.test.ApplicationManager;
import com.continuuity.test.ReactorTestBase;

/**
 * Cask.to Tests.
 */
public class TestCaskTo extends ReactorTestBase {

  @SuppressWarnings("unused")
  @Test
  public void testCaskToSimpleDeploy()
  throws IOException, TimeoutException, InterruptedException {

    // Deploy the Application
    ApplicationManager appManager = deployApplication(CaskToUrlShortener.class);
//
//    // Start the Flow and the Procedure
//    FlowManager flowManager = appManager.startFlow("WordCounter");
//    ProcedureManager procManager = appManager.startProcedure("RetrieveCounts");
//
//    // Send a few events to the stream
//    StreamWriter writer = appManager.getStreamWriter("wordStream");
//    writer.send("hello world");
//    writer.send("a wonderful world");
//    writer.send("the world says hello");
//
//    // Wait for the events to be processed, or at most 5 seconds
//    RuntimeMetrics metrics = RuntimeStats.getFlowletMetrics("WordCount", "WordCounter", "associator");
//    metrics.waitForProcessed(3, 5, TimeUnit.SECONDS);
//
//    // Now call the procedure
//    ProcedureClient client = procManager.getClient();
//
//    // First verify global statistics
//    String response = client.query("getStats", Collections.EMPTY_MAP);
//    Map<String, String> map = new Gson().fromJson(response, stringMapType);
//    Assert.assertEquals("9", map.get("totalWords"));
//    Assert.assertEquals("6", map.get("uniqueWords"));
//    Assert.assertEquals(((double) 42) / 9, (double) Double.valueOf(map.get("averageLength")), 0.001);
//
//    // Now verify some statistics for one of the words
//    response = client.query("getCount", ImmutableMap.of("word", "world"));
//    Map<String, Object> omap = new Gson().fromJson(response, objectMapType);
//    Assert.assertEquals("world", omap.get("word"));
//    Assert.assertEquals(3.0, omap.get("count"));
//
//    // The associations are a map within the map
//    @SuppressWarnings("unchecked")
//    Map<String, Double> assocs = (Map<String, Double>) omap.get("assocs");
//    Assert.assertEquals(2.0, (double) assocs.get("hello"), 0.000001);
//    Assert.assertTrue(assocs.containsKey("hello"));
  }
}
