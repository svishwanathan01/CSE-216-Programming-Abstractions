import java.util.*;

public class DensePolynomial implements Polynomial{

    private int[] poly;
    private String polyString;

    /**
     * Constructs a DensePolynomial instance given a string.
     * @param str
     * @throws IllegalArgumentException if the string is empty, null, or isn't a DensePolynomial.
     */
    //Creating an object requires spaces between the terms of the polynomial
    public DensePolynomial(String str){
        polyString = str;
        if(!this.wellFormed()) throw new IllegalArgumentException("Cannot form polynomial");

        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();

        for (String val : polyString.split(" ")) strings.add(val);
        int degree = 0;
        String lead = strings.get(0);
        if (lead.contains("^")) degree = Integer.parseInt(lead.substring(lead.indexOf("^") + 1));
        else if (lead.contains("x")) degree = 1;
        else degree = 0;
        int[] poly = new int[degree+1];
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals("-")) {
                strings.set(i + 1, "-" + strings.get(i + 1));
                strings.remove(i);
                i--;
            }
        }
        for (int i = 0; i < strings.size(); i++) { if (!(strings.get(i).equals("+"))) { temp.add(strings.get(i)); } }
        //System.out.println(temp);
        for(int i = 0; i < temp.size(); i++){
            String term = temp.get(i);
            if(term.contains("^")){
                if(term.substring(0, term.indexOf("x")).equals("")){ poly[Integer.parseInt(term.substring(term.indexOf("^") + 1))] = 1; }
                else if(term.substring(0, term.indexOf("x")).equals("-")){ poly[Integer.parseInt(term.substring(term.indexOf("^") + 1))] = -1; }
                else{ poly[Integer.parseInt(term.substring(term.indexOf("^") + 1))] = Integer.parseInt(term.substring(0, term.indexOf("x"))); }
            }
            else if(term.contains("x")){
                if(term.substring(0, term.indexOf("x")).equals("")){ poly[1] = 1;}
                else if(term.substring(0, term.indexOf("x")).equals("-")){ poly[1] = -1;}
                else{ poly[1] = Integer.parseInt(term.substring(0, term.indexOf("x"))); }
            }
            else poly[0] = Integer.parseInt(term);
        }

        this.poly = poly;
    }

    /**
     * Get the current value of the poly parameter.
     * The poly parameter contains the coefficients corresponding to the index of the array
     * @return poly
     */
    public int[] getPoly() { return poly; }

    /**
     * Get the current value of the polyString parameter.
     * The polyString parameter contains the string provided when creating the DensePolynomial Object
     * @return polyString
     */
    public String getPolyString() { return polyString; }

    /**
     * Returns the degree of the polynomial.
     *
     * @return the largest exponent with a non-zero coefficient.  If all terms have zero exponents, it returns 0.
     */
    public int degree() {
        int degree = 0;
        if(poly.length > 1){ degree = poly.length -1; }
        else degree = 0;
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
        if(d > poly.length) return 0;
        return poly[d];
    }

    /**
     * @return true if the polynomial represents the zero constant
     */
    public boolean isZero() {
        if(poly.length == 1 && poly[0] == 0) return true;
        return false;
    }

    /**
     * Returns a polynomial by adding the parameter to the current instance. Neither the current instance nor the
     * parameter are modified.
     *
     * @param q the non-null polynomial to add to <code>this</code>
     * @return a new DensePolynomial with the sum of this and q
     * @throws NullPointerException if q is null
     */
    public Polynomial add(Polynomial q) {
        String string = "";
        if(q == null) throw new NullPointerException("No such polynomial");
        else if(q instanceof DensePolynomial) {
            if(((DensePolynomial) q).getPolyString().equals("0")){
                //System.out.println(true);
                //System.out.println(this.polyString);
                string = polyString;
                //System.out.println(string);
                return new DensePolynomial(string);
            }
            else if (poly.length >= q.degree() + 1) {
                int[] temp = new int[poly.length + 1];
                for (int i = 0; i < poly.length; i++) { temp[i] = poly[i]; }
                for (int i = 0; i < ((DensePolynomial) q).getPoly().length; i++) {
                    temp[i] = temp[i] + ((DensePolynomial) q).getPoly()[i];
                }
                return new DensePolynomial(stringHelper(temp));
            } else {
                int[] temp = new int[((DensePolynomial) q).getPoly().length + 1];
                for (int i = 0; i < ((DensePolynomial) q).getPoly().length; i++) {
                    temp[i] = ((DensePolynomial) q).getPoly()[i];
                }
                //for(int num : temp) System.out.println(temp);
                for(int i = 0; i < poly.length; i++){ temp[i] = temp[i] + poly[i]; }
                return new DensePolynomial(stringHelper(temp));
            }
        }
        else if(q instanceof SparsePolynomial){
            if(((SparsePolynomial) q).getPolyString().equals("0")) return new DensePolynomial(polyString);
            Map<Integer, Integer> map = ((SparsePolynomial) q).getMap();
            for(int i = 0; i < poly.length; i++){
                if(map.containsKey(i)){ map.put(i, map.get(i) + poly[i]); }
                else{ map.put(i, poly[i]); }
            }
            String str = "";
            for (int keys : map.keySet()) { str = str + map.get(keys) + "x^" + keys + " + "; }
            str = str.replaceAll("\\+", "");

            Stack<String> stack = new Stack();
            for(String s : str.split("  ")){ stack.push(s); }
            String temp = "";
            while(!stack.empty()){ temp = temp + stack.pop() + " + "; }
            //System.out.println(temp);
            if(temp.contains("+ -")){ temp = temp.replace("+ -", "- "); }
            if(temp.contains("^1")){ temp = temp.replace("^1", ""); }
            if(temp.contains("x^0")){ temp = temp.replace("x^0", ""); }
            if(temp.substring(temp.length()-2).equals("+ ")){ temp = temp.substring(0, temp.length()-2); }

            //System.out.println(temp);
            return new DensePolynomial(temp);
        }
        return new DensePolynomial("0");
    }

    /**
     * Returns a polynomial by multiplying the parameter with the current instance.  Neither the current instance nor
     * the parameter are modified.
     *
     * @param q the polynomial to multiply with <code>this</code>
     * @return a new DensePolynomial with the product of this and q
     * @throws NullPointerException if q is null
     */
    public Polynomial multiply(Polynomial q) {
        if(q == null) throw new NullPointerException("No such polynomial");
        else if(q instanceof DensePolynomial){
            int size = poly.length + ((DensePolynomial) q).getPoly().length -1;
            int product[] = new int[size];
            for(int i = 0; i < size; i++){ product[i] = 0; }

            for(int i = 0; i < poly.length; i++){
                for(int j = 0; j < ((DensePolynomial) q).getPoly().length; j++){
                    product[i+j] += poly[i] * ((DensePolynomial) q).getPoly()[j];
                }
            }
            return new DensePolynomial(stringHelper(product));
        }
        else if(q instanceof SparsePolynomial){
            int size = poly.length + ((SparsePolynomial) q).getMap().size() - 1;
            Map<Integer, Integer> tempMap = new TreeMap<Integer, Integer>();
            for(int i = 0; i < poly.length; i++){
                for(int num : ((SparsePolynomial) q).getMap().keySet()){
                    if(tempMap.get(i + num) != null){
                        tempMap.put(i + num, (poly[i] * ((SparsePolynomial) q).getMap().get(num)) + tempMap.get(i + num));
                    }
                    else{
                        tempMap.put(i + num, poly[i] * ((SparsePolynomial) q).getMap().get(num));
                    }
                }
            }
            return new DensePolynomial(stringHelperMap(tempMap));
        }
        return new DensePolynomial("0");
    }

    /**
     * Returns a  polynomial by subtracting the parameter from the current instance. Neither the current instance nor
     * the parameter are modified.
     *
     * @param q the non-null polynomial to subtract from <code>this</code>
     * @return a new DensePolynomial with the difference of this and q
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
        if(!polyString.contains("x") && polyString.length() > 0){
            int count = Integer.parseInt(polyString);
            count = -1*count;
            return new DensePolynomial(String.valueOf(count));
        }
        int[] temp = new int[poly.length];
        for(int i = 0; i < poly.length; i++){
            temp[i] = -1 * poly[i];
        }
        return new DensePolynomial(stringHelper(temp));
    }

    /**
     * Checks if the class invariant holds for the current instance.
     *
     * @return {@literal true} if the class invariant holds, and {@literal false} otherwise.
     */
    public boolean wellFormed() {
        if(polyString.equals("")){ return false; }
        ArrayList<String> polyTemp = new ArrayList<String>();

        for(String s : polyString.split(" ")){
            if(!s.equals("+") && !s.equals("-")) polyTemp.add(s);
        }
        if(polyString.contains("x")){
            for(String s : polyTemp){
                String degree = "";
                String coeff = s;
                if(s.contains("^")) degree = s.substring(s.indexOf("^") + 1);
                if(s.contains("x")) coeff = s.substring(0, s.indexOf("x"));
                if(degree.contains("-")) return false;
                if(coeff.contains("-")) coeff = s.substring(s.indexOf("-") + 1, s.indexOf("x"));
                for(char ch : coeff.toCharArray()){
                    if(!Character.isDigit(ch)) return false;
                }
                for(char ch : degree.toCharArray()){
                    if(!Character.isDigit(ch)) return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns a string representation of a polynomial taken from an array
     * @param arr
     * @return a string representation of the polynomial taken from an array
     */
    public String stringHelper(int[] arr){
        String str = "";
        for(int i = arr.length - 1; i >=2; i--){
            if(arr[i] == 0);
            else if(arr[i] == 1){ str = str + "x^" + String.valueOf(i) + " + "; }
            else if(arr[i] == -1){ str = str + "-x^" + String.valueOf(i) + " + "; }
            else{ str = str + String.valueOf(arr[i]) + "x^" + String.valueOf(i) + " + "; }
        }

        if(arr.length > 1 && arr[1] != 0){
            if(arr[1] == 1) { str = str + "x + "; }
            if(arr[1] == -1) { str = str + "-x + "; }
            else{ str = str + String.valueOf(arr[1]) + "x + "; }
        }

        if(arr[0] != 0){ str = str + String.valueOf(arr[0]); }
        if(str.length() >=3 && str.substring(str.length() - 3, str.length()).equals(" + ")){ str = str.substring(0, str.length() - 3); }
        str = str.replaceAll("\\+ -", "- ");
        if(str == "") str = "0";

        return str;
    }

    /**
     * Returns a string representation of a polynomial taken from a map
     * @param map
     * @return a string representation of the polynomial taken from amap
     */
    public String stringHelperMap(Map<Integer, Integer> map){
        String str = "";
        for (int keys : map.keySet()) { str = str + map.get(keys) + "x^" + keys + " + "; }
        str = str.replaceAll("\\+", "");

        Stack<String> stack = new Stack();
        for(String s : str.split("  ")){ stack.push(s); }
        String temp = "";
        while(!stack.empty()){ temp = temp + stack.pop() + " + "; }
        //System.out.println(temp);
        if(temp.contains("+ -")){ temp = temp.replaceAll("\\+ -", "- "); }
        if(temp.contains("^1")){ temp = temp.replace("^1", ""); }
        if(temp.contains("x^0")){ temp = temp.replace("x^0", ""); }
        if(temp.length() >= 2 && temp.substring(temp.length()-2).equals("+ ")){ temp = temp.substring(0, temp.length()-2); }
        if(temp.length() >= 2 && temp.charAt(0) == '1'){ temp = temp.substring(1); }
        if(temp.length() >= 2 && temp.substring(0,2).equals("-1")){ temp = "-" + temp.substring(2); }
        if(temp.contains(" 1x")){ temp = temp.replace(" 1x", " x"); }
        if(temp.contains("+ 0")) { temp = temp.replace("+ 0", " "); }
        if(str.equals("")) str = "0";
        if(str.length() > 2){
            if(str.substring(str.length()-1, str.length()).equals(" "))
                str = str.substring(0, str.length()-1);
        }
        return temp;
    }

    /**
     * Returns a string representation of the DensePolynomial object
     * @return Returns a string representation of the DensePolynomial object
     */
    @Override
    public String toString() {
        return stringHelper(poly);
    }

    /**
     * Checks whether two polynomial objects are equal, and returns true if the polynomials are equal
     * @param o
     * @return true if the polynomials are equal
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof DensePolynomial){
            return Arrays.equals(poly, ((DensePolynomial) o).getPoly());
        }
        else if(o instanceof SparsePolynomial){
            return polyString.equals(((SparsePolynomial) o).getPolyString());
        }
        return false;
    }
}

