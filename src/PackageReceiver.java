import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Kwiat Bombaju on 20.10.2016.
 */
public class PackageReceiver {
    private List<String> packageList = new ArrayList<>();
    private String decodedSortedMessage;

    public PackageReceiver(List<String> unsortedPackageList){
        this.packageList = unsortedPackageList;
        this.decodedSortedMessage = "";
        System.out.println("Decoding procedure begin:");
    }

    public boolean doesPackageDecodedCountEqualToPackagesCount(){
        String hexInt = packageList.get(0).substring(2,4);
        int readPackagesCount = Integer.parseInt(hexInt, 16);
        int actualPackagesCount = packageList.size();

        System.out.println("Decoded number of packages: " + readPackagesCount);
        System.out.println("Actual number of packages: " + actualPackagesCount);

        if (readPackagesCount == actualPackagesCount){
            return true;
        }
        else {
            return false;
        }
    }

    public int whichPackageNrIsMissing(){
        int missingPackageNr = 1;
        for (int i = 0; i < packageList.size(); i++){
            String hexInt = packageList.get(i).substring(0,2);
            int packageNr = Integer.parseInt(hexInt, 16);
            if (packageNr == missingPackageNr){
                missingPackageNr++;
            }
            else{
                break;
            }
        }
        return missingPackageNr;
    }

    public void addMissingPackageToCollection(String a){
        packageList.add(a);
    }

    public void sortPackages(){
        Collections.sort(packageList, new PackageNumberComparator());

        System.out.println("Sorted received packages:");
        for (int i = 0; i < packageList.size(); i++){
            System.out.println(packageList.get(i));
        }
        System.out.println();
    }


    public void readPackages(){
        for (int i = 0; i < packageList.size(); i++){
            System.out.print("Content of " + (i + 1) + " package: ");
            String packageHexString = packageList.get(i).substring(6, packageList.get(i).length());
            System.out.println(packageHexString);
            String decodedPackageHexString = unHex(packageHexString);

            decodedSortedMessage += decodedPackageHexString;
        }
        System.out.println("Decoded message: " + decodedSortedMessage);
    }

    private static String unHex(String arg) {
        String str = "";
        for(int i=0;i<arg.length();i+=2)
        {
            String s = arg.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            str = str + (char) decimal;
        }
        return str;
    }
}
