package ast;

public class Variable implements Expresion {
	
	public int linea, columna;
	public String nombre;
	public boolean lvalue;
	public Definicion definicion;
	public Tipo tipo;
	

	
	public Variable(int linea, int columna, String nombre){
		this.linea = linea;
		this.columna = columna;
		this.nombre = nombre;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	


	
	@Override
	public String toString(){
		return nombre;
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
