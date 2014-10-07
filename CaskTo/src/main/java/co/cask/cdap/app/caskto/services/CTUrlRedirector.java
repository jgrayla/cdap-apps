package co.cask.cdap.app.caskto.services;

import co.cask.cdap.app.caskto.CaskTo;
import co.cask.cdap.app.caskto.datasets.CTAnalytics;
import co.cask.cdap.app.caskto.datasets.CTUrlTable;
import co.cask.cdap.app.caskto.types.CTErrorResponse;
import co.cask.cdap.app.caskto.types.CTUrlRedirectRequest;
import co.cask.cdap.app.caskto.types.CTUrlRedirectResponse;

import com.continuuity.api.annotation.Handle;
import com.continuuity.api.annotation.UseDataSet;
import com.continuuity.api.procedure.AbstractProcedure;
import com.continuuity.api.procedure.ProcedureRequest;
import com.continuuity.api.procedure.ProcedureResponder;
import com.continuuity.api.procedure.ProcedureSpecification;

public class CTUrlRedirector extends AbstractProcedure {

  public static final String NAME = "url-redirector";

  @UseDataSet(CaskTo.DATASET_URLS)
  private CTUrlTable urlTable;

  @UseDataSet(CaskTo.DATASET_ANALYTICS)
  private CTAnalytics analyticsTable;

  @Override
  public ProcedureSpecification configure() {
    return ProcedureSpecification.Builder.with()
        .setName(NAME)
        .setDescription("Cask.to URL Manager Procedure")
        .useDataSet(CaskTo.DATASET_URLS, CaskTo.DATASET_ANALYTICS)
        .build();
  }

  @Handle("resolve")
  public void resolveUrl(ProcedureRequest request, ProcedureResponder responder)
  throws Exception {
    String shortname = request.getArgument("shortname");
    if (shortname == null) {
      responder.sendJson(new CTErrorResponse("Must specify 'shortname'"));
      return;
    }

    CTUrlRedirectResponse response = this.urlTable.resolveUrl(
        new CTUrlRedirectRequest(shortname));
    this.analyticsTable.incrementUrlsRedirected();
    responder.sendJson(response);
  }
}
