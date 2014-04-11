package ast;
import java.util.ArrayList;
import java.util.List;


public class Programa implements NodoAST {
	
	public int linea, columna;
	public List<Definicion> definiciones = new ArrayList<Definicion>();
	public boolean lvalue;
	
	public Programa (int linea, int columna, List<Definicion> definiciones){
		this.linea = linea;
		this.columna = columna;
		this.definiciones = definiciones;
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	

	
	
	@Override
	public String toString(){
		String mensaje = "";
		
		for (Definicion definicion : definiciones)
			mensaje += definicion.toString();
		
		return mensaje;
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}

}
