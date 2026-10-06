# Java Learning Through a Real-World Project

## Goal and learning approach

Learn Java and its backend ecosystem by building one evolving real-world project. The learner already has software development and architecture experience and prefers practical examples that build lasting understanding.

Use the attached roadmap as a coverage checklist. Its promise of a job in three months is marketing, not a guaranteed outcome. Measure progress by what you can build, explain, test, and debug independently.

Build a working feature, understand the Java concepts behind it, and introduce a harder requirement that makes you revisit the design.

## Project: Order and Inventory Management System

Business flow:

> A customer places an order → stock is reserved → payment is attempted → the order is confirmed → a notification is sent.

Include customers, products, warehouses, orders, simulated payments, and notifications.

Start with a command-line application, evolve it into a Spring Boot application, and later extract one service. This provides firsthand experience with architectural tradeoffs.

Use a payment simulator that can deliberately become slow, fail, or return duplicate responses.

## Coverage of all 12 roadmap sections

| Roadmap topic | Project-based learning |
| --- | --- |
| 1. Core Java | Collections for products and orders; exceptions for invalid operations; concurrency for stock reservation. Inspect compilation, JVM execution, memory, and garbage collection through small experiments. Learn the roles of the JDK, JVM, and runtime environment. |
| 2. OOP | Encapsulate order state and stock rules. Use interfaces for payment and notification providers. Explore inheritance, polymorphism, abstraction, and composition. |
| 3. SOLID and design patterns | Refactor when adding a second payment provider. Apply factories and builders when useful. Use focused exercises for Singleton, Prototype, Abstract Factory, Factory Method, and Builder when the application does not naturally need them. |
| 4. Java 8 features | Streams for sales reports, lambdas for filtering, functional interfaces for discount rules, method references, and Optional for appropriate lookup results. Also learn modern Java features. |
| 5. Coding questions | Duplicate orders, top-selling products, grouping sales, SKU lookup, and inventory reconciliation. Practise strings, arrays, collections, HashMap problems, and streams. Add separate algorithm practice when needed. |
| 6. Spring Boot | REST endpoints, dependency injection, bean lifecycle, annotations, validation, Spring Data JPA, transactions, global exception handling, Spring Security, OAuth2/JWT, and caching. |
| 7. Microservices | Extract notifications or payments. Explore inter-service communication, API gateway routing, service discovery, timeouts, circuit breakers, and distributed tracing. |
| 8. Project-based questions | Explain architecture, responsibilities, end-to-end features, technology choices, challenging work, third-party API integration, performance improvements, production debugging, design decisions, and future improvements. Keep implementation evidence. |
| 9. Scenario-based questions | Inject slow queries, failed dependencies, concurrent updates, duplicate requests, and memory pressure. Investigate slow APIs, high request volume, service outages, external API failures, out-of-memory errors, production incidents, and REST API scaling. Diagnose before fixing. |
| 10. CI/CD | Git and Maven from the start. Later automate tests, build a Docker image, and deploy through one pipeline. Choose one CI platform, such as Jenkins or GitLab CI, rather than learning several at once. |
| 11. Cloud deployment | Learn AWS basics, deploy the application on EC2, store attachments in S3, and explore load balancing and multiple instances. |
| 12. Interview preparation | Revise Java, Spring Boot, and microservices. Rebuild selected features without assistance; explain underlying concepts; practise coding, project, scenario, resume-based, and design questions; conduct mock interviews. |

## Initial 12-week plan

Assumption: approximately 8–10 focused hours per week. Adjust the schedule to actual progress; the milestones matter more than the dates.

| Period | Deliverable | What it should prove |
| --- | --- | --- |
| Weeks 1–2 | Plain Java order application with in-memory storage | Write idiomatic Java, model business rules, use collections and generics, handle errors, and test behavior. |
| Weeks 3–4 | REST application backed by PostgreSQL | Understand Spring dependency injection, HTTP, persistence, validation, and transaction boundaries. |
| Weeks 5–6 | Secure ordering with reliable stock reservation | Handle authorization, concurrent updates, rollback, and duplicate submissions. |
| Weeks 7–8 | Measured performance and failure exercises | Investigate slow requests and queries, caching, thread behavior, and memory usage. |
| Weeks 9–10 | One extracted service | Understand what changes when a local method call becomes a network call. |
| Weeks 11–12 | Automated deployment and technical walkthrough | Deploy, observe, troubleshoot, and defend the design. |

## Architecture progression

Begin with a modular monolith: one deployment with clear internal boundaries. Focus first on Java behavior and the Spring execution model. Extract one service later to get a concrete before-and-after comparison.

Avoid adding infrastructure and patterns solely to satisfy a checklist. Learn each concept, but retain it in the application only when it solves an actual problem. Use small companion exercises for concepts that do not fit naturally.

## Additions and improvements to the image's curriculum

- Introduce testing, Git, and Maven in the first week.
- Add generics, equals/hashCode, immutability, and resource handling to Core Java.
- Give SQL, indexes, transactions, isolation, and JPA query behavior explicit attention.
- Include logs, metrics, traces, and profiling before cloud deployment.
- Distinguish OAuth2 from JWT: OAuth2 concerns authorization flows; JWT is a token format.
- Learn the listed design patterns without forcing all of them into production code.

## Suggested starting stack

- Java 25 LTS
- Spring Boot, introduced after the plain Java milestone
- Maven
- PostgreSQL
- JUnit
- Git from day one
- Docker later in the learning sequence

As checked on October 6, 2026, Java 25 is an LTS release, and the current Spring Boot documentation lists Java 25 within its supported range. Verify compatibility again when selecting the actual dependency versions.

Sources:

- [Oracle Java support roadmap](https://www.oracle.com/europe/java/technologies/java-se-support-roadmap.html)
- [Spring Boot system requirements](https://docs.spring.io/spring-boot/system-requirements.html)

## Repeatable learning loop

For every feature:

1. Define a business requirement and acceptance criteria.
2. Attempt the implementation yourself.
3. Study the Java concept needed to finish it.
4. Test normal behavior and deliberately break it.
5. Explain the result from memory, then rebuild a small part a few days later.

Example requirement:

> Two customers try to purchase the last item.

Revisit this requirement at different stages to learn synchronization, database transactions, optimistic locking, retries, and concurrency testing.

## Mentoring approach

Use the assistant as a mentor: requirements and hints first, implementation review next, explanations and alternatives afterward. Avoid generating the whole project upfront, since that reduces practice retrieving and applying concepts.

Bring existing architecture experience to the design discussions while spending deliberate practice on how Java expresses and executes those ideas.

## First milestone

Build a small plain Java application with tests that can:

- Create products.
- Place an order.
- Reject an order when stock is insufficient.
- Calculate the total using BigDecimal.

Use in-memory storage initially. Add Spring and database persistence after this milestone is understood and complete.

## Progress tracker

Detailed Week 1 tasks and acceptance criteria: [Week 1 checklist](<Week 1/to-dos/README.md>). Record session notes in the [learning log](<Week 1/to-dos/learning-log.md>).

- [ ] Weeks 1–2: Plain Java order application
- [ ] Weeks 3–4: REST API and PostgreSQL
- [ ] Weeks 5–6: Security and reliable stock reservation
- [ ] Weeks 7–8: Performance and failure diagnosis
- [ ] Weeks 9–10: Extract one service
- [ ] Weeks 11–12: Deployment and technical walkthrough

For each milestone, record what was built, concepts learned, bugs investigated, design decisions, verification evidence, and remaining questions. Update this README as the plan evolves.
