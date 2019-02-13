lexer grammar RcLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

RC_HEADER: 'RC   ';

STRAIN : 'STRAIN='           ->pushMode(RC_VALUE_MODE);
PLASMID : 'PLASMID='         ->pushMode(RC_VALUE_MODE);
TRANSPOSON : 'TRANSPOSON='   ->pushMode(RC_VALUE_MODE);
TISSUE : 'TISSUE='           ->pushMode(RC_VALUE_MODE);
CHANGE_OF_LINE: '\nRC   ';
SPACE: ' ';
NEW_LINE : '\n';

mode RC_VALUE_MODE;
SEMICOLON: ';'             ->popMode;
COMA: ',';
LEFT_B : '{'              ->pushMode(EVIDENCE_MODE);
WORD: LD ((LD|COMA|SEMICOLON)* LD)?;
SPACE_: ' '                -> type(SPACE);
CHANGE_OF_LINE_: '\nRC   '  {setType(CHANGE_OF_LINE);replaceChangeOfLine();};
fragment LD : ~[;, \r\n\t{];

mode EVIDENCE_MODE;
EV_SEPARATOR: ',';
EV_SPACE: ' '                      -> type (SPACE);
EV_CHANGE_OF_LINE: '\nRC   '       -> type(CHANGE_OF_LINE);
EV_TAG : ECO_TAG;//| EV_TAG_OLD;
//EV_TAG : ECO_TAG| EV_TAG_OLD;
//fragment EV_TAG_OLD : ('EI'|'EA'|'EP'|'EC') [1-9][0-9]*;
fragment ECO_TAG: ECO_TAG_EV (ECO_TAG_SOURCE)?;
//the new evidence ECO type.
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ ,}\n\r\t]|EV_CHANGE_OF_LINE)+;
RIGHT_B : '}'                                        -> popMode;



