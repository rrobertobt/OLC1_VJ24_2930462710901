package edu.robertob.olc1.vj24.Analysis;

//importaciones
import java_cup.runtime.Symbol;
import java.util.LinkedList;
//import excepciones.Errores;

%%

//codigo de usuario
%{
//    public LinkedList<Errores> listaErrores = new LinkedList<>();
%}

%init{
    yyline = 1;
    yycolumn = 1;
//    listaErrores = new LinkedList<>();
%init}

//caracteristicas de jflex
%cup
%class JCLexer

%public
%line
%char
%column
%full
//%debug
%ignorecase

OPENPAR="("
CLOSEPAR=")"
PLUS="+"
MINUS="-"
ASTERISK="*"
DOUBLEASTERISK="**"
SLASH="/"
MODULO="%"
EQUALS="="
DOUBLEEQUALS="=="
EXCLAMATION="!"
EXCLAMATIONEQUALS="!="
LESS="<"
LESSEQUALS="<="
GREATER=">"
GREATEREQUALS=">="
ENDLINE=";"
COLON=":"
OPENBRACE="{"
CLOSEBRACE="}"
BLANKS=[\ \r\t\f\n]+
INTEGER=[0-9]+
DECIMAL=[0-9]+"."[0-9]+
ID=[a-zA-z][a-zA-Z0-9_]*
STRING = [\"]([^\"])*[\"]
CHAR = [']([^\'])*[']
COMMENT_ONE_LINE = [\/]{2}.*
COMMENT_MULTIPLE_LINES = [\/][*]([^\*]|[\*][^\/])*[\*][\/]

RW_PRINT="println"
RW_INT="int"
RW_DOUBLE="double"
RW_STRING="string"
RW_CHAR="char"
RW_IF="if"
RW_TRUE="true"
RW_FALSE="false"
RW_BOOL="bool"
RW_VAR="var"
RW_CONST="const"

%%
<YYINITIAL> {RW_PRINT} {return new Symbol(sym.RW_PRINT, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_INT} {return new Symbol(sym.RW_INT, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_DOUBLE} {return new Symbol(sym.RW_DOUBLE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_STRING} {return new Symbol(sym.RW_STRING, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_CHAR} {return new Symbol(sym.RW_CHAR, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_BOOL} {return new Symbol(sym.RW_BOOL, yyline, yycolumn,yytext());}

//<YYINITIAL> {TRUE} {return new Symbol(sym.TRUE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_TRUE} {return new Symbol(sym.RW_TRUE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_FALSE} {return new Symbol(sym.RW_FALSE, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_CONST} {return new Symbol(sym.RW_CONST, yyline, yycolumn,yytext());}
<YYINITIAL> {RW_VAR} {return new Symbol(sym.RW_VAR, yyline, yycolumn,yytext());}
//<YYINITIAL> {FALSE} {return new Symbol(sym.FALSE, yyline, yycolumn,yytext());}
//<YYINITIAL> {IF} {return new Symbol(sym.IF, yyline, yycolumn,yytext());}
//<YYINITIAL> {BOOL} {return new Symbol(sym.BOOL, yyline, yycolumn,yytext());}

<YYINITIAL> {ID} {return new Symbol(sym.ID, yyline, yycolumn,yytext());}

<YYINITIAL> {DECIMAL} {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}
<YYINITIAL> {INTEGER} {return new Symbol(sym.INTEGER, yyline, yycolumn,yytext());}

<YYINITIAL> {STRING} {
    String foundString = yytext();
    String stringObj = foundString.substring(1, foundString.length()-1);
    return new Symbol(sym.STRING, yyline, yycolumn, stringObj);
    }

<YYINITIAL> {CHAR} {
    String foundString = yytext();
    String stringObj = foundString.substring(1, foundString.length()-1);
    // Remember to transform the string to a char in CUP
    return new Symbol(sym.CHAR, yyline, yycolumn, stringObj);
}

<YYINITIAL> {ENDLINE} {return new Symbol(sym.ENDLINE, yyline, yycolumn,yytext());}
<YYINITIAL> {COLON} {return new Symbol(sym.COLON, yyline, yycolumn,yytext());}
<YYINITIAL> {OPENPAR} {return new Symbol(sym.OPENPAR, yyline, yycolumn,yytext());}
<YYINITIAL> {CLOSEPAR} {return new Symbol(sym.CLOSEPAR, yyline, yycolumn,yytext());}
<YYINITIAL> {OPENBRACE} {return new Symbol(sym.OPENBRACE, yyline, yycolumn,yytext());}
<YYINITIAL> {CLOSEBRACE} {return new Symbol(sym.CLOSEBRACE, yyline, yycolumn,yytext());}


<YYINITIAL> {PLUS} {return new Symbol(sym.PLUS, yyline, yycolumn,yytext());}
<YYINITIAL> {MINUS} {return new Symbol(sym.MINUS, yyline, yycolumn,yytext());}
<YYINITIAL> {DOUBLEASTERISK} {return new Symbol(sym.DOUBLEASTERISK, yyline, yycolumn,yytext());}
<YYINITIAL> {ASTERISK} {return new Symbol(sym.ASTERISK, yyline, yycolumn,yytext());}
<YYINITIAL> {SLASH} {return new Symbol(sym.SLASH, yyline, yycolumn,yytext());}
<YYINITIAL> {MODULO} {return new Symbol(sym.MODULO, yyline, yycolumn,yytext());}
      // double equals
<YYINITIAL> {DOUBLEEQUALS} {return new Symbol(sym.DOUBLEEQUALS, yyline, yycolumn,yytext());}
      // single equals
<YYINITIAL> {EQUALS} {return new Symbol(sym.EQUALS, yyline, yycolumn,yytext());}
<YYINITIAL> {LESSEQUALS} {return new Symbol(sym.LESSEQUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {GREATEREQUALS} {return new Symbol(sym.GREATEREQUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {LESS} {return new Symbol(sym.LESS, yyline, yycolumn,yytext());}
<YYINITIAL> {GREATER} {return new Symbol(sym.GREATER, yyline, yycolumn,yytext());}
      <YYINITIAL> {EXCLAMATIONEQUALS} {return new Symbol(sym.NOT_EQUALS, yyline, yycolumn,yytext());}
<YYINITIAL> {EXCLAMATION} {return new Symbol(sym.EXCLAMATION, yyline, yycolumn,yytext());}

<YYINITIAL> {BLANKS} {
      }
<YYINITIAL> {COMMENT_ONE_LINE} {
      }
<YYINITIAL> {COMMENT_MULTIPLE_LINES} {
      }

<YYINITIAL> . {
//                listaErrores.add(new Errores("LEXICO","El caracter "+
//                yytext()+" NO pertenece al lenguaje", yyline, yycolumn));
System.out.println("El caracter "+yytext()+" NO pertenece al lenguaje");
}