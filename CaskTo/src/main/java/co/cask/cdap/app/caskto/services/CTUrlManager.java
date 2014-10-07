package co.cask.cdap.app.caskto.services;

import co.cask.cdap.app.caskto.CaskTo;
import co.cask.cdap.app.caskto.datasets.CTAnalytics;
import co.cask.cdap.app.caskto.datasets.CTUrlTable;
import co.cask.cdap.app.caskto.types.CTErrorResponse;
import co.cask.cdap.app.caskto.types.CTUrlListResponse;
import co.cask.cdap.app.caskto.types.CTUrlShortenRequest;
import co.cask.cdap.app.caskto.types.CTUrlShortenResponse;

import com.continuuity.api.annotation.Handle;
import com.continuuity.api.annotation.UseDataSet;
import com.continuuity.api.procedure.AbstractProcedure;
import com.continuuity.api.procedure.ProcedureRequest;
import com.continuuity.api.procedure.ProcedureResponder;
import com.continuuity.api.procedure.ProcedureSpecification;

public class CTUrlManager extends AbstractProcedure {

  public static final String NAME = "url-manager";

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

  @Handle("create")
  public void createUrl(ProcedureRequest request, ProcedureResponder responder)
  throws Exception {
    String url = request.getArgument("url");
    if (url == null) {
      responder.sendJson(new CTErrorResponse("Must specify 'url'"));
      return;
    }
    CTUrlShortenResponse response = this.urlTable.shortenUrl(
        new CTUrlShortenRequest(CaskTo.TEMP_USERID, url));
    if (!response.existed) {
      this.analyticsTable.incrementUrlsShortened();
    }
    responder.sendJson(response);
  }

  @Handle("list")
  public void listUrls(ProcedureRequest request, ProcedureResponder responder)
  throws Exception {
    CTUrlListResponse response = this.urlTable.listUrls();
    responder.sendJson(response);
  }
}
