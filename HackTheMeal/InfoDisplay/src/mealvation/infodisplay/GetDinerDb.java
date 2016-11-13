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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Class to download file from URL and save it localy.
 *
 * @author Pertti Harju
 */
public class GetDinerDb {

    private String dbaddr;
    private String savefilename;
    
/**
* Creates a new loader object
* 
* @param addr URL of the file where to download. 
* @param sfile path name of the file to save. 
*/

    public GetDinerDb(String addr, String sfile) {
        this.dbaddr = addr;
        this.savefilename = sfile;
    }

/**
* Downloads file from URL.
* 
* @return true if succeeded 
*/
    public boolean downLoadDb() {
        boolean ok = false;
        byte[] buffer = new byte[4096];
        int bytesRead;
        URL addrurl;
        InputStream is;
        FileOutputStream fos;
        try {
            addrurl = new URL(dbaddr);
            is = addrurl.openStream();
            fos = new FileOutputStream(savefilename);
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.close();
            is.close();
            ok = true;
        } catch (IOException ex) {
            System.out.println("Diner DB file save error " + ex);
        }
        return ok;
    }
}
