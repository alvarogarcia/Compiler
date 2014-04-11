package ast;

public class DefVariable implements Definicion, Sentencia {
	
	public int linea, columna;
	public String nombre;
	public Tipo tipo;
	public int ambito;
	public boolean lvalue;
	public int offset;

	
	public DefVariable(int linea, int columna, String nombre, Tipo tipo){
		this.linea = linea;
		this.columna = columna;
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	
	public void accept(Visitor v, Object params){
		v.visit(this, params);
	}
	
	
	
	@Override
	public String toString(){
		return nombre;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefVariable other = (DefVariable) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}


	@Override
	public boolean getLValue() {
		return lvalue;
	}


	@Override
	public String getNombre() {
		return nombre;
	}


	@Override
	public void setAmbito(int ambito) {
		this.ambito = ambito;
	}


	@Override
	public int getAmbito() {
		return ambito;
	}


	@Override
	public Tipo getTipo() {
		return tipo;
	}


	@Override
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	@Override
	public int getLinea(){
		return this.linea;
	}
	

}
