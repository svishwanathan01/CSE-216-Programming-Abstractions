import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SparsePolynomialTest {
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
    SparsePolynomial s8 = new SparsePolynomial("-5x^2 - 2x - 1");


    @org.junit.jupiter.api.Test
    void degree() {
        assertEquals(2500, s3.degree()); //Degree of SparsePolynomial
        assertNotEquals(2501, s3.degree()); //Degree of SparsePolynomial, is not equal
        assertThrows(NullPointerException.class, () ->{
            new SparsePolynomial(null).degree();
        }); //Degree of SparsePolynomial that is null, throws exception
    }

    @org.junit.jupiter.api.Test
    void getCoefficient() {
        assertEquals(-5, s2.getCoefficient(2)); //Coefficient of 2nd degree of SparsePolynomial
        assertNotEquals(5, s2.getCoefficient(2)); //Coefficient of 2nd degree of SparsePolynomial
        assertThrows(NullPointerException.class, () ->{
            new SparsePolynomial(null).getCoefficient(1231213123); //Coefficient of Null SparsePolynomial
        });
    }

    @org.junit.jupiter.api.Test
    void isZero() {
        assertFalse(s1.isZero()); //Checks isZero for SparsePolynomial
        assertTrue(s6.isZero()); //Checks isZero for SparsePolynomial
        assertThrows(IllegalArgumentException.class, () ->{ new SparsePolynomial("").isZero(); }); //Checks isZero for Empty SparsePolynomial
        assertThrows(NullPointerException.class, () ->{ new SparsePolynomial(null).isZero(); }); ////Checks isZero for SparsePolynomial
    }

    @org.junit.jupiter.api.Test
    void add() {
        assertEquals(s1, s1.add(s6)); //Sum of two SparsePolynomial objects
        assertEquals(new SparsePolynomial("x^3 + x^2 + 2x"), s1.add(s5)); //Sum of two SparsePolynomial objects
        assertEquals(new SparsePolynomial("x^3 - 5x^2 - x"), s1.add(d5)); //Sum of Sparse and DensePolynomial objects
        assertThrows(NullPointerException.class, () ->{ s4.add(null); }); //Sum of Sparse and null objects
    }

    @org.junit.jupiter.api.Test
    void multiply() {
        assertEquals(s6, s6.multiply(s3)); //Product of two SparsePolynomial objects
        assertEquals(s3, s7.multiply(s3)); //Product of two SparsePolynomial objects
        assertEquals(new SparsePolynomial("-5x^5 - 2x^4 - 6x^3 - 7x^2 - 3x - 1"), s2.multiply(s1)); //Product of two SparsePolynomial objects
        assertEquals(new SparsePolynomial("-5x^4 - 7x^3 + 2x^2 + x + 1"), s5.multiply(d3)); //Product of Sparse and DensePolynomial objects
        assertThrows(IllegalArgumentException.class, () -> {
            s4.multiply(new SparsePolynomial("4.3x^2"));
        }); //Product of two SparsePolynomial objects, throws exception because of float
        assertEquals(new SparsePolynomial("x^3 + x + 1"), s1.multiply(d2));
        assertDoesNotThrow(() -> { s1.multiply(d2); }); //Product of Sparse and DensePolynomial objects
        assertThrows(NullPointerException.class, () ->{ new SparsePolynomial(null).multiply(d3); }); //Product of Sparse and null objects
        assertThrows(NullPointerException.class, () ->{ s2.multiply(new SparsePolynomial(null)); }); //Product of Sparse and null objects
        assertThrows(NullPointerException.class, () ->{ new SparsePolynomial(null).multiply(s2); }); //Product of Sparse and null objects
    }

    @org.junit.jupiter.api.Test
    void subtract() {
        assertEquals(s2.minus(), s6.subtract(s2)); //Difference of two SparsePolynomial objects
        assertEquals(s2, s2.subtract(s6)); //Difference of two SparsePolynomial objects
        assertEquals(new SparsePolynomial("-x^3 - 5x^2 - 3x - 2"), s2.subtract(s1)); //Difference of two SparsePolynomial objects
        assertEquals(new SparsePolynomial("-x^3 - 5x^2 - 3x - 2"), s2.subtract(d4)); //Difference of Sparse and DensePolynomial objects
        assertThrows(IllegalArgumentException.class, () ->{
            s2.subtract(new SparsePolynomial("-4.3x^-1"));
        }); //Difference of two SparsePolynomial objects, throws exception because of float
        assertThrows(NullPointerException.class, () ->{ s3.subtract(null); }); //Difference of Sparse and null objects
        assertDoesNotThrow(() -> { s7.subtract(d3); }); //Product of Sparse and null objects
    }

    @org.junit.jupiter.api.Test
    void minus() {
        assertEquals(new SparsePolynomial("-x^2500 + 2x^433 + 1"), s3.minus()); //Minus of Sparse
        assertEquals(new SparsePolynomial("12"), new SparsePolynomial("-12").minus()); //Minus of Sparse constant
        assertNotEquals(s3, s3.minus()); //Minus of sparse not itself
        assertEquals(s3, s3.minus().minus()); //Minus of minus of sparse is itself
        assertThrows(IllegalArgumentException.class, () ->{ new SparsePolynomial("12312.5x^-12341"); }); //Minus of sparse, throws exception because of float
        assertThrows(NullPointerException.class, () ->{ new SparsePolynomial(null).minus(); }); //Minus of null sparse
    }

    @org.junit.jupiter.api.Test
    void wellFormed() {
        assertDoesNotThrow(() -> { new SparsePolynomial("5x^2 + 2x + 1"); }); //Well formed
        assertDoesNotThrow(() -> { new SparsePolynomial("x^2 + 1 + x^-2"); }); //Well formed
        assertThrows(IllegalArgumentException.class, () -> { new SparsePolynomial("4.214x^3 + 4.3x^2"); }); //Well formed
        assertThrows(NullPointerException.class, () ->{ new SparsePolynomial(null); }); //Well formed
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        assertTrue(s2.equals(s8)); //Equals
        assertFalse(s2.equals(s3)); //Equals
        assertFalse(s2.minus().equals(s2)); //Equals
        assertTrue(s2.minus().equals(s8.minus())); //Equals
        assertThrows(NullPointerException.class, () ->{ new SparsePolynomial(null).equals(s3); }); //Equals
        assertThrows(NullPointerException.class, () ->{ s4.equals(new SparsePolynomial(null)); }); //Equals
    }
}