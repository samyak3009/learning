class ListNode{
    int val;
    ListNode next;
    ListNode(int val){
        this.val = val;
        this.next = null;
    }
}

/**
 * Stack implementation using a linked list
 * Provides LIFO (Last In, First Out) data structure
 * The last element added to the stack is the first element to be removed (LIFO)
 */
class Stack{
    private ListNode top;
    private int size;

    // constructor
    public Stack(){
        this.top = null;
        this.size = 0;
    }

    // Pushes an element onto the top of the stack
    public void push(int val){
        ListNode newNode = new ListNode(val, null);
        newNode.next = top;
        top = newNode;
        size++;
    }

    // Removes and returns the top element from the stack
    public int pop(){
        if(top == null){
            throw new IllegalStateException("Stack is empty");
        }
        int val = top.val;
        top = top.next;
        size--;
        return val;
    }

    // Returns the top element without removing it
    public int peek(){
        if(top == null){
            throw new IllegalStateException("Stack is empty");
        }
        return top.val;
    }


    // Checks if the stack is empty
    public boolean isEmpty(){
        return top == null;
    }

    // Returns the number of elements in the stack
    public int length(){
        return size;
    }
}