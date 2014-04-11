// ************  Código a incluir ********************

package lexico;
import sintactico.Parser;

import ast.*;


%%
// ************  Opciones ********************
// % debug // * Opción para depurar
%byaccj
%class Lexico
%public
%unicode
%line
%column

%{
// ************  Atributos y métodos ********************
// * El analizador sintáctico
private Parser parser;
public void setParser(Parser parser) {
	this.parser=parser;
}

// * Para acceder al número de línea (yyline es package)
public int getYyline() { 
	// * Flex empieza en cero
	return yyline+1;
}

// * Para acceder al número de columna (yycolumn es package)
public int getYycolumn() { 
	// * Flex empieza en cero
	return yycolumn+1;
}

%}

// ************  Patrones (macros) ********************
ConstanteEntera = [0-9]+
ID = ([a-z]|[A-Z])([a-z]|[A-Z]|[0-9]|[ñÑáéíóúÁÉÍÓÚ])*
ComentarioUnaLinea = ("//"~(\r|\n|\r\n))
ComentarioVariasLineas = "/*"~"*/"
Basura = \t|\n|\r\n|\f
ConstanteReal = ({ConstanteEntera} "."? [0-9]*) [eE] [+-]? {ConstanteEntera} | ({ConstanteEntera} "." [0-9]*)
OtrosOperadores = [{};,+"-"*/%=><()"[""]"!.]
ConstanteCaracter = "'"."'"
ConstanteCaracter2 = "'"\\{ConstanteEntera}"'"



%%
// ************  Acciones ********************

// * Constante Entera
{Basura}			{ }	
" "					{ }
"++"				{ parser.setYylval(yytext());
					  return Parser.MASMAS;	}
"--"				{ parser.setYylval(yytext());
					  return Parser.MENOSMENOS;	}
"?"					{ parser.setYylval(yytext());
					  return Parser.INTERROG;	}
":"					{ parser.setYylval(yytext());
					  return Parser.DOSPUNTOS;	}
{OtrosOperadores}	{ parser.setYylval(yytext());
					  return yytext().charAt(0);	}		  
{ComentarioUnaLinea} | {ComentarioVariasLineas}	{ } //Ignoro los comentarios
{ConstanteEntera}	{ parser.setYylval(new Integer(yytext()));
         			  return Parser.CTE_ENTERA;  }
{ConstanteReal}		{ parser.setYylval(new Double(yytext()));
					  return Parser.CTE_REAL;	}
 "'\126'"              { parser.setYylval(new Character('~'));
                      return Parser.CTE_CARACTER;    }
"'\\n'"             { parser.setYylval(new Character('\n'));
                      return Parser.CTE_CARACTER;    }
"'\\t'"             { parser.setYylval(new Character('\t'));
                      return Parser.CTE_CARACTER;    }
{ConstanteCaracter} { parser.setYylval(new Character(yytext().charAt(1)));
                      return Parser.CTE_CARACTER;   }
{ConstanteCaracter2} {  parser.setYylval(new Character((char)Integer.parseInt(yytext().substring(2,yytext().length()-1))));
						return Parser.CTE_CARACTER;	}
"&&"				{ parser.setYylval(yytext());
					  return Parser.Y;	}
"||"				{ parser.setYylval(yytext());
					  return Parser.O;	}
"=="				{ parser.setYylval(yytext());
					  return Parser.IGUALDAD;	}
"!="				{ parser.setYylval(yytext());
					  return Parser.DISTINTO;	}
"<="				{ parser.setYylval(yytext());
					  return Parser.MENORIGUAL;	}
">="				{ parser.setYylval(yytext());
					  return Parser.MAYORIGUAL;	}
"read"				{ parser.setYylval(yytext());
					  return Parser.READ;	}
"write"				{ parser.setYylval(yytext());
					  return Parser.WRITE;	}
"while"				{ parser.setYylval(yytext());
					  return Parser.WHILE;	}
"if"				{ parser.setYylval(yytext());
					  return Parser.IF;	}
"else"				{ parser.setYylval(yytext());
					  return Parser.ELSE;	}
"int"				{ parser.setYylval(yytext());
					  return Parser.INT;	}
"double"			{ parser.setYylval(yytext());
					  return Parser.DOUBLE;	}
"char"				{ parser.setYylval(yytext());
					  return Parser.CHAR;	}
"struct"			{ parser.setYylval(yytext());
					  return Parser.STRUCT;	}
"main()"			{ parser.setYylval(yytext());
					  return Parser.MAIN;	}
"return"			{ parser.setYylval(yytext());
					  return Parser.RETURN;	}
"void"				{ parser.setYylval(yytext());
					  return Parser.VOID;	}
{ID}				{ parser.setYylval (yytext());
					  return Parser.ID;	}
.					{ new TipoError("Error Léxico en línea"  + this.getYyline() + " y columna " + this.getYycolumn() + ":\n\tCarácter \'" + yycharat(0) + "\' desconocido.", new TipoError());}



