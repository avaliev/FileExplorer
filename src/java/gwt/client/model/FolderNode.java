/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client.model;

import java.util.ArrayList;

/**
 *
 * @author Айрат
 */
public class FolderNode extends BaseTreeModel {
    
    private ArrayList<BaseTreeModel> files;

    public FolderNode() {
        files=new ArrayList<BaseTreeModel>();
    }
    
    
    

    public ArrayList<BaseTreeModel> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<BaseTreeModel> files) {
        this.files = files;
    }
    
    
    public void addChild(BaseTreeModel elem) {
        files.add(elem);
    }
     
     
     
}
