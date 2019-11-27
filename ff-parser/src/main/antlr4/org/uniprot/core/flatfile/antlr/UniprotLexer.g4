lexer grammar UniprotLexer;

options { superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtLexer; }

ID_LINE:   ID_HEADER .+? NEW_LINE;
AC_LINE:   AC_HEADER .+? NEW_LINE;
DT_LINE:   DT_HEADER .+? NEW_LINE;
DE_LINE:   DE_HEADER .+? NEW_LINE;
GN_LINE:   GN_HEADER .+? NEW_LINE;
OS_LINE:   OS_HEADER .+? NEW_LINE;
OX_LINE:   OX_HEADER .+? NEW_LINE;
OG_LINE:   OG_HEADER .+? NEW_LINE;
OH_LINE:   OH_HEADER .+? NEW_LINE;
OC_LINE:   OC_HEADER .+? NEW_LINE;
RN_LINE:   RN_HEADER .+? NEW_LINE;
RP_LINE:   RP_HEADER .+? NEW_LINE;
RA_LINE:   RA_HEADER .+? NEW_LINE;
RX_LINE:   RX_HEADER .+? NEW_LINE;
RG_LINE:   RG_HEADER .+? NEW_LINE;
RT_LINE:   RT_HEADER .+? NEW_LINE;
RL_LINE:   RL_HEADER .+? NEW_LINE;
RC_LINE:   RC_HEADER .+? NEW_LINE;
CC_LINE:   CC_HEADER .+? NEW_LINE;
DR_LINE:   DR_HEADER .+? NEW_LINE;
PE_LINE:   PE_HEADER .+? NEW_LINE;
KW_LINE:   KW_HEADER .+? NEW_LINE;
FT_LINE:   FT_HEADER .+? NEW_LINE;
SQ_LINE:   SQ_HEADER .+? NEW_LINE;
DR_SS_PROSITE_LINE: DR_SS_PROSITE_HEADER  .+? NEW_LINE;
SS_LINE:   SS_HEADER .+? NEW_LINE;

SEQUENCE_LINE: SPACE_FIVE .+? NEW_LINE;
END_OF_ENTRY: '//';


ID_HEADER: 'ID   ' ;
AC_HEADER: 'AC   ' ;
DT_HEADER: 'DT   ' ;
DE_HEADER: 'DE   ' ;
GN_HEADER: 'GN   ' ;
OS_HEADER: 'OS   ' ;
OG_HEADER: 'OG   ' ;
OC_HEADER: 'OC   ' ;
OX_HEADER: 'OX   ' ;
OH_HEADER: 'OH   ' ;
RN_HEADER: 'RN   ' ;
RP_HEADER: 'RP   ' ;
RA_HEADER: 'RA   ' ;
RG_HEADER: 'RG   ' ;
RX_HEADER: 'RX   ' ;
RT_HEADER: 'RT   ' ;
RL_HEADER: 'RL   ' ;
RC_HEADER: 'RC   ' ;
CC_HEADER: 'CC   ' ;
DR_HEADER: 'DR   ' ;
DR_SS_PROSITE_HEADER: '**   PROSITE' ;
PE_HEADER: 'PE   ' ;
KW_HEADER: 'KW   ' ;
FT_HEADER: 'FT   ' ;
SQ_HEADER: 'SQ   ' ;
SS_HEADER: '**' ;
SPACE_FIVE: '     ' ;
NEW_LINE: '\n'     ;


CC_COPY_RIGHT_OLD:
        'CC   -----------------------------------------------------------------------' NEW_LINE
        'CC   Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms' NEW_LINE
        'CC   Distributed under the Creative Commons Attribution (CC BY 4.0) License'     NEW_LINE
        'CC   -----------------------------------------------------------------------' NEW_LINE
        ;

CC_COPY_RIGHT:
        'CC   ---------------------------------------------------------------------------' NEW_LINE
        'CC   Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms' NEW_LINE
        'CC   Distributed under the Creative Commons Attribution (CC BY 4.0) License'     NEW_LINE
        'CC   ---------------------------------------------------------------------------' NEW_LINE
        ;