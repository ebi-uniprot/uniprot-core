/*
DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.
DT   19-JUL-2004, sequence version 1.
DT   18-APR-2012, entry version 24.
*/

lexer grammar DtLineLexer;




DT_HEAD : 'DT   ';

INTEGRATION_CLAUSE: ', integrated into UniProtKB/';
LINE_END: '.\n';

VERSION: [1-9][0-9]*;

SWISSPROT: 'Swiss-Prot';
TREMBL: 'TrEMBL';

SEQV: ', sequence version ';
ENTV: ', entry version ';

DATE : DIGIT DIGIT '-' MONTH_ABR '-' DIGIT DIGIT DIGIT DIGIT;
fragment MONTH_ABR : 'JAN' |'FEB'|'MAR' |'MAY'|'JUN' | 'JUL' | 'APR'| 'AUG'| 'SEP'| 'NOV'| 'OCT' |'DEC';
fragment DIGIT : [0-9];
