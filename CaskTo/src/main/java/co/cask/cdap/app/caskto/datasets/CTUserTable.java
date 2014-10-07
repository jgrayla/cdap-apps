package co.cask.cdap.app.caskto.datasets;

import co.cask.cdap.app.caskto.CaskTo;
import co.cask.cdap.app.caskto.types.CTUser;
import co.cask.cdap.app.caskto.types.CTUserCreateResponse;

import com.continuuity.api.dataset.DatasetSpecification;
import com.continuuity.api.dataset.lib.AbstractDataset;
import com.continuuity.api.dataset.module.EmbeddedDataset;
import com.continuuity.api.dataset.table.Table;

public class CTUserTable extends AbstractDataset {

  @SuppressWarnings("unused")
  private Table userTable;

  public CTUserTable(DatasetSpecification spec,
      @EmbeddedDataset(CaskTo.DATASET_USERS) Table userTable) {
    super(spec.getName(), userTable);
    this.userTable = userTable;
  }

  /**
   * Creates a new user with the specified CTUser object.  Returns success
   * or failure with a message.  On success, it returns the CTUser object with
   * any server-side fields completed (like the userid).
   * @param newUser
   * @return
   */
  public CTUserCreateResponse createUser(CTUser newUser) {
    return new CTUserCreateResponse(newUser, true, "Created User Successfully");
  }
}
