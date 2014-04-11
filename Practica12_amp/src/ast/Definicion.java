package ast;

public interface Definicion extends NodoAST {

	
	public String getNombre();

	public void setAmbito(int ambito);
	
	public int getAmbito();
	
	public Tipo getTipo();
	
	public void setTipo(Tipo tipo);
	
	public int getLinea();
	
	
}
