import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DensePolynomialTest {

    DensePolynomial d1 = new DensePolynomial("0");
    DensePolynomial d2 = new DensePolynomial("1");
    DensePolynomial d3 = new DensePolynomial("-5x^2 - 2x - 1");
    DensePolynomial d4 = new DensePolynomial("x^3 + x + 1");
    DensePolynomial d5 = new DensePolynomial("-5x^2 - 2x - 1");
    SparsePolynomial s1 = new SparsePolynomial("x^3 + x + 1");
    SparsePolynomial s2 = new SparsePolynomial("-5x^2 - 2x - 1");
    SparsePolynomial s3 = new SparsePolynomial("x^2500 - 2x^433 - 1");
    SparsePolynomial s4 = new SparsePolynomial("x^-1 + x - 5");
    SparsePolynomial s5 = new SparsePolynomial("x^2 + x - 1");
    SparsePolynomial s6 = new SparsePolynomial("0");
    SparsePolynomial s7 = new SparsePolynomial("1");

    @org.junit.jupiter.api.Test
    void degree() {
        assertEquals(3, d4.degree()); //degree of x^3 + x + 1
        assertNotEquals(-3, d4.degree()); //degree of x^3 + x + 1, should be 3
        assertThrows(NullPointerException.class, () ->{
            new DensePolynomial(null).degree();
        }); //degree of null object, throws NullPointerException
    }

    @org.junit.jupiter.api.Test
    void getCoefficient() {
        assertEquals(-5, d3.getCoefficient(2)); //coefficient of 2nd degree term of -5x^2 - 2x - 1
        assertNotEquals(5, d3.getCoefficient(2)); //coefficient of 2nd degree term of -5x^2 - 2x - 1, should be -5
        assertThrows(NullPointerException.class, () ->{
            new DensePolynomial(null).getCoefficient(2132);
        }); //coefficient of 2nd degree term of null object, throws NullPointerException
    }

    @org.junit.jupiter.api.Test
    void isZero() {
        assertTrue(d1.isZero()); //Checks 0 is a zero constant
        assertFalse(d2.isZero()); //Checks if 1 is a zero constant
        assertThrows(IllegalArgumentException.class, () ->{
            new DensePolynomial("").isZero();
        }); //Checks if "" is a 0 constant, throws IllegalArgumentException
        assertThrows(NullPointerException.class, () ->{
            new DensePolynomial(null).isZero();
        }); //Checks if null is a 0 constant, throws NullPointerException
    }

    @org.junit.jupiter.api.Test
    void add() {
        assertEquals(d4, d4.add(d1)); //Sum of DensePolynomial and 0(DensePolynomial), is itself
        assertEquals(new DensePolynomial("x^3 - 5x^2 - x"), d4.add(d3)); //Sum of two DensePolynomial objects
        assertEquals(new DensePolynomial("x^3 + x^2 + 2x"), d4.add(s5)); //Sum of DensePolynomial and SparsePolynomial objects
        assertThrows(IllegalArgumentException.class, () -> {
            d4.add(s4);
        }); //Sum of DensePolynomial and SparsePolynomial objects where the Sparse Polynomial object has a negative exponent
        assertThrows(NullPointerException.class, () ->{
            d4.add(new DensePolynomial(null));
        }); //Sum of DensePolynomial and DensePolynomial objects, where one of the objects is null
        assertDoesNotThrow(() -> {
            d4.add(s1); //Sum of DensePolynomial and SparsePolynomial objects
        });
    }

    @org.junit.jupiter.api.Test
    void multiply() {
        assertEquals(d1, d1.multiply(d3)); //Product of 0(DensePolynomial) and a DensePolynomial object, is 0
        assertEquals(d1, d3.multiply(d1)); //Product of DensePolynomial and 0(DensePolynomial), is 0
        assertEquals(d3, d2.multiply(d3)); //Product of 1(DensePolynomial) and a DensePolynomial object, is itself
        assertEquals(new DensePolynomial("-5x^5 - 2x^4 - 6x^3 - 7x^2 - 3x - 1"), d3.multiply(d4)); //Product of two DensePolynomial objects
        assertEquals(new DensePolynomial("-5x^4 - 7x^3 + 2x^2 + x + 1"), d3.multiply(s5)); //Product of DensePolynomial and SparsePolynomial object
        assertThrows(IllegalArgumentException.class, () -> {
            d4.multiply(s4);
        }); //Product of DensePolynomial and SparsePolynomial objects where the Sparse Polynomial object has a negative exponent
        assertDoesNotThrow(() -> {
            d2.multiply(s1);
        }); //Product of DensePolynomial and SparsePolynomial objects
        assertThrows(NullPointerException.class, () ->{
            d4.multiply(new DensePolynomial(null));
        }); //Product of DensePolynomial and DensePolynomial objects, where one of the objects is null
        assertThrows(NullPointerException.class, () ->{
            new DensePolynomial(null).multiply(d3);
        }); //Product of DensePolynomial and DensePolynomial objects, where one of the objects is null
        assertThrows(NullPointerException.class, () ->{
            new DensePolynomial(null).multiply(s2); //Product of DensePolynomial and SparsePolynomial objects, where the Dense object is null
        });
    }

    @org.junit.jupiter.api.Test
    void subtract() {
        assertEquals(d3.minus(), d1.subtract(d3)); //Difference of 0 and a DensePolynomial object, is its negative
        assertEquals(new DensePolynomial("-5x^2 - 2x - 1"), d3.subtract(d1)); //Difference of two DensePolynomial objects
        assertEquals(new DensePolynomial("-x^3 - 5x^2 - 3x - 2"), d3.subtract(d4)); //Difference of two DensePolynomial objects
        assertEquals(new DensePolynomial("-6x^2 - 3x"), d3.subtract(s5)); //Difference of DensePolynomial and SparsePolynomial objects
        assertThrows(IllegalArgumentException.class, () ->{
            d4.subtract(s4);
        }); //Difference of DensePolynomial and SparsePolynomial objects, where one of the objects is null
        assertThrows(NullPointerException.class, () ->{
            d4.subtract(null);
        }); //Difference of DensePolynomial and DensePolynomial objects, where one of the objects is null
        assertDoesNotThrow(() -> {
            d4.subtract(s1);
        }); //Difference of DensePolynomial and DensePolynomial objects
    }

    @org.junit.jupiter.api.Test
    void minus() {
        assertEquals(new DensePolynomial("0"), d1); //Minus of 0(dense polynomial)
        assertEquals(new DensePolynomial("12"), new DensePolynomial("-12").minus()); //Minus of constant(dense polynomial)
        assertEquals(new DensePolynomial("5x^2 + 2x + 1"), d3.minus()); //Minus of DensePolynomial
        assertNotEquals(d3, d3.minus()); //Minus of DensePolynomial is not equal to itself
        assertEquals(d3, d3.minus().minus()); //Minus of minus of DensePolynomial is equal to itself
        assertThrows(NullPointerException.class, () ->{
            new DensePolynomial(null).minus();
        }); //Minus for null DensePolynomial, throws NullPointerException
    }

    @org.junit.jupiter.api.Test
    void wellFormed() {
        assertDoesNotThrow(() -> { new DensePolynomial("5x^2 + 2x + 1"); }); //Well formed DensePolynomial
        assertThrows(IllegalArgumentException.class, () -> {
            new DensePolynomial("x^2 + 1 + x^-2");
        }); //Not Well Formed DensePolynomial
        assertThrows(IllegalArgumentException.class, () -> {
            new DensePolynomial("4x^3 + 4.3x^2");
        }); //Not Well Formed DensePolynomial
        assertThrows(NullPointerException.class, () ->{
            new DensePolynomial(null);
        }); //Null DensePolynomial
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        assertTrue(d3.equals(d5)); //Equality of DensePolynomial objects
        assertTrue(d3.equals(d3)); //Equality of the same DensePolynomial object
        assertFalse(d3.equals(d1)); //Inequality of DensePolynomial objects
        assertFalse(d3.minus().equals(d3)); //Inequality of DensePolynomial objects
        assertTrue(d3.minus().equals(d5.minus())); //Equality of DensePolynomial objects
        assertThrows(NullPointerException.class, () ->{
            new DensePolynomial(null).equals(d2);
        }); //Equality of DensePolynomial objects, where there is a null, throws NullPointerException
        assertThrows(NullPointerException.class, () ->{
            d3.equals(new DensePolynomial(null));
        }); //Equality of DensePolynomial objects, where there is a null, throws NullPointerException
    }
}