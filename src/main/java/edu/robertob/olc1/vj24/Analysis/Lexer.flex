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
ENDLINE=";"
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
RW_IF="if"
RW_TRUE="true"
RW_FALSE="false"
RW_BOOL="bool"

%%
<YYINITIAL> {RW_PRINT} {return new Symbol(sym.RW_PRINT, yyline, yycolumn,yytext());}
//<YYINITIAL> {INT} {return new Symbol(sym.INT, yyline, yycolumn,yytext());}
//<YYINITIAL> {DOUBLE} {return new Symbol(sym.DOUBLE, yyline, yycolumn,yytext());}
//<YYINITIAL> {STRING} {return new Symbol(sym.STRING, yyline, yycolumn,yytext());}
//<YYINITIAL> {TRUE} {return new Symbol(sym.TRUE, yyline, yycolumn,yytext());}
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
    char charObj = stringObj.charAt(0);
    return new Symbol(sym.CHAR, yyline, yycolumn, charObj);
}

<YYINITIAL> {ENDLINE} {return new Symbol(sym.ENDLINE, yyline, yycolumn,yytext());}
<YYINITIAL> {OPENPAR} {return new Symbol(sym.OPENPAR, yyline, yycolumn,yytext());}
<YYINITIAL> {CLOSEPAR} {return new Symbol(sym.CLOSEPAR, yyline, yycolumn,yytext());}
<YYINITIAL> {OPENBRACE} {return new Symbol(sym.OPENBRACE, yyline, yycolumn,yytext());}
<YYINITIAL> {CLOSEBRACE} {return new Symbol(sym.CLOSEBRACE, yyline, yycolumn,yytext());}


<YYINITIAL> {PLUS} {return new Symbol(sym.PLUS, yyline, yycolumn,yytext());}
<YYINITIAL> {MINUS} {return new Symbol(sym.MINUS, yyline, yycolumn,yytext());}
<YYINITIAL> {DOUBLEASTERISK} {return new Symbol(sym.DOUBLEASTERISK, yyline, yycolumn,yytext());}
<YYINITIAL> {ASTERISK} {return new Symbol(sym.ASTERISK, yyline, yycolumn,yytext());}
<YYINITIAL> {SLASH} {return new Symbol(sym.SLASH, yyline, yycolumn,yytext());}
//<YYINITIAL> {MODULO} {return new Symbol(sym.MODULO, yyline, yycolumn,yytext());}
<YYINITIAL> {EQUALS} {return new Symbol(sym.EQUALS, yyline, yycolumn,yytext());}


<YYINITIAL> {BLANKS} {}
<YYINITIAL> {COMMENT_ONE_LINE} {}
<YYINITIAL> {COMMENT_MULTIPLE_LINES} {}

<YYINITIAL> . {
//                listaErrores.add(new Errores("LEXICO","El caracter "+
//                yytext()+" NO pertenece al lenguaje", yyline, yycolumn));
System.out.println("El caracter "+yytext()+" NO pertenece al lenguaje");
}