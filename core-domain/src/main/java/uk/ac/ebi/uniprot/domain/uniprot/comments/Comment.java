package uk.ac.ebi.uniprot.domain.uniprot.comments;

/**
 * A Comment in the {@link uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry UniProtEntry}.<br><br>
 * Notes:
 * <ul>
 * <li>
 * public void setCommentType(CommentType type);<br>
 * removed because it causes havoc if textual
 * comments change into structured ones. Hence,
 * a comment does not change type once it is built.
 * </li>
 * <li>
 * To build a comment, use
 * {@link uk.ac.ebi.kraken.interfaces.factories.CommentFactory#buildComment(uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType) CommentFactory.buildComment(CommentType)}
 * </li>
 * <li>
 * Some Comments have further strucutrs such
 * as the {@link uk.ac.ebi.uniprot.domain.uniprot.comments.Interaction Interaction}
 * Comment.
 * </li>
 * <li>
 * The getValue()
 * method of each Comment implementation will return the UniProt flat file
 * representation of the Comment without linewrapping or "CC   -!- " /
 * "CC       " prefixes.
 * </li>
 * <li>
 * The setValue(String)
 * method of each of the implementations of the further structured Comments
 * will set the the corresponding fields simultaneously.
 * </li>
 * </ul>
 * <br><br>
 * These values can be found in the CC lines of the flat file on the marked position.
 * <pre class="example"><font color="#AAAAAA">   RT   "Cytochrome c in the apoptotic and antioxidant cascades.";
 * RL   FEBS Lett. 423:275-280(1998).
 * <font color="#000000">CC   -!- INTERACTION:</font>
 * <font color="#000000">CC       O14727:APAF1; NbExp=2; IntAct=EBI-446479, EBI-446492;</font>
 * <font color="#000000">CC   -!- SUBCELLULAR LOCATION: Mitochondrial matrix.</font>
 * <font color="#000000">CC   -!- PTM: Binds 1 heme group per subunit.</font>
 * DR   EMBL; M22877; AAA35732.1; -; Genomic_DNA.
 * DR   PIR; A31764; CCHU.
 * ...</font></pre>
 * In XML:
 * <pre><font color="#AAAAAA"> ...
 * &lt;scope&gt;REVIEW ON ROLE IN APOPTOSIS.&lt;/scope&gt;
 * &lt;/reference&gt;
 * <font color="#000000">&lt;comment type="interaction"&gt;
 * &lt;interactant intactId="EBI-446479"/&gt;
 * &lt;interactant intactId="EBI-446492"&gt;
 * &lt;id&gt;O14727&lt;/id&gt;
 * &lt;label&gt;APAF1&lt;/label&gt;
 * &lt;/interactant&gt;
 * &lt;organismsDiffer&gt;false&lt;/organismsDiffer&gt;
 * &lt;experiments&gt;2&lt;/experiments&gt;
 * &lt;/comment&gt;
 * &lt;comment type="subcellular location"&gt;
 * &lt;text&gt;Mitochondrial matrix&lt;/text&gt;
 * &lt;/comment&gt;
 * &lt;comment type="PTM"&gt;
 * &lt;text&gt;Binds 1 heme group per subunit&lt;/text&gt;
 * &lt;/comment&gt;</font>
 * &lt;dbReference type="EMBL" id="M22877" key="24"&gt;
 * &lt;property type="protein sequence ID" value="AAA35732.1"/&gt;
 * ...
 * </font></pre>
 */
public interface Comment {
    public CommentType getCommentType();


}
