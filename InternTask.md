# 🚀 CDAP Wrangler Enhancement – Byte Size & Time Duration Parsers

**Name**: Fidelis Coraro  
**Position**: Software Engineer Intern  
**Project**: CDAP Wrangler Enhancement – Byte Size & Time Duration Parsers  
**GitHub Repo**: [https://github.com/fidelis-coraro/wrangler](https://github.com/fidelis-coraro/wrangler)

---

## 📘 Introduction

This project enhances the **CDAP Wrangler** core library by adding native support for parsing:

- **Byte Size units** (e.g., `10KB`, `1.5MB`, `2GB`)
- **Time Duration units** (e.g., `500ms`, `2s`, `1.5min`)





---

## 🛠️ Implementation Overview

### ✅ Forked Repo:
> [https://github.com/fidelis-coraro/wrangler](https://github.com/fidelis-coraro/wrangler)

### ✅ Grammar Changes (`Directives.g4`)
- Added `BYTE_SIZE` and `TIME_DURATION` lexer and parser rules.
- Regenerated ANTLR parser with `mvn compile`.

### ✅ Token Types
- Created `ByteSize.java` and `TimeDuration.java` to parse and convert values.
- Extended `TokenType` enum.

### ✅ Directive: `aggregate-stats`

    - Byte size column
    - Time duration column
    - Output size column
    - Output time column
   

---

## 📋 Usage Examples

```wrangler
// Basic total
aggregate-stats :data_size :response_time total_size_mb total_time_sec

// Average with unit conversion
aggregate-stats :data_size :response_time avg_size avg_time average MB seconds

