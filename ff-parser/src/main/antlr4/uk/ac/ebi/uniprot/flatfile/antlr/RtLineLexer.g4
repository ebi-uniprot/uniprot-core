lexer grammar RtLineLexer;

options { superClass=uk.ac.ebi.uniprot.flatfile.antlr.RememberLastTokenLexer; }

RT_START  : 'RT   "' -> pushMode (RT_CONTENT);

mode RT_CONTENT;
CHANGE_OF_LINE : '\nRT   ' {replaceChangeOfLine();};

LETTER: ~[\r\n\t?!.];
RT_ENDING : '";\n'  ->popMode;
TITLE_END: [.!?];


