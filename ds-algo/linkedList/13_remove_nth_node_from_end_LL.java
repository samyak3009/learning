/*
Problem 19: Remove Nth Node From End of List
Link: https://leetcode.com/problems/remove-nth-node-from-end-of-list/

Given the head of a linked list, remove the nth node from the end of the list and return its head.

Example:
Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]

Time Complexity: O(n) - where n is the length of the linked list
Space Complexity: O(1) - only using two pointers regardless of input size
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


    // remove the nth node from the end of the linked list
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode current = head;

        int i = 0;
        while(i < n -1){
            current = current.next;
            i++;
        }

        ListNode temp = head;
        ListNode prev = null;
        while(current.next != null){
            prev = temp;
            temp = temp.next;
            current = current.next;
        }

        // check if the first node is the nth node from the end
        if(temp == head) {
            return head.next;
        }
        prev.next = temp.next;

        return head;
    }
}