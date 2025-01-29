# Class #3 | Tuesday Jan 21st | Collectors API

> This file will contain information imparted in class #3 of this first unit in Advanced Apps Programming. The idea 
> of this file is, most likely, to review the Collectors API to a certain depth. If not, the file will remain mostly 
> mostly empty.

## Callback Methods in Lambdas and the Stream API
 <tldr><i>A callback function is a function passed as an argument to another function and 
executed when that function completes or some event happens. These functions are specially 
useful when <b><code>working with asynchronous code</code></b>
</i></tldr>
<p>Java uses the idea of a callback function in various aspects of its Stream API, Collectors 
API, and Collections API, where we are allowed to send an <code>implementation of an 
interface</code> as the guide method that will fulfill the role of this callback function. In 
general, these are implemented through <code>interfaces defined in places where we would 
expect parameters, allowing us to send an implementation of it</code>.
<br/><br/>
This is the core reason why callback functions are used in tandem with so many APIs in Java, 
since most of them work over the Functional Interfaces that are defined in the Java packages, we 
effectively take advantage of this method by simply providing a method that will be ran later on,
(through an implementation of a functional interface), to a method that expects one.
<br/><br/>
Take for example, the case of filter (<code>Stream.filter(Predicate ...)</code>), this method 
takes a single parameter, a Predicate implementation which defines a single method (functional 
interface requirement) called <code>test()</code>, if we were to use this method in a stream, 
and we were to provide either an implementation, anonymous class, or even a lamda, we would be 
providing a callback function. Of course this <code>callback function will have to be 
resolved through the compiler (in the case of anonymous or lambdas)</code>, but if we know a 
method that <code>effectively conforms to the specification of the Predicate interface</code>, 
we can even use it directly through <b><code>method references</code></b>.
<br/><br/>
method references are patterns of accessing methods without specifying which parameters will be 
used and how, for example, the snippet <code>stream.forEach(System.out::println)</code>, is a 
method reference to the method println held within the OutputStream held within System. Now the 
idea of method references is that we, instead of defining a lambda which in and of itself would 
only call println or some other simpler method, we reference them directly and the stream will 
take care of executing the callback to these functions.
</p>
<note><p>By virtue of their definition, callback functions are implemented in every Stream API 
function, and are the default execution format for these functional interface (lambda version, 
method reference version, or anonymous class version).</p>
</note>
<p>Having defined the basics of callback methods, we shall take a look at how these are executed 
and replicated in Java through examples of code.
</p>

<procedure title="CallBack Methods | Examples" id="call_back_methods_examples" collapsible="true">
<list>
<li><b><format color="CornFlowerBlue">Example using anonymous classes, and functional interfaces</format></b>: 

```Java
package example;

public class Example{

    public static void main(String[] args){
    
      //! Anonymous class as variable
      Predicate<String> stringLongerThanFive = 
        new Predicate<String>() {
            @Override
            public boolean test(String string) {
                return string.length() > 5;
            }
        };

        List<String> strings = 
        List.of("Hello", "World", "This", "is", 
                 "a", "test", "of", "the",
                "longest", "string", "in", "the", "list");
        List<String> filtered = strings
            .stream().filter(stringLongerThanFive).toList();
        System.out.println("filtered = " + filtered);
        // filtered = [longest, string]
    }
    
}
```

</li> 
<li><b><format color="CornFlowerBlue">Example using anonymous classes (actual), and functional Interfaces </format></b>:

```Java
package example;

public class Example{

    public static void main(String[] args){
        int[] flattenedMatrix = Stream.of(matrix)
                .flatMap(new Function<int[], Stream<int[]>>() {
            @Override
            public  Stream<int[]> apply(int[] ints) {
                return Stream.of(ints);
            }
        }).peek(array -> {
                    System.out.println(Arrays.toString(array));
                }).flatMapToInt(new Function<int[], 
                                    IntStream>() {
                    @Override
                    public IntStream apply(int[] ints) {
                        return IntStream.of(ints);
                    }
                }).toArray();
        System.out.println("Arrays.toString(flattenedMatrix) = " 
        + Arrays.toString(flattenedMatrix));
        // [1, 2, 3]
        // [4, 5, 6]
        // [7, 8, 9]
        // [10, 11, 12]
        // Arrays.toString(flattenedMatrix) = 
        // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
    }
}
```
</li> 
<li><b><format color="CornFlowerBlue">Example using method references and functional interfaces</format></b>: 

```Java
List<Employee> employees = List.of(
      new Employee("John", "Aguilar", 100_000.0, "IT"),
      new Employee("Jane", "Arevalo", 20_000.0, "Sales"),
      new Employee("Jack", "Arellano", 30_000.50, "Marketing"),
      new Employee("Jill", "Quilumbo", 44_000.0, "IT"),
      new Employee("Joe", "Perez", 44_001.10, "Sales"));

//1.  Print all names
System.out.println("1. Printing all names");
employees.stream().forEachOrdered(System.out::println);
```
</li>
<li><b><format color="CornFlowerBlue">Example using method references (various types) and 
functional interfaces</format></b>: 

