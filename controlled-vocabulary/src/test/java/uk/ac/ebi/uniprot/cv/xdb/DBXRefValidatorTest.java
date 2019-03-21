package uk.ac.ebi.uniprot.cv.xdb;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.common.Pair;
import uk.ac.ebi.uniprot.cv.xdb.DatabaseCategory;
import uk.ac.ebi.uniprot.cv.xdb.UniProtXDbTypeDetail;
import uk.ac.ebi.uniprot.cv.xdb.UniProtXDbTypes;
import uk.ac.ebi.uniprot.cv.xdb.validator.DBXRefValidator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBXRefValidatorTest {

    @Test
    void testSuccessfulValidation() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("EMBL");
        // validate
        List<Pair<String, String>> mismatches = DBXRefValidator.validate(opType);
        assertTrue(mismatches.isEmpty());
    }

    @Test
    void testFailedValidation() {
        UniProtXDbTypeDetail opType = UniProtXDbTypes.INSTANCE.getType("ArachnoServer");

        UniProtXDbTypeDetail actualOpType = new UniProtXDbTypeDetail(opType.getName(), opType.getDisplayName(),
                DatabaseCategory.CHEMISTRY, opType.getUriLink(), opType.getAttributes());

        // validate, the category should mismatch
        List<Pair<String, String>> mismatches = DBXRefValidator.validate(actualOpType);
        assertEquals(1, mismatches.size());
        assertEquals(DatabaseCategory.ORGANISM_SPECIFIC_DATABASES.getDisplayName(), mismatches.get(0).getKey());// expected
        assertEquals(DatabaseCategory.CHEMISTRY.getDisplayName(), mismatches.get(0).getValue());// actual

    }

    @Test
    void testValidateEachDBXRef(){
        for(UniProtXDbTypeDetail dbTypeDetails : UniProtXDbTypes.INSTANCE.getAllDBXRefTypes()){
            DBXRefValidator.validate(dbTypeDetails);
        }
    }
}
