import java.sql.Timestamp;
/**
 * Abstract class representing an element in a file system.
 * This class serves as a base for any file or directory,
 * encapsulating common attributes such as name, creation date, and parent reference.
 *
 * @author Author
 */
public abstract class FileSystemElement {
    protected String name;             // Name of the file or directory
    protected Timestamp dateCreated;   // Timestamp indicating when the file or directory was created
    protected FileSystemElement parent; // Parent directory of this file or directory, null if this is the root

    /**
     * Constructs a new FileSystemElement with the specified name and parent.
     * The creation date is set to the current system time.
     *
     * @param name   the name of the file or directory
     * @param parent the parent directory, null if this is the root element
     * Time complexity: O(1)
     */
    public FileSystemElement(String name, FileSystemElement parent) {
        this.name = name;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
        this.parent = parent;
    }
    
    /**
     * Returns the name of this file or directory.
     *
     * @return the name of this file or directory
     * Time complexity: O(1)
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the creation timestamp of this file or directory.
     *
     * @return the creation timestamp
     * Time complexity: O(1)
     */
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**
     * Returns the parent directory of this file or directory.
     * Returns null if this is the root of the directory tree.
     *
     * @return the parent directory, or null if this is the root
     * Time complexity: O(1)
     */
    public FileSystemElement getParent() {
        return parent;
    }

    /**
     * Sets the name of this file or directory.
     *
     * @param name the new name of this file or directory
     * Time complexity: O(1)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the parent directory of this file or directory.
     * Use this method to change the location of this element within the file system.
     *
     * @param parent the new parent directory, or null if setting this element as root
     * Time complexity: O(1)
     */
    public void setParent(FileSystemElement parent) {
        this.parent = parent;
    }
    

    /**
     * Generates and returns the absolute path from the root to this file or directory.
     * This is a recursive method that builds the path by traversing up to the root directory.
     *
     * @return the absolute path from the root to this file or directory
     * Time complexity: O(n) where n is the depth of the file system tree
     */
    public String getPath() {
        if (parent == null) {
            return name;
        } else {
            return parent.getPath() + "/" + name;
        }
    }
}
