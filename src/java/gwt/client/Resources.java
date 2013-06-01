/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 *
 * @author Айрат
 */
public interface Resources extends ClientBundle {
    
    @Source("folder.png")
    ImageResource folderImg();
    
    @Source("document.png")
    ImageResource fileImg();
      
}
