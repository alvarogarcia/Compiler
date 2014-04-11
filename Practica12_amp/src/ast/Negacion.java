package ast;

public class Negacion implements Expresion {
	
	public int linea, columna;
	public Expresion expresion;
	public boolean lvalue;
	public Tipo tipo;
	
	public Negacion(int linea, int columna, Expresion expresion){
		this.linea = linea;
		this.columna = columna;
		this.expresion = expresion;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Soy una negación de una expresión " + expresion;
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
