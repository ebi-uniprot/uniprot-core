package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.time.LocalDate;

import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SequenceType;
import uk.ac.ebi.uniprot.xmlparser.Updater;

public class EntryAuditUpdater implements Updater<SequenceType, EntryAudit> {

	@Override
	public EntryAudit fromXml(EntryAudit modelObject, SequenceType xmlObject) {
		int seqVersion = xmlObject.getVersion();
		LocalDate seqDate = XmlConverterHelper.dateFromXml(xmlObject.getModified());
		return UniProtFactory.INSTANCE.createEntryAudit(modelObject.getFirstPublicDate(),
				modelObject.getLastAnnotationUpdateDate(),
				seqDate, modelObject.getEntryVersion(), seqVersion);
	}

	@Override
	public void toXml(SequenceType xmlObject, EntryAudit modelObject) {
		xmlObject.setVersion(modelObject.getSequenceVersion());
		LocalDate date = modelObject.getLastSequenceUpdateDate();
		xmlObject.setModified(XmlConverterHelper.dateToXml(date));
	}

}
