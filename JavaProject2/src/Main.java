import java.util.Scanner;

public class Main {

    static int[] seets = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21}; 
    static long[] IDarr = new long[0]; 
    static long[] IDarrWindow = new long[0];
    static String[] names = new String[0];


    public static void main(String[] args) {

        final int totalSeets = 20;
        final int windowSeets = 10;
        final int normalSeets = 10;
        long ID;

        double priceAdult = 299.9;
        double priceChild = 149.9;
        double moneyEarned = 0;
        Scanner keyboard = new Scanner(System.in);
        boolean shouldBeClosed = false;

        System.out.print("\n");
        System.out.println("välkommen till den advancerade bokningsystemet för bussar!");
        System.out.println("----------------------------------------------------------");

    // render loop
    while(!shouldBeClosed) {
        System.out.println("välj ett alternativ, efter namnent ser du vad för input du ska ge för att få tillgång till den");
        System.out.print("\n");
        System.out.println("Boka en plats:  B");
        System.out.println("Avboka en plats: A");
        System.out.println("se tillgängliga platser:  S");
        System.out.println("visa vinsten på biljätter:  V");
        System.out.println("Avsluta programmet:  X");
        System.out.println("Sortera array: Y");
        System.out.print("input: ");
        String ans = keyboard.nextLine();

        if (ans.equalsIgnoreCase("B")) {
            while (true) {
                System.out.print("skriv ditt födelsedatum för att boka: ");
                String idText = keyboard.nextLine(); 
                ID = Long.parseLong(idText);

                System.err.println("\n\n\n\n\n");
                System.out.print("Vill du boka en vanlig plats eller fönster plats? Skriv N för vanlig plats eller W för fönster: ");
                ans = keyboard.nextLine();
                
                if (ans.equalsIgnoreCase("N")) {
                    if (ID < 20050101) {
                        assignSeetviaID(totalSeets, normalSeets, ID, windowSeets); // pushes user value to the array
                        moneyEarned += priceAdult;     
                    } else {
                        assignSeetviaID(totalSeets, normalSeets, ID, windowSeets);
                        moneyEarned += priceChild;
                    }
                } else if (ans.equalsIgnoreCase("W")) {
                    if (ID < 20050101) {
                        assignWindowSeetviaID(totalSeets, normalSeets, ID, windowSeets); // pushes user value to the array
                        moneyEarned += priceAdult;     
                    } else {
                        assignWindowSeetviaID(totalSeets, normalSeets, ID, windowSeets);
                        moneyEarned += priceChild;
                    }
                } else {
                    System.out.print("\n");
                    System.out.println("ERROR! inte gilltigt alternativ!");
                    System.out.print("\n");                    
                }

                System.out.println("\n\n\n\n\n");
                System.out.println("boka en till plats eller lämna? Skriv Y för att fortsätta!");
                System.out.print("input: ");    
                ans = keyboard.nextLine();
                
                if (ans.equalsIgnoreCase("Y")) {
                    continue;
                } else {
                    break;
                }
            }
        } else if (ans.equalsIgnoreCase("A")) {
            while (true) {
                System.out.print("Ange ditt personnummer för att avboka: ");
                String idText = keyboard.nextLine();
                // converts users ID to their birthdate for the cancellation of their reserv
                ID = Long.parseLong(idText);
                ID /= 10000;
                for (int i = 0; i < IDarr.length; i++) {
                    if (IDarr[i] == ID) {
                        IDarr = pop(IDarr, ID);
                        System.out.println("Din plats är nu avbokat tack för din tid!");
                    }
                    if (i == IDarr.length && IDarr[i] != ID) {
                        System.out.println("Finns ingen plats som är bokat under dena personnummer");
                    }
                }

                System.out.println("\n\n\n\n\n");
                System.out.println("Avboka flera platser eller lämna? Skriv Y för att förtsätta!");
                System.out.print("Input:");
                ans = keyboard.nextLine();

                if (ans.equalsIgnoreCase("Y")) {
                    continue;
                } else {
                    break;
                }
            }

        } else if (ans.equalsIgnoreCase("S")) {
            seetAmount();
            System.out.println("finns just nu " + totalSeets + " platser kvar!");
        } else if (ans.equalsIgnoreCase("V")) {
            System.out.println(moneyEarned);
        } else if (ans.equalsIgnoreCase("X")) {
            shouldBeClosed = true;
        } else if (ans.equalsIgnoreCase("S")) {
            concat(IDarrWindow, IDarr);
            sort(IDarr, normalSeets);
            System.out.println("Array sorterad!");
        } else {
            System.out.print("\n");
            System.out.println("ERROR! inte gilltigt alternativ!");
            System.out.print("\n");
            }
        }
        keyboard.close();

        }

    // draws a diagram of the bus seet layout 
    static void seetAmount() {
        System.out.println("------------------------");
            for (int i = 0; i < seets.length - 1; i += 4) {
                System.out.println(" | " + seets[i] + " | " + seets[i + 1] + " | " + "\t" + " | " + seets[i + 2] + " | " + seets[i + 3] + " | ");
        }
        System.out.println("------------------------");
    }

    // will assign a seet based of the user input
    static void assignSeetviaID(int totalSeets, int normalSeets, long ID, int windowSeets) {
        if (totalSeets > 0 && normalSeets > 0) {
            IDarr = push(IDarr, ID);
            totalSeets--;
            normalSeets--;
        } else if (normalSeets == 0 && windowSeets > 0) {
            System.out.println("Tyvär har vi slut på icke fönster platser. Men det finns " + windowSeets + " fönster platser att boka kvar!");
        } else {
            System.out.println("Det finns inga tillgängliga platser kvar. Tack för din tid");
        }
        System.out.println(totalSeets);
    }

    
    // will assign a window seet based of the user input
    static void assignWindowSeetviaID(int totalSeets, int normalSeets, long ID, int windowSeets) {
        if (totalSeets > 0 && windowSeets > 0) {
            IDarrWindow = push(IDarr, ID);
            totalSeets--;
            windowSeets--;
        } else if (windowSeets == 0 && windowSeets > 0) {
            System.out.println("Tyvär har vi slut på fönster platser. Men det finns " + normalSeets + " vanliga platser att boka kvar!");
        } else {
            System.out.println("Det finns inga tillgängliga platser kvar. Tack för din tid");            
        }
    }

    // due to java arrays being static. A custom push function has been implemented
    static long[] push(long[] arr, long push) {
        long[] longArr = new long[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            longArr[i] = arr[i];
        }
        longArr[arr.length] = push;
        return longArr;
    }

    // same as push just removes the desired element instead of pushing a new one
    static long[] pop(long[] arr, long index) {
        // makes sure we do not for example try to access a negative index or going out of bounds
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        long[] shortArr = new long[arr.length - 1];
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            shortArr[j++] = arr[i];
        }
        return shortArr;    
    }

    // ----------------------------------------------------------------------------
    // serves same purpose as previous push but is used for names
    static String[] pushStr(String[] arr, String push) {
        String[] longArr = new String[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            longArr[i] = arr[i];
        }
        longArr[arr.length] = push;
        return longArr;
    }

    // same as long pop
    static String[] popStr(String[] arr, int index) {
        // makes sure we do not for example try to access a negative index or going out of bounds
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        String[] shortArr = new String[arr.length - 1];
        for (int i = 0, j = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            shortArr[j++] = arr[i];
        }
        return shortArr;    
    }

    //------------------------------------------------------------------------------

    static void sort(long[] arr, int n) {
        long temp;
        boolean isSwapped;
        for (int i = 0; i < n; i++) {
            isSwapped = false;
            for (int j = 0; j < n - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSwapped = true;
                }
            }
            // if no swapping occured break the loop
            if (!isSwapped) {
                break;
            }
    
        }

    }


    static long[] concat(long[] a, long[] b) {

        int length = a.length + b.length;

        long[] result = new long[length];
        int pos = 0;
        for (int i = 0; i < a.length; i++) {
            result[pos] = i;
            pos++;
        }

        for (int i = 0; i < b.length; i++) {
            result[pos] = i;
            pos++;
        }

        return result;
    }
}


