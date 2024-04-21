import java.util.LinkedList;
import java.util.List;

public class Directory extends FileSystemElement {
    private List<FileSystemElement> children;

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

    public List<FileSystemElement> getChildren() {
        return children;
    }

    @Override
    public void printDetails() {
        System.out.println("Directory: " + getName() + " - Contains: " + children.size() + " items.");
        for (FileSystemElement elem : children) {
            elem.printDetails();
        }
    }
}
