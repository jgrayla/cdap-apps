package co.cask.cdap.app.caskto.flows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.cask.cdap.app.caskto.CaskTo;
import co.cask.cdap.app.caskto.types.CTEvent;
import co.cask.cdap.app.caskto.types.CTEvent.CTEventSource;

import com.continuuity.api.annotation.ProcessInput;
import com.continuuity.api.common.Bytes;
import com.continuuity.api.flow.flowlet.AbstractFlowlet;
import com.continuuity.api.flow.flowlet.OutputEmitter;
import com.continuuity.api.flow.flowlet.StreamEvent;

/**
 * Accepts and parses cask.to events.
 */
public class CTEventParserFlowlet extends AbstractFlowlet {
  private static final Logger LOG =
      LoggerFactory.getLogger(CTEventParserFlowlet.class);

  private OutputEmitter<CTEvent> eventOutput;

  @ProcessInput
  public void parseEvent(StreamEvent streamEvent) {

    // Receive and parse event from JSON into CTEvent object
    String eventString = Bytes.toString(streamEvent.getBody().array());
    if (LOG.isDebugEnabled()) {
      LOG.debug("Received stream event: " + eventString);
    }
    CTEvent event = CaskTo.jsonStringToObject(eventString, CTEvent.class);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Parsed event: " + event.toString());
    }
    if (event.source == null || event.source != CTEventSource.PROCEDURE) {
      LOG.warn("Received an event with unknown source, or non-procedure source: " +
          event.source);
      return;
    }

    // Send onwards
    eventOutput.emit(event);
  }
}
