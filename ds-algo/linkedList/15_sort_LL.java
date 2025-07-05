/*
 * Problem: 148. Sort List
 * Link: https://leetcode.com/problems/sort-list/
 * 
 * Given the head of a linked list, return the list after sorting it in ascending order.
 * 
 * Example:
 * Input: head = [4,2,1,3]
 * Output: [1,2,3,4]
 * 
 * Constraints:
 * - The number of nodes in the list is in the range [0, 5 * 10^4]
 * - -10^5 <= Node.val <= 10^5
 * 
 * Solution Approach:
 * - Uses Merge Sort algorithm adapted for Linked List
 * - Recursively divides the list into two halves
 * - Sorts each half independently
 * - Merges the sorted halves
 * 
 * Time Complexity: O(n log n) 
 * - Dividing the list: O(log n) levels
 * - At each level, merging takes O(n)
 * - Total: O(n log n)
 * 
 * Space Complexity: O(log n)
 * - Due to recursive call stack depth
 * - No extra space used for data storage
 */

class LinkedList{
    
    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null) 
            return head;

        // break the list into two halves
        ListNode firstHalf =  getMiddle(head);
        ListNode secondHalf = firstHalf.next;
        firstHalf.next = null;

        // sort the two halves recursively
        ListNode left = sortList(head);
        ListNode right = sortList(secondHalf);

        // merge the two sorted halves into a single sorted list
        return merge(left, right);
    }

    // merge the two sorted halves into a single sorted list
    public ListNode merge(ListNode left, ListNode right){
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while(left != null && right != null){
            if(left.val < right.val){
                current.next = left;
                left = left.next;   
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }   

        // if any of the list is not empty, it must already be sorted the left LL
        // so we can just add the remaining nodes to the end of the merged list
        if(left != null){
            current.next = left;
        }

        // if any of the list is not empty, it must already be sorted the right LL
        // so we can just add the remaining nodes to the end of the merged list
        if(right != null){
            current.next = right;
        }

        // dummy.next is the head of the merged list
        return dummy.next;
    }

    public ListNode getMiddle(ListNode head){
        if(head == null || head.next == null)
            return head;

        ListNode slow = head;
        // will trace prev node of middle node that why we are using head.next.next
        ListNode fast = head.next.next;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}