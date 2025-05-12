# Null vs Undefined in JavaScript

JavaScript has two distinct primitive values that represent the absence of a meaningful value: `null` and `undefined`. While they might seem similar at first glance, they have important differences in their purpose, behavior, and usage.

## Undefined

`undefined` is the default value assigned when:
- A variable is declared but not initialized
- An object property doesn't exist
- A function doesn't explicitly return a value
- A function parameter is not provided

### Key Characteristics
- Type: `typeof undefined` returns `"undefined"`
- Automatically assigned by JavaScript
- Represents absence of assignment
- Not a valid JSON value
- Coercion: In numeric contexts, becomes `NaN`

## Null

`null` is a value that represents the intentional absence of any object value. It's explicitly assigned by developers to indicate:
- An intentional absence of value
- An object that doesn't exist
- A variable that will later be assigned an object

### Key Characteristics
- Type: `typeof null` returns `"object"` (this is a historical bug in JavaScript)
- Must be explicitly assigned
- Represents intentional emptiness
- Valid JSON value
- Coercion: In numeric contexts, becomes `0`

## Code Examples

```javascript
// Undefined examples
let a;
console.log(a); // undefined

function noReturn() {
  // No return statement
}
console.log(noReturn()); // undefined

function sayHi(name) {
  console.log(name);
}
sayHi(); // undefined

const obj = {};
console.log(obj.nonExistentProperty); // undefined

// Null examples
let emptyValue = null;
console.log(emptyValue); // null

let user = { name: "John" };
user = null; // User no longer exists
```

## Comparisons and Equality

```javascript
// Equality comparison
console.log(null == undefined); // true (loose equality)
console.log(null === undefined); // false (strict equality)

// Type checks
console.log(typeof undefined); // "undefined"
console.log(typeof null); // "object"

// Truthiness
console.log(Boolean(undefined)); // false
console.log(Boolean(null)); // false
```

## Common Interview Questions

1. **What is the difference between `null` and `undefined` in JavaScript?**
   - `undefined` means a variable has been declared but not assigned a value
   - `null` is an explicitly assigned value representing "no value" or "empty"

2. **What does `typeof null` return and why?**
   - It returns `"object"`, which is considered a bug in JavaScript
   - This bug was introduced in the early versions and kept for backward compatibility

3. **How can you check if a variable is `null` or `undefined`?**
   ```javascript
   // For null specifically
   if (variable === null) { /* ... */ }
   
   // For undefined specifically
   if (variable === undefined) { /* ... */ }
   
   // For either null or undefined
   if (variable == null) { /* ... */ }
   
   // Using typeof for undefined
   if (typeof variable === "undefined") { /* ... */ }
   ```

4. **In what scenarios would you use `null` over `undefined`?**
   - When you want to explicitly indicate that a variable has no value
   - When you want to reset an object reference
   - When working with APIs or data structures where `null` is a valid state

5. **What happens when you use `null` and `undefined` in mathematical operations?**
   - `undefined` becomes `NaN` in mathematical operations
   - `null` becomes `0` in mathematical operations

6. **Can you JSON stringify `null` and `undefined`?**
   - `JSON.stringify(null)` returns `"null"`
   - `undefined` values in objects are omitted during stringification
   - `JSON.stringify(undefined)` returns `undefined`

7. **What is the output of `null == 0` and `undefined == 0`?**
   - `null == 0` returns `false`
   - `undefined == 0` returns `false`

8. **How do you check if a property exists vs if it's `undefined` or `null`?**
   ```javascript
   // Checking if property exists
   if ('propertyName' in object) { /* ... */ }
   
   // Checking if property is not undefined
   if (object.propertyName !== undefined) { /* ... */ }
   
   // Checking if property has a value (not null or undefined)
   if (object.propertyName != null) { /* ... */ }
   ```

9. **What is the safest way to check for both `null` and `undefined`?**
   - `if (variable == null)` checks for both `null` and `undefined`
   - Using the optional chaining operator (`?.`) for nested properties
   - Using the nullish coalescing operator (`??`) for default values

10. **What does `void 0` return and why is it used?**
    - `void 0` returns `undefined`
    - It's sometimes used as a more reliable way to get the `undefined` value since `undefined` can be redefined in older JavaScript environments

## Best Practices

1. Use `undefined` for unintentional absence of value
2. Use `null` for intentional absence of value
3. Avoid explicit assignments of `undefined` (let JavaScript handle this)
4. Use nullish coalescing (`??`) and optional chaining (`?.`) for safer code
5. Be consistent with your usage across your codebase

Understanding these differences will help you write more predictable code and debug issues more effectively.
