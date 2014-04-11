package ast;

import java.util.List;

import ast.tipos.TipoCaracter;
import ast.tipos.TipoEntero;
import ast.tipos.TipoReal;

public class TipoError extends AbstractTipo 
{

	public String mensaje;
	public NodoAST nodo;
	public boolean lvalue;
	
	public TipoError (String mensaje, NodoAST nodo){
		this.mensaje = mensaje;
		this.nodo = nodo;
		ME.mE.addError(this);
	}
	
	public TipoError(){}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "ERROR: " + mensaje;
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}
	
	@Override
	public Tipo aritmetica(Tipo tipo) {
		return null;
	}

	@Override
	public Tipo aritmetica() {
		return this;
	}

	@Override
	public Tipo esCasteable(Tipo tipo) {
		return this;
	}

	@Override
	public Tipo esComparable(Tipo tipo){
		return this;
	}
	
	@Override
	public Tipo parentesis(List<Expresion> argumentos){
		return this;
	}
	
	@Override
	public Tipo promocionaA(Tipo tipo){
		return this;
	}
	
	@Override
	public Tipo punto(String nombre){
		return this;
	}

	@Override
	public int numeroBytes() {
		throw new RuntimeException("ERROR! No se deberían encontrar errores en la fase de generación de Código!");
	}

	@Override
	public Tipo promocionBidireccional(Tipo tipo) {
		return this;
	}
	
}
