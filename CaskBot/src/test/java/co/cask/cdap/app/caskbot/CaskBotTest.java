/*
 * Copyright (C) 2014 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package co.cask.cdap.app.caskbot;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import co.cask.cdap.app.caskbot.irc.CaskBotIrcService;
import co.cask.cdap.test.ApplicationManager;
import co.cask.cdap.test.TestBase;

/**
 * Test for {@link CaskBotApp}.
 */
public class CaskBotTest extends TestBase {

  @Test
  public void test() throws TimeoutException, InterruptedException, IOException {
    
    // Deploy the CaskBot Application
    ApplicationManager appManager = deployApplication(CaskBotApp.class);

    // Start the CaskBot Service
    appManager.startService(CaskBotIrcService.NAME);
    
    // Sleep for a while for now
    Thread.sleep(1000 * 60 * 60 * 24);
    
    // Stop everything
    appManager.stopAll();
  }

}
