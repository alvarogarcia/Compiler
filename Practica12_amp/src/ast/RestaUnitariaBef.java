package ast;

public class RestaUnitariaBef implements Expresion, Sentencia {

	public int linea, columna;
	public Expresion expresion = null;
	public boolean lvalue;
	public Tipo tipo;

	public RestaUnitariaBef(int linea, int columna, Expresion expresion) {
		this.linea = linea;
		this.columna = columna;
		this.expresion = expresion;
	}

	public void accept(Visitor v, Object params) {
		v.visit(this, params);
	}

	@Override
	public String toString() {
		return "--" + expresion;
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

	@Override
	public int getLinea() {
		return linea;
	}

}
