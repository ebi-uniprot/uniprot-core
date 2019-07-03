lexer grammar SsLineLexer;

options { superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtLexer; }

/*

**
**   #################    INTERNAL SECTION    ##################
**EV EI2; ProtImp; -; -; 11-SEP-2009.
**EV EI3; EMBL; -; ACT41999.1; 22-JUN-2010.
**EV EI4; EMBL; -; CAQ30614.1; 18-DEC-2010.
**EV ECO:0000256; SAAS:SAAS022644_004_000329; -; 11-FEB-2014.
**DG dg-000-000-614_P;
**ZB YOK, 19-NOV-2002;
*/


STAR_LINE: '**\n';

SS_START_LINE: '**   #################     SOURCE SECTION     ##################\n';
IS_START_LINE: '**   #################    INTERNAL SECTION    ##################\n';

STAR_STAR_SOURCE: '**   '   -> pushMode (SOURCE_TEXT_MODE);
EV_TOPIC: '**EV '           -> pushMode (EV_MODE);

STAR_STAR: '**';
TOPIC: [A-Z][A-Z];
SPACE: ' '                  -> pushMode (IA_TEXT_MODE);

mode IA_TEXT_MODE;
IA_TEXT:  TL+;
LINE_END: '\n'             ->popMode;
fragment TL: ~[\n\r];

mode EV_MODE;
EV_SEPARATOR: '; ';
EV_LINE_END: '.\n'          -> type(LINE_END), popMode;
EV_DATE: D (D)? DASH L L L DASH D D D D;
EV_WORD: EL ((EL|DOT)* EL)?;
fragment EL: ~[.;\n\r\t];
fragment D: [0-9];
fragment L: [A-Z];
fragment DASH: '-';
fragment DOT: '.';

mode SOURCE_TEXT_MODE;
SOURCE_TEXT:  TL_S+;
LINE_END_2: '\n'            -> type(LINE_END), popMode;
fragment TL_S: ~[\n\t\r];

