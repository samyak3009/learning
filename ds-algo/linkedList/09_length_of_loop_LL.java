/*
 * Problem: Length of Loop in Linked List
 * 
 * Given a linked list, if it contains a loop, find the length of the loop.
 * A loop in a linked list occurs when a node's next pointer points to a previously visited node.
 * 
 * Example:
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> 3 (points back to node with value 3)
 * Output: 3 (length of loop: 3->4->5->3)
 * 
 * Time Complexity: O(n) where n is the number of nodes in the linked list
 * - Floyd's cycle detection takes O(n) to find the meeting point
 * - Finding length of loop takes O(k) where k is the length of loop
 * 
 * Space Complexity: O(1)
 * - Only using a constant amount of extra space with pointers
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

    // find the length of the loop in the linked list
    public int detectCycle(ListNode head) {
        if(head == null || head.next == null)
            return 0;

        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast){
                return lengthOfLoop(slow);
            }
        } 
        return 0; // no cycle found
    }

    private int lengthOfLoop(ListNode slow){    
        ListNode temp = slow;
        int count = 1;
        while(temp.next != slow){
            temp = temp.next;
            count++;
        }
        return count;
    }
}