import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class VaccinationCenter {
    static Scanner input = new Scanner(System.in);
    static int numOfVaccinations = 150;
    static LinkedList<Booth> waitingList = new LinkedList<>();

    public static void main(String[] args) {
        Booth[] vaccinationCenter = new Booth[6];

        initialise(vaccinationCenter);

        while (true) {
            System.out.println("\n_________________________MENU_________________________\n" +
                    "100 or VVB: View all Vaccination Booths\n" +
                    "101 or VEB: View all Empty Booths\n" +
                    "102 or APB: Add Patient to a Booth\n" +
                    "103 or RPB: Remove Patient from a Booth\n" +
                    "104 or VPS: View Patients Sorted in alphabetical order\n" +
                    "105 or SPD: Store Program Data into file\n" +
                    "106 or LPD: Load Program Data from file\n" +
                    "107 or VRV: View Remaining Vaccinations\n" +
                    "108 or AVS: Add Vaccinations to the Stock\n" +
                    "999 or EXT: Exit the Program\n");

            System.out.print("Enter your preferred option : ");
            String choice = input.nextLine().toUpperCase();

            if (choice.equals("100") || choice.equals("VVB")) {
                viewAllBooths(vaccinationCenter);
            } else if (choice.equals("101") || choice.equals("VEB")) {
                viewEmptyBooths(vaccinationCenter);
            } else if (choice.equals("102") || choice.equals("APB")) {
                addPatient(vaccinationCenter);
            } else if (choice.equals("103") || choice.equals("RPB")) {
                removePatient(vaccinationCenter);
            } else if (choice.equals("104") || choice.equals("VPS")) {
                sortedPatients(vaccinationCenter);
            } else if (choice.equals("105") || choice.equals("SPD")) {
                storeProgramData(vaccinationCenter);
            } else if (choice.equals("106") || choice.equals("LPD")) {
                loadProgramData();
            } else if (choice.equals("107") || choice.equals("VRV")) {
                remainingVaccinations();
            } else if (choice.equals("108") || choice.equals("AVS")) {
                addVaccinations();
            } else if (choice.equals("999") || choice.equals("EXT")) {
                break;
            } else {
                System.out.print("\nYou have entered an invalid option.\n");
            }
        }
    }

    private static void initialise(Booth[] vaccinationCenterRef) {
        for (int x = 0; x < 6; x++ ) {
            if (x == 0 || x == 1) {
                vaccinationCenterRef[x] = new Booth("e", "AstraZeneca");
            }else if (x == 2 || x == 3) {
                vaccinationCenterRef[x] = new Booth("e", "Sinopharm");
            }else {
                vaccinationCenterRef[x] = new Booth("e", "Pfizer");
            }
        }
    }

    public static void viewAllBooths(Booth[] vaccinationCenter) {
        System.out.println("\nView all Vaccination Booths\n");

        for (int x = 0; x < vaccinationCenter.length; x++ ) {
            if (vaccinationCenter[x].getCustomerName().equals("e")) {
                System.out.println("Booth " + x + " is empty");
            } else {
                System.out.println("Booth " + x + " is occupied by " + vaccinationCenter[x].getCustomerName());
            }
        }
    }

    public static void viewEmptyBooths(Booth[] vaccinationCenter) {
        System.out.println("\nView all Empty Booths\n");

        for (int x = 0; x < vaccinationCenter.length; x++ ) {
            if (vaccinationCenter[x].getCustomerName().equals("e")) {
                System.out.println("Booth " + x + " is empty");
            }
        }
    }

    public static int checkEmptyBooths(Booth[] vaccinationCenter) {
        int count = 0;

        for (Booth patientName : vaccinationCenter) {
            if (patientName.getCustomerName().equals("e")) {
                count += 1;
            }
        }

        return count;
    }

    public static void addPatient(Booth[] vaccinationCenter) {
        System.out.println("\nAdd Patient to a Booth\n");

        while (true) {
            if (numOfVaccinations == 20) {
                System.out.println("*******************************************");
                System.out.println("*****WARNING ONLY 20 VACCINATIONS LEFT*****");
                System.out.println("*******************************************\n");
            } else if (numOfVaccinations == 0) {
                System.out.println("There are no vaccinations left.");
                break;
            }

            int boothNum;

            System.out.print("Enter patient's name : ");
            String customerName = input.next();
            input.nextLine();

            System.out.print("Enter the vaccination patient wants : ");
            String vaccinationRequested = input.next();
            input.nextLine();

            if (vaccinationRequested.toUpperCase().equals("ASTRAZENECA")) {
                if (vaccinationCenter[0].getCustomerName().equals("e")) {
                    boothNum = 0;
                } else if (vaccinationCenter[1].getCustomerName().equals("e")) {
                    boothNum = 1;
                } else {
                    boothNum = -1;
                }
            } else if (vaccinationRequested.toUpperCase().equals("SINOPHARM")) {
                if (vaccinationCenter[2].getCustomerName().equals("e")) {
                    boothNum = 2;
                } else if (vaccinationCenter[3].getCustomerName().equals("e")) {
                    boothNum = 3;
                } else {
                    boothNum = -1;
                }
            } else if (vaccinationRequested.toUpperCase().equals("PFIZER")) {
                if (vaccinationCenter[4].getCustomerName().equals("e")) {
                    boothNum = 4;
                } else if (vaccinationCenter[5].getCustomerName().equals("e")) {
                    boothNum = 5;
                } else {
                    boothNum = -1;
                }
            } else {
                System.out.println("You have entered an invalid vaccination.\n");
                continue;
            }

            System.out.print("Enter patient's first name : ");
            String firstName = input.next();
            input.nextLine();

            System.out.print("Enter patient's surname : ");
            String surname = input.next();
            input.nextLine();

            System.out.print("Enter patient's age : ");
            int age = input.nextInt();
            input.nextLine();

            System.out.print("Enter patient's city : ");
            String city = input.next();
            input.nextLine();

            System.out.print("Enter patient's NIC or Passport Number : ");
            int uNum = input.nextInt();
            input.nextLine();

            int emptyBooths = checkEmptyBooths(vaccinationCenter);

            if (emptyBooths == 0 || boothNum == -1)  {
                System.out.println("\nAll the reserved booths are full for the requested vaccination. Patient will be added to the waiting list.");

                Booth waitingPatient = new Booth(customerName, vaccinationRequested);

                waitingPatient.setFirstName(firstName);
                waitingPatient.setSurname(surname);
                waitingPatient.setAge(age);
                waitingPatient.setCity(city);
                waitingPatient.setNumNP(uNum);

                waitingList.add(waitingPatient);

                System.out.println("\nPatient added to waiting list ");
            } else {

                vaccinationCenter[boothNum].setCustomerName(customerName);
                vaccinationCenter[boothNum].setFirstName(firstName);
                vaccinationCenter[boothNum].setSurname(surname);
                vaccinationCenter[boothNum].setAge(age);
                vaccinationCenter[boothNum].setCity(city);
                vaccinationCenter[boothNum].setNumNP(uNum);

                System.out.println("\nPatient added successfully to booth " + boothNum);
            }

            numOfVaccinations -= 1;

            break;
        }
    }

    public static void removePatient(Booth[] vaccinationCenter) {
        System.out.println("\nRemove Patient from a Booth\n");

        while (true) {
            System.out.print("Enter the patient's booth number (0-5) or 6 to stop : ");
            int boothNum = input.nextInt();
            input.nextLine();

            if (boothNum == 6) {
                break;
            }

            if (boothNum < 0 || boothNum > 5) {
                System.out.println("You have entered an invalid booth number.\n");
                continue;
            }

            System.out.println("Patient removed successfully from booth " + boothNum);
            vaccinationCenter[boothNum].setCustomerName("e");

            System.out.println();

            if (waitingList.isEmpty() == false) {
                int count = 0;

                for(Booth waitingPatient : waitingList) {
                    String removedBooth = waitingPatient.getvRequested().toUpperCase();
                    String waitingBooth = vaccinationCenter[boothNum].getvRequested().toUpperCase();

                    if (removedBooth.equals(waitingBooth)) {
                        vaccinationCenter[boothNum].setCustomerName(waitingPatient.getCustomerName());
                        vaccinationCenter[boothNum].setFirstName(waitingPatient.getFirstName());
                        vaccinationCenter[boothNum].setSurname(waitingPatient.getSurname());
                        vaccinationCenter[boothNum].setAge(waitingPatient.getAge());
                        vaccinationCenter[boothNum].setCity(waitingPatient.getCity());
                        vaccinationCenter[boothNum].setNumNP(waitingPatient.getNumNP());

                        System.out.println("Patient " + waitingPatient.getCustomerName() + " was added to booth " + boothNum + " from the waiting list.");
                        System.out.println();

                        waitingList.remove(count);
                        break;
                    }

                    count += 1;
                }
            }
        }
    }

    public static void sortedPatients(Booth[] vaccinationCenter) {
        System.out.println("\nView Patients Sorted in alphabetical order\n");

        String[] vaccinationCenterRef = new String[6];

        for (int x = 0; x < vaccinationCenter.length; x++) {
            vaccinationCenterRef[x] = vaccinationCenter[x].getCustomerName();
        }

        boolean notSwitched = true;

        while (notSwitched) {
            notSwitched = false;
            for (int i = 0; i < vaccinationCenterRef.length - 1; i++) {
                if (vaccinationCenterRef[i].compareTo(vaccinationCenterRef[i + 1]) > 0) {
                    String temp = vaccinationCenterRef[i + 1];
                    vaccinationCenterRef[i + 1] = vaccinationCenterRef[i];
                    vaccinationCenterRef[i] = temp;
                    notSwitched = true;
                }
            }
        }

        for (String patientName : vaccinationCenterRef) {
            if (!(patientName.equals("e"))) {
                System.out.println(patientName);
            }
        }
    }

    public static void storeProgramData(Booth[] vaccinationCenter) {
        System.out.println("\nStore Program Data into file\n");

        try {
            FileWriter sampleData = new FileWriter("src/vaccinationCenter.txt");

            for (int x = 0; x < vaccinationCenter.length; x++ ) {
                if (vaccinationCenter[x].getCustomerName().equals("e")) {
                    sampleData.write("Booth " + x + "\nempty\n\n");
                } else {
                    sampleData.write("Booth " + x +
                            "\nPatient Name : " + vaccinationCenter[x].getCustomerName() +
                            "\nFirst Name : " + vaccinationCenter[x].getFirstName() +
                            "\nSurname : " + vaccinationCenter[x].getSurname() +
                            "\nAge : " + vaccinationCenter[x].getAge() +
                            "\nCity : " + vaccinationCenter[x].getCity() +
                            "\nNIC or Passport Number : " + vaccinationCenter[x].getNumNP() +
                            "\nVaccination Requested : " + vaccinationCenter[x].getvRequested() + "\n\n");
                }
            }

            sampleData.close();
            System.out.println("Data stored successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while storing data.");
        }
    }

    public static void loadProgramData() {
        System.out.println("\nLoad Program Data from file\n");

        try {
            File sampleData = new File("src/vaccinationCenter.txt");
            Scanner readData = new Scanner(sampleData);

            while (readData.hasNextLine()) {
                System.out.println(readData.nextLine());
            }
            readData.close();
            System.out.println("Data loaded successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading data.");
        }
    }

    public static void remainingVaccinations() {
        System.out.println("\nView Remaining Vaccinations\n");

        System.out.println("There are " + numOfVaccinations + " vaccinations remaining.");
    }

    public static void addVaccinations() {
        System.out.println("\nAdd Vaccinations to the Stock\n");

        while(true) {
            System.out.print("Enter the number of vaccinations to be added to the stock : ");
            int newVaccines = input.nextInt();

            if (numOfVaccinations + newVaccines <= 150) {
                System.out.println("Vaccinations successfully added to the stock.");
                numOfVaccinations += newVaccines;
                break;
            } else {
                System.out.println("Maximum you can add is " + (150 - numOfVaccinations));
                System.out.println();
            }
        }
    }
}
