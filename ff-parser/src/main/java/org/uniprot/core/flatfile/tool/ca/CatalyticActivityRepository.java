package org.uniprot.core.flatfile.tool.ca;

public interface CatalyticActivityRepository {
    CatalyticActivity getByRheaId(String rheaId);

    CatalyticActivity getByOldText(String text);
}
