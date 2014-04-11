%{
// * Declaraciones de código Java
// * Se sitúan al comienzo del archivo generado
// * El package lo añade yacc si utilizamos la opción -Jpackage
import lexico.Lexico;
import java.io.Reader;
import ast.*;
import ast.tipos.*;
import java.util.List;
import java.util.ArrayList;
%}

// * Declaraciones Yacc
%token CTE_ENTERA
%token ID
%token MAIN
%token READ
%token WRITE
%token WHILE
%token IF
%token ELSE
%token INT
%token DOUBLE
%token CHAR
%token IGUALDAD
%token DISTINTO
%token MAYORIGUAL
%token MENORIGUAL
%token CTE_REAL
%token CTE_CARACTER
%token CTE_STRING
%token O
%token Y
%token VOID
%token STRUCT
%token RETURN
%token MASMAS
%token MENOSMENOS
%token INTERROG
%token DOSPUNTOS

//	Precedencia de Operadores. Cuanto más arriba están menor precedencia tienen.
//	Precedencia de Operadores. Cuanto más arriba están menor precedencia tienen.

%right '='
%left ">" MAYORIGUAL "<" MENORIGUAL DISTINTO IGUALDAD Y O
%left '!'
%left '+' '-'
%left '*' '/' '%'
%left '.'
%NONASSOC CONDICIONELSE
%NONASSOC ELSE
%right MENOSUNARIO
%left '[' ']'
%NONASSOC '('
%NONASSOC ')'
%right MASMAS MENOSMENOS

%%
// * Gramática y acciones Yacc
programa: definiciones main 	   { List<Definicion> programa = new ArrayList<Definicion>();
                                          programa.addAll((List<Definicion>) $1);
                                          programa.add((DefinicionFuncion)$2);
                                          ast=new Programa(lexico.getYyline(), lexico.getYycolumn(), programa);   }
         ;
         
main: VOID MAIN '{' defvariables sentencias '}'		{ List<Sentencia> listaSentencias = (List<Sentencia>) $4;
																		listaSentencias.addAll((List<Sentencia>) $5);  
																		$$ = new DefinicionFuncion(lexico.getYyline(), lexico.getYycolumn(), "main", new TipoFuncion(TipoVoid.tipoVoid, new ArrayList<DefVariable>()) , listaSentencias);    }
		;
		
defvariables: defvariables defvariable			{ List<DefVariable> definiciones = (List<DefVariable>)$1; definiciones.addAll((List<DefVariable>) $2); $$=definiciones;	}
         |	/* VACIO */						{ $$=new ArrayList<DefVariable>();	}
         ;
 

defvariable: tipoSimple variables ';'            { List<DefVariable> definiciones = new ArrayList<DefVariable>(); 
                                             List<String> variables = (List<String>)$2;
                                             for(String st : variables){
                                             DefVariable dV = new DefVariable(lexico.getYyline(), lexico.getYycolumn(), st, (Tipo)$1);
                                             if(definiciones.contains(dV)){ //ERROR
                                                    new TipoError("Variable Repetida: " + st, dV);
                                             } else {definiciones.add(dV);} } $$=definiciones;    }                                             
                                                                                          
            | tipoComplejo variables ';'        { List<DefVariable> definiciones = new ArrayList<DefVariable>(); 
                                             List<String> variables = (List<String>)$2;
                                             for(String st : variables){
                                              DefVariable dV = new DefVariable(lexico.getYyline(), lexico.getYycolumn(), st, (Tipo)$1);
                                             if(definiciones.contains(dV)){ //ERROR
                                                    new TipoError("Variable Repetida: " + st, dV);
                                             } else {definiciones.add(dV);}  } $$=definiciones;    }         
        ;
        
definiciones: definiciones definicion		{ ((List<Definicion>) $1).addAll((List<Definicion>)$2); $$ = $1; }
			| /* VACIO */					{ $$ = new ArrayList<Definicion>();	}
			
