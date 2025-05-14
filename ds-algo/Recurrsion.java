import java.util.*;
public class Recurrsion{
    public static void main(String[] args) {
		Scanner sam = new Scanner(System.in);
		int n = sam.nextInt();
		int [] a = new int [n];
		for(int i=0;i<n;i++){
			a[i] = sam.nextInt();
		}
		// subsequence(a);
		// subseqsumk(a,3);
		// subseqonesumk(a,3);
		// combination(a,3);
		permute2(a);
		// queen(4);
    }            
  	//// print all the subsequence of a given array///////
	public static void subsequence(int [] arr){
		ArrayList<Integer> b = new ArrayList<Integer>();
		subfun(arr,0,b);
	}
	public static void subfun(int [] arr, int ind, ArrayList<Integer> b){
		if(ind == arr.length){
			printlist(b);
			return;
		}
		b.add(arr[ind]);
		subfun(arr,ind+1,b);
		b.remove(b.size()-1);
		subfun(arr,ind+1,b);
	}
	///////////////////////////////////////////////////////////
	////////////////print all the subsequence whose sum is equal to k /////
	public static void subseqsumk(int [] arr, int k){
		ArrayList<Integer> b = new ArrayList<Integer>();
		funsubsumk(arr,0,b,k);
	}
	public static void funsubsumk(int [] arr, int ind,ArrayList<Integer> b, int k){
		if(ind == arr.length){
			if(k==0){
				printlist(b);
			}
			return;
		}
		if(arr[ind]<=k){
			b.add(arr[ind]);
			funsubsumk(arr,ind+1,b,k-arr[ind]);
			b.remove(b.size()-1);
		}
		funsubsumk(arr,ind+1,b,k);
	}
	///////////////////////////////////////////////////////////////////////
	/////////////// print only one subsequence whose sum is k /////////////
	public static void subseqonesumk(int [] arr, int k){
		ArrayList<Integer> b = new ArrayList<Integer>();
		funsubonesumk(arr,0,b,k);
	}
	public static boolean funsubonesumk(int [] arr, int ind,ArrayList<Integer> b, int k){
		if(ind == arr.length){
			if(k==0){
				printlist(b);
				return true;
			}
			return false;
		}
		if(arr[ind]<=k){
			b.add(arr[ind]);
			if(funsubonesumk(arr,ind+1,b,k-arr[ind]))
				return true;
			b.remove(b.size()-1);
		}
		 return funsubonesumk(arr,ind+1,b,k);
	}
	/////////////////////////////////////////////////////////////////////////
	/////////////////////combination-I///////////////////////////////////////
	/////each element can be repeated any no of time to sum-up equal to k ///
	public static void combination(int [] arr, int sum){
		ArrayList<Integer> b = new ArrayList<Integer>();
		combsum(arr,0,sum,b);
	}
	public static void combsum(int arr[], int ind, int k, ArrayList<Integer> b){
		if(ind==arr.length){
			if(k==0)
				printlist(b);
			return;
		}
		if(k>=arr[ind]){
			b.add(arr[ind]);
			combsum(arr,ind,k-arr[ind],b);
			b.remove(b.size()-1);
		}
		combsum(arr,ind+1,k,b);
	}
	// Note that this question form the binary recursion tree of TAKE or NOT_TAKE the element
	// when it go with the TAKE then it call by function without increasing the index so that 
	// the process repeat by considering the same index element as in the question mention that 
	// we can repeat the element in the subsequence
	///////////////////////////////////////////////////////////////////////////

	/////////////////////////////Combination-II////////////////////////////////
	//Given a collection of candidate numbers (candidates) and a target number (target), 
	//find all unique combinations in candidates where the candidate numbers sum to target.
	//Each number in candidates may only be used once in the combination.
	//Note: The solution set must not contain duplicate combinations.
	// Input: candidates = [10,1,2,7,6,1,5], target = 8
	// Output: [[1,1,6],[1,2,5],[1,7],[2,6]]

