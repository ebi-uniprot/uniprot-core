/*
OX   Taxonomy_database_Qualifier=Taxonomic code;
OX   NCBI_TaxID=9606;
*/

grammar OxLine;

ox_ox: 'OX   ' db '=' tax (evidence)? ';\n';

db : XDB ;

tax: TAX;

evidence: LEFT_B  EV_TAG (EV_SEPARATOR (EV_SPACE|EV_CHANGE_OF_LINE)  EV_TAG)* RIGHT_B;

XDB: 'NCBI_TaxID';
TAX: [1-9][0-9]*;

LEFT_B : ' {';
RIGHT_B : '}';

EV_SEPARATOR: ',';
EV_SPACE: ' ';
EV_TAG : ECO_TAG_EV (ECO_TAG_SOURCE)?;
EV_CHANGE_OF_LINE: '\nOX   ';
//the new evidence ECO type.
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ ,}\n\r\t]|EV_CHANGE_OF_LINE)+;

