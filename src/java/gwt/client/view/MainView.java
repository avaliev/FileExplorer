/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.client.view;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import gwt.client.model.BaseTreeModel;
import gwt.client.model.BaseTreeViewModel;
import gwt.client.model.FileNode;
import gwt.client.model.FolderNode;
import gwt.client.widgets.DownloadForm;
import gwt.client.widgets.FileUploadPanel;

/**
 *
 * @author Айрат
 */
public class MainView extends VerticalPanel implements IMainView , SelectionChangeEvent.Handler {

    private Presenter _presenter;
    private Button renameBtn = new Button("Rename");
    private Button deleteBtn = new Button("Delete");
    private Button downloadBtn = new Button("Download");
    private Button uploadBtn = new Button("Upload");
    private Button mkDirBtn = new Button("MakeDir");
    
    private TextBox inputBox = new TextBox();
    
    FlowPanel toolBar = new FlowPanel();
    private Label label = new Label("Для сохранения введенного значения просто нажмите Enter");
    
    // форма для отправки запроса на загрузку файла
    private final DownloadForm downLoadform = new DownloadForm();
   
   
    //форма для отправки загрузки файла на сервер
    FileUploadPanel uploadPanel=new FileUploadPanel();
    
    ScrollPanel scrollp = new ScrollPanel();
    
    CellTree filesTree;
    int operID;
    final int renameOper = 10;
    final int mkdirOper = 20;
    final BaseTreeViewModel treeModel = new BaseTreeViewModel();

    public MainView() {
        InitView();
        treeModel.getSelectionModel().addSelectionChangeHandler(this);
    }

    /**
     * Инициализация представления
     */
    private void InitView() {
        setSize("100%", "100%");
        setSpacing(10);
        add(label);
        add(downLoadform);
        add(toolBar);
        addInputBox();
        add(scrollp);
        add(uploadPanel);
        toolBar.setWidth("100%");
        toolBar.setHeight("20px");
        //scrollp.add(dp);
        CreateButtons();
        scrollp.setSize("100%", "400px");
        
    }

    private void CreateButtons() {

        toolBar.add(mkDirBtn);
        toolBar.add(renameBtn);
        toolBar.add(deleteBtn);
        toolBar.add(downloadBtn);
        toolBar.add(uploadBtn);
        mkDirBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                BaseTreeModel model = treeModel.getSelectedNode();
                if (model instanceof FolderNode) {
                    inputBox.setVisible(true);
                    inputBox.setFocus(true);
                    label.setText(model.getPath());
                    mkDirBtn.setEnabled(false);
                    operID = mkdirOper;
                }
            }
        });

        deleteBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                BaseTreeModel model = treeModel.getSelectedNode();
                if (model instanceof FileNode || model instanceof FolderNode
                        && ((FolderNode) model).getFiles().isEmpty()) {
                    _presenter.delete(treeModel.getSelectedNode());
                } else {
                    Window.alert("Нельзя удалить непустую папку!");
                }
            }
        });
        downloadBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onDownload(treeModel.getSelectedNode());
            }
        });
        renameBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (treeModel.getSelectedNode() != null) {
                    operID = renameOper;
                    renameBtn.setEnabled(false);
                    inputBox.setVisible(true);
                    inputBox.setValue(treeModel.getSelectedNode().getName());
                    inputBox.setFocus(true);
                }
            }
        });
        
        
        uploadBtn.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                BaseTreeModel node=treeModel.getSelectedNode();
                if (node instanceof FolderNode) {
                    uploadPanel.showPanel(node.getPath());
                }
            }
        });
    }

    @Override
    public void setFilesTree(FolderNode root) {
        treeModel.setRootNode(root);
        filesTree = new CellTree(treeModel, null);
        scrollp.clear();
        scrollp.add(filesTree);
        filesTree.setSize("100%", "100%");
        TreeNode node= filesTree.getRootTreeNode();
        node.setChildOpen(0, true);
        filesTree.setFocus(true);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        _presenter = presenter; //To change body of generated methods, choose Tools | Templates.
    }

    private void onDownload(BaseTreeModel selectedNode) {
        if (selectedNode instanceof FileNode) {
            downLoadform.downloadFile(selectedNode.getPath());
        }
    }

    //
    private void addInputBox() {
        add(inputBox);
        inputBox.setVisible(false);
        inputBox.setWidth("300px");

        inputBox.addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                inputBox.setVisible(false);
                renameBtn.setEnabled(true);
                mkDirBtn.setEnabled(true);
                inputBox.setValue("");
            }
        });

        inputBox.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                boolean enterPressed = KeyCodes.KEY_ENTER == event.getNativeEvent().getKeyCode();
                if (enterPressed) {
                    if (operID == renameOper) {
                        toRenameFile();
                    }
                    if (operID == mkdirOper) {
                        OnMakeDir();
                    }
                }
            }
        });
    }

    private void toRenameFile() {
        renameBtn.setEnabled(true);
        inputBox.setVisible(false);
        String newValue = inputBox.getValue().trim();
        if (!"".equals(newValue) && !newValue.equals(treeModel.getSelectedNode().getName())) {
            _presenter.rename(treeModel.getSelectedNode(), inputBox.getValue());
        }
    }

    /**
     * Создается папка в текущей директории
     */
    private void OnMakeDir() {
        BaseTreeModel model = treeModel.getSelectedNode();
        String folderName = inputBox.getValue();
        if (model instanceof FolderNode && folderName != null && !folderName.equals("")) {
            inputBox.setVisible(false);
            mkDirBtn.setEnabled(true);
            _presenter.makeDir((FolderNode) model, folderName);
        }
    }

    @Override
    public void onSelectionChange(SelectionChangeEvent event) {
        BaseTreeModel model=treeModel.getSelectedNode();
        if (model instanceof FileNode) {
            mkDirBtn.setEnabled(false);
            uploadBtn.setEnabled(false);
            deleteBtn.setEnabled(true);
            downloadBtn.setEnabled(true);
        } 
        if (model instanceof FolderNode) {
            deleteBtn.setEnabled(false);
            downloadBtn.setEnabled(false);
            mkDirBtn.setEnabled(true);
            uploadBtn.setEnabled(true);
        }
        
    }
}
