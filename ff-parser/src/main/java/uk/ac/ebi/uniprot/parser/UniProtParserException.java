package uk.ac.ebi.uniprot.parser;

public class UniProtParserException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * <em class="text">
     * Static value to indicate that an Exception was thrown,
     * and the line number where it occured is unknown.
     * </em>
     */
    public static final int UNKNOWN_LINENUMBER = -1;

    /**
     * <em class="text">
     * The ID of the Entry where the Exception was thrown.
     * </em>
     */
    public static String id;

    /**
     * <em class="text">
     * The original Exception that was actually thrown.
     * </em>
     */
    public Exception originalException;

    /**
     * <em class="text">
     * The number of the line where the Exception was thrown.
     * </em>
     */
    public int linenumber;

    /**
     * <em class="text">
     * Constructs an YASPException with an unknown linenumber.
     * </em>
     * <br><br>
     *
     * @param originalException The original Exception that was thrown.
     */
    public UniProtParserException(Exception originalException) {
        this(originalException, UNKNOWN_LINENUMBER);
    }

    public UniProtParserException(String message) {
        super(message);
        this.linenumber = UNKNOWN_LINENUMBER;
    }

    /**
     * <em class="text">
     * Constructs an UniProtParserException contatining an original exception
     * and the line number where the Exception occured.
     * </em>
     * <br><br>
     *
     * @param originalException The original Exception that was thrown.
     * @param linenumber        The number of the line where the Exception was thrown.
     */
    public UniProtParserException(Exception originalException, int linenumber) {
        super("Line Number:" + linenumber, originalException);
        this.originalException = originalException;
        this.linenumber = linenumber;
    }

    /**
     * <em class="text">
     * Constructs an UniProtParserException contatining an original exception,
     * the line number where the Exception occured and additional
     * message.
     * </em>
     * <br><br>
     *
     * @param originalException The original Exception that was thrown
     * @param linenumber        The number of the line where the Exception was thrown
     * @param message           A message describing the original Exception in more detail
     */
    public UniProtParserException(Exception originalException, int linenumber, String message) {
        super(message, originalException);
        this.originalException = originalException;
        this.linenumber = linenumber;
    }

    /**
     * <em class="text">
     * Constructs an UniProtParserException given the line number, where it
     * occured and a message String
     * </em>
     * <br><br>
     *
     * @param linenumber The number of the line where the Exception was thrown
     * @param message    A message describing the original Exception in more detail
     */
    public UniProtParserException(int linenumber, String message) {
        super(message);
        this.linenumber = linenumber;
    }

    /**
     * <em class="text">
     * Returns the Accession Number of the Entry where this Exception was thrown.
     * </em>
     * <br><br>
     *
     * @return The Accession Number of the Entry where this Exception was thrown.
     */
    public static String getId() {
        return id;
    }

    /**
     * <em class="text">
     * Sets the ID of the Entry that is currently paresed and
     * which will be the only one during whose parsing this Exception is thrown.
     * Note that this mechanism doesn't yasp when Entries are parsed in parallel.
     * </em>
     * <br><br>
     *
     * @param currentID The ID of the Entry that is currently paresed
     */
    public static void setID(String currentID) {
        id = currentID;
    }

    /**
     * <em class="text">
     * Returns the original Exception that was actually thrown during the
     * parsing routine.
     * </em>
     * <br><br>
     *
     * @return The original Exception that was actually thrown during the
     *         parsing routine.
     */
    public Exception getOriginalException() {
        return originalException;
    }

    /**
     * <em class="text">
     * Returns the line number, where the original Exception was thrown.
     * </em>
     * <br><br>
     *
     * @return The line number, where the original Exception was thrown.
     */
    public int getLinenumber() {
        return linenumber;
    }


    @Override
    public String getMessage() {
        return originalException.getMessage();
    }

}
