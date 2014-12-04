package containing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hugo
 */
public class ContComparator {
    
    
    public static class CompadatumJ implements Comparator<Container> {
        @Override
        public int compare(Container arg0, Container arg1) {
            return arg0.adatumJ - arg1.adatumJ;
        }
    }
    
    public static class CompadatumM implements Comparator<Container> {
        @Override
        public int compare(Container arg0, Container arg1) {
            return arg0.adatumM - arg1.adatumM;
        }
    }
        
            public static class CompadatumD implements Comparator<Container> {
        @Override
        public int compare(Container arg0, Container arg1) {
            return arg0.adatumD - arg1.adatumD;
        }
    }
    public static class CompaVan implements Comparator<Container> {
        private int mod = 1;
        public CompaVan(boolean desc) {
            if (desc) mod =-1;
            
        }
        @Override
        public int compare(Container arg0, Container arg1) {
            
            return mod*(Double.valueOf(arg0.aVan).toString()).compareTo(Double.valueOf(arg1.aVan).toString());
        }
    }
    public static class CompaTot implements Comparator<Container> {
        private int mod = 1;
        public CompaTot(boolean desc) {
            if (desc) mod =-1;
            
        }
        @Override
        public int compare(Container arg0, Container arg1) {
            
            return mod*(Double.valueOf(arg0.aTot).toString()).compareTo(Double.valueOf(arg1.aTot).toString());
        }
    }
}
