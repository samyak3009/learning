class Stack{
    private int [] arr;
    private int top;
    private int size;

    // constructor
    public Stack(int size){
        this.arr = new int[size];
        this.top = -1;
        this.size = size;
    }

    // Pushes an element onto the top of the stack
    public void push(int val){
        if(top == size - 1){
            throw new IllegalStateException("Stack is full");
        }
        arr[++top] = val;
    }

    // Removes and returns the top element from the stack
    public int pop(){
        if(top == -1){
            throw new IllegalStateException("Stack is empty");
        }
        int val = arr[top];
        top--;
        return val;
    }

    // Returns the top element without removing it
    public int peek(){
        if(top == -1){
            throw new IllegalStateException("Stack is empty");
        }
        return arr[top];
    }

    // Checks if the stack is empty
    public boolean isEmpty(){
        return top == -1;
    }

    // Returns the number of elements in the stack
    public int length(){
        return top + 1;
    }
}