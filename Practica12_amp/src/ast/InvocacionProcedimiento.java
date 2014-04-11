package ast;

import java.util.ArrayList;
import java.util.List;

public class InvocacionProcedimiento implements Sentencia {
	
	public int linea, columna;
	public List<Expresion> argumentos = new ArrayList<Expresion>();
	public Variable funcion;
	public boolean lvalue;
	
	public InvocacionProcedimiento(int linea, int columna, Variable funcion, List<Expresion> argumentos){
		this.linea = linea;
		this.columna = columna;
		this.funcion = funcion;
		if(argumentos != null && argumentos.size() > 0)
			for(int i = argumentos.size() - 1; i >= 0; i--)
				this.argumentos.add(argumentos.get(i));
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "Soy una invocaci�n";
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
