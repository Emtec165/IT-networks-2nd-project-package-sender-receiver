
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Kwiat Bombaju on 20.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        Random generator = new Random();
        String inputMessage = "";
        int packageMaxLength, numberOfPackages, lostPackage;
        boolean doesPackageDecodedCountEqualToPackagesCount = false;
        List<String> listOfPackages = new ArrayList<>();
        List<String> listOfMixedPackages = new ArrayList<>();
        List<String> listOfBackupPackages = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("inputMessage.txt"))){
            inputMessage = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Define max length of package:");
        packageMaxLength = userInput.nextInt();
        userInput.nextLine(); //TO FIX USER INPUT SKIP ISSUE

        numberOfPackages = (int) Math.ceil((double) inputMessage.length() / (double) packageMaxLength);

        for (int i = 0; i < numberOfPackages; i++) {
            int startIndex = 0 + packageMaxLength * i;
            int endIndex = packageMaxLength + packageMaxLength * i;
            if (endIndex >= inputMessage.length()) {
                endIndex = inputMessage.length();
            }
            PackageMaker newPackage = new PackageMaker(inputMessage.substring(startIndex, endIndex), packageMaxLength, numberOfPackages, i + 1);
            listOfPackages.add(newPackage.makePackage());
            listOfBackupPackages.add(newPackage.makePackage());
        }

        System.out.println("Created collection:");
        for (int i = 0; i < listOfPackages.size(); i++) {
            System.out.println(listOfPackages.get(i));
        }
        System.out.println();

        while (listOfPackages.size() > 0) {
            int randomIndex = generator.nextInt(listOfPackages.size());
            listOfMixedPackages.add(listOfPackages.get(randomIndex));
            listOfPackages.remove(randomIndex);
        }

        System.out.println("Mixed collection:");
        for (int i = 0; i < listOfMixedPackages.size(); i++) {
            System.out.println(listOfMixedPackages.get(i));
        }
        System.out.println();


        int shouldDeletePackage = generator.nextInt(listOfMixedPackages.size() / 4);
        while (shouldDeletePackage != 0) {
            int randomPackageIndex = generator.nextInt(listOfMixedPackages.size());
            listOfMixedPackages.remove(randomPackageIndex);
            System.out.println("Upss... Package nr " + (randomPackageIndex + 1) + " was lost in stream...\n");
            shouldDeletePackage--;
        }

        PackageReceiver packageReceiver = new PackageReceiver(listOfMixedPackages);
        packageReceiver.sortPackages();
        doesPackageDecodedCountEqualToPackagesCount = packageReceiver.doesPackageDecodedCountEqualToPackagesCount();

        while (doesPackageDecodedCountEqualToPackagesCount != true) {
            System.out.println("Numbers don't match");

            int whichPackageNrIsMissing = packageReceiver.whichPackageNrIsMissing();
            System.out.println("Package nr " + whichPackageNrIsMissing + " is missing");
            packageReceiver.addMissingPackageToCollection(listOfBackupPackages.get(whichPackageNrIsMissing - 1));
            packageReceiver.sortPackages();

            doesPackageDecodedCountEqualToPackagesCount = packageReceiver.doesPackageDecodedCountEqualToPackagesCount();
        }
        System.out.println("Numbers match\n");
        packageReceiver.readPackages();
    }
}
