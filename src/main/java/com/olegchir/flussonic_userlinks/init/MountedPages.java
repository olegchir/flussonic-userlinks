package com.olegchir.flussonic_userlinks.init;
/**
 * Copyright (C) 2014 Oleg Chirukhin
 * Licensed under the Apache License 2.0,
 * see LICENSE-2.0.txt, LICENSE (it's a copy of LICENSE-2.0.txt) and NOTICE for additional information.
 */


import org.apache.wicket.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by olegchir on 26.12.14.
 */
public class MountedPages {
    private static volatile MountedPages instance;
    private HashMap<String,Class> mounts = new HashMap<String,Class>();

    public <T extends Page> void add(String path, final Class<T> pageClass) {
        mounts.put(path, pageClass);
    }

    public void remove(String path) {
        mounts.remove(path);
    }

    public boolean containsPath(String path) {
        return mounts.containsKey(path);
    }

    public boolean containsPathPrefix(String path) {
        Set<String> keySet = mounts.keySet();
        for (String key: keySet) {
            if (path.startsWith(key)) {
                return true;
            }
        }
        return false;
    }

    public <T extends Page> boolean containsPageClass(final Class<T> pageClass) {
        return mounts.containsValue(pageClass);
    }

    public static MountedPages getInstance() {
        MountedPages localInstance = instance;
        if (localInstance == null) {
            synchronized (MountedPages.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MountedPages();
                }
            }
        }
        return localInstance;
    }
}
