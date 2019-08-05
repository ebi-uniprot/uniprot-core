lexer grammar AcLineLexer;

options { superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtLexer; }

AC_HEAD: 'AC   ';
SPACE1: ' ';
NEWLINE: '\n';
SEMICOLON: ';';
DASH: '-';
INTEGER: FIRST_DIGIT DIGIT*;

//this is copied from swissprot syntax
ACCESSION:  FIRST_PART | (SECOND_FIRST_PART SECOND_SECOND_PART SECOND_SECOND_PART? DIGIT);

fragment SECOND_SECOND_PART: DIGIT LETTER LD LD;
fragment SECOND_FIRST_PART: [A-NR-Z];
fragment FIRST_PART: ([OPQ] DIGIT (LD LD LD ) DIGIT);

fragment LETTER: [A-Z];
fragment FIRST_DIGIT: [1-9];
fragment DIGIT: [0-9];
fragment LD: [A-Z0-9];
