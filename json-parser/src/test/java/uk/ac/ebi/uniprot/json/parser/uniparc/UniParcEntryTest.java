package uk.ac.ebi.uniprot.json.parser.uniparc;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.ebi.uniprot.domain.Location;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.builder.SequenceBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.SequenceFeature;
import uk.ac.ebi.uniprot.domain.uniparc.SignatureDbType;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcDatabaseType;
import uk.ac.ebi.uniprot.domain.uniparc.UniParcEntry;
import uk.ac.ebi.uniprot.domain.uniparc.builder.InterProGroupBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.builder.SequenceFeatureBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.builder.UniParcDBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.builder.UniParcEntryBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

/**
 *
 * @author jluo
 * @date: 24 May 2019
 *
*/

public class UniParcEntryTest {
	@Test
	void test() {
		UniParcEntry entry = createEntry() ;
		
		ValidateJson.verifyJsonRoundTripParser(UniParcJsonConfig.getInstance().getFullObjectMapper(), entry);
		
		try {
	           ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
	           String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
	           System.out.println(json);
	       }catch(Exception e) {
	           fail(e.getMessage());
	       }
	}
	
	private UniParcEntry createEntry() {
		UniParcEntryBuilder builder = new  UniParcEntryBuilder();
		 String sequence =
	                "MALYSISKPVGSKINKHSYQDENTLVGKQALSKGTEKTKLSTNFEINLPRRTVLSDVSNV"
	              + "GKNNADEKDTKKAKRSFDESNLSTNEEADKPVESKFVKKLKVYSKNADPSVETLQKDRVS"
	              + "NVDDHLSSNPLMAEEYAPEIFEYIRKLDLKCLPNPKYMDQQKELTWKMREILNEWLVEIH"
	              + "SNFCLMPETLYLAVNIIDRFLSRRSCSLSKFQLTGITALLIASKYEEVMCPSIQNFVYMT"
	              + "DGAFTVEDVCVAERYMLNVLNFDLSYPSPLNFLRKISQAEGYDAQTRTLGKYLTEIYLFD"
	              + "HDLLRYPMSKIAAAAMYLSRRLLRRGPWTPKLVESSGGYEEHELKEIAYIMLHYHNKPLE"
	              + "HKAFFQKYSSKRFLKASIFVHQLVRQRYSVNRTDDDDLQSEPSSSLTNDGH";
		   Sequence uniSeq = new SequenceBuilder(sequence).build();
		   List<SequenceFeature> sfs = new ArrayList<>();
		   SequenceFeatureBuilder sfBuilder = new SequenceFeatureBuilder();
		   sfBuilder.signatureDbType(SignatureDbType.PANTHER)
			.signatureDbId("PTHR11977")
			.addLocation(new Location(49, 790))
			.interproGroup(
			new InterProGroupBuilder()
			.id("IPR007122")
			.name("Villin/Gelsolin")
			.build());
		   
		   SequenceFeatureBuilder sfBuilder2 = new SequenceFeatureBuilder();
		   sfBuilder2.signatureDbType(SignatureDbType.PFAM)
			.signatureDbId("PF00626")
			.addLocation(new Location(81, 163))
			.addLocation(new Location(202, 267))
			.addLocation(new Location(330, 398))
			.addLocation(new Location(586, 653))
			.addLocation(new Location(692, 766))
			.interproGroup(
			new InterProGroupBuilder()
			.id("IPR007123")
			.name("Gelsolin-like domain")
			.build());
		   sfs.add(sfBuilder.build());
		   sfs.add(sfBuilder2.build());
		   builder.uniParcId("UPI0000083A08")
		   .sequence(uniSeq)
		   .sequenceFeatures(sfs)
		   .addDatabaseCrossReference(
				   new UniParcDBCrossReferenceBuilder()
				   .databaseType(UniParcDatabaseType.ENSEMBL_VERTEBRATE)
					.id("CG1106-PB" )
					.versionI(1)
					.active(false)
					.created(LocalDate.of(2003, 4, 1))
					.lastUpdated(LocalDate.of(2007, 11, 22)).build())
		 
		 ;
		   
			UniParcDBCrossReferenceBuilder xrefBuilder = new UniParcDBCrossReferenceBuilder();
			xrefBuilder.databaseType(UniParcDatabaseType.TREMBL)
			.id("A0A0C4DHG2" )
			.versionI(1)
			.version(1)
			.active(true)
			.created(LocalDate.of(2015, 4, 1))
			.lastUpdated(LocalDate.of(2019, 5, 8));
			List<Property> properties = new ArrayList<>();
			properties.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "9606"));
			properties.add(new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "Gelsolin, isoform J"));
			properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "Gel"));
		
			xrefBuilder.properties(properties);  
			builder.addDatabaseCrossReference(xrefBuilder.build());
			
			//id="NC_004354_874_0" version_i="5" active="Y" created="2007-04-27" last="2007-04-27">
			UniParcDBCrossReferenceBuilder xrefBuilder2 = new UniParcDBCrossReferenceBuilder();
			xrefBuilder2.databaseType(UniParcDatabaseType.TROME)
			.id("NC_004354_874_0" )
			.versionI(5)
			.active(true)
			.created(LocalDate.of(2007, 4, 27))
			.lastUpdated(LocalDate.of(2007, 4, 27));
			List<Property> properties2 = new ArrayList<>();
			properties2.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "7227"));
			xrefBuilder2.properties(properties2);  
			builder.addDatabaseCrossReference(xrefBuilder2.build());
		   return builder.build();
	}
}

