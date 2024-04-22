import java.util.LinkedList;

/**
 * Represents the entire file system, managing directories and files within it.
 * This class provides methods to perform operations such as creating, deleting, moving files and directories,
 * and printing the directory structure.
 */
public class FileSystem {
    private Directory currentDirectory;
    private Directory root;

    /**
     * Initializes the FileSystem with a root directory.
     * Time complexity: O(1)
     */
    public FileSystem() {
        this.root = new Directory("root", null);
        this.currentDirectory = this.root;
    }

    /**
     * Returns the current directory of the file system.
     *
     * @return the current directory
     * Time complexity: O(1)
     */
    public Directory getCurrentDirectory() {
        return this.currentDirectory;
    }

    /**
     * Sets the current directory to the specified path.
     * The path must represent a valid directory structure from the root.
     *
     * @param path the path to set as the current directory
     * Time complexity: O(n), where n is the number of elements in the directory tree.
     */
    public void setCurrentDirectory(String path) {
        Directory tempDirectory = root;
        String[] parts = path.split("/");
        for (String part : parts) {
            if (part.isEmpty()) continue;
            FileSystemElement element = searchFileOrDirectoryRecursive(tempDirectory, part);
            if (element == null || !(element instanceof Directory)) {
                System.out.println("Directory not found: " + part);
                return;
            }
            tempDirectory = (Directory) element;
        }
        this.currentDirectory = tempDirectory;
    }

    /**
     * Searches for a file or directory from the root of the file system.
     *
     * @param name the name of the file or directory to search
     * @return the file or directory if found, null otherwise
     * Time complexity: O(n), where n is the number of elements in the directory tree.
     */
    public FileSystemElement searchFileOrDirectory(String name) {
        return searchFileOrDirectoryRecursive(root, name);
    }

    /**
     * Recursively searches for a file or directory within a specified element.
     * 
     * @param element the element to start the search
     * @param name    the name of the file or directory to find
     * @return the found file or directory, or null if not found
     * Time complexity: O(n), where n is the number of elements in the directory tree.
     */
    private FileSystemElement searchFileOrDirectoryRecursive(FileSystemElement element, String name) {
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

    /**
     * Creates a file or directory in the current directory.
     *
     * @param name    the name of the new file or directory
     * @param isFile  true to create a file, false to create a directory
     * Time complexity: O(1)
     */
    public void createFileOrDirectory(String name, boolean isFile) {
        if (isFile) {
            File newFile = new File(name, currentDirectory);
            currentDirectory.addChild(newFile);
        } else {
            Directory newDirectory = new Directory(name, currentDirectory);
            currentDirectory.addChild(newDirectory);
        }
    }

    /**
     * Recursively deletes a file or directory within the current directory.
     *
     * @param name the name of the file or directory to delete
     * Time complexity: O(n), where n is the number of elements in the directory tree.
     */
    public void deleteFileOrDirectory(String name) {
        FileSystemElement toDelete = searchFileOrDirectoryRecursive(this.currentDirectory, name);
        if (toDelete == null) {
            System.out.println("File/directory not found!");
            return;
        }
        if (toDelete == this.currentDirectory) {
            System.out.println("Cannot delete current directory!");
            return;
        } else if (toDelete instanceof File) {
            ((Directory) toDelete.getParent()).removeChild(toDelete);
            System.out.println("Deleted file: " + name);
        } else {
            deleteDirectoryRecursive((Directory) toDelete);
            System.out.println("Deleted directory: " + name);
        }
    }

    /**
     * Recursively deletes a directory and its contents.
     *
     * @param directory the directory to delete
     * Time complexity: O(n), where n is the number of elements in the directory tree.
     */
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

    /**
     * Prints the entire directory tree starting from the root.
     * Time complexity: O(n), where n is the number of elements in the directory tree.
     */
    public void printDirectoryTree() {
        printDirectoryRecursive(root, "");
    }

    /**
     * Recursively prints the directory tree.
     *
     * @param element the current element to print
     * @param indent  the indentation to use for printing, increases with each level in the tree
     * Time complexity: O(n), where n is the number of elements in the directory tree.
     */
    public void printDirectoryRecursive(FileSystemElement element, String indent) {
        if (element instanceof File) {
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

    /**
     * Moves a file or directory from the current directory to a specified new location.
     *
     * @param sourceName      the name of the source file or directory
     * @param destinationPath the path to move the source to
     * Time complexity: O(n), where n is the number of elements in the directory tree.
     */
    public void moveFileOrDirectory(String sourceName, String destinationPath) {
        FileSystemElement source = this.currentDirectory.getOneChild(sourceName);
        if (source == null) {
            System.out.println("Source file/directory not found!");
            return;
        }
        FileSystemElement destination = root;
        String[] parts = destinationPath.split("/");
        for (String part : parts) {
            if (part.isEmpty()) continue;
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
        ((Directory) source.getParent()).removeChild(source);
        ((Directory) destination).addChild(source);
        System.out.println("Moved " + source.getName() + " to " + ((Directory) destination).getName());
    }

    /**
     * Sorts the children of the current directory by their timestamp.
     * Time complexity: O(n^2), where n is the number of children in the directory.
     */
    public void sortByTimeStamp() {
        Directory tempDirectory = this.currentDirectory;
        FileSystemElement temp;
        LinkedList<FileSystemElement> childrens = tempDirectory.getChildren();
        for (int i = 0; i < childrens.size(); i++) {
            for (int j = 0; j < childrens.size(); j++) {
                if (childrens.get(i).getDateCreated().compareTo(childrens.get(j).getDateCreated()) < 0) {
                    temp = childrens.get(i);
                    childrens.set(i, childrens.get(j));
                    childrens.set(j, temp);
                }
            }
        }
        for (FileSystemElement element : childrens) {
            System.out.println(element.getName() + " Created on: " + element.getDateCreated());
        }
    }
}
