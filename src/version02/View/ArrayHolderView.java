package version02.View;

import com.sun.star.awt.Rectangle;
import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.chart.XChartDocument;
import com.sun.star.chart.XDiagram;
import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.document.XEmbeddedObjectSupplier;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.sheet.*;
import com.sun.star.table.*;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.XInterface;


import version02.Controller.ArrayHolderController;
import version02.Controller.ArrayHolderControllerImpl;
import version02.Controller.Sort;
import version02.Model.ArrayHolder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tetya on 21.07.2017.
 */
public class ArrayHolderView {


    ArrayHolderController controller = new ArrayHolderControllerImpl();

    public ArrayHolderView(ArrayHolderController controller) {
        this.controller = controller;
    }

    public Episode sort(Episode e, int index) throws InvocationTargetException, IllegalAccessException {
        int [] size = new int[10];
        for (int i = 0; i < size.length ; i++) {
            size[i] = 1000 + i*1000;
        }

        List<ResultSort> resultSortList = new ArrayList<>();//result of all sort
        ArrayHolder a = new ArrayHolder();

        //cycle by sort
        Class c = controller.getClass();

        Method[] methods = c.getMethods();
        for (Method method : methods){
            Sort sortAnnotation = method.getDeclaredAnnotation(Sort.class); //My Annotation
            if (sortAnnotation != null ) {
            //reflection
                long[] timeSorts = new long[size.length];
                for (int j = 0; j < size.length; j++) {
                   switch (index){
                       case 1:{a.setArray(e.generateArraySortUp(size[j]));break;}
                       case 2:{a.setArray(e.generateArraySortUpPlusRandom(size[j]));break;}
                       case 3:{a.setArray(e.generateArraySortDown(size[j]));break;}
                       case 4:{a.setArray(e.generateArrayRandom(size[j]));break;}
                  }

                    timeSorts[j] = (long) method.invoke(controller, a);//sorting process
                }
                ResultSort r = new ResultSort();
                r.setNameSort(method.getName());
                r.setSizeArrayHolder(size);
                r.setTimeSort(timeSorts);
                resultSortList.add(r);
            }

        }
        e.setSorters(resultSortList);
        return e ;
    }

    public void episodeToString(Episode e){
        System.out.print(e.getNameEpisode() + ";");
        for (int i : e.getSorters().get(0).getSizeArrayHolder()) {
            System.out.print(i + ";");
        }
        System.out.println();

        for (ResultSort r:e.getSorters()) {
            System.out.print(r.getNameSort() +";");
            for (long i : r.getTimeSort()) {
                System.out.print(Math.round(i/10) + ";");
            }
            System.out.println();
        }
        System.out.println("============================");

    }

    private XSpreadsheetDocument myDoc = null;

    public void openDocument(){
        com.sun.star.uno.XComponentContext xContext = null;

        try {
            // get the remote office component context
            xContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
            if( xContext != null )
                System.out.println("Connected to a running office ...");
        }
        catch( Exception e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }

        //Open Calc document
        myDoc = openCalc(xContext);
    }

