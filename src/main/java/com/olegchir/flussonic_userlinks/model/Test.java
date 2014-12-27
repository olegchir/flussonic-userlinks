package com.olegchir.flussonic_userlinks.model;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


/**
 * Created by olegchir on 27.12.14.
 */
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "test")
public class Test {

    @Id
    public int id;

    @Basic
    public String data;
}