	public static void combination2(int [] arr, int k){
		ArrayList<Integer> b = new ArrayList<Integer>();
		//we will first sort the element of the given arry so that the combination we get 
		//will be in the sorted form.
		Arrays.sort(arr);
		com2sum(arr,b,0,k);
	}
	public static void com2sum(int[] arr, ArrayList<Integer>b, int ind,int k){
		if(k == 0){
			printlist(b);
			return;
		}
		for(int i=ind;i<arr.length;i++){
			if(arr[i]>k) break;
			// check if the next element is equal to or not if equal then we will skip it 
			// as we have to find the unique combination.
			if(i>ind && arr[i] == arr[i-1]) continue;
			b.add(arr[i]);
			com2sum(arr,b,i+1,k-arr[i]);
			// note here if we want to allow the repetation of the element in the combination 
			// then here in the parameter we will pass 'i' in place of 'i+1'.
			b.remove(b.size()-1);
		}
	}
	// Note that this combination will not form the binary tree it will form the n-ary tree
	// as in this we are runnning loop of n.
	//////////////////////////////////////////////////////////////////////////////
	//////////////////// print the all sub-set sum////////////////////////////////
	public void subsetSums(int [] arr){
		  sub(arr,0,0);
		  System.out.println();
	 }
	public void sub(int [] arr, int sum, int i){
		  if(i==arr.length){
				System.out.print(sum+" ");
				return;
		  }
		  sub(arr,sum+arr[i],i+1);
		  sub(arr,sum,i+1);
	}
	//////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////subset-2/////////////////////////////////
	//Given an integer array nums that may contain duplicates, 
	//return all possible subsets (the power set).
	//The solution set must not contain duplicate subsets.Return the solution in any order.
	public static void subsetsWithDup(int[] nums) {
		  ArrayList<Integer> b = new ArrayList<Integer>();
		  Arrays.sort(nums);
		  sub2(b,nums,0);
	 }
	public static void sub2(ArrayList<Integer>b, int [] nums, int ind){
		  printlist(b);
		  for(int i = ind;i<nums.length;i++){
				if(i>ind && nums[i] == nums[i-1]) continue;
				b.add(nums[i]);
				sub2(b,nums,i+1);
				b.remove(b.size()-1);
		  }
	 }
	//////////////////////////////////////////////////////////////////////////////
	///////////////////Premutation of Array/String////////////////////////////////
	public static void permute(int [] arr){
		ArrayList<Integer> b = new ArrayList<Integer>();
		boolean [] f = new boolean[arr.length];
		funpermute(b,arr,f);
	}
	public static void funpermute(ArrayList<Integer> b, int [] arr,boolean [] f){
		if(b.size() == arr.length){
			printlist(b);
			return;
		}
		for(int i=0;i<arr.length;i++){
			if(!f[i]){
				f[i] = true;
				b.add(arr[i]);
				funpermute(b,arr,f);
				b.remove(b.size()-1);
				f[i] = false;
			}
		}
	}
	//////////////////////////////////////////////////////////////////////////
	//////////////////////////Approach-2/////////////////////////////////////
	public static void swap(int i, int j, int [] arr){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	public static void permute2(int [] arr){
		funpermute2(0,arr); 
	}
	public static void funpermute2(int index, int [] arr){
		if(index == arr.length){
			printarr(arr);
			return;
		}
		for(int i=index;i<arr.length;i++){
			swap(index,i,arr);
			funpermute2(index+1,arr);
			swap(i,index,arr);
		}
	}
	////////////////////////////////////////////////////////////////////////
	////////////////// N*N Queen Problem ///////////////////////////////////
	public static void queen(int n){
		ArrayList<ArrayList<String>> s = new ArrayList<ArrayList<String>>();
		char [][] chess = new char [n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				chess[i][j] = '.';
		int [] side = new int [n];
		int [] lower = new int [2*n-1];
		int [] upper = new int [2*n-1];
		queenfun(s,chess,lower,upper,side,0,n);
		for(int i=0;i<s.size();i++)
			for(int j=0;j<s.get(i).size();j++)
				System.out.println(s.get(i).get(j));
			System.out.println(); 
	}
	public static void queenfun(ArrayList<ArrayList<String>>s, char[][]chess,
		int[]lower,int[]upper,int[]side,int col,int n){
		if(col==n){
			ArrayList<String> a = new ArrayList<String>();
			for(int i=0;i<n;i++){
				a.add(new String(chess[i]));
			}
			s.add(a);
			return;      
		}
		for(int row=0;row<n;row++){
			if(lower[n-1+col-row]==0 && upper[col+row]==0 && side[row]==0){
				lower[n-1+col-row]=1;
				upper[col+row]=1;
				side[row]=1;
				chess[row][col]='Q';
				queenfun(s,chess,lower,upper,side,col+1,n);
				lower[n-1+col-row]=0;
				upper[col+row]=0;
				side[row]=0;
				chess[row][col]='.';
			}
		}
	}
	//////////////////////// //////////////////////////////////////////////////////
	/////////////////////Sudoku probem 9*9 ////////////////////////////////////////
	static boolean SolveSudoku(int grid[][])
	{
		  for(int i=0;i<grid.length;i++){
				for(int j=0;j<grid[i].length;j++){
					 // check for null
					 if(grid[i][j]==0){
						  // run loop check for the values
						  for(int k=1;k<=9;k++){
								// taken value of 'k' and check if it is valid or not
								if(check(k,i,j,grid)){
									 // if valid then take it
									 grid[i][j] = k;
									 if(SolveSudoku(grid))
										  return true;
									 grid[i][j] = 0;
								}
						  }
						  return false;
					 }
				}
		  }
		  return true;
	 }
	 static boolean check(int val,int row,int col,int [][]grid){
		  for(int i=0;i<grid.length;i++){
				if(grid[row][i] == val || grid[i][col] == val)
					 return false;
				else if(grid[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == val)// line to be remember
					 return false;
		  }
		  return true;
	 }

	/////////////////////////////////////////////////////////////////
	//........................m-colouring-problem ...................................... 
	public static boolean graphColoring(List<Integer>[] G, int[] color, int i, int m) 
	{
		  // Your code here
		int n = G.length;
		return colouring(0,G,color,n,m);
		  
	}
	public static boolean colouring(int node, List<Integer>[] G,int [] color,int n, int m){
		if(node == n)
			return true;
		for(int i=1;i<=m;i++){
			if(issafe(node,G,color,n,i)){
				color[node] = i;
				if(colouring(node+1,G,color,n,m))
					return true;
				color[node] = 0;
			}
		}
		return false;
	 }
	 private static boolean issafe(int node, List<Integer>[] G, int[] color, int n, int col) {
		for(int it: G[node]) {
			if(color[it] == col) return false; 
		}
		return true; 
	 }
	/////////////////////////////////////////////////////////////////
	//.........................Palindrome Partitioning.....................................
	/*
	Given a string s, partition s such that every substring of the partition is a palindrome. 
	Return all possible palindrome partitioning of s.
	A palindrome string is a string that reads the same backward as forward.
	Example 1:
	Input: s = "aab"
	Output: [["a","a","b"],["aa","b"]]
	
	Example 2:
	Input: s = "a"
	Output: [["a"]]
	*/
	//.....................................................................................
   public List<List<String>> partition(String s) {
      List<List<String>> adj = new ArrayList<List<String>>();
      List<String> a = new ArrayList<String>();
      function(adj,a,s,0);
      return adj;
   }
    public void function(List<List<String>> adj,List<String> a, String s, int i){
        if(i == s.length()){
            adj.add(new ArrayList<>(a));
            return;
        }
        for(int j=i;j<s.length();j++){
            if(palindrone(s,i,j)){
                a.add(s.substring(i,j+1));
                function(adj,a,s,j+1);
                a.remove(a.size()-1);
            }   
        }
    }
    public boolean palindrone(String s, int start, int end){
        while(start<=end){
            if(s.charAt(start++)!=s.charAt(end--))
                return false;
        }
        return true;
    }
   	////////////////////////////////////////////////////////////////////////////////
    //////////////////// Rat in Maze ////////////////////////////////
    /*
	Consider a rat placed at (0, 0) in a square matrix of order N * N. It has to reach 
	the destination at (N - 1, N - 1). Find all possible paths that the rat can take to 
	reach from source to destination. The directions in which the rat can move are 'U'(up), 'D'(down), 'L' (left), 'R' (right). Value 0 at a cell in the matrix represents that it is blocked and rat cannot move to it while value 1 at a cell in the matrix represents that rat can be travel through it.
	Note: In a path, no cell can be visited more than one time.

    Input:
		N = 4
		m[][] = {{1, 0, 0, 0},
		         {1, 1, 0, 1}, 
		         {1, 1, 0, 0},
		         {0, 1, 1, 1}}
	Output:
		DDRDRR DRDDRR
		Explanation:
		The rat can reach the destination at 
		(3, 3) from (0, 0) by two paths - DRDDRR 
		and DDRDRR, when printed in sorted order 
		we get DDRDRR DRDDRR.*/

    public static ArrayList<String> findPath(int[][] m, int n) {
        // Your code here
        ArrayList<String> adj = new ArrayList<String>();
        boolean [][] visited = new boolean [n][n];
        if(m[0][0]==1)
            function(adj,"",m,n,0,0,visited);
        if(adj.size()>0)
            return adj;
        adj.add("-1");
        return adj;
    }
    public static void function(ArrayList<String> adj,String s,int [][]m,int n,int row,int col,boolean [][]visited){
        if(row == n-1 && col == n-1){
            adj.add(new String(s));
            return;
        }
        for(int i=0;i<4;i++){
            if(check(i,row,col,n,m,visited)){
                if(i==0){
                    visited[row][col]=true;
                    function(adj,s+"D",m,n,row+1,col,visited);
                    visited[row][col]=false;
                }else if(i==1){
                    visited[row][col]=true;
                    function(adj,s+"R",m,n,row,col+1,visited);
                    visited[row][col]=false;
                }else if(i==2){
                    visited[row][col]=true;
                    function(adj,s+"L",m,n,row,col-1,visited);
                    visited[row][col]=false;
                }else if(i==3){
                    visited[row][col]=true;
                    function(adj,s+"U",m,n,row-1,col,visited);
                    visited[row][col]=false;
                }
                
            }
        }
    }
    public static boolean check(int i,int row,int col,int n,int [][] m, boolean [][] mm){
        if(i==0){// down
            if(row+1<n && m[row+1][col]==1 && mm[row+1][col]==false)
                return true;
        }else if(i==1){ // left
            if(col+1<n && m[row][col+1]==1 && mm[row][col+1]==false)
                return true;
        }else if(i==2){ // right
            if(0<=col-1 && m[row][col-1]==1 && mm[row][col-1]==false)
                return true;
        }else if(i==3){ // up move
            if(0<=row-1 && m[row-1][col]==1 && mm[row-1][col]==false)
                return true;
        }
        return false;
    }
    ///////////////////////////////////////////////////////////////////////////
    /////////////////Kth permutation problem /////////////////////////////////
	/*
	The set [1, 2, 3, ..., n] contains a total of n! unique permutations.
	By listing and labeling all of the permutations in order, we get the
	following sequence for n = 3:

	"123"
	"132"
	"213"
	"231"
	"312"
	"321"
	
	Given n and k, return the kth permutation sequence.
	*/
    public static void Kthpermutation(int n, int k){
    	System.out.println(Kthpermutationfunction(n,k));
    }
    public static String Kthpermutationfunction(int n,int k){
    	int fact =1;
    	List<Integer> num = new ArrayList<Integer>();
    	for(int i=1;i<n;i++){
    		fact = fact*1;
    		num.add(i);
    	}
    	String ans = "";
    	num.add(n);
    	k = k-1;
    	while(true){
    		ans = num.get(k/fact);
    		num.remove(k/fact);
    		if(num.size()==0)
    			break;
    		k = k%fact;
    		fact = fact/num.size();
    	}
    	return ans;
    }
    ///////////////////////////////////////////////////////////////////
	/////////////print the arry in arr[] form//////////////////////////
	public static void printarr(int [] arr){
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
		}
		System.out.println();
 	}
    ////////////print the arry list //////////////////////////////////
    public static void printlist(ArrayList<Integer> arr){
		for(int x : arr){
			System.out.print(x+" ");
		}
		System.out.println();
   }


}