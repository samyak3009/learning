# JavaScript Hoisting

## What is Hoisting?

Hoisting is a JavaScript behavior where variable and function declarations are moved to the top of their containing scope during the compilation phase, before code execution. While the declarations are hoisted, assignments remain in place.

## How Hoisting Works

JavaScript execution happens in two phases:
1. **Compilation Phase**: Declarations are processed and allocated in memory
2. **Execution Phase**: Assignments and code execution happens line by line

## Variable Hoisting

### var Hoisting

Variables declared with `var` are hoisted and initialized with `undefined`.

```javascript
console.log(myVar); // undefined (not ReferenceError)
var myVar = "Hello World";

// Equivalent to:
var myVar; // Declaration is hoisted
console.log(myVar); // undefined
myVar = "Hello World"; // Assignment stays in place
```

### let and const Hoisting

Variables declared with `let` and `const` are hoisted but not initialized. They exist in a "Temporal Dead Zone" (TDZ) from the start of the block until the declaration.

```javascript
// console.log(myLet); // ReferenceError: Cannot access 'myLet' before initialization
let myLet = "Hello World";

// console.log(myConst); // ReferenceError: Cannot access 'myConst' before initialization
const myConst = "Hello World";
```

## Function Hoisting

### Function Declarations

Function declarations are completely hoisted with their body.

```javascript
sayHello(); // "Hello, World!" - Works!

function sayHello() {
  console.log("Hello, World!");
}
```

### Function Expressions

Function expressions using `var` are hoisted as variables (undefined), not as functions.

```javascript
// sayHi(); // TypeError: sayHi is not a function

var sayHi = function() {
  console.log("Hi there!");
};

// Equivalent to:
var sayHi; // Hoisted as undefined
// sayHi(); // TypeError: sayHi is not a function
sayHi = function() {
  console.log("Hi there!");
};
```

### Arrow Functions

Arrow functions behave like function expressions regarding hoisting.

```javascript
// greet(); // TypeError: greet is not a function

var greet = () => {
  console.log("Greetings!");
};
```

## Class Hoisting

Classes also follow hoisting rules similar to `let` and `const` declarations.

```javascript
// const p = new Person(); // ReferenceError: Cannot access 'Person' before initialization

class Person {
  constructor() {
    this.name = "John";
  }
}
```

## Practical Effects of Hoisting

### Function Order Flexibility

Due to hoisting, you can call functions before they are declared in the code.

```javascript
// Main program logic at the top
init();
setupEventListeners();
loadData();

// Helper functions below
function init() { /* ... */ }
function setupEventListeners() { /* ... */ }
function loadData() { /* ... */ }
```

### Variable Declaration Best Practices

Hoisting can lead to unexpected behavior if not understood properly.

```javascript
var x = 5;

function foo() {
  if (false) {
    var x = 10; // Hoisted to the top of the function
  }
  console.log(x); // undefined, not 5!
}

foo();
```

### Potential Issues

Hoisting can create subtle bugs:

```javascript
var x = 1;

function bar() {
  console.log(x); // undefined, not 1
  var x = 2;
}

bar();
```

## The Temporal Dead Zone (TDZ)

The TDZ is the period between entering a scope where a variable is declared and the actual declaration.

```javascript
{
  // TDZ starts for carName
  console.log(carType); // ReferenceError
  let carName = "Tesla"; // TDZ ends
  const carType = "Electric"; 
}
```

## Practical Implications and Best Practices

### 1. Declare Variables at the Top

```javascript
// Good practice
function processData() {
  var data; // All declarations at the top
  var result;
  var temp;
  
  // Code that uses these variables
  data = fetchData();
  temp = processRawData(data);
  result = formatResult(temp);
  
  return result;
}
```

### 2. Use Function Declarations for Mutual Recursion

```javascript
function isEven(n) {
  if (n === 0) return true;
  return isOdd(n - 1);
}

function isOdd(n) {
  if (n === 0) return false;
  return isEven(n - 1);
}
```

