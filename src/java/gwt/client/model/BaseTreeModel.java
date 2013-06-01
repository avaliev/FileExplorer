/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Айрат
 */
public class BaseTreeModel implements IsSerializable{
    
    private String name;
    
    /** 
     * Уникальный для каждого файла или папки
     */
    private String path;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.path != null ? this.path.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseTreeModel other = (BaseTreeModel) obj;
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        return true;
    }
   
    
}
