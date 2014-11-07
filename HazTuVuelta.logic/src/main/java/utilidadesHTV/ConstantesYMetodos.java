/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidadesHTV;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Jonathan
 */
public class ConstantesYMetodos {
 
    public static final int DURACION_APROX_TURNO_MIN = 5;
    
    public static final int RANGO_RESERVAR_TURNO_MIN = 60;
    
    public static final int RANGO_RESERVAR_TURNO_MILISEGUNDOS = RANGO_RESERVAR_TURNO_MIN*60000;
    
    public static final int DURACION_APROX_TURNO_MILISEGUNDOS = DURACION_APROX_TURNO_MIN*60000;
    
    public static boolean citasMismoDia(Date d1, Date d2){
        
        Calendar c1 = new GregorianCalendar();
        c1.setTime(d1);
        Calendar c2 = new GregorianCalendar();
        c2.setTime(d2);
        
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
        
    }
}
