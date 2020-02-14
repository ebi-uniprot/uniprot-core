package org.uniprot.core.flatfile.parser.impl.rl;

import java.util.ArrayList;
import java.util.List;

/** User: wudong, Date: 21/08/13, Time: 16:44 */
public class RlLineObject {

    private Ref reference;

    public Ref getReference() {
        return reference;
    }

    public void setReference(Ref reference) {
        this.reference = reference;
    }

    public interface Ref {}

    public static class Book implements Ref {
        private List<String> editors = new ArrayList<>();
        private String title;
        private String pageStart;
        private String pageEnd;
        private String volume;
        // to accomodate something like "abstract#543"
        private String pageString;
        private String press;
        private String place;
        private int year;

        public List<String> getEditors() {
            return editors;
        }

        public void setEditors(List<String> editors) {
            this.editors = editors;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPageStart() {
            return pageStart;
        }

        public void setPageStart(String pageStart) {
            this.pageStart = pageStart;
        }

        public String getPageEnd() {
            return pageEnd;
        }

        public void setPageEnd(String pageEnd) {
            this.pageEnd = pageEnd;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getPageString() {
            return pageString;
        }

        public void setPageString(String pageString) {
            this.pageString = pageString;
        }

        public String getPress() {
            return press;
        }

        public void setPress(String press) {
            this.press = press;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class Thesis implements Ref {
        private String institute;
        private String country;
        private int year;
        // missing possible city

        public String getInstitute() {
            return institute;
        }

        public void setInstitute(String institute) {
            this.institute = institute;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class JournalArticle implements Ref {
        private String journal;
        private int year;
        private String volume;
        private String firstPage;
        private String lastPage;

        public String getJournal() {
            return journal;
        }

        public void setJournal(String journal) {
            this.journal = journal;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(String firstPage) {
            this.firstPage = firstPage;
        }

        public String getLastPage() {
            return lastPage;
        }

        public void setLastPage(String lastPage) {
            this.lastPage = lastPage;
        }
    }

    public static class EPub implements Ref {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Patent implements Ref {
        private String patentNumber;
        private int year;
        private String month;
        private int day;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getPatentNumber() {
            return patentNumber;
        }

        public void setPatentNumber(String patentNumber) {
            this.patentNumber = patentNumber;
        }
    }

    public static class Unpublished implements Ref {
        private String month;
        private int year;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class Submission implements Ref {
        private SubmissionDB db;
        private int year;
        private String month;

        public SubmissionDB getDb() {
            return db;
        }

        public void setDb(SubmissionDB db) {
            this.db = db;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }

    public enum SubmissionDB {
        EMBL,
        UNIPROTKB,
        PDB,
        PIR
    }
}
