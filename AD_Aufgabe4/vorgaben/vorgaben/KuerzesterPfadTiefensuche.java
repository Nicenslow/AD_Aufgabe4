package vorgaben;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class KuerzesterPfadTiefensuche implements KuerzesterPfad {

	private List<Knoten<Zelle>> benutzteKnoten;
	private Stack<Knoten<Zelle>> stack;

	public KuerzesterPfadTiefensuche() {
		benutzteKnoten = new ArrayList<>();
		stack = new Stack<Knoten<Zelle>>();
	}

	/**
	 * Liefert die Liste der Knoten auf dem kuerzesten Pfad vom Start zum Ziel.
	 *
	 * @param graph  Graph mit Knoten und gewichteten Kanten, startKnoten Startknoten,zielKnoten Zielknoten
	 *   
	 * @return Liste mit Knoten den den kuerzesten Pfad repräsentieren          
	 */
	@Override
	public List<Knoten<Zelle>> berechneKuerzestenPfad(Graph<Zelle> graph, Knoten<Zelle> startKnoten,
			Knoten<Zelle> zielKnoten) {
		List<Knoten<Zelle>> ausgabeListe = new ArrayList<>();
		ausgabeListe.add(startKnoten);
		Comparator<Knoten<Zelle>> comparator = Comparator.comparingDouble(k -> k.getElement().getEntfernung(zielKnoten.getElement()));
		List<Knoten<Zelle>> nachbarn = graph.getNachbarn(startKnoten);

		// Objekt ist gefunden
		if (nachbarn.contains(zielKnoten) || startKnoten.equals(zielKnoten)) {
			ausgabeListe.add(zielKnoten);
			cleanTiefensuche();
			return ausgabeListe;
		}
		benutzteKnoten.add(startKnoten); // Knoten markieren
		nachbarn = entferneBenutzte(nachbarn); // Element auf Stack ablegen
		nachbarn.sort(comparator.reversed()); // nach dem Entfernnung sortieren
		stack.addAll(nachbarn);

		while(!stack.isEmpty() && nachbarEnthalten(nachbarn, startKnoten)) { // Pfad rekursiv durchlaufen
			List<Knoten<Zelle>> temp = berechneKuerzestenPfad(graph, stack.pop(), zielKnoten);
			if (temp != null) {
				if (temp.size() > 1) { // Rekursion erfolgreich, wenn mehr als ein Element vorhanden!!
					ausgabeListe.addAll(temp);
					return ausgabeListe;
				}
			}
		}
		return null; // kein passender Nachbar im Graph gefunden
	}

	private List<Knoten<Zelle>> entferneBenutzte(List<Knoten<Zelle>> input) {
		List<Knoten<Zelle>> neueListe = new ArrayList<>();
		for (Knoten<Zelle> element : input) { // prüfen ob Element bereits vorhanden
			if (benutzteKnoten.contains(element)) {
				continue;
			}
			neueListe.add(element);
		}
		return neueListe;
	}

	private void cleanTiefensuche() {
		// Werte der benutzen Knoten und des Stacks resetten
		benutzteKnoten = new ArrayList<>();
		stack = new Stack<Knoten<Zelle>>();
	}

	private boolean nachbarEnthalten(List<Knoten<Zelle>> liste, Knoten<Zelle> start) {
		if (liste.contains(stack.peek()) && stack.peek() != start) {
			return true; // also wenn Nachbar in Liste vorhanden, dann true returnen
		}
		return false;
	}

}
