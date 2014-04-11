package ast;

/**
 * @author Álvaro García
 *
 */
public class Aritmetica implements Expresion {
	
	public Expresion expresion1 = null;
	public Expresion expresion2 = null;
	public int linea;
	public int columna;
	public String operador;
	public boolean lvalue;
	public Tipo tipo;
	
	/**
	 * @param linea
	 * @param columna
	 * @param expresion1
	 * @param operador
	 * @param expresion2
	 */
	public Aritmetica (int linea, int columna, Expresion expresion1, String operador, Expresion expresion2){
		this.linea = linea;
		this.columna = columna;
		this.operador = operador;
		this.expresion1 = expresion1;
		this.expresion2 = expresion2;
	}

	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	
	@Override
	public String toString(){
		//return "(" + expresion1 + ")" + operador + "("  + expresion2 + ")";
		return expresion1 + operador + expresion2;
	}


	@Override
	public boolean getLValue() {
		return lvalue;
	}


	@Override
	public Tipo getTipo() {
		return tipo;
	}


	@Override
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
