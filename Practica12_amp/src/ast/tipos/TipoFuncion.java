package ast.tipos;

import java.util.ArrayList;
import java.util.List;

import ast.AbstractTipo;
import ast.DefVariable;
import ast.Expresion;
import ast.Tipo;
import ast.TipoError;
import ast.Visitor;

public class TipoFuncion extends AbstractTipo {
	
	public Tipo retorno;
	public List<DefVariable> defVariables = new ArrayList<DefVariable>(); //parámetros de la función
	public boolean lvalue;
	
	public TipoFuncion(Tipo retorno, List<DefVariable> defVariables){
		this.retorno = retorno;
		if(this.defVariables != null){
            for (int i = defVariables.size() - 1; i >= 0; i--)
                this.defVariables.add(defVariables.get(i));
		}
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	

	public String toString(){
		return "funcion";
	}

	@Override
	public boolean getLValue() {
		return lvalue;
	}
	
	@Override
	public Tipo parentesis(List<Expresion> argumentos){
		if(argumentos.size() != defVariables.size())
			return null;
		for (int i = 0; i < argumentos.size(); i++){
			argumentos.get(i).setTipo(argumentos.get(i).getTipo().promocionaA(defVariables.get(i).getTipo()));
			 if(argumentos.get(i).getTipo() == null || argumentos.get(i).getTipo() instanceof TipoError)
	                return argumentos.get(i).getTipo();
			
		}
		
		return this.retorno;
	}

	@Override
	public int numeroBytes() {
		return retorno.numeroBytes();
	}
	
	


}
