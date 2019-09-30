package org.uniprot.core.xml.uniprot;

import java.time.LocalDate;

import org.uniprot.core.uniprot.EntryAudit;
import org.uniprot.core.uniprot.builder.EntryAuditBuilder;
import org.uniprot.core.xml.Updater;
import org.uniprot.core.xml.jaxb.uniprot.SequenceType;

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
