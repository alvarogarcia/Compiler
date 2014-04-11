package semantico;

import ast.AccesoArray;
import ast.AccesoCampo;
import ast.Aritmetica;
import ast.Asignacion;
import ast.Campo;
import ast.Cast;
import ast.Comparacion;
import ast.DefVariable;
import ast.Definicion;
import ast.DefinicionFuncion;
import ast.Escritura;
import ast.Expresion;
import ast.InvocacionFuncion;
import ast.InvocacionProcedimiento;
import ast.Lectura;
import ast.LiteralCaracter;
import ast.LiteralEntero;
import ast.LiteralReal;
import ast.Logica;
import ast.MenosUnario;
import ast.Negacion;
import ast.OCondTernario;
import ast.Programa;
import ast.RestaUnitaria;
import ast.RestaUnitariaBef;
import ast.Return;
import ast.Sentencia;
import ast.SentenciaIF;
import ast.SentenciaWhile;
import ast.SumatorioUnitario;
import ast.SumatorioUnitarioBef;
import ast.Tipo;
import ast.TipoError;
import ast.Variable;
import ast.tipos.TipoArray;
import ast.tipos.TipoCaracter;
import ast.tipos.TipoEntero;
import ast.tipos.TipoFuncion;
import ast.tipos.TipoReal;
import ast.tipos.TipoRegistro;
import ast.tipos.TipoVoid;

public class VisitorSemantico extends VisitorDefault {

	@Override
	public Object visit(MenosUnario menosUnario, Object params) {
		menosUnario.expresion.accept(this, params);
		menosUnario.setTipo(menosUnario.expresion.getTipo().aritmetica());
		if (menosUnario.getTipo() == null)
			menosUnario.setTipo(new TipoError(
					"Error en la expresión  menosUnaria. ERROR en la línea "
							+ menosUnario.linea + " y columna "
							+ menosUnario.columna + "\n", menosUnario));
		
		menosUnario.lvalue = false;
		return null;
	}

	@Override
	public Object visit(Variable variable, Object params) {
		variable.lvalue = true;
		if (variable.definicion != null)
			variable.tipo = variable.definicion.getTipo();
		else
			variable.tipo = new TipoError(
					"Variable no definida. ERROR en línea " + variable.linea
							+ " y columna " + variable.columna, variable);
		return null;
	}

	@Override
	public Object visit(TipoError tipoError, Object params) {
		tipoError.lvalue = false;
		return null;
	}

	@Override
	public Object visit(SentenciaWhile sentenciaWhile, Object params) {
		sentenciaWhile.condicion.accept(this, params);
		if (!sentenciaWhile.condicion.getTipo().isLogic())
			sentenciaWhile.condicion.setTipo(new TipoError(
					"WHILE: Se esperaba una condición lógica. Línea "
							+ sentenciaWhile.linea + " y columna "
							+ sentenciaWhile.columna + "\n", sentenciaWhile));
		for (Sentencia sentencia : sentenciaWhile.sentencias)
			sentencia.accept(this, params);
		sentenciaWhile.lvalue = false;
		return null;
	}

	

	@Override
	public Object visit(Return retorno, Object params) {
		retorno.retorno.accept(this, params);
		if (params != null) {
			if ((retorno.retorno.getTipo()
					.promocionaA(((TipoFuncion) params).retorno)) == null)
				retorno.retorno
						.setTipo(new TipoError(
								"El tipo devuelto en la función no coincide con el tipo de la sentencia Return. ERROR en la línea "
										+ retorno.linea
										+ " y columna "
										+ retorno.columna + "\n", retorno));
		}
		retorno.lvalue = false;

		return null;
	}

	@Override
	public Object visit(Programa programa, Object params) {
		for (Definicion definicion : programa.definiciones)
			definicion.accept(this, params);
		programa.lvalue = false;

		return null;
	}

