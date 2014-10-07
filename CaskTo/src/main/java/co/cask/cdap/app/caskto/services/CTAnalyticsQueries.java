package co.cask.cdap.app.caskto.services;

import co.cask.cdap.app.caskto.CaskTo;
import co.cask.cdap.app.caskto.datasets.CTAnalytics;
import co.cask.cdap.app.caskto.types.CTStatsResponse;

import com.continuuity.api.annotation.Handle;
import com.continuuity.api.annotation.UseDataSet;
import com.continuuity.api.procedure.AbstractProcedure;
import com.continuuity.api.procedure.ProcedureRequest;
import com.continuuity.api.procedure.ProcedureResponder;
import com.continuuity.api.procedure.ProcedureSpecification;

public class CTAnalyticsQueries extends AbstractProcedure {

  public static final String NAME = "analytics";

  @UseDataSet(CaskTo.DATASET_ANALYTICS)
  private CTAnalytics analyticsTable;

  @Override
  public ProcedureSpecification configure() {
    return ProcedureSpecification.Builder.with()
        .setName(NAME)
        .setDescription("Cask.to Analytics Procedure")
        .useDataSet(CaskTo.DATASET_ANALYTICS)
        .build();
  }

  @Handle("stats")
  public void getStats(ProcedureRequest request, ProcedureResponder responder)
  throws Exception {
    long shortened = analyticsTable.getUrlsShortenedCount();
    long redirected = analyticsTable.getUrlsRedirectedCount();
    responder.sendJson(new CTStatsResponse(shortened, redirected));
  }

}
