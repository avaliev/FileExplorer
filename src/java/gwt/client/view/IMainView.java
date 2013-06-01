/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client.view;

import com.google.gwt.user.client.ui.IsWidget;
import gwt.client.model.BaseTreeModel;
import gwt.client.model.FolderNode;

/**
 *
 * @author Айрат
 */
public interface IMainView extends IsWidget{
    
    
    public void setPresenter(Presenter presenter);
    
    public void setFilesTree(FolderNode root);
    
    public interface Presenter {
        
        public void setFileTree();
        
        public void setView(IMainView view);

        public void delete(BaseTreeModel selectedNode);

        public void downloadFile(BaseTreeModel selectedNode);

        public void rename(BaseTreeModel selectedNode, String value);

        public void makeDir(FolderNode folderNode, String folderName);
    }
    
}
