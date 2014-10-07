package co.cask.cdap.app.caskto.flows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.cask.cdap.app.caskto.types.CTEvent;

import com.continuuity.api.annotation.ProcessInput;
import com.continuuity.api.flow.flowlet.AbstractFlowlet;

public class CTEventAnalyticsFlowlet extends AbstractFlowlet {
  private static final Logger LOG =
      LoggerFactory.getLogger(CTEventAnalyticsFlowlet.class);

  @ProcessInput
  public void receiveEvent(CTEvent event) {
    LOG.info("Received event: " + event.toString());
    // TODO: Perform analytics
  }
  
}
