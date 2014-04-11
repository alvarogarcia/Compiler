package ast.tipos;

import ast.AbstractTipo;
import ast.Tipo;
import ast.TipoError;
import ast.Visitor;

public class TipoReal extends AbstractTipo {

	public static TipoReal tipoReal = new TipoReal();
	public boolean lvalue;

	private TipoReal() {

	}

	public void accept(Visitor v, Object params) {
		v.visit(this, params);
	}

	public String toString() {
		return "real";
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}

	@Override
	public boolean isSimpleType() {
		return true;
	}
	
	@Override
	public Tipo aritmetica(Tipo tipo) {
		if(tipo instanceof TipoError){
			return tipo;
		} else if(tipo instanceof TipoEntero || tipo instanceof TipoCaracter || tipo instanceof TipoReal){
			return this;
		}  else 
			return null;
	}
	
	@Override
	public Tipo aritmetica(){
		return this;
	}

	@Override
    public Tipo esCasteable(Tipo tipo) {
        if (tipo instanceof TipoError)
            return tipo;
        else if (tipo instanceof TipoEntero)
            return TipoEntero.tipoEntero;
        else if (tipo instanceof TipoCaracter)
            return TipoCaracter.tipoCaracter;
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
	public Tipo promocionaA(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		//else if (tipo instanceof TipoEntero || tipo instanceof TipoReal || tipo instanceof TipoCaracter)
		else if (tipo instanceof TipoReal)
			return this;		
		else
			return null;
	}

	@Override
	public int numeroBytes() {
		return 4;
	}

	@Override
	public char sufijo() {
		
		return 'f';
	}
	
	@Override
	public Tipo mayor(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		else if(tipo instanceof TipoCaracter || tipo instanceof TipoEntero || tipo instanceof TipoReal)
			return this;
		else
			return null;
	}
	
	@Override
	public Tipo promocionBidireccional(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		else if(tipo instanceof TipoCaracter || tipo instanceof TipoEntero || tipo instanceof TipoReal)
			return this.tipoReal;
		else
			return null;
	}
	
}
