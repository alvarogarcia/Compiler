package tablasimbolos;

import java.util.*;

import ast.Definicion;

public class TablaSimbolos {

	private int ambito = 0;
	private List<Map<String, Definicion>> tabla = new ArrayList<Map<String, Definicion>>();

	public TablaSimbolos() {
		tabla.add(new HashMap<String, Definicion>());
	}

	public void set() {
		ambito++;
		tabla.add(new HashMap<String, Definicion>());
	}

	public void reset() {
		tabla.remove(ambito);
		ambito--;
	}

	public boolean insertar(Definicion simbolo) {
		try {
			if (buscarAmbitoActual(simbolo.getNombre()) == null) {
				simbolo.setAmbito(ambito);
				tabla.get(ambito).put(simbolo.getNombre(), simbolo);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public Definicion buscar(String id) {
		Definicion def;
		/* comprobamos si existe una variable desde el ámbito mayor al menor */
		for(int i = tabla.size() - 1; i >= 0; i--){
			if((def = tabla.get(i).get(id))!= null)
				return def;
		}
		return null;
	}

	public Definicion buscarAmbitoActual(String id) {
		return tabla.get(ambito).get(id);
	}
}
