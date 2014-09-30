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

package co.edu.uniandes.csw.Arquidalgos.usuario.master.persistence;
import co.edu.uniandes.csw.Arquidalgos.cita.logic.dto.CitaDTO;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.persistence.entity.UsuariocitasUsEntity;
import co.edu.uniandes.csw.Arquidalgos.cita.persistence.converter.CitaConverter;
import co.edu.uniandes.csw.Arquidalgos.turno.logic.dto.TurnoDTO;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.persistence.entity.UsuarioturnoUsuarioEntity;
import co.edu.uniandes.csw.Arquidalgos.turno.persistence.converter.TurnoConverter;
import co.edu.uniandes.csw.Arquidalgos.usuario.logic.dto.UsuarioDTO;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.logic.dto.UsuarioMasterDTO;
import co.edu.uniandes.csw.Arquidalgos.usuario.master.persistence.api._IUsuarioMasterPersistence;
import co.edu.uniandes.csw.Arquidalgos.usuario.persistence.api.IUsuarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class _UsuarioMasterPersistence implements _IUsuarioMasterPersistence {

  	@PersistenceContext(unitName="HazTuVueltaPU")
 
    protected EntityManager entityManager;
    
    @Inject
    protected IUsuarioPersistence usuarioPersistence;  

    public UsuarioMasterDTO getUsuario(Long usuarioId) {
        UsuarioMasterDTO usuarioMasterDTO = new UsuarioMasterDTO();
        UsuarioDTO usuario = usuarioPersistence.getUsuario(usuarioId);
        usuarioMasterDTO.setUsuarioEntity(usuario);
        usuarioMasterDTO.setListcitasUs(getUsuariocitasUsEntityList(usuarioId));
        usuarioMasterDTO.setListturnoUsuario(getUsuarioturnoUsuarioEntityList(usuarioId));
        return usuarioMasterDTO;
    }

    public UsuariocitasUsEntity createUsuariocitasUsEntity(UsuariocitasUsEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void deleteUsuariocitasUsEntity(Long usuarioId, Long citasUsId) {
        Query q = entityManager.createNamedQuery("UsuariocitasUsEntity.deleteUsuariocitasUsEntity");
        q.setParameter("usuarioId", usuarioId);
        q.setParameter("citasUsId", citasUsId);
        q.executeUpdate();
    }

    public List<CitaDTO> getUsuariocitasUsEntityList(Long usuarioId) {
        ArrayList<CitaDTO> resp = new ArrayList<CitaDTO>();
        Query q = entityManager.createNamedQuery("UsuariocitasUsEntity.getByMasterId");
        q.setParameter("usuarioId",usuarioId);
        List<UsuariocitasUsEntity> qResult =  q.getResultList();
        for (UsuariocitasUsEntity entity : qResult) { 
            if(entity.getCitasUsIdEntity()==null){
                entityManager.refresh(entity);
            }
            resp.add(CitaConverter.entity2PersistenceDTO(entity.getCitasUsIdEntity()));
        }
        return resp;
    }
    public UsuarioturnoUsuarioEntity createUsuarioturnoUsuarioEntity(UsuarioturnoUsuarioEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void deleteUsuarioturnoUsuarioEntity(Long usuarioId, Long turnoUsuarioId) {
        Query q = entityManager.createNamedQuery("UsuarioturnoUsuarioEntity.deleteUsuarioturnoUsuarioEntity");
        q.setParameter("usuarioId", usuarioId);
        q.setParameter("turnoUsuarioId", turnoUsuarioId);
        q.executeUpdate();
    }

    public List<TurnoDTO> getUsuarioturnoUsuarioEntityList(Long usuarioId) {
        ArrayList<TurnoDTO> resp = new ArrayList<TurnoDTO>();
        Query q = entityManager.createNamedQuery("UsuarioturnoUsuarioEntity.getByMasterId");
        q.setParameter("usuarioId",usuarioId);
        List<UsuarioturnoUsuarioEntity> qResult =  q.getResultList();
        for (UsuarioturnoUsuarioEntity entity : qResult) { 
            if(entity.getTurnoUsuarioIdEntity()==null){
                entityManager.refresh(entity);
            }
            resp.add(TurnoConverter.entity2PersistenceDTO(entity.getTurnoUsuarioIdEntity()));
        }
        return resp;
    }

}