	@Override
	public Object visit(Negacion negacion, Object params) {
		negacion.expresion.accept(this, params);
		negacion.lvalue = false;
		if (!negacion.expresion.getTipo().isLogic())
			negacion.tipo = new TipoError(
					"ERROR. Para negar una expresión esta tiene que ser lógica. ERROR en la línea "
							+ negacion.linea + " y columna " + negacion.columna,
					negacion);
		else
			negacion.tipo = negacion.expresion.getTipo();

		return null;
	}

	@Override
	public Object visit(Logica logica, Object params) {
		logica.expresion1.accept(this, params);
		logica.expresion2.accept(this, params);
		logica.lvalue = false;
		if (!logica.expresion1.getTipo().isLogic()
				|| !logica.expresion2.getTipo().isLogic())
			logica.tipo = new TipoError(
					"Para tener una comparación lógica,  ambas expresiones deben serlo. ERROR en la línea "
							+ logica.linea
							+ " y columna "
							+ logica.columna
							+ "\n", logica);
		else
			logica.tipo = logica.expresion1.getTipo();

		return null;
	}

	@Override
	public Object visit(LiteralEntero literalEntero, Object params) {
		literalEntero.lvalue = false;
		literalEntero.tipo = TipoEntero.tipoEntero;

		return null;
	}

	@Override
	public Object visit(LiteralReal literalReal, Object params) {
		literalReal.lvalue = false;
		literalReal.tipo = TipoReal.tipoReal;
		return null;
	}

	@Override
	public Object visit(LiteralCaracter literalCaracter, Object params) {
		literalCaracter.lvalue = false;
		literalCaracter.tipo = TipoCaracter.tipoCaracter;
		return null;
	}

	@Override
	public Object visit(Lectura lectura, Object params) {
		for (Expresion expresion : lectura.expresiones)
			expresion.accept(this, params);
		lectura.lvalue = false;

		for (Expresion expresion : lectura.expresiones)
			if (!expresion.getLValue())
				new TipoError("Se Esperaba un LValue en línea " + lectura.linea
						+ " y columna " + lectura.columna + "\n", lectura);

		return null;

	}

	@Override
	public Object visit(InvocacionProcedimiento invocacionProcedimiento,
			Object params) {
		for (Expresion argumento : invocacionProcedimiento.argumentos)
			argumento.accept(this, params);
		invocacionProcedimiento.funcion.accept(this, params);
		if (invocacionProcedimiento.funcion.getTipo().parentesis( // Debería ser
																	// iP.funcion.getTipo
																	// VOID o
																	// TipoFuncion
																	// con Tipo
																	// de
																	// retorno a
																	// VOID.
				invocacionProcedimiento.argumentos) == null) {
			new TipoError(
					"ERROR: Los tipos de los parámetros al invocar el procedimiento no coinciden. ERROR en la línea "
							+ invocacionProcedimiento.linea
							+ " y columna "
							+ invocacionProcedimiento.columna + "\n",
					invocacionProcedimiento);
		}
		invocacionProcedimiento.lvalue = false;

		return null;
	}

	@Override
	public Object visit(InvocacionFuncion invocacionFuncion, Object params) {
		invocacionFuncion.funcion.accept(this, params);
		for (Expresion argumento : invocacionFuncion.argumentos)
			argumento.accept(this, params);
		invocacionFuncion.tipo = invocacionFuncion.funcion.getTipo()
				.parentesis(invocacionFuncion.argumentos);
		if (invocacionFuncion.tipo == null)
			invocacionFuncion.tipo = new TipoError(
					"ERROR: Los tipos de los parámetros al invocar la función no coinciden. ERROR en la línea "
							+ invocacionFuncion.linea
							+ " y la columna "
							+ invocacionFuncion.columna + "\n",
					invocacionFuncion);
		invocacionFuncion.lvalue = false;

		return null;
	}

	@Override
	public Object visit(Escritura escritura, Object params) {
		for (Expresion expresion : escritura.expresiones)
			expresion.accept(this, params);
		escritura.lvalue = false;

		return null;
	}