```Java
import java.util.function.BiFunction;

public class MethodReferencesExamples {
    
    public static <T> T mergeThings(T a, T b, 
                BiFunction<T, T, T> merger) {
        return merger.apply(a, b);
    }
    
    public static String appendStrings(String a, String b) {
        return a + b;
    }
    
    public String appendStrings2(String a, String b) {
        return a + b;
    }

    public static void main(String[] args) {
        
        MethodReferencesExamples myApp = 
            new MethodReferencesExamples();

        // Calling the method mergeThings 
        // with a lambda expression
        System.out.println(MethodReferencesExamples.
            mergeThings("Hello ", "World!", (a, b) -> a + b));
        
        // Reference to a static method
        System.out.println(MethodReferencesExamples.
            mergeThings("Hello ", "World!", 
                MethodReferencesExamples::appendStrings));

        // Reference to an instance 
        // method of a particular object        
        System.out.println(MethodReferencesExamples.
            mergeThings("Hello ", "World!", 
                    myApp::appendStrings2));
        
        // Reference to an instance method 
        // of an arbitrary object of a
        // particular type
        System.out.println(MethodReferencesExamples.
            mergeThings("Hello ", "World!", String::concat));
    }
}
```
</li> 
</list>
</procedure>
<p>Lastly, we will have a bit of a look at callback's pros, cons, and key points to remember in 
relation to Java</p>
<procedure title="Callbacks ― Key Notes, Pros, and Cons" collapsible="true">
<tabs>
<tab title="Callbacks | Key Points">
    <list>
    <li><b><format color="CornFlowerBlue">Definition and Purpose</format></b>: A callback is a function passed as an argument to another function, allowing <code>code to be executed after a task is completed</code></li> 
    <li><b><format color="CornFlowerBlue">Implementation Types</format></b>: Can be implemented through interfaces, anonymous classes, or lambda expressions in modern Java</li>
    <li><b><format color="CornFlowerBlue">Execution Flow</format></b>: Follows the "Hollywood Principle" - <code>"Don't call us, we'll call you"</code>, inverting the control flow</li>
    <li><b><format color="CornFlowerBlue">Functional Nature</format></b>: In modern Java, callbacks often utilize functional interfaces and lambda expressions for cleaner implementation</li>
    </list>
</tab>
<tab title="Callbacks | Pros">
<list>
<li><b><format color="CornFlowerBlue">Flexibility</format></b>: <p>Enables <code>dynamic behavior injection</code> without modifying existing code</p></li>
<li><b><format color="CornFlowerBlue">Decoupling</format></b>: Allows separation of concerns between the caller and the callback implementation</li>
<li><b><format color="CornFlowerBlue">Asynchronous Operations</format></b>: Perfect for handling asynchronous tasks and event-driven programming</li>
<li><b><format color="CornFlowerBlue">Code Reusability</format></b>: Promotes reuse through parameterized behavior and standardized interfaces</li>
</list>
</tab>
<tab title="Callbacks | Cons">
<list>
<li><b><format color="CornFlowerBlue">Callback Hell</format></b>: <p>Nested callbacks can lead to <code>complex and hard-to-maintain code</code> structures</p></li>
<li><b><format color="CornFlowerBlue">Error Handling</format></b>: Can make error propagation and handling more complicated</li>
<li><b><format color="CornFlowerBlue">Memory Management</format></b>: Potential for memory leaks if callbacks aren't properly cleaned up</li>
<li><b><format color="CornFlowerBlue">Control Flow</format></b>: <p>Can make program flow harder 
to follow and debug due to <code>inversion of control</code></p> </li>
</list>
</tab>
</tabs>
</procedure>

## Comparator Interface | A closer look
<p>The Comparator interface is one of the basic interfaces that define a class's ability to 
order its elements. We all know about the <code>Comparable&lt;T&gt; functional interface</code> 
defines the <i><b><code>natural order</code></b> for a given class</i>, however this only 
applies in relation to a single class and its internal state (as seen by its functional method 
<code>c.compareTo(Other)</code>). On the other hand, comparator defines a 
<i><b><code>total ordering</code></b></i> of a class's objects, meaning that it is more 
important than the natural ordering as it can be used to effectively control the ordering of 
elments <code>with higher precision</code>, in collections, etc.
<br/><br/>
Additionally, and something of much importance when we work with streams or classes 
whose implementation is unknown to us, <i>the <code>Comparator interface</code> defines a set of 
methods for us to create our own comparator instances based on <code>extracted methodology, or 
parameters</code></i> Therefore, a comparator is a tool of great importance and value for us in 
both the <code>Java Collections API, or the Java Streams API</code>.
<br/><br/>
It is worth, at this point, defining a set of differences of both sorting interfaces for us to 
get a better grasp of both and their use cases.
</p>

