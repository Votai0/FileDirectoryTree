public class FileSystem {
    private Directory currentDirectory;
    private Directory root;
    public FileSystem() {
        this.currentDirectory = new Directory("root", null);
        this.root = this.currentDirectory;
    }

    public Directory getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String name) {
        //TODO: Implement
        FileSystemElement found = searchFileOrDirectory(name);
        if (found != null && found instanceof Directory) {
            currentDirectory = (Directory) found;
        } else {
            System.out.println("Directory not found!");
        }
    }

   // search file or directory from root
    public FileSystemElement searchFileOrDirectory(String name) {
        return searchFileOrDirectoryRecursive(root, name);
    }

    private FileSystemElement searchFileOrDirectoryRecursive(FileSystemElement element, String name) {
    if (element == null) {
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
        FileSystemElement element = searchFileOrDirectory(name);
        if (element == null) {
            System.out.println("File/directory not found!");
            return;
        }
        if (element instanceof File) {
            currentDirectory.removeChild(element);
        } else {
            Directory directory = (Directory) element;
            for (FileSystemElement child : directory.getChildren()) {
                deleteFileOrDirectory(child.getName());
            }
            currentDirectory.removeChild(element);
        }
    }
        

    public void printDirectoryTree() {
        printDirectoryRecursive(root, "");
    }

    public void printDirectoryRecursive(FileSystemElement element, String indent) {
        if (element == null) {
            return; // Eğer element null ise işlemi durdur.
        }
        if(element instanceof File) {
            System.out.println(indent + element.getName());
        } else {
            System.out.println(indent + "* " + element.getName() + "/");
        }
        if (element instanceof Directory) {
            for (FileSystemElement child : ((Directory) element).getChildren()) {
                printDirectoryRecursive(child, indent + "  ");
            }
        }
    }
    

    public void moveFileOrDirectory(String sourceName, String destinationPath) {
    // Kaynak dosya veya dizini bul
    FileSystemElement toMove = searchFileOrDirectory(sourceName);
    if (toMove == null) {
        System.out.println("Source file/directory not found!");
        return;
    }

    // Hedef dizini bul
    FileSystemElement destination = searchFileOrDirectory(destinationPath);
    if (destination == null || !(destination instanceof Directory)) {
        System.out.println("Destination directory not found or is not a directory!");
        return;
    }

    // Eğer kaynak ve hedef aynı ise, işlemi gerçekleştirme
    if (toMove.getParent() == destination) {
        System.out.println("Source and destination are the same.");
        return;
    }

    // Kaynaktan çıkar
    if (toMove.getParent() != null) {
        ((Directory) toMove.getParent()).removeChild(toMove);
    }

    // Hedefe ekle
    ((Directory) destination).addChild(toMove);
    toMove.setParent(destination);
    System.out.println("Moved " + sourceName + " to " + destinationPath);
    }






}
