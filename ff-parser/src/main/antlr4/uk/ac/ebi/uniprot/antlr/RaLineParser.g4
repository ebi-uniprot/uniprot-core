/*
RA   Galinier A., Bleicher F., Negre D., Perriere G., Duclos B.,
RA   Cozzone A.J., Cortay J.-C.;
*/

parser grammar RaLineParser;

options { tokenVocab=RaLineLexer;}

ra_ra: RA_HEADER names END_OF_RA;

names: name (COMA (SPACE|CHANGE_OF_LINE) name)*;
name: NAME;
