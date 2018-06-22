package visualisierung.basis;

import vorgaben.Graph;
import vorgaben.Knoten;
import vorgaben.Zelle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import visualisierung.basis.Konstanten.Richtung;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Repräsentiert den Zustand des Spiels, primär das Spielfeld und dessen Zellen.
 *
 * @author Phiipp Jenke
 */
public class Level extends Observable implements Jsonable {

    private static final String ZELLEN = "zellen";
    private static final Object SPIELFIGUR = "spielfigur";
    private static final Object NPC = "NPC";
    private static final Object BOESEWICHTE = "boesewichte";
    private static final Object LINKS = "links";

    /**
     * Menge aller Zellen
     */
    private Set<Zelle> zellen = new HashSet<Zelle>();

    /**
     * Spieler.
     */
    private Spielfigur spielfigur = null;

    /**
     * NPC (muss gerettet werden)
     */
    private SpielfigurNPC spielfigurNPC;

    /**
     * Alle Boesewichte im Level.
     */
    private List<Boesewicht> boesewichte = new ArrayList<Boesewicht>();

    /**
     * Erzeugt ein neues Level mit der angegebenen Anzahl von Zeilen und Spalten.
     */
    public Level() {
    }

    public Iterator<Zelle> getZellenIterator() {
        return zellen.iterator();
    }

    /**
     * Zelle in Level einfügen.
     */
    public void zelleHinzufuegen(Zelle zelle) {
        zellen.add(zelle);
    }

    public void setSpielfigur(Spielfigur spielfigur) {
        this.spielfigur = spielfigur;
    }

    public Spielfigur getSpielfigur() {
        return spielfigur;
    }

    public SpielfigurNPC getSpielfigurNPC() {
        return spielfigurNPC;
    }

    public void setSpielfigurNPC(SpielfigurNPC npc) {
        this.spielfigurNPC = npc;
    }

    /**
     * Weiteren Boesewicht einfügen.
     */
    public void addBoesewicht(Boesewicht boesewicht) {
        boesewichte.add(boesewicht);
    }

    public void removeBoesewicht(Boesewicht boesewicht) {
        if (boesewicht != null) {
            boesewicht.getZelle().setAsset(null);
        }
        boesewichte.remove(boesewicht);
    }

    public Iterator<Boesewicht> getBoesewichteIterator() {
        return boesewichte.iterator();
    }

    /**
     * Liefert einen Iterator über alle Links (zu den Nachbarn).
     */
    public Iterator<Link> getLinkIterator() {
        Set<Link> alleLinks = new HashSet<Link>();
        for (Zelle zelle : zellen) {
            for (Richtung richtung : Richtung.values()) {
                Link link = zelle.getLink(richtung);
                if (link != null) {
                    alleLinks.add(link);
                }
            }
        }
        return alleLinks.iterator();
    }

    @SuppressWarnings("unchecked")
    @Override
    public JSONObject toJson(Object metaInformation) {
        JSONObject levelObjekt = new JSONObject();

        // Links
        JSONArray linkListe = new JSONArray();
        Map<Link, Integer> linkMap = new HashMap<Link, Integer>();
        int linkIndex = 0;
        for (Iterator<Link> it = getLinkIterator(); it.hasNext(); ) {
            Link link = it.next();
            linkMap.put(link, linkIndex);
            linkListe.add(link.toJson(null));
            linkIndex++;
        }
        levelObjekt.put(LINKS, linkListe);

        // Zellen
        JSONArray zellenListe = new JSONArray();
        for (Iterator<Zelle> it = getZellenIterator(); it.hasNext(); ) {
            Zelle zelle = it.next();
            zellenListe.add(zelle.toJson(linkMap));
        }
        levelObjekt.put(ZELLEN, zellenListe);

        // Spielfigur
        levelObjekt.put(SPIELFIGUR, getSpielfigur().toJson(null));

        // NPC
        levelObjekt.put(NPC, getSpielfigurNPC().toJson(null));

        // Boesewichte
        JSONArray boesewichteListe = new JSONArray();
        for (Iterator<Boesewicht> it = getBoesewichteIterator(); it.hasNext(); ) {
            Boesewicht boesewicht = it.next();
            boesewichteListe.add(boesewicht.toJson(null));
        }
        levelObjekt.put(BOESEWICHTE, boesewichteListe);

        return levelObjekt;
    }

