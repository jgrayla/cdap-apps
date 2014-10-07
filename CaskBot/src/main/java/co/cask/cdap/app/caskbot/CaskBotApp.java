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

import co.cask.cdap.api.app.AbstractApplication;
import co.cask.cdap.api.dataset.table.Table;
import co.cask.cdap.app.caskbot.irc.CaskBotIrcService;

/**
 * CaskBot Application.
 */
public class CaskBotApp extends AbstractApplication {

  @Override
  public void configure() {
    setName(CaskBot.NAME);
    setDescription(CaskBot.DESC);
    createDataset(CaskBot.TABLE_CONFIG, Table.class);
    createDataset(CaskBot.TABLE_METRICS, Table.class);
    addService(new CaskBotIrcService());
    // addService(new CaskBotHipChatService());
  }

}
