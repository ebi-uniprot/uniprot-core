package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.description.Field;
import uk.ac.ebi.uniprot.domain.uniprot.description.FieldType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.NameType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.Section;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProteinDescriptionFactoryTest {

    @Test
    public void testCreateField() {
        FieldType type = FieldType.FULL;
        String value = "someValue";
        List<Evidence> evidences = createEvidences();
        Field field = ProteinDescriptionFactory.INSTANCE.createField(type, value, evidences);
        assertEquals(type, field.getType());
        assertEquals(value, field.getValue());
        assertEquals(evidences, field.getEvidences());
    }

    @Test
    public void testCreateName() {
        List<Field> fields = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        fields.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.FULL, "val1", evidences));
        fields.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.SHORT, "val2", evidences));
       Name name = ProteinDescriptionFactory.INSTANCE.createName(NameType.RECNAME, fields);
       assertEquals(NameType.RECNAME, name.getNameType());
       assertEquals(fields, name.getFields());
       assertTrue(name.getFieldsByType(FieldType.EC).isEmpty());
       assertEquals(1, name.getFieldsByType(FieldType.FULL).size());
       assertEquals(1, name.getFieldsByType(FieldType.SHORT).size());
    }

    @Test
    public void testCreateFlag() {
        List<Evidence> evidences = createEvidences();
        Flag flag =ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT, evidences);
        assertEquals(FlagType.FRAGMENT, flag.getFlagType());
        assertEquals(evidences, flag.getEvidences());
    }

    @Test
    public void testCreateSection() {
        List<Name> names =new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        fields.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.FULL, "val1", evidences));
        fields.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.SHORT, "val2", evidences));
       Name name = ProteinDescriptionFactory.INSTANCE.createName(NameType.RECNAME, fields);
       List<Field> fields2 = new ArrayList<>();
       fields2.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.FULL, "fullVal2", evidences));
       Name name2 = ProteinDescriptionFactory.INSTANCE.createName(NameType.ALTNAME, fields);
       names.add(name);
       names.add(name2);
        Section section =ProteinDescriptionFactory.INSTANCE.createSection(names);
        assertEquals(names, section.getNames());
        assertTrue(section.isValidSection());
        assertEquals(1, section.getNamesByType(NameType.RECNAME).size());
        assertEquals(1, section.getNamesByType(NameType.ALTNAME).size());
        assertEquals(0, section.getNamesByType(NameType.SUBNAME).size());
    }
    @Test
    public void testCreateProteinDescriptionSection1() {
        List<Name> names =new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        fields.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.FULL, "val1", evidences));
        fields.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.SHORT, "val2", evidences));
       Name name = ProteinDescriptionFactory.INSTANCE.createName(NameType.RECNAME, fields);
       List<Field> fields2 = new ArrayList<>();
       fields2.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.FULL, "fullVal2", evidences));
       Name name2 = ProteinDescriptionFactory.INSTANCE.createName(NameType.ALTNAME, fields);
       names.add(name);
       names.add(name2);
        Section section =ProteinDescriptionFactory.INSTANCE.createSection(names);
        ProteinDescription pd =ProteinDescriptionFactory.INSTANCE.createProteinDescription(section, null);
        assertEquals(name, pd.getRecommendedName());
        assertEquals(1, pd.getAlternativeNames().size());
        assertEquals(name2, pd.getAlternativeNames().get(0));
        assertTrue(pd.hasRecommendedName());
        assertTrue(pd.hasAlternativeNames());
        assertFalse(pd.hasSubNames());
        assertEquals(0, pd.getFlags().size());
        assertEquals(0, pd.getContains().size());
        assertEquals(0, pd.getIncludes().size());
    }
    @Test
    public void testCreateProteinDescription2() {
        List<Name> names =new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        fields.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.FULL, "val1", evidences));
        fields.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.SHORT, "val2", evidences));
       Name name = ProteinDescriptionFactory.INSTANCE.createName(NameType.RECNAME, fields);
       List<Field> fields2 = new ArrayList<>();
       fields2.add(ProteinDescriptionFactory.INSTANCE.createField(FieldType.FULL, "fullVal2", evidences));
       Name name2 = ProteinDescriptionFactory.INSTANCE.createName(NameType.ALTNAME, fields);
       names.add(name);
       names.add(name2);
        Section section =ProteinDescriptionFactory.INSTANCE.createSection(names);
        Flag flag =ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT, evidences);
        List<Flag> flags = new ArrayList<>();
        flags.add(flag);
        ProteinDescription pd =ProteinDescriptionFactory.INSTANCE.createProteinDescription(section, flags);
        assertEquals(name, pd.getRecommendedName());
        assertEquals(1, pd.getAlternativeNames().size());
        assertEquals(name2, pd.getAlternativeNames().get(0));
        assertTrue(pd.hasRecommendedName());
        assertTrue(pd.hasAlternativeNames());
        assertFalse(pd.hasSubNames());
        assertEquals(1, pd.getFlags().size());
        assertEquals(flags, pd.getFlags());
        assertEquals(0, pd.getContains().size());
        assertEquals(0, pd.getIncludes().size());
    }

   

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

}
