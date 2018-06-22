package visualisierung.basis;

import vorgaben.Zelle;
import visualisierung.basis.Konstanten.SpielStatus;

import java.util.Observable;

/**
 * Aktueller Zustand des Editors.
 *
 * @author Philipp Jenke
 */
public class SpielZustand extends Observable {
    /**
     * Aktuell ausgewählte Zelle.
     */
    private Zelle aktuelleZelle = null;

    /**
     * Startzelle für die Pfadsuche
     */
    private Zelle startZelle = null;

    /**
     * Zielzelle für die Pfadsuche
     */
    private Zelle zielZelle = null;

    /**
     * Aktueller Level
     */
    private Level aktuellerLevel = new Level();

    /**
     * Aktuell Status des Spiels
     */
    private SpielStatus spielStatus = null;

    /**
     * Flags, die den Renderer steuern können. Gesetzt über
     * Konstanten.RenderFlags.
     */
    private int renderFlags;

    /**
     * Singleton Instance
     */
    private static SpielZustand instance = null;

    private SpielZustand() {
    }

    public static SpielZustand getInstance() {
        if (instance == null) {
            instance = new SpielZustand();
        }
        return instance;
    }

    public Zelle getAktuelleZelle() {
        return aktuelleZelle;
    }

    public void setAktuelleZelle(Zelle aktuelleZelle) {
        this.aktuelleZelle = aktuelleZelle;
        setChanged();
        notifyObservers();
    }

    public Level getAktuellerLevel() {
        return aktuellerLevel;
    }

    public void setAktuellerLevel(Level level) {
        aktuellerLevel = level;
        aktuelleZelle = null;
        setChanged();
        notifyObservers();
    }

    /**
     * Setzen eines der Renderflags aus Konstanten.
     *
     * @param flag
     */
    public void setRenderFlag(int flag) {
        renderFlags = renderFlags | flag;
    }

    public int getRenderFlags() {
        return renderFlags;
    }

    public SpielStatus getSpielStatus() {
        return spielStatus;
    }

    public void setSpielStatus(SpielStatus spielStatus) {
        this.spielStatus = spielStatus;
    }

    public void setStartZelle(Zelle zelle) {
        this.startZelle = zelle;
    }

    public void setZielZelle(Zelle zelle) {
        this.zielZelle = zelle;
    }

    public Zelle getStartZelle() {
        return startZelle;
    }

    public Zelle getZielZelle() {
        return zielZelle;
    }
}
