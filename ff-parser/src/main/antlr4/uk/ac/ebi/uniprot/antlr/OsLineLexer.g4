lexer grammar OsLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

OS_HEADER : 'OS   '    -> pushMode (OS_CONTENT);

mode OS_CONTENT;
CHANGE_OF_LINE : '\nOS   ' {replaceChangeOfLine();};
DOT_CHANGE_OF_LINE : '.\nOS   ' {setText(". ");};
OS_LINE: (DOT|LD)*LD;
DOT: '.';
END: '.\n'              -> popMode;
fragment LD : ~[.\r\n\t];

