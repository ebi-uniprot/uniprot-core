
parser grammar FtLineParser;

options { tokenVocab=FtLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser;}

ft_ft: ft_line+;

ft_line: FT_HEADER ft_key ft_locations 
		  (ft_ligand_name)?
		  (ft_ligand_id)?
		  (ft_ligand_label)?
		  (ft_ligand_note)?
		  (ft_ligand_part_name)?
		  (ft_ligand_part_id)?
		  (ft_ligand_part_label)?
		  (ft_ligand_part_note)?
          (ft_note)?
          (ft_evidence )?
        (ft_id)?
        NEW_LINE
         ;
         
  
 ft_key: FT_KEY_INIT_MET|FT_KEY_SIGNAL|FT_KEY_PROPEP|FT_KEY_TRANSIT|FT_KEY_CHAIN|FT_KEY_PEPTIDE|FT_KEY_TOPO_DOM|FT_KEY_TRANSMEM|
         FT_KEY_INTRAMEM|FT_KEY_DOMAIN|FT_KEY_REPEAT|FT_KEY_CA_BIND|FT_KEY_ZN_FING|FT_KEY_DNA_BIND|FT_KEY_NP_BIND|FT_KEY_REGION|
         FT_KEY_COILED|FT_KEY_MOTIF|FT_KEY_COMPBIAS|FT_KEY_ACT_SITE|FT_KEY_METAL|FT_KEY_BINDING|FT_KEY_SITE|FT_KEY_LIPID|
         FT_KEY_CARBOHYD|FT_KEY_DISULFID|FT_KEY_CROSSLNK|FT_KEY_VARIANT|FT_KEY_MUTAGEN|FT_KEY_CONFLICT|FT_KEY_OTHERS|FT_KEY_VAR_SEQ
 ;
 ft_locations: (SPACE | SPACES)  loc_start (FT_SPOT FT_SPOT loc_end)?;
 //ft_isoform: FT_ISOFORM_WORD (SPACE FT_ISOFORM_WORD)*;
loc_start:   (FT_LOCATION_ISO|FT_LOCATION_2) (SPACE FT_LOCATION_ISO|FT_LOCATION_2)*;
loc_end:    FT_LOCATION_2;

ft_ligand_name: FT_LIGAND ligand_name DOUBLE_QUOTE;
ligand_name: (FT_LIGAND_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_LIGAND_WORD)+)*;

ft_ligand_id: FT_LIGAND_ID ligand_id DOUBLE_QUOTE;
ligand_id: (FT_LIGAND_ID_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_LIGAND_ID_WORD)+)*;

ft_ligand_label: FT_LIGAND_LABEL ligand_label DOUBLE_QUOTE;
ligand_label: (FT_LIGAND_LABEL_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_LIGAND_LABEL_WORD)+)*;

ft_ligand_note: FT_LIGAND_NOTE ligand_note DOUBLE_QUOTE;
ligand_note: (FT_LIGAND_NOTE_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_LIGAND_NOTE_WORD)+)*;

ft_ligand_part_name: FT_LIGAND_PT ligand_part_name DOUBLE_QUOTE;
ligand_part_name: (FT_LIGAND_PT_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_LIGAND_PT_WORD)+)*;

ft_ligand_part_id: FT_LIGAND_PT_ID ligand_part_id DOUBLE_QUOTE;
ligand_part_id: (FT_LIGAND_PT_ID_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_LIGAND_PT_ID_WORD)+)*;

ft_ligand_part_label: FT_LIGAND_PT_LABEL ligand_part_label DOUBLE_QUOTE;
ligand_part_label: (FT_LIGAND_PT_LABEL_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_LIGAND_PT_LABEL_WORD)+)*;

ft_ligand_part_note: FT_LIGAND_PT_NOTE ligand_part_note DOUBLE_QUOTE;
ligand_part_note: (FT_LIGAND_PT_NOTE_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_LIGAND_PT_NOTE_WORD)+)*;

ft_note: FT_NOTE note_text DOUBLE_QUOTE;
note_text: (FT_NOTE_WORD)+ ((SPACE|CHANGE_OF_LINE) (FT_NOTE_WORD)+)*;
ft_evidence: FT_EVIDENCE EV_TAG (EV_SEPARATOR (SPACE|CHANGE_OF_LINE) EV_TAG)* DOUBLE_QUOTE;


ft_id: FT_ID FTID_VALUE DOUBLE_QUOTE;



