package generadorCodigo;

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

public class VisitorDefaultErrores implements Visitor {

	@Override
	public Object visit(MenosUnario menosUnario, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (menosUnario). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Variable variable, Object params) {

		throw new RuntimeException(
				"Excepción en fase del semántico (variable). No se debería pasar por aquí");
	}

	@Override
	public Object visit(TipoError tipoError, Object params) {

		throw new RuntimeException(
				"Excepción en fase del semántico (tipoError). No se debería pasar por aquí");
	}

	@Override
	public Object visit(SentenciaWhile sentenciaWhile, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (sentenciaWhile). No se debería pasar por aquí");
	}

	@Override
	public Object visit(SentenciaIF sentenciaIF, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (sentenciaIF). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Return retorno, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (retorno). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Programa programa, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (programa). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Negacion negacion, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (negación). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Logica logica, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (lógica). No se debería pasar por aquí");
	}

	@Override
	public Object visit(LiteralEntero literalEntero, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (literalEntero). No se debería pasar por aquí");
	}

	@Override
	public Object visit(LiteralReal literalReal, Object params) {

		throw new RuntimeException(
				"Excepción en fase del semántico (literalReal). No se debería pasar por aquí");
	}

	@Override
	public Object visit(LiteralCaracter literalCaracter, Object params) {

		throw new RuntimeException(
				"Excepción en fase del semántico (literalCaracter). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Lectura lectura, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (lectura). No se debería pasar por aquí");

	}

	@Override
	public Object visit(InvocacionProcedimiento invocacionProcedimiento,
			Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (invocacionProcedimiento). No se debería pasar por aquí");
	}

	@Override
	public Object visit(InvocacionFuncion invocacionFuncion, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (invocacionFuncion). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Escritura escritura, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (escritura). No se debería pasar por aquí");
	}

	@Override
	public Object visit(DefVariable defVariable, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (defVariable). No se debería pasar por aquí");
	}

	@Override
	public Object visit(DefinicionFuncion definicionFuncion, Object params) {
		definicionFuncion.tipo.accept(this, params);
		throw new RuntimeException(
				"Excepción en fase del semántico (definicionFuncion). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Comparacion comparacion, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (comparacion). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Cast cast, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (cast). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Campo campo, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (campo). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Asignacion asignacion, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (asignacion). No se debería pasar por aquí");
	}

	@Override
	public Object visit(Aritmetica aritmetica, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (aritmetica). No se debería pasar por aquí");
	}

	@Override
	public Object visit(AccesoCampo accesoCampo, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (accesoCampo). No se debería pasar por aquí");
	}

	@Override
	public Object visit(AccesoArray accesoArray, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (accesoArray). No se debería pasar por aquí");
	}

	@Override
	public Object visit(TipoVoid tipoVoid, Object params) {

		throw new RuntimeException(
				"Excepción en fase del semántico (tipoVoid). No se debería pasar por aquí");
	}

	@Override
	public Object visit(TipoRegistro tipoRegistro, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (tipoRegistro). No se debería pasar por aquí");
	}

	@Override
	public Object visit(TipoReal tipoReal, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (tipoReal). No se debería pasar por aquí");
	}

	@Override
	public Object visit(TipoEntero tipoEntero, Object params) {

		throw new RuntimeException(
				"Excepción en fase del semántico (tipoEntero). No se debería pasar por aquí");
	}

	@Override
	public Object visit(TipoCaracter tipoCaracter, Object params) {

		throw new RuntimeException(
				"Excepción en fase del semántico (tipoCaracter). No se debería pasar por aquí");
	}

	@Override
	public Object visit(TipoArray tipoArray, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (tipoArray). No se debería pasar por aquí");
	}

	@Override
	public Object visit(TipoFuncion tipoFuncion, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (tipoFuncion). No se debería pasar por aquí");
	}

	@Override
	public Object visit(SumatorioUnitario sumatorioUnitario, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (sumatorioUnitario). No se debería pasar por aquí");

	}

	@Override
	public Object visit(RestaUnitaria restaUnitaria, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (restaUnitaria). No se debería pasar por aquí");
	}

	@Override
	public Object visit(SumatorioUnitarioBef sumatorioUnitarioBef, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (sumatorioUnitarioBef). No se debería pasar por aquí");

	}
	
	@Override
	public Object visit(RestaUnitariaBef restaUnitariaBef, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (restaUnitariaBef). No se debería pasar por aquí");

	}

	@Override
	public Object visit(OCondTernario oCondTernario, Object params) {
		throw new RuntimeException(
				"Excepción en fase del semántico (oCondTernario). No se debería pasar por aquí");

	}

}
