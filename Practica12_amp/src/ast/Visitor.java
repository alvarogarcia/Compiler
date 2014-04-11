package ast;

import ast.tipos.*;

public interface Visitor {

	public Object visit(MenosUnario menosUnario, Object params);

	public Object visit(Variable variable, Object params);

	public Object visit(TipoError tipoError, Object params);

	public Object visit(SentenciaWhile sentenciaWhile, Object params);

	public Object visit(SentenciaIF sentenciaIF, Object params);

	public Object visit(Return retorno, Object params);

	public Object visit(Programa programa, Object params);

	public Object visit(Negacion negacion, Object params);

	public Object visit(Logica logica, Object params);

	public Object visit(LiteralEntero literalEntero, Object params);

	public Object visit(LiteralReal literalReal, Object params);

	public Object visit(LiteralCaracter literalCaracter, Object params);

	public Object visit(Lectura lectura, Object params);

	public Object visit(InvocacionProcedimiento invocacionProcedimiento,
			Object params);

	public Object visit(InvocacionFuncion invocacionFuncion, Object params);

	public Object visit(Escritura escritura, Object params);

	public Object visit(DefVariable defVariable, Object params);

	public Object visit(DefinicionFuncion definicionFuncion, Object params);

	public Object visit(Comparacion comparacion, Object params);

	public Object visit(Cast cast, Object params);

	public Object visit(Campo campo, Object params);

	public Object visit(Asignacion asignacion, Object params);

	public Object visit(Aritmetica aritmetcia, Object params);

	public Object visit(AccesoCampo accesoCampo, Object params);

	public Object visit(AccesoArray accesoArray, Object params);

	public Object visit(TipoVoid tipoVoid, Object params);

	public Object visit(TipoRegistro tipoRegistro, Object params);

	public Object visit(TipoReal tipoReal, Object params);

	public Object visit(TipoFuncion tipoFuncion, Object params);

	public Object visit(TipoEntero tipoEntero, Object params);

	public Object visit(TipoCaracter tipoCaracter, Object params);

	public Object visit(TipoArray tipoArray, Object params);

	public Object visit(SumatorioUnitario sumatorioUnitario, Object params);
	
	public Object visit(RestaUnitaria restaUnitaria, Object params);
	
	public Object visit(SumatorioUnitarioBef sumatorioUnitarioBef, Object params);

	public Object visit(RestaUnitariaBef restaUnitariaBef, Object params);

	public Object visit(OCondTernario oCondTernario, Object params);

}