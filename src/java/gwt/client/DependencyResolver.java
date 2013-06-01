/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client;
import com.google.gwt.core.client.GWT;
import gwt.client.presenter.MainPresenter;
import gwt.client.view.*;
/**
 *
 * @author Айрат
 */
public class DependencyResolver {
    
    public static final Resources resources = GWT.create(Resources.class);
    public static final IMainView.Presenter PRESENTER=new MainPresenter();
    
    public IMainView getIMainViewImpl(){
        return new MainView();
    }
    
    
    public IMainView.Presenter getMainViewPresenter() {
        return PRESENTER;
    } 
    
    
    public static Resources  getResources() {
       return resources;
    }
}
