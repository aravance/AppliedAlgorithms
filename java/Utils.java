public class Utils {

    private Utils() {
        // disallow instantiation
    }

    public static String repeat(Object o, int n) {
        return n <= 0 || o == null ?
                "" :
                String.format(String.format("%%0%dd", n), 0).replaceAll("0", o.toString());
    }
}
