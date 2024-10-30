// https://github.com/michaelvanstraten/Mathematics-B.Sc./blob/master/Grundlagen%20der%20Programmierung/Caesar.java

public class Caesar {
    public static void main(String[] args) {
        // check for the correct number of arguments
        if (args.length < 2) {
            System.out.println(
                    "Bitte geben Sie einen Schlüssel K als ersten Parameter an, sowie mindestens eine zu verschlüsselnde Nachricht.");
            System.exit(-1);
        }

        int key = Integer.parseInt(args[0]);

        // for every message we are given
        for (int i = 1; i < args.length; i++) {
            String message = args[i];

            // Convert the message into a stream of characters, process it and collect the
            // result back into a string. This is not completely necessary since we are
            // "encrypting" every byte in place but, this could also be use if we where
            // actually mapping our char stream though some cryptographically secure
            // cypher implementation that would not be a one to one mapping.
            String encryptedOrDecryptedMessage = message.chars().map(characterCode -> {
                // character "a" in ASCII
                int baseOffset = 97;
                int letter = characterCode - baseOffset;

                // we might be ciphering an uppercase letter in which case the base will be
                // different
                if (letter < 0) {
                    // character "A" in ASCII
                    baseOffset = 65;
                    letter = characterCode - baseOffset;
                }

                // check if we have a character that is not a valid letter in the alphabet just
                // return it
                if (letter > 25 || letter < 0) {
                    return characterCode;
                }

                // shift the letter by the key and modulated it back to a valid letter if
                // it "overflows", then convert it back to a character
                return baseOffset + (Math.floorMod(letter + key, 26));
            }).collect(
                    StringBuilder::new,
                    StringBuilder::appendCodePoint,
                    StringBuilder::append).toString();

            System.out.println(encryptedOrDecryptedMessage);
        }
    }
}