    public void episodeToCalc(Episode episode, int page){
        //page = 0;
        myDoc.getSheets().insertNewByName(episode.getNameEpisode(), (short) page);
        //give parameters from episodes to Calc
        XSpreadsheet xSheet = null;
        try {
            XSpreadsheets xSheets = myDoc.getSheets() ;

            XIndexAccess oIndexSheets = UnoRuntime.queryInterface(XIndexAccess.class, xSheets);

            xSheet = UnoRuntime.queryInterface(XSpreadsheet.class, oIndexSheets.getByIndex(page));
        } catch (Exception e) {
            System.out.println("Couldn't get Sheet " +e);
            e.printStackTrace(System.err);
        }
        insertIntoCell(0,0,episode.getNameEpisode(),xSheet,"");
        for (int i = 0; i < episode.getSorters().get(0).getSizeArrayHolder().length; i++) {
            String s = String.valueOf(episode.getSorters().get(0).getSizeArrayHolder()[i]);
            insertIntoCell(0,i+1,s,xSheet,"");
        }

        int j = 1;
        for (ResultSort r: episode.getSorters()) {
            insertIntoCell(j,0,r.getNameSort(),xSheet,"");
            for (int i = 0; i < r.getSizeArrayHolder().length; i++) {
                String s = String.valueOf(r.getTimeSort()[i]);
                insertIntoCell(j,i+1,s,xSheet,"");
            }
            j++;
        }
        // insert a chart
        Rectangle oRect = new Rectangle();
        oRect.X = 500;
        oRect.Y = 3500;
        oRect.Width = 35000;
        oRect.Height = 12000;

        XCellRange oRange = UnoRuntime.queryInterface(XCellRange.class, xSheet);
        //data
               //A2 begin data -> 0
               //G11 end data -> count of sorts episode.getSorters().size();
        String s = "A2:" + stringFromCountSort(episode.getSorters().size());
        s+=episode.getSorters().get(0).getSizeArrayHolder().length+1;

        XCellRange myRange = oRange.getCellRangeByName(s);

        XCellRangeAddressable oRangeAddr = UnoRuntime.queryInterface(XCellRangeAddressable.class, myRange);
        CellRangeAddress myAddr = oRangeAddr.getRangeAddress();

        CellRangeAddress[] oAddr = new CellRangeAddress[1];
        oAddr[0] = myAddr;
        XTableChartsSupplier oSupp = UnoRuntime.queryInterface(XTableChartsSupplier.class, xSheet);

        XTableChart oChart = null;

        XTableCharts oCharts = oSupp.getCharts();
        oCharts.addNewByName(episode.getNameEpisode(), oRect, oAddr, true, true);

        // get the diagramm and Change some of the properties
        try {
            oChart = (UnoRuntime.queryInterface(
                    XTableChart.class, UnoRuntime.queryInterface(XNameAccess.class, oCharts)
                            .getByName(episode.getNameEpisode())));
            XEmbeddedObjectSupplier oEOS = UnoRuntime.queryInterface(XEmbeddedObjectSupplier.class, oChart);
            XInterface oInt = oEOS.getEmbeddedObject();
            XChartDocument xChart = UnoRuntime.queryInterface(XChartDocument.class,oInt);

            XDiagram oDiag = xChart.getDiagram();
            XPropertySet oCPS = UnoRuntime.queryInterface(XPropertySet.class, oDiag );

            //============
            //Legend
            //System.out.println(xChart.getLegend().getClass());
            XPropertySet oLPS = UnoRuntime.queryInterface(XPropertySet.class, xChart.getLegend() );

            com.sun.star.awt.Size size = xChart.getLegend().getSize();
            size.Height = 100;
            size.Width = 100;
            //xChart.getLegend().setSize(size);
            oLPS.setPropertyValue( "com.sun.star.awt.Size", size);
            com.sun.star.awt.Point point = xChart.getLegend().getPosition();
            point.X = 100;
            point.Y  = 100;
            //xChart.getLegend().setPosition(point);
            oLPS.setPropertyValue( "com.sun.star.awt.Point", point);
            //============
            //name Episode
            XPropertySet oTPS = UnoRuntime.queryInterface(XPropertySet.class, xChart.getTitle() );
            oTPS.setPropertyValue("String",episode.getNameEpisode());
        } catch (Exception e){
            System.err.println("Changin Properties failed "+e);
            e.printStackTrace(System.err);
        }
    }

    public void closeDocument(){
        //System.out.println("done");
        System.exit(0);
    }

    public static String stringFromCountSort(int count){
        String l = "";
        switch (count){
            case 1: l= "B";break;
            case 2: l= "C";break;
            case 3: l= "D";break;
            case 4: l= "E";break;
            case 5: l= "F";break;
            case 6: l= "G";break;
            case 7: l= "H";break;
            case 8: l= "J";break;
            case 9: l= "K";break;
        }
        return l;
    }

    //++
    public static XSpreadsheetDocument openCalc(XComponentContext xContext)
    {
        //define variables
        XMultiComponentFactory xMCF = null;
        XComponentLoader xCLoader;
        XSpreadsheetDocument xSpreadSheetDoc = null;
        XComponent xComp = null;

        try {
            // get the servie manager rom the office
            xMCF = xContext.getServiceManager();

            // create a new instance of the desktop
            Object oDesktop = xMCF.createInstanceWithContext("com.sun.star.frame.Desktop", xContext );

            // query the desktop object for the XComponentLoader
            xCLoader = UnoRuntime.queryInterface(XComponentLoader.class, oDesktop );

            PropertyValue[] szEmptyArgs = new PropertyValue [0];
            String strDoc = "private:factory/scalc";

            xComp = xCLoader.loadComponentFromURL(strDoc, "_blank", 0, szEmptyArgs );
            xSpreadSheetDoc = UnoRuntime.queryInterface(XSpreadsheetDocument.class, xComp);

        } catch(Exception e){
            System.err.println(" Exception " + e);
            e.printStackTrace(System.err);
        }
        return xSpreadSheetDoc;
    }


    public static void insertIntoCell(int CellX, int CellY, String theValue,XSpreadsheet TT1, String flag){
        XCell xCell = null;

        try {
            xCell = TT1.getCellByPosition(CellX, CellY);
        } catch (com.sun.star.lang.IndexOutOfBoundsException ex) {
            System.err.println("Could not get Cell");
            ex.printStackTrace(System.err);
        }

        if (flag.equals("V")) {
            xCell.setValue((new Float(theValue)).floatValue());
        } else {
            xCell.setFormula(theValue);
        }
    }

}
