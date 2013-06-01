/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client.presenter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import gwt.client.MainService;
import gwt.client.MainServiceAsync;
import gwt.client.model.BaseTreeModel;
import gwt.client.model.FileNode;
import gwt.client.model.FolderNode;
import gwt.client.view.IMainView;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Айрат
 */
public class MainPresenter implements IMainView.Presenter {
    
    private IMainView _view;
    
    private MainServiceAsync service;

    @Override
    public void makeDir(FolderNode folderNode, String folderName) {
        service.mkdir(folderNode, folderName, new MyCallBack()); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Acинхронный обратный вызов от сервера. 
     * При успешном завершении операции обновляем все дерево.
     */
    private class MyCallBack implements AsyncCallback<BaseTreeModel> {

        @Override
        public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage()); 
        }
        
        @Override
        public void onSuccess(BaseTreeModel result) {
             _view.setFilesTree((FolderNode)result); 
        }
        
    }
    
    @Override
    public void setView(IMainView view) {
        _view=view; 
        service=GWT.create(MainService.class);
    }
    
    
    public void setFileTree() {
        service.getFileTree(new MyCallBack());
    }

    @Override
    public void delete(BaseTreeModel selectedNode) {
        service.delete(selectedNode, new MyCallBack());
    }

    @Override
    public void downloadFile(BaseTreeModel selectedNode) {
        if (selectedNode instanceof FileNode) {
            String url=GWT.getHostPageBaseURL()+ "FileDownload?file=" + selectedNode.getPath();
            url=URL.encode(url);
            RequestBuilder builder=new RequestBuilder(RequestBuilder.GET, url);
            builder.setRequestData("charset=win-1251");
            try {
                Request request=builder.sendRequest(null,new RequestCallback() {

                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    public void onError(Request request, Throwable exception) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
            } catch (RequestException ex) {
                Logger.getLogger(MainPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
    }

    @Override
    public void rename(BaseTreeModel selectedNode, String value) {
        service.rename(selectedNode,value,new MyCallBack());
    }
    
   
}
