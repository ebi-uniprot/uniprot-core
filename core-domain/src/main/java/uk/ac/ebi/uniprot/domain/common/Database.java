package uk.ac.ebi.uniprot.domain.common;

/**
 * Created by IntelliJ IDEA.
 * User: Sam
 * Date: 15-Apr-2005
 * Time: 13:52:27
 * To change this template use File | Settings | File Templates.
 */
public interface Database  {

	public String getName();

	public String getFullName();

	int displayOrder();
	int sortType();

}
