lexer grammar RnLineLexer;

options { superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtLexer; }

RN_HEADER: 'RN   ';
NEWLINE : '\n';

LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
INTEGER: [1-9][0-9]*;

LEFT_B : '{';
RIGHT_B : '}';
EV_SEPARATOR: ',';
SPACE: ' '                     ;
CHANGE_OF_LINE: '\nRN   '      ;
EV_TAG : ECO_TAG;
fragment ECO_TAG: ECO_TAG_EV (ECO_TAG_SOURCE)?;
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ ,}\n\r\t]|CHANGE_OF_LINE)+;
