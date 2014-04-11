package generadorCodigo;

import ast.AccesoArray;
import ast.AccesoCampo;
import ast.Campo;
import ast.DefVariable;
import ast.Variable;
import ast.tipos.TipoArray;
import ast.tipos.TipoEntero;
import ast.tipos.TipoRegistro;

public class VisitorGCDireccion extends VisitorDefaultErrores {
	
	
	
	@Override
	public Object visit(Variable variable, Object params) {

		if(variable.definicion.getAmbito() == 0)
			GeneradorCodigo.pusha(((DefVariable) variable.definicion).offset);
		else{
			GeneradorCodigo.pushbp();
			GeneradorCodigo.push(((DefVariable)variable.definicion).offset);
			GeneradorCodigo.add(TipoEntero.tipoEntero);
		}
		return null;
			
	}
	
	@Override
	public Object visit(AccesoCampo accesoCampo, Object params){
		accesoCampo.nombreStruct.accept(this, params);
		for(Campo c : ((TipoRegistro) accesoCampo.nombreStruct.getTipo()).campos)
			if(c.nombre.equals(accesoCampo.campo)){
				GeneradorCodigo.push(c.offset);
				break;
			}
		
		GeneradorCodigo.add(TipoEntero.tipoEntero);
		
		return null;
	}
	
	public Object visit(AccesoArray accesoArray, Object params){
		accesoArray.array.accept(this, params);
		accesoArray.posicion.accept(new VisitorGCValor(), params);
		GeneradorCodigo.convertirA(accesoArray.posicion.getTipo(), TipoEntero.tipoEntero);
		GeneradorCodigo.push(((TipoArray) accesoArray.array.getTipo()).tipoArray.numeroBytes());
		GeneradorCodigo.mul(TipoEntero.tipoEntero);
		GeneradorCodigo.add(TipoEntero.tipoEntero);
		
		return null;
	}

}
