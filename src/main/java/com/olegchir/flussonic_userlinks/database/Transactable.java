package com.olegchir.flussonic_userlinks.database;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by olegchir on 27.12.14.
 */
public interface Transactable {
        void run(EntityManager em, EntityTransaction tx);
}
