/* ========================================================================
 * Copyright 2014 Arquidalgos
 *
 * Licensed under the MIT, The MIT License (MIT)
 * Copyright (c) 2014 Arquidalgos

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 * ========================================================================


Source generated by CrudMaker version 1.0.0.201408112050

*/

package co.edu.uniandes.csw.Arquidalgos.sede.persistence;

import co.edu.uniandes.csw.Arquidalgos.cita.logic.dto.CitaDTO;
import co.edu.uniandes.csw.Arquidalgos.cita.persistence.converter.CitaConverter;
import co.edu.uniandes.csw.Arquidalgos.cita.persistence.entity.CitaEntity;
import co.edu.uniandes.csw.Arquidalgos.sede.logic.dto.SedeDTO;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

import co.edu.uniandes.csw.Arquidalgos.sede.persistence.api.ISedePersistence;
import co.edu.uniandes.csw.Arquidalgos.turno.logic.dto.TurnoDTO;
import co.edu.uniandes.csw.Arquidalgos.turno.persistence.converter.TurnoConverter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.LocalBean;
import javax.persistence.Query;
import utilidadesHTV.Tiempo;
import utilidadesHTV.Rangos;

@Default
@Stateless 
@LocalBean
public class SedePersistence extends _SedePersistence  implements ISedePersistence {

    public int aumentarTurno() {
        
        SedeDTO sedeActual = getSede(1L);
        
        sedeActual.setTurno(sedeActual.getTurno()+1);
        
        updateSede(sedeActual);
        
        return getSede(1L).getTurno();
    }

    public int turnoActual() {
        
        return getSede(1L).getTurno();
    }

    @SuppressWarnings("unchecked")
    public List<TurnoDTO> getTurnos() {
	Query q = entityManager.createQuery("select u from TurnoEntity u");
	return TurnoConverter.entity2PersistenceDTOList(q.getResultList());
    }
    
    public List<TurnoDTO> getTurnosSede(Long idSede){
        
        List < TurnoDTO> turnos = getTurnos();
        List < TurnoDTO> resp = new ArrayList<TurnoDTO>();
        
        for (TurnoDTO turno : turnos) {
            
            if ( turno.getSedeturnoId().equals(idSede)){
                
                resp.add(turno);                
            }            
        }
        return resp;
    }
    
    public List <TurnoDTO> getTurnosSedeHoy (Long idSede){
        
        List < TurnoDTO> turnosSede = getTurnosSede(idSede);
        List < TurnoDTO> resp = new ArrayList<TurnoDTO>();
        Calendar calActual = new GregorianCalendar();
        calActual.setTime(Tiempo.getCurrentDate());
        
        for (TurnoDTO turno : turnosSede) {
            
            Calendar calTurno = new GregorianCalendar();
            calTurno.setTime(turno.getFechaTurno());
            if ( calActual.get(Calendar.YEAR)== calTurno.get(Calendar.YEAR) &&
                    calActual.get(Calendar.DAY_OF_YEAR) == calTurno.get(Calendar.DAY_OF_YEAR)){
                
                // Son el mismo dia
                
                resp.add(turno);               
            }
        }
        return resp;
    }
    
    public int asignarSiguienteTurno ( Long idSede){
        
        List <TurnoDTO> turnosHoy = getTurnosSedeHoy(idSede);
        
        TurnoDTO nuevoTurno = new TurnoDTO();
        
        nuevoTurno.setFechaTurno(Tiempo.getCurrentDate());
        nuevoTurno.setHoraInicio(turnosHoy.get(turnosHoy.size()-1).getHoraFinal());
        nuevoTurno.setHoraFinal(new Date(nuevoTurno.getHoraInicio().getTime()
                +(Rangos.DURACION_APROX_TURNO_MIN*60000)));
        nuevoTurno.setSedeturnoId(idSede);
        nuevoTurno.setTurno(turnosHoy.size()+1);
        turnosHoy.add(nuevoTurno);
        return turnosHoy.size();
    }

