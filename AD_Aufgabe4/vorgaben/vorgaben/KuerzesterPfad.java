package vorgaben;

import visualisierung.basis.Konstanten;

import java.awt.*;
import java.util.List;

/**
 * Gemeinsames Interface für Algorithmen, die kürzeste Pfade berechnen.
 */
public interface KuerzesterPfad {
    /**
     * Liefert die Liste der Knoten auf dem kürzesten Pfad vom Start zum Ziel.
     *
     * @param graph       Graph mit Knoten und gewichteten Kanten
     * @param startKnoten Startknoten
     * @param zielKnoten  Zielknoten
     */
    public abstract List<Knoten<Zelle>> berechneKuerzestenPfad(Graph<Zelle> graph,
                                                               Knoten<Zelle> startKnoten, Knoten<Zelle> zielKnoten);


}
