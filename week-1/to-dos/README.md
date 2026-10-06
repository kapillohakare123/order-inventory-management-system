# Week 1: Plain Java Order Application

## Outcome

Build a command-line application that manages products and stock, places orders, and retains data in memory while the application runs. Restarting the application resets the data.

Work in small steps. Attempt each implementation yourself, then request a review or hints. Start with a scripted demo; add an interactive menu after the business behavior works.

Suggested time: 8–10 focused hours across seven sessions. Completion criteria matter more than calendar days.

## Scope

Implement product creation, product listing, stock replenishment, order placement, order lookup, and order listing. Use a single currency (INR), one inventory location, immediate stock deduction, and one application thread.

Defer customers, warehouses, persistence, REST, Spring, payments, notifications, cancellation, discounts, taxes, authentication, and concurrency to later milestones.

## Business rules

- Product IDs and order IDs are unique. Start with UUIDs; a SKU is a separate unique business identifier.
- A product has an ID, SKU, name, and unit price. SKU matching ignores surrounding whitespace and letter case; normalize before storing and looking up.
- Names and SKUs must not be blank. Prices must be greater than zero, with no more than two fractional digits.
- Store and calculate money using BigDecimal constructed from decimal strings, not double. Compare monetary values numerically with compareTo.
- Initial stock is zero or positive. Replenishment quantities and ordered quantities must be positive integers.
- An order contains at least one item. For this milestone, reject duplicate product IDs within an order rather than silently merging them.
- Reject an order if any product is unknown or any item has insufficient stock. A rejected order must change neither stock nor stored orders.
- Validate the entire order before changing stock. This is all-or-nothing business behavior in a single-threaded application, not a database transaction or a concurrency guarantee.
- A successful order reduces stock and stores an order with ID, creation time, items, and total.
- Each order item stores a snapshot of the product ID, name, unit price, and quantity. Existing order totals must not depend on later product changes.
- Line total = unit price × quantity. Order total = sum of line totals. Display money with two decimal places.
- Avoid exposing mutable internal collections or allowing callers to bypass stock rules.

## Steps and acceptance criteria

### Session 1 — Set up and run a tiny Java program

- [ ] Confirm JDK 25 is installed and your editor uses it.
- [ ] Create a Maven project in `Week 1/app`, with `pom.xml`, `src/main/java`, and `src/test/java`.
- [ ] Use a package such as `com.kapil.orderinventory` and create an application entry point.
- [ ] Add JUnit and run one small test to verify the setup.
- [ ] Ignore build output (`target/`) in Git.

**Done when:** you can compile, run the entry point, and execute a test from the terminal.

**Learn:** JDK versus JVM, source versus bytecode, packages, main method, Maven lifecycle, and test execution.

### Session 2 — Create and list products

- [ ] Model a Product with ID, normalized SKU, name, and BigDecimal price.
- [ ] Implement in-memory product storage using a Map.
- [ ] Add product creation, lookup by ID, and listing.
- [ ] Reject duplicate SKUs, blank names/SKUs, and invalid prices.
- [ ] Seed two products in a scripted demo.

**Done when:** valid products can be retrieved and invalid products are rejected without changing storage.

**Learn:** classes, constructors, encapsulation, generics, Map, String handling, BigDecimal, and validation. Discuss List versus Map and equals/hashCode during review.

### Session 3 — Track inventory

- [ ] Track available quantity by product ID, keeping inventory separate from product details.
- [ ] Initialize stock when a product is created.
- [ ] Add stock replenishment and available-stock lookup.
- [ ] Reject unknown products, negative initial stock, and nonpositive replenishment.

**Done when:** replenishment increases stock correctly and invalid requests leave stock unchanged.

**Learn:** service methods, object collaboration, state changes, and protecting invariants.

### Session 4 — Model and calculate an order

- [ ] Model an order request item (product ID and quantity), OrderItem, and Order.
- [ ] Calculate line totals and order totals using BigDecimal.
- [ ] Capture item price/name snapshots and a creation timestamp.
- [ ] Make the stored item collection immutable or defensively copied.

**Done when:** two items with prices 499.90 and 1499.00, quantities 2 and 1, produce a total of 2498.80.

**Learn:** composition, value objects, collections, immutability, java.time, and decimal arithmetic.

### Session 5 — Place an order safely

- [ ] Implement placeOrder: validate request, resolve products, check all stock, construct order, deduct stock, then store the order.
- [ ] Reject empty orders, invalid quantities, duplicate product IDs, unknown products, and insufficient stock.
- [ ] Add order lookup by ID and order listing.
- [ ] Choose clear exception messages; introduce a custom business exception only if it improves clarity.

**Done when:** a valid order deducts stock exactly once, and every rejected order leaves all stock and orders unchanged.

**Learn:** control flow, separation of responsibilities, exceptions, and all-or-nothing operations.

### Session 6 — Add a small command-line menu

- [ ] Add options: create product, list products with stock, replenish stock, place order, view order, list orders, exit.
- [ ] Keep input/output handling separate from business rules.
- [ ] Handle malformed input and business errors without terminating the application.
- [ ] Show order IDs, item details, totals, and remaining stock.

**Done when:** someone can complete the happy path from the menu and recover from an invalid input.

**Learn:** input parsing, loops, switch, exception handling, and try-with-resources where appropriate.

### Session 7 — Verify, explain, and review

- [ ] Test successful product creation and duplicate SKU rejection.
- [ ] Test money calculation with decimal prices.
- [ ] Test successful ordering and exact stock deduction.
- [ ] Test ordering the exact remaining stock (ending at zero).
- [ ] Test a multi-item order where the last item lacks stock: no earlier item's stock may change.
- [ ] Test unknown products, empty orders, duplicate items, and nonpositive quantities.
- [ ] Confirm order item snapshots and collection protection.
- [ ] Run a complete demo and record the commands needed to build, test, and run it.
- [ ] Explain the implementation without reading the code, then request a review.

**Done when:** acceptance criteria pass and you can explain the behavior and key design choices independently.

## Suggested structure

```text
Week 1/
  to-dos/
    README.md
    learning-log.md
  app/
    pom.xml
    src/main/java/com/kapil/orderinventory/
      Application.java
      model/
      service/
      repository/
      cli/
    src/test/java/com/kapil/orderinventory/
```

This is a starting guide, not a requirement to create every abstraction immediately. Concrete in-memory repositories are sufficient initially. Introduce interfaces when discussing a future persistence implementation; avoid building a generic repository framework.

## End-to-end demo

1. Create `KEYBOARD` at INR 499.90 with stock 10.
2. Create `HEADSET` at INR 1499.00 with stock 5.
3. Order two keyboards and one headset.
4. Verify total INR 2498.80, keyboard stock 8, headset stock 4.
5. Attempt an order for one keyboard and five headsets.
6. Verify rejection, keyboard stock still 8, headset stock still 4, and no extra order stored.
7. Retrieve the successful order by ID.
8. Restart the application and confirm in-memory data has reset.

## Review questions

- Why BigDecimal rather than double for prices?
- Why use a Map for product lookup? What changes if we use a List?
- Why validate every order item before deducting any stock?
- Why does an order keep the price at purchase time?
- How could a caller accidentally mutate internal state?
- What will break if two threads try to buy the last item?
- What changes when storage moves to a database?

## Next action

Start with Session 1, then implement product creation and listing from Session 2. Keep the first review small: setup, Product, product storage, and validation.
