/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.client.model.BaseTreeModel;
import gwt.client.model.FolderNode;

/**
 *
 * @author Айрат
 */
public interface MainServiceAsync {
    
    public void getFileTree(AsyncCallback<BaseTreeModel> callback);
    
    public void delete(BaseTreeModel node,AsyncCallback<BaseTreeModel> callback);
   
    public void rename(BaseTreeModel node,String newName,AsyncCallback<BaseTreeModel> callback);
    
    public void mkdir(FolderNode node,String folderName,AsyncCallback<BaseTreeModel> callback);
   
}
