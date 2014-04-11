package generadorCodigo;

import ast.Asignacion;
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
import ast.MenosUnario;
import ast.Programa;
import ast.RestaUnitaria;
import ast.RestaUnitariaBef;
import ast.Return;
import ast.Sentencia;
import ast.SentenciaIF;
import ast.SentenciaWhile;
import ast.SumatorioUnitario;
import ast.SumatorioUnitarioBef;
import ast.Variable;
import ast.tipos.TipoEntero;
import ast.tipos.TipoFuncion;
import ast.tipos.TipoVoid;

public class VisitorGCEjecutar extends VisitorDefaultErrores {

	private VisitorGCValor visitorGCValor = new VisitorGCValor();
	private VisitorGCDireccion visitorGCDireccion = new VisitorGCDireccion();

	@Override
	public Object visit(Programa programa, Object params) {
		for (Definicion d : programa.definiciones){
			GeneradorCodigo.line(d.getLinea());
			if (d instanceof DefVariable)
				d.accept(this, params);
		}
		GeneradorCodigo.call("main");
		GeneradorCodigo.halt();

		for (Definicion d : programa.definiciones)
			if (d instanceof DefinicionFuncion)
				d.accept(this, params);

		return null;

	}
	
	@Override
	public Object visit(Escritura escritura, Object params) {
		GeneradorCodigo.comentario("* Escritura");
		/* Como las expresiones estï¿½n dadas la vuelta por culpa del sintï¿½ctico, genero su cï¿½digo al revï¿½s */
		for(int i = escritura.expresiones.size() - 1; i >= 0; i--){
			escritura.expresiones.get(i).accept(visitorGCValor, params);
			GeneradorCodigo.out(escritura.expresiones.get(i).getTipo());
		}


		return null;
	}

	@Override
	public Object visit(Lectura lectura, Object params) {
		GeneradorCodigo.comentario("* Lectura");
		for (Expresion exp : lectura.expresiones) {
			exp.accept(visitorGCDireccion, params);
			GeneradorCodigo.in(exp.getTipo());
			GeneradorCodigo.store(exp.getTipo());
		}
		return null;

	}

	@Override
	public Object visit(Asignacion asignacion, Object params) {
		asignacion.expresion1.accept(visitorGCDireccion, params);
		asignacion.expresion2.accept(visitorGCValor, params);
		GeneradorCodigo.convertirA(asignacion.expresion2.getTipo(),
				asignacion.expresion1.getTipo());
		GeneradorCodigo.store(asignacion.expresion1.getTipo());
		return null;
	}

	@Override
	public Object visit(DefinicionFuncion defFuncion, Object params) {
		
		GeneradorCodigo.id(defFuncion.nombre);
		GeneradorCodigo.comentario("* Parametros:");
		for (DefVariable parametro : ((TipoFuncion) defFuncion.tipo).defVariables)
			parametro.accept(this, params);
		GeneradorCodigo.comentario("* Variables Locales:");
		params = defFuncion;
		for (Sentencia sentencia : defFuncion.sentencias)
			if (sentencia instanceof DefVariable){
				GeneradorCodigo.line(sentencia.getLinea());
				sentencia.accept(this, defFuncion);
			}
		GeneradorCodigo.enter(defFuncion.offsetVarsLocales);
		for (Sentencia sent : defFuncion.sentencias)
			if (!(sent instanceof DefVariable)){
				GeneradorCodigo.line(sent.getLinea());
				sent.accept(this, params);
			}
		if (((TipoFuncion) defFuncion.tipo).retorno == TipoVoid.tipoVoid)
			if (((TipoFuncion) defFuncion.tipo).defVariables.size() == 0)
				GeneradorCodigo.ret(0, defFuncion.offsetVarsLocales, 0);
			else{
				DefVariable ultimoParametro = ((TipoFuncion) defFuncion.tipo).defVariables.get(0);
				GeneradorCodigo
						.ret(0,
								defFuncion.offsetVarsLocales,
								ultimoParametro.offset + ultimoParametro.getTipo().numeroBytes() - 4);}
		return null;
	}

	

	@Override
	public Object visit(DefVariable defVariable, Object params) {
		GeneradorCodigo.comentario(" * " + defVariable.tipo.toString() + " "
				+ defVariable.nombre + " (offset " + defVariable.offset + ")");

		return null;

	}

	@Override
	public Object visit(Return retorno, Object params) {
		DefinicionFuncion defFunc = (DefinicionFuncion) params;
		retorno.retorno.accept(this, params);
		//retorno.retorno.accept(visitorGCValor, params);
		GeneradorCodigo.convertirA(retorno.retorno.getTipo(), ((TipoFuncion) defFunc.getTipo()).retorno);
		if (((TipoFuncion) defFunc.tipo).defVariables.size() == 0)
			GeneradorCodigo.ret(defFunc.tipo.numeroBytes(),
					defFunc.offsetVarsLocales, 0);
		else
			GeneradorCodigo.ret(defFunc.tipo.numeroBytes(),
					defFunc.offsetVarsLocales,
					((TipoFuncion) defFunc.tipo).defVariables
							.get(0).offset - 4 + ((TipoFuncion) defFunc.tipo).defVariables
							.get(0).getTipo().numeroBytes());
		return null;
	}

	