### Comparator Interface | Comparator Vs Comparable
<procedure title="Comparable vs Comparator ― Interface Comparison" collapsible="true"> 
<table>
<tr>
<td><b>Aspect</b></td>
<td><b>Comparable&lt;T&gt;</b></td>
<td><b>Comparator&lt;T&gt;</b></td>
</tr>
<tr>
<td><format color="CornFlowerBlue">Package Location</format></td>
<td>java.lang package</td>
<td>java.util package</td>
</tr>
<tr>
<td><format color="CornFlowerBlue">Method Signature</format></td>
<td><code>compareTo(T obj)</code></td>
<td><code>compare(T obj1, T obj2)</code></td>
</tr>
<tr>
<td><format color="CornFlowerBlue">Implementation Location</format></td>
<td>Must be implemented in the class being compared</td>
<td>Implemented in a separate class or lambda expression</td>
</tr>
<tr>
<td><format color="CornFlowerBlue">Sorting Flexibility</format></td>
<td>Single sorting sequence (natural ordering)</td>
<td>Multiple sorting sequences possible</td>
</tr>
<tr>
<td><format color="CornFlowerBlue">Class Modification</format></td>
<td>Requires modification of source class</td>
<td>No modification of source class needed</td>
</tr>
<tr>
<td><format color="CornFlowerBlue">Usage Context</format></td>
<td>When class has a natural ordering</td>
<td>When multiple comparison criteria are needed</td>
</tr>
<tr>
<td><format color="CornFlowerBlue">Collections Method</format></td>
<td><code>Collections.sort(List&lt;T&gt;)</code></td>
<td><code>Collections.sort(List&lt;T&gt;, Comparator&lt;T&gt;)</code></td>
</tr>
<tr>
<td><format color="CornFlowerBlue">Third-Party Classes</format></td>
<td>Cannot be used if source code access is unavailable</td>
<td>Can be used with any class, including third-party</td>
</tr>
</table>
</procedure>
<p>It is clear then that the main difference between these two classes is the use case, 
Comparable is a requirement for classes but not everyone implements it. On the other hand, 
Comparator can be implemente even if the class does not have a natural order established. 
Moreover, <code>Comparator provides more flexibility and augments the sorting 
capabilities of a class</code>, by allowing us (as we will see) build complex comparators, nest 
them, and even create them on the fly based on a class's parameters.</p>

### Comparator Interface | Main methods. Definition, Use cases, and Implementation

#### Main Methods | Static Comparator Methods Building Blocks

<p>Within our Comparator class there are a series of methods that are meant to help us create, 
mostly on the fly, <code>Comparator instances based on some parameter of our class</code>. This 
then creates an amazing environment in which we can create our own comparators, concatenate them 
(with methods to be discussed later), and even secure them for null values (methods to be 
discussed in this section).
<br/><br/>
The way this section will work is that we will present a set of static methods held within the 
Comparator Interface through examples, text and even some use cases in which they might be 
useful. The ordering is up to the Javadoc documentation, and the presentation will be handled 
through independent procedure blocks
</p>
<list>
<li>
  <b><format color="CornFlowerBlue">static &lt; T, U extends Comparable &lt; ? super U &gt; &gt;
  Comparator&lt;T&gt; comparing(Function &lt;? super T,? extends U&gt; keyExtractor)</format></b>
  <p>
    This method is the basic entry point through which we can create our own 
    <code>Comparator</code> instances. Its parameters indicate that 
    <code>it takes a function that extracts a sort key from our type T</code>, and using that 
    extracted key (<code>of type U</code>), it creates a <code>Comparator</code> for our class.
  </p>
  <p>The following is a set of definitions related to it:</p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          It takes an object of a class and, through a defined 
          <code>function method (functional interface)</code>, extracts a key from it, using the 
          key to order the object with respect to others.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          It takes objects of type parameter <code>T</code> and extracts a key of type 
          <code>U extends Comparable</code>. This allows it to create the comparator internally 
          using the <code>compareTo</code> method provided by <code>U</code>.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is serializable if and only if the 
          <code>specified function</code> is also serializable.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>
              When working on a stream of data and not certain if it implements a 
              <code>Comparable</code>, you can use a simple 
              <code>stream.sorted(Comparator)</code>, where the comparator is created from this 
              method.
            </p>
          </li>
          <li>
            <p>
              When working with objects whose components already implement 
              <code>Comparable</code>, we can use this to create a comparator for sorting 
              collections based on those components.
            </p>
          </li>
          <li>
            <p>
              When there’s no need to apply additional comparison logic after extracting a 
              parameter (as opposed to other overloaded versions of this method, which allow 
              further customization).
            </p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
  <p>The following are some examples of how to implement this method:</p>
  <procedure title="Examples" collapsible="true">

    ```Java
    package Example

    public class Example { 
        public static void main(String[] args) {
            // Example 1: Sorting an array of Strings
            String[] names = {"Alice", "Bob", "Charlie", "Diana"};
            //! Using method reference for function
            Arrays.sort(names, Comparator.comparing(String::length));
            System.out.println("Sorted names by length : " 
                                        + Arrays.toString(names));
        
            // Example 2: Sorting a Map (TreeMap) by its values
            Map<String, Double> priceMap = new HashMap<>();
            priceMap.put("ItemA", 45.99);
            priceMap.put("ItemB", 15.50);
            priceMap.put("ItemC", 85.20);
            priceMap.put("ItemD", 15.50);
        
            Map<String, Double> sortedByValueMap = new TreeMap<>(
            Comparator.comparing((Map.Entry<String, Double> entry) 
                                                -> entry.getValue()));
            sortedByValueMap.putAll(priceMap);
            System.out.println("Sorted Map by value: "
                                    + sortedByValueMap);
        
            // Example 3: Sorting an ArrayList of Vehicle objects
            class Vehicle {
                String name;
                int speed;
        
                public Vehicle(String name, int speed) {
                    this.name = name;
                    this.speed = speed;
                }
        
                public String getName() {
                    return name;
                }
        
                public int getSpeed() {
                    return speed;
                }
        
                @Override
                public String toString() {
                    return "Vehicle{name='" 
                    + name + "', speed=" + speed + '}';
                }
            }
        
            List<Vehicle> vehicles = new ArrayList<>();
            vehicles.add(new Vehicle("Car", 120));
            vehicles.add(new Vehicle("Bike", 80));
            vehicles.add(new Vehicle("Truck", 80));
            vehicles.add(new Vehicle("SUV", 100));
        
            vehicles.sort(
                Comparator.comparing(Vehicle::getSpeed)
            );
            System.out.println("Sorted vehicles by speed: " 
                                                        + vehicles);
        }
    }
    ```
  </procedure>
