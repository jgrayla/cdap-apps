package co.cask.cdap.app.caskto.flows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.cask.cdap.app.caskto.types.CTEvent;

import com.continuuity.api.annotation.ProcessInput;
import com.continuuity.api.common.Bytes;
import com.continuuity.api.flow.flowlet.AbstractFlowlet;
import com.continuuity.api.flow.flowlet.OutputEmitter;
import com.continuuity.api.flow.flowlet.StreamEvent;

/**
 * Accepts and parses cask.to web logs into CTEvents.
 */
public class CTWebLogParserFlowlet extends AbstractFlowlet {
  private static final Logger LOG =
      LoggerFactory.getLogger(CTWebLogParserFlowlet.class);

  @SuppressWarnings("unused")
  private OutputEmitter<CTEvent> eventOutput;

  @ProcessInput
  public void parseEvent(StreamEvent streamEvent) {

    String eventString = Bytes.toString(streamEvent.getBody().array());

    LOG.info("Somehow receieved an event (flowlet not implemented): " + eventString);

    // event.source = CTEventSource.WEBLOG;

  }
}
