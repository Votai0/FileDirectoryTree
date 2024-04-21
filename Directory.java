import java.util.LinkedList;

public class Directory extends FileSystemElement {
    private LinkedList<FileSystemElement> children;

    public Directory(String name, FileSystemElement parent) {
        super(name, parent);
        this.children = new LinkedList<>();
    }

    public void addChild(FileSystemElement element) {
        children.add(element);
        element.setParent(this);
    }

    public void removeChild(FileSystemElement element) {
        children.remove(element);
    }

    public LinkedList<FileSystemElement> getChildren() {
        return children;
    }
    public FileSystemElement getOneChild(String name) {
        for (FileSystemElement elem : children) {
            if (elem.getName().equals(name)) {
                return elem;
            }
        }
        return null;
    }

    public FileSystemElement getChild(String name) {
        for (FileSystemElement elem : children) {
            if (elem.getName().equals(name)) {
                return elem;
            }
        }
        return null;
    }

    @Override
    public void printDetails() {
        System.out.println("Directory: " + getName() + " - Contains: " + children.size() + " items.");
        for (FileSystemElement elem : children) {
            elem.printDetails();
        }
    }
}