    @Override
    public void fromJson(JSONObject jsonObjekt, Object metaInformation) {
        // Links
        JSONArray linksArray = (JSONArray) jsonObjekt.get(LINKS);
        List<Link> links = linksVonJson(linksArray);

        // Zellen
        JSONArray zellenArray = (JSONArray) jsonObjekt.get(ZELLEN);
        List<Zelle> zellen = zellenVonJson(zellenArray, links);
        for (Zelle zelle : zellen) {
            zelleHinzufuegen(zelle);
        }

        // Spielfigur
        JSONObject spielfigurObjekt = (JSONObject) jsonObjekt.get(SPIELFIGUR);
        Spielfigur spielfigur = new Spielfigur();
        spielfigur.fromJson(spielfigurObjekt, zellen);
        setSpielfigur(spielfigur);

        // NPC
        JSONObject npcObjekt = (JSONObject) jsonObjekt.get(NPC);
        SpielfigurNPC npc = new SpielfigurNPC();
        npc.fromJson(npcObjekt, zellen);
        setSpielfigurNPC(npc);

        // Boesewichte
        JSONArray boesewichteArray = (JSONArray) jsonObjekt.get(BOESEWICHTE);
        List<Boesewicht> boesewichte = boesewichteVonJson(boesewichteArray, zellen);
        for (Boesewicht boesewicht : boesewichte) {
            addBoesewicht(boesewicht);
        }
    }

    /**
     * JSON-Array mit Links entpacken.
     */
    private static List<Link> linksVonJson(JSONArray linksArray) {
        List<Link> links = new ArrayList<Link>();
        for (int i = 0; i < linksArray.size(); i++) {
            Object obj = linksArray.get(i);
            JSONObject linkObjekt = (JSONObject) obj;
            Link link = new Link();
            link.fromJson(linkObjekt, null);
            links.add(link);
        }
        return links;
    }

    /**
     * JSON-Array mit Boesewichten entpacken.
     */
    private static List<Boesewicht> boesewichteVonJson(JSONArray boesewichteArray,
                                                       List<Zelle> zellen) {
        List<Boesewicht> boesewichte = new ArrayList<Boesewicht>();
        for (int i = 0; i < boesewichteArray.size(); i++) {
            Object obj = boesewichteArray.get(i);
            JSONObject boesewichtObjekt = (JSONObject) obj;
            Boesewicht boesewicht = new Boesewicht();
            boesewicht.fromJson(boesewichtObjekt, zellen);
            boesewichte.add(boesewicht);
        }
        return boesewichte;
    }

    /**
     * JSON-Array mit Zellen entpacken.
     */
    private static List<Zelle> zellenVonJson(JSONArray zellenArray,
                                             List<Link> links) {
        List<Zelle> zellen = new ArrayList<Zelle>();
        for (int i = 0; i < zellenArray.size(); i++) {
            Object obj = zellenArray.get(i);
            JSONObject zellenObjekt = (JSONObject) obj;
            Zelle zelle = new Zelle(new Point(-1, -1));
            zelle.fromJson(zellenObjekt, links);
            zellen.add(zelle);
        }
        return zellen;
    }

    public void alleZellenUnsichtbar() {
        for (Zelle zelle : zellen) {
            zelle.setSichtbar(false);
        }
    }

    public void alleZellenSichtbar() {
        for (Zelle zelle : zellen) {
            zelle.setSichtbar(true);
        }
    }

    /**
     * Erzeugt einen Graphen für die Zellen des Spielfeldes.
     */
    public Graph<Zelle> erzeugeGraph() {
        Graph<Zelle> graph = new Graph<>();

        // Knoten
        Map<Zelle, Knoten<Zelle>> zelleKnotenMap = new HashMap<>();
        for (Zelle zelle : zellen) {
            Knoten<Zelle> knoten = new Knoten<>(zelle);
            zelleKnotenMap.put(zelle, knoten);
            graph.addKnoten(knoten);
        }

        // Kanten
        for (Zelle zelle : zellen) {
            for (Richtung richtung : Richtung.values()) {
                Zelle nachbarzelle = zelle.getNachbarZelle(richtung);
                if (nachbarzelle != null && !zelle.istWand(richtung)) {
                    graph.addKante(zelleKnotenMap.get(zelle), zelleKnotenMap.get(nachbarzelle), 1);
                }
            }
        }

        return graph;
    }

    /**
     * Liefert eine zufällige Zelle
     */
    public Zelle getZufallsZelle() {
        return new ArrayList<Zelle>(zellen).get((int) (Math.random() * zellen.size()));
    }
}
