/*
DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.
DT   19-JUL-2004, sequence version 1.
DT   18-APR-2012, entry version 24.
*/

parser grammar DtLineParser;

options { tokenVocab=DtLineLexer;}

dt_dt: dt_integration_line dt_seqver_line dt_entryver_line;

dt_integration_line :  DT_HEAD DATE INTEGRATION_CLAUSE dt_database LINE_END;

dt_seqver_line: DT_HEAD DATE SEQV VERSION LINE_END;

dt_entryver_line: DT_HEAD DATE ENTV VERSION LINE_END;

dt_database : SWISSPROT | TREMBL;

