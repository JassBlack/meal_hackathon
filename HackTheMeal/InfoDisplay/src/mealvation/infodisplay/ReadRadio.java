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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Class to read data from named pipe.
 *
 * @author Pertti Harju
 */
public class ReadRadio {
    private final String f_pipe;
    
/**
* Creates a new reader object
* 
* @param pipe path name of pipe.
*/
    public ReadRadio(String pipe) {
        this.f_pipe = pipe;
    }
    
/**
* Reads one line from pipe.
* 
* @return line of data in pipe
* @see String
*/
    public String getData(){   
        String dataline = "";
        try {
            Thread.sleep(10);
            RandomAccessFile raf = new RandomAccessFile(f_pipe, "r");
            String line=null;
            int idleTime = 0;
            int maxIdleTime = 600; 
            for( ;idleTime<maxIdleTime; ){
                try {
                    line=raf.readLine();
                } catch (IOException ex) {
                    System.out.println("Pipe read err1 " + ex);
                }                  
                if( line!=null ) {
                    dataline = line;
                    idleTime=0;
                    break;
                }else{
                    Thread.sleep(10);
                    idleTime+=60;
                }
            }          
        } 
        catch (FileNotFoundException | InterruptedException ex ) {
            System.out.println("Pipe read err2 " + ex);
        }
        return dataline;
    }  
}
