public class File extends FileSystemElement {
    public File(String name, FileSystemElement parent) {
        super(name, parent);
    }

    @Override
    public void printDetails() {
        System.out.println("File: " + getName() + " Created on: " + getDateCreated());
    }
}
