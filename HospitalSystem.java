import java.util.Scanner;

// 1. Visit History - Singly Linked List Node & Class
class VisitNode {
    int visitId;
    String visitDate;
    String doctorName;
    String diagnosis;
    String treatment;
    VisitNode next;

    public VisitNode(int visitId, String visitDate, String doctorName, String diagnosis, String treatment) {
        this.visitId = visitId;
        this.visitDate = visitDate;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.next = null;
    }
}

class VisitHistoryLinkedList {
    private VisitNode head;

    public void addVisit(int visitId, String date, String doctor, String diagnosis, String treatment) {
        VisitNode newVisit = new VisitNode(visitId, date, doctor, diagnosis, treatment);
        if (head == null) {
            head = newVisit;
        } else {
            VisitNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newVisit;
        }
        System.out.println("Visit added successfully!");
    }

    public void removeVisit(int visitId) {
        if (head == null) {
            System.out.println("No visit history found.");
            return;
        }
        if (head.visitId == visitId) {
            head = head.next;
            System.out.println("Visit ID " + visitId + " removed.");
            return;
        }
        VisitNode current = head;
        VisitNode prev = null;
        while (current != null && current.visitId != visitId) {
            prev = current;
            current = current.next;
        }
        if (current == null) {
            System.out.println("Visit ID not found.");
        } else {
            prev.next = current.next;
            System.out.println("Visit ID " + visitId + " removed.");
        }
    }

    public void searchVisit(int visitId) {
        VisitNode temp = head;
        while (temp != null) {
            if (temp.visitId == visitId) {
                System.out.println("Found Visit -> ID: " + temp.visitId + " | Date: " + temp.visitDate +
                        " | Doctor: " + temp.doctorName + " | Diagnosis: " + temp.diagnosis + " | Treatment: " + temp.treatment);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Visit ID not found.");
    }

    public void displayVisits() {
        if (head == null) {
            System.out.println("No visit history available.");
            return;
        }
        VisitNode temp = head;
        System.out.println("\n--- Patient Visit History ---");
        while (temp != null) {
            System.out.println("Visit ID: " + temp.visitId + " | Date: " + temp.visitDate +
                    " | Doctor: " + temp.doctorName + " | Diagnosis: " + temp.diagnosis + " | Treatment: " + temp.treatment);
            temp = temp.next;
        }
    }
}

// 2. Patient Record - BST Node & Class
class PatientNode {
    int patientId;
    String name;
    int age;
    String contact;
    String medicalCondition;
    VisitHistoryLinkedList visitHistory;
    PatientNode left, right;

    public PatientNode(int patientId, String name, int age, String contact, String medicalCondition) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.medicalCondition = medicalCondition;
        this.visitHistory = new VisitHistoryLinkedList();
        this.left = this.right = null;
    }
}

class PatientBST {
    private PatientNode root;

    public void insert(int id, String name, int age, String contact, String condition) {
        root = insertRec(root, id, name, age, contact, condition);
    }

    private PatientNode insertRec(PatientNode root, int id, String name, int age, String contact, String condition) {
        if (root == null) {
            return new PatientNode(id, name, age, contact, condition);
        }
        if (id < root.patientId) {
            root.left = insertRec(root.left, id, name, age, contact, condition);
        } else if (id > root.patientId) {
            root.right = insertRec(root.right, id, name, age, contact, condition);
        } else {
            System.out.println("Patient ID already exists!");
        }
        return root;
    }

    public PatientNode search(int id) {
        return searchRec(root, id);
    }

    private PatientNode searchRec(PatientNode root, int id) {
        if (root == null || root.patientId == id) {
            return root;
        }
        if (id < root.patientId) {
            return searchRec(root.left, id);
        }
        return searchRec(root.right, id);
    }

    public void delete(int id) {
        root = deleteRec(root, id);
    }

    private PatientNode deleteRec(PatientNode root, int id) {
        if (root == null) return root;

        if (id < root.patientId) {
            root.left = deleteRec(root.left, id);
        } else if (id > root.patientId) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;

            root.patientId = minValue(root.right);
            root.right = deleteRec(root.right, root.patientId);
        }
        return root;
    }

