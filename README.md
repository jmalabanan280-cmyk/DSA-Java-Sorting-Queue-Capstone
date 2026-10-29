# Clinic Patient Queue System

## Project Description

This console program helps a clinic keep a simple patient register and manage the order in which waiting patients are served. Staff can record patient details, find and update records, and use a FIFO queue to track the waiting line. Manual sorting makes it possible to organize the displayed records by name, age, or priority without changing the service order.

## Objectives

- Practice parallel `ArrayList` data storage and synchronized record operations.
- Implement Bubble Sort and Insertion Sort manually.
- Use a Java `Queue<Integer>` to model a first-in, first-out clinic line.
- Validate console input and explain each operation with readable messages.

## Use Cases and Features

- Add a patient with an automatically assigned ID, name, age, condition, and priority.
- Display all patient records and search by ID or partial name.
- Update or delete a record; deleting a queued patient also removes that ID from the queue.
- Sort the record list by name, age, or priority with either manual algorithm.
- Enqueue a waiting patient once, inspect the next patient, display the line, serve the next patient, and check whether the line is empty.
- Served patients are marked `Served` and cannot be enqueued again. Priority describes the record; the queue itself remains FIFO.

## Data Structure Explanation

Six parallel `ArrayList`s store patient IDs, names, ages, conditions, priorities, and statuses. The value at the same index in each list belongs to one patient. Add, update, delete, and `swapRecords` operations keep those positions aligned. IDs increase automatically and are not recycled after deletion.

The `Queue<Integer>` is backed by a `LinkedList` and contains patient IDs, not list indexes. This lets records move during sorting while their queue references remain valid. `offer` adds at the back; `poll` serves from the front; `peek` reads the front without removing it. Sorting never edits the queue.

## Sorting Algorithm Explanation

### Bubble Sort

Bubble Sort compares each pair of neighboring records and swaps them when they are in the wrong order. After each pass, a larger value for the selected field has moved toward the end. It is simple to trace for a small clinic list. The program prints the records before and after the sort and swaps every field together.

### Insertion Sort

Insertion Sort grows a sorted section from left to right. For each new record, it swaps that record left while the preceding record should come after it. This is easy to follow for a short list that is already partly ordered. The program prints the records before and after and keeps all fields together.

Both algorithms can sort by name (case-insensitive), age, or priority number. Priority 1 sorts before 2 and 3. Equal sort keys keep their existing relative order. Neither algorithm changes the FIFO queue.

## How to Run the Program

From the project root, compile and launch with a JDK installed:

```text
javac src/Main.java
java -cp src Main
```

Choose a menu number and press Enter. Numeric prompts accept whole numbers; names and conditions are read as complete lines. Choose `0` to exit.

## Screenshots of the System

The screenshots folder is intentionally empty apart from `.gitkeep`. Capture genuine images from your own running program and save them using these exact filenames:

1. `screenshots/main-menu.png` — show the full menu and the application title.
2. `screenshots/add-and-display-records.png` — add at least two patients, then display the records and their generated IDs.
3. `screenshots/queue-operations.png` — show enqueue, peek, queue display, dequeue, and empty check operations.
4. `screenshots/fifo-behavior.png` — enqueue two patients in a known order, display the queue, serve once, and show that the first patient was served.
5. `screenshots/sorting-algorithm-1.png` — choose Bubble Sort and show its before and after records.
6. `screenshots/sorting-algorithm-2.png` — choose Insertion Sort and show its before and after records.
7. `screenshots/input-validation.png` — show an invalid menu choice and rejected values such as a blank name, nonnumeric age, out-of-range age, and invalid priority.

### Capture checklist

- [ ] `main-menu.png`: start the app and capture the menu.
- [ ] `add-and-display-records.png`: add multiple records, then choose Display all patients.
- [ ] `queue-operations.png`: enqueue a patient; use peek, display, dequeue, and check-empty options.
- [ ] `fifo-behavior.png`: enqueue two different IDs in order; display, serve once, then display the remaining queue.
- [ ] `sorting-algorithm-1.png`: choose Bubble Sort, select a sort field, and capture the before/after output.
- [ ] `sorting-algorithm-2.png`: choose Insertion Sort, select a sort field, and capture the before/after output.
- [ ] `input-validation.png`: enter an invalid menu choice and invalid patient values, capturing the friendly messages.
