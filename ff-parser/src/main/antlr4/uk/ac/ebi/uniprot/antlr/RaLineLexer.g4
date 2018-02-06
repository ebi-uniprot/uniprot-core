/*
RA   Galinier A., Bleicher F., Negre D., Perriere G., Duclos B.,
RA   Cozzone A.J., Cortay J.-C.;
*/

lexer grammar RaLineLexer;

RA_HEADER: 'RA   '    -> pushMode(AUTHOR_CONTENT);

mode AUTHOR_CONTENT;
CHANGE_OF_LINE: '\nRA   ';
SPACE: ' ';
COMA: ',';
END_OF_RA: ';\n'   ->popMode;

NAME: LT+ (SPACE LT+)* ;
fragment LT: ~[ ,\t\n\r;] ;