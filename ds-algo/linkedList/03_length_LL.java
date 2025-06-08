/*
 * Problem: Find Length of Linked List
 * 
 * Description:
 * Given the head of a singly linked list, return the number of nodes in the linked list.
 * This is a fundamental operation used in many linked list problems.
 * 
 * Example:
 * Input: head = [1,2,3,4,5]
 * Output: 5
 * 
 * Input: head = []
 * Output: 0
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * - We need to traverse through each node exactly once
 * 
 * Space Complexity: O(1)
 * - We only use a single variable to keep track of count
 * - No extra space is used that grows with input size
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

    // find the length of the linked list
    public int length(){
        int length = 0;
        ListNode current = head;
        while(current != null){
            length++;
            current = current.next;
        }
        return length;
    }
}