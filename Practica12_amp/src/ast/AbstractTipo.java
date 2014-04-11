package ast;

import java.util.List;

import ast.tipos.TipoCaracter;
import ast.tipos.TipoEntero;
import ast.tipos.TipoReal;

public abstract class AbstractTipo implements Tipo {

	@Override
	public void accept(Visitor v, Object params) {

	}

	@Override
	public boolean getLValue() {
		return false;
	}

	@Override
	public boolean isLogic() {
		return false;
	}

	@Override
	public boolean isSimpleType() {
		return false;
	}

	@Override
	public Tipo aritmetica(Tipo tipo) {
		return null;
	}

	@Override
	public Tipo aritmetica() {
		return null;
	}

	@Override
	public Tipo esCasteable(Tipo tipo) {
		if (tipo instanceof TipoError)
			return tipo;
		else
			return null;
	}

	@Override
	public Tipo esComparable(Tipo tipo){
		
			return null;
	}
	
	@Override
	public Tipo parentesis(List<Expresion> argumentos){
		return null;
	}
	
	@Override
	public Tipo promocionaA(Tipo tipo){
		return null;
	}
	
	@Override
	public Tipo punto(String nombreCampo){
		return null;
	}
	
	@Override
	public Tipo corchetes(Tipo tipo){
		return null;
	}
	
	@Override
	public char sufijo(){
		throw new RuntimeException("No se puede pedir el sufijo de ese tipo");
	}
	
	@Override
	public Tipo mayor(Tipo tipo){
		return null;
	}
	
	@Override
	public Tipo promocionBidireccional(Tipo tipo){
		return null;
	}

}
