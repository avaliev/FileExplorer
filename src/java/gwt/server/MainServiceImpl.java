/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import gwt.client.MainService;
import gwt.client.model.BaseTreeModel;
import gwt.client.model.FileNode;
import gwt.client.model.FolderNode;
import java.io.*;


/**
 *
 * @author Айрат
 */
public class MainServiceImpl extends RemoteServiceServlet implements MainService {

    static String DIRECTORY="C:/test/";
    
    private FolderNode root;
            
    @Override
    public BaseTreeModel getFileTree() {
        String path=getServletContext().getInitParameter("base.dir");
        File rootDir=new File(path);
        root=new FolderNode();
        root.setName(rootDir.getName());
        root.setPath(rootDir.getAbsolutePath());
        processFolder(root, rootDir);
        return root;
    }
    
    private void processFolder(FolderNode folder,File file) {
       File[] childs=file.listFiles();
       
       for (File f:childs) {
           if (f.isFile()) {
               FileNode fileNode=new FileNode();
               fileNode.setPath(f.getAbsolutePath());
               fileNode.setName(f.getName());
               folder.addChild(fileNode);
           }
           if (f.isDirectory()) {
               FolderNode folderNode=new FolderNode();
               folderNode.setPath(f.getAbsolutePath());
               folderNode.setName(f.getName());
               folder.addChild(folderNode);
               File fold=new File(f.getPath());
               processFolder(folderNode,fold);
           }
       }
    }

    @Override
    public BaseTreeModel delete(BaseTreeModel node) {
         File file=new File(node.getPath());
         if  (file.isFile() || file.list().length==0) file.delete();
         
         return getFileTree();
    }

    @Override
    public BaseTreeModel rename(BaseTreeModel node, String newName) {
        File file=new File(node.getPath());
        File newFile=new File(file.getParent() + "/" + newName);
        file.renameTo(newFile);
        return getFileTree();
    }

    @Override
    public BaseTreeModel mkdir(FolderNode node, String folderName) {
        File file=new File(node.getPath()+ "/" +folderName);
        file.mkdir();
        return getFileTree();
    }

   
}
