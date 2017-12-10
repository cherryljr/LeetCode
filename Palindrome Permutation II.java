/*
Given a string s, return all the palindromic permutations (without duplicates) of it. 
Return an empty list if no palindromic permutation could be form.

For example:
Given s = "aabb", return ["abba", "baab"].
Given s = "abc", return [].

Hint:
If a palindromic permutation exists, we just need to generate the first half of the string.
To generate all distinct permutations of a (half of) string, use a similar approach from: Permutations II or Next Permutation.
*/

/**
 * Approach 1： Brute Force [Time Limit Exceeded]
 * Algorithm
 * The simplest solution is generate every possible permutation of the given string s and check if the generated permutation is a palindrome. 
 * After this, the palindromic permuations can be added to a set in order to eliminate the duplicates. 
 * At the end, we can return an array comprised of the elements of this set as the result array.
 * 
 * Let's look at the way these permutations are generated. 
 * (Here we use swap numbers method, we can also use backtracking method like the Approach 2 in Permutations in LintCode).
 * 
 * We make use of a recursive function permute which takes the index of the current element current_index as one of the arguments. 
 * Then, it swaps the current element with every other element in the array, lying towards its right, 
 * so as to generate a new ordering of the array elements. 
 * After the swapping has been done, it makes another call to permute but this time with the index of the next element in the array. 
 * While returning back, we reverse the swapping done in the current function call. 
 * Thus, when we reach the end of the array, a new ordering of the array's elements is generated.
 * 
 * Complexity Analysis
 * Time complexity : O((n+1)!). 
 * A total of n! permutations are possible. 
 * For every permutation generated, we need to check if it is a palindrome, each of which requires O(n) time.
 * Space complexity : O(n). The depth of the recursion tree can go upto n.
 */
public class Solution {
    Set<String> set = new HashSet<>();
    public List<String> generatePalindromes(String s) {
        permute(s.toCharArray(), 0);
        return new ArrayList<String>(set);
    }
    
    void permute(char[] s, int start) {
        if (start == s.length) {
            if (isPalindrome(s)) {
                set.add(new String(s));   
            }
        } else {
            for (int i = start; i < s.length; i++) {
                swap(s, start, i);
                permute(s, start + 1);
                // While returning back, we reverse the swapping done in the current function call. 
                swap(s, start, i);  
            }
        }
    }
    
    public boolean isPalindrome(char[] s) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] != s[s.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }
    
    public void swap(char[] s, int i, int j) {
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}

/**
 * Approach 2: Backtracking 
 * Algorithm
 * It might be possible that no palindromic permutation could be possible for the given string s. 
 * Thus, it is useless to generate the permutations in such a case. 
 * Taking this idea, firstly we check if a palindromic permutation is possible for s. 
 * If yes, then only we proceed further with generating the permutations. 
 * 
 * To check this, we make use of a array map which stores the number of occurences of each character(out of 128 ASCII characters possible). 
 * If the number of characters with odd number of occurences exceeds 1, it indicates that no palindromic permutation is possible for s.
 * To look at this checking process in detail, look at Approach 3 of Palindrome Permutation.
 * https://github.com/cherryljr/LeetCode/blob/master/Palindrome%20Permutation.java
 * 
 * Once we are sure that a palindromic permutation is possible for s, we go for the generation of the required permutations. 
 * But, instead of wildly generating all the permutations, we can include some smartness in the generation of permutations 
 * i.e. we can generate only those permutations which are already palindromes.
 * 
 * One idea to to do so is to generate only the first half of the palindromic string 
 * and to append its reverse string to itself to generate the full length palindromic string.
 * 
 * Based on this idea, by making use of the number of occurences of the characters in s stored in map, 
 * we create a string st which contains all the characters of s 
 * but with the number of occurences of these characters in st reduced to half their original number of occurences in s.
 * Thus, now we can generate all the permutations of this string st 
 * and append the reverse of this permuted string to itself to create the palindromic permutations of s.
 * 
 * In case of a string s with odd length, whose palindromic permutations are possible, 
 * one of the characters in s must be occuring an odd number of times. 
 * We keep a track of this character, ch, and it is kept separte from the string st. 
 * We again generate the permutations for st similarly and append the reverse of the generated permutation to itself, 
 * but we also place the character ch at the middle of the generated string.
 * 
 * In this way, only the required palindromic permutations will be generated. 
 * Even if we go with the above idea, a lot of duplicate strings will be generated.
 * In order to avoid generating duplicate palindromic permutations in the first place itself, as much as possible, we can make use of this idea. 
 * As discussed in the last approach, 
 * we swap the current element with all the elements lying towards its right to generate the permutations. 
 * Before swapping, we can check if the elements being swapped are equal. 
 * If so, the permutations generated even after swapping the two will be duplicates(redundant). 
 * Thus, we need not proceed further in such a case.
 */
public class Solution {
    Set<String> set = new HashSet<>();  // Using HashSet data-structure, we can remove duplicate permutations.
    public List <String> generatePalindromes(String s) {
        int[] map = new int[128];
        char[] st = new char[s.length() / 2];   // contains all the characters of s but occurences reduced to half
        if (!canPermutePalindrome(s, map)) {
            return new ArrayList<>();   
        }
        char ch = 0;    //  the only character occured an odd number of times
        int k = 0;
        for (int i = 0; i < map.length; i++) {
            if (map[i] % 2 == 1) {
                ch = (char) i;                
            }
            // construct the st
            for (int j = 0; j < map[i] / 2; j++) {
                st[k++] = (char) i;
            }
        }
        permute(st, 0, ch);
        return new ArrayList<String>(set);
    }
    
    public boolean canPermutePalindrome(String s, int[] map) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            map[s.charAt(i)]++;
            if (map[s.charAt(i)] % 2 == 0) {
                count--;                
            }
            else {
                count++;                
            }
        }
        return count <= 1;
    }
    
    void permute(char[] s, int start, char ch) {
        if (start == s.length) {
            set.add(new String(s) + (ch == 0 ? "" : ch) + new StringBuffer(new String(s)).reverse());
        } else {
            for (int i = start; i < s.length; i++) {
                if (s[start] != s[i] || start == i) {
                    swap(s, start, i);
                    permute(s, start + 1, ch);
                    swap(s, start, i);
                }
            }
        }
    }
    
    public void swap(char[] s, int i, int j) {
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
    }
}