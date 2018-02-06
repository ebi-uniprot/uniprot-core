/*
OH   NCBI_TaxID=TaxID; HostName.

The length of an OH line may exceed 75 characters.
*/

lexer grammar OhLineLexer;

OH_HEADER: 'OH   ';
NCBI_TAX: 'NCBI_TaxID=';

TAX: [1-9][0-9]+;

SEPARATOR: '; '     -> pushMode(HOSTNAME_MODE);

mode HOSTNAME_MODE;
HOST_WORD: HL+;
SPACE: ' ';
DOT: '.';
fragment HL: ~[ \.];
LINE_END: '.\n'     -> popMode;

