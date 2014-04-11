package ast;
 /**
 * @author Álvaro García
 *
 *Interfaz del nodo del Arbol Abstracto Sintactico
 */
public interface NodoAST {
	
	/**
	 * En caso de depuración me servirá para saber en que línea falló
	 * @return entero con la línea donde se encuentra el código
	 */
	//public int getLinea(); ya no se necesita puesto que se accede 
	//directamente (variables públicas por cuestión de eficiencia)
	
	/**
	 * En caso de depuración me servirá para saber en que columna falló
	 * @return entero con la columna donde se encuentra el código
	 */
	//public int getColumna(); ya no se necesita puesto que se accede 
	//directamente (variables públicas por cuestión de eficiencia)

	public void accept(Visitor v, Object params);
	public boolean getLValue();
}
