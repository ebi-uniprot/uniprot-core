package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class CC {
    private CcLineObject.CCTopicEnum topic;
    private Object object;

    public CcLineObject.CCTopicEnum getTopic() {
        return topic;
    }

    public void setTopic(CcLineObject.CCTopicEnum topic) {
        this.topic = topic;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
