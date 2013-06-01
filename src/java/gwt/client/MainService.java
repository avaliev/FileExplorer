/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import gwt.client.model.BaseTreeModel;
import gwt.client.model.FolderNode;

/**
 *
 * @author Айрат
 */
@RemoteServiceRelativePath("mainservice")
public interface MainService extends RemoteService {
    
    /**
     * 
     * @return возвращает дерево в виде ссылки на главный элемент
     */
    public BaseTreeModel getFileTree();
    
    /**
     * 
     * @param node файл или папка которую нужно удалить
     * @return возвращает результат в виде нового дерева
     */
    public BaseTreeModel delete(BaseTreeModel node);
    
    
    public BaseTreeModel rename(BaseTreeModel node,String newName);
    
    
    public BaseTreeModel mkdir(FolderNode node,String folderName);
    
}