definicion: tipoSimple variables ';'            { List<DefVariable> definiciones = new ArrayList<DefVariable>(); 
                                             List<String> variables = (List<String>)$2;
                                             for(String st : variables){
                                             DefVariable dV = new DefVariable(lexico.getYyline(), lexico.getYycolumn(), st, (Tipo)$1);
                                             if(definiciones.contains(dV)){ //ERROR
                                                    new TipoError("Variable Repetida: " + st, dV);
                                             } else {definiciones.add(dV);} } $$=definiciones;    }                                             
                                                                                          
            | tipoComplejo variables ';'        { List<DefVariable> definiciones = new ArrayList<DefVariable>(); 
                                             List<String> variables = (List<String>)$2;
                                             for(String st : variables){
                                              DefVariable dV = new DefVariable(lexico.getYyline(), lexico.getYycolumn(), st, (Tipo)$1);
                                             if(definiciones.contains(dV)){ //ERROR
                                                    new TipoError("Variable Repetida: " + st, dV);
                                             } else {definiciones.add(dV);}  } $$=definiciones;    }
           | defFuncion         			 { List<DefinicionFuncion> listaFuncion = new ArrayList<DefinicionFuncion>(); listaFuncion.add((DefinicionFuncion)$1); $$=listaFuncion;	}
        ;

variables: ID                          { List<String> variables = new ArrayList<String>(); variables.add((String)$1); $$=variables;	}
         | variables ',' ID            { List<String> variables = (List<String>)$1; variables.add((String) $3); 
         								 $$=variables;}
         ;
		 
sentencias: sentencias sentencia 		{ List<Sentencia> sentencias = (List<Sentencia>)$1; sentencias.add((Sentencia)$2); $$=sentencias;	}
         | 									{ $$=new ArrayList<Sentencia>();	}
         ;






sentencia: expresion '=' expresion ';'    { $$=new Asignacion(lexico.getYyline(), lexico.getYycolumn(), (Expresion)$1, (Expresion)$3);    }
         | READ expresiones ';'        { $$=new Lectura(lexico.getYyline(), lexico.getYycolumn(), (List<Expresion>)$2);      }
         | WRITE expresiones ';'      { $$=new Escritura(lexico.getYyline(), lexico.getYycolumn(), (List<Expresion>)$2);    }
         | condicion                 
         | while
         | RETURN expresion ';'         { $$=new Return (lexico.getYyline(), lexico.getYycolumn(),(Expresion) $2);    }
         | ID'('expresiones')' ';'       {$$=new InvocacionProcedimiento(lexico.getYyline(), lexico.getYycolumn(), new Variable(lexico.getYyline(), lexico.getYyline(), (String) $1), (List<Expresion>) $3);    }
         | ID'(' ')' ';'       			{$$=new InvocacionProcedimiento(lexico.getYyline(), lexico.getYycolumn(), new Variable(lexico.getYyline(), lexico.getYyline(), (String) $1), new ArrayList<Expresion>());    }
       	 | expresion MASMAS	';'		{ $$ = new SumatorioUnitario(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1); }
         | expresion MENOSMENOS	';'		{ $$ = new RestaUnitaria(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1);	}
         | MASMAS expresion ';'	  { $$ = new SumatorioUnitarioBef(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $2);	}
         | MENOSMENOS expresion ';' { $$ = new RestaUnitariaBef(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $2);	}
         ;
         
  
  
 while: WHILE '(' expresion ')' sentencia                 { List<Sentencia> sentencia = new ArrayList<Sentencia>();
                                                          sentencia.add((Sentencia) $5);
                                                          $$ = new SentenciaWhile(lexico.getYyline(), lexico.getYycolumn(),(Expresion) $3, sentencia); }
          | WHILE '('expresion ')' '{' sentencias '}'     {$$ = new SentenciaWhile(lexico.getYyline(), lexico.getYycolumn(),
                                                                (Expresion) $3, (List<Sentencia>) $6); }
          ;
          
         
         
condicion: IF '(' expresion ')' sentencia  condicion2                { List<Sentencia> sentenciaIf = new ArrayList<Sentencia>(); sentenciaIf.add((Sentencia) $5); $$ = new SentenciaIF(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $3, sentenciaIf , (List<Sentencia>) $6);    }
           | IF '(' expresion ')' '{' sentencias '}' condicion2    { List<Sentencia> sentenciaIf = new ArrayList<Sentencia>(); sentenciaIf.addAll((List<Sentencia>) $6); $$ = new SentenciaIF(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $3, sentenciaIf, (List<Sentencia>) $8);    }
           ;
           
condicion2: ELSE sentencia                                          { List<Sentencia> sentenciaUnica = new ArrayList<Sentencia>(); sentenciaUnica.add((Sentencia) $2); $$ = sentenciaUnica; }
            | ELSE '{' sentencias '}'                              { $$=(List<Sentencia>)$3;    }
            | /* VACIO */  %prec CONDICIONELSE                                        { $$=new ArrayList<Sentencia>();    }
            ;

