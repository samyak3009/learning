/*
 * Problem: Intersection of Two Linked Lists
 * LeetCode: https://leetcode.com/problems/intersection-of-two-linked-lists/
 * 
 * Given the heads of two singly linked-lists headA and headB, return the node at which the two lists intersect.
 * If the two linked lists have no intersection at all, return null.
 * 
 * Note that the linked lists must retain their original structure after the function returns.
 * 
 * Time Complexity: O(N + M) where N and M are lengths of the two linked lists
 * - In the worst case, we need to traverse both lists once
 * - If lists have different lengths, ptr1 will traverse list1 + (list2 - intersection)
 * - ptr2 will traverse list2 + (list1 - intersection)
 * 
 * Space Complexity: O(1)
 * - Only using two pointers regardless of input size
 * - No extra data structures used
 * 
 * Algorithm:
 * - Use two pointers that traverse through both lists
 * - When a pointer reaches end of its list, redirect it to head of other list
 * - If lists intersect, pointers will meet at intersection point
 * - If no intersection, both pointers will become null
 */

class LinkedList{

    // Node of LL
    public static class ListNode{
        int val;
        ListNode next;

        public ListNode(int val, ListNode next){
            this.val = val;
            this.next = next;
        }
    }

    ListNode head;

    public LinkedList(){
        this.head = null;
    }


    // find the intersection of two LL
    public ListNode getIntersectionNode(ListNode headA, ListNode headB){
        if(headA == null || headB == null)
            return null;

        ListNode a = headA;
        ListNode b = headB;

        // if a and b have different length, then loop will break in 2nd iteration otherwise it will break in 1st iteration
        while(a != b){
            //for the end of first iteration, we just reset the pointer to the head of another linkedlist
            //Our operations in first iteration will help us counteract the difference in length in diff length LL
            //If no intersection, both pointers will become null and loop will break
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;

        }
        return a;
    }
}