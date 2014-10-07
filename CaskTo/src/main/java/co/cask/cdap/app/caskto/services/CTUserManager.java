package co.cask.cdap.app.caskto.services;

import co.cask.cdap.app.caskto.CaskTo;
import co.cask.cdap.app.caskto.datasets.CTUserTable;

import com.continuuity.api.annotation.Handle;
import com.continuuity.api.annotation.UseDataSet;
import com.continuuity.api.procedure.AbstractProcedure;
import com.continuuity.api.procedure.ProcedureRequest;
import com.continuuity.api.procedure.ProcedureResponder;

public class CTUserManager extends AbstractProcedure {

  @UseDataSet(CaskTo.DATASET_USERS)
  private CTUserTable userTable;

  @Handle("create")
  public void createUser(ProcedureRequest request, ProcedureResponder responder)
  throws Exception {

  }

  @Handle("login")
  public void loginUser(ProcedureRequest request, ProcedureResponder responder)
  throws Exception {

  }

  @Handle("remove")
  public void removeUser(ProcedureRequest request, ProcedureResponder responder)
  throws Exception {

  }

}
