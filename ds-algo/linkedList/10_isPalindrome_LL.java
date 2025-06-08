/*
 * LeetCode 234: Palindrome Linked List
 * Link: https://leetcode.com/problems/palindrome-linked-list/
 * 
 * Problem Description:
 * Given the head of a singly linked list, return true if it is a palindrome or false otherwise.
 * 
 * Example 1:
 * Input: head = [1,2,2,1]
 * Output: true
 * 
 * Example 2:
 * Input: head = [1,2]
 * Output: false
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * - Finding middle node: O(n/2)
 * - Reversing second half: O(n/2)
 * - Comparing both halves: O(n/2)
 * 
 * Space Complexity: O(1)
 * - Only using a constant amount of extra space
 * - Modifying the input linked list in-place
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


    // check if the linked list is a palindrome
    public boolean isPalindrome(ListNode head){
        ListNode secondHalf = middleNode(head);
        secondHalf = reverse(secondHalf);
        ListNode firstHalf = head;

        while(secondHalf != null){
            if(firstHalf.val != secondHalf.val){
                return false;
            }
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }
        return true;
    }

    // find the middle node of the linked list
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        } 
        return slow;
    }

    // reverse the linked list
    public ListNode reverse(ListNode head){
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