package ast.tipos;

import ast.AbstractTipo;
import ast.Tipo;
import ast.TipoError;
import ast.Visitor;

public class TipoArray extends AbstractTipo {

	public int tamanio;
	public Tipo tipoArray;
	public boolean lvalue;

	public TipoArray(int tamanio, Tipo tipoArray) {
		this.tamanio = tamanio;
		this.tipoArray = tipoArray;

		int tamTemp = tamanio;
		TipoArray tipoTemp = this;

		while (tipoTemp.tipoArray instanceof TipoArray) {
			tipoTemp.tamanio = ((TipoArray) tipoTemp.tipoArray).tamanio;
			((TipoArray) tipoTemp.tipoArray).tamanio = tamTemp;

			if (tipoTemp.tipoArray instanceof TipoArray) {
				tipoTemp = (TipoArray) tipoTemp.tipoArray;
				tamTemp = tipoTemp.tamanio;
			} else
				break;
		}

	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	

	public String toString() {
		return "array";
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}
	

	 @Override
	    public Tipo corchetes(Tipo tipo) {
	        if(tipo instanceof TipoEntero || tipo instanceof TipoCaracter)
	            return this.tipoArray;
	        return null;
	    }

	@Override
	public int numeroBytes() {
		return (tamanio * tipoArray.numeroBytes());
	}
	
	
	@Override
	public Tipo promocionBidireccional(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		else if(tipo instanceof TipoArray)
			return tipo;
		else
			return null;
	}
	
	


}
