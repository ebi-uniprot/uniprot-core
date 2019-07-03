/*
OX   Taxonomy_database_Qualifier=Taxonomic code;
OX   NCBI_TaxID=9606;
*/

lexer grammar OxLineLexer;

options { superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtLexer; }

OX_HEADER: 'OX   ';
XDB: 'NCBI_TaxID=';
TAX: [1-9][0-9]*;
SEMICOLON : ';';
END: '\n'   ;

LEFT_B : ' {';
RIGHT_B : '}';

EV_SEPARATOR: ',';
EV_SPACE: ' ';
EV_TAG : ECO_TAG_EV (ECO_TAG_SOURCE)?;
EV_CHANGE_OF_LINE: '\nOX   ';
//the new evidence ECO type.
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ ,}\n\r\t]|EV_CHANGE_OF_LINE)+;
