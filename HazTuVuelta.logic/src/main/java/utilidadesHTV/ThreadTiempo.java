/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidadesHTV;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.Singleton;

/**
 *
 * @author Jonathan
 */

public class ThreadTiempo extends Thread {

    public static int hora;

    public static int minutos;

  
    
    public ThreadTiempo(){
        
    }
    
   
    public static void main(String args[]) {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String line = br.readLine();

            while (line != null) {
                
                String [] s = line.split(" ");
                
                hora = Integer.parseInt(s[0]);
                
                minutos = Integer.parseInt(s[1]);
                
                System.out.println("La hora es "+hora+":"+minutos);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Date darTiempo() {

        Date d = new Date();
        Calendar c = new GregorianCalendar();
        c.setTime(d);

        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hora, minutos);

        return c.getTime();
    }

}
