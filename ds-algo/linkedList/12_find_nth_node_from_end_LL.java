/*
 * Problem: Find the nth node from the end of a Linked List
 * LeetCode Link: https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * 
 * Given the head of a linked list and an integer n, return the nth node from the end of the list.
 * 
 * Example:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: Node with value 4 (2nd node from end)
 * 
 * Time Complexity: O(N) where N is the length of the linked list
 * Space Complexity: O(1) as we only use two pointers
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


    // find the nth node from the end of the linked list
    public ListNode findNthNodeFromEnd(ListNode head, int n){
        ListNode current = head;
        ListNode temp = head;
        // Move ref n -1 positions
        int i = 0;
        while(i < n -1){
            current = current.next;
            i++;
        }

        // Move both pointers
        while(current.next != null){
            temp = temp.next;
            current = current.next;
        }

        // temp is the nth node from the end
        return temp;
    }
}