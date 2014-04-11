package ast.tipos;

import ast.AbstractTipo;
import ast.Tipo;
import ast.TipoError;
import ast.Visitor;

public class TipoVoid extends AbstractTipo {
	
	public static TipoVoid tipoVoid = new TipoVoid();
	public boolean lvalue;
	
	private TipoVoid(){
		
	}
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	public String toString(){
		return "void";
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
	public int numeroBytes() {
		return 0;
	}
	
	


}
