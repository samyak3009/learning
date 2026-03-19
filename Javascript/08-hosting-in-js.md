# 🚀 Complete Guide to Hoisting in JavaScript

> A comprehensive resource covering hoisting concepts, execution context, and interview preparation for Senior Frontend Engineers

## Table of Contents

- [What is Hoisting?](#what-is-hoisting)
- [Variable Hoisting](#variable-hoisting)
- [Function Hoisting](#function-hoisting)
- [Temporal Dead Zone (TDZ)](#temporal-dead-zone-tdz)
- [Execution Context](#execution-context)
- [Scope Chain](#scope-chain)
- [Common Pitfalls](#common-pitfalls)
- [Interview Questions & Answers](#interview-questions--answers)

---

## What is Hoisting?

**Hoisting** is JavaScript's default behavior of moving declarations to the top of their scope before code execution. This means you can use variables and functions before they are declared in the code.

### Key Points:
- Only **declarations** are hoisted, not **initializations**
- Happens during the **creation phase** of the execution context
- Different behavior for `var`, `let`, `const`, and functions

```javascript
// What you write
console.log(x); // undefined (not ReferenceError!)
var x = 5;

// How JavaScript interprets it
var x;
console.log(x); // undefined
x = 5;
```

---

## Variable Hoisting

### 1. `var` Hoisting

Variables declared with `var` are hoisted and initialized with `undefined`.

```javascript
console.log(name); // undefined
var name = 'John';
console.log(name); // 'John'

// Interpreted as:
var name;
console.log(name); // undefined
name = 'John';
console.log(name); // 'John'
```

**Example with Multiple Variables:**
```javascript
console.log(a); // undefined
console.log(b); // undefined
console.log(c); // undefined

var a = 1;
var b = 2;
var c = 3;

console.log(a); // 1
console.log(b); // 2
console.log(c); // 3

// Hoisted as:
var a;
var b;
var c;
console.log(a); // undefined
console.log(b); // undefined
console.log(c); // undefined
a = 1;
b = 2;
c = 3;
```

**`var` in Functions:**
```javascript
function example() {
  console.log(x); // undefined
  var x = 10;
  console.log(x); // 10
}
example();

// Hoisted as:
function example() {
  var x;
  console.log(x); // undefined
  x = 10;
  console.log(x); // 10
}
```

### 2. `let` Hoisting

Variables declared with `let` are hoisted but **NOT initialized**. They remain in the **Temporal Dead Zone (TDZ)** until the declaration is reached.

```javascript
console.log(name); // ReferenceError: Cannot access 'name' before initialization
let name = 'John';

// let is hoisted but stays in TDZ
```

**Block Scope with `let`:**
```javascript
{
  console.log(x); // ReferenceError Cannot access 'name' before initialization
  let x = 5;
}

// let is block-scoped
console.log(x); // ReferenceError: x is not defined
```

**`let` in Loops:**
```javascript
for (let i = 0; i < 3; i++) {
  setTimeout(() => console.log(i), 100);
}
// Output: 0, 1, 2 (each iteration has its own 'i')

for (var j = 0; j < 3; j++) {
  setTimeout(() => console.log(j), 100);
}
// Output: 3, 3, 3 (all share the same 'j')
```

### 3. `const` Hoisting

Similar to `let`, `const` is hoisted but remains in the TDZ. Additionally, `const` must be initialized during declaration.

```javascript
console.log(PI); // ReferenceError: Cannot access 'PI' before initialization
const PI = 3.14;

// const must be initialized
const VALUE; // SyntaxError: Missing initializer in const declaration
```

**`const` with Objects:**
```javascript
const user = { name: 'John' };
user.name = 'Jane'; // ✅ Allowed (mutating property)
user.age = 30;      // ✅ Allowed (adding property)

user = { name: 'Bob' }; // ❌ TypeError: Assignment to constant variable
```

### Visual Comparison

```javascript
// VAR - Hoisted and initialized with undefined
console.log(varVariable); // undefined
var varVariable = 'I am var';

// LET - Hoisted but in TDZ
console.log(letVariable); // ReferenceError
let letVariable = 'I am let';

// CONST - Hoisted but in TDZ
console.log(constVariable); // ReferenceError
const constVariable = 'I am const';
```

---

## Function Hoisting

### 1. Function Declarations

**Function declarations** are fully hoisted (both declaration and definition).

```javascript
// Call before declaration - Works!
sayHello(); // "Hello!"

function sayHello() {
  console.log("Hello!");
}

// Another example
console.log(add(2, 3)); // 5

function add(a, b) {
  return a + b;
}

// Hoisted as:
function sayHello() {
  console.log("Hello!");
}
function add(a, b) {
  return a + b;
}
console.log(add(2, 3)); // 5
sayHello(); // "Hello!"
```

### 2. Function Expressions

**Function expressions** are NOT hoisted (only the variable declaration is hoisted).

```javascript
// Call before declaration - Error!
sayHello(); // TypeError: sayHello is not a function

var sayHello = function() {
  console.log("Hello!");
};

// Hoisted as:
var sayHello;
sayHello(); // TypeError: sayHello is not a function
sayHello = function() {
  console.log("Hello!");
};
```

**With `let` and `const`:**
```javascript
// With let
greet(); // ReferenceError: Cannot access 'greet' before initialization
let greet = function() {
  console.log("Hi!");
};

// With const
multiply(); // ReferenceError: Cannot access 'multiply' before initialization
const multiply = function(a, b) {
  return a * b;
};
```

### 3. Arrow Functions

Arrow functions are **function expressions**, so they follow the same hoisting rules.

```javascript
// Error - not hoisted
calculate(); // ReferenceError or TypeError

const calculate = (a, b) => a + b;

// With var
sum(); // TypeError: sum is not a function
var sum = (a, b) => a + b;
```

### 4. Named Function Expressions

```javascript
// The function name is only available inside the function
var factorial = function fact(n) {
  if (n <= 1) return 1;
  return n * fact(n - 1);
};

console.log(factorial(5)); // 120
console.log(fact(5)); // ReferenceError: fact is not defined
```

### Function Hoisting Priority

When there's a conflict between variable and function declarations, **functions win**.

```javascript
console.log(typeof foo); // "function"

var foo = 'I am a variable';
function foo() {
  return 'I am a function';
}

console.log(typeof foo); // "string"

// Hoisted as:
function foo() {
  return 'I am a function';
}
var foo; // Declaration ignored (already exists)
console.log(typeof foo); // "function"
foo = 'I am a variable';
console.log(typeof foo); // "string"
```

---

## Temporal Dead Zone (TDZ)

The **Temporal Dead Zone** is the period between entering scope and the actual declaration where the variable cannot be accessed.

### Understanding TDZ

```javascript
{
  // TDZ starts here for 'x'
  console.log(x); // ReferenceError
  console.log(y); // ReferenceError
  
  let x = 10; // TDZ ends here for 'x'
  const y = 20; // TDZ ends here for 'y'
  
  console.log(x); // 10
  console.log(y); // 20
}
```

### TDZ with Function Parameters

```javascript
function example(a = b, b) { // Parameter initialization starts LEFT → RIGHT in this b is in TDZ
  return [a, b];
}
// note that parameter behave as let 

example(undefined, 2); // ReferenceError: Cannot access 'b' before initialization

// Fixed version
function example(b, a = b) { // here b is 2 
  return [a, b];
}
example(2); // [2, 2]
```

### TDZ in Complex Scenarios

```javascript
let x = 'outer';

function foo() {
  console.log(x); // ReferenceError (not "outer")
  let x = 'inner';
}

foo();

// Why? Because 'let x' is hoisted within foo's scope
// and creates a TDZ until the declaration
```

### `typeof` in TDZ

```javascript
console.log(typeof undeclaredVariable); // "undefined" (no error)
console.log(typeof declaredVariable); // ReferenceError!
let declaredVariable;
```

---

## Execution Context

Understanding execution context is crucial to understanding hoisting.

### Types of Execution Context

1. **Global Execution Context**
2. **Function Execution Context**
3. **Eval Execution Context** (rarely used)

### Execution Context Phases

#### 1. Creation Phase
- Create the Variable Environment (var, function declarations)
- Create the Lexical Environment (let, const)
- Set up the scope chain
- Determine the value of `this`

#### 2. Execution Phase
- Assign values to variables
- Execute code line by line

### Example of Execution Context

```javascript
var name = 'Global';

function outer() {
  var name = 'Outer';
  
  function inner() {
    var name = 'Inner';
    console.log(name); // Inner
  }
  
  inner();
  console.log(name); // Outer
}

outer();
console.log(name); // Global

/*
Execution Context Stack:
1. Global EC created
   - name: undefined → 'Global'
   - outer: function

2. outer() called → outer EC created
   - name: undefined → 'Outer'
   - inner: function

3. inner() called → inner EC created
   - name: undefined → 'Inner'
   - Executes console.log(name)

4. inner EC popped off
5. outer EC continues
6. outer EC popped off
7. Back to Global EC
*/
```

### Detailed Creation Phase Example

```javascript
console.log(a); // undefined
console.log(b); // ReferenceError
console.log(foo); // [Function: foo]
console.log(bar); // undefined

var a = 10;
let b = 20;

function foo() {
  return 'function declaration';
}

var bar = function() {
  return 'function expression';
};

/*
Creation Phase:
Variable Environment:
  foo: function() { return 'function declaration'; }
  a: undefined
  bar: undefined

Lexical Environment:
  b: <uninitialized> (TDZ)
*/
```

---

## Scope Chain

The scope chain determines how variable lookup works in JavaScript.

### Lexical Scope

```javascript
var globalVar = 'global';

function outer() {
  var outerVar = 'outer';
  
  function inner() {
    var innerVar = 'inner';
    
    console.log(innerVar);  // 'inner' - found in local scope
    console.log(outerVar);  // 'outer' - found in outer scope
    console.log(globalVar); // 'global' - found in global scope
  }
  
  inner();
}

outer();

/*
Scope Chain for inner():
inner scope → outer scope → global scope
*/
```

### Scope Chain with Closures

```javascript
function createCounter() {
  let count = 0; // Enclosed in closure
  
  return {
    increment() {
      count++;
      return count;
    },
    decrement() {
      count--;
      return count;
    },
    getCount() {
      return count;
    }
  };
}

const counter = createCounter();
console.log(counter.increment()); // 1
console.log(counter.increment()); // 2
console.log(counter.decrement()); // 1
console.log(counter.getCount());  // 1

// 'count' is not accessible directly
console.log(counter.count); // undefined
```

### Block Scope vs Function Scope

```javascript
// var is function-scoped
function varExample() {
  if (true) {
    var x = 10;
  }
  console.log(x); // 10 (accessible outside block)
}

// let/const are block-scoped
function letExample() {
  if (true) {
    let y = 10;
  }
  console.log(y); // ReferenceError: y is not defined
}

varExample();
letExample();
```

---

## Common Pitfalls

### 1. Loop Variable Hoisting

```javascript
// Problem with var
for (var i = 0; i < 3; i++) {
  setTimeout(function() {
    console.log(i); // 3, 3, 3
  }, 1000);
}

// Solution 1: Use let
for (let i = 0; i < 3; i++) {
  setTimeout(function() {
    console.log(i); // 0, 1, 2
  }, 1000);
}

// Solution 2: IIFE with var
for (var i = 0; i < 3; i++) {
  (function(j) {
    setTimeout(function() {
      console.log(j); // 0, 1, 2
    }, 1000);
  })(i);
}

// Solution 3: bind
for (var i = 0; i < 3; i++) {
  setTimeout(function(j) {
    console.log(j); // 0, 1, 2
  }.bind(null, i), 1000);
}
```

### 2. Function Declaration vs Expression

```javascript
// This works
foo(); // "I'm a declaration"

function foo() {
  console.log("I'm a declaration");
}

// This doesn't work
bar(); // TypeError: bar is not a function

var bar = function() {
  console.log("I'm an expression");
};
```

### 3. Overwriting Functions

```javascript
function myFunc() {
  console.log('First');
}

function myFunc() {
  console.log('Second');
}

myFunc(); // "Second"

// Both are hoisted, but the second one overwrites the first
```

### 4. Variable and Function Name Conflict

```javascript
var myFunc = 'I am a string';

function myFunc() {
  console.log('I am a function');
}

console.log(typeof myFunc); // "string"

// Hoisted as:
function myFunc() {
  console.log('I am a function');
}
var myFunc; // Ignored (already declared)
myFunc = 'I am a string';
```

### 5. Hoisting in Classes

```javascript
// Classes are NOT hoisted
const p = new Person(); // ReferenceError

class Person {
  constructor(name) {
    this.name = name;
  }
}

// Must declare before use
class Person {
  constructor(name) {
    this.name = name;
  }
}
const p = new Person('John'); // Works
```

---

## Interview Questions & Answers

### Basic Level

#### Q1: What is hoisting in JavaScript?

**Answer:**

Hoisting is JavaScript's default behavior of moving variable and function declarations to the top of their scope during the compilation phase, before code execution.

**Important Points:**
- Only declarations are hoisted, not initializations
- Happens during the creation phase of execution context
- `var` declarations are hoisted and initialized with `undefined`
- `let` and `const` declarations are hoisted but remain in TDZ
- Function declarations are fully hoisted
- Function expressions are not hoisted

```javascript
// Example
console.log(x); // undefined (not ReferenceError)
var x = 5;

// How JavaScript interprets it:
var x; // Declaration hoisted
console.log(x); // undefined
x = 5; // Initialization stays in place
```

---

#### Q2: What's the difference between `var`, `let`, and `const` in terms of hoisting?

**Answer:**

| Feature | var | let | const |
|---------|-----|-----|-------|
| Hoisted? | ✅ Yes | ✅ Yes | ✅ Yes |
| Initialized? | ✅ undefined | ❌ TDZ | ❌ TDZ |
| Re-declaration | ✅ Allowed | ❌ Error | ❌ Error |
| Re-assignment | ✅ Allowed | ✅ Allowed | ❌ Error |
| Scope | Function | Block | Block |

```javascript
// VAR
console.log(a); // undefined
var a = 1;
var a = 2; // Re-declaration allowed
a = 3; // Re-assignment allowed

// LET
console.log(b); // ReferenceError (TDZ)
let b = 1;
// let b = 2; // SyntaxError: Identifier 'b' has already been declared
b = 3; // Re-assignment allowed

// CONST
console.log(c); // ReferenceError (TDZ)
const c = 1;
// const c = 2; // SyntaxError
// c = 3; // TypeError: Assignment to constant variable
```

---

#### Q3: What is the Temporal Dead Zone (TDZ)?

**Answer:**

The Temporal Dead Zone is the time period between entering a scope and the actual variable declaration where the variable exists but cannot be accessed.

```javascript
{
  // TDZ starts for 'x'
  console.log(x); // ReferenceError
  // TDZ continues
  console.log(x); // ReferenceError
  // TDZ ends with declaration
  let x = 10;
  console.log(x); // 10 - OK
}
```

**Why TDZ Exists:**
- Prevents usage before initialization
- Catches programming errors
- Makes temporal semantics clear

**TDZ Examples:**

```javascript
// Example 1: Function parameters
function test(a = b, b) {
  return [a, b];
}
test(undefined, 2); // ReferenceError: 'b' in TDZ

// Example 2: typeof operator
console.log(typeof undeclared); // "undefined" - no error
console.log(typeof declared); // ReferenceError - in TDZ!
let declared;

// Example 3: Nested scope
let x = 'outer';
{
  console.log(x); // ReferenceError (not "outer")
  let x = 'inner'; // Creates TDZ for this block
}
```

---

#### Q4: Predict the output:

```javascript
console.log(foo);
console.log(bar);

var foo = function() {
  console.log('foo');
};

function bar() {
  console.log('bar');
}
```

**Answer:**

```javascript
console.log(foo); // undefined
console.log(bar); // [Function: bar]

/*
Explanation:
After hoisting:

function bar() {
  console.log('bar');
}
var foo; // undefined

console.log(foo); // undefined
console.log(bar); // [Function: bar]

foo = function() {
  console.log('foo');
};
*/
```

---

### Intermediate Level

#### Q5: What will be the output and why?

```javascript
var x = 1;

function foo() {
  console.log(x);
  var x = 2;
  console.log(x);
}

foo();
console.log(x);
```

**Answer:**

```javascript
Output:
undefined
2
1

/*
Explanation:
After hoisting in foo():

function foo() {
  var x; // Hoisted, shadows global x
  console.log(x); // undefined (not 1)
  x = 2;
  console.log(x); // 2
}

The 'var x' inside foo() creates a local variable that shadows
the global 'x'. This local 'x' is hoisted to the top of foo()
and initialized with undefined.
*/
```

---

#### Q6: Explain the execution context and hoisting in this code:

```javascript
var a = 10;

function outer() {
  console.log(a);
  var a = 20;
  
  function inner() {
    console.log(a);
    var a = 30;
    console.log(a);
  }
  
  inner();
  console.log(a);
}

outer();
console.log(a);
```

**Answer:**

```javascript
Output:
undefined
undefined
30
20
10

/*
Execution Flow:

1. Global Execution Context (Creation Phase):
   - a: undefined → 10
   - outer: function

2. outer() called:
   Creation Phase:
   - a: undefined (local to outer)
   - inner: function
   
   Execution Phase:
   - console.log(a); // undefined (local a not yet assigned)
   - a = 20;
   
3. inner() called:
   Creation Phase:
   - a: undefined (local to inner)
   
   Execution Phase:
   - console.log(a); // undefined (local a not yet assigned)
   - a = 30;
   - console.log(a); // 30
   
4. Back to outer():
   - console.log(a); // 20 (outer's local a)
   
5. Back to Global:
   - console.log(a); // 10 (global a)
*/
```

---

#### Q7: What's the difference in hoisting between function declarations and arrow functions?

**Answer:**

**Function Declarations:**
- Fully hoisted (declaration + definition)
- Can be called before declaration
- Have their own `this` binding

**Arrow Functions:**
- Treated as function expressions
- Not hoisted (only variable declaration is hoisted if using var)
- Do not have their own `this`

```javascript
// Function Declaration - Works
sayHello(); // "Hello"
function sayHello() {
  console.log("Hello");
}

// Arrow Function with var - TypeError
greet(); // TypeError: greet is not a function
var greet = () => {
  console.log("Hi");
};

// Arrow Function with const - ReferenceError
welcome(); // ReferenceError: Cannot access 'welcome' before initialization
const welcome = () => {
  console.log("Welcome");
};

// this binding difference
const obj = {
  name: 'Object',
  
  regularFunc: function() {
    console.log(this.name); // "Object"
  },
  
  arrowFunc: () => {
    console.log(this.name); // undefined (inherits from outer scope)
  }
};

obj.regularFunc();
obj.arrowFunc();
```

---

### Advanced Level

#### Q8: Predict the output and explain the hoisting behavior:

```javascript
function test() {
  console.log(a);
  console.log(foo());
  
  var a = 1;
  function foo() {
    return 2;
  }
}

test();
```

**Answer:**

```javascript
Output:
undefined
2

/*
Explanation:
After hoisting in test():

function test() {
  var a;
  function foo() {
    return 2;
  }
  
  console.log(a); // undefined
  console.log(foo()); // 2
  
  a = 1;
}

Both 'var a' and 'function foo' are hoisted to the top.
Function declarations are fully hoisted, so foo() can be called.
'var a' is hoisted but initialized with undefined.
*/
```

---

#### Q9: What will be the output? Explain scope chain and hoisting:

```javascript
var x = 'global';

function outer() {
  console.log(x); // ?
  var x = 'outer';
  
  function inner() {
    console.log(x); // ?
    var x = 'inner';
    console.log(x); // ?
  }
  
  inner();
  console.log(x); // ?
}

outer();
console.log(x); // ?
```

**Answer:**

```javascript
Output:
undefined
undefined
inner
outer
global

/*
Detailed Explanation:

1. Global EC:
   var x = 'global';

2. outer() called - Creation Phase:
   var x; // undefined (shadows global)
   function inner() { ... }
   
   Execution Phase:
   console.log(x); // undefined (local x not assigned yet)
   x = 'outer';

3. inner() called - Creation Phase:
   var x; // undefined (shadows outer's x)
   
   Execution Phase:
   console.log(x); // undefined (local x not assigned yet)
   x = 'inner';
   console.log(x); // 'inner'

4. Back to outer():
   console.log(x); // 'outer' (outer's local x)

5. Back to Global:
   console.log(x); // 'global'

Key Point: Each 'var x' creates a new variable in its scope,
shadowing any x from outer scopes.
*/
```

---

#### Q10: Explain the behavior of this code with let/const:

```javascript
let x = 10;

if (true) {
  console.log(x); // ?
  let x = 20;
  console.log(x); // ?
}

console.log(x); // ?
```

**Answer:**

```javascript
Output:
ReferenceError: Cannot access 'x' before initialization

/*
Explanation:
The first console.log(x) throws a ReferenceError because
'let x = 20' creates a new block-scoped variable that is
hoisted to the top of the block but remains in the TDZ
until the declaration is reached.

If we remove the first console.log:

let x = 10;

if (true) {
  // console.log(x); // Removed
  let x = 20;
  console.log(x); // 20
}

console.log(x); // 10

The block-scoped 'x' shadows the outer 'x' only within the block.
*/
```

---

#### Q11: What is the output? Explain function hoisting priority:

```javascript
var foo = 'variable';

function foo() {
  return 'function';
}

console.log(typeof foo);
console.log(foo);
```

**Answer:**

```javascript
Output:
string
variable

/*
Explanation:
After hoisting:

function foo() {
  return 'function';
}
var foo; // Declaration ignored (foo already exists)

console.log(typeof foo); // "function" at this point
console.log(foo); // [Function: foo]

But then:
foo = 'variable'; // This would change it

Wait! Let me trace through the actual execution:

Hoisting phase:
1. function foo() { return 'function'; } - hoisted first
2. var foo - declaration ignored (already exists)

Execution phase:
3. foo = 'variable'; - assignment executes
4. console.log(typeof foo); - "string"
5. console.log(foo); - "variable"

The key: Function declarations are hoisted first, but
variable assignments happen during execution phase.
*/
```

---

#### Q12: Predict the output with classes:

```javascript
const p1 = new Person('John');

class Person {
  constructor(name) {
    this.name = name;
  }
}

const p2 = new Person('Jane');
```

**Answer:**

```javascript
Output:
ReferenceError: Cannot access 'Person' before initialization

/*
Explanation:
Classes are NOT hoisted like function declarations.
They behave like let/const - they're hoisted but remain
in the TDZ until the declaration is reached.

Correct version:

class Person {
  constructor(name) {
    this.name = name;
  }
}

const p1 = new Person('John'); // Works
const p2 = new Person('Jane'); // Works
*/
```

---

#### Q13: Complex hoisting scenario with same names:

```javascript
console.log(foo); // ?

var foo = 10;

console.log(foo); // ?

function foo() {
  console.log('I am a function');
}

console.log(foo); // ?

var foo = 20;

console.log(foo); // ?
```

**Answer:**

```javascript
Output:
[Function: foo]
10
10
20

/*
Detailed Execution:

HOISTING PHASE:
1. function foo() { ... } is hoisted first
2. var foo - declaration ignored (foo already declared by function)

After hoisting:
function foo() {
  console.log('I am a function');
}

EXECUTION PHASE:
console.log(foo); // [Function: foo]
foo = 10;
console.log(foo); // 10
// function foo() { ... } - already hoisted, ignored here
console.log(foo); // 10 (still the number)
foo = 20;
console.log(foo); // 20
*/
```

---

#### Q14: Hoisting with nested functions:

```javascript
var a = 1;

function outer() {
  console.log(a); // ?
  
  function inner() {
    console.log(a); // ?
    var a = 3;
    console.log(a); // ?
  }
  
  inner();
  console.log(a); // ?
  var a = 2;
  console.log(a); // ?
}

outer();
console.log(a); // ?
```

**Answer:**

```javascript
Output:
undefined
undefined
3
undefined
2
1

/*
Execution Trace:

Global EC:
  var a = 1;

outer() called:
  HOISTING:
    var a; // undefined
    function inner() { ... }
  
  EXECUTION:
    console.log(a); // undefined (local a)
    
    inner() called:
      HOISTING:
        var a; // undefined
      
      EXECUTION:
        console.log(a); // undefined
        a = 3;
        console.log(a); // 3
    
    console.log(a); // undefined (outer's local a)
    a = 2;
    console.log(a); // 2

Back to Global:
  console.log(a); // 1 (global a)
*/
```

---

#### Q15: Modern JavaScript hoisting with modules:

```javascript
// module.js
console.log(PI); // ?

export const PI = 3.14;

console.log(PI); // ?

// What happens if we try to import before export?
```

**Answer:**

```javascript
Output:
ReferenceError: Cannot access 'PI' before initialization
// Second console.log never executes

/*
Explanation:
ES6 modules use strict mode by default, and exported
bindings follow let/const hoisting rules (TDZ applies).

The export statement doesn't change hoisting behavior:
- const/let are still hoisted
- They remain in TDZ until declaration
- ReferenceError if accessed before declaration

Correct version:

export const PI = 3.14;
console.log(PI); // 3.14

Module imports are hoisted:
// This works:
import { someFunction } from './module.js';
someFunction();

export function someFunction() {
  return 'Hello';
}
*/
```

---

## Advanced Patterns

### 1. IIFE and Hoisting

```javascript
// Immediately Invoked Function Expression
(function() {
  console.log(x); // undefined
  var x = 10;
  console.log(x); // 10
})();

console.log(x); // ReferenceError: x is not defined
```

### 2. Hoisting in Callbacks

```javascript
setTimeout(function() {
  console.log(a); // undefined (hoisted within callback)
  var a = 10;
}, 0);

var a = 5;
```

### 3. Hoisting with Destructuring

```javascript
console.log(a, b); // ReferenceError (TDZ for both)

let { a, b } = { a: 1, b: 2 };

// With var
console.log(x, y); // undefined, undefined
var { x, y } = { x: 10, y: 20 };
console.log(x, y); // 10, 20
```

---

## Best Practices

### ✅ DO:

1. **Always declare variables at the top of their scope**
```javascript
function example() {
  let a, b, c; // Declarations at top
  
  a = 1;
  b = 2;
  c = 3;
}
```

2. **Use `const` by default, `let` when reassignment is needed**
```javascript
const API_URL = 'https://api.example.com';
let counter = 0;
counter++;
```

3. **Use function declarations for named functions**
```javascript
function calculateTotal(items) {
  return items.reduce((sum, item) => sum + item.price, 0);
}
```

4. **Declare functions before use (even though they're hoisted)**
```javascript
function greet(name) {
  return `Hello, ${name}!`;
}

console.log(greet('John'));
```

### ❌ DON'T:

1. **Avoid relying on hoisting**
```javascript
// Bad
console.log(x);
var x = 10;

// Good
var x = 10;
console.log(x);
```

2. **Don't use var (use let/const instead)**
```javascript
// Bad
var name = 'John';

// Good
const name = 'John';
```

3. **Avoid same variable names in nested scopes**
```javascript
// Confusing
let x = 1;
function test() {
  let x = 2;
  console.log(x); // Which x?
}
```

---

## Common Interview Tricks

### Trick 1: Scope Shadowing
```javascript
var a = 10;
function test() {
  console.log(a); // undefined (not 10)
  var a = 20;
}
test();
```

### Trick 2: Loop Variable
```javascript
for (var i = 0; i < 3; i++) {
  setTimeout(() => console.log(i), 0);
}
// Output: 3, 3, 3
```

### Trick 3: Function vs Variable Priority
```javascript
var myFunc;
function myFunc() {}
console.log(typeof myFunc); // "function"
```

---

## Summary Cheat Sheet

| Declaration | Hoisted? | Initialized? | TDZ? | Scope |
|-------------|----------|--------------|------|-------|
| var | ✅ | ✅ undefined | ❌ | Function |
| let | ✅ | ❌ | ✅ | Block |
| const | ✅ | ❌ | ✅ | Block |
| function declaration | ✅ | ✅ full | ❌ | Function |
| function expression | Depends on var/let/const | ❌ | Depends | Depends |
| arrow function | Depends on var/let/const | ❌ | Depends | Depends |
| class | ✅ | ❌ | ✅ | Block |

---

## Additional Resources

- [MDN: Hoisting](https://developer.mozilla.org/en-US/docs/Glossary/Hoisting)
- [JavaScript.info: Variables](https://javascript.info/variables)
- [You Don't Know JS: Scope & Closures](https://github.com/getify/You-Dont-Know-JS)

---
