package co.cask.cdap.app.caskto.flows;

import co.cask.cdap.app.caskto.CaskTo;

import com.continuuity.api.flow.Flow;
import com.continuuity.api.flow.FlowSpecification;

public class CTRedirectFlow implements Flow {

  public static final String NAME = "RedirectFlow";

  @Override
  public FlowSpecification configure() {
    return FlowSpecification.Builder.with()
        .setName(NAME)
        .setDescription("Flow to process redirect events")
        .withFlowlets()
            .add("analytics", new RedirectAnalyticsFlowlet())
            .add("trend-detect", new RedirectTrendDetectorFlowlet())
        .connect()
            .fromStream(CaskTo.STREAM_REDIRECT_EVENTS).to("analytics")
            .from("analytics").to("trend-detect")
        .build();
  }

}
