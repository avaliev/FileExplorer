/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client.model;

import java.util.ArrayList;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.cell.client.ImageResourceCell;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SimpleKeyProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import gwt.client.DependencyResolver;
import gwt.client.Resources;
/**
 *
 * @author Айрат
 */
public class BaseTreeViewModel<M extends BaseTreeModel> implements TreeViewModel {
    
    
    private final Resources resoures=DependencyResolver.getResources();
    
    private CompositeCell<M> nodeCell;
    
    private M selectedNode;
    
    private SingleSelectionModel<M> selectionModel;
    
    private M rootNode;
    
    
    
    public BaseTreeViewModel() {
        createCell();
        
        selectionModel=new SingleSelectionModel<M>(new SimpleKeyProvider<M>());
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                selectedNode=selectionModel.getSelectedObject();
                
            }
        });
    }
    

    @Override
    public <T> NodeInfo<?> getNodeInfo(T value) {
        
        if (value==null) {
           ArrayList<BaseTreeModel> list=new ArrayList<BaseTreeModel>();
           list.add(rootNode);
           return new DefaultNodeInfo(new ListDataProvider<BaseTreeModel>(list),nodeCell,selectionModel,null); 
        }
        
        if (value instanceof FolderNode) {
           FolderNode folder=(FolderNode)value;
           return new DefaultNodeInfo(new ListDataProvider<BaseTreeModel>(folder.getFiles()),nodeCell,selectionModel,null);
        }
        return null;
    }

    @Override
    public boolean isLeaf(Object value) {
        if (value==null) return false;
        return (value instanceof FileNode || ((FolderNode)value).getFiles().isEmpty() );
    }
    
    private CompositeCell<M> createCell() {
        
        ArrayList<HasCell<M, ?>> hasCells = new ArrayList<HasCell<M, ?>>();
     
        hasCells.add(new HasCell<M, ImageResource>() {
            
            @Override
            public Cell<ImageResource> getCell() {
                return new ImageResourceCell();
            }
            @Override
            public FieldUpdater<M, ImageResource> getFieldUpdater() {
                return null; 
            }
            @Override
            public ImageResource getValue(M object) {
                
                if (object instanceof FileNode) {
                    return resoures.fileImg(); 
                }
                 if (object instanceof FolderNode) {
                    return resoures.folderImg();
                }
                 return null;
            }   
        });
            
        TextColumn col=new TextColumn<M>() {

            @Override
            public String getValue(M object) {
               return object.getName();//To change body of generated methods, choose Tools | Templates.
            }
            
        };
        
        hasCells.add(col);
        nodeCell=new CompositeCell<M>(hasCells);
        return nodeCell;
    }

    public M getSelectedNode() {
        return selectedNode;
    }
    
    

    public M getRootNode() {
        return rootNode;
    }

    public void setRootNode(M rootNode) {
        this.rootNode = rootNode;
    }

    public SingleSelectionModel<M> getSelectionModel() {
        return selectionModel;
    }
    
    
}
