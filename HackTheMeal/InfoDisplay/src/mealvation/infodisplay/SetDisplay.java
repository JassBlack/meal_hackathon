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

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.management.StringValueExp;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Class to draw graphics on device screen.
 *
 *
 * @author Pertti Harju
 */
public class SetDisplay {
    
    private final GraphicsDevice device;
    private final String dinerdb;
    private final String pagedir;
    private final SimpleDateFormat ft;
    private Date date;
    private String dinername;
    //public String URL = "http://mealvation.fi/hackthemeal/display/dispdish.php?dishid=1";
    /**
    * Creates an object to display html documents.
    * 
    * @param ddb path name of diner SqLite db file.
    * @param pd directory of the local html files  
    */

    public SetDisplay(String ddb, String pd) {
        this.ft = new SimpleDateFormat ("E MM.dd HH:mm");
        this.device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        this.dinerdb = ddb;
        this.pagedir = pd;
    }
    
     /**
     * Displays the html document on the screen in JFrame. 
     * The page argument must specify the html file address and protocol
     * leading (file:/ or http:) in String format
     * <p> 
     * When this method attempts to draw the image on
     * the screen, the data will be loaded. The graphics primitives 
     * that draw the image will incrementally paint on the screen. 
     *
     * @param  page the location of the html file
     * @see         String
     */
    
    public void showPage(String page){
        System.out.println(InfoDisplay.mealname);
        GetInfoPage gip = new GetInfoPage();
        JFrame cframe = new JFrame();
        cframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cframe.setUndecorated(true);
        cframe.add(gip.getPage(page));
        cframe.pack();
        cframe.setVisible(true);
        device.setFullScreenWindow(cframe);
    }
    
