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
*/

parser grammar DrLineParser;

options { tokenVocab=DrLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser;}

dr_dr:
     (dr_line|dr_prosite_ss)+ ;

dr_line: DR_HEADER
         DB_NAME SEPARATOR ATTRIBUTES END_OF_LINE;

dr_prosite_ss:
         DR_PROSITE_SS SEPARATOR ATTRIBUTES END_OF_LINE;

