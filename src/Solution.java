import java.util.*;

public class Solution {
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