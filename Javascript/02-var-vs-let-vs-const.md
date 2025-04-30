# 🔀 Difference Between `var`, `let`, and `const` in JavaScript

In JavaScript, you use `var`, `let`, and `const` to declare variables — but each behaves differently.

---

## 🟡 `var`

### ✅ Features:
- **Function-scoped** (NOT block-scoped)
- Can be **redeclared** in the same scope
- Can be **updated** (value reassigned)
- Gets **hoisted** (moved to top of scope), but initialized as `undefined`

### ⚠️ Issues:
- Hoisting + function scope = hard-to-find bugs
- Not recommended in modern JS (use `let` or `const` instead)

### Example:
```js
var x = 10;
var x = 20; // ✅ Redeclaration allowed

function test() {
  if (true) {
    var a = 5;
  }
  console.log(a); // ✅ Works: var is function-scoped
}
```

---

## 🟢 `let`

### ✅ Features:
- **Block-scoped** (respects `{}` boundaries)
- Can be **updated**, but NOT redeclared in the same scope
- **Hoisted**, but **not initialized** (throws error if used before declaration)

### Use when:
- You need to change the variable’s value

### Example:
```js
let y = 15;
y = 25;     // ✅ Allowed
let y = 30; // ❌ Error: cannot redeclare in the same scope

if (true) {
  let b = 50;
}
console.log(b); // ❌ Error: b is not defined (block scope)
```

---

## 🔵 `const`

### ✅ Features:
- **Block-scoped** (like `let`)
- Cannot be **updated or redeclared**
- Must be **initialized during declaration**
- Only the **reference** is constant (for objects/arrays, properties can still change)

### Use when:
- The value should **never change** (constants)

### Example:
```js
const z = 100;
z = 200;     // ❌ Error: can't reassign

const obj = { name: "John" };
obj.name = "Doe"; // ✅ Allowed (mutating object, not reassigning)

const obj = { age: 25 };
obj = { age: 30 }; // ❌ Error: can't reassign a const object
```

---

## 🔁 Summary Table

| Feature                | `var`              | `let`               | `const`                  |
|------------------------|--------------------|----------------------|---------------------------|
| Scope                 | Function            | Block               | Block                     |
| Hoisting              | Yes (initialized as `undefined`) | Yes (but TDZ) | Yes (but TDZ)            |
| Redeclaration         | ✅ Yes              | ❌ No               | ❌ No                    |
| Reassignment          | ✅ Yes              | ✅ Yes              | ❌ No                    |
| Initialization Needed | ❌ No               | ❌ No               | ✅ Yes                   |

---

## ✅ Best Practices

- ✅ Use `**const**` by default
- ✅ Use `**let**` only when the value will change
- ❌ Avoid using `var` in modern code

---

## 🧠 TDZ (Temporal Dead Zone)

> The time between entering the block and the actual declaration is the **TDZ** — accessing `let` or `const` before their line throws an error.

```js
console.log(a); // ❌ ReferenceError
let a = 5;
```

---

## 🔁 Quick Recap

- `var` is **old-school**, function-scoped — avoid it in modern code
- `let` is **flexible**, block-scoped — great when value needs to change
- `const` is **strict**, block-scoped — best for constants and safer code

---