expresiones: expresion                 { List<Expresion> expresiones = new ArrayList<Expresion>(); expresiones.add((Expresion)$1); $$=expresiones; }
         | expresion ',' expresiones       { List<Expresion> expresiones = (List<Expresion>)$3; expresiones.add((Expresion)$1); $$=expresiones; }
         ;
         

         

defFuncion:     tipoSimple ID '(' parametros ')' '{' defvariables sentencias'}'   { List<Sentencia> listaSentencias = (List<Sentencia>) $7;
																		listaSentencias.addAll((List<Sentencia>) $8);  
																		$$ = new DefinicionFuncion(lexico.getYyline(), lexico.getYycolumn(), (String) $2, new TipoFuncion((Tipo) $1, (List<DefVariable>) $4), listaSentencias);    }
				|  VOID ID '(' parametros ')' '{' defvariables sentencias'}'   { List<Sentencia> listaSentencias = (List<Sentencia>) $7;
																		listaSentencias.addAll((List<Sentencia>) $8);  
																		$$ = new DefinicionFuncion(lexico.getYyline(), lexico.getYycolumn(), (String) $2, new TipoFuncion(TipoVoid.tipoVoid, (List<DefVariable>) $4), listaSentencias);    }									
				
		;
		
tipo: tipoSimple 						{ $$ = (Tipo) $1;	}
	| tipoComplejo						{ $$ = (Tipo) $1;	}
	;

tipoSimple:      INT                       { $$=TipoEntero.tipoEntero; } 
         | DOUBLE                     { $$=TipoReal.tipoReal; }
         | CHAR                       { $$=TipoCaracter.tipoCaracter; }
         ;
         
tipoComplejo: tipo '[' CTE_ENTERA ']'    { $$=new TipoArray((int)$3, (Tipo) $1); }
         | STRUCT  '{' campos '}'      { $$=new TipoRegistro((List<Campo>) $3);    }
         ;
         

campo: tipo variables ';'            { List<Campo> definiciones = new ArrayList<Campo>(); 
                                             List<String> variables = (List<String>)$2;
                                             for(String st : variables){definiciones.add(new Campo(lexico.getYyline(), lexico.getYycolumn(), st, (Tipo)$1));} $$=definiciones; }
         ;                            

campos: campos campo                 { List<Campo> definiciones = (List<Campo>)$1; definiciones.addAll((List<Campo>) $2); $$=definiciones; }
         |  /* VACIO */              { $$=new ArrayList<Campo>();  }
         ;



         
parametros:  parametro  parametros2       { List<DefVariable> parametros = (List<DefVariable>) $2; parametros.add((DefVariable)$1); $$ = parametros; }
             | /* VACIO */                { $$ = new ArrayList<DefVariable>(); }
             ;

parametros2: ',' parametro parametros2    { List<DefVariable> parametros = (List<DefVariable>) $3; parametros.add((DefVariable)$2); $$ = parametros; }
             | /* VACIO */                { $$ = new ArrayList<DefVariable>(); }
             ;
             
parametro: tipoSimple ID      { $$ = new DefVariable(lexico.getYyline(), lexico.getYycolumn(), (String)$2, (Tipo) $1);}
			;