	@Override
	public Object visit(DefVariable defVariable, Object params) {
		defVariable.tipo.accept(this, params);
		defVariable.lvalue = true;

		return null;
	}

	@Override
	public Object visit(DefinicionFuncion definicionFuncion, Object params) {
		definicionFuncion.tipo.accept(this, params);
		for (Sentencia sentencia : definicionFuncion.sentencias) {
			// Le paso como parámetros el tipo para que en el return
			// se pueda saber qué es lo que tiene que devolver la función y se
			// compruebe en su visitor si
			// los tipos coinciden y en caso contrario se lance un error.
			sentencia.accept(this, definicionFuncion.tipo);
		}
		definicionFuncion.lvalue = false;

		return null;
	}

	@Override
	public Object visit(Comparacion comparacion, Object params) {
		comparacion.expresion1.accept(this, params);
		comparacion.expresion2.accept(this, params);
		if (comparacion.expresion1.getTipo().esComparable(
				comparacion.expresion2.getTipo()) == null) {
			new TipoError("Las expresiones no son comparables. ERROR en línea "
					+ comparacion.linea + " y columna " + comparacion.columna
					+ "\n", comparacion);
		} else
			comparacion.tipo = comparacion.expresion1.getTipo().esComparable(
					comparacion.expresion2.getTipo());
		comparacion.lvalue = false;

		return null;
	}

	@Override
	public Object visit(Cast cast, Object params) {
		cast.tipoCast.accept(this, params);
		cast.expresion.accept(this, params);
		cast.setTipo(cast.expresion.getTipo().esCasteable(cast.tipoCast));
		if (cast.getTipo() == null)
			cast.setTipo(new TipoError("ERROR al castear en la línea "
					+ cast.linea + " y columna " + cast.columna + "\n", cast));
		cast.lvalue = false;

		return null;
	}

	@Override
	public Object visit(Campo campo, Object params) {
		campo.tipo.accept(this, params);
		campo.lvalue = true;

		return null;
	}

	@Override
	public Object visit(Asignacion asignacion, Object params) {
		asignacion.expresion1.accept(this, params);
		asignacion.expresion2.accept(this, params);

		if (asignacion.expresion2.getTipo().promocionaA(
				asignacion.expresion1.getTipo()) == null)
			asignacion.expresion1.setTipo(new TipoError(
					"Los tipos de la asignación no coinciden. ERROR en la línea "
							+ asignacion.linea + " y columna "
							+ asignacion.columna + "\n", asignacion));

		if (!asignacion.expresion1.getLValue()) {
			new TipoError("Se Esperaba un LValue en línea " + asignacion.linea
					+ " y columna " + asignacion.columna + "\n", asignacion);
		}

		asignacion.lvalue = false;

		return null;
	}

	@Override
	public Object visit(Aritmetica aritmetica, Object params) {
		aritmetica.expresion1.accept(this, params);
		aritmetica.expresion2.accept(this, params);
		aritmetica.tipo = aritmetica.expresion1.getTipo().aritmetica(
				aritmetica.expresion2.getTipo());
		if (aritmetica.tipo == null)
			aritmetica.tipo = new TipoError(
					"error de tipos en la aritmetica en línea "
							+ aritmetica.linea + " y columna "
							+ aritmetica.columna + "\n", aritmetica);
		aritmetica.lvalue = false;

		return null;
	}

	@Override
	public Object visit(AccesoCampo accesoCampo, Object params) {
		accesoCampo.nombreStruct.accept(this, params);
		accesoCampo.lvalue = true;

		if (accesoCampo.nombreStruct.getTipo() instanceof MenosUnario)
			accesoCampo.tipo = ((TipoRegistro) ((MenosUnario) accesoCampo.nombreStruct
					.getTipo()).expresion.getTipo()).punto(accesoCampo.campo);
		else
			accesoCampo.tipo = ((TipoRegistro) accesoCampo.nombreStruct
					.getTipo()).punto(accesoCampo.campo);

		if (accesoCampo.tipo == null)
			accesoCampo
					.setTipo(new TipoError(
							"Error en el acceso al campo o Los tipos del accesoCampo y el campo al que se ha accedido no son iguales. ERROR en línea "
									+ accesoCampo.linea
									+ " y columna "
									+ accesoCampo.columna + "\n", accesoCampo));

		return null;
	}

