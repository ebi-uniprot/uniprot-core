/*
RX   Bibliographic_db=IDENTIFIER[; Bibliographic_db=IDENTIFIER...];

RX   MEDLINE=83283433; PubMed=6688356;
RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;
RX   MEDLINE=22709107; PubMed=12788972; DOI=10.1073/pnas.1130426100;
RX   AGRICOLA=IND20551642; DOI=10.1007/BF00224104;
*/

lexer grammar RxLineLexer;

options { superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtLexer; }

RX_HEADER : 'RX   ' ;

CHANGE_OF_LINE: '\nRX   ';
SPACE: ' ';
NEW_LINE: '\n';

PUBMED: 'PubMed=' -> pushMode (RX_VALUE);
DOI: 'DOI='  -> pushMode (RX_VALUE_DOI);
AGRICOLA: 'AGRICOLA='  -> pushMode (RX_VALUE);
MEDLINE: 'MEDLINE='  -> pushMode (RX_VALUE);

mode RX_VALUE;
R_SEMICOLON: ';'             ->popMode, type(SEMICOLON) ;
R_VALUE: R_LD+              -> type(VALUE);
fragment R_LD: [A-Z0-9];

mode RX_VALUE_DOI;
VALUE: LD ((LD|SEMICOLON)* LD)?;
SEMICOLON: ';'             ->popMode  ;
fragment LD: ~[;\r\t\n] ;
