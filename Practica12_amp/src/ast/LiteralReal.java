package ast;

public class LiteralReal implements Expresion {
	
	public int linea, columna;
	public double valor;
	public boolean lvalue;
	public Tipo tipo;
	
	public LiteralReal(int linea, int columna, double valor){
		this.linea = linea;
		this.columna = columna;
		this.valor = valor;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
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
