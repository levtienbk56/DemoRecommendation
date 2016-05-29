package org.hedspi.tienlv.demorecommender.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tienlv.hust on 5/12/2016.
 */
public class Ads {
    private String content;
    private List<String> labels;
    private Date timeEnd;

    public Ads(){
        content = "";
        labels = new LinkedList<>();
        timeEnd = new Date();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public void addLabel(String label){
        this.labels.add(label);
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }
}