/*
OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
OG   Plasmid IncFII R1-19 (R1 drd-19).
*/
lexer grammar OgLineLexer;
options { superClass=org.uniprot.core.flatfile.antlr.RememberLastTokenLexer; }

OG_HEADER: 'OG   ';
HYDROGENOSOME: 'Hydrogenosome';
MITOCHONDRION: 'Mitochondrion';
NUCLEOMORPH: 'Nucleomorph';
PLASTID: 'Plastid';
APICOPLAST: 'Apicoplast';
ORGANELLAR_CHROMATOPHORE : 'Organellar chromatophore';
CYANELLE: 'Cyanelle';
CHLOROPLAST: 'Chloroplast';
NON_PHOTOSYNTHETIC_PLASTID: 'Non-photosynthetic plastid';
DOT_NEW_LINE: '.\n';
//PLASMID_SPACE: 'Plasmid '                                         ;
PLASMID: 'Plasmid'                    ->pushMode(PLASMID_VALUE_MODE) ;
COMA_O: ','    ->type (COMA);

SEMICOLON: ';';
LEFT_B: '{'                                              -> pushMode (EVIDENCE_MODE);
CHANGE_OF_LINE: '\nOG   ';
SPACE: ' '  ;
AND: 'and';
NEW_LINE: '\n';

mode PLASMID_VALUE_MODE;
DOT_NEW_LINE_V: '.\n'                    -> type (DOT_NEW_LINE), popMode;
COMA: ','                                -> popMode;
PL_SPACE: ' '                            -> type (SPACE);
CHANGE_OF_LINE_OG: '\nOG   '      {setType(CHANGE_OF_LINE);replaceChangeOfLine();};
LEFT_BRACKET_V: '{'                      -> type (LEFT_B), pushMode (EVIDENCE_MODE);
PLASMID_VALUE: PLASMID_WORD (PL_SPACE PLASMID_WORD)*;
fragment PLASMID_WORD: LD ((LD|DOT)* LD)? ;
fragment LD : ~[ ,.{\n\r\t];
fragment DOT: '.';

mode EVIDENCE_MODE;
EV_SEPARATOR: ',';
EV_SPACE: ' '                      -> type (SPACE);
EV_CHANGE_OF_LINE: '\nOG   '       -> type(CHANGE_OF_LINE);
EV_TAG : ECO_TAG;//| EV_TAG_OLD;
//EV_TAG : ECO_TAG| EV_TAG_OLD;
//fragment EV_TAG_OLD : ('EI'|'EA'|'EP'|'EC') [1-9][0-9]*;
fragment ECO_TAG: ECO_TAG_EV (ECO_TAG_SOURCE)?;
//the new evidence ECO type.
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ ,}\n\r\t]|EV_CHANGE_OF_LINE)+;
RIGHT_B : '}'                                        -> popMode;
