/** Functions for checking if a given string is an anagram. */
public class Anagram {

    public static void main(String args[]) {
        // Tests the isAnagram function.
        System.out.println(isAnagram("silent","listen"));  // true
        System.out.println(isAnagram("William Shakespeare","I am a weakish speller")); // true
        System.out.println(isAnagram("Madam Curie","Radium came")); // true
        System.out.println(isAnagram("Tom Marvolo Riddle","I am Lord Voldemort")); // true

        // Tests the preProcess function.
        System.out.println(preProcess("What? No way!!!"));
        
        // Tests the randomAnagram function.
        System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");
        
        // Performs a stress test of randomAnagram 
        String str = "1234567";
        Boolean pass = true;
        //// 10 can be changed to much larger values, like 1000
        for (int i = 0; i < 10; i++) {
            String randomAnagram = randomAnagram(str);
            System.out.println(randomAnagram);
            pass = pass && isAnagram(str, randomAnagram);
            if (!pass) break;
        }
        System.out.println(pass ? "test passed" : "test Failed");
    }  

    /**
     * Returns true if the two given strings are anagrams, false otherwise.
     * We ignore spaces, punctuation, and upper/lower case.
     */
    public static boolean isAnagram(String str1, String str2) {
        // First preprocess both strings (lowercase letters, remove punctuation, keep spaces)
        String s1 = preProcess(str1);
        String s2 = preProcess(str2);

        // For each *letter* in s1, try to find and remove the same letter from s2.
        // If a letter from s1 cannot be found in s2, they are not anagrams.
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);

            // Ignore spaces when checking anagrams
            if (c == ' ') {
                continue;
            }

            // Search for this character in s2
            int index = -1;
            for (int j = 0; j < s2.length(); j++) {
                if (s2.charAt(j) == c) {
                    index = j;
                    break;
                }
            }

            // Character not found → not an anagram
            if (index == -1) {
                return false;
            }

            // Remove the matched character from s2 so we don't use it again
            String before = s2.substring(0, index);
            String after = s2.substring(index + 1);
            s2 = before + after;
        }

        // If there are any letters left in s2 (ignoring spaces), then str2 had extra letters
        for (int j = 0; j < s2.length(); j++) {
            char c = s2.charAt(j);
            if (c != ' ') {
                return false;
            }
        }

        // All letters matched, nothing extra left → it’s an anagram
        return true;
    }
       
    /**
     * Returns a preprocessed version of the given string:
     * - Letters are converted to lower-case.
     * - Spaces are kept.
     * - All other characters (punctuation, digits, etc.) are removed.
     *
     * For example: "Hello World!" → "hello world"
     */
    public static String preProcess(String str) {
        String result = "";

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            // Uppercase letter → convert to lowercase and keep
            if (c >= 'A' && c <= 'Z') {
                char lower = (char) (c - 'A' + 'a');
                result = result + lower;
            }
            // Lowercase letter → keep
            else if (c >= 'a' && c <= 'z') {
                result = result + c;
            }
            // Space → keep
            else if (c == ' ') {
                result = result + c;
            }
            // Anything else (digits, punctuation, etc.) → skip
        }

        return result;
    } 
       
    /**
     * Returns a random anagram (random permutation) of the given string.
     * We repeatedly pick a random character from the remaining characters
     * and build a new string.
     */
    public static String randomAnagram(String str) {
        String remaining = str;  // characters we haven't used yet
        String result = "";      // build the anagram here

        // While there are still characters left
        while (remaining.length() > 0) {
            // Pick a random index between 0 and remaining.length()-1
            int index = (int) (Math.random() * remaining.length());

            // Take that character and append it to result
            char chosen = remaining.charAt(index);
            result = result + chosen;

            // Remove that character from remaining
            String before = remaining.substring(0, index);
            String after = remaining.substring(index + 1);
            remaining = before + after;
        }

        return result;
    }
}