</li>
<li>
  <b><format color="CornFlowerBlue">static &lt; T, U &gt; Comparator&lt;T&gt; 
  comparing(Function &lt;? super T,? extends U&gt; keyExtractor, Comparator&lt;? super U&gt; keyComparator)</format></b>
  <p>
    Similar to the style of the above code, this method relies on a function to extract the value 
    of a class parameter, using either a lambda, anonymous class, or method reference to 
    effectively grab this value and use it to create a comparator instance based on it.
  </p>
  <p>
    In contrast to the method above, we are also passing a 
    <code>method reference, lambda, or anonymous class</code> that implements a comparator for 
    the data type that we are extracting. This allows us to control both the 
    <em>key extraction</em> and <em>key comparison</em> processes directly, enabling greater 
    flexibility and refinement.
  </p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          Takes two parameters: a <code>keyExtractor function</code> that pulls a value from the 
          object, and a <code>keyComparator</code> that defines how these extracted values should 
          be compared.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          Accepts objects of type parameter <code>T</code> and extracts a key of type <code>U</code>. 
          Unlike the single-parameter version, <code>U</code> does not need to implement 
          <code>Comparable</code> since the comparison logic is provided by the 
          <code>keyComparator</code> parameter.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is serializable if and only if both the 
          <code>keyExtractor function</code> and the <code>keyComparator</code> are serializable.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>
              When working with custom objects that have fields requiring specific comparison 
              logic that differs from their natural ordering through 
              <code>custom comparison rules</code>.
            </p>
          </li>
          <li>
            <p>
              When dealing with extracted values that either do not implement 
              <code>Comparable</code> or when you need to override their natural ordering with 
              <code>specialized comparison behavior</code>.
            </p>
          </li>
          <li>
            <p>
              When implementing complex sorting strategies where the comparison logic needs to be 
              <code>parameterized or modified at runtime</code> based on specific requirements.
            </p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
  <p>In addition to these, the following are some simple examples of its implementation:</p>
  <procedure title="Examples" collapsible="true">

    ```Java
    package Example;

    import java.util.*;

    public class Example {
        static class Employee {
            private String name;
            private Double salary;
            private Integer yearsOfService;

            public Employee(String name, Double salary, 
                              Integer yearsOfService) {
                this.name = name;
                this.salary = salary;
                this.yearsOfService = yearsOfService;
            }

            public String getName() { return name; }
            public Double getSalary() { return salary; }
            public Integer 
                getYearsOfService() { return yearsOfService; }

            @Override
            public String toString() {
                return "Employee{name='" + name 
                        + "', salary=" + salary + 
                       ", years=" + yearsOfService + '}';
            }
        }

        public static void main(String[] args) {
            // Example 1: Custom comparison 
            // for Strings using length
            List<String> words = 
                Arrays.asList("elephant", "dog", "cat", "bird");
            // Using method reference with custom comparator
            words.sort(Comparator.comparing(
                String::length,              //Key Extractor
                (len1, len2) -> len2 - len1  // Comparator 
                                             // w. Reverse order
            ));
            System.out
                .println("Words sorted by length (descending): " 
                                                        + words);

            // Example 2: Complex number 
            // wrapper comparison
            List<Double> numbers = 
                Arrays.asList(1.5, -2.3, 0.0, 3.14, -1.0);
            // Using lambda with custom absolute value comparator
            numbers.sort(Comparator.comparing(
                num -> num,
                (a, b) -> Double.compare(Math.abs(a), Math.abs(b))
            ));
            System.out
                .println("Numbers sorted by absolute value: " 
                                                   + numbers);

            // Example 3: Employee sorting with multiple criteria
            List<Employee> employees = Arrays.asList(
                new Employee("John", 50000.0, 5),
                new Employee("Alice", 60000.0, 3),
                new Employee("Bob", 60000.0, 3),
                new Employee("Carol", 55000.0, 4)
            );

            // Using method reference with anonymous comparator
            employees.sort(Comparator.comparing(
                Employee::getSalary,
                new Comparator<Double>() {
                    @Override
                    public int compare(Double s1, Double s2) {
                        return s2.compareTo(s1); // Descending salary
                    }
                }
            ));
            System.out
                .println("Employees sorted by salary (descending): " 
                                                       + employees);

            // Example 4: Complex Employee sorting with multiple fields
            employees.sort(Comparator.comparing(
                Employee::getYearsOfService,
                (y1, y2) -> {
                    return y2.compareTo(y1);
                }
            ));
            System.out
                .println(
                "Employees sorted by years (desc) then name: " + 
                                                      employees);
        }
    }
    ```
  </procedure>
