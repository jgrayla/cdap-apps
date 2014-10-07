package co.cask.cdap.app.caskto.datasets;

import com.continuuity.api.common.Bytes;
import com.continuuity.api.dataset.DatasetSpecification;
import com.continuuity.api.dataset.lib.AbstractDataset;
import com.continuuity.api.dataset.module.EmbeddedDataset;
import com.continuuity.api.dataset.table.Increment;
import com.continuuity.api.dataset.table.Row;
import com.continuuity.api.dataset.table.Table;

public class IDGenerator extends AbstractDataset {

  private static final byte [] COLUMN = new byte [] { 'c' };

  private Table idTable;

  public IDGenerator(DatasetSpecification spec,
      @EmbeddedDataset("ids") Table idTable) {
    super(spec.getName(), idTable);
    this.idTable = idTable;
  }

  public long generateID(String idGroup) {
    Row row = this.idTable.increment(
        new Increment(Bytes.toBytes(idGroup), COLUMN, 1L));
    return row.getLong(COLUMN);
  }
}