	@Override
	public Object visit(AccesoArray accesoArray, Object params) {
		accesoArray.array.accept(this, params);
		accesoArray.posicion.accept(this, params);
		accesoArray.lvalue = true;

		if (!accesoArray.array.getLValue() || !accesoArray.lvalue) {
			new TipoError("Se Esperaba un LValue en línea " + accesoArray.linea
					+ " y columna " + accesoArray.columna + "\n", accesoArray);
		}

		accesoArray.setTipo(accesoArray.array.getTipo().corchetes(
				accesoArray.posicion.getTipo()));
		if (accesoArray.getTipo() == null) {
			accesoArray.setTipo(new TipoError(
					"ERROR en el acceso al Array en la línea "
							+ accesoArray.linea + " y columna "
							+ accesoArray.columna + "\n", accesoArray));
		}

		return null;
	}

	@Override
	public Object visit(TipoVoid tipoVoid, Object params) {
		// tipoVoid.tipoVoid.accept(this, params);
		tipoVoid.lvalue = false;

		return null;
	}

	@Override
	public Object visit(TipoRegistro tipoRegistro, Object params) {
		for (Campo campo : tipoRegistro.campos)
			campo.accept(this, params);
		tipoRegistro.lvalue = false;

		return null;
	}

	@Override
	public Object visit(TipoReal tipoReal, Object params) {
		// tipoReal.tipoReal.accept(this, params);
		tipoReal.lvalue = false;

		return null;
	}

	@Override
	public Object visit(TipoEntero tipoEntero, Object params) {
		// tipoEntero.tipoEntero.accept(this, params);
		tipoEntero.lvalue = false;

		return null;
	}

	@Override
	public Object visit(TipoCaracter tipoCaracter, Object params) {
		// tipoCaracter.tipoCaracter.accept(this, params);
		tipoCaracter.lvalue = false;

		return null;
	}

	@Override
	public Object visit(TipoArray tipoArray, Object params) {
		tipoArray.tipoArray.accept(this, params);
		tipoArray.lvalue = false;

		return null;
	}

	@Override
	public Object visit(TipoFuncion tipoFuncion, Object params) {
		for (DefVariable definicionVariable : tipoFuncion.defVariables)
			definicionVariable.accept(this, params);
		tipoFuncion.lvalue = false;

		return null;
	}
	
	
	@Override
	public Object visit(SumatorioUnitario sumatorioUnitario, Object params) {
		sumatorioUnitario.expresion.accept(this, params);
		sumatorioUnitario.setTipo(sumatorioUnitario.expresion.getTipo().aritmetica());
		if (sumatorioUnitario.getTipo() == null)
			sumatorioUnitario.setTipo(new TipoError(
					"Error en la expresión  de sumatorioUnitario. ERROR en la línea "
							+ sumatorioUnitario.linea + " y columna "
							+ sumatorioUnitario.columna + "\n", sumatorioUnitario));
		
		sumatorioUnitario.lvalue = false;
		return null;
	}
	
	@Override
	public Object visit(RestaUnitaria restaUnitaria, Object params) {
		restaUnitaria.expresion.accept(this, params);
		restaUnitaria.setTipo(restaUnitaria.expresion.getTipo().aritmetica());
		if (restaUnitaria.getTipo() == null)
			restaUnitaria.setTipo(new TipoError(
					"Error en la expresión  de sumatorioUnitario. ERROR en la línea "
							+ restaUnitaria.linea + " y columna "
							+ restaUnitaria.columna + "\n", restaUnitaria));
		
		restaUnitaria.lvalue = false;
		return null;
	}
	
	
	@Override
	public Object visit(SumatorioUnitarioBef sumatorioUnitarioBef, Object params) {
		sumatorioUnitarioBef.expresion.accept(this, params);
		sumatorioUnitarioBef.setTipo(sumatorioUnitarioBef.expresion.getTipo().aritmetica());
		if (sumatorioUnitarioBef.getTipo() == null)
			sumatorioUnitarioBef.setTipo(new TipoError(
					"Error en la expresión  de sumatorioUnitario. ERROR en la línea "
							+ sumatorioUnitarioBef.linea + " y columna "
							+ sumatorioUnitarioBef.columna + "\n", sumatorioUnitarioBef));
		
		sumatorioUnitarioBef.lvalue = false;
		return null;
	}
	
