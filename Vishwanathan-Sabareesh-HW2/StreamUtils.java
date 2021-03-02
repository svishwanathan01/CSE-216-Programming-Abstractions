import java.util.*;
import java.util.stream.Collectors;

public class StreamUtils {

    public static Collection<String> capitalized(Collection<String> strings) {
        return strings.stream().
                map(s -> s.substring(0,1).toUpperCase() + s.substring(1)).
                collect(Collectors.toCollection(ArrayList<String>::new));
    }

    public static String longest(Collection<String> strings, boolean from_start){
        return strings.stream()
                        .reduce((x,y)->{
                            if(x.length()>y.length())  return x;
                            else if(x.length() == y.length() && from_start == true) return x;
                            else return y;
                        }).orElse(null);
    }

    public static <T extends Comparable<T>> T least(Collection<T> items, boolean from_start){
        return items.stream()
                .reduce((x,y)->{
                    if(x.compareTo(y) > 0) return x;
                    else if(x.compareTo(y) == 0) return x;
                    else return y;
                }).orElse(null);
    }

    public static <K, V> List<String> flatten(Map<K, V> aMap){
        return aMap.entrySet().stream()
                .map(k -> k.getKey() + " -> " + k.getValue()).collect(Collectors.toList());
    }

//    public static void main(String[] args) {
//        Collection<String> strings =  Arrays.asList("hello", "cow", "world", "java", "hi");
//        Collection<Integer> ints = Arrays.asList(4, 2, 3, 1);
//        for(String s: capitalized(strings))
//            System.out.println(s);
//
//        System.out.println(longest(strings, true));
//        System.out.println(least(ints, false));
//        Map<String, String> input = new HashMap<>();
//        input.put("a", "1234");
//        input.put("b", "2345");
//        input.put("c", "3456");
//        input.put("d", "4567");
//        System.out.println(flatten(input));
//    }
}
