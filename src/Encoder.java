public class Encoder {

    private final String refTable; // Reference table with the desired characters to encode/decode
    private final int offset; // Offset number for encoding/decoding

    public Encoder(String refTable, char offsetChar) {
        int offset = refTable.indexOf(offsetChar);
        if (offset == -1) {
            throw new IllegalArgumentException("Offset character not found in ref table: " + offsetChar);
        }
        this.refTable = refTable;
        this.offset = offset;
    }

    public static void main(String[] args) {
        String referenceTable = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";

        Encoder encoder = new Encoder(referenceTable, 'B');
        String plaintext = "HELLO WORLD";
        String encoded = encoder.encode(plaintext);
        System.out.println(encoded);
        String decoded = encoder.decode(encoded);
        System.out.println(decoded);
    }

    // Encodes the given plain text by offsetting each character
    public String encode(String plainText) {
        StringBuilder encodedText = new StringBuilder();
        encodedText.append(refTable.charAt(offset)); // the first character is the offset
        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);
            int refIndex = refTable.indexOf(c);
            if (refIndex == -1) {
                encodedText.append(c);
            } else {
                encodedText.append(refTable.charAt((refIndex + refTable.length() - offset) % refTable.length()));
            }
        }
        return encodedText.toString();
    }

    // Decodes the given encoded text by un-offsetting each character using the first character as the offset
    public String decode(String encodedText) {
        StringBuilder decodedText = new StringBuilder();
        char offsetChar = encodedText.charAt(0);
        int offset = refTable.indexOf(offsetChar);
        if (offset == -1) {
            throw new IllegalArgumentException("Offset character not found in ref table: " + offsetChar);
        }

        for (int i = 1; i < encodedText.length(); i++) {
            char c = encodedText.charAt(i);
            int refIndex = refTable.indexOf(c);
            if (refIndex == -1) {
                decodedText.append(c);
            } else {
                decodedText.append(refTable.charAt((refIndex + offset) % refTable.length()));
            }
        }
        return decodedText.toString();
    }

}