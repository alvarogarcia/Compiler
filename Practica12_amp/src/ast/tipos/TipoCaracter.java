package ast.tipos;

import ast.AbstractTipo;
import ast.Tipo;
import ast.TipoError;
import ast.Visitor;

public class TipoCaracter extends AbstractTipo {
	
	public static TipoCaracter tipoCaracter = new TipoCaracter();
	public boolean lvalue;
	
		
	private TipoCaracter(){
		
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	

	public String toString(){
		return "caracter";
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}

	@Override
	public boolean isLogic() {
		//return true;
		return false;
	}

	@Override
	public boolean isSimpleType() {
		return true;
	}
	
	@Override
	public Tipo aritmetica(Tipo tipo) {
		if(tipo instanceof TipoError){
			return tipo;
		} else if(tipo instanceof TipoReal || tipo instanceof TipoEntero){
			return tipo;
		} else if(tipo instanceof TipoCaracter){
			return TipoEntero.tipoEntero;
		} else 
			return null;
	}
	
	@Override
	public Tipo aritmetica(){
		return TipoEntero.tipoEntero;
	}
	
	
	
	@Override
	public Tipo promocionaA(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		else if (tipo instanceof TipoEntero || tipo instanceof TipoReal || tipo instanceof TipoCaracter)
			return tipo;
		else
			return null;
	}
	
	  @Override
	    public Tipo esCasteable(Tipo tipo){
	        if(tipo instanceof TipoError)
	            return tipo;
	        else if(tipo instanceof TipoReal || tipo instanceof TipoEntero)
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
		return 1;
	}

	@Override
	public char sufijo() {
		
		return 'b';
	}
	
	
	@Override
	public Tipo mayor(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		else if(tipo instanceof TipoCaracter)
			return TipoEntero.tipoEntero;
		else if(tipo instanceof TipoEntero || tipo instanceof TipoReal)
			return tipo;
		else
			return null;
	}
	
	@Override
	public Tipo promocionBidireccional(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		else if(tipo instanceof TipoCaracter || tipo instanceof TipoEntero || tipo instanceof TipoReal)
			return tipo;
		else
			return null;
	}
	 
}