</li> 
<li>
  <b><format color="CornFlowerBlue">static &lt;T&gt; Comparator&lt;T&gt; 
  comparingDouble(ToDoubleFunction&lt;? super T&gt; keyExtractor)</format></b>
  <p>
    This is one of those methods that are not often studied in a normal Java course, but I thought 
    I would explore it on my own as a learning exercise. The basic idea of the 
    <code>comparingDouble</code> method is that it accepts a function 
    <code>that extracts a double sort key from the object of type T</code>, and then it returns a 
    comparator implementation based on the extracted double value.
  </p>
  <p>
    Generally, it works similarly to other extractor-based comparator implementations of this 
    interface. Below are some important aspects and use cases:
  </p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          Takes an object of a class and extracts a primitive double value through a 
          <code>ToDoubleFunction</code>, which is a specialized functional interface for 
          <code>double extraction without boxing</code>.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          Takes objects of type parameter <code>T</code> and directly extracts a 
          <code>primitive double</code>, avoiding the overhead of boxing/unboxing operations 
          that would occur with <code>Double</code> wrapper objects. No additional comparator 
          is needed as primitive doubles have a natural ordering.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is serializable if and only if the 
          <code>ToDoubleFunction</code> instance used for extraction is serializable.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>
              When working with numerical data where <code>performance is critical</code>, 
              as it avoids the overhead of boxing primitive doubles.
            </p>
          </li>
          <li>
            <p>
              When implementing sorting logic based on floating-point calculations or metrics 
              where <code>primitive double values</code> are the natural comparison key.
            </p>
          </li>
          <li>
            <p>
              When dealing with statistical, scientific, or financial data where 
              <code>precise numerical comparison</code> is required without the overhead of 
              wrapper classes.
            </p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
  <p>Lastly, for this section, we shall study some examples:</p>
  <procedure title="Examples" collapsible="true">

    ```Java
    package Example;

    import java.util.*;
    import java.util.function.ToDoubleFunction;

    public class ComparingDoubleExample {

        // Taxes class with a field for tax amount
        static class Taxes {
            private String name;
            private double amount;

            public Taxes(String name, double amount) {
                this.name = name;
                this.amount = amount;
            }

            public String getName() {
                return name;
            }

            public double getAmount() {
                return amount;
            }

            @Override
            public String toString() {
                return "Taxes{name='" + name + 
                    "', amount=" + amount + '}';
            }
        }

        public static void main(String[] args) {
            // Example 1: Using comparingDouble with a list of doubles
            List<Double> doubleValues = 
                Arrays.asList(3.5, 2.2, -1.7, 5.0, -4.6);

            // Sort using a method reference, 
            // directly passing the value as-is
            doubleValues.sort(Comparator
                    .comparingDouble(Double::doubleValue));
            System.out.println("Doubles sorted by their values: " 
                                                + doubleValues);

            // Sort using a lambda expression for absolute values
            doubleValues.sort(
                Comparator.comparingDouble(value -> Math.abs(value)));
            System.out
                .println("Doubles sorted by absolute values: " 
                                              + doubleValues);

            // Example 2: Using comparingDouble 
            // with a class (Taxes)
            List<Taxes> taxes = Arrays.asList(
                new Taxes("Income Tax", 5000.50),
                new Taxes("Property Tax", 1500.75),
                new Taxes("Sales Tax", 250.30),
                new Taxes("Luxury Tax", 7000.90)
            );

            // Sort by tax amount using a method reference
            taxes.sort(Comparator
                    .comparingDouble(Taxes::getAmount));
            System.out.
                println("Taxes sorted by amount (ascending): " 
                                                     + taxes);

            // Sort by tax amount in descending order using a lambda
            taxes
            .sort(
                (tax1, tax2) -> 
                    Double.compare(tax2.getAmount(), 
                                   tax1.getAmount()));
            System.out.println(
                "Taxes sorted by amount (descending): " + taxes);

            // Example 3: Using an anonymous 
            // class for the comparator
            taxes.sort(Comparator
                .comparingDouble(new ToDoubleFunction<Taxes>() {
                    @Override
                    public double applyAsDouble(Taxes tax) {
                        return tax.getAmount();
                    }
            }));
            System
            .out
            .println(
                "Taxes sorted by amount (ascending)" 
                + "using an anonymous class: " + taxes);

        }
    }
    ```
  </procedure>
