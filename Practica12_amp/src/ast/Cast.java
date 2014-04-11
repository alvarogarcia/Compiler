package ast;

public class Cast implements Expresion {
	
	public int linea, columna;
	public Tipo tipoCast;
	public Expresion expresion;
	public boolean lvalue;
	public Tipo tipo;
	
	public Cast(int linea, int columna, Tipo tipoCast, Expresion expresion){
		this.linea = linea;
		this.columna = columna;
		this.tipoCast = tipoCast;
		this.expresion = expresion;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Soy un cast de tipo " + tipoCast + " sobre una expresión " + expresion;
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
