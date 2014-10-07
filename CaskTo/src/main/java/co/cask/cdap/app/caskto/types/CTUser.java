package co.cask.cdap.app.caskto.types;

public class CTUser {

  public static final long NO_ID = -1L;

  public long id;

  public String username;

  public String password;

  public String fullname;
  
  public CTUser(String username, String password, String fullname) {
    this(NO_ID, username, password, fullname);
  }

  public CTUser(long id, String username, String password, String fullname) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.fullname = fullname;
  }

  public boolean isValid() {
    return id != NO_ID;
  }

}
