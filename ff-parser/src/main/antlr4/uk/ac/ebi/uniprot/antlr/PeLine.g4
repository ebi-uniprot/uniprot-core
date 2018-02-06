/*
PE   Level: Evidence;

1: Evidence at protein level
2: Evidence at transcript level
3: Inferred from homology
4: Predicted
5: Uncertain

*/

grammar PeLine;

pe_pe: pe_head  pe_level ';' NEWLINE;

pe_head: 'PE   ';

pe_level : LEVEL1 | LEVEL2 | LEVEL3| LEVEL4| LEVEL5;

LEVEL1: '1: Evidence at protein level';
LEVEL2: '2: Evidence at transcript level';
LEVEL3: '3: Inferred from homology';
LEVEL4: '4: Predicted';
LEVEL5: '5: Uncertain';

NEWLINE : '\n';

