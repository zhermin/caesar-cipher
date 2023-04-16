import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class EncoderTest {

    private static final String referenceTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";

    @Test
    public void testOneOffset() {
        Encoder encoder = new Encoder(referenceTable, 'B');
        String plaintext = "HELLO WORLD";
        String expectedEncoded = "BGDKKN VNQKC";

        String encoded = encoder.encode(plaintext);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decode(encoded);
        assertEquals(plaintext, decoded);
    }

    @Test
    public void testNegativeOffset() {
        Encoder encoder = new Encoder(referenceTable, 'F');
        String plaintext = "HELLO WORLD";
        String expectedEncoded = "FC/GGJ RJMG.";

        String encoded = encoder.encode(plaintext);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decode(encoded);
        assertEquals(plaintext, decoded);
    }

    @Test
    public void testInvalidOffset() {
        try {
            new Encoder(referenceTable, '%');
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception thrown
        }
    }

    @Test
    public void testInvalidOffsetInEncodedText() {
        try {
            Encoder encoder = new Encoder(referenceTable, 'B');
            encoder.decode("!GDKKN VNQKC!");
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception thrown
        }
    }

    @Test
    public void testEmptyString() {
        Encoder encoder = new Encoder(referenceTable, 'A');
        String plaintext = "";
        String expectedEncoded = "A";
        String expectedDecoded = "";

        String encoded = encoder.encode(plaintext);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decode(encoded);
        assertEquals(expectedDecoded, decoded);
    }

    @Test
    public void testAllNotInReferenceTable() {
        Encoder encoder = new Encoder(referenceTable, 'E');
        String plaintext = "hello world!";
        String expectedEncoded = "Ehello world!";

        String encoded = encoder.encode(plaintext);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decode(encoded);
        assertEquals(plaintext, decoded);
    }

    @Test
    public void testMaxOffset() {
        Encoder encoder = new Encoder(referenceTable, '/');
        String plaintext = "HELLO WORLD";
        String expectedEncoded = "/IFMMP XPSME";

        String encoded = encoder.encode(plaintext);
        assertEquals(expectedEncoded, encoded);

        String decoded = encoder.decode(encoded);
        assertEquals(plaintext, decoded);
    }

}
