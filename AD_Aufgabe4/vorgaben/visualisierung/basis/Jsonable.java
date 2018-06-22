package visualisierung.basis;

import org.json.simple.JSONObject;

/**
 * Gemeinsames Interface für alle Klassen, die über eine JSON-IO-Schnittstelle
 * verfügen
 * 
 * @author Philipp Jenke
 *
 */
public interface Jsonable {

  /**
   * Generieren eines JSON-Objektes aus der Instanz.
   * 
   * @param meta
   *          If required: Meta information for the JSON creation process.
   *          Otherwise: null.
   */
  public JSONObject toJson(Object metaInformation);

  /**
   * Befüllen des Zustands der Instanz aus einem JSON-Objekt.
   * 
   * @param meta
   *          If required: Meta information for the JSON creation process.
   *          Otherwise: null.
   */
  public void fromJson(JSONObject jsonObjekt, Object metaInformation);
}
