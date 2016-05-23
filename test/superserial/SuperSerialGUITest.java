/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package superserial;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Carlton Johnson
 */
public class SuperSerialGUITest {
    
    public SuperSerialGUITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sendStringInd method, of class SuperSerialGUI.
     */
    @Test
    public void testSendStringInd() {
        System.out.println("sendStringInd");
        Individual out = null;
        SuperSerialGUI instance = new SuperSerialGUI();
        instance.sendStringInd(out);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class SuperSerialGUI.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        SuperSerialGUI.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
