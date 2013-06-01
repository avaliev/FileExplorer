/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.dom.client.ClickHandler;
import gwt.client.view.IMainView;

/**
 * Main entry point.
 *
 * @author Айрат
 */
public class FileExplorerEntryPoint implements EntryPoint {

    /**
     * Creates a new instance of FileExplorerEntryPoint
     */
    public FileExplorerEntryPoint() {
        
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
        DependencyResolver resolver=new DependencyResolver();
        IMainView mainView=resolver.getIMainViewImpl();
        IMainView.Presenter presenter=resolver.getMainViewPresenter();
        mainView.setPresenter(presenter);
        presenter.setView(mainView);
        presenter.setFileTree();
        RootPanel.get().add(mainView);
    }
}
