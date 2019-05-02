'''
Implement the StreamChecker class as follows:
StreamChecker(words): Constructor, init the data structure with the given words.
query(letter): returns true if and only if for some k >= 1,
the last k characters queried (in order from oldest to newest,
including this letter just queried) spell one of the words in the given list.

Example:
StreamChecker streamChecker = new StreamChecker(["cd","f","kl"]); // init the dictionary.
streamChecker.query('a');          // return false
streamChecker.query('b');          // return false
streamChecker.query('c');          // return false
streamChecker.query('d');          // return true, because 'cd' is in the wordlist
streamChecker.query('e');          // return false
streamChecker.query('f');          // return true, because 'f' is in the wordlist
streamChecker.query('g');          // return false
streamChecker.query('h');          // return false
streamChecker.query('i');          // return false
streamChecker.query('j');          // return false
streamChecker.query('k');          // return false
streamChecker.query('l');          // return true, because 'kl' is in the wordlist

Note:
    1. 1 <= words.length <= 2000
    2. 1 <= words[i].length <= 2000
    3. Words will only consist of lowercase English letters.
    4. Queries will only consist of lowercase English letters.
    5. The number of queries is at most 40000.
'''

# Approach: Reverse Word and Search + Trie
# 解法详解参考同名 java 文件
# 时间复杂度：O(QW) Q代表查询的次数，W代表当前数据流的长度
# 空间复杂度：O(W) W代表数据流长度，因为使用了 Trie, 所以空间耗费不会大于 O(W)
class StreamChecker:

    def __init__(self, words: List[str]):
        trie = {}
        for word in words:
            subtrie = trie
            for c in word[::-1]:
                if c in subtrie:
                    subtrie = subtrie[c]
                else:
                    subtrie[c] = {}
                    subtrie = subtrie[c]
            subtrie['end'] = True
        self.trie = trie
        self.arr = []
        
    
    def query(self, letter: str) -> bool:
        arr = self.arr
        arr.append(letter)
        trie = self.trie
        for i in range(len(arr)-1, -1, -1):
            l = arr[i]
            if l not in trie: 
                return False
            else: 
                trie = trie[l]
            if 'end' in trie: 
                return True
            
        return False
        

# Your StreamChecker object will be instantiated and called as such:
# obj = StreamChecker(words)
# param_1 = obj.query(letter)