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

package co.edu.uniandes.csw.Arquidalgos.cita.logic.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _CitaDTO {

	

	private Long id;
	

	private Date horaIni;
	

	private Date horaFin;
	

        private Date fechaCita;
        
        
	private String name;
	

	private Long sedecitaId;
        
        private boolean espera;

        private Integer turnoAsignado;
        
        private Integer horaInicInt;

    public Integer getHoraInicInt() {
        return horaInicInt;
    }

    public void setHoraInicInt(Integer horaInicInt) {
        this.horaInicInt = horaInicInt;
    }
        
        

        public Integer getTurnoAsignado() {
            return turnoAsignado;
        }

        public void setTurnoAsignado(Integer turnoAsignado) {
            this.turnoAsignado = turnoAsignado;
        }

        
        
        public boolean isEspera() {
            return espera;
        }

        public void setEspera(boolean espera) {
            this.espera = espera;
        }
        
        
        

        public Date getFechaCita() {
            return fechaCita;
        }

        public void setFechaCita(Date fechaCita) {
            this.fechaCita = fechaCita;
        }



	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}


	public Date getHoraIni() {
		return horaIni;
	}
 
	public void setHoraIni(Date horaini) {
		this.horaIni = horaini;
	}


	public Date getHoraFin() {
		return horaFin;
	}
 
	public void setHoraFin(Date horafin) {
		this.horaFin = horafin;
	}


	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}


	public Long getSedecitaId() {
		return sedecitaId;
	}
 
	public void setSedecitaId(Long sedecitaid) {
		this.sedecitaId = sedecitaid;
	}
	
}