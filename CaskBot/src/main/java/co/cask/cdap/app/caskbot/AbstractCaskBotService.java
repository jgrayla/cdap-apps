package co.cask.cdap.app.caskbot;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.cask.cdap.api.annotation.UseDataSet;
import co.cask.cdap.api.common.Bytes;
import co.cask.cdap.api.dataset.lib.KeyValueTable;
import co.cask.cdap.api.dataset.table.Table;
import co.cask.cdap.api.service.AbstractService;
import co.cask.cdap.api.service.AbstractServiceWorker;
import co.cask.cdap.api.service.http.AbstractHttpServiceHandler;
import co.cask.cdap.api.service.http.HttpServiceRequest;
import co.cask.cdap.api.service.http.HttpServiceResponder;
import co.cask.cdap.app.caskbot.data.CaskBotConfig;
import co.cask.cdap.app.caskbot.data.CaskBotMetrics;

public abstract class AbstractCaskBotService extends AbstractService {
  public static Logger LOG = LoggerFactory.getLogger(AbstractCaskBotService.class);

  public abstract String getName();

  public abstract String getDescription();

  public abstract AbstractServiceWorker getServiceWorker();

  @Override
  public void configure() {
    setName(getName());
    setDescription(getDescription());
    useDataset(CaskBot.TABLE_CONFIG);
    useDataset(CaskBot.TABLE_METRICS);
    addWorker(getServiceWorker());
    addHandler(new CaskBotHttpServiceHandler());
  }

  public static class CaskBotHttpServiceHandler extends AbstractHttpServiceHandler {

    @UseDataSet(CaskBot.TABLE_CONFIG)
    KeyValueTable configTable;

    @UseDataSet(CaskBot.TABLE_METRICS)
    KeyValueTable metricsTable;

    @Path("metric/{name}")
    @GET
    public void getMetric(HttpServiceRequest request, HttpServiceResponder responder,
        @PathParam("name") String name) throws Exception {
      long eventCount = metricsTable.incrementAndGet(Bytes.toBytes(name), 0L);
      responder.sendJson(Long.toString(eventCount));
    }
  }

  public abstract static class AbstractCaskBotServiceWorker extends AbstractServiceWorker {

    @UseDataSet(CaskBot.TABLE_CONFIG)
    Table configTable;

    @UseDataSet(CaskBot.TABLE_METRICS)
    Table metricsTable;

    public abstract CaskBot getBot();

    public abstract List<CaskBotPlugin> getPlugins();

    @Override
    public void run() {
      LOG.info("Starting CaskBot '" + getName() + "' Service Worker");
      LOG.info("'" + getName() + "' Service Worker : Injecting Tables and Plugins");
      try {
        getBot().initialize(new CaskBotConfig(configTable), new CaskBotMetrics(metricsTable),
            getPlugins());
      } catch (RuntimeException re) {
        LOG.error("Runtime error!", re);
        throw re;
      } catch (Exception e) {
        LOG.error("Generic exception", e);
      }
      LOG.info("'" + getName() + "' Service Worker : Tables and Plugins injected");
      LOG.info("'" + getName() + "' Service Worker : Running bot");
      getBot().run();
      LOG.info("'" + getName() + "' Service Worker : Bot stopped, exiting");
    }
    
  }
}
