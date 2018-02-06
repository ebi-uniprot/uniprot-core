parser grammar UniprotParser;

options { tokenVocab=UniprotLexer;}

ff: entry+;

entry:
        id
        ac
        dt
        de
        gn?
        os
        og?
        oc
        ox
        oh?
        reference+
        cc?
        copyright?
        dr?
        pe
        kw?
        ft?
        ss?
        sq
        END_OF_ENTRY
        NEW_LINE?
        ;

reference:
        rn
        rp
        rc?
        rx?
        (ra|rg|(rg ra))
        rt?
        rl;

sq: SQ_LINE SEQUENCE_LINE+;
copyright: CC_COPY_RIGHT;

id: ID_LINE;
ac: AC_LINE+;
dt: DT_LINE DT_LINE DT_LINE;
de: DE_LINE+;
gn: GN_LINE+;
os: OS_LINE+;
og: OG_LINE+;
oc: OC_LINE+;
ox: OX_LINE+;
oh: OH_LINE+;
cc: CC_LINE+;
dr: (DR_LINE|DR_SS_PROSITE_LINE)+;
pe: PE_LINE;
kw: KW_LINE+;
ft: FT_LINE+;
rn: RN_LINE+;
rp: RP_LINE+;
rc: RC_LINE+;
rx: RX_LINE+;
ra: RA_LINE+;
rg: RG_LINE+;
rt: RT_LINE+;
rl: RL_LINE+;
ss: (SS_LINE|DR_SS_PROSITE_LINE)+;


