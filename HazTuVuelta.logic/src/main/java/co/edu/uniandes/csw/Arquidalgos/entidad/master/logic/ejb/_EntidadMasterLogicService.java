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

package co.edu.uniandes.csw.Arquidalgos.entidad.master.logic.ejb;

import co.edu.uniandes.csw.Arquidalgos.sede.logic.dto.SedeDTO;
import co.edu.uniandes.csw.Arquidalgos.sede.persistence.api.ISedePersistence;
import co.edu.uniandes.csw.Arquidalgos.entidad.logic.dto.EntidadDTO;
import co.edu.uniandes.csw.Arquidalgos.entidad.master.logic.api._IEntidadMasterLogicService;
import co.edu.uniandes.csw.Arquidalgos.entidad.master.logic.dto.EntidadMasterDTO;
import co.edu.uniandes.csw.Arquidalgos.entidad.master.persistence.api.IEntidadMasterPersistence;
import co.edu.uniandes.csw.Arquidalgos.entidad.master.persistence.entity.EntidadsedeEntidadEntity;
import co.edu.uniandes.csw.Arquidalgos.entidad.persistence.api.IEntidadPersistence;
import javax.inject.Inject;

public abstract class _EntidadMasterLogicService implements _IEntidadMasterLogicService {

    @Inject
    protected IEntidadPersistence entidadPersistance;
    @Inject
    protected IEntidadMasterPersistence entidadMasterPersistance;
    @Inject
    protected ISedePersistence sedePersistance;

    public EntidadMasterDTO createMasterEntidad(EntidadMasterDTO entidad) {
        EntidadDTO persistedEntidadDTO = entidadPersistance.createEntidad(entidad.getEntidadEntity());
        if (entidad.getCreatesedeEntidad() != null) {
            for (SedeDTO sedeDTO : entidad.getCreatesedeEntidad()) {
                SedeDTO createdSedeDTO = sedePersistance.createSede(sedeDTO);
                EntidadsedeEntidadEntity entidadSedeEntity = new EntidadsedeEntidadEntity(persistedEntidadDTO.getId(), createdSedeDTO.getId());
                entidadMasterPersistance.createEntidadsedeEntidadEntity(entidadSedeEntity);
            }
        }
        // update sede
        if (entidad.getUpdatesedeEntidad() != null) {
            for (SedeDTO sedeDTO : entidad.getUpdatesedeEntidad()) {
                sedePersistance.updateSede(sedeDTO);
                EntidadsedeEntidadEntity entidadSedeEntity = new EntidadsedeEntidadEntity(persistedEntidadDTO.getId(), sedeDTO.getId());
                entidadMasterPersistance.createEntidadsedeEntidadEntity(entidadSedeEntity);
            }
        }
        return entidad;
    }

    public EntidadMasterDTO getMasterEntidad(Long id) {
        return entidadMasterPersistance.getEntidad(id);
    }

    public void deleteMasterEntidad(Long id) {
        entidadPersistance.deleteEntidad(id);
    }

    public void updateMasterEntidad(EntidadMasterDTO entidad) {
        entidadPersistance.updateEntidad(entidad.getEntidadEntity());

        //---- FOR RELATIONSHIP
        // persist new sede
        if (entidad.getCreatesedeEntidad() != null) {
            for (SedeDTO sedeDTO : entidad.getCreatesedeEntidad()) {
                SedeDTO createdSedeDTO = sedePersistance.createSede(sedeDTO);
                EntidadsedeEntidadEntity entidadSedeEntity = new EntidadsedeEntidadEntity(entidad.getEntidadEntity().getId(), createdSedeDTO.getId());
                entidadMasterPersistance.createEntidadsedeEntidadEntity(entidadSedeEntity);
            }
        }
        // update sede
        if (entidad.getUpdatesedeEntidad() != null) {
            for (SedeDTO sedeDTO : entidad.getUpdatesedeEntidad()) {
                sedePersistance.updateSede(sedeDTO);
            }
        }
        // delete sede
        if (entidad.getDeletesedeEntidad() != null) {
            for (SedeDTO sedeDTO : entidad.getDeletesedeEntidad()) {
                entidadMasterPersistance.deleteEntidadsedeEntidadEntity(entidad.getEntidadEntity().getId(), sedeDTO.getId());
                sedePersistance.deleteSede(sedeDTO.getId());
            }
        }
    }
}
