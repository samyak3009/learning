/*
 * Problem: 206. Reverse Linked List
 * Link: https://leetcode.com/problems/reverse-linked-list/description/
 * 
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 * 
 * Example:
 * Input: head = [1,2,3,4,5]
 * Output: [5,4,3,2,1]
 * 
 * Time Complexity: O(n) - where n is the number of nodes in the linked list
 * Space Complexity: O(1) - only using a constant amount of extra space
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

    // reverse the linked list
    public ListNode reverse(ListNode head){
        if(head == null || head.next == null)
            return head;

        ListNode current = head;
        ListNode prev = null;
        ListNode next = null;
        
        while(current != null){
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
}