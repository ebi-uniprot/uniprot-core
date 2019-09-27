package org.uniprot.core.xml.uniprot;

import java.util.List;

import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.OrganismType;

public class OrganismConverter implements Converter<OrganismType, Organism> {
    private final EvidenceIndexMapper evRefMapper;
    private final ObjectFactory xmlUniprotFactory;

    public OrganismConverter(EvidenceIndexMapper evRefMapper) {
        this(evRefMapper, new ObjectFactory());
    }

    public OrganismConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
        this.evRefMapper = evRefMapper;
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Organism fromXml(OrganismType xmlObj) {
        OrganismBuilder builder = new OrganismBuilder();
        builder.taxonId(Long.parseLong(xmlObj.getDbReference().get(0).getId()));
        OrganismConverterUtil.updateOrganismNameFromXml(xmlObj.getName(), builder);
        builder.lineage(xmlObj.getLineage().getTaxon());
        builder.evidences(evRefMapper.parseEvidenceIds(xmlObj.getEvidence()));
        return builder.build();
    }

    @Override
    public OrganismType toXml(Organism organism) {
        OrganismType xmlOrganism = xmlUniprotFactory.createOrganismType();
        xmlOrganism
                .getDbReference()
                .add(
                        OrganismConverterUtil.taxonIdToXmlDbRef(
                                xmlUniprotFactory, organism.getTaxonId()));
        xmlOrganism
                .getName()
                .addAll(OrganismConverterUtil.organismNameToXml(xmlUniprotFactory, organism));

        OrganismType.Lineage lineage = xmlUniprotFactory.createOrganismTypeLineage();
        organism.getLineage().forEach(val -> lineage.getTaxon().add(val));
        xmlOrganism.setLineage(lineage);
        if (!organism.getEvidences().isEmpty()) {
            List<Integer> ev = evRefMapper.writeEvidences(organism.getEvidences());
            if (!ev.isEmpty()) xmlOrganism.getEvidence().addAll(ev);
        }
        return xmlOrganism;
    }
}
