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
import ast.TipoError;
import ast.Variable;
import ast.Visitor;
import ast.tipos.TipoArray;
import ast.tipos.TipoCaracter;
import ast.tipos.TipoEntero;
import ast.tipos.TipoFuncion;
import ast.tipos.TipoReal;
import ast.tipos.TipoRegistro;
import ast.tipos.TipoVoid;

public class VisitorDefault implements Visitor {

	@Override
	public Object visit(MenosUnario menosUnario, Object params) {
		menosUnario.expresion.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Variable variable, Object params) {

		return null;
	}

	@Override
	public Object visit(TipoError tipoError, Object params) {

		return null;
	}

	@Override
	public Object visit(SentenciaWhile sentenciaWhile, Object params) {
		sentenciaWhile.condicion.accept(this, params);
		for (Sentencia sentencia : sentenciaWhile.sentencias)
			sentencia.accept(this, params);

		return null;
	}

	@Override
	public Object visit(SentenciaIF sentenciaIF, Object params) {
		sentenciaIF.condicion.accept(this, params);
		for (Sentencia sentencia : sentenciaIF.cuerpoIF)
			sentencia.accept(this, params);
		for (Sentencia sentencia : sentenciaIF.cuerpoELSE)
			sentencia.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Return retorno, Object params) {
		retorno.retorno.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Programa programa, Object params) {
		for (Definicion definicion : programa.definiciones)
			definicion.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Negacion negacion, Object params) {
		negacion.expresion.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Logica logica, Object params) {
		logica.expresion1.accept(this, params);
		logica.expresion2.accept(this, params);

		return null;
	}

	@Override
	public Object visit(LiteralEntero literalEntero, Object params) {

		return null;
	}

	@Override
	public Object visit(LiteralReal literalReal, Object params) {

		return null;
	}

	@Override
	public Object visit(LiteralCaracter literalCaracter, Object params) {

		return null;
	}

	@Override
	public Object visit(Lectura lectura, Object params) {
		for (Expresion expresion : lectura.expresiones)
			expresion.accept(this, params);

		return null;

	}

	@Override
	public Object visit(InvocacionProcedimiento invocacionProcedimiento,
			Object params) {
		for (Expresion argumento : invocacionProcedimiento.argumentos)
			argumento.accept(this, params);
		invocacionProcedimiento.funcion.accept(this, params);

		return null;
	}

	@Override
	public Object visit(InvocacionFuncion invocacionFuncion, Object params) {
		invocacionFuncion.funcion.accept(this, params);
		for (Expresion argumento : invocacionFuncion.argumentos)
			argumento.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Escritura escritura, Object params) {
		for (Expresion expresion : escritura.expresiones)
			expresion.accept(this, params);

		return null;
	}

	@Override
	public Object visit(DefVariable defVariable, Object params) {
		defVariable.tipo.accept(this, params);

		return null;
	}

	@Override
	public Object visit(DefinicionFuncion definicionFuncion, Object params) {
		definicionFuncion.tipo.accept(this, params);
		for (Sentencia sentencia : definicionFuncion.sentencias)
			sentencia.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Comparacion comparacion, Object params) {
		comparacion.expresion1.accept(this, params);
		comparacion.expresion2.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Cast cast, Object params) {
		cast.tipoCast.accept(this, params);
		cast.expresion.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Campo campo, Object params) {
		campo.tipo.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Asignacion asignacion, Object params) {
		asignacion.expresion1.accept(this, params);
		asignacion.expresion2.accept(this, params);

		return null;
	}

	@Override
	public Object visit(Aritmetica aritmetica, Object params) {
		aritmetica.expresion1.accept(this, params);
		aritmetica.expresion2.accept(this, params);

		return null;
	}

	@Override
	public Object visit(AccesoCampo accesoCampo, Object params) {
		accesoCampo.nombreStruct.accept(this, params);

		return null;
	}

	@Override
	public Object visit(AccesoArray accesoArray, Object params) {
		accesoArray.array.accept(this, params);
		accesoArray.posicion.accept(this, params);

		return null;
	}

	@Override
	public Object visit(TipoVoid tipoVoid, Object params) {

		return null;
	}

	@Override
	public Object visit(TipoRegistro tipoRegistro, Object params) {
		for (Campo campo : tipoRegistro.campos)
			campo.accept(this, params);

		return null;
	}

	@Override
	public Object visit(TipoReal tipoReal, Object params) {

		return null;
	}

	@Override
	public Object visit(TipoEntero tipoEntero, Object params) {

		return null;
	}

	@Override
	public Object visit(TipoCaracter tipoCaracter, Object params) {

		return null;
	}

	@Override
	public Object visit(TipoArray tipoArray, Object params) {
		tipoArray.tipoArray.accept(this, params);

		return null;
	}

	@Override
	public Object visit(TipoFuncion tipoFuncion, Object params) {
		for (DefVariable definicionVariable : tipoFuncion.defVariables)
			definicionVariable.accept(this, params);

		return null;
	}

	@Override
	public Object visit(SumatorioUnitario sumatorioUnitario, Object params) {
		sumatorioUnitario.expresion.accept(this, params);
		return null;
	}

	@Override
	public Object visit(RestaUnitaria restaUnitaria, Object params) {
		restaUnitaria.expresion.accept(this, params);
		return null;
	}

	@Override
	public Object visit(SumatorioUnitarioBef sumatorioUnitarioBef, Object params) {
		sumatorioUnitarioBef.expresion.accept(this, params);
		return null;
	}

	@Override
	public Object visit(RestaUnitariaBef restaUnitariaBef, Object params) {
		restaUnitariaBef.expresion.accept(this, params);
		return null;
		
	}

	@Override
	public Object visit(OCondTernario oCondTernario, Object params) {
		oCondTernario.condicional.accept(this, params);
		oCondTernario.salidaCierto.accept(this, params);
		oCondTernario.salidaFalso.accept(this, params);
		
		return null;
		
	}

}
