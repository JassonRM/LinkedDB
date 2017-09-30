package org.tec.datos1.linkeddb.test;

import junit.framework.TestCase;
import org.tec.datos1.linkeddb.Store;

public class StoreTest extends TestCase {
    public void testStore(){
        Store store = new Store("Prueba");
        assertEquals("Prueba", store.getName());
    }
}
