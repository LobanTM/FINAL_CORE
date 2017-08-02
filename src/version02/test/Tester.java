package version02.test;

import version02.Controller.ArrayHolderController;
import version02.Controller.ArrayHolderControllerImpl;
import version02.View.ArrayHolderView;
import version02.View.Episode;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.sun.star.uno.UnoRuntime;

import com.sun.star.awt.Rectangle;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;

import com.sun.star.chart.XDiagram;
import com.sun.star.chart.XChartDocument;

import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNameContainer;

import com.sun.star.document.XEmbeddedObjectSupplier;

import com.sun.star.frame.XComponentLoader;

import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XMultiComponentFactory;

//import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XInterface;
import com.sun.star.uno.XComponentContext;

import com.sun.star.sheet.XCellRangeAddressable;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.sheet.XSpreadsheetDocument;

import com.sun.star.style.XStyleFamiliesSupplier;

import com.sun.star.table.CellRangeAddress;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import com.sun.star.table.XTableChart;
import com.sun.star.table.XTableCharts;
import com.sun.star.table.XTableChartsSupplier;

/**
 * Created by tetya on 23.07.2017.
 */
public class Tester {

    public void startTesting() throws InvocationTargetException, IllegalAccessException {
        List<Episode> episodes = new ArrayList<>();
        ArrayHolderController controller = new ArrayHolderControllerImpl();
        ArrayHolderView view = new ArrayHolderView(controller);

        Episode episodeUp = new Episode("Massive sorted by Up");
        episodeUp = view.sort(episodeUp,1);
        episodes.add(episodeUp);

        Episode episodeUpPlusRandom = new Episode("Massive sorted by Up plus random element");
        episodeUpPlusRandom = view.sort(episodeUpPlusRandom,2);
        episodes.add(episodeUpPlusRandom);

        Episode episodeDown = new Episode("Massive sorted by Down");
        episodeDown = view.sort(episodeDown,3);
        episodes.add(episodeDown);

        Episode episodeRandom = new Episode("Massive Random");
        episodeRandom = view.sort(episodeRandom,4);
        episodes.add(episodeRandom);

        view.openDocument();
        int k = 0;
        for (Episode episode: episodes) {
            view.episodeToCalc(episode, k);
            k++;
        }
        view.closeDocument();
    }
}
