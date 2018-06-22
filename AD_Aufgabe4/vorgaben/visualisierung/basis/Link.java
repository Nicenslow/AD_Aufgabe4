package visualisierung.basis;

import vorgaben.Zelle;
import org.json.simple.JSONObject;

/**
 * Verbindung zwischen zwei Zellen.
 * 
 * @author Philipp Jenke
 */
public class Link implements Jsonable {

  private static final Object IST_WAND = "istWand";

  /**
   * Die beiden Zellen, die durch den Link verbunden sind.
   */
  private Zelle[] zellen = new Zelle[2];

  /**
   * Flag für Wand-Verbindungen.
   */
  private boolean istWand = false;

  public Link() {
    zellen[0] = null;
    zellen[1] = null;
  }

  public Link(Zelle zelle1, Zelle zelle2) {
    zellen[0] = zelle1;
    zellen[1] = zelle2;
  }

  /**
   * Liefert die andere Zelle (als die Parameter-Zelle).
   */
  public Zelle getAndereZelle(Zelle zelle) {
    if (zellen[0] == zelle) {
      return zellen[1];
    } else {
      return zellen[0];
    }
  }

  public void setWand(boolean hatWand) {
    istWand = hatWand;
  }

  public boolean istWand() {
    return istWand;
  }

  @SuppressWarnings("unchecked")
  @Override
  public JSONObject toJson(Object metaInformation) {
    JSONObject linkObjekt = new JSONObject();
    linkObjekt.put(IST_WAND, istWand());
    return linkObjekt;
  }

  @Override
  public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
    boolean istWand = (boolean) jsonObjekt.get(IST_WAND);
    setWand(istWand);
  }

  /**
   * Setzt die angegebene Zelle als eine der Zellen des Links
   */
  public void setZelle(Zelle zelle) {
    if (zellen[0] == null) {
      zellen[0] = zelle;
    } else if (zellen[1] == null) {
      zellen[1] = zelle;
    } else {
      System.out.println("Link hat bereits zwei Zellen!");
    }
  }
}
