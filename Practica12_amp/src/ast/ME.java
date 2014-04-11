package ast;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;



public class ME {
	
	public static ME mE = new ME();
	public  List<TipoError> errores = new ArrayList<TipoError>();
	
	
	public void addError(TipoError error){
		this.errores.add(error);
	}
	
	public boolean huboErrores(){
		if(errores.size() == 0)
			return false;
		else
			return true;
	}
	
	 public void mostrarErrores(PrintStream ps) {
	        for (TipoError error : errores)
	            ps.print(error.toString());
	    }
	 
	 
		
	
	

}