	@Override
	public Object visit(RestaUnitariaBef restaUnitariaBef, Object params) {
		restaUnitariaBef.expresion.accept(this, params);
		restaUnitariaBef.setTipo(restaUnitariaBef.expresion.getTipo().aritmetica());
		if (restaUnitariaBef.getTipo() == null)
			restaUnitariaBef.setTipo(new TipoError(
					"Error en la expresión  de sumatorioUnitario. ERROR en la línea "
							+ restaUnitariaBef.linea + " y columna "
							+ restaUnitariaBef.columna + "\n", restaUnitariaBef));
		
		restaUnitariaBef.lvalue = false;
		return null;
	}
	
	@Override
	public Object visit(SentenciaIF sentenciaIF, Object params) {
		sentenciaIF.condicion.accept(this, params);
		if (!sentenciaIF.condicion.getTipo().isLogic())
			sentenciaIF.condicion.setTipo(new TipoError(
					"IF: Se esperaba una condición lógica. Línea "
							+ sentenciaIF.linea + " y columna "
							+ sentenciaIF.columna + "\n", sentenciaIF));
		for (Sentencia sentencia : sentenciaIF.cuerpoIF)
			sentencia.accept(this, params);
		for (Sentencia sentencia : sentenciaIF.cuerpoELSE)
			sentencia.accept(this, params);
		sentenciaIF.lvalue = false;

		return null;
	}
	
	@Override
	public Object visit(OCondTernario oCondTernario, Object params){
		oCondTernario.condicional.accept(this, params);
		oCondTernario.salidaCierto.accept(this, params);
		oCondTernario.salidaFalso.accept(this, params);
		if(!oCondTernario.condicional.getTipo().isLogic())
			oCondTernario.condicional.setTipo(new TipoError("OCONDTERNARIO: Se esperaba una condición lógica. Línea " + oCondTernario.linea + " y columna " + oCondTernario.columna, oCondTernario));
		
		/*if(!(oCondTernario.salidaCierto.getTipo() == oCondTernario.salidaFalso.getTipo() && (oCondTernario.salidaCierto.getTipo() == TipoCaracter.tipoCaracter || oCondTernario.salidaCierto.getTipo() ==  TipoEntero.tipoEntero || oCondTernario.salidaCierto.getTipo() == TipoReal.tipoReal)))
			oCondTernario.tipo = new TipoError("ERROR: los tipos de salida en caso de que la expresión sea verdadera o falsa han de ser iguales. ERROR en la línea "  + oCondTernario.linea + " y columna "  + oCondTernario.columna, oCondTernario);
		else
			oCondTernario.tipo = oCondTernario.salidaCierto.getTipo();*/
		
		oCondTernario.tipo = oCondTernario.salidaCierto.getTipo().promocionBidireccional(oCondTernario.salidaFalso.getTipo());
		
		if(oCondTernario.tipo == null)
			oCondTernario.tipo = new TipoError("ERROR: Tipos de salida no compatibles. ERROR en la línea " + oCondTernario.linea + " y columna " + oCondTernario.columna, oCondTernario);
		
		if(oCondTernario.tipo instanceof TipoArray || oCondTernario.tipo instanceof TipoRegistro)
				oCondTernario.lvalue = true;
		else
			oCondTernario.lvalue = false;
		
		return null;
	}

}
