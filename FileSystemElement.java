import java.sql.Timestamp;

public abstract class FileSystemElement {
    protected String name;
    protected Timestamp dateCreated;
    protected FileSystemElement parent;

    public FileSystemElement(String name, FileSystemElement parent) {
        this.name = name;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public FileSystemElement getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(FileSystemElement parent) {
        this.parent = parent;
    }

    //get path
    public String getPath() {
        if (parent == null) {
            return name;
        } else {
            return parent.getPath() + "/" + name;
        }
    }

    // Bu metod, alt sınıflar tarafından özelleştirilerek tanımlanacak.
    public abstract void printDetails();
}
