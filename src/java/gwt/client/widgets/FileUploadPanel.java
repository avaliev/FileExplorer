/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import gwt.client.DependencyResolver;

/**
 *
 * @author Айрат
 */
public class FileUploadPanel extends FormPanel {

    TextBox dirNameTb = new TextBox();
    Label label = new Label();

    public FileUploadPanel() {

        VerticalPanel panel = new VerticalPanel();

        panel.setSize("100%", "100px");
        panel.add(label);
        panel.add(dirNameTb);
        dirNameTb.setName("folder");
        dirNameTb.setVisible(false);

        final FileUpload fileUpload = new FileUpload();
        fileUpload.setName("fileUpload");
        panel.add(fileUpload);

        Button submit = new Button("Отправить");
        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String fileName = fileUpload.getFilename();
                if (fileName != null && fileName.length() != 0) {
                    submit();
                    setVisible(false);
                }
            }
        });
        panel.add(submit);
        add(panel);
        setVisible(false);
        setFormAttributes();
        addHandler();
        
    }

    private void setFormAttributes() {
        String url = GWT.getHostPageBaseURL() + "FileUpload";
        setAction(url);
        setEncoding(FormPanel.ENCODING_MULTIPART);
        setMethod(FormPanel.METHOD_POST);
    }

    public void showPanel(String dirName) {
        dirNameTb.setValue(dirName);
        label.setText("Загрузка файла в выбранную папку:");
        setVisible(true);
        dirNameTb.setFocus(true);
    }

    private void addHandler() {

        addSubmitCompleteHandler(new SubmitCompleteHandler() {
            @Override
            public void onSubmitComplete(SubmitCompleteEvent event) {
                DependencyResolver.PRESENTER.setFileTree();
            }
        });
    }
}
