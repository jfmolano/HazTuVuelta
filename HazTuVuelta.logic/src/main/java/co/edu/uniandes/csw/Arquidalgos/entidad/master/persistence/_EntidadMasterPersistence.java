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

package co.edu.uniandes.csw.Arquidalgos.entidad.master.persistence;
import co.edu.uniandes.csw.Arquidalgos.sede.logic.dto.SedeDTO;
import co.edu.uniandes.csw.Arquidalgos.entidad.master.persistence.entity.EntidadsedeEntidadEntity;
import co.edu.uniandes.csw.Arquidalgos.sede.persistence.converter.SedeConverter;
import co.edu.uniandes.csw.Arquidalgos.entidad.logic.dto.EntidadDTO;
import co.edu.uniandes.csw.Arquidalgos.entidad.master.logic.dto.EntidadMasterDTO;
import co.edu.uniandes.csw.Arquidalgos.entidad.master.persistence.api._IEntidadMasterPersistence;
import co.edu.uniandes.csw.Arquidalgos.entidad.persistence.api.IEntidadPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class _EntidadMasterPersistence implements _IEntidadMasterPersistence {

  	@PersistenceContext(unitName="HazTuVueltaPU")
 
    protected EntityManager entityManager;
    
    @Inject
    protected IEntidadPersistence entidadPersistence;  

    public EntidadMasterDTO getEntidad(Long entidadId) {
        EntidadMasterDTO entidadMasterDTO = new EntidadMasterDTO();
        EntidadDTO entidad = entidadPersistence.getEntidad(entidadId);
        entidadMasterDTO.setEntidadEntity(entidad);
        entidadMasterDTO.setListsedeEntidad(getEntidadsedeEntidadEntityList(entidadId));
        return entidadMasterDTO;
    }

    public EntidadsedeEntidadEntity createEntidadsedeEntidadEntity(EntidadsedeEntidadEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    public void deleteEntidadsedeEntidadEntity(Long entidadId, Long sedeEntidadId) {
        Query q = entityManager.createNamedQuery("EntidadsedeEntidadEntity.deleteEntidadsedeEntidadEntity");
        q.setParameter("entidadId", entidadId);
        q.setParameter("sedeEntidadId", sedeEntidadId);
        q.executeUpdate();
    }

    public List<SedeDTO> getEntidadsedeEntidadEntityList(Long entidadId) {
        ArrayList<SedeDTO> resp = new ArrayList<SedeDTO>();
        Query q = entityManager.createNamedQuery("EntidadsedeEntidadEntity.getByMasterId");
        q.setParameter("entidadId",entidadId);
        List<EntidadsedeEntidadEntity> qResult =  q.getResultList();
        for (EntidadsedeEntidadEntity entity : qResult) { 
            if(entity.getSedeEntidadIdEntity()==null){
                entityManager.refresh(entity);
            }
            resp.add(SedeConverter.entity2PersistenceDTO(entity.getSedeEntidadIdEntity()));
        }
        return resp;
    }

}