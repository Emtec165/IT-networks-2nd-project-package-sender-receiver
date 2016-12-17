import java.util.Comparator;

/**
 * Created by Kwiat Bombaju on 17.12.2016.
 */
class PackageNumberComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        String hexIntA = a.substring(0,2);
        String hexIntB = b.substring(0,2);
        int intA = Integer.parseInt(hexIntA, 16);
        int intB = Integer.parseInt(hexIntB, 16);
        return intA < intB ? -1 : intA == intB ? 0 : 1;
    }
}