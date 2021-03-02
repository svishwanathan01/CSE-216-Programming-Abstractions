import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class HigherOrderUtils {

    interface NamedBiFunction<T, U, R> extends BiFunction<T, U, R>{
        String name();
    }

    public static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>(){

        @Override
        public Double apply(Double a, Double b) {
            return a + b;
        }

        @Override
        public String name() {
            return "add";
        }
    };

    public static NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<Double, Double, Double>(){

        @Override
        public Double apply(Double a, Double b) {
            return a - b;
        }

        @Override
        public String name() {
            return "diff";
        }
    };

    public static NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<Double, Double, Double>(){

        @Override
        public Double apply(Double a, Double b) {
            return a * b;
        }

        @Override
        public String name() {
            return "mult";
        }
    };

    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>(){

        @Override
        public Double apply(Double a, Double b) {
            try {
                if ((Double) b == 0) throw new ArithmeticException();
                return a/b;
            } catch (ArithmeticException e) {
                System.out.println("Cannot divide by 0.");
                return null;
            }
        }

        @Override
        public String name() {
            return "div";
        }
    };

    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> biFunction){
        if(args.size() != biFunction.size() + 1){
            System.out.println("Arg list isn't large enough.");
            return null;
        }
        else{
            for(int i = 0; i < biFunction.size(); i++){
                T result = biFunction.get(i).apply(args.get(i), args.get(i+1));

                if(result == null) return null;
                else args.set(i+1, result);

                if(i == biFunction.size() -1) return result;
            }
        }
        return null;
    }

    static class FunctionComposition<T, U, R>{
        public BiFunction<Function<T, U>, Function<U, R>, Function<T, R>> composition = new BiFunction<Function<T, U>, Function<U, R>, Function<T, R>>() {
            @Override
            public Function<T, R> apply(Function<T, U> tuFunction, Function<U, R> urFunction) {
                return tuFunction.andThen(urFunction);
            }
        };
    }

//    public static void main(String[] args) {
//        List<Double> argsList = Arrays.asList(1d, 1d, 3d, 0d, 4d);
//        List<NamedBiFunction<Double, Double, Double>> bfs = Arrays.asList(add, multiply, add, divide);
//        System.out.println(zip(argsList, bfs));
//        FunctionComposition<Integer, String, Integer> fg = new FunctionComposition<>();
//        Function<Integer, String> f = Object::toString;
//        Function<String, Integer> g = String::length;
//
//        Function<Integer, Integer> a = g.compose(f);
//        BiFunction<Function<Integer, String>, Function<String, Integer>, Function<Integer, Integer>> inter = fg.composition;
//        Function<Integer, Integer> combine = inter.apply(f, g);
//        System.out.println("Composition with BiFunction:  " + combine.apply(14523) );
//        System.out.println(a.apply(14523));
//    }
}

