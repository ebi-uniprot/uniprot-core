/*
KW   Activator; Complete proteome; Reference proteome; Transcription;
KW   Transcription regulation.
*/

lexer grammar KwLineLexer;

options { superClass=uk.ac.ebi.uniprot.antlr.RememberLastTokenLexer; }

KW_HEAD: 'KW   ';

CHANGE_OF_LINE : '\nKW   ' {replaceChangeOfLine();};
NEW_LINE : '\n';
SPACE: ' ';

SEMICOLON : ';';
DOT : '.';
LEFT_B : '{'         -> pushMode(EVIDENCE_MODE);

WORD: LD+;
fragment LD : ~[,.;{}\n ];

mode EVIDENCE_MODE;
EV_SEPARATOR: ',';
EV_SPACE: ' '                      -> type (SPACE);
EV_CHANGE_OF_LINE: '\nKW   '  -> type(CHANGE_OF_LINE);
EV_TAG : ECO_TAG;//| EV_TAG_OLD;
//EV_TAG : ECO_TAG| EV_TAG_OLD;
//fragment EV_TAG_OLD : ('EI'|'EA'|'EP'|'EC') [1-9][0-9]*;
fragment ECO_TAG: ECO_TAG_EV (ECO_TAG_SOURCE)?;
//the new evidence ECO type.
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ ,}\n\r\t]|EV_CHANGE_OF_LINE)+;
RIGHT_B : '}'                                        -> popMode;



           