/*
 * Problem: 237. Delete Node in a Linked List
 * Link: https://leetcode.com/problems/delete-node-in-a-linked-list/description/
 * 
 * You are given the node to be deleted in a singly linked list. 
 * You will NOT be given access to the head of the list.
 * Instead, you will be given access to the node to be deleted directly.
 * It is guaranteed that the node to be deleted is not a tail node in the list.
 * 
 * Example:
 * Input: head = [4,5,1,9], node = 5
 * Output: [4,1,9]
 * Explanation: You are given the second node with value 5, the linked list 
 * should become 4 -> 1 -> 9 after calling your function.
 * 
 * Time Complexity: O(1) - We only need to modify the given node and its next node
 * Space Complexity: O(1) - We don't use any extra space
 */

import java.util.*;
import java.io.*;

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
    ListNode tail;
    int size;

    public LinkedList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    // delete a given node from the linked list
    public void deleteNode(ListNode node) {
        ListNode nextNode = node.next;
        node.val = nextNode.val;
        node.next = nextNode.next;
        nextNode.next = null;
    }
}