package co.cask.cdap.app.caskto;

import co.cask.cdap.app.caskto.datasets.CTAnalytics;
import co.cask.cdap.app.caskto.datasets.CTUrlTable;
import co.cask.cdap.app.caskto.flows.CTEventFlow;
import co.cask.cdap.app.caskto.flows.CTRedirectFlow;
import co.cask.cdap.app.caskto.flows.CTSecFlow;
import co.cask.cdap.app.caskto.services.CTAnalyticsQueries;
import co.cask.cdap.app.caskto.services.CTUrlManager;
import co.cask.cdap.app.caskto.services.CTUrlRedirector;

import com.continuuity.api.app.AbstractApplication;
import com.continuuity.api.data.stream.Stream;

/**
 * Cask.to URL Shortener
 *
 * This is an application to power a URL Shortening service that runs on CDAP.
 * 
 * A live production instance of this application is running at http://cask.to
 */
public class CaskToUrlShortener extends AbstractApplication {
  
  public static final String NAME = "caskto";

  @Override
  public void configure() {

    // Configure metadata of the application
    setName(NAME);
    setDescription("Cask URL Shortener Service (cask.to)");

    // Streams to receive raw events and logs from the shortener service
    // for permanent archival and real-time processing for analytics
    addStream(new Stream(CaskTo.STREAM_EVENTS));
    addStream(new Stream(CaskTo.STREAM_REDIRECT_EVENTS));
    addStream(new Stream(CaskTo.STREAM_WEB_LOGS));
    
    // Flow to process redirects
    addFlow(new CTRedirectFlow());
    
    // FLow to process user events and web logs
    addFlow(new CTEventFlow());
    
    // Flow to process all events for security / denial of service attacks
    addFlow(new CTSecFlow());

    // Datasets to store user and url data
    // createDataset(CaskTo.DATASET_USERS, CTUserTable.class);
    createDataset(CaskTo.DATASET_URLS, CTUrlTable.class);
    
    // Datasets to store analytics
    // createDataset(CaskTo.DATASET_CUBE, Cube.class);
    createDataset(CaskTo.DATASET_ANALYTICS, CTAnalytics.class);
    
    // Datasets to store security info, rules and alerts
    // createDataset(CaskTo.DATASET_SECURITY_BLACKLIST, CTSecIPBlackList.class);
    // createDataset(CaskTo.DATASET_SECURITY_RULES, CTSecRules.class);

    // Procedure to serve user requests
    // addProcedure(new CTUserManager());
    
    // Procedure to serve url requests
    addProcedure(new CTUrlManager());
    
    // Procedure to serve redirects
    addProcedure(new CTUrlRedirector());
    
    // Procedure to serve analytics
    addProcedure(new CTAnalyticsQueries());
    
    // Procedure to serve security information
    // addProcedure(new CTSecManager());
    
  }
}
