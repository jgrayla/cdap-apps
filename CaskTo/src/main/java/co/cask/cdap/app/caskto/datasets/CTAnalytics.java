package co.cask.cdap.app.caskto.datasets;

import com.continuuity.api.dataset.DatasetSpecification;
import com.continuuity.api.dataset.lib.AbstractDataset;
import com.continuuity.api.dataset.module.EmbeddedDataset;
import com.continuuity.api.dataset.table.Get;
import com.continuuity.api.dataset.table.Increment;
import com.continuuity.api.dataset.table.Row;
import com.continuuity.api.dataset.table.Table;

public class CTAnalytics extends AbstractDataset {

  private static final String ROW_SHORTENED = "shortened";
  private static final String ROW_REDIRECTED = "redirected";
  private static final String COL_TOTAL = "total";

  private Table countTable;

  public CTAnalytics(DatasetSpecification spec,
      @EmbeddedDataset("counts") Table countTable) {
    super(spec.getName(), countTable);
    this.countTable = countTable;
  }

  public void incrementUrlsShortened() {
    this.countTable.increment(new Increment(ROW_SHORTENED, COL_TOTAL, 1L));
  }
  
  public void incrementUrlsRedirected() {
    this.countTable.increment(new Increment(ROW_REDIRECTED, COL_TOTAL, 1L));
  }
  
  public long getUrlsShortenedCount() {
    Row row = this.countTable.get(new Get(ROW_SHORTENED, COL_TOTAL));
    return row == null || row.isEmpty() ? 0L : row.getLong(COL_TOTAL);
  }
  
  public long getUrlsRedirectedCount() {
    Row row = this.countTable.get(new Get(ROW_REDIRECTED, COL_TOTAL));
    return row == null || row.isEmpty() ? 0L :  row.getLong(COL_TOTAL);
  }

}
