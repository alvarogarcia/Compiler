package ast;

public class LiteralEntero implements Expresion {
	
	public int valor, linea, columna;
	public boolean lvalue;
	public Tipo tipo;
	
	public LiteralEntero (int linea, int columna, int valor){
		this.linea = linea;
		this.columna = columna;
		this.valor = valor;
	}

	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	@Override
	public String toString(){
		return valor + "";
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
