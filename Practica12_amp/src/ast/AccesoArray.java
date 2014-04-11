package ast;

public class AccesoArray implements Expresion {

	public int linea, columna;
	public Expresion array, posicion;
	public boolean lvalue;
	public Tipo tipo;

	public AccesoArray(int linea, int columna, Expresion array,
			Expresion posicion) {
		this.linea = linea;
		this.columna = columna;
		this.array = array;
		this.posicion = posicion;
	}

	public void accept(Visitor v, Object params) {
		v.visit(this, params);
	}

	public String toString() {
		return "Acceso Array " + array + " en la posición " + posicion;
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
