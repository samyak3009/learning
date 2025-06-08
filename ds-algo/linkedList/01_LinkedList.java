/*
 * Problem: Design Linked List (LeetCode 707)
 * Link: https://leetcode.com/problems/design-linked-list/description/
 * 
 * Description:
 * Design your implementation of the linked list. You can choose to use a singly or doubly linked list.
 * Implement the MyLinkedList class:
 * - MyLinkedList(): Initializes the MyLinkedList object.
 * - int get(int index): Get the value of the indexth node in the linked list. If the index is invalid, return -1.
 * - void addAtHead(int val): Add a node of value val before the first element of the linked list.
 * - void addAtTail(int val): Append a node of value val as the last element of the linked list.
 * - void addAtIndex(int index, int val): Add a node of value val before the indexth node in the linked list.
 *   If index equals the length of the linked list, the node will be appended to the end of the linked list.
 *   If index is greater than the length, the node will not be inserted.
 * - void deleteAtIndex(int index): Delete the indexth node in the linked list, if the index is valid.
 * 
 * Time Complexity:
 * - get(index): O(n) - traversing to the index
 * - addAtHead(val): O(1) - constant time operation
 * - addAtTail(val): O(1) - with tail pointer
 * - addAtIndex(index, val): O(n) - traversing to the index
 * - deleteAtIndex(index): O(n) - traversing to the index
 * 
 * Space Complexity:
 * - O(1) for all operations except the overall space used by the linked list which is O(n)
 *   where n is the number of nodes in the list
 */

import java.util.*;
import java.io.*;

class MyLinkedList {

    // ListNode of LL
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

    // size of LL
    int size;

    // constructor
    public MyLinkedList() {
        this.size = 0;
    }
    
    // getter function indexing starts from 0
    public int get(int index) {
        if(index < 0 || index >= size){
            return -1;
        }

        ListNode current = head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        return current.val;
    }
    
    // Insertion at beginning
    public void addAtHead(int val) {
        if(head == null){
            head = new ListNode(val, null);
            tail = head;
        }else{
            ListNode data = new ListNode(val, head);
            head = data; 
        }
        size++;
    }
    
    // Insertion at end
    public void addAtTail(int val) {
        if(head == null){
            head = new ListNode(val, null);
            tail = head;
        }else{
            tail.next = new ListNode(val, null);
            tail = tail.next; 
        }
        size++;
    }

    // add a node at the given index
    public void addAtIndex(int index, int val) {
        if(index < 0 || index > size){
            return;
        }

        // Insertion at beginning	
        if(index == 0){
            addAtHead(val);
            return;
        }

        // Insertion at end
        if(index == size){
            addAtTail(val);
            return;
        }

        // Insertion at nth index
        ListNode current = head;

        // hold the prev node
        for(int i = 0; i < index-1; i++){
            current = current.next;
        }

        ListNode data = new ListNode(val, current.next);
        current.next = data;
        size++;
    }
    
    // delete a node at the given index
    public void deleteAtIndex(int index) {
        if(index < 0 || index >= size){
            return;
        }
        
        // reset LL
        if(size == 1){
            head = null;
            tail = null;
            size = 0;
            return;
        }

        // Deletion at beginning
        if(index == 0){
            head = head.next;
            size --;
            return;
        }
        

        ListNode current = head;

        // hold the prev node
        for(int i = 0; i < index - 1 ;i++){
            current = current.next;
        }

        if(current.next == tail){
            // Deletion at end
            current.next = null;
            tail = current;
        }else{
            // Deletion at nth index
            current.next = current.next.next;
        }
        size--;
    }
}
