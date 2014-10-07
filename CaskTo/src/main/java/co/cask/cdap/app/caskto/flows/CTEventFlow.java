package co.cask.cdap.app.caskto.flows;

import co.cask.cdap.app.caskto.CaskTo;

import com.continuuity.api.flow.Flow;
import com.continuuity.api.flow.FlowSpecification;

public class CTEventFlow implements Flow {

  public static final String NAME = "EventFlow";

  @Override
  public FlowSpecification configure() {
    return FlowSpecification.Builder.with()
        .setName(NAME)
        .setDescription("Flow to process user events and web logs")
        .withFlowlets()
            .add("event-parser", new CTEventParserFlowlet())
            .add("weblog-parser", new CTWebLogParserFlowlet())
            .add("event-join", new CTEventJoinFlowlet())
            .add("analytics", new CTEventAnalyticsFlowlet())
        .connect()
            .fromStream(CaskTo.STREAM_EVENTS).to("event-parser")
            .fromStream(CaskTo.STREAM_WEB_LOGS).to("weblog-parser")
            .from("event-parser").to("event-join")
            .from("weblog-parser").to("event-join")
            .from("event-join").to("analytics")
        .build();
  }

}
