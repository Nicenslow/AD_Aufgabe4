package vorgaben;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Daniel Biedermann, Katerina Milenkovski
 * Klasse zum berechnen des kuerzesten Pfades eine Graphens, mittels Dijkstra-Algorithmus
 */

public class KuerzesterPfadDijkstra implements KuerzesterPfad {

	private List<Integer> vorgaenger;
	private List<Double> kosten;
	private List<Boolean> flag;

	/**
	 * Konstruktor
	 */
	public KuerzesterPfadDijkstra() {
		vorgaenger = new ArrayList<>();
		kosten = new ArrayList<>();
		flag = new ArrayList<>();
	}
	/**
	 * Methode zum brechnen des kuerzesten Pfades mit Hilfe des Dijkstra Algorithmuses
	 * 
	 * @param graph Graph, startKnoten Startknoten des Pfades, zielKnoten Zielknoten des Pfades
	 * @return Liste mit Knoten die den Pfad representieren
	 */
	@Override
	public List<Knoten<Zelle>> berechneKuerzestenPfad(Graph<Zelle> graph, Knoten<Zelle> startKnoten,
			Knoten<Zelle> zielKnoten) {
		int startIndex = init(startKnoten, graph); // alle Knoten initialisieren und Index des Startknotens bekommen
		List<Integer> rand = randErstellen(startIndex, graph); // Rand erstellen
		int minKostenIndex = 0;

		while (!rand.isEmpty()) {
			minKostenIndex = findeMinKosten(rand); // Knotenindex des Knotens mit geringsten Kosten suchen													
			rand.remove(Integer.valueOf(minKostenIndex)); // entferne Knoten mit geringsten Kosten
			if (graph.getKnoten(minKostenIndex).equals(zielKnoten)) {
				return getPath(minKostenIndex, startIndex, graph); // am Ziel angelagt & Pfad des Ziels angeben															
			} else {
				flag.set(minKostenIndex, true);
				ergaenzeRand(minKostenIndex, rand, graph);
			}
		}
		return Arrays.asList(startKnoten, zielKnoten);
	}	
	/**
	 *  Hilfsmethode zum Initialisieren aller Knotenelemente des Graphen
	 *  
	 *  @param start Startknoten des Graphen, graph Graph
	 *  @return Index des Startknotens
	 */
	private int init(Knoten<Zelle> start, Graph<Zelle> graph) {
		int startIndex = 0;

		for (int i = startIndex; i < graph.getAnzahlKnoten(); i++) {
			if (graph.getKnoten(i).equals(start)) { // Startknoten wird initialisiert						
				kosten.add(i, 0.0);
				vorgaenger.add(i, startIndex);
				flag.add(i, true);
				startIndex = i; // Index des Startknotens merken
			} else {
				kosten.add(i, Double.MAX_VALUE);
				vorgaenger.add(i, null);
				flag.add(i, false);
			}
		}
		return startIndex; // Startknotenindex zurückgeben
	}

	// Hilfsmethode, die Rand erstellt und als Liste mit Indizes der Knoten, zu
	// die der Knoten am Startindex eine Kante hat, zurueckgibt
	private List<Integer> randErstellen(int startIndex, Graph<Zelle> graph) {
		List<Integer> rand = graph.getNachbarIndices(startIndex);
		for (int i = 0; i < rand.size(); i++) {
			int randIndex = rand.get(i);
			kosten.set(randIndex, graph.getKantenGewicht(startIndex, randIndex));
		}
		return rand;
	}

	// Hilfsmethode zum finden der minimalen Kosten. sucht im Rand nach Index
	// mit minimalen Kosten und gibt diesen zurück.
	private int findeMinKosten(List<Integer> rand) {
		int minKnoten = -1;
		double minKosten = Double.MAX_VALUE;

		for (int i = 0; i < rand.size(); i++) {
			int randknotenIndex = rand.get(i);
			double randKnotenKosten = kosten.get(randknotenIndex);
			if (randKnotenKosten < minKosten) {
				minKnoten = randknotenIndex;
				minKosten = randKnotenKosten;
			}
		}
		return minKnoten;
	}

	// Hilfsmethode zum befüllen des Randes. alle adjazenten Knoten, die nicht
	// bereits besucht wurden, werden auf geringste Kosten geprüft
	private void ergaenzeRand(int indexMinKosten, List<Integer> rand, Graph<Zelle> graph) {
		List<Integer> adjazenzVonMin = graph.getNachbarIndices(indexMinKosten);
		for (int i = 0; i < adjazenzVonMin.size(); i++) {
			int adjazenzKnotenIndex = adjazenzVonMin.get(i);

			// alle nicht-markierten Knoten K im Rand
			if (!flag.get(adjazenzKnotenIndex)) {
				double alternativeKosten = kosten.get(indexMinKosten)
						+ graph.getKantenGewicht(indexMinKosten, adjazenzKnotenIndex);
				if (kosten.get(adjazenzKnotenIndex) > alternativeKosten) { // wenn alternativer Weg kuerzer
					kosten.set(adjazenzKnotenIndex, alternativeKosten); // setze Kosten vom Knoten
					vorgaenger.set(adjazenzKnotenIndex, indexMinKosten); // aktualisiere Vorgaenger vom Knoten
				}
				rand.add(adjazenzKnotenIndex); // zum Rand hinzufuegen
			}
		}
	}

	// Hilfsmethode zum Finden des zurückgelegten Weges 
	// Dabei wird vom ZielKnoten ueber Vorgaenger  zum Startknoten in die Liste gefuegt
	private List<Knoten<Zelle>> getPath(int zielKnotenIndex, int startKnotenIndex, Graph<Zelle> graph) {
		List<Knoten<Zelle>> path = new ArrayList<>();
		int naechsterKnoten = zielKnotenIndex;
		while (vorgaenger.get(naechsterKnoten) != null) {
			path.add(graph.getKnoten(naechsterKnoten));
			naechsterKnoten = vorgaenger.get(naechsterKnoten);
		}
		path.add(graph.getKnoten(startKnotenIndex));
		return path;
	}

}