	@Override
	public Object visit(Cast cast, Object params) {
		cast.expresion.accept(visitorGCValor, params);
		GeneradorCodigo.convertirA(cast.expresion.getTipo(), cast.tipo);

		return null;
	}
	
	
	@Override
	public Object visit(SentenciaWhile sentenciaWhile, Object params){
		int etiquetas = GeneradorCodigo.dameEtiqueta(2);
		GeneradorCodigo.id("etiqueta" + etiquetas);
		sentenciaWhile.condicion.accept(visitorGCValor, params);
		if(!(sentenciaWhile.condicion instanceof Comparacion))
			GeneradorCodigo.convertirA(sentenciaWhile.condicion.getTipo(), TipoEntero.tipoEntero);
		GeneradorCodigo.jz("etiqueta" + (etiquetas + 1));
		for(Sentencia s : sentenciaWhile.sentencias){
			GeneradorCodigo.line(s.getLinea());
			s.accept(this, params);
		}
		GeneradorCodigo.jmp("etiqueta" + etiquetas);
		GeneradorCodigo.id("etiqueta" + (etiquetas + 1));
		
		return null;
	}
	
	@Override
	public Object visit(SentenciaIF sentenciaIF, Object params){
		int etiquetas = GeneradorCodigo.dameEtiqueta(2);
		sentenciaIF.condicion.accept(visitorGCValor, params);
		//GeneradorCodigo.convertirA(sentenciaIF.condicion.getTipo(), TipoEntero.tipoEntero);
		// No hace falta por que al comparar queda un uno o un cero. El tipo de la comparación no es
		// Lo que devuelve la máquina tras hacer la comparación.
		GeneradorCodigo.jz("etiqueta" + etiquetas);
		for(Sentencia s : sentenciaIF.cuerpoIF){
			GeneradorCodigo.line(s.getLinea());
			s.accept(this, params);

		}
		GeneradorCodigo.jmp("etiqueta" + (etiquetas + 1));
		GeneradorCodigo.id("etiqueta" + etiquetas);
		for(Sentencia s : sentenciaIF.cuerpoELSE){
			GeneradorCodigo.line(s.getLinea());
			s.accept(this, params);
		}
		GeneradorCodigo.id("etiqueta" + (etiquetas + 1));
		
		return null;
			
	}
	
	@Override
	public Object visit(InvocacionFuncion invocacionFuncion, Object params){
		invocacionFuncion.accept(visitorGCValor, params);
		if(!(invocacionFuncion.tipo instanceof TipoVoid))
			GeneradorCodigo.pop(((Expresion) invocacionFuncion).getTipo());
		
		return null;
	}
	
	@Override
	public Object visit(InvocacionProcedimiento invocacionProcedimiento, Object params){
		invocacionProcedimiento.accept(visitorGCValor, params);
		if(!(((TipoFuncion)invocacionProcedimiento.funcion.tipo).retorno == TipoVoid.tipoVoid))
			GeneradorCodigo.pop(((TipoFuncion)invocacionProcedimiento.funcion.tipo).retorno);
		return null;
	}
	
	@Override
	public Object visit(Variable variable, Object params){
		variable.accept(visitorGCValor, params);
		return null;
	}
	
	@Override
	public Object visit(LiteralCaracter lc, Object params){
		lc.accept(visitorGCValor, params);
		return null;
	}
	
	@Override
	public Object visit(LiteralEntero le, Object params){
		le.accept(visitorGCValor, params);
		return null;
	}
	
	@Override
	public Object visit(LiteralReal lr, Object params){
		lr.accept(visitorGCValor, params);
		return null;
	}
	
	@Override
	public Object visit(MenosUnario mu, Object params){
		mu.expresion.accept(visitorGCValor, params);
		GeneradorCodigo.convertirA(mu.expresion.getTipo(), mu.getTipo());
		GeneradorCodigo.push(-1);
		GeneradorCodigo.convertirA(TipoEntero.tipoEntero, mu.getTipo());
		GeneradorCodigo.mul(mu.getTipo());
		return null;
	}
	
	@Override
	public Object visit(SumatorioUnitario sumatorioUnitario, Object params){
		sumatorioUnitario.accept(visitorGCValor, params);
		GeneradorCodigo.pop(sumatorioUnitario.getTipo());
		return null;
		
	}
	
	@Override
	public Object visit(RestaUnitaria restaUnitaria , Object params){
		restaUnitaria.accept(visitorGCValor, params);
		GeneradorCodigo.pop(restaUnitaria.getTipo());
		return null;
		
	}
	
	@Override
	public Object visit(SumatorioUnitarioBef sumatorioUnitarioBef, Object params){
		sumatorioUnitarioBef.accept(visitorGCValor, params);
		GeneradorCodigo.pop(sumatorioUnitarioBef.getTipo());
		return null;
		
	}
	
	
	@Override
	public Object visit(RestaUnitariaBef restaUnitariaBef, Object params){
		restaUnitariaBef.accept(visitorGCValor, params);
		GeneradorCodigo.pop(restaUnitariaBef.getTipo());
		return null;
		
	}
}
