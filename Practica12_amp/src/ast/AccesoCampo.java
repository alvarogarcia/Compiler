package ast;

public class AccesoCampo implements Expresion {
	
	public int linea, columna;
	public Expresion nombreStruct;
	public String campo;
	public boolean lvalue;
	public Tipo tipo;
	
	public AccesoCampo(int linea, int columna, Expresion nombreStruct, String campo){
		this.linea = linea;
		this.columna = columna;
		this.nombreStruct = nombreStruct;
		this.campo = campo;
	}
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Soy un acceso a un campo del struct " + nombreStruct;
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
