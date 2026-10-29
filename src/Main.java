import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Integer> patientIds = new ArrayList<Integer>();
    static ArrayList<String> names = new ArrayList<String>();
    static ArrayList<Integer> ages = new ArrayList<Integer>();
    static ArrayList<String> conditions = new ArrayList<String>();
    static ArrayList<Integer> priorities = new ArrayList<Integer>();
    static ArrayList<String> statuses = new ArrayList<String>();
    static Queue<Integer> patientQueue = new LinkedList<Integer>();
    static int nextPatientId = 1;
    static boolean running = true;

    public static void main(String[] args) {
        while (running) {
            showMenu();
            String choiceText = readLine("Choose an option: ");
            if (choiceText == null) break;
            int choice;
            try {
                choice = Integer.parseInt(choiceText.trim());
            } catch (NumberFormatException error) {
                System.out.println("Please enter a menu number.");
                continue;
            }
            switch (choice) {
                case 1: addPatient(); break;
                case 2: displayPatients(); break;
                case 3: searchPatient(); break;
                case 4: updatePatient(); break;
                case 5: deletePatient(); break;
                case 6: bubbleSortPatients(); break;
                case 7: insertionSortPatients(); break;
                case 8: enqueuePatient(); break;
                case 9: dequeuePatient(); break;
                case 10: peekQueue(); break;
                case 11: displayQueue(); break;
                case 12: checkQueueEmpty(); break;
                case 0: running = false; System.out.println("Goodbye."); break;
                default: System.out.println("Invalid menu choice. Choose 0 through 12.");
            }
        }
        scanner.close();
    }

    static void showMenu() {
        System.out.println("\n=== Clinic Patient Queue System ===");
        System.out.println("1. Add patient        2. Display all patients");
        System.out.println("3. Search patient     4. Update patient");
        System.out.println("5. Delete patient     6. Bubble sort patients");
        System.out.println("7. Insertion sort     8. Enqueue patient");
        System.out.println("9. Serve next patient 10. Peek at next patient");
        System.out.println("11. Display queue     12. Check if queue is empty");
        System.out.println("0. Exit");
    }

    static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) return null;
        return scanner.nextLine();
    }

    static Integer readInteger(String prompt) {
        String text = readLine(prompt);
        if (text == null) { running = false; return null; }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException error) {
            System.out.println("Please enter a whole number.");
            return null;
        }
    }

    static Integer readIntegerInRange(String prompt, int minimum, int maximum) {
        while (running) {
            String text = readLine(prompt);
            if (text == null) { running = false; return null; }
            try {
                int number = Integer.parseInt(text.trim());
                if (number >= minimum && number <= maximum) return number;
                System.out.println("Enter a number from " + minimum + " to " + maximum + ".");
            } catch (NumberFormatException error) {
                System.out.println("Please enter a whole number.");
            }
        }
        return null;
    }

    static String readNonEmpty(String prompt) {
        while (running) {
            String value = readLine(prompt);
            if (value == null) { running = false; return null; }
            value = value.trim();
            if (!value.isEmpty()) return value;
            System.out.println("This value cannot be empty.");
        }
        return null;
    }

    static void addPatient() {
        if (!running) return;
        String name = readNonEmpty("Patient name: ");
        if (name == null) return;
        Integer age = readIntegerInRange("Age (0-120): ", 0, 120);
        if (age == null) return;
        String condition = readNonEmpty("Condition: ");
        if (condition == null) return;
        Integer priority = readIntegerInRange("Priority (1 emergency, 2 urgent, 3 normal): ", 1, 3);
        if (priority == null) return;
        patientIds.add(nextPatientId++);
        names.add(name);
        ages.add(age);
        conditions.add(condition);
        priorities.add(priority);
        statuses.add("Waiting");
        System.out.println("Patient added with ID " + patientIds.get(patientIds.size() - 1) + ".");
    }

    static void displayPatients() {
        if (patientIds.isEmpty()) { System.out.println("No patients have been added yet."); return; }
        System.out.println("\nID | Name | Age | Condition | Priority | Status");
        for (int i = 0; i < patientIds.size(); i++) printPatient(i);
    }

    static void printPatient(int index) {
        System.out.println(patientIds.get(index) + " | " + names.get(index) + " | " + ages.get(index)
                + " | " + conditions.get(index) + " | " + priorityLabel(priorities.get(index))
                + " | " + statuses.get(index));
    }

    static String priorityLabel(int priority) {
        if (priority == 1) return "1 (Emergency)";
        if (priority == 2) return "2 (Urgent)";
        return "3 (Normal)";
    }

    static int findPatientIndex(int id) {
        for (int i = 0; i < patientIds.size(); i++) if (patientIds.get(i) == id) return i;
        return -1;
    }

    static void searchPatient() {
        if (patientIds.isEmpty()) { System.out.println("There are no patients to search."); return; }
        System.out.println("Search by: 1. ID  2. Name");
        String modeText = readLine("Choose 1 or 2: ");
        if (modeText == null) { running = false; return; }
        int mode;
        try { mode = Integer.parseInt(modeText.trim()); }
        catch (NumberFormatException error) { System.out.println("Invalid search choice."); return; }
        if (mode == 1) {
            Integer id = readInteger("Patient ID: ");
            if (id == null) return;
            int index = findPatientIndex(id);
            if (index < 0) System.out.println("No patient has ID " + id + ".");
            else { System.out.println("ID | Name | Age | Condition | Priority | Status"); printPatient(index); }
        } else if (mode == 2) {
            String query = readNonEmpty("Patient name: ");
            if (query == null) return;
            boolean found = false;
            for (int i = 0; i < names.size(); i++) {
                if (names.get(i).toLowerCase().contains(query.toLowerCase())) {
                    if (!found) System.out.println("ID | Name | Age | Condition | Priority | Status");
                    printPatient(i); found = true;
                }
            }
            if (!found) System.out.println("No patient matched that name.");
        } else System.out.println("Invalid search choice. Choose 1 or 2.");
    }

    static void updatePatient() {
        Integer id = readInteger("Patient ID to update: ");
        if (id == null) return;
        int index = findPatientIndex(id);
        if (index < 0) { System.out.println("Patient not found."); return; }
        String name = readNonEmpty("New name: "); if (name == null) return;
        Integer age = readIntegerInRange("New age (0-120): ", 0, 120); if (age == null) return;
        String condition = readNonEmpty("New condition: "); if (condition == null) return;
        Integer priority = readIntegerInRange("New priority (1-3): ", 1, 3); if (priority == null) return;
        names.set(index, name); ages.set(index, age); conditions.set(index, condition); priorities.set(index, priority);
        System.out.println("Patient updated.");
    }

    static void deletePatient() {
        Integer id = readInteger("Patient ID to delete: ");
        if (id == null) return;
        int index = findPatientIndex(id);
        if (index < 0) { System.out.println("Patient not found."); return; }
        patientQueue.remove(id);
        patientIds.remove(index); names.remove(index); ages.remove(index);
        conditions.remove(index); priorities.remove(index); statuses.remove(index);
        System.out.println("Patient deleted. If they were waiting in the queue, they were removed too.");
    }

    static int readSortKey() {
        System.out.println("Sort by: 1. Name  2. Age  3. Priority");
        Integer key = readIntegerInRange("Choose 1-3: ", 1, 3);
        return key == null ? -1 : key;
    }

    static boolean comesAfter(int left, int right, int key) {
        if (key == 1) return names.get(left).compareToIgnoreCase(names.get(right)) > 0;
        if (key == 2) return ages.get(left) > ages.get(right);
        return priorities.get(left) > priorities.get(right);
    }

    static void swapRecords(int first, int second) {
        Integer integerValue = patientIds.get(first); patientIds.set(first, patientIds.get(second)); patientIds.set(second, integerValue);
        String stringValue = names.get(first); names.set(first, names.get(second)); names.set(second, stringValue);
        integerValue = ages.get(first); ages.set(first, ages.get(second)); ages.set(second, integerValue);
        stringValue = conditions.get(first); conditions.set(first, conditions.get(second)); conditions.set(second, stringValue);
        integerValue = priorities.get(first); priorities.set(first, priorities.get(second)); priorities.set(second, integerValue);
        stringValue = statuses.get(first); statuses.set(first, statuses.get(second)); statuses.set(second, stringValue);
    }

    static void bubbleSortPatients() {
        if (patientIds.isEmpty()) { System.out.println("No patients to sort."); return; }
        int key = readSortKey(); if (key < 0) return;
        System.out.println("Before bubble sort:"); displayPatients();
        // Compare neighbors; each pass moves the next out-of-order record toward the end.
        for (int pass = 0; pass < patientIds.size() - 1; pass++) {
            for (int i = 0; i < patientIds.size() - 1 - pass; i++) {
                if (comesAfter(i, i + 1, key)) swapRecords(i, i + 1);
            }
        }
        System.out.println("After bubble sort:"); displayPatients();
        System.out.println("Queue order was not changed.");
    }

    static void insertionSortPatients() {
        if (patientIds.isEmpty()) { System.out.println("No patients to sort."); return; }
        int key = readSortKey(); if (key < 0) return;
        System.out.println("Before insertion sort:"); displayPatients();
        // Take each next record and move it left by swapping until the sorted part is in order.
        for (int i = 1; i < patientIds.size(); i++) {
            int current = i;
            while (current > 0 && comesAfter(current - 1, current, key)) {
                swapRecords(current - 1, current);
                current--;
            }
        }
        System.out.println("After insertion sort:"); displayPatients();
        System.out.println("Queue order was not changed.");
    }

    static void enqueuePatient() {
        Integer id = readInteger("Patient ID to enqueue: "); if (id == null) return;
        int index = findPatientIndex(id);
        if (index < 0) { System.out.println("Patient not found."); return; }
        if (statuses.get(index).equals("Served")) { System.out.println("A served patient cannot be enqueued."); return; }
        if (patientQueue.contains(id)) { System.out.println("That patient is already in the queue."); return; }
        patientQueue.offer(id);
        System.out.println(names.get(index) + " added to the end of the queue.");
    }

    static void dequeuePatient() {
        Integer id = patientQueue.poll();
        if (id == null) { System.out.println("The queue is empty; there is no patient to serve."); return; }
        int index = findPatientIndex(id);
        if (index >= 0) {
            statuses.set(index, "Served");
            System.out.println("Now serving " + names.get(index) + " (ID " + id + "). Status set to Served.");
        }
    }

    static void peekQueue() {
        Integer id = patientQueue.peek();
        if (id == null) { System.out.println("The queue is empty."); return; }
        int index = findPatientIndex(id);
        if (index >= 0) { System.out.println("Next patient: " + names.get(index) + " (ID " + id + ")"); }
        else System.out.println("The next queue entry refers to a missing patient.");
    }

    static void displayQueue() {
        if (patientQueue.isEmpty()) { System.out.println("The queue is empty."); return; }
        System.out.println("Queue, front to back (FIFO):");
        for (Integer id : patientQueue) {
            int index = findPatientIndex(id);
            if (index >= 0) System.out.println("ID " + id + " - " + names.get(index));
        }
    }

    static void checkQueueEmpty() {
        if (patientQueue.isEmpty()) System.out.println("Yes, the queue is empty.");
        else System.out.println("No, the queue has " + patientQueue.size() + " patient(s).");
    }
}