</li>
<li>
  <b><format color="CornFlowerBlue">static &lt;T&gt; Comparator&lt;T&gt; 
  comparingLong(ToLongFunction&lt;? super T&gt; keyExtractor)</format></b>
  <p>
    This is another method in the family of simple extractor-based comparator implementations. 
    The idea of this method is that <code>the extracted key should be in some way convertible to 
    a primitive long</code>.
  </p>
  <p>
    Once it is a <code>long</code>, it will be internally used to effectively order the elements 
    based on the long's natural order. Since this method is similar to the previous 
    `comparingInt` version, there is minimal functional difference, aside from working with 
    longs instead of ints. Below are the keynotes for this method:
  </p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          Takes an object of a class and extracts a primitive long value through a 
          <code>ToLongFunction</code>, which is a specialized functional interface for 
          <code>long extraction without boxing</code>.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          Takes objects of type parameter <code>T</code> and directly extracts a 
          <code>primitive long</code>, avoiding the overhead of boxing/unboxing operations that 
          would occur with `Long` wrapper objects. No additional comparator is necessary, 
          as primitive longs already have a natural ordering.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is serializable if and only if the 
          <code>ToLongFunction</code> instance used for extraction is serializable.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>
              When working with large integer values or timestamps where 
              <code>performance is critical</code>, as it avoids the overhead of boxing 
              primitive longs.
            </p>
          </li>
          <li>
            <p>
              When implementing sorting logic based on identifiers or sequence numbers where 
              <code>primitive long values</code> are the natural comparison key.
            </p>
          </li>
          <li>
            <p>
              When dealing with time-ordered operations or large numerical calculations where 
              <code>precise integer comparison</code> is required without the overhead of 
              wrapper classes.
            </p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
  <p>Lastly, let’s take a look at some examples for this method:</p>
  <procedure title="Examples" collapsible="true">

    ```Java
    package Example;

    import java.util.*;
    import java.util.function.ToLongFunction;

    public class ComparingLongExample {

        // TimeRecord class with a timestamp field
        static class TimeRecord {
            private String eventName;
            private long timestamp;

            public TimeRecord(String eventName, long timestamp) {
                this.eventName = eventName;
                this.timestamp = timestamp;
            }

            public String getEventName() {
                return eventName;
            }

            public long getTimestamp() {
                return timestamp;
            }

            @Override
            public String toString() {
                return "TimeRecord{eventName='" + eventName + 
                       "', timestamp=" + timestamp + '}';
            }
        }

        public static void main(String[] args) {
            // Example 1: Using comparingLong with a list of Longs
            List<Long> longValues = 
                Arrays.asList(1000L, 500L, -200L, 3000L, -1500L);

            // Sort using a method reference, 
            // directly passing the value
            longValues.sort(Comparator
                    .comparingLong(Long::longValue));
            System.out.println("Longs sorted by their values: " 
                                                + longValues);

            // Sort using a lambda 
            // expression for absolute values
            longValues.sort(
                Comparator.comparingLong(value -> Math.abs(value)));
            System.out.println("Longs sorted by absolute values: " 
                                                + longValues);

            // Example 2: Using comparingLong 
            // with TimeRecord class
            List<TimeRecord> records = Arrays.asList(
                new TimeRecord("Server Start", 1645729200000L),
                new TimeRecord("Database Backup", 1645725600000L),
                new TimeRecord("User Login", 1645726500000L),
                new TimeRecord("System Update", 1645728300000L)
            );

            // Sort by timestamp using method reference
            records.sort(Comparator
                    .comparingLong(TimeRecord::getTimestamp));
            System.out
                .println("Records sorted by timestamp (ascending): " 
                                                         + records);

            // Sort by timestamp in descending order using lambda
            records.sort(
                (rec1, rec2) -> 
                    Long.compare(rec2.getTimestamp(), 
                               rec1.getTimestamp()));
            System.out.println(
                "Records sorted by timestamp (descending): " 
                                                 + records);

            // Example 3: Using anonymous class for the comparator
            records.sort(Comparator
                .comparingLong(new ToLongFunction<TimeRecord>() {
                    @Override
                    public long applyAsLong(TimeRecord record) {
                        return record.getTimestamp();
                    }
            }));
            System.out.println(
                "Records sorted by timestamp (ascending) " +
                "using anonymous class: " + records);

            // Example 4: Complex sorting with time differences
            records.sort(Comparator
                .comparingLong(record -> 
                    Math.abs(record.getTimestamp() - 
                            System.currentTimeMillis())));
            System.out.println(
                "Records sorted by time difference from now: " 
                                                    + records);
        }
    }

    ```
  </procedure>
