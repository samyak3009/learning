# ⚡ JavaScript Event Loop - Interview Revision Guide

> Quick revision guide covering Event Loop, Call Stack, Task Queue, and Microtasks

## Table of Contents

- [Core Concept](#core-concept)
- [Event Loop Architecture](#event-loop-architecture)
- [Call Stack](#call-stack)
- [Task Queues](#task-queues)
- [Execution Order](#execution-order)
- [Common Patterns](#common-patterns)
- [Interview Questions](#interview-questions)

---

## Core Concept

JavaScript is **single-threaded** but can handle asynchronous operations using the **Event Loop**.

**Key Points:**
- One call stack = One thing at a time
- Event Loop manages async operations
- Web APIs handle background tasks
- Queues hold callbacks waiting to execute

---

## Event Loop Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        JAVASCRIPT ENGINE                        │
│                                                                 │
│  ┌──────────────┐                                               │
│  │  Call Stack  │  ← Executes code synchronously                │
│  │              │                                               │
│  │   main()     │                                               │
│  │   foo()      │                                               │
│  │   bar()      │                                               │
│  └──────────────┘                                               │
│                                                                 │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │              Memory Heap (Variables/Objects)             │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              ▲
                              │
                              │ Event Loop monitors
                              │ and pushes callbacks
                              │
┌─────────────────────────────┴───────────────────────────────────┐
│                        EVENT LOOP                               │
│                                                                 │
│  Checks: Is Call Stack empty?                                   │
│  Then: Move tasks from queues to Call Stack                     │
│                                                                 │
│  Priority Order:                                                │
│  1. Microtask Queue (Promises, queueMicrotask)                  │
│  2. Task Queue (setTimeout, setInterval, I/O)                   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
                              ▲
                              │
                ┌─────────────┴─────────────┐
                │                           │
                │                           │
    ┌───────────▼──────────┐    ┌──────────▼──────────┐
    │  Microtask Queue     │    │     Task Queue      │
    │  (Higher Priority)   │    │  (Lower Priority)   │
    │                      │    │                     │
    │  • Promises          │    │  • setTimeout       │
    │  • queueMicrotask    │    │  • setInterval      │
    │  • MutationObserver  │    │  • setImmediate     │
    │  • process.nextTick  │    │  • I/O operations   │
    │    (Node.js)         │    │  • UI rendering     │
    └──────────────────────┘    └─────────────────────┘
                ▲                           ▲
                │                           │
                │                           │
    ┌───────────┴──────────┐    ┌──────────┴──────────┐
    │   Promise.then()     │    │   Web APIs          │
    │   async/await        │    │                     │
    │   .catch()           │    │  • DOM events       │
    │   .finally()         │    │  • fetch()          │
    └──────────────────────┘    │  • XMLHttpRequest   │
                                │  • Timers           │
                                └─────────────────────┘
```

---

## Call Stack

The **Call Stack** is where JavaScript keeps track of function execution.

### How It Works

```
Last In, First Out (LIFO)

┌─────────────┐
│   bar()     │  ← Top (currently executing)
├─────────────┤
│   foo()     │
├─────────────┤
│   main()    │
└─────────────┘  ← Bottom
```

### Example:

```javascript
function multiply(a, b) {
  return a * b;
}

function square(n) {
  return multiply(n, n);
}

function printSquare(n) {
  const result = square(n);
  console.log(result);
}

printSquare(4);

/*
Call Stack Execution:

1. Push: printSquare(4)
   ┌────────────────┐
   │ printSquare(4) │
   └────────────────┘

2. Push: square(4)
   ┌────────────────┐
   │   square(4)    │
   ├────────────────┤
   │ printSquare(4) │
   └────────────────┘

3. Push: multiply(4, 4)
   ┌────────────────┐
   │ multiply(4, 4) │
   ├────────────────┤
   │   square(4)    │
   ├────────────────┤
   │ printSquare(4) │
   └────────────────┘

4. Pop: multiply(4, 4) returns 16
   ┌────────────────┐
   │   square(4)    │
   ├────────────────┤
   │ printSquare(4) │
   └────────────────┘

5. Pop: square(4) returns 16
   ┌────────────────┐
   │ printSquare(4) │
   └────────────────┘

6. Push: console.log(16)
   ┌────────────────┐
   │ console.log(16)│
   ├────────────────┤
   │ printSquare(4) │
   └────────────────┘

7. Pop: console.log(16)
   ┌────────────────┐
   │ printSquare(4) │
   └────────────────┘

8. Pop: printSquare(4) - Stack empty!
   ┌────────────────┐
   │     EMPTY      │
   └────────────────┘
*/
```

---

## Task Queues

### Microtask Queue (High Priority)

Executed **immediately** after current script, before tasks.

```javascript
// Microtasks
Promise.resolve().then(() => console.log('Promise'));
queueMicrotask(() => console.log('Microtask'));
// process.nextTick() in Node.js - even higher priority
```

### Task Queue (Low Priority)

Executed **after** all microtasks are done.

```javascript
// Tasks (formerly called Macrotasks)
setTimeout(() => console.log('Timeout'), 0);
setInterval(() => console.log('Interval'), 1000);
setImmediate(() => console.log('Immediate')); // Node.js
```

### Priority Order

```
┌──────────────────────────────────────┐
│  1. Synchronous Code (Call Stack)    │  ← Highest Priority
├──────────────────────────────────────┤
│  2. Microtasks (Promise.then, etc)   │  ← High Priority
├──────────────────────────────────────┤
│  3. Tasks (setTimeout, etc)          │  ← Low Priority
└──────────────────────────────────────┘
```

---

## Execution Order

### Complete Flow Diagram

```
START
  │
  ▼
┌─────────────────────────────────────┐
│  Execute Synchronous Code           │
│  (Push/Pop from Call Stack)         │
└─────────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────────┐
│  Call Stack Empty?                  │
└─────────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────────┐
│  Execute ALL Microtasks             │
│  (Promise.then, queueMicrotask)     │
│  Until Microtask Queue is EMPTY     │
└─────────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────────┐
│  Execute ONE Task                   │
│  (setTimeout, setInterval, etc)     │
└─────────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────────┐
│  Execute ALL Microtasks Again       │
│  (if any were created)              │
└─────────────────────────────────────┘
  │
  ▼
┌─────────────────────────────────────┐
│  Render UI (if browser)             │
└─────────────────────────────────────┘
  │
  └──► Loop back (check for more tasks)
```

### Example:

```javascript
console.log('1: Sync');

setTimeout(() => console.log('2: Timeout'), 0);

Promise.resolve().then(() => console.log('3: Promise'));

console.log('4: Sync');

/*
Execution Steps:

Step 1: Execute synchronous code
  Call Stack: console.log('1: Sync')
  Output: "1: Sync"

Step 2: Register setTimeout (goes to Web API)
  Task Queue: [timeout callback]

Step 3: Register Promise.then (goes to Microtask Queue)
  Microtask Queue: [promise callback]

Step 4: Execute synchronous code
  Call Stack: console.log('4: Sync')
  Output: "4: Sync"

Step 5: Call Stack empty! Check Microtask Queue
  Execute: Promise callback
  Output: "3: Promise"

Step 6: Microtask Queue empty! Check Task Queue
  Execute: setTimeout callback
  Output: "2: Timeout"

Final Output:
1: Sync
4: Sync
3: Promise
2: Timeout
*/
```

---

## Common Patterns

### Pattern 1: Multiple Promises and Timeouts

```javascript
console.log('Start');

setTimeout(() => console.log('Timeout 1'), 0);

Promise.resolve()
  .then(() => console.log('Promise 1'))
  .then(() => console.log('Promise 2'));

setTimeout(() => console.log('Timeout 2'), 0);

Promise.resolve().then(() => console.log('Promise 3'));

console.log('End');

/*
Output:
Start
End
Promise 1
Promise 3
Promise 2
Timeout 1
Timeout 2

Explanation:
1. Sync: Start, End
2. All Microtasks: Promise 1, Promise 3, Promise 2
3. Tasks one by one: Timeout 1, Timeout 2
*/
```

### Pattern 2: Nested Promises and Timeouts

```javascript
console.log('1');

setTimeout(() => {
  console.log('2');
  Promise.resolve().then(() => console.log('3'));
}, 0);

Promise.resolve()
  .then(() => {
    console.log('4');
    setTimeout(() => console.log('5'), 0);
  });

console.log('6');

/*
Output:
1
6
4
2
3
5

Step-by-step:
1. Sync: 1, 6
2. Microtask: 4 (registers setTimeout for '5')
3. Task: 2 (registers Promise for '3')
4. Microtask: 3
5. Task: 5
*/
```

### Pattern 3: async/await

```javascript
console.log('1');

async function async1() {
  console.log('2');
  await async2();
  console.log('3'); // Microtask (Promise.then equivalent)
}

async function async2() {
  console.log('4');
}

async1();

setTimeout(() => console.log('5'), 0);

Promise.resolve().then(() => console.log('6'));

console.log('7');

/*
Output:
1
2
4
7
3
6
5

Explanation:
- async1() is called synchronously
- await async2() pauses async1
- Code after await goes to Microtask Queue
- Rest executes normally
*/
```

### Pattern 4: queueMicrotask

```javascript
console.log('1');

setTimeout(() => console.log('2'), 0);

queueMicrotask(() => console.log('3'));

Promise.resolve()
  .then(() => console.log('4'))
  .then(() => console.log('5'));

queueMicrotask(() => console.log('6'));

console.log('7');

/*
Output:
1
7
3
4
6
5
2

Explanation:
Microtasks: 3, 4, 6, 5 (in order they were queued)
Task: 2
*/
```

### Pattern 5: Event Loop with Fetch

```javascript
console.log('1');

fetch('https://api.example.com/data')
  .then(response => {
    console.log('2');
    return response.json();
  })
  .then(data => console.log('3'));

Promise.resolve().then(() => console.log('4'));

console.log('5');

/*
Output (assuming fetch completes quickly):
1
5
4
2
3

Explanation:
- Sync: 1, 5
- Microtask already in queue: 4
- When fetch completes, goes to Microtask: 2, 3
*/
```

---

## Interview Questions

### Q1: Predict the output

```javascript
console.log('A');

setTimeout(() => console.log('B'), 0);

Promise.resolve().then(() => console.log('C'));

console.log('D');
```

**Answer:**
```
A
D
C
B
```
Sync code first (A, D), then microtasks (C), then tasks (B).

---

### Q2: What's the output?

```javascript
console.log('1');

setTimeout(() => {
  console.log('2');
  Promise.resolve().then(() => console.log('3'));
}, 0);

Promise.resolve().then(() => {
  console.log('4');
  setTimeout(() => console.log('5'), 0);
});

console.log('6');
```

**Answer:**
```
1
6
4
2
3
5
```

**Step-by-step:**
1. Sync: 1, 6
2. Microtask: 4 (registers timeout for 5)
3. Task: 2 (registers promise for 3)
4. Microtask: 3
5. Task: 5

---

### Q3: async/await order

```javascript
async function foo() {
  console.log('1');
  await bar();
  console.log('2');
}

async function bar() {
  console.log('3');
}

foo();
console.log('4');
```

**Answer:**
```
1
3
4
2
```

Code after `await` is microtask (like Promise.then).

---

### Q4: Multiple microtasks

```javascript
Promise.resolve().then(() => {
  console.log('1');
  Promise.resolve().then(() => console.log('2'));
});

Promise.resolve().then(() => console.log('3'));

Promise.resolve().then(() => console.log('4'));
```

**Answer:**
```
1
3
4
2
```

Microtasks execute in order, but nested promises add to end of queue.

---

### Q5: setTimeout vs Promise

```javascript
setTimeout(() => console.log('1'), 0);

Promise.resolve()
  .then(() => console.log('2'))
  .then(() => console.log('3'))
  .then(() => console.log('4'));

setTimeout(() => console.log('5'), 0);
```

**Answer:**
```
2
3
4
1
5
```

All microtasks complete before any task.

---

### Q6: Complex nesting

```javascript
console.log('start');

setTimeout(() => {
  console.log('timeout 1');
  Promise.resolve().then(() => console.log('promise 1'));
  setTimeout(() => console.log('timeout 2'), 0);
}, 0);

Promise.resolve()
  .then(() => console.log('promise 2'))
  .then(() => console.log('promise 3'));

console.log('end');
```

**Answer:**
```
start
end
promise 2
promise 3
timeout 1
promise 1
timeout 2
```

**Breakdown:**
1. Sync: start, end
2. Microtasks: promise 2, promise 3
3. Task: timeout 1 (adds promise 1 and timeout 2)
4. Microtask: promise 1
5. Task: timeout 2

---

### Q7: What causes "Maximum call stack size exceeded"?

**Answer:**

Stack overflow happens when call stack limit is reached.

```javascript
// Infinite recursion
function recursive() {
  recursive(); // No base case!
}
recursive(); // Error: Maximum call stack size exceeded

/*
Call Stack grows infinitely:
┌─────────────┐
│ recursive() │
├─────────────┤
│ recursive() │
├─────────────┤
│ recursive() │
├─────────────┤
│    ...      │  ← Keeps growing until overflow
*/

// Fix: Add base case
function recursive(n) {
  if (n <= 0) return;
  recursive(n - 1);
}
```

---

### Q8: Event Loop in Node.js vs Browser?

**Answer:**

| Feature | Browser | Node.js |
|---------|---------|---------|
| Microtasks | Promises, queueMicrotask | Same + process.nextTick |
| Tasks | setTimeout, setInterval | Same + setImmediate |
| Priority | Promise > setTimeout | nextTick > Promise > setImmediate > setTimeout |
| UI Rendering | Yes (between tasks) | No |

**Node.js Specific:**

```javascript
// process.nextTick has highest priority (even higher than Promise)
process.nextTick(() => console.log('1'));
Promise.resolve().then(() => console.log('2'));
setTimeout(() => console.log('3'), 0);

// Output: 1, 2, 3
```

---

## Key Takeaways

### Event Loop Rules

1. **Synchronous code executes first** (Call Stack)
2. **Microtasks execute next** (ALL of them)
3. **One task executes** after microtasks
4. **Repeat** - Check for microtasks again, then next task
5. **UI renders** (browser only) between tasks

### Priority Order

```
Highest → Lowest:

1. Synchronous code (Call Stack)
2. process.nextTick (Node.js only)
3. Microtasks (Promises, queueMicrotask)
4. Tasks (setTimeout, setInterval)
5. setImmediate (Node.js - after I/O)
```

### Memory Tips

- **Micro** = small, quick, high priority (Promises)
- **Task** = larger operations, lower priority (setTimeout)
- **Stack** = LIFO (Last In, First Out)
- **Queue** = FIFO (First In, First Out)

### Common Mistakes

❌ Thinking `setTimeout(fn, 0)` executes immediately  
✅ It's queued as macrotask, executes after microtasks

❌ Blocking event loop with heavy synchronous code  
✅ Break into smaller chunks or use Web Workers

❌ Assuming async code is parallel  
✅ JavaScript is single-threaded, async = non-blocking

---

## Visual Summary

```
┌─────────────────────────────────────────────────────────┐
│                    EVENT LOOP CYCLE                     │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
        ┌─────────────────────────────────┐
        │  Execute All Sync Code          │
        └─────────────────────────────────┘
                          │
                          ▼
        ┌─────────────────────────────────┐
        │  Execute ALL Microtasks         │
        │  (until queue is empty)         │
        └─────────────────────────────────┘
                          │
                          ▼
        ┌─────────────────────────────────┐
        │  Execute ONE Task               │
        └─────────────────────────────────┘
                          │
                          ▼
        ┌─────────────────────────────────┐
        │  Execute ALL Microtasks         │
        │  (if any were added)            │
        └─────────────────────────────────┘
                          │
                          ▼
        ┌─────────────────────────────────┐
        │  Render UI (Browser only)       │
        └─────────────────────────────────┘
                          │
                          └──────► REPEAT
```

---

## Quick Reference Card

```javascript
// SYNCHRONOUS (Immediate)
console.log('sync');
let x = 5;

// MICROTASKS (After sync, before tasks)
Promise.resolve().then(() => {});
queueMicrotask(() => {});
async/await
process.nextTick(() => {}); // Node.js - highest priority

// TASKS (Last)
setTimeout(() => {}, 0);
setInterval(() => {}, 1000);
setImmediate(() => {}); // Node.js
requestAnimationFrame(() => {}); // Browser
I/O operations
UI rendering
```

---
