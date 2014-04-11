package generadorCodigo;

import ast.AccesoArray;
import ast.AccesoCampo;
import ast.Aritmetica;
import ast.Cast;
import ast.Comparacion;
import ast.Expresion;
import ast.InvocacionFuncion;
import ast.InvocacionProcedimiento;
import ast.LiteralCaracter;
import ast.LiteralEntero;
import ast.LiteralReal;
import ast.Logica;
import ast.MenosUnario;
import ast.Negacion;
import ast.OCondTernario;
import ast.RestaUnitaria;
import ast.RestaUnitariaBef;
import ast.Sentencia;
import ast.SentenciaIF;
import ast.SumatorioUnitario;
import ast.SumatorioUnitarioBef;
import ast.Tipo;
import ast.Variable;
import ast.tipos.TipoEntero;
import ast.tipos.TipoFuncion;

public class VisitorGCValor extends VisitorDefaultErrores {
	
	private VisitorGCDireccion visitorGCDireccion = new VisitorGCDireccion();

	@Override
	public Object visit(Variable variable, Object params) {
		variable.accept(visitorGCDireccion, params);
		GeneradorCodigo.load(variable.getTipo());
		
		return null;			
	}
	
	@Override
	public Object visit(LiteralEntero literalEntero, Object params) {
		GeneradorCodigo.push(literalEntero.valor);
		
		return null;
	}

	@Override
	public Object visit(LiteralReal literalReal, Object params) {
		GeneradorCodigo.push(literalReal.valor);
		return null;
	}

	@Override
	public Object visit(LiteralCaracter literalCaracter, Object params) {
		GeneradorCodigo.push(literalCaracter.caracter);
		return null;
	}
	
	@Override
	public Object visit(Aritmetica aritmetica, Object params) {
		aritmetica.expresion1.accept(this, params);
		GeneradorCodigo.convertirA(aritmetica.expresion1.getTipo(),
				aritmetica.getTipo());
		aritmetica.expresion2.accept(this, params);
		GeneradorCodigo.convertirA(aritmetica.expresion2.getTipo(),
				aritmetica.getTipo());
		GeneradorCodigo.aritmetica(aritmetica.tipo, aritmetica.operador);
		return null;
	}
	
	@Override
	public Object visit(Negacion negacion, Object params) {
		negacion.expresion.accept(this, params);
		GeneradorCodigo.not();
		return null;
	}
	
	/* HAY QUE PASAR EL MAYOR TIPO EN LOS CONVERTIR A, NO EL TIPO DE LA COMPARACIï¿½N
	 * PORQUE SI 4 < 4.4 VA A SER 4 < 4 
	 * no hay que andar comprobando si me pasan array o struct porque ya estï¿½ comprobado.
	 * TIPO MAYOR = OP1.TIPO.MAYOR(OP2.TIPO)
	 * VALOR COMPARACION.OP1
	 * GC.CONVERTIR A (OP1.TIPO, MAYOR)
	 * VALOR COMPARACION.OP2
	 * GC.CONVERTIR A (OP2.TIPO, MAYOR)
	 * GC.COMPARACION(OPERADOR, MAYOR)
	 * 
	 * (non-Javadoc)
	 * @see generadorCodigo.VisitorDefaultErrores#visit(ast.Comparacion, java.lang.Object)
	 */
	@Override
	public Object visit(Comparacion comparacion, Object params) {
		comparacion.expresion1.accept(this, params);
		Tipo mayor = comparacion.expresion1.getTipo().mayor(comparacion.expresion2.getTipo());
		GeneradorCodigo.convertirA(comparacion.expresion1.getTipo(), mayor);
		comparacion.expresion2.accept(this, params);
		GeneradorCodigo.convertirA(comparacion.expresion2.getTipo(),
				mayor);
		comparacion.setTipo(mayor);
		GeneradorCodigo.comparacion(comparacion);
		return null;
	}

	@Override
	public Object visit(Logica logica, Object params) {
		logica.expresion1.accept(this, params);
		GeneradorCodigo.convertirA(logica.expresion1.getTipo(), logica.tipo);
		logica.expresion2.accept(this, params);
		GeneradorCodigo.convertirA(logica.expresion2.getTipo(), logica.tipo);
		GeneradorCodigo.condiciones(logica.operador);
		return null;
	}
	