    /**
     * Sets value of element inside page documnet and writes new document to 
     * file live.html and displays it. 
     * <p> 
     * @param  page the location of the html file
     * @param  element name of the element in document
     * @param  val for element
     * @see         String
     */    
    public void setElement(String page, String element, String val) {
        //print("Fetching %s...", page);
        Document doc = null;
        try {
            File input = new File(page);
            doc = Jsoup.parse(input, "UTF-8", "");      
            doc.getElementById(element).text(val);
            File livefile = new File(pagedir + "live.html");
            if (!livefile.exists()) {
                livefile.createNewFile();
            }
            FileWriter fw = new FileWriter(livefile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(doc.html());
            bw.close();
        } catch (IOException ex) {
           System.out.println("prerr" + ex );
        }
        showPage("file://" + pagedir + "live.html");
    }

    /**
     * Sets new value for take element in wdlive.html file and displays altered file. 
     * <p> 
     * @param  take advise to take paortion
     * @see         String
     */  
    
    public void setReady(String take) {
        Document doc = null;
        String meal = InfoDisplay.mealname;
        try {
            File input = new File(pagedir + "wdlive.html");
            doc = Jsoup.parse(input, "UTF-8", "");      
            doc.getElementById("takes").text("Now you can take the food");
            File livefile = new File(pagedir + "wdlive.html");
            if (!livefile.exists()) {
                livefile.createNewFile();
            }
            FileWriter fw = new FileWriter(livefile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(doc.html());
            bw.close();
        } catch (IOException ex) {
           System.out.println("prerr" + ex );
        }
        showPage("file://" + pagedir +"wdlive.html");
    }
    
    /**
     * Populates wdlive.html with values and displays it to inform diner when recognized. 
     *
     * Values to display are queried from dinerdb,
     * diner name is saved for private use in class. 
     * 
     * <p> 
     * @param  dinerid  id of diner in infodisplay.db 
     */ 

    public void showWelcomepage (int dinerid){
        Connection c = null;
        Statement stmt = null;
        Document doc = null;
        String  fname = "";
        String  lname = "";
        int energyneed = 0;
        int lac = 0, glu = 0, mil = 0, vl = 0, veg = 0;
        int status = 0;
        String allerg = ""; 
        //String diet ="My diets: ";
        String diet ="";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dinerdb);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM diner WHERE id = " + dinerid + ";" );
            while (rs.next()) {
                fname = rs.getString("fname");
                lname = rs.getString("lname");
                energyneed  = rs.getInt("energyneed");
                lac = rs.getInt("L");
                glu = rs.getInt("G");
                mil = rs.getInt("M");
                vl = rs.getInt("VL");
                veg = rs.getInt("V");
                status = rs.getInt("status");
                allerg = rs.getString("allergy");
            }
            if(lac == 1) diet = diet +"L ";
            if(glu == 1) diet = diet +"G ";
            if(mil == 1) diet = diet +"M ";
            if(vl == 1) diet = diet +"VL ";
            if(veg == 1) diet = diet +"V";
            rs.close();
            stmt.close();
            c.close();
            dinername = fname;
            date = new Date();
            File input = new File(pagedir + "welcome.html");
            doc = Jsoup.parse(input, "UTF-8", "");
            //doc.getElementById("myheader").text("Restaurant SMART " + ft.format(date));
            doc.getElementById("diner").text("Welcome, " + fname);
            doc.getElementById("diet").text(diet);
            //doc.getElementById("energy").text("My daily energyneed is " + Integer.toString(energyneed) + " kcal");
            doc.getElementById("energy").text("Your diet is (" + String.valueOf((int)(energyneed*0.4)) + " kcal/dinner) + ");
            doc.getElementById("salads").text(" ~ "+String.valueOf((int)(energyneed* 0.4*0.1))+" kcal");
            doc.getElementById("fixings").text(" ~ "+String.valueOf((int)(energyneed* 0.4*0.4))+" kcal");
            doc.getElementById("main-dish").text(" ~ "+String.valueOf((int)(energyneed* 0.4*0.5))+" kcal");
            File livefile = new File(pagedir + "wdlive.html");
            if (!livefile.exists()) {
                livefile.createNewFile();
            }
            FileWriter fw = new FileWriter(livefile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(doc.html());
            bw.close();
        } catch ( IOException | ClassNotFoundException | SQLException e ) {
          System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        showPage("file://" + pagedir + "wdlive.html");
    }
    
    /**
     * Populates intake.html with new values, save them to liveintake.html
     * and displays it to inform diner from intake amounts. 
     * <p> 
     * @param  intake  amount of intake 
     * 
     * @see     String
     */ 
    
    public void showIntake(String intake, int dinerid){
        Connection c = null;
        Statement stmt = null;
        Document doc = null;
        double energyneed = 0;
        double dinnerenergyneed=0;
        double mainenergyneed =0;
        String kcal = "";
        String mainweight = "";
        //String diet ="My diets: ";
        String diet ="";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dinerdb);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM diner WHERE id = " + dinerid + ";" );
            while (rs.next()) {
                energyneed  = rs.getInt("energyneed");
            }
            rs.close();
            stmt.close();
            c.close();
            dinnerenergyneed = 0.4* energyneed;
            mainenergyneed = Math.round(0.5*dinnerenergyneed);
            doc = null;
        //Document doc = null;
        kcal = String.valueOf(Integer.parseInt(intake) * getEnergy()/100) ;
        mainweight = String.valueOf(Math.round(mainenergyneed / getEnergy() *100))+" g";
        //try{
            File input = new File(pagedir + "intake.html");
            doc = Jsoup.parse(input, "UTF-8", "");
            //doc.getElementById("myheader").text("Restaurant SMART " + ft.format(date));
            //doc.getElementById("diner").text("Your intake " + dinername);
            doc.getElementById("meal").text(InfoDisplay.mealname);
            doc.getElementById("weight").text(intake + " of "+mainweight);
            String valueof=String.valueOf((int)mainenergyneed);
            doc.getElementById("energy").text( kcal+" of "+ valueof+ " kcal");
            doc.getElementById("total").text(kcal+" of "+(int)dinnerenergyneed+" kcal");
            File livefile = new File(pagedir + "liveintake.html");
            if (!livefile.exists()) {
                livefile.createNewFile();
            }
            FileWriter fw = new FileWriter(livefile.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(doc.html());
            bw.close();
        }
        catch ( IOException | ClassNotFoundException | SQLException e ) {
          System.out.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        showPage("file://"+ pagedir + "liveintake.html");
    }
    
    /**
     * Gets meal name inside file live.html. 
     * <p>
     * @return meal name
     * @see         String
     */
    
    public String getMeal(String path){
        Document doc = null;
        String meal = "";
        try {
            //File input = new File(pagedir + "live.html");
            //doc = Jsoup.parse(input, "UTF-8", "");
            doc = Jsoup.connect(path).get();
            Element link = doc.getElementById("meal");
            meal = link.text();
        } catch (IOException ex) {
           System.out.println("getmealerr" + ex );
        }
        return meal;
    }
    
    /**
    * Gets energy as kcal/100g of the meal inside file live.html. 
    * <p> 
    * @return energy
    * @see         int
    */
    
    public int getEnergy(){
        Document doc = null;
        int energy = 0;
        try {
            File input = new File(pagedir + "live.html");
            doc = Jsoup.parse(input, "UTF-8", "");
            Element link = doc.getElementById("energy");
            energy = Integer.parseInt(link.text());
        } catch (IOException ex) {
           System.out.println("getenergyerr" + ex );
        }
        return energy;
    }
}

    

