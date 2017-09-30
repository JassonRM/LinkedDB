package org.tec.datos1.linkeddb.test;

import junit.framework.TestCase;
import org.tec.datos1.linkeddb.Store;

import java.io.File;

public class MyTestClass extends TestCase {
    public MyTestClass(String string) {
        super(string);
    }

    public void testStoreDir(){
        Store store = new Store("TestStore");
        File dir = store.getDir();
        assert(dir.exists());
    }
}



