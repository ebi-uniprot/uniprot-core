package org.uniprot.core.flatfile.parser.impl.dr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.exception.DatabaseTypeNotExistException;
import org.uniprot.core.flatfile.parser.impl.EvidenceCollector;
import org.uniprot.core.flatfile.parser.impl.EvidenceConverterHelper;
import org.uniprot.core.uniprotkb.InternalLine;
import org.uniprot.core.uniprotkb.InternalLineType;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.InternalLineBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

public class DrLineConverter extends EvidenceCollector
        implements Converter<DrLineObject, UniProtDrObjects> {
    private boolean ignoreWrongDR = false;

    public DrLineConverter() {}

    public DrLineConverter(boolean ignoreWrongDR) {
        this.ignoreWrongDR = ignoreWrongDR;
    }

    @Override
    public UniProtDrObjects convert(DrLineObject f) {

        UniProtDrObjects uniProtDrObjects = new UniProtDrObjects();
        if (f == null) return uniProtDrObjects;
        Map<Object, List<Evidence>> evidences =
                EvidenceConverterHelper.convert(f.getEvidenceInfo());
        this.addAll(evidences.values());
        for (DrLineObject.DrObject drline : f.drObjects) {
            if (drline.ssLineValue != null) addSSProsites(drline, uniProtDrObjects);
            else addDrLine(drline, uniProtDrObjects, evidences);
        }
        return uniProtDrObjects;
    }

    private void addSSProsites(DrLineObject.DrObject drline, UniProtDrObjects uniProtDrObjects) {
        if (drline.ssLineValue != null) {
            if (uniProtDrObjects.ssProsites == null)
                uniProtDrObjects.ssProsites = new ArrayList<InternalLine>();
            uniProtDrObjects.ssProsites.add(
                    new InternalLineBuilder(InternalLineType.PROSITE, drline.ssLineValue).build());
        }
    }

    private void addDrLine(
            DrLineObject.DrObject drline,
            UniProtDrObjects uniProtDrObjects,
            Map<Object, List<Evidence>> evidenceMap) {

        if (drline.ssLineValue != null) return;

        String id = drline.attributes.get(0);
        String description = drline.attributes.get(1);

        String thirdAttribute = null;
        String fourthAttribute = null;
        String isoformId = null;

        if (drline.attributes.size() > 2) {
            thirdAttribute = drline.attributes.get(2);
        }
        if (drline.attributes.size() > 3) {
            fourthAttribute = drline.attributes.get(3);
        }
        if ((drline.isoform != null) && (!drline.isoform.isEmpty())) {
            isoformId = drline.isoform;
        }
        try {
            UniProtKBDatabase type = new UniProtKBDatabaseImpl(drline.DbName);
            uniProtDrObjects.drObjects.add(
                    new UniProtCrossReferenceBuilder()
                            .id(id)
                            .database(type)
                            .isoformId(isoformId)
                            .propertiesAdd(type.getUniProtDatabaseAttribute(0), description)
                            .propertiesAdd(type.getUniProtDatabaseAttribute(1), thirdAttribute)
                            .propertiesAdd(type.getUniProtDatabaseAttribute(2), fourthAttribute)
                            .build());
        } catch (Exception e) {
            if (!ignoreWrongDR) throw new DatabaseTypeNotExistException(drline.DbName);
        }
    }
}
