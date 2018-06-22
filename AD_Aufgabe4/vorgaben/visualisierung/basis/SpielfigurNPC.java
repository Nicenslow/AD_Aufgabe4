package visualisierung.basis;

import vorgaben.Zelle;
import visualisierung.assets.Assets;
import org.json.simple.JSONObject;

/**
 * Die Spielfigur, die vom Anwender gesteuert wird.
 * 
 * @author Philipp Jenke
 */
public class SpielfigurNPC extends Asset {

  public SpielfigurNPC() {
    super(Assets.AssetTyp.NPC);
  }

  @Override
  public void setZelle(Zelle zelle) {
    // Von alter Zelle entfernen.
    if (this.zelle != null) {
      this.zelle.setAsset(null);
    }
    super.setZelle(zelle);
  }

  @Override
  public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
    if (jsonObjekt == null) {
      return;
    }
    super.fromJson(jsonObjekt, metaInformation);
    setZelle(zelle);
  }
}
