package ast;

import java.util.List;

public interface Tipo extends NodoAST {

	public void accept(Visitor v, Object params);
	
	public boolean getLValue();
	
	public boolean isLogic();
	
	public boolean isSimpleType();
	
	public Tipo aritmetica(Tipo tipo);
	
	public Tipo aritmetica(); // Para el menosUnario
	
	public Tipo esCasteable(Tipo tipo);
	
	public Tipo esComparable(Tipo tipo);
	
	public Tipo parentesis(List<Expresion> argumentos);

	public Tipo promocionaA(Tipo tipo);
	
	public Tipo punto(String nombreCampo);
	
	public Tipo corchetes(Tipo tipo);
	
	public int numeroBytes();
	
	public char sufijo();
	
	public Tipo mayor(Tipo tipo);
	
	public Tipo promocionBidireccional(Tipo tipo);
}
