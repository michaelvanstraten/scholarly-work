public class Caesar {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    "Bitte geben Sie einen Schlüssel K als ersten Parameter an, sowie mindestens eine zu verschlüsselnde Nachricht.");
            System.exit(-1);
        }

        int key = Integer.parseInt(args[0]);

        for (int i = 1; i < args.length; i++) {
            String message = args[i];

            String encryptedOrDecryptedMessage = message.chars().map(characterCode -> {
                int baseOffset = 97;
                int alphabetOffset = characterCode - baseOffset;

                if (alphabetOffset < 0) {
                    baseOffset = 65;
                    alphabetOffset = characterCode - baseOffset;
                }

                if (alphabetOffset > 25 || alphabetOffset < 0) {
                    return characterCode;
                }

                return baseOffset + (Math.floorMod(alphabetOffset + key, 26));
            }).collect(
                    StringBuilder::new,
                    StringBuilder::appendCodePoint,
                    StringBuilder::append).toString();

            System.out.println(encryptedOrDecryptedMessage);
        }
    }
}
