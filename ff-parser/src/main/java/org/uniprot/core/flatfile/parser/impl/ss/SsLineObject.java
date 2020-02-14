package org.uniprot.core.flatfile.parser.impl.ss;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** User: wudong, Date: 22/09/13, Time: 23:12 */
public class SsLineObject {

    private List<SsLine> ssIALines = new ArrayList<>();
    private List<EvLine> ssEVLines = new ArrayList<>();
    private List<String> ssSourceLines = new ArrayList<>();

    public List<SsLine> getSsIALines() {
        return ssIALines;
    }

    public void setSsIALines(List<SsLine> ssIALines) {
        this.ssIALines = ssIALines;
    }

    public List<EvLine> getSsEVLines() {
        return ssEVLines;
    }

    public void setSsEVLines(List<EvLine> ssEVLines) {
        this.ssEVLines = ssEVLines;
    }

    public List<String> getSsSourceLines() {
        return ssSourceLines;
    }

    public void setSsSourceLines(List<String> ssSourceLines) {
        this.ssSourceLines = ssSourceLines;
    }

    public static class SsLine {
        private String topic;
        private String text;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class EvLine {
        private String id;
        private String db;
        private String attr1;
        private String attr2;
        private LocalDate date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDb() {
            return db;
        }

        public void setDb(String db) {
            this.db = db;
        }

        public String getAttr1() {
            return attr1;
        }

        public void setAttr1(String attr1) {
            this.attr1 = attr1;
        }

        public String getAttr2() {
            return attr2;
        }

        public void setAttr2(String attr2) {
            this.attr2 = attr2;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }
    }
}
