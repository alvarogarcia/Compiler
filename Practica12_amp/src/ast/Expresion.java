package ast;

public interface Expresion extends NodoAST {

	public void accept(Visitor v, Object params);
	
	public boolean getLValue();
	
	public Tipo getTipo();
	
	public void setTipo(Tipo tipo);
	

}
