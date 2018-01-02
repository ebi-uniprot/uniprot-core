package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.description.Field;
import uk.ac.ebi.uniprot.domain.uniprot.description.FieldType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.NameType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.Section;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FieldImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.NameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.SectionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public enum ProteinDescriptionFactory {
    INSTANCE;
    
    public Field createField(FieldType type, String value, List<Evidence> evidences) {
        return new FieldImpl( type,  value, evidences) ;        
    }
    public Name createName(NameType type,List<Field>  fields ){
        return new NameImpl(type, fields);
    }
    public Flag createFlag(FlagType type, List<Evidence> evidences){
        return new FlagImpl(type, evidences);
    }
    public Section createSection(List<Name> names) {
        return new SectionImpl(names);
    }
    public ProteinDescription createProteinDescription(Section mainSection,
            List<Flag> flags){
        return new ProteinDescriptionImpl(mainSection, flags);
        
    }
    
    public ProteinDescription createProteinDescription(Section mainSection,
            List<Flag> flags,
            List<Section> contains, List<Section> includes){
        return new ProteinDescriptionImpl(mainSection, flags, contains, includes);
        
    }
}
