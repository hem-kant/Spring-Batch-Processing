package com.hemkant.batchprocessing.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="OrderData")
public class OrderData {
    @Id
    @Column(name = "ORDER_ID")
    private int id;

    @Column(name="NAME")
    private String name;

    @Column(name="COST")
    private int cost;

    @Column(name="CONTACT")
    private int contact;

    @Column(name="VENDOR")
    private String vendor;

}