    public void atenderTurno(Long idSede) {
        
        SedeDTO sede = getSede(idSede);
        int turnoActual = sede.getTurno();
        
        //Correr tiempos por si se demora menos/mas
        
        List <TurnoDTO> turnos = getTurnosSedeHoy(idSede);
        Date temp = Tiempo.getCurrentDate();
        for (int i = turnoActual; i < turnos.size(); i++) {
            
            TurnoDTO actual = turnos.get(i);
            actual.setHoraInicio(temp);
            actual.setHoraFinal(new Date(actual.getHoraInicio().getTime()+Rangos.DURACION_APROX_TURNO_MIN*60000));
            temp = actual.getHoraFinal();
        }
        // pasar todas las citas en espera a la fila, si se cumple
        
        List <CitaDTO> citas = darCitasRango(turnos.get(turnos.size()-1).getHoraFinal());
        
        for (CitaDTO cita : citas) {
            
            //Falta relacionar cita-turno-usuario
            asignarSiguienteTurno(idSede);
            cita.setEspera(false);
        }
        
        sede.setTurno(turnoActual+1);     
                
    }

    public int darUltimoTurnoAsignado(Long sede) {
        
        return getTurnosSedeHoy(sede).size();
    }

    public Date darUltimoInicioDeCita(Long idSede) {
    
        List <TurnoDTO> turnos = getTurnosSedeHoy(idSede);
        
        return turnos.get(turnos.size()-1).getHoraInicio();
    }

    public int darUltimoTurnoAtendido(Long idSede) {
       
        return getSede(idSede).getTurno();
    }

    
    public List<CitaDTO> darCitasRango (Date fecha){
        
        List<CitaDTO> citas = getCitas();
        List<CitaDTO> resp = new ArrayList<CitaDTO>();
        for (CitaDTO cita : citas) {
            
            if ( fecha.before(cita.getHoraIni())&&fecha.after(cita.getHoraFin())&&cita.isEspera()){
                
                
                resp.add(cita);
            }
            
        }
        
        return resp;
    }
    public Date darHoraAproximadaAtencion(CitaDTO citaP) {
    
        CitaDTO cita = getCita(citaP.getId());
        
        if ( cita.isEspera() ){
            
            return cita.getHoraIni();
            
        }
        else{
            
            Long inicioUltimoTurno = darUltimoInicioDeCita(citaP.getSedecitaId()).getTime();
            int turnoActual = darUltimoTurnoAtendido(citaP.getSedecitaId());
            
            return new Date(inicioUltimoTurno+(cita.getTurnoAsignado()-turnoActual)*Rangos.DURACION_APROX_TURNO_MIN*60000);
            
        }   
    }
    
    public CitaDTO getCita(Long id) {
	return CitaConverter.entity2PersistenceDTO(entityManager.find(CitaEntity.class, id));
    }

    public void reservarCita(CitaDTO nuevaCita) {
        
        CitaDTO cita = new CitaDTO();
        cita.setEspera(true);
        cita.setFechaCita(Tiempo.getCurrentDate());
        cita.setHoraIni(nuevaCita.getHoraIni());
        cita.setHoraFin(new Date (cita.getHoraIni().getTime()+Rangos.RANGO_RESERVAR_TURNO_MIN*60000));
        cita.setSedecitaId(nuevaCita.getSedecitaId());
        cita.setTurnoAsignado(-1);
        
        createCita(cita);
    }
    
    
    public CitaDTO createCita(CitaDTO cita) {
		CitaEntity entity=CitaConverter.persistenceDTO2Entity(cita);
		entityManager.persist(entity);
		return CitaConverter.entity2PersistenceDTO(entity);
	}
    
    @SuppressWarnings("unchecked")
	public List<CitaDTO> getCitas() {
		Query q = entityManager.createQuery("select u from CitaEntity u");
		return CitaConverter.entity2PersistenceDTOList(q.getResultList());
	}


}