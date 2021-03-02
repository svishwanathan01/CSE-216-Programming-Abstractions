import java.util.*;

public class SparsePolynomial implements Polynomial {

    Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
    private String polyString;

    /**
     * Constructs a SparsePolynomial instance given a string.
     * @param str
     * @throws IllegalArgumentException if the string is empty, null, or isn't a SparsePolynomial.
     */
    //Creating an object requires spaces between the terms of the polynomial
    public SparsePolynomial(String str){
        //String str = "x^2500 - 2x^433 - 1";
        polyString = str;
        if(!this.wellFormed()) throw new IllegalArgumentException("Cannot form polynomial");
        if(str.contains("x")){
            ArrayList<String> temp = new ArrayList<>();
            Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
            for(String s : polyString.split(" ")){
                if(!s.equals("+")) temp.add(s);
            }

            for(int i = 0; i < temp.size(); i++){
                if(temp.get(i).equals("-")){
                    temp.set(i+1, "-" + temp.get(i+1));
                    temp.remove(i);
                    i--;
                }
            }

            for(String term : temp){
                if(term.contains("^")){
                    if(term.substring(0, term.indexOf("x")).equals("")){
                        map.put(Integer.parseInt(term.substring(term.indexOf("^")+1)), 1);
                    }
                    else if(term.substring(0, term.indexOf("x")).equals("-")){
                        map.put(Integer.parseInt(term.substring(term.indexOf("^")+1)), -1);
                    }
                    else{
                        map.put(Integer.parseInt(term.substring(term.indexOf("^") + 1)), Integer.parseInt(term.substring(0, term.indexOf("x"))));
                    }
                }
                else if(term.contains("x")){
                    if(term.substring(0, term.indexOf("x")).equals("")){ map.put(1, 1);}
                    else if(term.substring(0, term.indexOf("x")).equals("-")){ map.put(1, -1);}
                    else{ map.put(1, Integer.parseInt(term.substring(0, term.indexOf("x")))); }
                }
                else map.put(0, Integer.parseInt(term));
            }
            this.map = map;
        }
        else{
            //System.out.println(str);
            if(str.substring(str.length() - 1, str.length()).equals(" ")){
                str = str.substring(0, str.length()-1);
            }
            map.put(0, Integer.parseInt(str));
            this.map = map;
        }

        //System.out.println(map);
        //System.out.println(map.get(5));

    }

    /**
     * Get the current value of the polyString parameter.
     * The polyString parameter contains the string provided when creating the SparsePolynomial Object
     * @return polyString
     */
    public String getPolyString() { return polyString; }

    /**
     * Get the current value of the map parameter.
     * The map parameter contains the coefficients corresponding to the index of the map
     * @return map
     */
    public Map<Integer, Integer> getMap() { return map; }

    /**
     * Returns the degree of the polynomial.
     *
     * @return the largest exponent with a non-zero coefficient.  If all terms have zero exponents, it returns 0.
     */
    public int degree() {
        int degree = 0;
        if(map.keySet().size() > 0){
            for(int key : map.keySet()){ if(key > degree) degree = key; }
        }
        return degree;
    }

    /**
     * Returns the coefficient corresponding to the given exponent.  Returns 0 if there is no term with that exponent
     * in the polynomial.
     *
     * @param d the exponent whose coefficient is returned.
     * @return the coefficient of the term of whose exponent is d.
     */
    public int getCoefficient(int d) {
        if(map.get(d) == null) return 0;
        return map.get(d);
    }

    /**
     * @return true if the polynomial represents the zero constant
     */
    public boolean isZero() {
        if(map.keySet().size() == 1){
            if((Integer)map.keySet().toArray()[0] == 0 && map.get(0) == 0){ return true; }
        }
        return false;
    }

    /**
     * Returns a polynomial by adding the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     *
     * @param q the non-null polynomial to add to <code>this</code>
     * @return a new SparsePolynomial with the sum of this and q
     * @throws NullPointerException if q is null
     */
    public Polynomial add(Polynomial q) {
        Map<Integer, Integer> tempMap = new TreeMap<Integer, Integer>();
        for(int keys : map.keySet()){ tempMap.put(keys, map.get(keys)); }
        if(q == null) throw new NullPointerException("No such polynomial");
        else if(q instanceof SparsePolynomial){
            if(((SparsePolynomial) q).getPolyString().equals("0")) return new SparsePolynomial(polyString);
            for(int keys : ((SparsePolynomial) q).getMap().keySet()){
                if(tempMap.get(keys) != null){ tempMap.put(keys, tempMap.get(keys) + ((SparsePolynomial) q).getMap().get(keys)); }
                else{ tempMap.put(keys, ((SparsePolynomial) q).getMap().get(keys)); }
            }
            return new SparsePolynomial(stringHelper(tempMap));
        }
        else if(q instanceof DensePolynomial){
            if(((DensePolynomial) q).getPolyString().equals("0")) return new SparsePolynomial(polyString);
            for(int i = 0; i < ((DensePolynomial) q).getPoly().length; i++){
                if(tempMap.get(i) != null){ tempMap.put(i, tempMap.get(i) + ((DensePolynomial) q).getPoly()[i]); }
                else { tempMap.put(i, ((DensePolynomial) q).getPoly()[i]); }
            }
            return new SparsePolynomial(stringHelper(tempMap));
        }

        return new SparsePolynomial("0");
    }