	@Override
	public Object visit(AccesoCampo accesoCampo, Object params){
		accesoCampo.accept(visitorGCDireccion, params);
		GeneradorCodigo.load(accesoCampo.tipo);
		
		return null;
	}
	
	@Override
	public Object visit(Cast cast, Object params){
		cast.expresion.accept(this, params);
		GeneradorCodigo.convertirA(cast.expresion.getTipo(), cast.tipo);
		
		return null;
		
	}
	
	@Override
	public Object visit(AccesoArray accesoArray, Object params){
		accesoArray.accept(visitorGCDireccion, params);
		GeneradorCodigo.load(accesoArray.tipo);
		
		return null;
	}
	
	@Override
	public Object visit(InvocacionFuncion invocacionFuncion, Object params){
		int i = 0;
		for(Expresion exp : invocacionFuncion.argumentos){
			exp.accept(this, params);
			GeneradorCodigo.convertirA(exp.getTipo(), ((TipoFuncion)invocacionFuncion.funcion.tipo).defVariables.get(i).tipo);
			i++;
		}
		GeneradorCodigo.call(invocacionFuncion.funcion.nombre);
		
		return null;
	}
	
	@Override
	public Object visit(InvocacionProcedimiento invocacionProcedimiento, Object params){
		int i = 0;
		for(Expresion exp : invocacionProcedimiento.argumentos){
			exp.accept(this, params);
			GeneradorCodigo.convertirA(exp.getTipo(), ((TipoFuncion)invocacionProcedimiento.funcion.tipo).defVariables.get(i).tipo);
			i++;
		}
		GeneradorCodigo.call(invocacionProcedimiento.funcion.nombre);
		
		return null;
	}
	
	
	@Override
	public Object visit(MenosUnario mu, Object params){
		/*mu.expresion.accept(this, params);
		GeneradorCodigo.push(-1);
		GeneradorCodigo.convertirA(TipoEntero.tipoEntero, mu.expresion.getTipo());
		GeneradorCodigo.mul(mu.expresion.getTipo());*/
		mu.expresion.accept(this, params);
		GeneradorCodigo.convertirA(mu.expresion.getTipo(), mu.getTipo());
		GeneradorCodigo.push(-1);
		GeneradorCodigo.convertirA(TipoEntero.tipoEntero, mu.getTipo());
		GeneradorCodigo.mul(mu.getTipo());
		return null;
	}
	
	@Override
	public Object visit(SumatorioUnitario sumatorioUnitario, Object params){
		// dejo el valor en la pila porque se retornará el valor anterior
		sumatorioUnitario.expresion.accept(this, params);
		sumatorioUnitario.expresion.accept(visitorGCDireccion, params);
		sumatorioUnitario.expresion.accept(this, params);
		GeneradorCodigo.convertirA(sumatorioUnitario.expresion.getTipo(), sumatorioUnitario.getTipo());
		GeneradorCodigo.push(1);
		GeneradorCodigo.convertirA(TipoEntero.tipoEntero, sumatorioUnitario.tipo);
		GeneradorCodigo.add(sumatorioUnitario.tipo);
		GeneradorCodigo.convertirA(sumatorioUnitario.getTipo(), sumatorioUnitario.expresion.getTipo());
		GeneradorCodigo.store(sumatorioUnitario.expresion.getTipo());
		// En caso de que fuese tipoCaracter el ejecutar sacaría entero y cascaría. Para evitar
		// andar poniendo instanceof le cambiamos el tipo.
		sumatorioUnitario.tipo = sumatorioUnitario.expresion.getTipo(); 
		return null;
	}
	

