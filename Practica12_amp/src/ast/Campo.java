package ast;

public class Campo implements NodoAST{
	
	int linea, columna;
	public String nombre;
	public Tipo tipo;
	public boolean lvalue;
	public int offset;
	
	public Campo(int linea, int columna, String nombre, Tipo tipo){
		this.linea = linea;
		this.columna = columna;
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Soy un campo de nombre " + nombre + " y tipo " + tipo;
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}

}
