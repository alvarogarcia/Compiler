package ast;

public class OCondTernario implements Expresion {
	
	public int linea, columna;
	public Expresion condicional = null;
	public Expresion salidaCierto = null;
	public Expresion salidaFalso = null;
	public boolean lvalue;
	public Tipo tipo;

	public OCondTernario(int linea, int columna, Expresion condicional, Expresion salidaCierto, Expresion salidaFalso) {
		this.linea = linea;
		this.columna = columna;
		this.condicional = condicional;
		this.salidaCierto = salidaCierto;
		this.salidaFalso = salidaFalso;
	}

	public void accept(Visitor v, Object params) {
		v.visit(this, params);
	}

	@Override
	public String toString() {
		return condicional + " ? " + salidaCierto + " : " + salidaFalso;
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
