grammar RnLine;

rn_rn: 'RN   ' LEFT_BRACKET rn_number RIGHT_BRACKET ((SPACE | CHANGE_OF_LINE)evidence)?'\n';

rn_number: INTEGER;

LEFT_BRACKET: '[';
RIGHT_BRACKET: ']';
INTEGER: [1-9][0-9]*;

evidence: LEFT_B  EV_TAG (EV_SEPARATOR (SPACE|CHANGE_OF_LINE) EV_TAG)* RIGHT_B;

LEFT_B : '{';
RIGHT_B : '}';
EV_SEPARATOR: ',';
SPACE: ' '                     ;
CHANGE_OF_LINE: '\nRN   '      ;
EV_TAG : ECO_TAG;
fragment ECO_TAG: ECO_TAG_EV (ECO_TAG_SOURCE)?;
fragment ECO_TAG_EV : 'ECO:'[0-9]* ;
fragment ECO_TAG_SOURCE: '|'(~[ ,}\n\r\t]|CHANGE_OF_LINE)+;
