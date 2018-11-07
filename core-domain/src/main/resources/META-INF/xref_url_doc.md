# Cross reference url explanation

The url template is used to cross reference url, **%value** will be replaced by the real value. This fits to most of the cases.
In some cases, uniprot accession is also required, **%acc** will be replaced by uniprot accession.

There are some exceptions. Following are the list of exceptions

## UniGene
The first part is for ORG and second part is CID, separated by dot.

## Reproduction2dpage
The url is not working

## UCSC
The url is linked to uniprot accession only, and **%acc** is in the url template

## EcoGene
The url may not work

## eggNOG
Both uniprot accession and cross reference id are required for this cross reference. 
The url template has two parameters: **%acc** and **%value**.

## HOVERGEN
The url is not working

## OMA
The url is linked to uniprot accession only, and **%acc** is in the url template

## BRENDA
Both uniprot accession and cross reference id are required for this cross reference. 
The url template has two parameters: **%acc** and **%value**.

## Reactome
Both uniprot accession and cross reference id are required for this cross reference. 
The url template has two parameters: **%acc** and **%value**.

## UniPathway
no url, the site is closed

## Genevisible
This url template contains two parameter for this cross reference. The first one is
**%value** which is cross reference id. The second parameter is **%des** which is
the first attribute of the cross reference organism id.
