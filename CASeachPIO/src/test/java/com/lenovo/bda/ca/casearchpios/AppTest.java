package com.lenovo.bda.ca.casearchpios;

import com.lenovo.bda.ca.casearchpios.entities.POI;
import com.lenovo.bda.ca.casearchpios.utilities.DBUtilities;
import com.lenovo.bda.ca.casearchpios.utilities.ESPOIIndexUtils;
import com.lenovo.bda.ca.casearchpios.utilities.PointCheckUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.awt.geom.Point2D;
import java.util.*;

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
    public void testString(){
        String s1 = "hello";
        String s = "hello";
        System.out.println(s == s1);
        String s2 = "he" + new String("llo");
        System.out.println(s1 == s2);
    }

    public void testRun() throws Exception{
        Thread t = new Thread(){
            public void run(){
                pong();
            }
        };
        t.start();
        Thread.sleep(10);
        System.out.print("ping");
    }

    static void pong(){
        System.out.println("pong");
    }

    public void testESearchByLocation(){
        System.out.println(ESPOIIndexUtils.searchbyLocationAndType("010100",116.546600,39.950031,5000d));
    }

    public void testESearchByTypeCode(){
        System.out.println(ESPOIIndexUtils.searchByKeyAndLocation("加油站",116.546600,39.950031,5000d));
    }

    public void testESearchBykeyAndType(){
        System.out.println(ESPOIIndexUtils.searchBykeyAndTypeCode("010100","旺平东",116.546600,39.950031,5000d));
    }

    public void testESearchBylocations(){
        List<Point2D.Double> doubles = new LinkedList<>();
        doubles.add(new Point2D.Double(116.546600,39.950031));
        doubles.add(new Point2D.Double(116.546600,38.950031));
        doubles.add(new Point2D.Double(116.646600,38.950031));
        long init = new Date().getTime();
        System.out.println(ESPOIIndexUtils.searchByLocationsAndKeyword(doubles,"加油站"));
        long begin = new Date().getTime();
        System.out.println(begin - init);
        System.out.println(ESPOIIndexUtils.searchBylocationsAndkeyAndtype(doubles,"旺平东","010100"));
        long end = new Date().getTime();
        System.out.println(end-begin);
        //System.out.println(ESPOIIndexUtils.searchByLocationsAndType(doubles,"010100"));
    }
    public void testSin(){
        System.out.println(Math.sin(Math.PI /6));
    }

    public void testSortList(){
        List<Point2D.Double> doubles = new LinkedList<>();
        doubles.add(new Point2D.Double(1,2));
        doubles.add(new Point2D.Double(2,3));
        doubles.add(new Point2D.Double(0.5,2.5));
        List<Point2D.Double> copeDoubles = new LinkedList<>(doubles);
        List<Point2D.Double> doubles1 = PointCheckUtils.sortLocation(doubles, "lang");
        for (Point2D.Double d :doubles1){
            System.out.println(d.getX());
        }
        System.out.println("=======================================");
        List<Point2D.Double> doubles2 = PointCheckUtils.sortLocation(doubles, "lat");
        for (Point2D.Double d :doubles2){
            System.out.println(d.getY());
        }
        System.out.println("=======================================");
        for (Point2D.Double d :doubles){
            System.out.println(d.getX()+"\t"+d.getY());
        }
        System.out.println("=======================================");
        doubles.clear();
        for (Point2D.Double d :copeDoubles){
            System.out.println(d.getX()+"\t"+d.getY());
        }

    }
}
