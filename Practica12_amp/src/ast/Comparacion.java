package ast;

public class Comparacion implements Expresion {
	
	public int linea, columna;
	public Expresion expresion1, expresion2;
	public String operador;
	public boolean lvalue;
	public Tipo tipo;
	
	public Comparacion(int linea, int columna, Expresion expresion1, String operador, Expresion expresion2){
		this.linea = linea;
		this.columna = columna;
		this.expresion1 = expresion1;
		this.operador = operador;
		this.expresion2 = expresion2;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Soy una comparación entre las expresiones " + expresion1 + " y " + expresion2;
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}

	@Override
	public Tipo getTipo() {
		return tipo;
	}

	@Override
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
