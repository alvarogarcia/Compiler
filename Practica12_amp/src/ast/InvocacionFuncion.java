package ast;

import java.util.ArrayList;
import java.util.List;

public class InvocacionFuncion implements Expresion {
	
	public int linea, columna;
	public Variable funcion;
	public List<Expresion> argumentos = new ArrayList<Expresion>();
	public boolean lvalue;
	public Tipo tipo;
	
	public InvocacionFuncion(int linea, int columna, Variable funcion, List<Expresion> argumentos){
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
		return "Invocacion a una función con una variable " + funcion;
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
