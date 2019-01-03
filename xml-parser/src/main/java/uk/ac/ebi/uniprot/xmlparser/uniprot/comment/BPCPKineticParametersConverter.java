package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.comment.KineticParameters;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MaximumVelocity;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstant;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MichaelisConstantUnit;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.BPCPCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType.Kinetics;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidencedValueConverter;

public class BPCPKineticParametersConverter implements Converter<CommentType.Kinetics, KineticParameters> {
	private final ObjectFactory xmlUniprotFactory;
	private final EvidencedValueConverter evValueConverter;
	private final EvidenceIndexMapper evRefMapper;

	public BPCPKineticParametersConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public BPCPKineticParametersConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
		this.evValueConverter = new EvidencedValueConverter(evRefMapper, xmlUniprotFactory, false);
		this.evRefMapper = evRefMapper;
	}

	@Override
	public KineticParameters fromXml(Kinetics xmlObj) {
		List<MichaelisConstant> mConstants = xmlObj.getKM().stream().map(val -> michaelisConstantFromXml(val))
				.collect(Collectors.toList());
		List<MaximumVelocity> velocities = xmlObj.getVmax().stream().map(val -> maximumVelocityFromXml(val))
				.collect(Collectors.toList());

		Note note = null;
		if (!xmlObj.getText().isEmpty()) {
			note = CommentFactory.INSTANCE
					.createNote(xmlObj.getText().stream().map(evValueConverter::fromXml).collect(Collectors.toList()));
		}

		return BPCPCommentBuilder.createKineticParameters(velocities, mConstants, note);
	}

	@Override
	public Kinetics toXml(KineticParameters kp) {
		Kinetics kineticsXML = xmlUniprotFactory.createCommentTypeKinetics();

		// MichaelisConstant
		kp.getMichaelisConstants().forEach(mc -> kineticsXML.getKM().add(michaelisConstantToXml(mc)));

		// Maximum velocity
		kp.getMaximumVelocities().forEach(mc -> kineticsXML.getVmax().add(maximumVelocityToXml(mc)));

		// kineticParameterNote
		Note note = kp.getNote();
		if ((note != null) && (!note.getTexts().isEmpty())) {
			note.getTexts().forEach(val -> kineticsXML.getText().add(evValueConverter.toXml(val)));
		}

		return kineticsXML;
	}

	private MichaelisConstant michaelisConstantFromXml(EvidencedStringType evStringType) {

		String xmlValueString = evStringType.getValue();
		int pos1;
		pos1 = xmlValueString.indexOf(' ');
		int pos2 = xmlValueString.indexOf(' ', pos1 + 1);
		int pos3 = xmlValueString.indexOf(' ', pos2 + 1);
		String constantString = xmlValueString.substring(0, pos1);
		String unitString = xmlValueString.substring(pos1 + 1, pos2);
		String substrate = xmlValueString.substring(pos3 + 1);
		double constant = Double.parseDouble(constantString);
		MichaelisConstantUnit unit = MichaelisConstantUnit.convert(unitString);
		List<Evidence> evidences = evRefMapper.parseEvidenceIds(evStringType.getEvidence());

		return BPCPCommentBuilder.createMichaelisConstant(constant, unit, substrate, evidences);
	}

	private EvidencedStringType michaelisConstantToXml(MichaelisConstant mc) {
		EvidencedStringType kmkinetics = xmlUniprotFactory.createEvidencedStringType();
		StringBuilder sb = new StringBuilder();
		String numericalValue = mc.getConstant() + "";

		if (numericalValue.matches("\\d*.0")) {
			numericalValue = numericalValue.replace(".0", "");
		}

		sb.append(numericalValue);
		sb.append(' ');
		sb.append(mc.getUnit().toDisplayNameString()).append(" for ");
		sb.append(mc.getSubstrate());
		kmkinetics.setValue(sb.toString());

		if (!mc.getEvidences().isEmpty())
			kmkinetics.getEvidence().addAll(evRefMapper.writeEvidences(mc.getEvidences()));
		return kmkinetics;
	}

	private MaximumVelocity maximumVelocityFromXml(EvidencedStringType evStringType) {
		// double velocity, String unit, String enzyme,
		// List<Evidence> evidences

		String xmlValueString = evStringType.getValue();
		int pos1;
		pos1 = xmlValueString.indexOf(' ');
		int pos2 = xmlValueString.indexOf(' ', pos1 + 1);
		String velocityString = xmlValueString.substring(0, pos1);
		String unit = xmlValueString.substring(pos1 + 1, pos2);
		String enzyme = xmlValueString.substring(pos2 + 1);
		double velocity = Double.parseDouble(velocityString);

		List<Evidence> evidences = evRefMapper.parseEvidenceIds(evStringType.getEvidence());

		return BPCPCommentBuilder.createMaximumVelocity(velocity, unit, enzyme, evidences);
	}

	private EvidencedStringType maximumVelocityToXml(MaximumVelocity mv) {
		EvidencedStringType vmaxKinetics = xmlUniprotFactory.createEvidencedStringType();
		StringBuilder sb = new StringBuilder();
		sb.append(mv.getVelocity()).append(" ");
		sb.append(mv.getUnit()).append(" ");
		sb.append(mv.getEnzyme());
		vmaxKinetics.setValue(sb.toString());

		if (!mv.getEvidences().isEmpty())
			vmaxKinetics.getEvidence().addAll(evRefMapper.writeEvidences(mv.getEvidences()));
		return vmaxKinetics;
	}
}