### 3. Always Use `let` and `const` Over `var`

```javascript
// Preferred
function calculateArea() {
  let width = 10;
  let height = 5;
  
  // Other code...
  
  return width * height;
}
```

### 4. Initialize Variables During Declaration

```javascript
// Good practice
let counter = 0;
const MAX_SIZE = 100;
```

## Interview Questions

1. **What is hoisting in JavaScript?**
   - Hoisting is JavaScript's default behavior of moving declarations to the top of the current scope during compilation.
   - While declarations are hoisted, assignments remain in place.

2. **Is `let` hoisted in JavaScript?**
   - Yes, `let` declarations are hoisted, but unlike `var`, they are not initialized.
   - They remain in the "Temporal Dead Zone" until their declaration statement.

3. **What's the difference between function declaration and function expression hoisting?**
   - Function declarations are completely hoisted with their body.
   - Function expressions are hoisted as variables, which means only the variable declaration is hoisted, not the function assignment.

4. **What will be the output of the following code?**
   ```javascript
   console.log(x);
   var x = 5;
   ```
   - Output: `undefined` (not an error, because the declaration is hoisted but not the assignment)

5. **Why does the following code return `undefined`?**
   ```javascript
   var x = 10;
   function foo() {
     console.log(x);
     var x = 20;
   }
   foo();
   ```
   - Local variable `x` is hoisted to the top of the function, shadowing the global `x`
   - At the point of the `console.log()`, local `x` exists but is `undefined`

6. **Can hoisting lead to memory leaks?**
   - Hoisting itself doesn't cause memory leaks.
   - However, misunderstanding hoisting can lead to unintended variable scopes that may contribute to memory issues in some cases.

7. **How do `let`, `const`, and `var` differ regarding hoisting?**
   - `var`: Hoisted and initialized with `undefined`
   - `let` and `const`: Hoisted but not initialized (remain in TDZ)

8. **What is the Temporal Dead Zone (TDZ)?**
   - The period between entering a scope where a variable is declared and the actual declaration
   - During this period, accessing the variable throws a `ReferenceError`

9. **Does hoisting work inside blocks?**
   - For `var`: Yes, but it hoists to the function scope, not block scope
   - For `let` and `const`: They are hoisted to the block scope

10. **How does hoisting work with ES6 classes?**
    - Classes are hoisted but remain uninitialized until declaration (similar to `let` and `const`)
    - Attempting to use a class before its declaration results in a `ReferenceError`

## Advantages of Understanding Hoisting

1. **Better Code Organization**
   - Put main logic at the top, implementation details below
   - Enables a more natural reading flow

2. **Avoiding Common Bugs**
   - Understanding hoisting helps prevent unexpected behavior
   - Makes variable scope clearer

3. **More Predictable Code**
   - Proper use of hoisting leads to more maintainable code
   - Reduces debugging time

## Limitations and Edge Cases

### Nested Scopes

Hoisting operates within the current scope:

```javascript
function outer() {
  console.log(x); // undefined
  
  var x = 10;
  
  function inner() {
    var x = 20; // Different x, scoped to inner()
    console.log(x); // 20
  }
  
  inner();
  console.log(x); // 10
}
```

### Hoisting in Loops

Be careful with hoisting in loops:

```javascript
for (var i = 0; i < 3; i++) {
  setTimeout(function() {
    console.log(i); // 3, 3, 3 (not 0, 1, 2)
  }, 100);
}

// Better with let (block scope)
for (let j = 0; j < 3; j++) {
  setTimeout(function() {
    console.log(j); // 0, 1, 2
  }, 100);
}
```

## Best Practices Summary

1. Use `let` and `const` instead of `var` to avoid hoisting surprises
2. Declare variables at the top of their scope
3. Initialize variables when declaring them
4. Understand the different hoisting behaviors of declarations
5. Be mindful of the Temporal Dead Zone with `let` and `const`
6. Use function declarations for functions that need to be accessible before their definition
