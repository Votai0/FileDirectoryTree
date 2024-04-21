import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FileSystem fileSystem = new FileSystem();

    

        while (true) {
            System.out.println("\n===== File System Management Menu =====");
            System.out.println("Please select an option:");
            System.out.println("1. Change directory");
            System.out.println("2. List directory contents");
            System.out.println("3. Create file/directory");
            System.out.println("4. Delete file/directory");
            System.out.println("5. Move file/directory");
            System.out.println("6. Search file/directory");
            System.out.println("7. Print directory tree");
            System.out.println("8. Sort contents by date created");
            System.out.println("9. Exit");

            System.out.print("Please select an option: ");
            int choice = 0;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Bu satır, input buffer'daki kalan newline karakterini temizler.
            } else {
                System.out.println("Please enter a valid integer.");
                scanner.nextLine(); // Geçersiz girişi temizle
                continue; // Döngünün başına dön
            }

            switch (choice) {
                case 1:{
                    System.out.println("Current directory: " + fileSystem.getCurrentDirectory().getPath());
                    System.out.print("Enter name of directory to change to: ");
                    String dirName = scanner.nextLine();
                    fileSystem.setCurrentDirectory(dirName);
                    break;
                }
                case 2:{
                    fileSystem.printDirectoryRecursive(fileSystem.getCurrentDirectory(), "");
                    break;
                }
                case 3:{
                    System.out.println("Current directory: " + fileSystem.getCurrentDirectory().getPath());
                    System.out.print("Create file or directory (f/d): ");
                    String type = scanner.nextLine();
                    if (type.equalsIgnoreCase("f")) {
                        System.out.print("Enter name for new file: ");
                        String fileName = scanner.nextLine();
                        fileSystem.createFileOrDirectory(fileName, true);
                        System.out.println("File created: " + fileName);
                    } else if (type.equalsIgnoreCase("d")) {
                        System.out.print("Enter name for new directory: ");
                        String dirName = scanner.nextLine();
                        fileSystem.createFileOrDirectory(dirName, false);
                        System.out.println("Directory created: " + dirName);
                    } else {
                        System.out.println("Invalid option!");
                    }
                    break;
                }
                case 4:{
                    System.out.println("Current directory: " + fileSystem.getCurrentDirectory().getPath());
                    System.out.print("Enter name of file/directory to delete: ");
                    String nameToDelete = scanner.nextLine();
                    fileSystem.deleteFileOrDirectory(nameToDelete);
                    break;
                    }

                case 5:{
                    System.out.println("Current directory: " + fileSystem.getCurrentDirectory().getPath());
                    System.out.print("Enter name of file/directory to move: ");
                    String nameToMove = scanner.nextLine();
                    System.out.print("Enter new directory path: ");
                    String newPath = scanner.nextLine();
                    fileSystem.moveFileOrDirectory(nameToMove, newPath);
                    break;
                    }
                case 6:{
                    System.out.print("Search query: ");
                    String query = scanner.nextLine();
                    System.out.println("Searching from root.. ");
                    //TODO: Search file/directory
                    FileSystemElement found = fileSystem.searchFileOrDirectory(query);
                    if (found != null) {
                        System.out.println("Found: " + found.getPath());
                    } else {
                        System.out.println("Not found!");
                    }
                    break;
                    }
                case 7:{
                    fileSystem.printDirectoryTree();
                    break;
                    }
                case 8:{
                    System.out.println("Sorting contents of " + fileSystem.getCurrentDirectory().getPath() + " by date created..");
                    fileSystem.sortByTimeStamp();
                    break;
                    }
                case 9:{
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                }
                default:{
                    System.out.println("Invalid option!");
                    break;
                }

            }
        }
    }
}