    private int minValue(PatientNode root) {
        int minv = root.patientId;
        while (root.left != null) {
            minv = root.left.patientId;
            root = root.left;
        }
        return minv;
    }

    public void inOrder() {
        System.out.println("\n--- Registered Patients (In-Order Traversal) ---");
        inOrderRec(root);
    }

    private void inOrderRec(PatientNode root) {
        if (root != null) {
            inOrderRec(root.left);
            System.out.println("ID: " + root.patientId + " | Name: " + root.name + " | Age: " + root.age +
                    " | Contact: " + root.contact + " | Condition: " + root.medicalCondition);
            inOrderRec(root.right);
        }
    }
}

// 3. Emergency Queue (FIFO)
class QueueNode {
    PatientNode patient;
    QueueNode next;

    public QueueNode(PatientNode patient) {
        this.patient = patient;
        this.next = null;
    }
}

class EmergencyQueue {
    private QueueNode front, rear;

    public void enqueue(PatientNode patient) {
        QueueNode newNode = new QueueNode(patient);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        System.out.println("Patient " + patient.name + " added to Emergency Queue.");
    }

    public PatientNode dequeue() {
        if (front == null) {
            System.out.println("Emergency Queue is empty!");
            return null;
        }
        PatientNode temp = front.patient;
        front = front.next;
        if (front == null) rear = null;
        return temp;
    }

    public void displayQueue() {
        if (front == null) {
            System.out.println("No patients currently in Emergency Queue.");
            return;
        }
        System.out.println("\n--- Emergency Waiting Queue ---");
        QueueNode temp = front;
        while (temp != null) {
            System.out.println("Patient ID: " + temp.patient.patientId + " | Name: " + temp.patient.name + " | Condition: " + temp.patient.medicalCondition);
            temp = temp.next;
        }
    }
}

// 4. Treatment History Stack (LIFO)
class TreatmentNode {
    int patientId;
    String patientName;
    String treatmentDetails;
    TreatmentNode next;

    public TreatmentNode(int patientId, String patientName, String treatmentDetails) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.treatmentDetails = treatmentDetails;
        this.next = null;
    }
}

class TreatmentStack {
    private TreatmentNode top;

    public void push(int patientId, String patientName, String treatmentDetails) {
        TreatmentNode newNode = new TreatmentNode(patientId, patientName, treatmentDetails);
        newNode.next = top;
        top = newNode;
        System.out.println("Treatment record saved for " + patientName);
    }

    public void pop() {
        if (top == null) {
            System.out.println("Treatment Stack is empty!");
            return;
        }
        System.out.println("Removed Most Recent Treatment: Patient ID " + top.patientId + " | Name: " + top.patientName);
        top = top.next;
    }

    public void displayStack() {
        if (top == null) {
            System.out.println("No treatment records in Stack.");
            return;
        }
        System.out.println("\n--- Treatment History Stack (Recent First) ---");
        TreatmentNode temp = top;
        while (temp != null) {
            System.out.println("Patient ID: " + temp.patientId + " | Name: " + temp.patientName + " | Treatment: " + temp.treatmentDetails);
            temp = temp.next;
        }
    }
}

