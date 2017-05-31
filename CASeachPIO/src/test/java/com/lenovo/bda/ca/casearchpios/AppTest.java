package com.lenovo.bda.ca.casearchpios;

import com.lenovo.bda.ca.casearchpios.entities.POI;
import com.lenovo.bda.ca.casearchpios.utilities.DBUtilities;
import com.lenovo.bda.ca.casearchpios.utilities.ESPOIIndexUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testPOIJson(){
        HashMap<Integer, POI> poiHashMap = DBUtilities.GetPOIMap(0, 2);
        for (Map.Entry entry:poiHashMap.entrySet()) {
            POI poi = (POI) entry.getValue();
            //poi.setPhotos(null);
            Date insertTime = poi.getInsertTime();
            //poi.setInsertTime(null);
            System.out.println( poi.toJsonString());
        }

    }

   /* public void testESearchByTypeCode(){
        System.out.println(ESPOIIndexUtils.byTypeCode("991000"));
    }

    public void testESearchByKeyWords(){
        System.out.println(ESPOIIndexUtils.byKeyWords("旺平东"));
    }

    public void testESearchByKeyWordWithTerm(){
        System.out.println(ESPOIIndexUtils.byKeyWordsWithTerm("旺平东"));
    }

    public void testESearchByKeyAndType(){
        System.out.println(ESPOIIndexUtils.byKeyWordsAndTypeCode("010100","旺平东"));
    }


    public void testESearchByTypeCodeAndLocation(){
        //ESPOIIndexUtils.byTypeCodeAndLocation("010100",116.546600,39.950031);
    } */


    public void testESearchByLocation(){
        ESPOIIndexUtils.SearchbyLocationAndType("010100",116.546600,39.950031,0d);
    }
}
