/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidadesHTV;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JoseMiguel
 */
public class Simulador extends Thread{

    public static void main (String[] args)
    {
        new Simulador(180, PP_7AM, 9, Calendar.PM).start();
    }
    
    Calendar cal;
    private final int milisegundos;
    private final int horaFin;
    public final static String PP_7AM = "7AM";
    private final int AM_PM;
    
    public Simulador(int proporcionAcelaracion, String puntoDePartida, int horaFin, int AM_PM) {
        cal = Calendar.getInstance();
        if ( puntoDePartida  != null)
        {
            if ( puntoDePartida.equals(PP_7AM))
            {
                cal.set(Calendar.AM_PM, Calendar.AM);
                cal.set(Calendar.HOUR, 7);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
            }
        }
        milisegundos = 60000/proporcionAcelaracion;
        this.horaFin = horaFin;
        this.AM_PM = AM_PM;
    }

    
    @Override
    public void run() {
        
        while ( cal.get(Calendar.HOUR) != horaFin || cal.get(Calendar.AM_PM) != AM_PM )
        {
            try {
                sleep(milisegundos);
                cal.add(Calendar.MINUTE, 1);
                Tiempo.setDate(cal.getTime());
            } catch (InterruptedException ex) {
                Logger.getLogger(Simulador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
}
