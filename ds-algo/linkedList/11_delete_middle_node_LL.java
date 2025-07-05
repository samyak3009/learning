/*
 * LeetCode #2095 - Delete the Middle Node of a Linked List
 * https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/
 * 
 * Problem Description:
 * You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.
 * The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start using 0-based indexing.
 * 
 * Example:
 * Input: head = [1,3,4,7,1,2,6]
 * Output: [1,3,4,1,2,6]
 * Explanation: The middle node is 7, which gets deleted.
 * 
 * Approach:
 * - Use the fast and slow pointer technique (Floyd's algorithm)
 * - Fast pointer moves twice as fast as the slow pointer
 * - When fast reaches the end, slow will be at the node just before the middle
 * - We keep track of the node before the middle to perform deletion
 * - Special case: If list has only one node, return null
 * 
 * Time Complexity: O(n) - where n is the number of nodes in the linked list
 * Space Complexity: O(1) - only using constant extra space
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


    // delete the middle node of the linked list
    public ListNode deleteMiddle(ListNode head) {
        if(head == null || head.next == null)
            return null;
        
        ListNode slow = head;
        // will trace prev node of middle node
        ListNode fast = head.next.next;
        
        while (fast != null && fast.next != null)
        {
            slow = slow.next;
            fast = fast.next.next;
        }
        slow.next= slow.next.next;
        return head;
    }
}