# Campus Cafe Inventory Manager

A console program that lets cafe staff add products, list them, and check the stock: total value, average price, restocking and duplicating a product. The menu keeps running until the user chooses 0.

## Files

| File | What it is |
|---|---|
| `Product.java` | The product class: three constructors (no-argument, parameterized, copy), `stockValue()`, `display()` and the static `totalProducts` counter |
| `Main.java` | The menu loop (`do`/`while` + `switch`) with options 0 to 6, storing up to 10 products in an array |
| `Warmup.java` | The casting and `switch` fall-through warm-up |
| `output.txt` | A full test session that uses every menu option, including invalid input |

## How to run

```
javac *.java
java Main
java Warmup
```

It needs only a standard JDK and `java.util.Scanner`.

## Input checks beyond the brief

The starter read numbers with `sc.nextInt()` and `sc.nextDouble()`, which crash on input such as `abc`. `Main` now reads input through small helper methods instead:

- If you type something that isn't a number (letters, `2.5` where a whole number is needed, `NaN`, `Infinity`), it asks again instead of crashing.
- A product name that is empty or only spaces is rejected, and spaces around a name are trimmed.
- A restock that would push the quantity past the largest `int` (2,147,483,647) is refused instead of wrapping round to a negative number.
- If the input runs out before 0 is chosen (Ctrl+D), the program says goodbye and prints the product count instead of crashing.

## Questions

**1. What did you predict for Warmup.java, and what did it actually print? Explain the lines that surprised you.**

I predicted `7 5 130` and then just `Two`. It actually printed `7 5.0 -126`, then `Two` and `Three`. `d` prints as `5.0` because it is a `double`, even though the value came from an `int`. A `byte` only holds -128 to 127, so 130 wraps around to 130 − 256 = −126. `Three` also prints because the cases have no `break`, so execution falls through from `case 2` into `case 3`.

**2. What is the difference between the static variable `totalProducts` and the instance variable `name`?**

`totalProducts` belongs to the class itself, so there is only one copy, shared by every `Product`, and it is read as `Product.totalProducts`. Every constructor increases that same counter. `name` is an instance variable, so each `Product` object has its own, and changing one product's name has no effect on any other product.

**3. What does the copy constructor do, and why does changing `p2.name` not change `p1.name`?**

`new Product(p1)` creates a brand-new object and copies `p1`'s name, price and quantity into it (and increases `totalProducts`). `p1` and `p2` refer to two different objects in memory, so `p2.name = "Coffee"` only changes the copy's field and `p1` stays `Tea`. Writing `p2 = p1` would be different: both variables would point at the same object, and the change would show up through both.

**4. Why does Java force you to write `(int) average` when converting a double to an int, and what happens to the decimal part?**

Going from `double` to `int` is a narrowing conversion that can lose information, so Java will not do it silently and needs the explicit cast to show you mean it. The decimal part is cut off (truncated), not rounded, so `12.75` becomes `12`. The other direction, `int` to `double`, is widening and loses nothing, which is why `double d = x;` and `price * quantity` need no cast.
