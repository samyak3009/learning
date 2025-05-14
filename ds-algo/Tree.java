import java.util.*;
class Main{
  public static void main(String[] args) {
    Scanner sam = new Scanner(System.in);

  }
  void inorder(Node n){
    inorder(n.left);
    System.out.println(n.key);
    inorder(n.right);
  }
  void postorder(Node n){
    postorder(n.left);
    postorder(n.right);
    System.out.println(n.key);
  }
  void preorder(Node n){
    System.out.println(n.key);
    preorder(n.left);
    preorder(n.right);
  }
  int height(Node n){
    if(n==null)
      return 0;
    else
      return Math.max(height(n.left,n.right)+1);
  }
  //Tree traversal
  void BFS(Node n){
    if(n==null)
      return;
    Queue<Node> q = new LinkedList<>();
    q.add(n);
    while(!q.isEmpty()){
      int k = q.size();
      for(int i=0;i<k;i++){
        Node n = q.poll();
        System.out.print(n.key+" ");
        if(n.left!=null)
          q.add(n.left);
        if(n.right!=null)
          q.add(n.right);
      }
      System.out.println();
    }
  }

  void printkdist(Node n,int k){
    if(n==null)
      return;
    if(k==0) System.out.print(n.key+" ");
    else{
      printkdist(n.left,k-1);
      printkdist(n.right,k-1);
    }
  }

  int maximum(Node n){
    if(n==null)
      return Integer.MIN_VALUE;
    return Math.max(n.key,Math.max(maximum(n.left),maximum(n.right)));
  }

  void leftview(Node n){
    if(n==null)
      return;
    Queue<Node> q = new LinkedList<>();
    q.add(n);
    while(!q.isEmpty()){
      int k = q.size();
      for(int i=0;i<k;i++){
        Node p = q.poll();
        if(i==0)
          System.out.println(p.key);
        if(p.left!=null)
          q.add(p.left);
        if(p.right!=null)
          q.add(p.right);
      }
      System.out.println();
    }
  }


  void rightview(Node n){
    if(n==null)
      return;
    Queue<Node> q = new LinkedList<>();
    q.add(n);
    while(!q.isEmpty()){
      int k = q.size();
      for(int i=0;i<k;i++){
        Node p = q.poll()
 ;       if(i==k-1)
          System.out.println(p.key);
        if(p.left!=null)
          q.add(p.left);
        if(p.right!=null)
          q.add(p.right);
      }
      System.out.println();
    }
  }

  boolean childsum(Node n){
    if(n==null)
      return true;
    if(n.left == null && n.right == null)
      return true;
    int sum=0;
    if(n.left!=null)
      sum+=n.left.key;
    if(n.right!=null)
      sum+=n.right.key;
    return (n==sum && childsum(n.left) && childsum(n.right));
  }

  int balance(Node n){
    if(n==null)
      return 0;
    int lft = balance(n.left);
    if(lft == -1) return -1;
    int rht = balance(n.right);
    if(rht == -1) return -1;
    if(Math.abs(lft-rht)>1)
      return -1;
    return Math.max(lft,rht)+1;
  }

  int res =0;
  int height(Node n){
      if(n==null) return 0;
      int lh = height(n.left);
      int rh = height(n.right);
      res = Math.max(res, 1+lh+rh);
      return 1+ Math.max(lh,rh);
  }



  void mirror(Node n){
    if(n==null)
      return;
    Node num;
    mirror(n.left);
    mirror(n.right);
    curr = n.left;
    n.left = n.right;
    n.right = curr;
  }
}