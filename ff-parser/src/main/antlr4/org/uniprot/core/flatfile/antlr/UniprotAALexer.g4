lexer grammar UniprotAALexer;

options { superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtLexer; }

AC_LINE:   AC_HEADER .+? NEW_LINE;
DE_LINE:   DE_HEADER .+? NEW_LINE;
GN_LINE:   GN_HEADER .+? NEW_LINE;
CC_LINE:   CC_HEADER .+? NEW_LINE;
KW_LINE:   KW_HEADER .+? NEW_LINE;
FT_LINE:   FT_HEADER .+? NEW_LINE;
END_OF_ENTRY: '//';


AC_HEADER: 'AC   ' ;
DE_HEADER: 'DE   ' ;
GN_HEADER: 'GN   ' ;
CC_HEADER: 'CC   ' ;
KW_HEADER: 'KW   ' ;
FT_HEADER: 'FT   ' ;
NEW_LINE: '\n'     ;
