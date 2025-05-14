import java.util.*;
import java.io.*;  
public class Main{  
    public static void main(String[] args)
    {
      Scanner sam = new Scanner(System.in);
      int n = sam.nextInt();
      int [] a = new int [n];
      for(int i=0;i<n;i++)
          a[i] = sam.nextInt();
      reverse(a,n);
      printarry(a,n);
      System.out.println(maximum(a,n));
      System.out.println(minimum(a,n));
    }
    ///////////////////1//////////////////////////
    public static void reverse(int [] a, int n){
      int i=0,j=n-1;
      while(i<=j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        i++;
        j--;
      }
    }
    //////////////////////2//////////////////////////
    public static int maximum(int [] a, int n){
      int max = a[0];
      for(int i=1;i<n;i++)
        if(max<a[i])
          max = a[i];
      return max;
    }
    public static int minimum(int [] a,int n){
      int min = a[0];
      for(int i=1;i<n;i++)
        if(min>a[i])
          min = a[i];
      return min;
    }
    ////////////////////print Array/////////////////////
    public static void printarry(int [] a, int n){
      for(int i=0;i<n;i++)
        System.out.print(a[i]+" ");
    }
    ////////////////////////////4////////////////////////
    public static void sort102(int [] a, int n){
      int l =0,mid =0, h = n-1;
      while(mid<=h){
        switch(a[mid]){
          case 0:{
            int temp = a[l];
            a[l] = a[mid];
            a[mid] = temp;
            mid++;
            l++;
            break;
          }case 1:
            mid++;
            break;
          case 2:{
            int temp = a[mid];
            a[mid] = a[h];
            a[h] = temp;
            h--;
            break;
          }
        }
      } 
    }
    /////////////(5)Move all negative numbers to beginning and positive to end//////////
    public static void rearrange(int [] a,int n){
      int k = 0;
      for(int i=0;i<n;i++){
        if(a[i]<0){
          int temp = a[k];
          a[k] = a[i];
          a[i] = temp;
          k++;
        }
      }
    }
    /////////////////////(6) union of two unsorted Array//////////////////////////////////////////
    public static int doUnion(int a[], int n, int b[], int m) 
    {
        Set<Integer> s = new HashSet<Integer>();
        for(int i = 0;i<n;i++)
            s.add(a[i]);
        for(int i=0;i<m;i++)
            s.add(b[i]);
        return s.size();
    }
    ////////////////////(7)rotate by one left///////////////////////////////////
    public static void rotate(int [] a,int n){
      int temp = a[n-1];
      for(int i=n-1;i>0;i++)
        a[i] = a[i-1];
      a[0] = temp;
    }
    ///////////////////(8)largest continues sum array ////////////////////////////
    public static int kadane(int [] arr, int n){
      int a = Integer.MIN_VALUE, b=0;
      for(int i=0;i<n;i++){
        b+=a[i];
        if(a<b)
          a=b;
        if(b<0)
          b=0;
      }
      return a;
    }
    //////////////////////(9)minimum height///////////////////////////////
    int getMinDiff(int[] arr, int n, int k) 
    {
        Arrays.sort(arr);
        int smallest=arr[0]+k;
        int largest=arr[n-1]-k;
        int mi,ma,res=arr[n-1]-arr[0];
        for(int i=0;i<n-1;i++)
        {
            mi=Math.min(smallest,arr[i+1]-k);
            ma=Math.max(largest,arr[i]+k);
            System.out.println(mi+" "+ma);
            if(mi<0)
                continue;
            res=Math.min(res,ma-mi);
        }
        return res;
    }
    ///////////////////////////////////
    public int [][] merge(int [][]arr){
      if(arr.length<=1)
        return arr;
      Arrays.sort(arr,(i1,i2)-> Integer.compare(i1[0],i2[0]));
      List<int []> result = new ArrayList<>();
      int [] newInterval = arr[0];
      result.add(newInterval);
      for(int [] interval: arr){
        if(interval[0]<=newInterval[1]){
          newInterval = Math.min(interval[1],newInterval[1]);
        }else{
          newInterval = interval;
          result.add(newInterval);
        }
      }
      return result.toArray(new int[result.size()][]);
    }
}