</li>
<li>
  <b><format color="CornFlowerBlue">static &lt;T&gt; Comparator&lt;T&gt; 
  comparingInt(ToIntFunction&lt;? super T&gt; keyExtractor)</format></b>
  <p>
    Ternary specialization of our comparing methods where the input value is an object of type 
    <code>T</code>, and the output is a <code>non-wrapped int</code>, which will be used for 
    ordering purposes underneath.
  </p>
  <p>
    The idea of this method is to order a set of objects based on an integer value. This value 
    can represent an object parameter, a derived representation, or any other integer-generated 
    metric. This method relies on the natural ordering of these integers for efficient sorting. However, 
    we can customize the ordering by altering the way the integer value is extracted from the object.
  </p>
  <p>The following are some pointers for this method:</p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          Takes an object of a class and extracts a primitive <code>int</code> value through a 
          <code>ToIntFunction</code>, which is a specialized functional interface for <code>int extraction 
          without boxing</code>.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          Takes objects of type parameter <code>T</code> and directly extracts a 
          <code>primitive int</code>, avoiding the overhead of boxing/unboxing operations. No additional 
          comparator is necessary, as primitive <code>int</code>  have a natural ordering.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is serializable if and only if the provided 
          <code>ToIntFunction</code> used for extraction is serializable.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>
              When working with standard integer values where <code>performance is critical</code>, 
              as it avoids the overhead of boxing primitive ints.
            </p>
          </li>
          <li>
            <p>
              When implementing sorting logic based on counts or indices where 
              <code>primitive int values</code> are the natural comparison key.
            </p>
          </li>
          <li>
            <p>
              When dealing with array indices or collection sizes that require 
              <code>precise integer comparison</code> without the overhead of wrapper classes.
            </p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
  <p>Examples of this method would be:</p>
  <procedure title="Examples" collapsible="true">
    
    ```Java
    package Example;

    public class Example {
        public static void main(String[] args) {
            // Example 1: Sorting a list of integers
            List<Integer> intValues = Arrays.asList(10, 5, -3, 20, -15);

            // Sort integers in ascending 
            // order using method reference
            intValues.sort(Comparator.comparingInt(Integer::intValue));
            System.out.println("Integers sorted by their values: " + intValues);

            // Sort integers by absolute values 
            // using a lambda expression
            intValues.sort(Comparator.comparingInt(value -> Math.abs(value)));
            System.out.println("Integers sorted by absolute values: " + intValues);

            // Example 2: Sorting a list of Employee objects
            class Employee {
                private String name;
                private int age;

                public Employee(String name, int age) {
                    this.name = name;
                    this.age = age;
                }

                public String getName() {
                    return name;
                }

                public int getAge() {
                    return age;
                }

                @Override
                public String toString() {
                    return "Employee{name='" + name + "', age=" + age + '}';
                }
            }

            List<Employee> employees = Arrays.asList(
                new Employee("Alice", 30),
                new Employee("Bob", 25),
                new Employee("Charlie", 35),
                new Employee("Diana", 28)
            );

            // Sort employees by age using method reference
            employees.sort(Comparator.comparingInt(Employee::getAge));
            System.out.println("Employees sorted by age (ascending): " + employees);

            // Sort employees by age in descending order using lambda expression
            employees.sort((emp1, emp2) -> Integer.compare(emp2.getAge(), emp1.getAge()));
            System.out.println("Employees sorted by age (descending): " + employees);

            // Sort employees using an anonymous class for the comparator
            employees.sort(new Comparator<Employee>() {
                @Override
                public int compare(Employee emp1, Employee emp2) {
                    return Integer.compare(emp1.getAge(), emp2.getAge());
                }
            });
            System.out.println("Employees sorted by age (ascending) using anonymous class: " + employees);

            // Sort employees by name length using an additional comparator
            employees.sort(Comparator.comparingInt(emp -> emp.getName().length()));
            System.out.println("Employees sorted by name length: " + employees);
        }
    }
    ```
  </procedure>
</li>
<li>
  <b><format color="CornFlowerBlue">static &lt;T&gt; Comparator&lt;T&gt; 
  nullsFirst(Comparator&lt;? super T&gt; comparator)</format></b>
  <p>
    This method is an extension case over a normal comparator that makes it 
    <code>null-friendly</code>. By this, we mean the comparator will not throw an exception 
    when encountering a <code>null</code> value. Instead, it will consider that 
    <b><code>everything that is null is less than non-null</code></b>, placing all null values 
    at the front of the sorted stream, collection, etc.
  </p>
  <p>
    On the other hand, when both values are non-null, the normal comparator instance is 
    used. This means the bulk of the comparison still relies on the original comparator, 
    but it now has this additional functionality to handle <code>null</code> values.
  </p>
  <p>Below are some of the keynotes for this method:</p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          Takes a Comparator instance as input and returns a new comparator that 
          <code>handles null values by considering them less than non-null values</code>, 
          placing them at the beginning of any sorted structure.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          Takes objects of type parameter <code>T</code> and creates a null-safe comparator 
          that can handle both <code>null and non-null values</code>. When both values 
          are non-null, it delegates to the provided comparator for comparison.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is serializable if and only if the 
          <code>wrapped comparator provided as an argument is serializable</code>.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>
              When working with collections that may contain <code>null values</code> and 
              need to be sorted safely without throwing <code>NullPointerException</code>.
            </p>
          </li>
          <li>
            <p>
              When implementing sorting logic where <code>null values should be treated as 
              smaller</code> than any non-null value in the collection.
            </p>
          </li>
          <li>
            <p>
              When dealing with database results or external data sources where 
              <code>null values need to be handled gracefully</code> in sorting operations.
            </p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
  <p>Additionally, some simple examples of their implementation are presented here:</p>
  <procedure title="Examples" collapsible="true">
    
    ```Java
    package Example;
    import java.util.*;
    
    public class NullsFirstExample {
    static class Product {
    private String name;
    private Integer stock;
    
            public Product(String name, Integer stock) {
                this.name = name;
                this.stock = stock;
            }
    
            public String getName() { return name; }
            public Integer getStock() { return stock; }
    
            @Override
            public String toString() {
                return "Product{name='" + name + "', stock=" + stock + '}';
            }
        }
    
        public static void main(String[] args) {
            // Example 1: Basic list with nulls using method reference
            List<String> items = Arrays.asList("Apple", null, "Banana", null, "Cherry");
            items.sort(Comparator.nullsFirst(String::compareTo));
            System.out.println("Sorted with nulls first (method reference): " + items);
    
            // Example 2: Using lambda with nullsFirst
            List<Product> products = Arrays.asList(
                new Product("Laptop", null),
                new Product("Phone", 50),
                new Product("Tablet", null),
                new Product("Desktop", 25)
            );
            
            products.sort(Comparator.nullsFirst(
                (p1, p2) -> p1.getStock().compareTo(p2.getStock())
            ));
            System.out.println("Products sorted by stock (nulls first): " + products);
    
            // Example 3: Using anonymous class
            products.sort(Comparator.nullsFirst(new Comparator<Integer>() {
                @Override
                public int compare(Integer s1, Integer s2) {
                    return s1.compareTo(s2);
                }
            }.comparing(Product::getStock)));
            System.out.println("Products sorted (anonymous class): " + products);
        }
    }
    ```
  </procedure>
