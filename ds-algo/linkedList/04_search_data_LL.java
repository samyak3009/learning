/*
 * Problem: Search in a Linked List
 * 
 * Problem Description:
 * Given a linked list and a value, determine if the value exists in the linked list.
 * Return true if the value is found, false otherwise.
 * 
 * Time Complexity: O(n) - where n is the number of nodes in the linked list
 *                 We may need to traverse the entire linked list in worst case
 * 
 * Space Complexity: O(1) - only using a single pointer variable for traversal
 *                  No extra space is used that grows with input size
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

    // search for a data in the linked list
    public boolean search(int data){
        ListNode current = head;
        while(current != null){
            if(current.val == data){
                return true;
            }
            current = current.next;
        }
        return false;
    }
}