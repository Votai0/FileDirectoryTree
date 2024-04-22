import java.util.LinkedList;

/**
 * Represents a directory in a file system.
 * This class extends {@link FileSystemElement} and holds child elements, which can be files or directories.
 * Provides functionalities to manage child elements such as adding or removing them.
 */
public class Directory extends FileSystemElement {
    private LinkedList<FileSystemElement> children;

    /**
     * Constructs a new Directory with the specified name and parent.
     * Initializes the list of children.
     *
     * @param name   the name of the directory
     * @param parent the parent directory or null if this directory is the root
     */
    public Directory(String name, FileSystemElement parent) {
        super(name, parent);
        this.children = new LinkedList<>();
    }

    /**
     * Adds a child to the directory.
     * This method also sets the parent of the child to this directory.
     *
     * @param element the child element to be added
     * @return void
     * Time complexity: O(1) 
     */
    public void addChild(FileSystemElement element) {
        children.add(element);
        element.setParent(this);
    }

    /**
     * Removes a child from the directory.
     *
     * @param element the child element to be removed
     * @return void
     * Time complexity: O(n), where n is the number of children, as it may need to traverse all children in the worst case.
     */
    public void removeChild(FileSystemElement element) {
        children.remove(element);
    }

    /**
     * Returns the list of all child elements of the directory.
     *
     * @return a list of child elements
     * Time complexity: O(1)
     */
    public LinkedList<FileSystemElement> getChildren() {
        return children;
    }

    /**
     * Retrieves a child by name.
     * This method searches through the children of the directory to find an element with the specified name.
     *
     * @param name the name of the child to retrieve
     * @return the child element with the specified name, or null if no such child exists
     * Time complexity: O(n), where n is the number of children, as it may need to traverse all children in the worst case.
     */
    public FileSystemElement getOneChild(String name) {
        for (FileSystemElement elem : children) {
            if (elem.getName().equals(name)) {
                return elem;
            }
        }
        return null;
    }
}
