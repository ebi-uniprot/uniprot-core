/*
ID   EntryName Status; SequenceLength.
*/
lexer grammar IdLineLexer;


ID_HEAD: 'ID   ';

REVIEW_STATUS_REVIEWED : 'Reviewed;';
REVIEW_STATUS_UNREVIEWED : 'Unreviewed;';

SPACE: ' ';
INT:[1-9][0-9]*;
DOT_NEWLINE : '.\n' ;
AA: 'AA';
UNDERSCORE : '_';
DASH: '-';
INTEGER: FIRST_DIGIT DIGIT*;

ENTRY_NAME: (LD5 | ACCESSION) (DASH INTEGER)?  UNDERSCORE LD5 (UNDERSCORE LD+)?;

fragment LD5: LD | (LD LD) | (LD LD LD)| (LD LD LD LD)| (LD LD LD LD LD);
fragment LETTER: [A-Z];
fragment FIRST_DIGIT: [1-9];
fragment DIGIT: [0-9];
fragment LD: [A-Z0-9];

//this is copied from swissprot syntax
fragment ACCESSION:  FIRST_PART | (SECOND_FIRST_PART SECOND_SECOND_PART SECOND_SECOND_PART? DIGIT);
fragment SECOND_SECOND_PART: DIGIT LETTER LD LD;
fragment SECOND_FIRST_PART: [A-NR-Z];
fragment FIRST_PART: ([OPQ] DIGIT (LD LD LD ) DIGIT);
