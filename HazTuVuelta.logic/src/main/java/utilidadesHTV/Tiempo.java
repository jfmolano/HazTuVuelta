/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidadesHTV;

import java.util.Date;
import javax.ejb.Singleton;

/**
 *
 * @author JoseMiguel
 */
@Singleton
public class Tiempo {
    private static Date date;
    public static synchronized void setDate (Date date)
    {
        System.out.println(date.toString());
        Tiempo.date = date; 
    }
    public static Date getCurrentDate ()
    {
        if ( Tiempo.date == null){
            return new Date();
        }
        else return Tiempo.date; 
    }
}
