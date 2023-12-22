package com.ganc.banking.models;

import jakarta.persistence.*;

@Entity
@Table(name="etudiants")
public class Students {
    //@Entity have to an @Id
    @Id
    @GeneratedValue
    @Column
    public Integer id;
    public String firstname;
}
