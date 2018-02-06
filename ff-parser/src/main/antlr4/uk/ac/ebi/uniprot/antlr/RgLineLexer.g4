/*
RG   The mouse genome sequencing consortium;
*/

lexer grammar RgLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

RG_HEADER: 'RG   '   ->pushMode (RG_CONTENT);

mode RG_CONTENT;
RG_END: ';\n'        ->popMode;
CHANGE_OF_LINE : '\nRG   ' {replaceChangeOfLine();};
SPACE: ' ';
WORD: LD+;
fragment LD: ~[ ;\n\r\t];