    /**
     * Returns a polynomial by multiplying the parameter with the current instance.  Neither the current instance nor
     * the parameter are modified.
     *
     * @param q the polynomial to multiply with <code>this</code>
     * @return a new SparsePolynomial with the product of this and q
     * @throws NullPointerException if q is null
     */
    public Polynomial multiply(Polynomial q) {
        //for(int keys : map.keySet()){ tempMap.put(keys, map.get(keys)); }
        if(q == null) throw new NullPointerException("No such polynomial");
        else if(polyString.equals("0")) return new SparsePolynomial("0");
        else if(q instanceof SparsePolynomial){
            Map<Integer, Integer> tempMap = new TreeMap<Integer, Integer>();
            for(int num : map.keySet()){
                for(int num2 : ((SparsePolynomial) q).getMap().keySet()){
                    if(tempMap.get(num + num2) != null){ tempMap.put(num + num2, (map.get(num) * ((SparsePolynomial) q).getMap().get(num2)) + tempMap.get(num + num2)); }
                    else{ tempMap.put(num + num2, map.get(num) * ((SparsePolynomial) q).getMap().get(num2)); }
                }
            }
            return new SparsePolynomial(stringHelper(tempMap));
        }
        else if(q instanceof DensePolynomial){
            int size = ((DensePolynomial) q).getPoly().length + map.size() - 1;
            Map<Integer, Integer> tempMap = new TreeMap<Integer, Integer>();
            for(int i = 0; i < ((DensePolynomial) q).getPoly().length; i++){
                for(int num : map.keySet()){
                    if(tempMap.get(i + num) != null){ tempMap.put(i + num, (((DensePolynomial) q).getPoly()[i] * map.get(num) + tempMap.get(i + num))); }
                    else{ tempMap.put(i + num, ((DensePolynomial) q).getPoly()[i] * map.get(num)); }
                }
            }
            return new SparsePolynomial(stringHelper(tempMap));
        }
        return new SparsePolynomial("0");
    }

    /**
     * Returns a  polynomial by subtracting the parameter from the current instance. Neither the current instance nor
     * the parameter are modified.
     *
     * @param q the non-null polynomial to subtract from <code>this</code>
     * @return a new SparsePolynomial with the difference of this and q
     * @throws NullPointerException if q is null
     */
    public Polynomial subtract(Polynomial q) {
        if(q == null) throw new NullPointerException("No such polynomial");
        else if(q instanceof DensePolynomial){
            if(((DensePolynomial) q).getPolyString().equals("0")) {
                return this;
            }
            return this.add(q.minus());
        }
        else if(q instanceof SparsePolynomial){
            if(((SparsePolynomial) q).getPolyString().equals("0")){
                return this;
            }
            return this.add(q.minus());
        }
        return new DensePolynomial("");
    }

    /**
     * Returns a polynomial by negating the current instance. The current instance is not modified.
     *
     * @return -this
     */
    public Polynomial minus() {
        Map<Integer, Integer> tempMap = new TreeMap<Integer, Integer>();
        for(int keys : map.keySet()){ tempMap.put(keys, -1*map.get(keys)); }
        //System.out.println(tempMap);
        return new SparsePolynomial(stringHelper(tempMap));
    }

    /**
     * Checks if the class invariant holds for the current instance.
     *
     * @return {@literal true} if the class invariant holds, and {@literal false} otherwise.
     */
    public boolean wellFormed() {
        if(polyString.equals("")){ return false; }
        String temp = polyString;
        if(temp.contains(".")){
            return false;
        }
        return true;
    }

    /**
     * Returns a string representation of a polynomial taken from a map
     * @param map
     * @return a string representation of the polynomial taken from amap
     */
    public String stringHelper(Map<Integer, Integer> map){
        String str = "";
        for (int keys : map.keySet()) { str = str + map.get(keys) + "x^" + keys + " + "; }
        str = str.replaceAll("\\+", "");
        if(str.contains("x")){
            Stack<String> stack = new Stack();
            for(String s : str.split("  ")){ stack.push(s); }
            String temp = "";
            while(!stack.empty()){ temp = temp + stack.pop() + " + "; }
            //System.out.println(temp);
            if(temp.contains("+ -")){ temp = temp.replaceAll("\\+ -", "- "); }
            if(temp.contains("^1")){ temp = temp.replace("^1", ""); }
            if(temp.contains("x^0")){ temp = temp.replace("x^0", ""); }
            if(temp.length() >= 2 && temp.substring(temp.length()-2).equals("+ ")){ temp = temp.substring(0, temp.length()-2); }
            if(temp.length() >= 1 && temp.charAt(0) == '1' && temp.contains("x")){ temp = temp.substring(1); }
            if(temp.length() >= 2 && temp.substring(0,2).equals("-1") && temp.contains("x")){ temp = "-" + temp.substring(2); }
            if(temp.contains(" 1x")){ temp = temp.replace(" 1x", " x"); }
            if(temp.contains("+ 0")) { temp = temp.replace("+ 0", " "); }
            if(str.equals("")) str = "0";
            return temp;
        }
        return str;

    }

    /**
     * Returns a string representation of the SparsePolynomial object
     * @return Returns a string representation of the SparsePolynomial object
     */
    @Override
    public String toString() {
        return stringHelper(map);
    }

    /**
     * Checks whether two polynomial objects are equal, and returns true if the polynomials are equal
     * @param o
     * @return true if the polynomials are equal
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof SparsePolynomial){
            return map.equals(((SparsePolynomial) o).getMap());
        }
        else if(o instanceof DensePolynomial){
            return polyString.equals(((DensePolynomial) o).getPolyString());
        }
        return false;
    }

}