// Main Interactive System
public class HospitalSystem {
    public static void main(String[] args) {
        PatientBST bst = new PatientBST();
        EmergencyQueue queue = new EmergencyQueue();
        TreatmentStack stack = new TreatmentStack();
        Scanner scanner = new Scanner(System.in);

        // Pre-populating sample data
        bst.insert(102, "Kamal Perera", 45, "0771234567", "Fever");
        bst.insert(101, "Nimal Jayasinghe", 30, "0719876543", "Fracture");
        bst.insert(103, "Sunil Fernando", 50, "0751122334", "Chest Pain");

        while (true) {
            System.out.println("\n=============================================");
            System.out.println(" MINI HOSPITAL EMERGENCY MANAGEMENT SYSTEM ");
            System.out.println("=============================================");
            System.out.println("1. Register Patient (BST Insert)");
            System.out.println("2. Search Patient (BST Search)");
            System.out.println("3. Delete Patient (BST Delete)");
            System.out.println("4. Display All Patients (BST In-Order)");
            System.out.println("5. Add to Emergency Queue (Queue Enqueue)");
            System.out.println("6. Treat Next Patient (Queue Dequeue)");
            System.out.println("7. Display Emergency Queue");
            System.out.println("8. Complete Treatment (Stack Push)");
            System.out.println("9. Remove Last Treatment (Stack Pop)");
            System.out.println("10. Display Treatment History (Stack)");
            System.out.println("11. Manage Patient Visit History (Singly Linked List)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Patient ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Contact: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter Medical Condition: ");
                    String cond = scanner.nextLine();
                    bst.insert(id, name, age, contact, cond);
                    System.out.println("Patient registered successfully!");
                    break;
                case 2:
                    System.out.print("Enter Patient ID to Search: ");
                    int sId = scanner.nextInt();
                    PatientNode found = bst.search(sId);
                    if (found != null) {
                        System.out.println("Patient Found -> Name: " + found.name + " | Condition: " + found.medicalCondition);
                    } else {
                        System.out.println("Patient NOT found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Patient ID to Delete: ");
                    int dId = scanner.nextInt();
                    bst.delete(dId);
                    System.out.println("Patient record processed/deleted.");
                    break;
                case 4:
                    bst.inOrder();
                    break;
                case 5:
                    System.out.print("Enter Patient ID to Enqueue: ");
                    int qId = scanner.nextInt();
                    PatientNode qPatient = bst.search(qId);
                    if (qPatient != null) {
                        queue.enqueue(qPatient);
                    } else {
                        System.out.println("Patient not registered! Register first.");
                    }
                    break;
                case 6:
                    PatientNode treated = queue.dequeue();
                    if (treated != null) {
                        System.out.println("Calling for Treatment -> Patient ID: " + treated.patientId + " | Name: " + treated.name);
                    }
                    break;
                case 7:
                    queue.displayQueue();
                    break;
                case 8:
                    System.out.print("Enter Patient ID for Completed Treatment: ");
                    int tId = scanner.nextInt();
                    scanner.nextLine();
                    PatientNode tPatient = bst.search(tId);
                    if (tPatient != null) {
                        System.out.print("Enter Treatment Details: ");
                        String details = scanner.nextLine();
                        stack.push(tPatient.patientId, tPatient.name, details);
                    } else {
                        System.out.println("Patient ID not found.");
                    }
                    break;
                case 9:
                    stack.pop();
                    break;
                case 10:
                    stack.displayStack();
                    break;
                case 11:
                    System.out.print("Enter Patient ID to Manage Visit History: ");
                    int vId = scanner.nextInt();
                    PatientNode vPatient = bst.search(vId);
                    if (vPatient != null) {
                        System.out.println("1. Add Visit | 2. Remove Visit | 3. Search Visit | 4. Display Visits");
                        int vChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (vChoice == 1) {
                            System.out.print("Visit ID: "); int visId = scanner.nextInt(); scanner.nextLine();
                            System.out.print("Date: "); String date = scanner.nextLine();
                            System.out.print("Doctor Name: "); String doc = scanner.nextLine();
                            System.out.print("Diagnosis: "); String diag = scanner.nextLine();
                            System.out.print("Treatment: "); String trt = scanner.nextLine();
                            vPatient.visitHistory.addVisit(visId, date, doc, diag, trt);
                        } else if (vChoice == 2) {
                            System.out.print("Enter Visit ID to Remove: "); int remId = scanner.nextInt();
                            vPatient.visitHistory.removeVisit(remId);
                        } else if (vChoice == 3) {
                            System.out.print("Enter Visit ID to Search: "); int srcId = scanner.nextInt();
                            vPatient.visitHistory.searchVisit(srcId);
                        } else if (vChoice == 4) {
                            vPatient.visitHistory.displayVisits();
                        }
                    } else {
                        System.out.println("Patient ID not found.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting System. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }
}