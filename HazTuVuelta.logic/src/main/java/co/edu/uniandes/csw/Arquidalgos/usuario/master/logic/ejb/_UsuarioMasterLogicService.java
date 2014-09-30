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

package co.edu.uniandes.csw.Arquidalgos.usuario.master.logic.ejb;

import co.edu.uniandes.csw.Arquidalgos.cita.logic.dto.CitaDTO;
import co.edu.uniandes.csw.Arquidalgos.cita.persistence.api.ICitaPersistence;
import co.edu.uniandes.csw.Arquidalgos.turno.logic.dto.TurnoDTO;
import co.edu.uniandes.csw.Arquidalgos.turno.persistence.api.ITurnoPersistence;
import co.edu.uniandes.csw.Arquidalgos.usuario.logic.dto.UsuarioDTO;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.logic.api._IUsuarioMasterLogicService;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.logic.dto.UsuarioMasterDTO;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.persistence.api.IUsuarioMasterPersistence;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.persistence.entity.UsuariocitasUsEntity;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.persistence.entity.UsuarioturnoUsuarioEntity;
import co.edu.uniandes.csw.Arquidalgos.usuario.persistence.api.IUsuarioPersistence;
import javax.inject.Inject;

public abstract class _UsuarioMasterLogicService implements _IUsuarioMasterLogicService {

    @Inject
    protected IUsuarioPersistence usuarioPersistance;
    @Inject
    protected IUsuarioMasterPersistence usuarioMasterPersistance;
    @Inject
    protected ITurnoPersistence turnoPersistance;
    @Inject
    protected ICitaPersistence citaPersistance;

    public UsuarioMasterDTO createMasterUsuario(UsuarioMasterDTO usuario) {
        UsuarioDTO persistedUsuarioDTO = usuarioPersistance.createUsuario(usuario.getUsuarioEntity());
        if (usuario.getCreatecitasUs() != null) {
            for (CitaDTO citaDTO : usuario.getCreatecitasUs()) {
                CitaDTO createdCitaDTO = citaPersistance.createCita(citaDTO);
                UsuariocitasUsEntity usuarioCitaEntity = new UsuariocitasUsEntity(persistedUsuarioDTO.getId(), createdCitaDTO.getId());
                usuarioMasterPersistance.createUsuariocitasUsEntity(usuarioCitaEntity);
            }
        }
        if (usuario.getCreateturnoUsuario() != null) {
            for (TurnoDTO turnoDTO : usuario.getCreateturnoUsuario()) {
                TurnoDTO createdTurnoDTO = turnoPersistance.createTurno(turnoDTO);
                UsuarioturnoUsuarioEntity usuarioTurnoEntity = new UsuarioturnoUsuarioEntity(persistedUsuarioDTO.getId(), createdTurnoDTO.getId());
                usuarioMasterPersistance.createUsuarioturnoUsuarioEntity(usuarioTurnoEntity);
            }
        }
        // update cita
        if (usuario.getUpdatecitasUs() != null) {
            for (CitaDTO citaDTO : usuario.getUpdatecitasUs()) {
                citaPersistance.updateCita(citaDTO);
                UsuariocitasUsEntity usuarioCitaEntity = new UsuariocitasUsEntity(persistedUsuarioDTO.getId(), citaDTO.getId());
                usuarioMasterPersistance.createUsuariocitasUsEntity(usuarioCitaEntity);
            }
        }
        // update turno
        if (usuario.getUpdateturnoUsuario() != null) {
            for (TurnoDTO turnoDTO : usuario.getUpdateturnoUsuario()) {
                turnoPersistance.updateTurno(turnoDTO);
                UsuarioturnoUsuarioEntity usuarioTurnoEntity = new UsuarioturnoUsuarioEntity(persistedUsuarioDTO.getId(), turnoDTO.getId());
                usuarioMasterPersistance.createUsuarioturnoUsuarioEntity(usuarioTurnoEntity);
            }
        }
        return usuario;
    }

    public UsuarioMasterDTO getMasterUsuario(Long id) {
        return usuarioMasterPersistance.getUsuario(id);
    }

    public void deleteMasterUsuario(Long id) {
        usuarioPersistance.deleteUsuario(id);
    }

    public void updateMasterUsuario(UsuarioMasterDTO usuario) {
        usuarioPersistance.updateUsuario(usuario.getUsuarioEntity());

        //---- FOR RELATIONSHIP
        // persist new cita
        if (usuario.getCreatecitasUs() != null) {
            for (CitaDTO citaDTO : usuario.getCreatecitasUs()) {
                CitaDTO createdCitaDTO = citaPersistance.createCita(citaDTO);
                UsuariocitasUsEntity usuarioCitaEntity = new UsuariocitasUsEntity(usuario.getUsuarioEntity().getId(), createdCitaDTO.getId());
                usuarioMasterPersistance.createUsuariocitasUsEntity(usuarioCitaEntity);
            }
        }
        // update cita
        if (usuario.getUpdatecitasUs() != null) {
            for (CitaDTO citaDTO : usuario.getUpdatecitasUs()) {
                citaPersistance.updateCita(citaDTO);
            }
        }
        // delete cita
        if (usuario.getDeletecitasUs() != null) {
            for (CitaDTO citaDTO : usuario.getDeletecitasUs()) {
                usuarioMasterPersistance.deleteUsuariocitasUsEntity(usuario.getUsuarioEntity().getId(), citaDTO.getId());
                citaPersistance.deleteCita(citaDTO.getId());
            }
        }
        // persist new turno
        if (usuario.getCreateturnoUsuario() != null) {
            for (TurnoDTO turnoDTO : usuario.getCreateturnoUsuario()) {
                TurnoDTO createdTurnoDTO = turnoPersistance.createTurno(turnoDTO);
                UsuarioturnoUsuarioEntity usuarioTurnoEntity = new UsuarioturnoUsuarioEntity(usuario.getUsuarioEntity().getId(), createdTurnoDTO.getId());
                usuarioMasterPersistance.createUsuarioturnoUsuarioEntity(usuarioTurnoEntity);
            }
        }
        // update turno
        if (usuario.getUpdateturnoUsuario() != null) {
            for (TurnoDTO turnoDTO : usuario.getUpdateturnoUsuario()) {
                turnoPersistance.updateTurno(turnoDTO);
            }
        }
        // delete turno
        if (usuario.getDeleteturnoUsuario() != null) {
            for (TurnoDTO turnoDTO : usuario.getDeleteturnoUsuario()) {
                usuarioMasterPersistance.deleteUsuarioturnoUsuarioEntity(usuario.getUsuarioEntity().getId(), turnoDTO.getId());
                turnoPersistance.deleteTurno(turnoDTO.getId());
            }
        }
    }
}
