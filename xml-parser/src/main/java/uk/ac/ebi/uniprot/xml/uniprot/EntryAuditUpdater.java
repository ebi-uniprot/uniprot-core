package uk.ac.ebi.uniprot.xml.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.builder.EntryAuditBuilder;
import uk.ac.ebi.uniprot.xml.Updater;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SequenceType;

import java.time.LocalDate;

public class EntryAuditUpdater implements Updater<SequenceType, EntryAudit> {

    @Override
    public EntryAudit fromXml(EntryAudit modelObject, SequenceType xmlObject) {
        int seqVersion = xmlObject.getVersion();
        LocalDate seqDate = XmlConverterHelper.dateFromXml(xmlObject.getModified());
        return new EntryAuditBuilder()
                .firstPublic(modelObject.getFirstPublicDate())
                .lastAnnotationUpdate(modelObject.getLastAnnotationUpdateDate())
                .lastSequenceUpdate(seqDate)
                .entryVersion(modelObject.getEntryVersion())
                .sequenceVersion(seqVersion)
                .build();
    }

    @Override
    public void toXml(SequenceType xmlObject, EntryAudit modelObject) {
        xmlObject.setVersion(modelObject.getSequenceVersion());
        LocalDate date = modelObject.getLastSequenceUpdateDate();
        xmlObject.setModified(XmlConverterHelper.dateToXml(date));
    }

}
