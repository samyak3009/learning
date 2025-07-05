# LinkedList Problems - Quick Revision Guide

## Table of Contents
1. [Basic Operations](#basic-operations)
2. [Two Pointer Techniques](#two-pointer-techniques)
3. [Cycle Detection](#cycle-detection)
4. [Reversal & Palindrome](#reversal--palindrome)
5. [Node Manipulation](#node-manipulation)
6. [Advanced Problems](#advanced-problems)

---

## Basic Operations

### 1. Design Linked List (LeetCode 707)
**Problem**: Implement a complete LinkedList with CRUD operations
**Time Complexity**: 
- get(index): O(n)
- addAtHead(val): O(1)
- addAtTail(val): O(1)
- addAtIndex(index, val): O(n)
- deleteAtIndex(index): O(n)
**Space Complexity**: O(1) for operations, O(n) for overall list
```java
class MyLinkedList {
    ListNode head, tail;
    int size;
    
    public int get(int index) {
        if(index < 0 || index >= size) return -1;
        ListNode current = head;
        for(int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.val;
    }
    
    public void addAtHead(int val) {
        if(head == null) {
            head = new ListNode(val, null);
            tail = head;
        } else {
            head = new ListNode(val, head);
        }
        size++;
    }
    
    public void addAtTail(int val) {
        if(head == null) {
            head = new ListNode(val, null);
            tail = head;
        } else {
            tail.next = new ListNode(val, null);
            tail = tail.next;
        }
        size++;
    }
    
    public void addAtIndex(int index, int val) {
        if(index < 0 || index > size) return;
        
        if(index == 0) {
            addAtHead(val);
            return;
        }
        
        if(index == size) {
            addAtTail(val);
            return;
        }
        
        ListNode current = head;
        for(int i = 0; i < index-1; i++) {
            current = current.next;
        }
        
        ListNode newNode = new ListNode(val, current.next);
        current.next = newNode;
        size++;
    }
    
    public void deleteAtIndex(int index) {
        if(index < 0 || index >= size) return;
        
        if(size == 1) {
            head = null;
            tail = null;
            size = 0;
            return;
        }
        
        if(index == 0) {
            head = head.next;
            size--;
            return;
        }
        
        ListNode current = head;
        for(int i = 0; i < index-1; i++) {
            current = current.next;
        }
        
        if(current.next == tail) {
            current.next = null;
            tail = current;
        } else {
            current.next = current.next.next;
        }
        size--;
    }
}
```

### 2. Delete Node in Linked List (LeetCode 237)
**Problem**: Delete a node without access to head
**Time Complexity**: O(1)
**Space Complexity**: O(1)
```java
public void deleteNode(ListNode node) {
    ListNode nextNode = node.next;
    node.val = nextNode.val;
    node.next = nextNode.next;
    nextNode.next = null;
}
```

### 3. Find Length
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public int length(ListNode head) {
    int length = 0;
    ListNode current = head;
    while(current != null) {
        length++;
        current = current.next;
    }
    return length;
}
```

### 4. Search in Linked List
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public boolean search(ListNode head, int data) {
    ListNode current = head;
    while(current != null) {
        if(current.val == data) return true;
        current = current.next;
    }
    return false;
}
```

---

## Two Pointer Techniques

### 5. Middle of Linked List (LeetCode 876)
**Problem**: Find middle node (second middle if even length)
**Time Complexity**: O(n)
**Space Complexity**: O(1)
**Important**: For even number of nodes, returns the **SECOND** middle node
```java
public ListNode middleNode(ListNode head) {
    if(head == null || head.next == null) return head;
    
    ListNode slow = head, fast = head;
    while(fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}
```

**Examples:**
- **Odd nodes**: [1,2,3,4,5] → returns node with value 3
- **Even nodes**: [1,2,3,4] → returns node with value 3 (second middle)
- **Even nodes**: [1,2,3,4,5,6] → returns node with value 4 (second middle)

### 6. Find Nth Node from End
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public ListNode findNthNodeFromEnd(ListNode head, int n) {
    ListNode current = head, temp = head;
    
    // Move current n-1 positions ahead
    for(int i = 0; i < n-1; i++) {
        current = current.next;
    }
    
    // Move both pointers
    while(current.next != null) {
        temp = temp.next;
        current = current.next;
    }
    return temp;
}
```

### 7. Remove Nth Node from End (LeetCode 19)
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode current = head;
    
    // Move current n-1 positions ahead
    for(int i = 0; i < n-1; i++) {
        current = current.next;
    }
    
    ListNode temp = head, prev = null;
    while(current.next != null) {
        prev = temp;
        temp = temp.next;
        current = current.next;
    }
    
    if(temp == head) return head.next;
    prev.next = temp.next;
    return head;
}
```

---

## Cycle Detection

### 8. Detect Loop (LeetCode 141)
**Problem**: Check if linked list has cycle
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public boolean hasCycle(ListNode head) {
    if(head == null || head.next == null) return false;
    
    ListNode slow = head, fast = head;
    while(fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if(slow == fast) return true;
    }
    return false;
}
```

### 9. Find Cycle Start (LeetCode 142)
**Problem**: Find starting point of cycle
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public ListNode detectCycle(ListNode head) {
    if(head == null || head.next == null) return null;
    
    ListNode slow = head, fast = head;
    while(fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if(slow == fast) {
            return getCycleStart(slow, head);
        }
    }
    return null;
}

private ListNode getCycleStart(ListNode slow, ListNode head) {
    ListNode temp = head;
    while(temp != slow) {
        temp = temp.next;
        slow = slow.next;
    }
    return temp;
}
```

### 10. Length of Loop
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public int detectCycleLength(ListNode head) {
    if(head == null || head.next == null) return 0;
    
    ListNode slow = head, fast = head;
    while(fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if(slow == fast) {
            return lengthOfLoop(slow);
        }
    }
    return 0;
}

private int lengthOfLoop(ListNode slow) {
    ListNode temp = slow;
    int count = 1;
    while(temp.next != slow) {
        temp = temp.next;
        count++;
    }
    return count;
}
```

---

## Reversal & Palindrome

### 11. Reverse Linked List (LeetCode 206)
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public ListNode reverse(ListNode head) {
    if(head == null || head.next == null) return head;
    
    ListNode current = head, prev = null, next = null;
    while(current != null) {
        next = current.next;
        current.next = prev;
        prev = current;
        current = next;
    }
    return prev;
}
```

### 12. Palindrome Linked List (LeetCode 234)
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public boolean isPalindrome(ListNode head) {
    if(head == null || head.next == null) return true;
    
    ListNode secondHalf = middleNode(head);
    secondHalf = reverse(secondHalf);
    ListNode firstHalf = head;
    
    while(secondHalf != null) {
        if(firstHalf.val != secondHalf.val) return false;
        firstHalf = firstHalf.next;
        secondHalf = secondHalf.next;
    }
    return true;
}
```

---

## Node Manipulation

### 13. Delete Middle Node (LeetCode 2095)
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public ListNode deleteMiddle(ListNode head) {
    if(head == null || head.next == null) return null;
    
    ListNode slow = head;
    // will trace prev node of middle node that why we are using head.next.next
    ListNode fast = head.next.next;
    
    while(fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    slow.next = slow.next.next;
    return head;
}
```

### 14. Odd Even Linked List (LeetCode 328)
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
public ListNode oddEvenList(ListNode head) {
    if(head == null || head.next == null) return head;
    
    ListNode odd = head, even = head.next, evenHead = even;
    
    while(even != null && even.next != null) {
        odd.next = odd.next.next;
        even.next = even.next.next;
        odd = odd.next;
        even = even.next;
    }
    odd.next = evenHead;
    return head;
}
```

---

## Advanced Problems

### 15. Sort List (LeetCode 148)
**Problem**: Sort linked list using merge sort
**Time Complexity**: O(n log n)
**Space Complexity**: O(log n) - due to recursive call stack
```java
public ListNode sortList(ListNode head) {
    if(head == null || head.next == null) return head;
    
    // Break into two halves
    ListNode firstHalf = getMiddle(head);
    ListNode secondHalf = firstHalf.next;
    firstHalf.next = null;
    
    // Sort recursively
    ListNode left = sortList(head);
    ListNode right = sortList(secondHalf);
    
    // Merge sorted halves
    return merge(left, right);
}

private ListNode merge(ListNode left, ListNode right) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    
    while(left != null && right != null) {
        if(left.val < right.val) {
            current.next = left;
            left = left.next;
        } else {
            current.next = right;
            right = right.next;
        }
        current = current.next;
    }
    
    if(left != null) current.next = left;
    if(right != null) current.next = right;
    
    return dummy.next;
}
```

### 16. Intersection of Two Linked Lists (LeetCode 160)
**Time Complexity**: O(N + M) where N and M are lengths of the two lists
**Space Complexity**: O(1)
```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if(headA == null || headB == null) return null;
    
    ListNode a = headA, b = headB;
    
    while(a != b) {
        a = a == null ? headB : a.next;
        b = b == null ? headA : b.next;
    }
    return a;
}
```

### 17. Add Two Numbers (LeetCode 2)
**Time Complexity**: O(max(N, M)) where N and M are lengths of the input lists
**Space Complexity**: O(max(N, M)) to store the result
```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode current = dummy;
    int carry = 0;
    
    while(l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if(l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        if(l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }
        
        int val = sum % 10;
        carry = sum / 10;
        
        current.next = new ListNode(val);
        current = current.next;
    }
    return dummy.next;
}
```

---

## Key Patterns to Remember

### 1. Fast and Slow Pointer (Floyd's Algorithm)
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
ListNode slow = head, fast = head;
while(fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
// slow is at middle
```

### 2. Reversing a LinkedList
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
ListNode prev = null, current = head, next = null;
while(current != null) {
    next = current.next;
    current.next = prev;
    prev = current;
    current = next;
}
head = prev;
```

### 3. Dummy Node Pattern
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
ListNode dummy = new ListNode(0);
ListNode current = dummy;
// ... operations ...
return dummy.next;
```

### 4. Two Pointer for Nth from End
**Time Complexity**: O(n)
**Space Complexity**: O(1)
```java
ListNode current = head, temp = head;
for(int i = 0; i < n-1; i++) current = current.next;
while(current.next != null) {
    temp = temp.next;
    current = current.next;
}
```

---

## Time & Space Complexity Summary

| Problem | Time Complexity | Space Complexity |
|---------|----------------|------------------|
| Design Linked List | O(n) for get/addAtIndex/deleteAtIndex, O(1) for addAtHead/addAtTail | O(1) for operations, O(n) overall |
| Delete Node | O(1) | O(1) |
| Find Length | O(n) | O(1) |
| Search | O(n) | O(1) |
| Middle Node | O(n) | O(1) |
| Nth from End | O(n) | O(1) |
| Remove Nth from End | O(n) | O(1) |
| Detect Loop | O(n) | O(1) |
| Find Cycle Start | O(n) | O(1) |
| Loop Length | O(n) | O(1) |
| Reverse List | O(n) | O(1) |
| Palindrome | O(n) | O(1) |
| Delete Middle | O(n) | O(1) |
| Odd Even List | O(n) | O(1) |
| Sort List | O(n log n) | O(log n) |
| Intersection | O(N + M) | O(1) |
| Add Two Numbers | O(max(N,M)) | O(max(N,M)) |

---

## Common Edge Cases
1. Empty list (null head)
2. Single node
3. Two nodes
4. Even/odd length lists
5. Circular lists
6. Lists with duplicates
7. Lists of different lengths

---

## Quick Tips
- Always check for null before dereferencing
- Use dummy nodes to simplify edge cases
- Fast/slow pointer is your friend for middle and cycle problems
- For reversal, remember: prev → current → next
- When in doubt, draw the linked list on paper 