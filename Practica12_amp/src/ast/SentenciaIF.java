package ast;

import java.util.ArrayList;
import java.util.List;

public class SentenciaIF implements Sentencia {

	public int linea, columna;
	public List<Sentencia> cuerpoIF = new ArrayList<Sentencia>();
	public List<Sentencia> cuerpoELSE = new ArrayList<Sentencia>();
	public Expresion condicion;
	public boolean lvalue;
	
	public SentenciaIF(int linea, int columna, Expresion condicion, List<Sentencia> cuerpoIF, List<Sentencia> cuerpoELSE){
		this.linea = linea;
		this.columna = columna;
		this.cuerpoIF = cuerpoIF;
		this.cuerpoELSE = cuerpoELSE;
		this.condicion = condicion;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	
	public String toString(){
		return "Soy una sentencia IF";
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
