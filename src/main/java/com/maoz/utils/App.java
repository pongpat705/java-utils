package com.maoz.utils;

import com.maoz.utils.bean.Cmp;
import com.maoz.utils.bean.CvsBaaniaCmp;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        ObjectConverter converter = new ObjectConverter();
        
        Cmp source = new Cmp();
        source.setId(1L);
        source.setCompNo(2L);
        source.setPropertyId(1313L);
        source.setProjectCode("44EDS2");
        source.setProjectName("Namaste");
        source.setCurrentStatus("SALE");
        source.setPropertyType("Condo");
        source.setLatitude(12.3333);
        source.setLongitude(144.2223);
        
        CvsBaaniaCmp cmp = converter.convertValue(source, CvsBaaniaCmp.class);
        
        System.out.println(cmp.toString());
        
    }
}
