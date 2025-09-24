# Campus-Course-Manager
A **console-based Java SE application** for managing students, courses, enrollments, grades, and transcripts with file import/export and backup utilities.  

This project demonstrates **OOP principles**, **Java SE core features**, **Streams API**, **NIO.2 file handling**, and **design patterns** (Singleton, Builder).  

---

## 🚀 Features
- Student Management (add/list/update/deactivate, print transcript).  
- Course Management (add/list/update/deactivate, search & filter).  
- Enrollment & Grading (business rules, GPA calculation).  
- File Operations (CSV import/export, backup with NIO.2, recursive utilities).  
- CLI workflow (menu-driven console with loops, conditions, jump statements).  

---

## 📂 Project Structure
edu.ccrm
- ├─ cli/ // Console menu
- ├─ domain/ // Person, Student, Instructor, Course, Enrollment, enums
- ├─ service/ // Business logic: StudentService, CourseService, EnrollmentService
- ├─ io/ // Import/export, backup (NIO.2, Streams)
- ├─ util/ // Validators, Comparators, recursion utilities
- └─ config/ // AppConfig (Singleton), Builders


---

## 📖 Evolution of Java (Timeline)

- **1995** – Java 1.0 released by Sun Microsystems (WORA: Write Once, Run Anywhere).  
- **2004** – Java 5: Generics, annotations, enums introduced.  
- **2011** – Java 7: NIO.2, try-with-resources.  
- **2014** – Java 8: Lambdas, Streams API, Date/Time API.  
- **2017** – Oracle moved to a 6-month release cycle.  
- **2021** – Java 17 (LTS) introduced records, sealed classes, pattern matching.  

---

## 📊 Java Editions

| Edition  | Purpose | Typical Use-Cases |
|----------|---------|--------------------|
| **Java SE** (Standard Edition) | Core Java platform | Desktop apps, console apps, core APIs (Collections, IO, JDBC, Streams) |
| **Java EE** (Enterprise Edition) | Built on top of SE, adds enterprise APIs | Web apps, distributed apps, Servlets, JSP, EJB, JPA |
| **Java ME** (Micro Edition) | Subset for embedded/mobile devices | IoT, feature phones, embedded systems |

---

## ⚙️ Java Architecture: JDK, JRE, JVM

- **JDK (Java Development Kit)** → Tools for developers (compiler `javac`, debugger, JRE).  
- **JRE (Java Runtime Environment)** → Libraries + JVM to run Java apps.  
- **JVM (Java Virtual Machine)** → Executes Java bytecode on any OS.  

**Flow:**  
Source Code (.java) → javac compiler → Bytecode (.class) → JVM → OS/Hardware

---

## 🖥️ Install & Configure Java on Windows

1. Download **JDK** from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://jdk.java.net/).  
2. Install in `C:\Program Files\Java\jdk-17`.  
3. Set environment variables:  
   - `JAVA_HOME = C:\Program Files\Java\jdk-17`  
   - Add `%JAVA_HOME%\bin` to PATH.  
4. Verify installation:  
   ```bash
   java -version
   javac -version
---

## ✍️ Author & Purpose

This project **Campus Course & Records Manager (CCRM)** was developed by ***Advay Bhagat***, ***Register No: 24BCE10305***
as part of an academic exercise to demonstrate the practical application of **Java SE concepts,  
object-oriented programming principles, design patterns, and modern Java APIs**.  

The goal of this project is to showcase how a real-world academic records system can be  
implemented in Java while following clean architecture, modular design, and best coding practices.  
