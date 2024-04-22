import java.util.Scanner;

/**
 * Main class that serves as the entry point for the FileSystem management application.
 * Provides a command-line interface to interact with the FileSystem, allowing users to manage files and directories.
 */
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
                scanner.nextLine(); // Clears the newline character from the input buffer.
            } else {
                System.out.println("Please enter a valid integer.");
                scanner.nextLine(); // Clears the invalid input
                continue; // Continues to the next iteration of the loop
            }

            switch (choice) {
                case 1: // Change directory
                    System.out.println("Current directory: " + fileSystem.getCurrentDirectory().getPath());
                    System.out.print("Enter name of directory to change to: ");
                    String dirName = scanner.nextLine();
                    fileSystem.setCurrentDirectory(dirName);
                    // Time Complexity: O(n), where n is the number of directories in the path
                    break;
                case 2: // List directory contents
                    fileSystem.printDirectoryRecursive(fileSystem.getCurrentDirectory(), "");
                    // Time Complexity: O(n), where n is the number of files/directories in the current directory
                    break;
                case 3: // Create file/directory
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
                        String newDirName = scanner.nextLine();
                        fileSystem.createFileOrDirectory(newDirName, false);
                        System.out.println("Directory created: " + newDirName);
                    } else {
                        System.out.println("Invalid option!");
                    }
                    // Time Complexity: O(1) for adding to LinkedList
                    break;
                case 4: // Delete file/directory
                    System.out.println("Current directory: " + fileSystem.getCurrentDirectory().getPath());
                    System.out.print("Enter name of file/directory to delete: ");
                    String nameToDelete = scanner.nextLine();
                    fileSystem.deleteFileOrDirectory(nameToDelete);
                    // Time Complexity: O(n), where n is the number of elements to potentially recurse through
                    break;
                case 5: // Move file/directory
                    System.out.println("Current directory: " + fileSystem.getCurrentDirectory().getPath());
                    System.out.print("Enter name of file/directory to move: ");
                    String nameToMove = scanner.nextLine();
                    System.out.print("Enter new directory path: ");
                    String newPath = scanner.nextLine();
                    fileSystem.moveFileOrDirectory(nameToMove, newPath);
                    // Time Complexity: O(n+m), where n is the path length to find source and m is the path length to find destination
                    break;
                case 6: // Search file/directory
                    System.out.print("Search query: ");
                    String query = scanner.nextLine();
                    System.out.println("Searching from root...");
                    FileSystemElement found = fileSystem.searchFileOrDirectory(query);
                    if (found != null) {
                        System.out.println("Found: " + found.getPath());
                    } else {
                        System.out.println("Not found!");
                    }
                    // Time Complexity: O(n), where n is the number of elements in the directory tree
                    break;
                case 7: // Print directory tree
                    fileSystem.printDirectoryTree();
                    // Time Complexity: O(n), where n is the number of elements in the directory tree
                    break;
                case 8: // Sort contents by date created
                    System.out.println("Sorting contents of " + fileSystem.getCurrentDirectory().getPath() + " by date created...");
                    fileSystem.sortByTimeStamp();
                    // Time Complexity: O(n^2), where n is the number of files/directories in the current directory (bubble sort)
                    break;
                case 9: // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default: // Invalid option
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }
}