expresion: expresion '+' expresion      { $$=new Aritmetica(lexico.getYyline(), lexico.getYycolumn(), (Expresion)$1, "+", (Expresion)$3); }
         | expresion '*' expresion      { $$=new Aritmetica(lexico.getYyline(), lexico.getYycolumn(), (Expresion)$1, "*", (Expresion)$3); }
         | expresion '/' expresion      { $$=new Aritmetica(lexico.getYyline(), lexico.getYycolumn(), (Expresion)$1, "/", (Expresion)$3); }
         | expresion '%' expresion      { $$=new Aritmetica(lexico.getYyline(), lexico.getYycolumn(), (Expresion)$1, "%", (Expresion)$3); }
         | expresion '-' expresion      { $$=new Aritmetica(lexico.getYyline(), lexico.getYycolumn(), (Expresion)$1, "-", (Expresion)$3); }
         | '-' expresion %prec MENOSUNARIO   { $$=new MenosUnario(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $2); }
         | expresion MASMAS			{ $$ = new SumatorioUnitario(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1); }
         | expresion MENOSMENOS			{ $$ = new RestaUnitaria(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1);	}
         | MASMAS expresion	  { $$ = new SumatorioUnitarioBef(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $2);	}
         | MENOSMENOS expresion  { $$ = new RestaUnitariaBef(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $2);	}
         | CTE_ENTERA                   { $$=new LiteralEntero(lexico.getYyline(), lexico.getYycolumn(), (Integer)$1);}
         | ID                           { $$=new Variable(lexico.getYyline(), lexico.getYycolumn(), (String)$1);    }
         | CTE_CARACTER					{ $$=new LiteralCaracter(lexico.getYyline(), lexico.getYycolumn(), (Character)$1);	}
         | CTE_REAL                     { $$ = new LiteralReal(lexico.getYyline(), lexico.getYycolumn(), (Double) $1); }
         | expresion'['expresion']'     { $$=new AccesoArray(lexico.getYyline(), lexico.getYycolumn(), (Expresion)$1, (Expresion) $3);  }
         | '(' expresion ')'            { $$=(Expresion) $2;    }
         | ID'('expresiones')'          { $$=new InvocacionFuncion(lexico.getYyline(), lexico.getYycolumn(), new Variable(lexico.getYyline(), lexico.getYyline(), (String) $1), (List<Expresion>) $3);     }
         | ID'(' ')'          { $$=new InvocacionFuncion(lexico.getYyline(), lexico.getYycolumn(), new Variable(lexico.getYyline(), lexico.getYyline(), (String) $1), new ArrayList<Expresion>());     }
         | '(' tipoSimple ')' expresion { $$ = new Cast(lexico.getYyline(), lexico.getYycolumn(), (Tipo) $2, (Expresion) $4);    }
         | expresion '>' expresion         { $$ = new Comparacion(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1, ">", (Expresion) $3);    }
         | expresion MAYORIGUAL expresion        { $$ = new Comparacion(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1, ">=", (Expresion) $3);    }
         | expresion '<' expresion         { $$ = new Comparacion(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1, "<", (Expresion) $3);    }
         | expresion MENORIGUAL expresion        { $$ = new Comparacion(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1, "<=", (Expresion) $3);    }
         | expresion DISTINTO expresion        { $$ = new Comparacion(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1, "!=", (Expresion) $3);    }
         | expresion IGUALDAD expresion        { $$ = new Comparacion(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1, "==", (Expresion) $3);    }
         | expresion Y expresion        { $$ = new Logica(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1, "&&", (Expresion) $3);    }
         | expresion O expresion        { $$ = new Logica(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $1, "||", (Expresion) $3);    }
         | '!' expresion                   { $$ = new Negacion(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $2);    }
         | expresion '.' ID               { $$ = new AccesoCampo(lexico.getYyline(), lexico.getYycolumn(),(Expresion) $1, (String) $3);    }
         | '(' expresion INTERROG expresion DOSPUNTOS expresion')'	{ $$ = new OCondTernario(lexico.getYyline(), lexico.getYycolumn(), (Expresion) $2, (Expresion) $4, (Expresion) $6); }
         ;
         







		 

 
%%

// * Código Java
// * Se crea una clase "Parser", lo que aquí ubiquemos será:
//	- Atributos, si son variables
//	- Métodos, si son funciones
//   de la clase "Parser"

// * Estamos obligados a implementar:
//	int yylex()
//	void yyerror(String)

// * Referencia al analizador léxico
private Lexico lexico;

// Declaro el NodoAST
public NodoAST ast;



// * Llamada al analizador léxico
private int yylex () {
    int token=0;
    try { 
	token=lexico.yylex(); 
    } catch(Throwable e) {
	    System.err.println ("Error Léxico en línea " + lexico.getYyline()+
		" y columna "+lexico.getYycolumn()+":\n\t"+e); 
    }
    return token;
}

// * Manejo de Errores Sintácticos
public void yyerror (String error) {
    /*System.err.println ("Error Sintáctico en línea " + lexico.getYyline()+
		" y columna "+lexico.getYycolumn()+":\n\t"+error);*/
		new TipoError("Error sintáctico en la línea " + lexico.getYyline() + " y columna " + lexico.getYycolumn() + ":\n\t" + error, new TipoError());
		
}

// * El yylval no es un atributo público
public Object getYylval() {
    	return yylval;
}
public void setYylval(Object yylval) {
        this.yylval = yylval;
}

// * Constructor del Sintáctico
public Parser(Lexico lexico) {
	this.lexico = lexico;
	lexico.setParser(this);
}

