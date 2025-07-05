/*
 * Problem: Add Two Numbers (LeetCode #2)
 * Link: https://leetcode.com/problems/add-two-numbers/
 * 
 * Description:
 * You are given two non-empty linked lists representing two non-negative integers.
 * The digits are stored in reverse order, and each of their nodes contains a single digit.
 * Add the two numbers and return the sum as a linked list.
 * 
 * Example:
 * Input: 
 *    l1 = [2→4→3] represents number 342
 *    l2 = [5→6→4] represents number 465
 * Output: 
 *    [7→0→8] represents number 807
 * 
 * Detailed calculation:
 *    342
 *  + 465
 *  -----
 *    807
 * 
 * Step by step:
 * 1. 2+5=7  (first node)
 * 2. 4+6=10 (carry 1, put 0 in second node)
 * 3. 3+4+1=8 (1 was carried over)
 * 
 * Time Complexity: O(max(N, M)) where N and M are lengths of the input linked lists
 * Space Complexity: O(max(N, M)) to store the result linked list
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


    // add two numbers represented by linked lists
    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        int carry = 0;

        while(l1 != null || l2 != null || carry != 0){
            int sum = carry;
            if(l1 != null){
                sum += l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                sum += l2.val;
                l2 = l2.next;
            }
            int val = sum % 10;
            carry = sum / 10;
            
            // create a new node with the value of the sum and add it to the current node
            current.next = new ListNode(val);
            current = current.next;
        }
        return dummy.next;
    }
}