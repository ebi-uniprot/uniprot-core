/*
PE   Level: Evidence;

1: Evidence at protein level
2: Evidence at transcript level
3: Inferred from homology
4: Predicted
5: Uncertain

*/


lexer grammar PeLineLexer;

options { superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtLexer; }

PE_HEADER: 'PE   ';

LEVEL1: '1: Evidence at protein level';
LEVEL2: '2: Evidence at transcript level';
LEVEL3: '3: Inferred from homology';
LEVEL4: '4: Predicted';
LEVEL5: '5: Uncertain';

NEWLINE : '\n';
SEMICOLON : ';';