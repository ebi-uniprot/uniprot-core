package uk.ac.ebi.uniprot.xmlparser.uniprot;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SequenceType;
import uk.ac.ebi.uniprot.xmlparser.Updater;

public class EntryAuditUpdater implements Updater<SequenceType, EntryAudit> {

	@Override
	public EntryAudit fromXml(EntryAudit modelObject, SequenceType xmlObject) {
		int seqVersion = xmlObject.getVersion();
		LocalDate seqDate = xmlObject.getModified().toGregorianCalendar().toZonedDateTime().toLocalDate();
		return UniProtFactory.INSTANCE.createEntryAudit(modelObject.getFirstPublicDate(),
				modelObject.getLastAnnotationUpdateDate(),
				seqDate, modelObject.getEntryVersion(), seqVersion);
	}

	@Override
	public void toXml(SequenceType xmlObject, EntryAudit modelObject) {
		xmlObject.setVersion(modelObject.getSequenceVersion());
		LocalDate date = modelObject.getLastSequenceUpdateDate();
		try {
		GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
		XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
		xmlObject.setModified(xcal);
		}catch(DatatypeConfigurationException e) {
			new RuntimeException (e);
		}
		
	}

}
