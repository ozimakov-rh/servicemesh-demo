package com.redhat.demo;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "review")
public class Review extends PanacheEntity {

    public String author;
    public String text;
    public Date lastUpdated;
    public int score;
    public long objectId;

}
