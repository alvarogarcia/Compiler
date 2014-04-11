package generadorCodigo;

import java.nio.channels.AcceptPendingException;
import java.util.List;

import ast.Campo;
import ast.DefVariable;
import ast.DefinicionFuncion;
import ast.Sentencia;
import ast.tipos.TipoFuncion;
import ast.tipos.TipoRegistro;
import semantico.VisitorDefault;

public class VisitorOffset extends VisitorDefault {

	public int offsetVariablesGlobales = 0, offsetVariablesLocales = 0;

	@Override
	public Object visit(DefVariable defVariable, Object params) {

		// Si la variable es global (ambito = 0) entonces lo sumamos al
		// offsetVariables
		if (defVariable.ambito == 0) {
			defVariable.offset = offsetVariablesGlobales;
			offsetVariablesGlobales += defVariable.tipo.numeroBytes();
		} else if (defVariable.ambito == 1 && defVariable.offset == 0) { // Son
																			// variables
																			// internas
																			// a
																			// la
																			// funcion
			offsetVariablesLocales -= defVariable.tipo.numeroBytes();
			defVariable.offset = offsetVariablesLocales;
		}

		defVariable.tipo.accept(this, params);
		
		return null;
	}

	@Override
	public Object visit(DefinicionFuncion definicionFuncion, Object params) {
		offsetVariablesLocales = 0; // Cada vez que se entra en una funcion
		definicionFuncion.tipo.accept(this, params);
		for (Sentencia sent : definicionFuncion.sentencias)
			if (sent instanceof DefVariable)
				sent.accept(this, params);
		
		definicionFuncion.offsetVarsLocales = offsetVariablesLocales;
		return null;
	}

	@Override
	public Object visit(TipoRegistro tipoRegistro, Object params) {
		// Variable offset desde 0 en cada registro
		int offsetRegistro = 0;
		for (Campo campo : tipoRegistro.campos) {
			campo.offset = offsetRegistro;
			offsetRegistro += campo.tipo.numeroBytes();
			campo.accept(this, params); // Por si el campo es otro struct

		}
		return null;
	}

	@Override
	public Object visit(TipoFuncion tipoFuncion, Object params) {
		int tempOffset = 4;
		List<DefVariable> parametrosFuncion = tipoFuncion.defVariables;

		for (int i = parametrosFuncion.size() - 1; i >= 0; i--) {
			parametrosFuncion.get(i).offset = tempOffset;
			tempOffset += parametrosFuncion.get(i).tipo.numeroBytes();
		}
		return null;
	}

	// TipoFuncion para los parámetros

}
