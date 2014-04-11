package ast;

public class Asignacion implements Sentencia {
	
	public int linea, columna;
	public Expresion expresion1;
	public Expresion expresion2;
	public boolean lvalue;
	
	public Asignacion (int linea, int columna, Expresion expresion1, Expresion expresion2){
		this.linea = linea;
		this.columna = columna;
		this.expresion1 = expresion1;
		this.expresion2 = expresion2;
	}

	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	@Override
	public String toString(){
		return expresion1.toString() + " = " + expresion2.toString(); 
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
