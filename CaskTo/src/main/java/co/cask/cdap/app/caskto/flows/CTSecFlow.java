package co.cask.cdap.app.caskto.flows;

import co.cask.cdap.app.caskto.CaskTo;

import com.continuuity.api.flow.Flow;
import com.continuuity.api.flow.FlowSpecification;

public class CTSecFlow implements Flow {

  public static final String NAME = "SecurityFlow";

  @Override
  public FlowSpecification configure() {
    return FlowSpecification.Builder.with()
        .setName(NAME)
        .setDescription("Flow to process user events and web logs")
        .withFlowlets()
            .add("redirect-parser", new CTEventParserFlowlet())
            .add("event-parser", new CTEventParserFlowlet())
            .add("weblog-parser", new CTWebLogParserFlowlet())
            .add("event-join", new CTEventJoinFlowlet())
            .add("dos-detect", new DenialOfServiceDetectorFlowlet())
        .connect()
            .fromStream(CaskTo.STREAM_REDIRECT_EVENTS).to("redirect-parser")
            .fromStream(CaskTo.STREAM_EVENTS).to("event-parser")
            .fromStream(CaskTo.STREAM_WEB_LOGS).to("weblog-parser")
            .from("redirect-parser").to("event-join")
            .from("event-parser").to("event-join")
            .from("weblog-parser").to("event-join")
            .from("event-join").to("dos-detect")
        .build();
  }

}
