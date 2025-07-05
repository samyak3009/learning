/*
Problem: 141. Linked List Cycle
Link: https://leetcode.com/problems/linked-list-cycle/

Given head, the head of a linked list, determine if the linked list has a cycle in it.
There is a cycle in a linked list if there is some node in the list that can be reached 
again by continuously following the next pointer.

Time Complexity: O(n) - where n is the number of nodes in the linked list
                We may need to visit each node once.
                
Space Complexity: O(1) - we only use two pointers (slow and fast) regardless of input size.
                 Floyd's Cycle Finding Algorithm (also known as "tortoise and hare")
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

    // detect loop in the linked list
    public boolean hasCycle(ListNode head){
        if(head == null || head.next == null)
            return false;

        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){   
                return true;
            }
        }
        return false;
    }
}