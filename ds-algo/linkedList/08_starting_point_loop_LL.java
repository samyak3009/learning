/*
 * Problem: 142. Linked List Cycle II
 * Link: https://leetcode.com/problems/linked-list-cycle-ii/
 * 
 * Given the head of a linked list, return the node where the cycle begins. 
 * If there is no cycle, return null.
 * 
 * There is a cycle in a linked list if there is some node in the list that can be 
 * reached again by continuously following the next pointer.
 * 
 * Do not modify the linked list.
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * - Finding the intersection point using slow and fast pointers takes O(n)
 * - Finding the cycle start using two pointers takes O(n)
 * 
 * Space Complexity: O(1)
 * - Only using a constant amount of extra space with the two pointers
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

    // find the starting point of the loop in the linked list
    public ListNode detectCycle(ListNode head) {
        if(head == null || head.next == null)
            return null;

        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                ListNode result = getPoint(slow, head);
                return result;
            }
        } 
        return null;
    }
    public ListNode getPoint(ListNode slow, ListNode head){
        ListNode temp = head;
        while(temp != slow){
            temp = temp.next;
            slow = slow.next;
        }
        return temp;
    }
}