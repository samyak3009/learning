# 🎯 JavaScript `this` Keyword - Interview Guide

> Complete guide to understanding `this` in JavaScript with examples and interview questions

## Table of Contents

- [What is `this`?](#what-is-this)
- [The 4 Binding Rules](#the-4-binding-rules)
- [Arrow Functions and `this`](#arrow-functions-and-this)
- [Common Pitfalls](#common-pitfalls)
- [Interview Questions](#interview-questions)

---

## What is `this`?

`this` is a special identifier keyword that's automatically defined in the scope of every function. Its value is determined by **how the function is called**, not where it's defined.

**Key Point:** `this` is runtime binding, not author-time binding.

```javascript
function identify() {
  return this.name;
}

const person1 = { name: 'Alice' };
const person2 = { name: 'Bob' };

identify.call(person1); // 'Alice'
identify.call(person2); // 'Bob'

// Same function, different 'this' based on how it's called
```

---

## The 4 Binding Rules

### Visual Priority

```
┌────────────────────────────────────────┐
│         'this' Binding Priority        │
├────────────────────────────────────────┤
│  1. new Binding (highest)              │
│  2. Explicit Binding (call/apply/bind) │
│  3. Implicit Binding (object method)   │
│  4. Default Binding (lowest)           │
└────────────────────────────────────────┘
```

---

### 1. Default Binding (Lowest Priority)

Function called in standalone mode. `this` defaults to global object (or `undefined` in strict mode).

```javascript
function showThis() {
  console.log(this);
}

showThis(); // window (browser) or global (Node.js)

// Strict mode
'use strict';
function showThisStrict() {
  console.log(this);
}

showThisStrict(); // undefined
```

**Example:**

```javascript
var name = 'Global';

function greet() {
  console.log(this.name);
}

greet(); // 'Global' (non-strict mode)

/*
Explanation:
- greet() is called without any object
- 'this' defaults to global object
- global object has 'name' property
*/
```

---

### 2. Implicit Binding

Function called as a method of an object. `this` refers to the object before the dot.

```javascript
const person = {
  name: 'Alice',
  greet: function() {
    console.log(this.name);
  }
};

person.greet(); // 'Alice'

/*
Rule: Object.method()
'this' = Object (the object before the dot)
*/
```

**Multiple Levels:**

```javascript
const obj = {
  name: 'obj',
  inner: {
    name: 'inner',
    greet: function() {
      console.log(this.name);
    }
  }
};

obj.inner.greet(); // 'inner'

/*
Only the last object in the chain matters
obj.inner.greet() → 'this' = inner
*/
```

**Implicit Loss (Common Pitfall):**

```javascript
const person = {
  name: 'Alice',
  greet: function() {
    console.log(this.name);
  }
};

person.greet(); // 'Alice' ✅

const greetFn = person.greet;
greetFn(); // undefined (or global name) ❌

/*
Why?
- greetFn is now a standalone function reference
- When called as greetFn(), no object context
- Falls back to default binding
*/
```

**Callback Function Loss:**

```javascript
const person = {
  name: 'Alice',
  greet: function() {
    console.log(this.name);
  }
};

setTimeout(person.greet, 1000); // undefined ❌

/*
Why?
setTimeout internally does:
  const fn = person.greet; // Lost context
  fn(); // Called without object

Solution 1: Arrow function
setTimeout(() => person.greet(), 1000); // 'Alice' ✅

Solution 2: bind
setTimeout(person.greet.bind(person), 1000); // 'Alice' ✅
*/
```

---

### 3. Explicit Binding

Using `call()`, `apply()`, or `bind()` to explicitly set `this`.

#### call()

```javascript
function greet(greeting, punctuation) {
  console.log(greeting + ', ' + this.name + punctuation);
}

const person = { name: 'Alice' };

greet.call(person, 'Hello', '!'); // 'Hello, Alice!'

/*
Syntax: func.call(thisArg, arg1, arg2, ...)
- First argument: what 'this' should be
- Rest: function arguments
*/
```

#### apply()

```javascript
function greet(greeting, punctuation) {
  console.log(greeting + ', ' + this.name + punctuation);
}

const person = { name: 'Bob' };

greet.apply(person, ['Hi', '?']); // 'Hi, Bob?'

/*
Syntax: func.apply(thisArg, [argsArray])
- First argument: what 'this' should be
- Second: array of arguments
*/
```

#### bind()

Creates a new function with `this` permanently bound.

```javascript
function greet() {
  console.log(this.name);
}

const person = { name: 'Charlie' };

const boundGreet = greet.bind(person);
boundGreet(); // 'Charlie'

// Even if you try to change 'this', it won't work
const anotherPerson = { name: 'David' };
boundGreet.call(anotherPerson); // Still 'Charlie'

/*
Syntax: func.bind(thisArg, arg1, arg2, ...)
- Returns a NEW function
- 'this' is permanently set
- Cannot be overridden
*/
```

**Partial Application with bind:**

```javascript
function multiply(a, b) {
  return a * b;
}

const double = multiply.bind(null, 2);
console.log(double(5)); // 10
console.log(double(10)); // 20

/*
bind can pre-fill arguments
bind(null, 2) → first arg is always 2
*/
```

---

### 4. `new` Binding (Highest Priority)

When a function is called with `new`, a new object is created and `this` is bound to it.

```javascript
function Person(name) {
  this.name = name;
  this.greet = function() {
    console.log('Hello, ' + this.name);
  };
}

const alice = new Person('Alice');
alice.greet(); // 'Hello, Alice'

/*
What 'new' does:
1. Creates a new empty object
2. Sets 'this' to that new object
3. Links object to prototype
4. Returns the object (unless function explicitly returns object)
*/
```

**Step-by-step:**

```javascript
function Car(brand) {
  // 1. Empty object created: {}
  // 2. 'this' = that object
  
  this.brand = brand;
  
  // 3. Return 'this' (implicitly)
}

const myCar = new Car('Toyota');
console.log(myCar.brand); // 'Toyota'

/*
Equivalent to:
function Car(brand) {
  const obj = {};
  obj.brand = brand;
  return obj;
}
*/
```

---

## Binding Priority

### Priority Test

```javascript
function test() {
  console.log(this.name);
}

const obj1 = { name: 'obj1', test: test };
const obj2 = { name: 'obj2' };

// Implicit vs Default
obj1.test(); // 'obj1' (implicit wins)

// Explicit vs Implicit
obj1.test.call(obj2); // 'obj2' (explicit wins)

// new vs Implicit
const boundTest = test.bind(obj1);
const newObj = new boundTest(); // undefined (new wins over bind)
newObj.name = 'newObj';
console.log(newObj.name); // 'newObj'

/*
Priority Order:
1. new (highest)
2. explicit (call/apply/bind)
3. implicit (obj.method)
4. default (lowest)
*/
```

---

## Arrow Functions and `this`

Arrow functions **DO NOT** have their own `this`. They inherit `this` from the enclosing lexical scope.

### Regular Function vs Arrow Function

```javascript
const obj = {
  name: 'Object',
  
  regularFunc: function() {
    console.log(this.name); // 'this' determined by call-site
  },
  
  arrowFunc: () => {
    console.log(this.name); // 'this' from enclosing scope
  }
};

obj.regularFunc(); // 'Object' ✅
obj.arrowFunc();   // undefined (or global name) ❌

/*
Arrow function 'this' is lexical (where it's defined)
Regular function 'this' is dynamic (how it's called)
*/
```

### Arrow Functions Fix Callbacks

```javascript
const person = {
  name: 'Alice',
  hobbies: ['reading', 'coding'],
  
  // Problem with regular function
  showHobbies1: function() {
    this.hobbies.forEach(function(hobby) {
      console.log(this.name + ' likes ' + hobby);
      // 'this' is undefined or global ❌
    });
  },
  
  // Solution 1: Arrow function
  showHobbies2: function() {
    this.hobbies.forEach((hobby) => {
      console.log(this.name + ' likes ' + hobby);
      // 'this' inherited from showHobbies2 ✅
    });
  },
  
  // Solution 2: bind
  showHobbies3: function() {
    this.hobbies.forEach(function(hobby) {
      console.log(this.name + ' likes ' + hobby);
    }.bind(this));
  },
  
  // Solution 3: Store 'this'
  showHobbies4: function() {
    const self = this;
    this.hobbies.forEach(function(hobby) {
      console.log(self.name + ' likes ' + hobby);
    });
  }
};

person.showHobbies2(); // ✅ Works
```

### Arrow Functions Cannot be Bound

```javascript
const obj = { name: 'obj' };

const arrowFunc = () => {
  console.log(this.name);
};

arrowFunc.call(obj);  // Cannot change 'this'
arrowFunc.apply(obj); // Cannot change 'this'
const bound = arrowFunc.bind(obj);
bound(); // Still cannot change 'this'

/*
Arrow functions ignore call/apply/bind for 'this'
They always use lexical 'this'
*/
```

### Arrow Functions in Classes

```javascript
class Counter {
  constructor() {
    this.count = 0;
    
    // Regular method
    this.incrementRegular = function() {
      this.count++;
    };
    
    // Arrow function
    this.incrementArrow = () => {
      this.count++;
    };
  }
}

const counter = new Counter();

// Problem with regular method
const incrementFn = counter.incrementRegular;
incrementFn(); // Error or doesn't work ❌

// Works with arrow function
const incrementArrow = counter.incrementArrow;
incrementArrow(); // Works! ✅
console.log(counter.count); // 1
```

---

## Common Pitfalls

### Pitfall 1: Event Handlers

```javascript
class Button {
  constructor() {
    this.count = 0;
  }
  
  // Problem: Regular method
  handleClickRegular() {
    this.count++;
    console.log(this.count);
  }
  
  // Solution: Arrow function
  handleClickArrow = () => {
    this.count++;
    console.log(this.count);
  }
}

const btn = new Button();

// DOM event listener
document.querySelector('button')
  .addEventListener('click', btn.handleClickRegular);
  // 'this' will be the button element, not btn instance ❌

document.querySelector('button')
  .addEventListener('click', btn.handleClickArrow);
  // 'this' is correctly btn instance ✅

// Or use bind
document.querySelector('button')
  .addEventListener('click', btn.handleClickRegular.bind(btn));
  // Also works ✅
```

### Pitfall 2: setTimeout/setInterval

```javascript
const obj = {
  name: 'Object',
  
  delayedGreet: function() {
    setTimeout(function() {
      console.log(this.name); // undefined ❌
    }, 1000);
  },
  
  delayedGreetFixed: function() {
    setTimeout(() => {
      console.log(this.name); // 'Object' ✅
    }, 1000);
  }
};

obj.delayedGreet();
obj.delayedGreetFixed();
```

### Pitfall 3: Method Extraction

```javascript
const person = {
  name: 'Alice',
  greet() {
    console.log(this.name);
  }
};

person.greet(); // 'Alice' ✅

const { greet } = person; // Destructuring extracts method
greet(); // undefined ❌

// Solutions:
const greetBound = person.greet.bind(person);
greetBound(); // 'Alice' ✅

// Or use arrow function wrapper
const greetWrapper = () => person.greet();
greetWrapper(); // 'Alice' ✅
```

### Pitfall 4: Nested Functions

```javascript
const obj = {
  name: 'Object',
  
  method: function() {
    console.log(this.name); // 'Object' ✅
    
    function nested() {
      console.log(this.name); // undefined ❌
    }
    
    nested();
  }
};

obj.method();

// Fix: Arrow function
const obj2 = {
  name: 'Object',
  
  method: function() {
    console.log(this.name); // 'Object' ✅
    
    const nested = () => {
      console.log(this.name); // 'Object' ✅
    };
    
    nested();
  }
};
```

---

## Interview Questions

### Q1: What will be the output?

```javascript
const obj = {
  name: 'obj',
  getName: function() {
    return this.name;
  }
};

console.log(obj.getName());
const getNameFn = obj.getName;
console.log(getNameFn());
```

**Answer:**
```
'obj'
undefined (or error in strict mode)
```

**Explanation:**
- `obj.getName()` - Implicit binding, `this` = obj
- `getNameFn()` - Default binding, `this` = global/undefined

---

### Q2: Predict the output:

```javascript
var name = 'Global';

const person = {
  name: 'Alice',
  greet: () => {
    console.log(this.name);
  }
};

person.greet();
```

**Answer:**
```
'Global'
```

**Explanation:**
Arrow functions don't have their own `this`. They inherit from enclosing scope (global in this case).

---

### Q3: What's the output?

```javascript
function Person(name) {
  this.name = name;
  
  this.greet = function() {
    console.log('Hello, ' + this.name);
  };
  
  this.delayedGreet = function() {
    setTimeout(function() {
      console.log('Delayed: ' + this.name);
    }, 1000);
  };
}

const alice = new Person('Alice');
alice.greet();
alice.delayedGreet();
```

**Answer:**
```
'Hello, Alice'
'Delayed: undefined' (or global name)
```

**Explanation:**
- `greet()` - `this` = alice (implicit binding)
- `delayedGreet()` - setTimeout callback loses context (default binding)

**Fix:**
```javascript
this.delayedGreet = function() {
  setTimeout(() => {
    console.log('Delayed: ' + this.name);
  }, 1000);
};
```

---

### Q4: What's the difference?

```javascript
const obj = {
  name: 'obj',
  method1: function() { console.log(this.name); },
  method2: () => { console.log(this.name); }
};

obj.method1();
obj.method2();
```

**Answer:**
```
'obj'
undefined (or global name)
```

**Explanation:**
- Regular function: `this` determined by call-site (obj)
- Arrow function: `this` from lexical scope (global)

---

### Q5: Priority test:

```javascript
function test() {
  console.log(this.value);
}

const obj1 = { value: 1 };
const obj2 = { value: 2 };

test.call(obj1);
test.call(obj2);

const boundTest = test.bind(obj1);
boundTest.call(obj2);
```

**Answer:**
```
1
2
1
```

**Explanation:**
- First two: `call()` explicitly sets `this`
- Last: `bind()` creates permanently bound function, cannot be overridden

---

### Q6: Class methods:

```javascript
class Person {
  constructor(name) {
    this.name = name;
  }
  
  greet() {
    console.log(this.name);
  }
}

const alice = new Person('Alice');
alice.greet();

const greet = alice.greet;
greet();
```

**Answer:**
```
'Alice'
undefined (or TypeError in strict mode)
```

**Explanation:**
Class methods are not bound to instance by default. Extraction loses context.

**Fix:**
```javascript
class Person {
  constructor(name) {
    this.name = name;
    this.greet = this.greet.bind(this); // Bind in constructor
  }
  
  greet() {
    console.log(this.name);
  }
}

// Or use arrow function
class Person {
  constructor(name) {
    this.name = name;
  }
  
  greet = () => {
    console.log(this.name);
  }
}
```

---

### Q7: Nested objects:

```javascript
const obj = {
  value: 'outer',
  inner: {
    value: 'inner',
    getValue: function() {
      return this.value;
    }
  }
};

console.log(obj.inner.getValue());

const getValue = obj.inner.getValue;
console.log(getValue());
```

**Answer:**
```
'inner'
undefined
```

**Explanation:**
- `obj.inner.getValue()` - `this` = inner object
- `getValue()` - Lost context, default binding

---

### Q8: call vs apply vs bind:

```javascript
function introduce(greeting, punctuation) {
  console.log(greeting + ', I am ' + this.name + punctuation);
}

const person = { name: 'Alice' };

// What's the difference?
introduce.call(person, 'Hello', '!');
introduce.apply(person, ['Hello', '!']);
const boundIntroduce = introduce.bind(person, 'Hello', '!');
boundIntroduce();
```

**Answer:**
All output: `'Hello, I am Alice!'`

**Differences:**
- `call()` - Executes immediately, arguments as list
- `apply()` - Executes immediately, arguments as array
- `bind()` - Returns new function, doesn't execute immediately

---

### Q9: Arrow function with new:

```javascript
const ArrowPerson = (name) => {
  this.name = name;
};

const person = new ArrowPerson('Alice');
```

**Answer:**
```
TypeError: ArrowPerson is not a constructor
```

**Explanation:**
Arrow functions cannot be used as constructors. They don't have their own `this` and cannot be called with `new`.

---

### Q10: Complex nesting:

```javascript
const obj = {
  name: 'obj',
  
  method1: function() {
    console.log('1:', this.name);
    
    const method2 = function() {
      console.log('2:', this.name);
      
      const method3 = () => {
        console.log('3:', this.name);
      };
      
      method3();
    };
    
    method2();
  }
};

obj.method1();
```

**Answer:**
```
'1: obj'
'2: undefined'
'3: undefined'
```

**Explanation:**
- `method1` - Implicit binding, `this` = obj
- `method2` - Regular function, lost context, `this` = global
- `method3` - Arrow function, inherits `this` from method2 (which is global)

---

## Visual Summary

```
┌──────────────────────────────────────────────────────────┐
│                   'this' Decision Tree                    │
└──────────────────────────────────────────────────────────┘
                            │
                            ▼
                    ┌───────────────┐
                    │ Arrow Function?│
                    └───────────────┘
                       Yes │   │ No
                    ┌──────┘   └──────┐
                    ▼                  ▼
            ┌──────────────┐   ┌──────────────┐
            │ Lexical 'this'│   │ Check binding│
            │ (from parent) │   │    rules     │
            └──────────────┘   └──────────────┘
                                        │
                        ┌───────────────┼───────────────┐
                        ▼               ▼               ▼
                   ┌────────┐    ┌──────────┐   ┌──────────┐
                   │  new?  │    │call/apply│   │  obj.fn? │
                   │        │    │  /bind?  │   │          │
                   └────────┘    └──────────┘   └──────────┘
                        │              │              │
                        ▼              ▼              ▼
                  new object    explicit obj   implicit obj
                        │              │              │
                        └──────┬───────┴──────┬───────┘
                               ▼              ▼
                          Has binding?    No binding
                               │              │
                               ▼              ▼
                          Use that      global/undefined
```

---

## Key Takeaways

### Rules to Remember

1. **Arrow functions** inherit `this` from parent scope (lexical)
2. **Regular functions** determine `this` by how they're called (dynamic)
3. **Priority:** new > explicit > implicit > default
4. **Lost context:** Method extraction, callbacks lose `this`
5. **bind()** creates permanently bound function

### Common Patterns

```javascript
// Pattern 1: Save context
const self = this;
setTimeout(function() {
  console.log(self.name);
}, 1000);

// Pattern 2: Arrow function
setTimeout(() => {
  console.log(this.name);
}, 1000);

// Pattern 3: bind
setTimeout(this.method.bind(this), 1000);

// Pattern 4: Class field arrow function
class MyClass {
  method = () => {
    console.log(this);
  }
}
```

### When to Use What

| Scenario | Solution |
|----------|----------|
| Event handlers | Arrow function or bind |
| Callbacks | Arrow function |
| Object methods | Regular function |
| Constructor | Regular function (not arrow) |
| Array methods | Arrow function for callbacks |
| Class methods (extracted) | Arrow function or bind in constructor |

---

## Quick Reference

```javascript
// Default Binding
function fn() { console.log(this); }
fn(); // global or undefined

// Implicit Binding
obj.fn(); // this = obj

// Explicit Binding
fn.call(obj);   // this = obj
fn.apply(obj);  // this = obj
fn.bind(obj)(); // this = obj

// new Binding
new fn(); // this = new object

// Arrow Function
const arrow = () => console.log(this);
// 'this' from parent scope, cannot be changed
```

---

**Last Updated:** March 2026

## Resources

- [MDN: this](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/this)
- [You Don't Know JS: this & Object Prototypes](https://github.com/getify/You-Dont-Know-JS/tree/2nd-ed/this%20%26%20object%20prototypes)

**Good luck with your interview! 🚀**