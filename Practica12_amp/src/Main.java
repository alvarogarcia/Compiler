import semantico.VisitorIdentificacion;
import semantico.VisitorSemantico;
import sintactico.Parser;
import generadorCodigo.GeneradorCodigo;
import generadorCodigo.VisitorGCEjecutar;
import generadorCodigo.VisitorOffset;
import introspector.model.IntrospectorModel;
import introspector.view.IntrospectorTree;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ast.ME;

import lexico.Lexico;

/**
 * Prueba del analizador l�xico.<br/>
 * Dise�o de Lenguajes de Programaci�n.<br/>
 * Escuela de Ingenier�a Inform�tica.<br/>
 * Universidad de Oviedo <br/>
 * 
 * @author Francisco Ortin
 */

public class Main {

	public static void main(String args[]) throws IOException {
		if (args.length < 1) {
			System.err.println("Necesito el archivo de entrada.");
			return;
		}

		FileReader fr = null;
		try {
			fr = new FileReader(args[0]);
		} catch (IOException io) {
			System.err.println("El archivo " + args[0]
					+ " no se ha podido abrir.");
			return;
		}

		Lexico lexico = new Lexico(fr);
		Parser parser = new Parser(lexico);
		/*
		 * int token; while ((token=lexico.yylex())!=0) {
		 * System.out.println("Linea: "+lexico.getYyline()+
		 * ", columna: "+lexico.getYycolumn()+ ", token: "+token+
		 * ", valor sem�ntico: "+parser.getYylval()+"."); }
		 */
		String archivoSalida = "salidaAlvaro.txt";
		FileWriter fw = new FileWriter(archivoSalida);
		
		parser.run();
		if(parser.ast != null){
		parser.ast.accept(new VisitorIdentificacion(), null);
		parser.ast.accept(new VisitorSemantico(), null);}
		if (ME.mE.huboErrores())
			ME.mE.mostrarErrores(System.err);
		else {
			parser.ast.accept(new VisitorOffset(), null);
			GeneradorCodigo.out = fw;
			GeneradorCodigo.source(args[0]);
			parser.ast.accept(new VisitorGCEjecutar(), null);
			fw.close();
			IntrospectorModel modelo = new IntrospectorModel("Programa",
					parser.ast);
			new IntrospectorTree("Introspector", modelo);
		}
	}

}