import java.util.LinkedList;

public class FileSystem {
    private Directory currentDirectory;
    private Directory root;
    public FileSystem() {
        this.currentDirectory = new Directory("root", null);
        this.root = this.currentDirectory;
    }

    public Directory getCurrentDirectory() {
        return this.currentDirectory;
    }

    public void setCurrentDirectory(String path) {
        Directory tempDirectory = root;  // Kök dizinden başla
        String[] parts = path.split("/");  // Gelen yolu "/" karakterine göre böl
        for (String part : parts) {
            FileSystemElement element = searchFileOrDirectoryRecursive(tempDirectory, part);
            if (element == null || !(element instanceof Directory)) {
                System.out.println("Directory not found: " + part);
                return;
            }
            tempDirectory = (Directory) element;            
        }
        this.currentDirectory = tempDirectory;
    }


   // search file or directory from root
    public FileSystemElement searchFileOrDirectory(String name) {
        return searchFileOrDirectoryRecursive(root, name);
    }

    private FileSystemElement searchFileOrDirectoryRecursive(FileSystemElement element, String name) {
        if (element == null || name == null || name.isEmpty() || name.isBlank()) {
            return null;
        }
        if (element.getName().equals(name)) {
            return element;
        }
        if (element instanceof Directory) {
            for (FileSystemElement child : ((Directory) element).getChildren()) {
                FileSystemElement found = searchFileOrDirectoryRecursive(child, name);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }


    public void createFileOrDirectory(String name, boolean isFile) {
        if (isFile) {
            File newFile = new File(name, currentDirectory);
            currentDirectory.addChild(newFile);
        } else {
            Directory newDirectory = new Directory(name, currentDirectory);
            currentDirectory.addChild(newDirectory);
        }
    }

    //Recursively delete directories and their contents.
    public void deleteFileOrDirectory(String name) {
        //search under the current directory
        FileSystemElement toDelete = searchFileOrDirectoryRecursive(this.currentDirectory, name);
        if (toDelete == null) {
            System.out.println("File/directory not found!");
            return;
        }if (toDelete == this.currentDirectory) {
            System.out.println("Cannot delete current directory!");
            return;
        }else if (toDelete instanceof File) {
            ((Directory) toDelete.getParent()).removeChild(toDelete);
            System.out.println("Deleted file: " + name);
        } else {
            deleteDirectoryRecursive((Directory) toDelete);
            System.out.println("Deleted directory: " + name);
        }
    }

    public void deleteDirectoryRecursive(Directory directory) {
        for (FileSystemElement child : directory.getChildren()) {
            if (child instanceof Directory) {
                deleteDirectoryRecursive((Directory) child);
            } else {
                System.out.println("Deleted file: " + child.getName());
            }
        }
        ((Directory) directory.getParent()).removeChild(directory);
    }
        

    public void printDirectoryTree() {
        printDirectoryRecursive(root, "");
    }

    public void printDirectoryRecursive(FileSystemElement element, String indent) {
        if (element == null) {
            return; // Eğer element null ise işlemi durdur.
        }
        if(element instanceof File) {
            System.out.println(indent + " " + element.getName());
        } else {
            System.out.println(indent + "*" + element.getName() + "/");
        }
        if (element instanceof Directory) {
            for (FileSystemElement child : ((Directory) element).getChildren()) {
                printDirectoryRecursive(child, indent + "  ");
            }
        }
    }
    public void moveFileOrDirectory(String sourceName, String destinationPath) {
        // Kaynak nesneyi bul
        FileSystemElement source = this.currentDirectory.getOneChild(sourceName);

        if (source == null) {
            System.out.println("Source file/directory not found!");
            return;
        }

        // Hedef dizini bul
        FileSystemElement destination = root;
        String[] parts = destinationPath.split("/");  // Gelen yolu "/" karakterine göre böl
        for (String part : parts) {
            FileSystemElement element = searchFileOrDirectoryRecursive(destination, part);
            if (element == null || !(element instanceof Directory)) {
                System.out.println("Directory not found: " + part);
                return;
            }
            destination = (Directory) element;            
        }

        if (destination == null || !(destination instanceof Directory)) {
            System.out.println("Destination directory not found or is not a directory!");
            return;
        }
        // Taşıma işlemini engelleyecek durumları kontrol et
        if (source == destination) {
            System.out.println("Source and destination are the same!");
            return;
        }
        if (source.getParent() == null) {
            System.out.println("Cannot move the root directory!");
            return;
        }
        if (source == this.currentDirectory) {
            System.out.println("Cannot move the current working directory!");
            return;
        }

        // Kaynak nesneyi eski dizinden çıkar
        ((Directory) source.getParent()).removeChild(source);
        // Hedef dizine ekle
        ((Directory) destination).addChild(source);


        System.out.println("Moved " + source.getName() + " to " + ((Directory) destination).getName());
    }




    public void sortByTimeStamp(){
        Directory tempDirectory = this.currentDirectory;
        FileSystemElement temp;
        LinkedList <FileSystemElement> childrens = tempDirectory.getChildren();
        for(int i = 0; i < childrens.size(); i++){
            for(int j = 0; j < childrens.size(); j++){
                if(childrens.get(i).getDateCreated().compareTo(childrens.get(j).getDateCreated()) < 0){
                    temp = childrens.get(i);
                    childrens.set(i, childrens.get(j));
                    childrens.set(j, temp);
                }
            }
        }
        
        for(FileSystemElement element : childrens){
            System.out.println(element.getName() + " Created on: " + element.getDateCreated());
        }

    }




}
