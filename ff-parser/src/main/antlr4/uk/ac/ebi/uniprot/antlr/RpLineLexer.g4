lexer grammar RpLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

RP_START  : 'RP   '             -> pushMode (RP_CONTENT);

mode RP_CONTENT;
RP_ENDING : '.\n'               -> popMode;
AND : COMA (SPACE|CHANGE_OF_LINE)'AND'                              ;
CHANGE_OF_LINE : '\nRP   '      {replaceChangeOfLine();} ;
SPACE: ' ';
COMA: ',';
DOT: '.';
WORD: LD((LD|COMA|DOT)*LD)?        {!getText().endsWith(".")}?;
fragment LD : ~[ .,\r\na-z];