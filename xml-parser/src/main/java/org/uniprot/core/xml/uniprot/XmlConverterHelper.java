package org.uniprot.core.xml.uniprot;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.common.base.Strings;

/**
 * Utility methods for comments for the text information value.
 *
 * <p><text>...</text>
 */
public final class XmlConverterHelper {

    public static String addIfNoPostfix(String text, String postfix) {
        if (Strings.isNullOrEmpty(text)) return text;
        if (!text.endsWith(postfix)) return text + postfix;
        else return text;
    }

    public static String removeIfPostfix(String xmlText, String postfix) {
        if (Strings.isNullOrEmpty(xmlText)) return xmlText;
        return xmlText.endsWith(postfix)
                ? xmlText.substring(0, xmlText.length() - postfix.length())
                : xmlText;
    }

    public static String uppercaseFirstLetter(String val) {

        if (Strings.isNullOrEmpty(val)) return val;
        return val.substring(0, 1).toUpperCase() + val.substring(1);
    }

    public static String lowercaseFirstLetter(String val) {
        if (Strings.isNullOrEmpty(val)) return val;
        return val.substring(0, 1).toLowerCase() + val.substring(1);
    }

    public static XMLGregorianCalendar dateToXml(LocalDate date) {
        try {
            GregorianCalendar gcal =
                    GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));

            XMLGregorianCalendar xmlCal =
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            xmlCal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
            return xmlCal;
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDate dateFromXml(XMLGregorianCalendar xmlDate) {
        return xmlDate.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }
}
