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

    public void deleteFileOrDirectory(String name) {
        FileSystemElement toDelete = null;
        for (FileSystemElement element : currentDirectory.getChildren()) {
            if (element.getName().equals(name)) {
                toDelete = element;
                break;
            }
        }
        if (toDelete != null) {
            currentDirectory.removeChild(toDelete);
        }
    }

    public void printDirectoryTree() {
        printDirectoryRecursive(root, "");
    }

    public void printDirectoryRecursive(FileSystemElement element, String indent) {
    if (element == null) {
        return; // Eğer element null ise işlemi durdur.
    }
    System.out.println(indent + element.getName());
    if (element instanceof Directory) {
        for (FileSystemElement child : ((Directory) element).getChildren()) {
            printDirectoryRecursive(child, indent + "  ");
        }
    }
}



    private Directory findRootRecursive(FileSystemElement element) {
        if (element.getParent() == null) {
            return (Directory) element;
        }
        return findRootRecursive(element.getParent());
    }
}
