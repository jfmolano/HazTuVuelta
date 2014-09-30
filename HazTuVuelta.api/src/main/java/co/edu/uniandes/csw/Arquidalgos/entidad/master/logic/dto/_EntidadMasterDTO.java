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

package co.edu.uniandes.csw.Arquidalgos.entidad.master.logic.dto;

import co.edu.uniandes.csw.Arquidalgos.sede.logic.dto.SedeDTO;
import co.edu.uniandes.csw.Arquidalgos.entidad.logic.dto.EntidadDTO;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class _EntidadMasterDTO {

 
    protected EntidadDTO entidadEntity;
    protected Long id;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public EntidadDTO getEntidadEntity() {
        return entidadEntity;
    }

    public void setEntidadEntity(EntidadDTO entidadEntity) {
        this.entidadEntity = entidadEntity;
    }
    
    public List<SedeDTO> createsedeEntidad;
    public List<SedeDTO> updatesedeEntidad;
    public List<SedeDTO> deletesedeEntidad;
    public List<SedeDTO> listsedeEntidad;	
	
	
	
    public List<SedeDTO> getCreatesedeEntidad(){ return createsedeEntidad; };
    public void setCreatesedeEntidad(List<SedeDTO> createsedeEntidad){ this.createsedeEntidad=createsedeEntidad; };
    public List<SedeDTO> getUpdatesedeEntidad(){ return updatesedeEntidad; };
    public void setUpdatesedeEntidad(List<SedeDTO> updatesedeEntidad){ this.updatesedeEntidad=updatesedeEntidad; };
    public List<SedeDTO> getDeletesedeEntidad(){ return deletesedeEntidad; };
    public void setDeletesedeEntidad(List<SedeDTO> deletesedeEntidad){ this.deletesedeEntidad=deletesedeEntidad; };
    public List<SedeDTO> getListsedeEntidad(){ return listsedeEntidad; };
    public void setListsedeEntidad(List<SedeDTO> listsedeEntidad){ this.listsedeEntidad=listsedeEntidad; };	
	
	
}