</li>
<li>
  <b><format color="CornFlowerBlue">static &lt;T&gt; Comparator&lt;T&gt; 
  nullsLast(Comparator&lt;? super T&gt; comparator)</format></b>
  <p>
    This is the last of the complicated methods from this section. It is specifically the reverse 
    of the previous <code>nullsFirst()</code> method, as in it takes the same parameters and 
    effectively does the same, only that it positions any null reference to the front of the 
    sorted collection, stream, etc.
  </p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          Takes a Comparator instance as input and returns a new comparator that 
          <code>handles null values by considering them greater than non-null values</code>, 
          placing them at the end of any sorted structure.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          Takes objects of type parameter <code>T</code> and creates a null-safe comparator that 
          can handle both <code>null and non-null values</code>. When both values are non-null, it 
          delegates to the provided comparator for comparison.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is serializable if and only if the <code>wrapped comparator 
          provided as argument is serializable</code>.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>
              When working with collections that may contain <code>null values</code> and need to be 
              sorted safely with nulls at the end.
            </p>
          </li>
          <li>
            <p>
              When implementing sorting logic where <code>null values should be treated as larger</code> 
              than any non-null value in the collection.
            </p>
          </li>
          <li>
            <p>
              When dealing with data presentation scenarios where 
              <code>null values should appear last</code> in sorted displays or reports.
            </p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
  <p>
    Sadly for this section examples will be omitted as they are the same as the nullsFirst method.
  </p>
</li>
<li>
  <b><format color="CornFlowerBlue">static &lt; extends Comparable&lt; ? super T &gt;&gt; 
  Comparator&lt;T&gt; naturalOrder()</format></b>
  <p>
    The implementation of this method is quite simple. The idea is to 
    <code>grab a class of type T which already implements a Comparable</code>, and use said internal 
    implementation to produce a <code>Comparator</code> instance that aligns with its requirements.
  </p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          Takes no direct input but requires that the type parameter <code>T extends Comparable<? super T></code>, 
          ensuring that the elements to be compared have a <code>natural ordering defined through the Comparable 
          interface</code>.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          Takes objects of type parameter <code>T</code> that must implement the Comparable interface, 
          utilizing their <code>natural ordering through the compareTo() method</code>. This method 
          returns a comparator that imposes the natural ordering.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is <code>always serializable</code> as it uses the natural ordering defined 
          by the Comparable interface implementation of the type T.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>When working with standard Java classes that already <code>implement Comparable</code>, 
            like String, Integer, or Date.</p>
          </li>
          <li>
            <p>When you want to sort elements using their <code>natural ordering without defining custom 
            comparison logic</code>.</p>
          </li>
          <li>
            <p>When implementing sorting operations where the <code>default ordering of elements is 
            sufficient</code> for the business requirements.</p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
</li>
<li>
  <b><format color="CornFlowerBlue">static &lt;T extends Comparable&lt;? super T&gt;&gt; 
  Comparator&lt;T&gt; reverseOrder()</format></b>
  <p>
    This method, similarly to the naturalOrder method, 
    <code>does not take any parameters</code>. Instead, it returns the inverse of the natural order 
    of the given type parameter. To use this method and the reverse order feature, 
    your class must implement at least the <code>natural order (Comparable Interface)</code>.
  </p>
  <procedure title="Keynotes">
    <list>
      <li>
        <b><format color="CornFlowerBlue">Inputs Objects</format></b>:
        <p>
          Takes no direct input but requires that the type parameter 
          <code>T extends Comparable<? super T></code>. It returns a comparator that imposes the 
          <code>reverse of the natural ordering</code> defined by the Comparable interface.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Type Parameter</format></b>:
        <p>
          Takes objects of type parameter <code>T</code> that must implement the Comparable interface. 
          This method creates a comparator that <code>inverts the natural ordering</code> by reversing 
          the result of compareTo method calls. This effectively reverses the sorting order.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Serializable</format></b>:
        <p>
          The returned comparator is <code>always serializable</code> as it uses the reverse of the 
          natural ordering defined by the Comparable interface implementation of type T.
        </p>
      </li>
      <li>
        <b><format color="CornFlowerBlue">Use cases</format></b>:
        <list>
          <li>
            <p>
              When you need to sort collections in <code>descending order</code> using the natural ordering 
              of elements.
            </p>
          </li>
          <li>
            <p>
              When working with standard Java classes where you want to 
              <code>reverse their default sorting</code> behavior.
            </p>
          </li>
          <li>
            <p>
              When implementing sorting operations where the <code>inverse of natural ordering</code> 
              is required without creating custom comparators.
            </p>
          </li>
        </list>
      </li>
    </list>
  </procedure>
</li>
</list>
<p>Finally
</p>

#### hello World 

