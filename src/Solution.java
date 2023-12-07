import java.util.*;
class Solution {
    class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public int wordIndex = -1;
        public List<Integer> restIsPalindrome = new ArrayList<>();

        public TrieNode() {

        }
    }
    TrieNode root = new TrieNode();
    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> palindromePairs(String[] words) {
        int n = words.length;

        for(int i=0; i<n; i++){
            add(words[i], i);
        }

        for(int i=0; i<n; i++){
            search(words[i], i);
        }

        return res;
    }

    public void add(String word, int wordIndex){
        char[] chs = word.toCharArray();
        TrieNode cur = root;
        //倒敘
        for(int i=chs.length-1; i>=0; i--){
            //取出第幾個字母下標
            int j = chs[i] - 'a'; //0~25
            //如果0~i為回文，當前的restIsPalindrome就存下當前的wordIndex
            if(isPalindrome(chs, 0, i)){
                cur.restIsPalindrome.add(wordIndex);
            }

            //如果該字母代表children不存在
            if(cur.children[j] == null){
                //new
                cur.children[j] = new TrieNode();
            }
            //往下走
            cur = cur.children[j];
        }
        //最後存下有一個wordIndex
        cur.wordIndex = wordIndex;
    }

    public void search(String word, int wordIndex){
        char[] chs = word.toCharArray();
        TrieNode cur = root;
        for(int i=0; i<chs.length; i++){
            int j = chs[i] - 'a'; //第幾個字母
            //如果當前的Trie是一個字，且chs的i~len為回文
            //case 3
            // 後面為回文，前面的有倒續文字 zyx llxyz
            // 這個case存在x中有restIsPalindrome
            if(cur.wordIndex != -1 && isPalindrome(chs, i, chs.length-1)) {
                res.add(Arrays.asList(wordIndex, cur.wordIndex));
            }
            //如果children為空就不要走了
            if(cur.children[j] == null)
                return;
            cur = cur.children[j];
        }

        //這邊要全部走完
        //case 1 發現存在 abcd dcba
        // 注意 aaa 不能加自己
        if(cur.wordIndex != -1 && cur.wordIndex != wordIndex){
            res.add(Arrays.asList(wordIndex, cur.wordIndex));
        }

        // case 2
        // xyzll zyx
        for(int j : cur.restIsPalindrome){
            res.add(Arrays.asList(wordIndex, j));
        }
    }

    public boolean isPalindrome(char[] chs, int i, int j){
        while(i<j){
            if(chs[i++]!=chs[j--]) return false;
        }
        return true;
    }
    public int numBusesToDestination(int[][] routes, int source, int target) {
        //base case
        if(source == target){
            return 0;
        }

        HashMap<Integer, List<Integer>> adjList = new HashMap<Integer, List<Integer>>();
        for(int i=0; i<routes.length; i++){
            for(int j=0; j<routes[i].length; j++){
                List<Integer> lst = adjList.getOrDefault(routes[i][j], new ArrayList<>());
                lst.add(i);
                adjList.put(routes[i][j], lst);
            }
        }

        // bfs
        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> que = new LinkedList<>();
        que.offer(source);
        int steps = 0;

        while(!que.isEmpty()){
            steps++;
            int queueSize = que.size();
            for(int i=0; i<queueSize; i++){
                int cur = que.poll();

                for(int bus : adjList.get(cur)){
                    if(visited.contains(bus)){
                        continue;
                    }

                    visited.add(bus);

                    for(int stop : routes[bus]){
                        if(stop == target){
                            return steps;
                        }
                        que.offer(stop);
                    }
                }

            }
        }

        return -1;
    }
}