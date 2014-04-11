package ast;

public class Return implements Sentencia {

	public int linea, columna;
	public Expresion retorno;
	public boolean lvalue;
	
	public Return(int linea, int columna, Expresion retorno){
		this.linea = linea;
		this.columna = columna;
		this.retorno = retorno;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Soy un Return que retorno " + retorno;
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}
	
	@Override
	public int getLinea(){
		return this.linea;
	}
}
