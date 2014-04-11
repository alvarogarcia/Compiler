package ast.tipos;

import java.util.ArrayList;
import java.util.List;

import ast.AbstractTipo;
import ast.Campo;
import ast.Tipo;
import ast.TipoError;
import ast.Visitor;

public class TipoRegistro extends AbstractTipo {
	
	
	public List<Campo> campos = new ArrayList<Campo>();
	public boolean lvalue;
	
	public TipoRegistro(List<Campo> campos){
		this.campos = campos;
		
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "struct";
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}

	@Override
	public Tipo punto(String nombreCampo){
		for(Campo campo : campos){
			if(campo.nombre.equals(nombreCampo))
				return campo.tipo;
		}
		return null;
	}

	@Override
	public int numeroBytes() {
		int tam = 0;
		for(Campo campo : campos)
			tam += campo.tipo.numeroBytes();
		return tam;
	}
	
	@Override
	public Tipo promocionBidireccional(Tipo tipo){
		if(tipo instanceof TipoError)
			return tipo;
		else if(tipo instanceof TipoRegistro)
			return tipo;
		else
			return null;
	}

}