	@Override
	public Object visit(RestaUnitaria restaUnitaria, Object params){
		// dejo el valor en la pila porque se retornará el valor anterior.
		restaUnitaria.expresion.accept(this, params);
		restaUnitaria.expresion.accept(visitorGCDireccion, params);
		restaUnitaria.expresion.accept(this, params);
		GeneradorCodigo.convertirA(restaUnitaria.expresion.getTipo(), restaUnitaria.getTipo());
		GeneradorCodigo.push(1);
		GeneradorCodigo.convertirA(TipoEntero.tipoEntero, restaUnitaria.tipo);
		GeneradorCodigo.sub(restaUnitaria.tipo);
		GeneradorCodigo.convertirA(restaUnitaria.getTipo(), restaUnitaria.expresion.getTipo());
		GeneradorCodigo.store(restaUnitaria.expresion.getTipo());
		// En caso de que fuese tipoCaracter el ejecutar sacaría entero y cascaría. Para evitar
		// andar poniendo instanceof le cambiamos el tipo.
		restaUnitaria.tipo = restaUnitaria.expresion.getTipo(); 
		return null;
	}
	
	@Override
	public Object visit(SumatorioUnitarioBef sumatorioUnitarioBef, Object params){
		sumatorioUnitarioBef.expresion.accept(visitorGCDireccion, params);
		sumatorioUnitarioBef.expresion.accept(this, params);
		GeneradorCodigo.convertirA(sumatorioUnitarioBef.expresion.getTipo(), sumatorioUnitarioBef.getTipo());
		GeneradorCodigo.push(1);
		GeneradorCodigo.convertirA(TipoEntero.tipoEntero, sumatorioUnitarioBef.tipo);
		GeneradorCodigo.add(sumatorioUnitarioBef.tipo);
		GeneradorCodigo.convertirA(sumatorioUnitarioBef.getTipo(), sumatorioUnitarioBef.expresion.getTipo());
		GeneradorCodigo.store(sumatorioUnitarioBef.expresion.getTipo());
		// En caso de que fuese tipoCaracter el ejecutar sacaría entero y cascaría. Para evitar
		// andar poniendo instanceof le cambiamos el tipo.
		sumatorioUnitarioBef.expresion.accept(this, params);
		sumatorioUnitarioBef.tipo = sumatorioUnitarioBef.expresion.getTipo(); 
		return null;
	}

	@Override
	public Object visit(RestaUnitariaBef restaUnitariaBef, Object params){
		restaUnitariaBef.expresion.accept(visitorGCDireccion, params);
		restaUnitariaBef.expresion.accept(this, params);
		GeneradorCodigo.convertirA(restaUnitariaBef.expresion.getTipo(), restaUnitariaBef.getTipo());
		GeneradorCodigo.push(1);
		GeneradorCodigo.convertirA(TipoEntero.tipoEntero, restaUnitariaBef.tipo);
		GeneradorCodigo.sub(restaUnitariaBef.tipo);
		GeneradorCodigo.convertirA(restaUnitariaBef.getTipo(), restaUnitariaBef.expresion.getTipo());
		GeneradorCodigo.store(restaUnitariaBef.expresion.getTipo());
		// En caso de que fuese tipoCaracter el ejecutar sacaría entero y cascaría. Para evitar
		// andar poniendo instanceof le cambiamos el tipo.
		restaUnitariaBef.expresion.accept(this, params);
		restaUnitariaBef.tipo = restaUnitariaBef.expresion.getTipo(); 
		return null;
	}
	

	
	@Override
	public Object visit(OCondTernario oCondTernario, Object params){
		int etiquetas = GeneradorCodigo.dameEtiqueta(2);
		oCondTernario.condicional.accept(this, params);
		GeneradorCodigo.jz("etiqueta" + etiquetas);
		oCondTernario.salidaCierto.accept(this, params);
		GeneradorCodigo.convertirA(oCondTernario.salidaCierto.getTipo(), oCondTernario.tipo);
		GeneradorCodigo.jmp("etiqueta" + (etiquetas + 1));
		GeneradorCodigo.id("etiqueta" + etiquetas);
		oCondTernario.salidaFalso.accept(this, params);
		GeneradorCodigo.convertirA(oCondTernario.salidaFalso.getTipo(), oCondTernario.tipo);
		GeneradorCodigo.id("etiqueta" + (etiquetas + 1));
		
		return null;
		
	}
}
