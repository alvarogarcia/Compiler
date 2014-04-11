package ast.tipos;

import ast.AbstractTipo;
import ast.Tipo;
import ast.TipoError;
import ast.Visitor;

public class TipoEntero extends AbstractTipo {

	public static TipoEntero tipoEntero = new TipoEntero();
	public boolean lvalue;

	private TipoEntero() {

	}

	public void accept(Visitor v, Object params) {
		v.visit(this, params);
	}

	public String toString() {
		return "entero";
	}

	@Override
	public boolean getLValue() {
		return lvalue;

	}

	@Override
	public boolean isLogic() {
		return true;
	}

	@Override
	public boolean isSimpleType() {
		return true;
	}

	@Override
	public Tipo aritmetica(Tipo tipo) {
		if (tipo instanceof TipoError) {
			return tipo;
		} else if (tipo instanceof TipoEntero || tipo instanceof TipoCaracter) {
			return this;
		} else if (tipo instanceof TipoReal) {
			return tipo;
		} else
			return null;
	}

	@Override
	public Tipo aritmetica() {
		return this;
	}

	@Override
	public Tipo esCasteable(Tipo tipo) {
		if (tipo instanceof TipoError || tipo instanceof TipoCaracter
				|| tipo instanceof TipoReal)
			return tipo;
		else if (tipo instanceof TipoEntero)
			return this;
		else
			return null;
	}

	

	@Override
	public Tipo promocionaA(Tipo tipo) {
		if (tipo instanceof TipoError)
			return tipo;
		else if (tipo instanceof TipoEntero || tipo instanceof TipoReal)
			return tipo;
		else
			return null;
	}
	
	@Override
	public Tipo esComparable(Tipo tipo){
		if (tipo instanceof TipoError)
			return tipo;
		else if(tipo instanceof TipoCaracter || tipo instanceof TipoEntero || tipo instanceof TipoReal)
			return TipoEntero.tipoEntero;
		else
			return null;
	}

	@Override
	public int numeroBytes() {
		return 2;
	}

	@Override
	public char sufijo() {
		return 'i';
	}
	
	@Override
	public Tipo mayor(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		else if(tipo instanceof TipoCaracter)
			return this;
		else if(tipo instanceof TipoEntero || tipo instanceof TipoReal)
			return tipo;
		else
			return null;
	}
	
	@Override
	public Tipo promocionBidireccional(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		if(tipo instanceof TipoCaracter)
			return this.tipoEntero;
		else if(tipo instanceof TipoEntero || tipo instanceof TipoReal)
			return tipo;
		else
			return null;
	}
	
	
}
