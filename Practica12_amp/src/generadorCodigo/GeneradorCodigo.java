package generadorCodigo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import ast.Comparacion;
import ast.Tipo;
import ast.tipos.TipoCaracter;
import ast.tipos.TipoEntero;
import ast.tipos.TipoReal;

public class GeneradorCodigo {

	public static FileWriter out;
	public static int contadorEtiquetas;

	public GeneradorCodigo(FileWriter out) {
		this.out = out;
	}

	public static void push(char c) {
		try {
			out.write("\tpushb " + (int) c + "\n");
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void push(int i) {
		try {
			out.write("\tpushi " + i + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void push(double f) {
		try {
			out.write("\tpushf " + f + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void pusha(int direccionMemoria) {
		try {
			out.write("\tpusha " + direccionMemoria + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void pushbp() {
		try {
			out.write("\tpush bp\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void load(Tipo tipo) {
		try {
			out.write("\tload" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void store(Tipo tipo) {
		try {
			out.write("\tstore" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void add(Tipo tipo) {
		try {
			out.write("\tadd" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void line(int linea) {
		try {
			out.write("\n#line\t" + linea + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sub(Tipo tipo) {
		try {
			out.write("\tsub" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void mul(Tipo tipo) {
		try {
			out.write("\tmul" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void div(Tipo tipo) {
		try {
			out.write("\tdiv" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void mod() {
		try {
			out.write("\tmodi\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void out(Tipo tipo) {
		try {
			out.write("\tout" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void in(Tipo tipo) {
		try {
			out.write("\tin" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void enter(int espacioAReservar) {
		try {
			out.write("\tenter " + -espacioAReservar + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void call(String funcionAInvocar) {
		try {
			out.write("\tcall " + funcionAInvocar + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void halt() {
		try {
			out.write("\thalt\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void convertirA(Tipo tipoOrigen, Tipo tipoDestino) {
		try {
			if (tipoOrigen instanceof TipoCaracter
					&& tipoDestino instanceof TipoEntero)

				out.write("\tb2i\n");
			else if (tipoOrigen instanceof TipoCaracter
					&& tipoDestino instanceof TipoReal)
				out.write("\tb2i\n\ti2f\n");
			else if (tipoOrigen instanceof TipoEntero
					&& tipoDestino instanceof TipoReal)
				out.write("\ti2f\n");
			else if (tipoOrigen instanceof TipoReal
					&& tipoDestino instanceof TipoEntero)
				out.write("\tf2i\n");
			else if (tipoOrigen instanceof TipoReal
					&& tipoDestino instanceof TipoCaracter)
				out.write("\tf2i\n\ti2b\n");
			else if (tipoOrigen instanceof TipoEntero
					&& tipoDestino instanceof TipoCaracter)
				out.write("\ti2b\n");
			else if (tipoOrigen == tipoDestino)
				;
			else
				throw new RuntimeException("No se pueden convertir los tipos");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void comparar(Tipo tipo, String operador) { try { switch
	 * (operador) { case ">": out.write("gt" + tipo.sufijo() + "\n"); break;
	 * case "<": out.write("lt" + tipo.sufijo() + "\n"); break; case ">=":
	 * out.write("ge" + tipo.sufijo() + "\n"); break; case "<=": out.write("le"
	 * + tipo.sufijo() + "\n"); break; case "!=": out.write("ne" + tipo.sufijo()
	 * + "\n"); break; case "==": out.write("eq" + tipo.sufijo() + "\n"); break;
	 * 
	 * default: break; } } catch (IOException e) { e.printStackTrace(); } }
	 */

	public static void comparacion(Comparacion comparacion) {
		switch (comparacion.operador) {
		case "==":
			eq(comparacion.expresion1.getTipo().mayor(
					comparacion.expresion2.getTipo()));
			break;
		case "<=":
			le(comparacion.expresion1.getTipo().mayor(
					comparacion.expresion2.getTipo()));
			break;
		case ">=":
			ge(comparacion.expresion1.getTipo().mayor(
					comparacion.expresion2.getTipo()));
			break;
		case "!=":
			ne(comparacion.expresion1.getTipo().mayor(
					comparacion.expresion2.getTipo()));
			break;
		case "<":
			lt(comparacion.expresion1.getTipo().mayor(
					comparacion.expresion2.getTipo()));
			break;
		case ">":
			gt(comparacion.expresion1.getTipo().mayor(
					comparacion.expresion2.getTipo()));
			break;

		}
	}

	public static void eq(Tipo tipo) {
		try {
			out.write("\teq" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void le(Tipo tipo) {
		try {
			out.write("\tle" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void ge(Tipo tipo) {
		try {
			out.write("\tge" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void ne(Tipo tipo) {
		try {
			out.write("\tne" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void lt(Tipo tipo) {
		try {
			out.write("\tlt" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void gt(Tipo tipo) {
		try {
			out.write("\tgt" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void condiciones(String operador) {
		try {
			switch (operador) {
			case "&&":
				out.write("\tand\n");
				break;
			case "||":
				out.write("\tor\n");
				break;

			default:
				break;
			}

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void not() {
		try {
			out.write("\tnot\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void ret(int tamRetorno, int varsLocales, int params) {
		try {
			out.write("\tret " + tamRetorno + ", " + (-varsLocales) + ", "
					+ params + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void id(String id) {
		try {
			out.write(id + ":\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void source(String source) {
		try {
			out.write("#source \"" + source + "\"\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void comentario(String comentario) {
		try {
			out.write("\t\' " + comentario + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void aritmetica(Tipo tipo, String operador) {
		switch (operador) {
		case "+":
			add(tipo);
			break;
		case "-":
			sub(tipo);
			break;
		case "*":
			mul(tipo);
			break;
		case "/":
			div(tipo);
			break;
		case "%":
			mod();
			break;

		default:
			break;
		}
	}

	public static void jz(String etiqueta) {
		try {
			out.write("\tjz " + etiqueta + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void jmp(String etiqueta) {
		try {
			out.write("\tjmp " + etiqueta + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int dameEtiqueta(int numEtiquetas) {
		int retorno = contadorEtiquetas;
		contadorEtiquetas += numEtiquetas;

		return retorno;
	}

	public static void pop(Tipo tipo) {
		try {
			out.write("\tpop" + tipo.sufijo() + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
