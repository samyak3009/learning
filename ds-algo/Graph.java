public class Graph{
   public static void main(String[] args) {
      
   }
   public static void addedge(ArrayList<ArrayList<Integer>> adj, int u, int v){
      adj.get(u).add(v);
      adj.get(v).add(u);
   }
   //////////////////TRAVERSAL OF GRAPH///////////////////////////////
   //////////////////   BFS-   //////////////////////////////////////
   // given a sourse vertex s from which all the connected component are printed from 
   // that source vertex.
   public static void bfs(ArrayList<ArrayList<Integer>> adj,int v, int s,boolean [] visited){
      Queue<Integer> q = new LinkedList<>();
      visited[s] = true;
      q.add(s);
      while(!q.isEmpty()){
         int k = q.poll();
         System.out.println(k);
         //////////////////////////
         for(int w : adj.get(k)){
            if(visited[w] == false){
               visited[w] = true;
               q.add(w);
            }
         }
         //////////////////////////
      }
   }
   //( BFS )-when no source vertex is given /////////////////
   // this will cover all the unconnected component also////
   public static void bfsdis(ArrayList<ArrayList<Integer>> adj, int v){
      boolean visited [] = new boolean[v+1];
      //int count=0;
      for(int i=0;i<v;i++){
         if(visited[i] == false){
            bfs(adj,v,i,visited)
            //count++
            // for counting the no of discounnected graph componenet
         }
      }
   }
   //////////////////////////////////////////////////////////////////
   //////////////////  DFS   ///////////////////////////////////////
   // when source vertex is given 
   public static void dfs(ArrayList<ArrayList<Integer>> adj,int v, int s){
      boolean [] visited = new boolean[v+1];
      dfsfun(adj,v,s,visited);
   }
   public static void dfsfun(ArrayList<ArrayList<Integer>> adj,int v,int s, boolean[]visited){
      System.out.println(s);
      visited[s] = true;
      //////////////////////////////////////
      for (int w : adj.get(s)) {
         if(visited[w] == false)
            dfsfun(adj,v,w,visited); //recursion call
      }
      /////////////////////////////////////
   }
   //when no source vertex is given 
   // count the no the disconnected components
   public static void dfscount(ArrayList<ArrayList<Integer>> adj, int v){
      boolean [] visited = new boolean[v+1];
      int count = 0;
      for(int i=0;i<v;i++){   
         if(visited[i]==false){
            count++;
            dfs(adj,v,i,visited);
         }
      }
   }
   /////////////////////////////////////////////////////////////////
   // shortest path from the source vertex to every other given vertex 
   // in a given weighted graph.
   // BFS - Solution
   // can be done same as it is in DFS
   public static int [] shortestpath_bfs(ArrayList<ArrayList<Integer>> adj, int v, int s){
      int [] dis = new int [v+1];
      boolean [] visited = new boolean[v+1];
      Queue<Integer> q = new LinkedList<>();
      Array.fills(result,Integer.MIN_VALUE);
      q.add(s);
      visited[s] = true;
      dis[s] = 0;
      while(!q.isEmpty()){
         int k = q.poll();
         ////////////////////////////////////
         for(int w : adj.get(k)){
            if(visited[w] == false){
               visited[w] = true;
               q.add(w);
               dis[w] = dis[k]+1;
            }
         }
         ///////////////////////////////////
      }
   }
   //////////////////////////////////////////////////////////////////
   // Detect cycle in an undirected graph
   // DFS - Solution
   public static boolean dfs_check_iscylce_undirect(ArrayList<ArrayList<Integer>> adj, int v,int s, int parent, boolean[]visited){
      visited[s] = true;
      ////////////////////////////////////////
      for(int w : adj.get(s)){
         if(visited[w] == false && dfs_check_iscylce_undirect(adj,v,w,s,visited)){
               return true;
         }else if(w!=parent)// base condition repeation of vertx 
            return true;    // here we if the repeated vertex is not the parent vertex then their is cycle
      }
      ////////////////////////////////////////
      return false;
   }
   public static boolean iscylce_undirect(ArrayList<ArrayList<Integer>> adj, int v){
      boolean [] visited = new boolean[v+1];
      for(int i=0;i<v;i++){
         if(visited[i] == false)
            if(dfs_check_iscylce_undirect(adj,v,i,-1,visited))// pass -1 in parent
               return true;
      }
      return false;
   }
   ///////////////////////////////////////////////////////////////////
   // Detect cycle in an Directed graph
   // DFS - Solution
   public static boolean dfs_check_iscylce_direct(ArrayList<Integer<Integer>> adj, int v, int s,
      boolean[]visited, boolean [] parent){
      visited[s] = true;
      parent[s] = true;
      ///////////////////////////////////////////
      for(int w : adj.get(s)){
         if(visited[w] == false && dfs_check_iscylce_direct(adj,v,w,visited,parent))
            return true;
         else if(parent[w] == true)// base condition to chech the previusly connected vertex.
            return true;
      }
      //////////////////////////////////////////
      parent[s] = false;
      return false; 
   }
   public static boolean iscylce_direct(ArrayList<ArrayList<Integer>> adj, int v){
      boolean [] visited = new boolean[v+1];
      boolean [] parent = new boolean[v+1];
      for(int i=0;i<v;i++){
         if(visited[i]==false && dfs_check_iscylce_direct(adj,v,i,visited,parent))
            return true;
      }
      return false;
   }
   ///////////////////////////////////////////////////////////////////
   // Detect cycle in undirected using BFS solution
   // BFS - Solution
   static class Node{
      int vertex;
      int parent;
      Node(int x, int y){
         vertex = x;
         parent = y;
      }
   }
   public static boolean iscylce_bfs(ArrayList<ArrayList<Integer>> adj, int v){
      boolean [] visited = new boolean[v+1];
      for(int i=0;i<v;i++){
         if(visited[i]==false && check_iscylce_bfs(adj,v,i,visited))
            return true;
      }
      return false;
   }
   public static boolean check_iscylce_bfs(ArrayList<ArrayList<Integer>> adj, int v, int s, boolean [] visited){
      Queue<Node> q = new LinkedList<>();
      Node e = new Node(s,-1);
      q.add(e);
      visited[s] = true;
      while(!q.isEmpty()){
         int vertex = q.peek().vertex;
         int parent = q.peek().vertex;
         q.poll();
         /////////////////////////////////
         for(int w : adj.get(vertex)){
            if(visited[w] == false)
               q.add(w,vertex);
            else if(parent != w)
               return true;
         }
         ////////////////////////////////
      }
      return false;
   }
   ///////////////////////////////////////////////////////////////////
   // Topological Sorting in BFS
   // Detect cylce in Directed graph is same in BFS.
   // Khan's Algorithum.

   // 1. topological sorting only works when graph does not have any cycle.
   // 2. topological sorting is always for Directed Acyclic Graph.

   /* BFS -solution (khan's Algorithum)
      Algorithum
      1. store the indegree of every vertex.
      2. create a queue q
      3. add all the 0 indegree element in the queue.
      4. while(!q.isEmpty()){
         int k = q.pop();
         print(k);
         for(int w : adj.get(k)){
            indegree[w]--;
            if(indegree[w]==0)
               q.add(w);
         }
      }
   */
   public static void topo_iscylce_bfs_directed(ArrayList<ArrayList<Integer>> adj, int v){
      int [] indegree = new int[v+1];
      // find the indegree of every vertex
      for(int i=0;i<v;i++)
         for(int w : adj.get(i))
            indegree[w]++;
      Queue<Integer> q = new LinkedList<Integer>();
      for(int i=0;i<v;i++)
         if(indegree[i]==0)
            q.add(i);
      int count = 0;
      while(!q.isEmpty()){
         int k = q.poll();
         System.out.println(k);
         //////////////////////////
         for(int w : adj.get(k)){
            if(--indegree[w]==0)
               q.add(w);
         }
         count++;
         //////////////////////////
      }
      if(count != v)
         System.out.println("Cycle is detected");
   }
   //////////////////////////////////////////////////////////////////////////////
   // Topological sorting - DFS Solution
   public static void sort(ArrayList<ArrayList<Integer>> adj, int v,int w,Stack<Integer> s, boolean[] visited){
      visited[w] = true;
      for(int e : adj.get(w)){
         if(visited[e]==false)
            sort(adj,v,e,s,visited);
      }
      s.push(w);
   }
   public static void topological(ArrayList<ArrayList<Integer>> adj, int v){
      Stack<Integer> s = new Stack<Integer>();
      boolean [] visited = new boolean[v+1];
      for(int i=0;i<v;i++){
         if(visited[i]==false)
            sort(adj,v,i,s,visited);
      }
      while(!s.isEmpty())
         System.out.println(s.pop());
   }
   //////////////////////////////////////////////////////////////////////////////
   //Bi-Partile
   boolean checkBipartile(ArrayList<ArrayList<Integer>> adj,int v){
      int [] color = new int [v+1];
      Array.fills(color,-1);
      //////////////////////////////////
      for(int i=0;i<v;i++){
         if(color[i] == -1 && ! bfsbipartile(adj,v,i,color)){
            return false;
         }
      }
      return true;
      //////////////////////////////////
   }
   // BFS - function 
   boolean bfsbipartile(ArrayList<ArrayList<Integer>> adj,int v, int w,int [] color){
      color[w] = 1;
      Queue<Integer> q = new LinkedList<>();
      q.add(w);
      while(!q.isEmpty()){
         int k = q.poll();
         for(int e : adj.get(k)){
            if(color[e]==-1){
               color[e] = 1-color[k]; // main concept of 0 and 1 .
               q.add(e);
            }else if(color[e]==color[k]) // base condition
               return false;
         }
      }
      return true;
   }
   // DFS - function
   boolean dfsbipartile(ArrayList<ArrayList<Integer>> adj, int v, int w,int [] color){
      if(w==-1)// if it is a starting vertex of disconnected graph 
         color[w]=1;
      for(int e : adj.get(w)){
         if(color[e]==-1){
            color[e] = 1-color[w];
            if(!dfsbipartile(adj,v,e,color))
               return false;
         }else if(color[e]==color[w])
            return false;
      }
      return true;
   }
   //////////////////////////////////////////////////////////////////////////////
   /////////////////////////Dijkstra algorithum//////////////////////////////////
   // it is basically for Undirected Weighted graph ////////////////////////////////
   // find the shortest distance of all the node from a given source node of a given 
   // undirected weighted graph.

   //////////// weighted graph structure ////////////////////////
   static class Node implements comparator<Node>{
      int vertex;
      int weight;
      Node(int vertex, int weight){
         this.vertex = vertex;
         this.weight = weight;
      }
      public int compare(Node node1,Node node2){
         if(node1.weight<node2.weight)
            return -1;
         else if(node1.weight>node2.weight)
            return 1;
         return 0;
      }
   }
   //Dijkrasta Algo
   public static int [] shortestpath(ArrayList<ArrayList<Node>> adj,int v, int s){
      int [] dis = new int [v+1];
      Array.fills(dis,Integer.MAX_VALUE);
      dis[s] = 0;
      // here min heap is used //////////
      PriorityQueue<Node> pq = new PriorityQueue<>();
      pd.add(new Node(s,0));
      while(!pq.isEmpty()){
         Node node = pq.poll();
         ////////////////////////////////
         for(Node x : adj.get(node.vertex)){
            if(dis[node.vertex]+x.weight < dis[x.vertex]){
               dist[x.vertex] = dist[node.vertex] + x.weight; 
               pq.add(new Node(x.vertex,dis[x.vertex] ));
            }
         }
         ///////////////////////////////
      } 
   }
   ///////////////////////////////////////////////////////////////////////////////
   // Shortest Path from the given source vertex in an Directed Acyclic Graph (DAG).
   /*
   Algorithum
   step 1. we will do the topologocal sorting using DFS using a stack 
   step 2. after that we will initiate dis[s] = 0.
   step 3. run a loop of stack and run the relaxation function over the loop.
   */
   public static void sortit(ArrayList<ArrayList<Node>> adj,int v,int s,Stack<Integer> stack,
      boolean [] visited){
      visited[s] = true;
      for(Node e : adj.get(s)){
         if(visited[e.vertex] == false)
            sortit(adj,v,e.vertex,stack,visited);
      } 
      stack.add(s);
   }
   public static void shortestpathDAG(ArrayList<ArrayList<Node>> adj, int v, int s){
      Stack<Integer> stack = new Stack<Integer>();
      int [] dis = new int [v+1];
      Array.fills(dis,Integer.MAX_VALUE);
      boolean [] visited = new boolean [v+1]; 
      for(int i=0;i<v;i++){
         if(visited[i]==false){
            sortit(adj,v,i,s,visited);
         }
      }
      dis[s] = 0;
      while(!stack.isEmpty()){
         int k = stack.poll();
         for(Node e : adj.get(k)){
            if(dis[k]+e.weight < dis[e.vertex])
               dis[e.vertex] = dis[k]+e.weight;
         }
      } 
   } 
   //////////////////////////////////////////////////////////////////////////////////
   // Prim's Algorithum 
   // to generate a mininum spanning tree of a given graph

   // Node class is used as the data type

   public static void primsAlgo(ArrayList<ArrayList<Node>> adj, int v){
      int [] key = new int [v+1];
      boolean [] visited = new boolean[v+1];
      int [] parent = new int [v+1];
      Array.fills(key,Integer.MAX_VALUE);
      Array.fills(parent,-1);
      PriorityQueue<Node> pq = new PriorityQueue<Node>(v, new Node());
      // add the first source index as by default zero.
      key[0] = 0;
      pq.add(new Node(0,key[0]));
      while(!pq.isEmpty()){
         int u = pq.poll().vertex;
         visited[u] = true;
         /////////////////////////////
         for(Node e : adj.get(u)){
            if(visited[e.vertex] == false && key[e.vertex] > e.weight){
               parent = u;
               key[e.vertex] = e.weight;
               pq.add(new Node(e.vertex,key[e.vertex]));
            }
         }
         /////////////////////////////
      }
   }
   ///////////////////////////////////////////////////////////////////////////////////
   
}