/*
 * Copyright 2016, Mealvation Oy Hack the Meal project
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package mealvation.infodisplay;

import java.io.IOException;
import javax.swing.JPanel;
import org.fit.cssbox.css.CSSNorm;
import org.fit.cssbox.css.DOMAnalyzer;
import org.fit.cssbox.io.DOMSource;
import org.fit.cssbox.io.DefaultDOMSource;
import org.fit.cssbox.io.DefaultDocumentSource;
import org.fit.cssbox.io.DocumentSource;
import org.fit.cssbox.layout.BrowserCanvas;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Class to get graphical representation of html file.
 *
 * @author Pertti Harju
 */
public class GetInfoPage {
    
/**
* Builds graphics.
* 
* @param purl location of the html document to display file:// or html:/
* @return graphical component to use with Swing
* @see JPanel
*/

    public JPanel getPage(String purl){  
        JPanel page = new JPanel();
        try{
            DocumentSource docSource = new DefaultDocumentSource(purl);
            DOMSource parser = new DefaultDOMSource(docSource);
            Document doc = parser.parse();
            DOMAnalyzer da = new DOMAnalyzer(doc, docSource.getURL());
            
            da.attributesToStyles();
            da.addStyleSheet(null, CSSNorm.stdStyleSheet(), DOMAnalyzer.Origin.AGENT);
            da.addStyleSheet(null, CSSNorm.userStyleSheet(), DOMAnalyzer.Origin.AGENT); 
            da.addStyleSheet(null, CSSNorm.formsStyleSheet(), DOMAnalyzer.Origin.AGENT);
            da.getStyleSheets();
                    
            page = new BrowserCanvas(da.getRoot(), da, new java.awt.Dimension(800, 480), docSource.getURL());
            docSource.close();
        } 
        catch (IOException | SAXException er) {
            System.out.println("Errorv: "+er.getMessage());
        }
        return page;
    }
}
