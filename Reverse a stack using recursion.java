/*
This is a quetions on: http://www.geeksforgeeks.org/reverse-a-stack-using-recursion/
Description:
Write a program to reverse a stack using recursion. 
You are not allowed to use loop constructs like while, for..etc, 
and you can only use the following ADT functions on Stack S:
    isEmpty(S)
    push(S)
    pop(S)
*/

// Approach 1
import java.util.Stack;

class Main
{
    //using Stack class for stack implementation
    static Stack<Character> st = new Stack<>();
     
    // Below is a recursive function that delete an element
    // at the bottom of a stack and return it.
    static char remove_LastElement() {
        char rst = st.pop();
        if (st.isEmpty()) {
            return rst;
        } else {
            char last = remove_LastElement();
            st.push(rst);
            return last;
        }
    }
     
    // Below is the function that reverses the given stack using
    // remove_LastElement()
    static void  reverse()
    {
        if (st.isEmpty()) {
            return;
        }
        char c = remove_LastElement();
        reverse();
        st.push(c);
    }
 
     
    // Driver method
    public static void main(String[] args) 
    {
        //push elements into the stack
        st.push('1');
        st.push('2');
        st.push('3');
        st.push('4');
         
        System.out.println("Original Stack");
         
        System.out.println(st);
         
        //function to reverse the stack
        reverse();
         
        System.out.println("Reversed Stack");
         
        System.out.println(st);
    }
}


// Approach 2
import java.util.Stack;

class Main
{
    //using Stack class for stack implementation
    static Stack<Character> st = new Stack<>();
     
    // Below is a recursive function that inserts an element
    // at the bottom of a stack.
    static void insert_at_bottom(char x) {
        if (st.isEmpty()) {
            st.push(x);
        } else {
        /* All items are held in Function Call Stack until we
           reach end of the stack. When the stack becomes
           empty, the st.size() becomes 0, the
           above if part is executed and the item is inserted
           at the bottom */
            char a = st.peek();
            st.pop();
            insert_at_bottom(x);
 
            //push all the items held in Function Call Stack
            //once the item is inserted at the bottom
            st.push(a);
        }
    }
     
    // Below is the function that reverses the given stack using
    // insert_at_bottom()
    static void  reverse()
    {
        if (st.size() > 0) {
            /* Hold all items in Function Call Stack until we
               reach end of the stack */
            char x = st.peek();
            st.pop();
            reverse();
            /* Insert all the items held in Function Call Stack
               one by one from the bottom to top. Every item is
               inserted at the bottom */
            insert_at_bottom(x);
        }
    }
 
     
    // Driver method
    public static void main(String[] args) 
    {
        //push elements into the stack
        st.push('1');
        st.push('2');
        st.push('3');
        st.push('4');
         
        System.out.println("Original Stack");
         
        System.out.println(st);
         
        //function to reverse the stack
        reverse();
         
        System.out.println("Reversed Stack");
         
        System.out.println(st);
    }
}