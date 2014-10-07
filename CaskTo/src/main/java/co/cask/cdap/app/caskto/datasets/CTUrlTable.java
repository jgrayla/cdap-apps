package co.cask.cdap.app.caskto.datasets;

import java.util.ArrayList;
import java.util.List;

import co.cask.cdap.app.caskto.CaskTo;
import co.cask.cdap.app.caskto.types.CTUrlListResponse;
import co.cask.cdap.app.caskto.types.CTUrlListResponse.MappedUrl;
import co.cask.cdap.app.caskto.types.CTUrlRedirectRequest;
import co.cask.cdap.app.caskto.types.CTUrlRedirectResponse;
import co.cask.cdap.app.caskto.types.CTUrlShortenRequest;
import co.cask.cdap.app.caskto.types.CTUrlShortenResponse;

import com.continuuity.api.common.Bytes;
import com.continuuity.api.dataset.DatasetSpecification;
import com.continuuity.api.dataset.lib.AbstractDataset;
import com.continuuity.api.dataset.module.EmbeddedDataset;
import com.continuuity.api.dataset.table.Get;
import com.continuuity.api.dataset.table.Increment;
import com.continuuity.api.dataset.table.Put;
import com.continuuity.api.dataset.table.Row;
import com.continuuity.api.dataset.table.Scanner;
import com.continuuity.api.dataset.table.Table;

public class CTUrlTable extends AbstractDataset {

  private static String IDGROUP_SHORT = "short";
  
  private static byte [] COL_IDGEN = Bytes.toBytes("idgen");
  
  private static byte [] COL_SHORTNAME = Bytes.toBytes("short");
  
  private static byte [] COL_URL = Bytes.toBytes("url");

  private Table idGenerator;

  private Table originalUrlTable;

  private Table redirectUrlTable;

  public CTUrlTable(DatasetSpecification spec,
      @EmbeddedDataset("ids") Table idGenerator,
      @EmbeddedDataset("original") Table originalUrlTable,
      @EmbeddedDataset("redirect") Table redirectUrlTable) {
    super(spec.getName(), idGenerator, originalUrlTable, redirectUrlTable);
    this.idGenerator = idGenerator;
    this.originalUrlTable = originalUrlTable;
    this.redirectUrlTable = redirectUrlTable;
  }

  public CTUrlShortenResponse shortenUrl(CTUrlShortenRequest request) {
    byte [] urlBytes = Bytes.toBytes(request.url);

    // Check if URL is shortened
    Row existingRow = originalUrlTable.get(new Get(urlBytes, COL_SHORTNAME));
    
    if (!existingRow.isEmpty()) {
      // Already exists, return known shortened URL
      return new CTUrlShortenResponse(
          existingRow.getString(COL_SHORTNAME), "URL Already Shortened", true);
    }

    // Does not exist, generate new shortened URL
    long urlShortId = generateID(IDGROUP_SHORT);
    String shortened = CaskTo.idToString(urlShortId);
    
    // Add to original URL table
    Put originalPut = new Put(urlBytes);
    originalPut.add(COL_SHORTNAME, shortened);
    this.originalUrlTable.put(originalPut);
    
    // Add to redirect URL table
    Put redirectPut = new Put(shortened);
    redirectPut.add(COL_URL, urlBytes);
    this.redirectUrlTable.put(redirectPut);

    return new CTUrlShortenResponse(shortened, "Shortened URL Successfully", false);
  }

  private long generateID(String idGroup) {
    Row row = this.idGenerator.increment(
        new Increment(Bytes.toBytes(idGroup), COL_IDGEN, 1L));
    return row.getLong(COL_IDGEN);
  }

  public CTUrlRedirectResponse resolveUrl(CTUrlRedirectRequest request) {
    byte [] shortnameBytes = Bytes.toBytes(request.shortname);

    // Lookup full URL
    Row existingRow = this.redirectUrlTable.get(
        new Get(shortnameBytes, COL_URL));

    if (existingRow.isEmpty()) {
      // Shortened URL does not exist
      return new CTUrlRedirectResponse("Unknown short URL");
    }

    return new CTUrlRedirectResponse(existingRow.getString(COL_URL),
        "Resolved Successfully");
  }

  public CTUrlListResponse listUrls() {
    Scanner scanner = this.originalUrlTable.scan(null, null);
    Row row = null;
    List<MappedUrl> urls = new ArrayList<MappedUrl>();
    while ((row = scanner.next()) != null) {
      String url = Bytes.toString(row.getRow());
      String shortname = row.getString(COL_SHORTNAME);
      urls.add(new MappedUrl(url, shortname));
    }
    scanner.close();
    return new CTUrlListResponse(urls);
  }

}
