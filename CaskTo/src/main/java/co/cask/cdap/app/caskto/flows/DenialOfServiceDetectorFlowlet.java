package co.cask.cdap.app.caskto.flows;

import co.cask.cdap.app.caskto.types.CTEvent;

import com.continuuity.api.annotation.ProcessInput;
import com.continuuity.api.flow.flowlet.AbstractFlowlet;

public class DenialOfServiceDetectorFlowlet extends AbstractFlowlet {

  @ProcessInput
  public void dummyInput(CTEvent event) {
    // TODO: Insert minor details here
  }
}
