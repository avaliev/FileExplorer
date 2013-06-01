/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 *
 * @author Айрат
 */
public class DownloadForm extends FormPanel {

    // параметр запроса - содержит имя файла для скачивания
    TextBox fileNameTb = new TextBox();

    public DownloadForm() {
        add(fileNameTb);
        fileNameTb.setName("file");
        setEncoding(FormPanel.ENCODING_URLENCODED);
        setMethod(FormPanel.METHOD_GET);
        String url = GWT.getHostPageBaseURL() + "FileDownload";
        setAction(url);
        
        setVisible(false);
    }
    
    
    public void downloadFile(String fileName) {
        fileNameTb.setValue(fileName);
        submit();
    }
}
