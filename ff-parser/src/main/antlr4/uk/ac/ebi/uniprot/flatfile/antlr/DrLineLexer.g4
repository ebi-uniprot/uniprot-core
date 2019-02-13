/*
DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
DR   RefSeq; YP_031579.1; NC_005946.1.
DR   ProteinModelPortal; Q6GZX4; -.
DR   GeneID; 2947773; -.
DR   ProtClustDB; CLSP2511514; -.
DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
DR   GO; GO:0006351; P:transcription, DNA-dependent; IEA:UniProtKB-KW.
DR   InterPro; IPR007031; Poxvirus_VLTF3.
DR   Pfam; PF04947; Pox_VLTF3; 1.

-- ISO form.
DR PRIDE; P19802; -.{EI1}
DR PRIDE; P19802; -. [P19802-2]
DR PROSITE; PS00157; RUBISCO_LARGE; 1. [P21235-2]
DR PROSITE; PS00157; RUBISCO_LARGE; 1. [P21235-2]{EI1}
*/

lexer grammar DrLineLexer;

tokens {SEPARATOR,  RIGHT_BRACKET}

DR_HEADER : 'DR   ';
DB_NAME: [A-Za-z][A-Za-z0-9_\-]*      -> pushMode (DR_ATTR);

DR_PROSITE_SS: '**   PROSITE'        -> pushMode (DR_ATTR);

mode DR_ATTR;

SEPARATOR: '; ';
ATTRIBUTES: LT_START LT* ;
END_OF_LINE: '\n'     -> popMode;

fragment LT: ~[\n\r\t];
fragment LT_START: ~[;\n\r\t];
