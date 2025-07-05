/*
 * Problem: 328. Odd Even Linked List
 * Link: https://leetcode.com/problems/odd-even-linked-list/
 * 
 * Given the head of a singly linked list, group all the nodes with odd indices together 
 * followed by the nodes with even indices, and return the reordered list.
 * The first node is considered odd, and the second node is even, and so on.
 * Note that the relative order inside both the even and odd groups should remain as it was in the input.
 * You must solve the problem in O(1) extra space complexity and O(n) time complexity.
 * 
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [1,3,5,2,4]
 * 
 * Example 2:
 * Input: head = [2,1,3,5,6,4,7]
 * Output: [2,3,6,7,1,5,4]
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * - We traverse each node exactly once
 * 
 * Space Complexity: O(1)
 * - We only use a constant amount of extra space with pointers
 * - No extra space that grows with input size
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


    // separate the odd and even nodes of the linked list
    public ListNode oddEvenList(ListNode head){
        if(head == null || head.next == null) 
            return head;

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        // separate the odd and even nodes
        // even will always be ahead of odd
        while(even != null && even.next != null){
            odd.next = odd.next.next;
            even.next = even.next.next;
            odd = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}