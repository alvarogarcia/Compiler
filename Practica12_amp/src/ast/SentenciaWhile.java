package ast;

import java.util.ArrayList;
import java.util.List;

public class SentenciaWhile implements Sentencia {

	public int linea, columna;
	public List<Sentencia> sentencias = new ArrayList<Sentencia>();
	public Expresion condicion;
	public boolean lvalue;

	public SentenciaWhile(int linea, int columna, Expresion condicion,
			List<Sentencia> sentencias) {
		this.linea = linea;
		this.columna = columna;
		this.sentencias = sentencias;
		this.condicion = condicion;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	

	public String toString(){
		String mensaje = "Soy una sentencia While de condicion " + condicion.toString() + " y de sentencias "; 
		for(Sentencia sent : sentencias) mensaje += sent.toString();
		return mensaje;
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}
	
	@Override
	public int getLinea(){
		return this.linea;
	}
}
