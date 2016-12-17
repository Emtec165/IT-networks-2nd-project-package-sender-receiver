import java.math.BigInteger;

/**
 * Created by Kwiat Bombaju on 20.10.2016.
 */
public class PackageMaker {
    private String fragmentOfMessage;
    private int packageMaxLength, numberOfPackages, numberOfPackage;

    public PackageMaker(String fragmentOfMessage, int packageMaxLength, int numberOfPackages, int numberOfPackage){
        this.fragmentOfMessage = fragmentOfMessage;
        this.packageMaxLength = packageMaxLength;
        this.numberOfPackages = numberOfPackages;
        this.numberOfPackage = numberOfPackage;
    }

    public String makePackage(){
        String packageLength = String.format("%02X", (0xFF & packageMaxLength));

        String frame = String.format("%02X", (0xFF & numberOfPackage));
        frame += String.format("%02X", (0xFF & numberOfPackages));
        frame += packageLength;
        frame += String.format("%X", new BigInteger(1, fragmentOfMessage.getBytes(/*YOUR_CHARSET?*/)));
        return frame;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append("numberOfPackage: " + numberOfPackage + NEW_LINE);
        result.append("numberOfPackages: " + numberOfPackages + NEW_LINE);
        result.append("packageMaxLength: " + packageMaxLength + NEW_LINE);
        result.append("fragmentOfMessage: " + fragmentOfMessage + NEW_LINE);

        return result.toString();
    }
}
