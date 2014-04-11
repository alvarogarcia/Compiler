package ast;

import java.util.ArrayList;
import java.util.List;

public class DefinicionFuncion implements Definicion {

	public String nombre;
	public Tipo tipo;
	public List<Sentencia> sentencias = new ArrayList<Sentencia>();
	public int linea, columna;
	public boolean lvalue;
	public int ambito;
	public int offsetVarsLocales;
	
	
	public DefinicionFuncion(int linea, int columna, String nombre, Tipo tipo, List<Sentencia> sentencias){
		this.linea = linea;
		this.columna = columna;
		this.nombre = nombre;
		this.sentencias = sentencias;
		this.tipo = tipo;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Soy una funci�n de nombre "  + nombre + " y tipo " + tipo;
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setAmbito(int ambito) {
		this.ambito = ambito;
	}

	@Override
	public int getAmbito() {
		return ambito;
	}

	@Override
	public Tipo getTipo() {
		
		return tipo;
	}

	@Override
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	@Override
	public int getLinea(){
		return this.linea;
	}

	
}
