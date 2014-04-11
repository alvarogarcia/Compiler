package semantico;

import java.util.HashMap;
import java.util.Map;

import ast.AccesoCampo;
import ast.Campo;
import ast.DefVariable;
import ast.DefinicionFuncion;
import ast.OCondTernario;
import ast.Sentencia;
import ast.TipoError;
import ast.Variable;
import ast.tipos.TipoFuncion;
import ast.tipos.TipoRegistro;
import tablasimbolos.TablaSimbolos;

public class VisitorIdentificacion extends VisitorDefault {

	TablaSimbolos ts = new TablaSimbolos();

	@Override
	public Object visit(DefinicionFuncion definicionFuncion, Object params) {
		if (!ts.insertar(definicionFuncion))
			new TipoError("Variable ya definida en línea "
					+ definicionFuncion.linea + " y columna "
					+ definicionFuncion.columna + "\n", definicionFuncion);
		else {
			ts.set();
			for(DefVariable param : ((TipoFuncion) definicionFuncion.tipo).defVariables){
				param.accept(this, params);
			}
			for (Sentencia sent : definicionFuncion.sentencias) {
				sent.accept(this, params);/*
										 * if (sent instanceof DefVariable) if
										 * (!ts.insertar((DefVariable) sent))
										 * new TipoError(
										 * "Variable ya definida en la función de nombre "
										 * + definicionFuncion.nombre +
										 * ", línea " + definicionFuncion.linea
										 * + " y columna " +
										 * definicionFuncion.columna,
										 * (DefVariable) sent);
										 */

			}
			
			ts.reset();
		}

		return null;
	}

	@Override
	public Object visit(DefVariable definicionVariable, Object params) {
		definicionVariable.tipo.accept(this, params);
		if (!ts.insertar(definicionVariable))
			new TipoError("Variable ya definida en línea "
					+ definicionVariable.linea + " y columna "
					+ definicionVariable.columna + "\n", definicionVariable);

		return null;
	}

	@Override
	public Object visit(Variable variable, Object params) {
		
		variable.definicion = ts.buscar(variable.nombre);
		if (params != null) {
			
			boolean tieneCampo = false;
			

				if (variable.definicion.getTipo().punto((String)params) != null) // si tiene campo el método punto devolvería el tipo de su campo y por lo tanto existe.
					tieneCampo = true;
			
			if (!tieneCampo)
				new TipoError(
						"El Struct no tiene un campo con ese nombre. Error en la línea "
								+ variable.linea + " y columna "
								+ variable.columna + ".\n", variable);
		} 
			

		if (variable.definicion == null) {
			new TipoError("Variable utilizada en línea " + variable.linea
					+ " y columna " + variable.columna + " no definida \n",
					variable);
		}

		return null;

	}

	@Override
	public Object visit(AccesoCampo accesoCampo, Object params) {
		accesoCampo.nombreStruct.accept(this, accesoCampo.campo);

		return null;
	}
	
	
	@Override
	public Object visit(TipoRegistro tipoRegistro, Object params){
		
		Map<String, Campo> comprobacionCamposDuplicados = new HashMap<String, Campo>();
		for(Campo campo : tipoRegistro.campos){
		if(comprobacionCamposDuplicados.get(campo.nombre) == null){ // No existe el campo y por lo tanto no estará duplicado
			comprobacionCamposDuplicados.put(campo.nombre, campo);
		} else
			campo.tipo = new TipoError("ERROR: Campos duplicados.", tipoRegistro);
		}
		
		return null;
	}
	


	/**
	 * Para los structs tengo de opciones: 1. Si en cada DefVariable pregunto si
	 * el tipo es una instancia de TipoRegistro que se añadan todos los tipos =>
	 * PROBLEMA: ts.insertar(Definicion y no Campo) y además no podría definir
	 * una variable si el campo se llama igual (puedo ponerle de nombre
	 * nombrestruct.nombrecampo pero como se crea la variable en el campo en el
	 * sinj.y cuando se tiene ID.expresion (siendo expresion un ID) no se sabe
	 * el nombre del struct
	 * 
	 * 2. Eliminar el método visit (no cascaría, pero no estaría comprobando si
	 * hay problemas
	 */

}
