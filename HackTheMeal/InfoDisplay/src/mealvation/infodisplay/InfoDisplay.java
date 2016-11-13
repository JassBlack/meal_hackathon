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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import org.apache.log4j.varia.NullAppender;

/**
 * Main class to draw graphical representation of html file on device screen.
 * Parameters for dispalying when running are:
 * <ul>
 * <li>C continious state, no diner, no tray
 * <li>D diner has recognized 
 * <li>E element manipulation
 * <li>T tray recognized
 * <li>I intake recognized
 * </ul>
 *
 * @author Pertti Harju
 */

public class InfoDisplay {
    public static String mealname="null";
    public static int dinerid=0;
    public static String dishid="0";
    public static int weight =0;
    public static int oldweight = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());
        String cloudaddr = "http://mealvation.fi/hackthemeal/display/";
        String workdir = "/home/beta/infodisp/";
        String pagedir = "/home/beta/pages/";
        String dinerdb = workdir + "infodiners.db";
        String params, ini, tail; 
        String age = "old";
        String canintake = "Please take your portion of ";
        
        SetDisplay sd = new SetDisplay(dinerdb, pagedir);
        ReadRadio rr = new ReadRadio(workdir + "tray");
        GetDinerDb gdb = new GetDinerDb(cloudaddr + "getdinerdb.php", dinerdb);
        if(gdb.downLoadDb()){
            age = "new";
        }
        sd.showPage(cloudaddr + "dispdish.php?dishid=1");
        System.out.println("Infodisplay app started with " + age + " diner db");
        while(true){
            params = rr.getData();
            try {
                ini = params.substring(0, 1);
                tail = params.substring(2);            
                switch (ini){
                    case "E":
                        String[] parts = tail.split(" ");
                        String element = parts[0];
                        String value = parts[1];
                        if (element.startsWith("orig")) value = "Origin: " + value;
                        sd.setElement("/home/beta/pages/laxbox.html", element, value);
                    break;
                    case "C":
                        dishid = tail;
                        mealname = sd.getMeal(cloudaddr + "dispdish.php?dishid=" + dishid);
                        sd.showPage(cloudaddr + "dispdish.php?dishid=" + dishid);
                    break;
                    case "D":
                        dinerid = Integer.parseInt(tail);
                        sd.showWelcomepage(dinerid);
                    break;
                    case "T":
                        sd.setReady(canintake);
                    break;
                    case "I":
                        oldweight=weight;
                        weight = Integer.parseInt(tail);
                        if(weight<0)
                        {
                            if(dinerid>0 && dishid!="0" && oldweight>0)
                            {
                                String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
                            sd.showPage(cloudaddr +"insertportion.php?dinerid="+ dinerid +"&dishid="+dishid+"&edate="+ date +"&weight="+oldweight);
                            }
                            //left insertportion.php?dinerid=1&dishid=5&edate=2016-11-09&weight=335
                        }
                        else
                        {
                          
                          sd.showIntake(tail, dinerid);  
                        }
                        
                    break;
                    default:
                        System.out.println("Check given first parameter\n");
                }
            }
            catch (Exception e) {
                System.out.println("Parameter error: " + e);
            }
        }   
    } 
}
