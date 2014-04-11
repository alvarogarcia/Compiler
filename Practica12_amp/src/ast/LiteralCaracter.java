package ast;

public class LiteralCaracter implements Expresion {
	
	public int linea, columna;
	public char caracter;
	public boolean lvalue;
	public Tipo tipo;
	
	public LiteralCaracter(int linea, int columna, char caracter){
		this.linea = linea;
		this.columna = columna;
		this.caracter = caracter;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Caracter: " + caracter;
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
