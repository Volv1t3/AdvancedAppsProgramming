# Class #2 | Thursday 16th | Streams—A Closer Look

> This file will contain information about Streams and their in depth definitions, methods, methodology, as well as 
> some examples in Java code.


## Stream Overview
<p>Streams are functional programming pipelines of information that <code>allow the programmer to define 
intermediate operations</code>, on the data in the stream. Additionally, we are capable of collecting, or 
collecting-reducing the data that is returned by a Stream into objects or data structures for further processing.
<br/><br/>
Streams offer various methods to both <code>parallelize, alter, generate, and operate </code> on data sources like 
<code>data structures, user defined structures, as well as files, zip files, or even JAR files</code>.
<br/><br/> 
Based on the information provided on this course we will review some of the methods defined in te Stream Interface, 
as intermediate operations. <code>(filtering, processing, and modification operations that are done over a 
mutable Stream</code>. After these operations, a <code>terminal operation</code>, is defined as an operation that 
<code>produces a final output on the stream, either converting it to  collection, or reducing it to a certain 
output</code>
</p>

### Stream < T > Interface | Continuation
<p>As we have seen, the Stream interface defines most of the methods used in Streams, here there 
are <code> intermediate and terminal operations</code>, that are relatively simple to work with. 
It is important to note that most of these are given as <code>static methods rather than 
definite instance methods</code>.
</p>
<img src="https://www.logicbig.com/tutorials/core-java-tutorial/java-util-stream/stream-api-intro
/images/stream-api.png" style="block" border-effect="rounded"/>

#### Stream Pipeline
<p>A stream pipeline is combination of <code>one or more intermediate operations</code>, and 
<code>one terminal operation.</code>. These in turn allow for more concepts and methods to be 
applied like <code>short-circuiting operations, or collector operations</code>
<br/><br/>
</p>
<tldr>
<i>A stream pipeline therefore, is a three-part system consisting of a <code>source, 
intermediate operation(s), and a terminal operation(s)</code>. The source can be a file, a 
generator, or an I/O channel, while the other operations can be anything defined in the 
<code>Stream interface, and Collectors Class respectively</code></i>
</tldr>

#### Intermediate Operations
<tldr><i>Intermediate operations return a new stream. They are lazy, and can be further 
subdivided into stateful and stateless operations. Additionally, some intermediate operations 
are divided into short-circuiting operations. Lastly, some might have side effects or not.</i></tldr>
<p>Intermediate operations are method calls that can be chained and more often than note, 
execute a <code>functional interface to execute some business logic on the stream</code>. 
<br/><br/>
Internally, these intermediate operations, when supplied with a functional interface, store the 
method internally and <code>execute lazily</code>, only when the stream is actually returned or 
<code>collected (terminal operations)</code>, such that it outputs a value.
<br/><br/>
There are a series of operations defined in the <code>Stream interface</code> as intermediate 
operations. However, for the sake of understanding we will separate each set of methods 
depending on an internal classification. We begin with <code>non-short-circuit stateless 
operations</code>
</p>

##### Intermediate Operations — Basic Operations
<tldr><i>The main idea of these operations is that they, as they are stateless <b>do not 
retain state of previously seen elements when processing a new element</b>, therefore each 
element is reviewed independently.</i></tldr>

<p>There are a series of operations that can be applied to a stream in a way that does not shot 
circuit our stream, nor retains state from other objects during their iteration over the 
stream</p>

A short list of the basic stream operations that do these is presented below. Examples of  
implementation are kept to a minimum to retain simplicity.

<procedure collapsible="true" title="Intermediate Operations | Basic Operations">
<list>
<li><b><format color="CornFlowerBlue"><p>filter(Predicate &lt; ? super T &gt; predicate)</p>
</format></b>: is an intermediate operation focused on filtering information based on a 
predicate definition (a functional interface that returns a boolean true or false depending on 
its internal condition.

```Java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
List<Integer> evenNumbers = numbers.stream()
                            .filter(n -> n % 2 == 0)
                            .collect(Collectors.toList());
System.out.println(evenNumbers); // Output: [2, 4, 6]
```
</li> 
<li><b><format color="CornFlowerBlue"><p>map(Function &lt; ? super T, ? extends R &gt; mapper)</p>
</format></b>: intermediate operation that takes a stream of type <code>T</code>, and transform 
this into a stream of type <code>R</code> depending on a mapper function instance (this is 
another functional interface).

```Java

List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
List<Integer> nameLengths = names.stream()
                            .map(String::length)
                            .collect(Collectors.toList());
System.out.println(nameLengths); // Output: [5, 3, 7]
```
</li> 
<li><b><format color="CornFlowerBlue">mapToDouble(ToDoubleFunction &lt; ? super T &gt; mapper)
</format></b>: transforms elements into a DoubleStream, useful for numeric operations requiring 
decimal precision.

```Java 
List<String> prices = Arrays.asList("10.5", "20.7", "30.2");
DoubleStream doubleStream = prices.stream()
                .mapToDouble(Double::parseDouble);
System.out.println(Arrays.toString(doubleStream.toArray())); 
// Output: [10.5, 20.7, 30.2]
```

</li>
<li><b><format color="CornFlowerBlue">mapToInt(ToIntFunction &lt; ? super T &gt; mapper)
</format></b>: transforms elements into an IntStream, optimized for integer operations.

```Java 
List<String> numbers = Arrays.asList("1", "2", "3");
IntStream intStream = numbers.stream()
         .mapToInt(Integer::parseInt);
System.out.println(Arrays.toString(intStream.toArray()));
// Output: [1, 2, 3]
```

</li>
<li><b><format color="CornFlowerBlue">mapToLong(ToLongFunction &lt; ? super T &gt; mapper)
</format></b>: transforms elements into a LongStream, suitable for large integer values.

```Java 
List<String> bigNumbers = 
          Arrays.asList("1000000", "2000000", "3000000");
LongStream longStream = bigNumbers.stream()
               .mapToLong(Long::parseLong);
System.out.println(Arrays.toString(longStream.toArray())); 
// Output: [1000000, 2000000, 3000000]
```
</li>
<li><b><format color="CornFlowerBlue">flatMap(Function &lt; ? super T, ? extends Stream&lt;? 
extends R&gt; &gt; mapper)</format></b>: transforms each element into a stream and then flattens 
all streams into a single stream.

```Java 
List<List<Integer>> nestedList = 
     Arrays.asList(Arrays.asList(1, 2), Arrays.asList(3, 4));
List<Integer> flatList = nestedList.stream()
                     .flatMap(List::stream)
             .collect(Collectors.toList());
System.out.println(flatList); 
// Output: [1, 2, 3, 4]
```
</li>
<li><b><format color="CornFlowerBlue">flatMapToDouble(Function &lt; ? super T, ? extends 
DoubleStream &gt; mapper)</format></b>: similar to flatMap but specifically for flattening 
streams into a DoubleStream.

```Java
List<double[]> arrayList = 
    Arrays.asList(new double[]{1.0, 2.0}, 
                 new double[]{3.0, 4.0});
DoubleStream flatDoubles = arrayList.stream()
            .flatMapToDouble(Arrays::stream);
System.out.println(Arrays.toString(flatDoubles.toArray())); 
// Output: [1.0, 2.0, 3.0, 4.0]
```
</li>
<li><b><format color="CornFlowerBlue">flatMapToInt(Function &lt; ? super T, ? extends IntStream 
&gt; mapper)</format></b>: similar to flatMap but specifically for flattening streams into an 
IntStream.

```Java
List<int[]> arrayList = 
     Arrays.asList(new int[]{1, 2}, new int[]{3, 4});
IntStream flatInts = arrayList.stream()
          .flatMapToInt(Arrays::stream);
System.out.println(Arrays.toString(flatInts.toArray())); 
// Output: [1, 2, 3, 4]
```
</li>
<li><b><format color="CornFlowerBlue">flatMapToLong(Function &lt; ? super T, ? extends 
LongStream &gt; mapper)</format></b>: similar to flatMap but specifically for flattening streams 
into a LongStream.

```Java
List<long[]> arrayList = 
     Arrays.asList(new long[]{1L, 2L}, new long[]{3L, 4L});
LongStream flatLongs = arrayList.stream()
          .flatMapToLong(Arrays::stream);
System.out.println(Arrays.toString(flatLongs.toArray())); 
// Output: [1, 2, 3, 4]
```
</li>
<li><b><format color="CornFlowerBlue">peek(Consumer &lt; ? super T &gt; action)</format></b>: 
performs an action on each element as it passes through the stream, useful for debugging.

```Java
List<String> names = 
        Arrays.asList("Alice", "Bob", "Charlie");
List<String> result = names.stream()
    .peek(e -> System.out.println("Processing: " + e))
    .collect(Collectors.toList());
// Output: Processing: Alice, 
// Processing: Bob, 
// Processing: Charlie
```
</li>
</list>
<p>Additionally to this family of operations, there is a set of operations called <code>map Multi</code>, which will 
be discussed in the following list elements. However, it is important to gather a basic idea of how these are 
implemented and what they do first
</p>
<note><i>A Java mapMulti method in Streams is a method that <code>takes in a BiConsumer</code>,and applies it in 
such a way that it replaces each element of a Stream with <code>one or more elements</code>
</i></note>
<p>A mapMulti method takes in, as mentioned, a <code>BiConsumer in the form of a Biconsumer&lt;? super T, ? 
 super Consumer&lt;R&gt;&gt;)</code>, where the type <code>R</code> represents the return type of the resulting 
stream after performing the mapping operation.
<br/><br/>
The idea behind it is that the mapper function replaces every value in the original stream by <code>calling 
the consumer argument of the BiConsumer</code>, with them. Therefore, when a pipeline invokes the mapper argument or 
an element, it computes replacement elements for it (zero, one, or more), and then consumes them.
</p>
<list>
<li><b><format color="CornFlowerBlue"><p>default &lt;R&gt; Stream&lt;R&gt; mapMulti(BiConsumer&lt;? super T,? super 
Consumer &lt;R&gt;&gt; mapper)</p></format></b>:The idea of this function is 
to apply a multi-value transformation that effectively <code>grabs an original value through the stream and 
applies one or more variations through methods in the consumer</code>.

This, effectively, means that we will transform each value in a one-to-one or one-to-many version based on the
consumer specification.

An example of this would be.

```Java
List<String> originalStrings =
                Arrays.asList("Hello", "World",
                              "Java", "Programming");
List<String> transformedStrings  =
       originalStrings.stream()
             .mapMulti((String s, 
             java.util.function.Consumer<String> consumer) -> {
                    consumer.accept(s.toUpperCase());
             }).toList();
System.out.println("transformedStrings = " + transformedStrings);
```

<p>Something that is interesting about these methods is the way in which you provide the Consumer instance. The 
transformation done to the data does not take a <code>Function or any other mapper functional interface</code>, 
rather it takes in a consumer in which we can define everything we would lke to work on.
<br/><br/>
In the block of code above, we can see that the lambda method defining our execution technique, effectively 
creates a <code>one-to-one mapping relationship.</code>, the idea of this method is not specifically to do this but 
the idea is to see how it works. in general, the lambda requires you to define two things <code>the parameter going 
in</code>, and the way it is meant to be mapped to another, and the consumer instance which is going to be used. 
Generally, <code>a consumer is a functional interface that basically consumes a value and does not return 
anything</code>. This is the backbone of the mapMulti implementation.
<br/><br/>
The main way this method works is not by effectively receiving an implemented version of the Consumer, rather it 
expects you to do all your logic within the parameter that can be passed into the Consumer. This then means that we 
are capable of executing <code>one-to-one, or one-to-many relationships within this section</code>
This example of code showcases this idea
</p>

```Java
List<String> transformedStringsUpperLOwer =
    originalStrings.stream()
    .mapMulti(new BiConsumer<String, 
               Consumer<String>>() {
            @Override
            public void accept(String s, 
                Consumer<String> objectConsumer) {
                objectConsumer.accept(s.toLowerCase());
                objectConsumer.accept(s.toUpperCase());
                objectConsumer.accept(s.repeat(2));
            }
        }).toList();
System.out.println("transformedStringsUpperLOwer = " 
                    + transformedStringsUpperLOwer);
                    
List<String> input = 
      List.of("apple", "pear", "peach", "banana");
List<String> result = input.stream()
        .<String>mapMulti((element, consumer) -> {
            if (element.length() % 2 == 0) {
                consumer.accept(element + "-1");
                consumer.accept(element + "-2");
            }
        })
        .collect(Collectors.toList());
System.out.println(result);
                   
```

<p>Effectively then, the idea of this method is to use the consumer interface to accept any all 
modifications that can be mapped to the type of the input, using it to receive the values and 
store them.</p>
</li> 
<li><b><format color="CornFlowerBlue"> <p>default DoubleStream mapMultiToDouble(BiConsumer&lt;? 
super T, ? super 
DoubleConsumer&gt; mapper)</p></format></b>: The idea of this method is similar to 
those that we 
have studied before, like we did, <code>converting from a value to a double value</code>, we 
are going to do the same for a mapping of one to many double values. The idea here however is 
that it will take what is known as a <code>DoubleConsumer</code>, which is an specialization 
of the general consumer.

The work methodology of methods such as the DoubleConsumer, or as we will see LongConsumer or 
IntConsumer is to be more concrete implementations of the consumer such that you are forced to 
provide an input value of the type of the interface. Since the methodology doesn't change much 
from the example above in the simple mapMulti example, we shall take a look at some more 
examples here.

```Java
List<Double> numbersTimesTwo = 
    Stream.iterate(1, new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 1;
            }
    })
    .limit(10)
    .mapMultiToDouble(new BiConsumer<Integer, DoubleConsumer>() {
            @Override
            public void accept(Integer integer, 
                              DoubleConsumer doubleConsumer)
            {
                doubleConsumer.accept(integer.doubleValue() 
                                    * integer.doubleValue());
            }
        }).boxed().toList();
        System.out.println("numbersTimesTwo = " 
        + numbersTimesTwo);
 // numbersTimesTwo = [1.0, 4.0, 9.0, 16.0, 25.0, 36.0, 
 // 49.0, 64.0, 81.0, 100.0]
 
 //! Second example with an inner class
 //! Creating a series of employees
List<Employee> employees = List.of(
                new Employee("John", 25),
                new Employee("Jane", 30),
                new Employee("Jack", 35),
                new Employee("Jill", 40),
                new Employee("Joe", 45)
);
OptionalDouble duplicateValueAndAverage =
     employees
     .stream()
     .mapMultiToDouble(new BiConsumer<Employee, DoubleConsumer>() {
          @Override
          public void accept(Employee employee, 
                DoubleConsumer doubleConsumer) {
               doubleConsumer.accept(employee.age); //! First copy
               doubleConsumer.accept(employee.age); //! Second copy
          }
     }).average();
 duplicateValueAndAverage.ifPresent(System.out::println);
 
private static class Employee{
        String name;
        int age;
        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }
}
 
```

</li> 
<li><b><format color="CornFlowerBlue"><p>default IntStream mapMultiToInt(BiConsumer&lt;? super T, ? 
super IntConsumer&gt; mapper)</p></format></b>: <p>the way this method works is 
similarly 
to the 
double map multi implementation, in which it takes an input stream with values of some type, and 
through an IntConsumer will transform them into a series of integer values (most likely 
primitive ints) with which the program will form an IntStream instance for us to use.<br/><br/>
The behavior of this consumer is as before, it will take whatever we till it to consume, and
then create a stream based on it without us ever seeing what happened (underneath it uses a
Spined Buffer and AFAIK, it was taking the values from the consumer
either way). The values that it will take from us surely have to be of type `int` for them to
work with the consumer. Below are some examples of how this method works </p>


```Java
Optional<Integer> sumOfEvenIntegersUpTo100Doubled = 
        Stream.iterate(1, new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer <= 100;
            }
        }, new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 1;
            }
        }).mapMultiToInt(new BiConsumer<Integer, IntConsumer>() {
            @Override
            public void accept(Integer integer, 
                        IntConsumer intConsumer) {
                for(Integer integer1 : List.of(integer, integer)){
                    System.out.println("integer1 = " + integer1);
                    intConsumer.accept(integer1);
                }
            }
        }).boxed().reduce(Integer::sum);
System.out.println("sumOfDuplicatedAges = " 
        + sumOfEvenIntegersUpTo100Doubled);
```
</li> 
<li><b><format color="CornFlowerBlue"><p>default LongStream mapMultiToLong(BiConsumer ? super T, 
? super IntConsumer mapper)</p>
</format></b>: 
<p>
this method works just like the other two above, it is the same behavior, the 
same parameters, and the same specialization to the `long` data type. We have seen that the are 
flatMaps to each, and now mapMulti to each, as well as simple Maps to each of these data types 
too, therefore it is not impossible to see that this method would return a LongStream made up 
`long` values, that can then be operated upon.<br/><br/>
The following example will show how to do this
</p>

```Java

OptionalLong sumOfDuplicatedLongsUpTo50 = 
    Stream.iterate(1L, new Predicate<Long>() {
        @Override
        public boolean test(Long input) {
            return input <= 50;
        }
    }, new UnaryOperator<Long>() {
        @Override
        public Long apply(Long input) {
            return input + 1;
        }
    }).mapMultiToLong(new BiConsumer<Long, LongConsumer>() {
        @Override
        public void accept(Long value, LongConsumer longConsumer) {
            // For each original value, 
            // generate two copies into the LongStream
            longConsumer.accept(value);
            longConsumer.accept(value);
        }
    }).reduce(Long::sum);

System.out.println("Sum of duplicated long values = " 
             + sumOfDuplicatedLongsUpTo50.orElse(0L));
```
</li> 
</list>
</procedure>

##### Intermediate Operations — Stateful Operations
<tldr><i>The amin idea of these operations is that they, as they are stateful, 
<code>their methodology requires them to retain some kind of state from a 
previously seen object to execute their business logic</code></i></tldr>
<p>Stateful intermediate operations often implement some kind of content that requires iteration 
over the entire stream before returning a changed stream. For example, the sorting methods or 
the distinct methods might require taking a look at the entire stream before they execute their 
business logic.
<br/><br/>
As an interesting sidenote <code>in parallel environments</code>, if a stream contains 
operations that are stateful, <code>these might require multiple passes over the data, and 
even data buffering to finish their business logic</code>.
<br/><br/>
Moreover, if we were working on a parallel environment, the mere use of these stateful 
operations, or code that <code>has stateful behavioral (functional) parameters</code>, our code 
will work in unexpected and un-replicable ways, leading to logic errors, and with 
expected unexpected results. <code>The solution so to avoid functional parameters that 
modify external state</code>. 
<br/><br/>
Below, then, we define a series of operations that are categorized as stateful.
</p>
<procedure title="Intermediate Operations | Stateful Operations" collapsible="true">
<list>
<li><b><format color="CornFlowerBlue">sorted()</format></b>: intermediate operation that sorts 
elements according to their natural ordering. Elements must implement Comparable interface. This 
operation is stateful as it needs to process all elements to determine the correct order.

```Java
List<String> words = Arrays.asList("banana", "apple", "cherry");
List<String> sortedWords = words.stream()
            .sorted()
            .collect(Collectors.toList());
System.out.println(sortedWords); 
// Output: [apple, banana, cherry]
```
</li>
<li><b><format color="CornFlowerBlue">sorted(Comparator&lt;? super T&gt; comparator)
</format></b>: intermediate operation that sorts elements using a custom Comparator. This allows 
sorting of elements that do not implement Comparable or sorting in a different order than the 
natural ordering.

```Java
List<String> words = Arrays
     .asList("banana", "apple", "cherry");
List<String> customSorted = words.stream()
         .sorted((a, b) -> b.compareTo(a)) // reverse order
         .collect(Collectors.toList());
System.out.println(customSorted); 
// Output: [cherry, banana, apple]
```
</li>

<li><b><format color="CornFlowerBlue">distinct()</format></b>: intermediate operation that 
returns a stream with unique elements based on Object.equals(). This operation is stateful as it 
must maintain a history of seen elements to eliminate duplicates.

```Java
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 4);
List<Integer> uniqueNumbers = numbers.stream()
.distinct()
.collect(Collectors.toList());
System.out.println(uniqueNumbers); // Output: [1, 2, 3, 4]
```
</li>
</list>
</procedure>

##### Intermediate Operations — Short-circuit Operations
<tldr><i>Short-circuit operations are those that have the ability to 
<code>presented an infinite input, produce a finite stream as a result</code></i></tldr>
<p>The concept of short-circuit operations does not lie only with intermediate operations. Rather 
they are also applicable to terminal operations. <br/><br/>
These operations, when it comes to <code>terminal operations</code>, mean that <code>when 
presented with infinite input, it may terminate in finite time.
</code><br/><br/>
Effectively, these operations are here to help us ground or code when it comes to infinite 
sources of data (like IntStream sources) such that we do not kill the JVM<br/><br/>
Below, we present a listing based on the short-circuiting operations given in the Java lang</p>
<procedure title="Intermediate Operations | Short-circuit Operations" collapsible="true">
<list>
<li><b><format color="CornFlowerBlue">limit(long maxSize)</format></b>: intermediate operation 
that truncates the stream to be no longer than the specified size. This operation is 
short-circuiting as it can stop processing once it reaches the size limit, even with infinite 
streams.

```Java
List<Integer> numbers = 
         Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
List<Integer> limitedNumbers = numbers.stream()
              .limit(3)
              .collect(Collectors.toList());
System.out.println(limitedNumbers); // Output: [1, 2, 3]
```
</li>
<li><b><format color="CornFlowerBlue">skip(long n)</format></b>: intermediate operation that 
discards the first n elements of the stream and returns a stream consisting of the remaining 
elements. This operation is short-circuiting as it can begin processing after skipping n elements.

```Java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
List<Integer> skippedNumbers = numbers.stream()
              .skip(2)
              .collect(Collectors.toList());
System.out.println(skippedNumbers); // Output: [3, 4, 5, 6]
```
</li>
<li><b><format color="CornFlowerBlue">takeWhile(Predicate&lt;? super T&gt; predicate)
</format></b>: intermediate operation that takes elements from the stream while the predicate 
returns true, and stops once the predicate returns false for an element. This operation is 
especially useful for ordered streams.

```Java
List<Integer> numbers = Arrays.asList(2, 4, 6, 7, 8, 10);
List<Integer> taken = numbers.stream()
           .takeWhile(n -> n % 2 == 0)
           .collect(Collectors.toList());
System.out.println(taken); // Output: [2, 4, 6]
```

</li>

<li><b><format color="CornFlowerBlue">dropWhile(Predicate&lt;? super T&gt; predicate)
</format></b>: intermediate operation that drops elements from the stream while the predicate 
returns true, and keeps all remaining elements once the predicate returns false for an element. 
This operation is complementary to takeWhile.

```Java
List<Integer> numbers = Arrays.asList(2, 4, 6, 7, 8, 10);
List<Integer> dropped = numbers.stream()
             .dropWhile(n -> n % 2 == 0)
             .collect(Collectors.toList());
System.out.println(dropped); // Output: [7, 8, 10]
```
</li>
</list>
</procedure>


#### Terminal Operations — Collectors, Reductions
<tldr>
<i>A terminal operation is one that produces an output (a value, Optional or collection) from a 
stream after a series of intermediate operations. A terminal operation might produce a 
<code>side-effect</code>. Lastly, once the stream terminal operation is used the pipeline is 
<code>consumed</code>, and cannot be used again</i>
</tldr>
<p>Stream terminal operations are a set of operations dedicated to producing either 
<code>an output or a side-effect (to be discussed in a moment)</code>, these are known also as 
<code>eager operations</code>, since every time they are invoked they quickly iterate through 
the entire stream and return the value specified by the method called.
<br/><br/>
It is important to note that, <b>with the exception of the spliterator() and iterator() 
escape-hatch operations,</b> all other operators execute every method in a chained stream call 
once the terminal operation is invoked. <br/><br/>
Additionally, when working with streams it is important to note that if we are to modify a 
source that is part of a non-closed stream, this has to be done only before the terminal 
operation such that any changes are reflected.
<br/><br/>
having said all of this, let us take a look at the operations under this section
</p>
<procedure title="Terminal Operations | Regular Terminal Operations" collapsible="true">
<warning><i>Operations such as reduce, collect,are <code>stateful terminal operations</code>. While 
forEach can introduce to some extent stateful behavior in our code</i></warning>
<list>
<li><b><format color="CornFlowerBlue">reduce(BinaryOperator&lt;T&gt; accumulator)</format></b>: 
terminal operation that performs a reduction on the stream elements using an associative 
accumulation function and returns an Optional. The accumulator function must be stateless and 
associative.

```Java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
Optional<Integer> sum = numbers.stream()
                .reduce((a, b) -> a + b);
System.out.println(sum.get()); // Output: 15
```
</li>
<li><b><format color="CornFlowerBlue">reduce(T identity, BinaryOperator&lt;T&gt; accumulator)
</format></b>: terminal operation similar to the previous reduce but with an identity value as 
the starting point for the reduction. Returns the reduced value directly rather than an Optional.

```Java
List<Integer> numbers = 
         Arrays.asList(1, 2, 3, 4, 5);
Integer sum = numbers.stream()
          .reduce(0, (a, b) -> a + b);
System.out.println(sum); // Output: 15
```
</li>
<li><b><format color="CornFlowerBlue">reduce(U identity, BiFunction&lt;U,? super T,U&gt; 
accumulator, BinaryOperator&lt;U&gt; combiner)</format></b>: terminal operation that performs a 
reduction with an identity value and a combining function for parallel processing. Useful when 
the result type differs from the stream element type.

```Java
List<String> strings = Arrays.asList("a", "b", "c");
Integer length = strings.stream()
        .reduce(0, 
                (sum, str) -> sum + str.length(), 
                Integer::sum);
System.out.println(length); // Output: 3
```
</li>

<li><b><format color="CornFlowerBlue">collect(Collector&lt;? super T,A,R&gt; collector)
</format></b>: terminal operation that performs a mutable reduction using a Collector. 
Collectors provide common reduction operations like grouping, joining, or collecting elements 
into collections.

```Java
List<String> words = Arrays.asList("hello", "world");
String result = words.stream()
                 .collect(Collectors.joining(", "));
System.out.println(result); // Output: hello, world
```
</li>

<li><b><format color="CornFlowerBlue">collect(Supplier&lt;R&gt; supplier, BiConsumer&lt;R,? 
super T&gt; accumulator, BiConsumer&lt;R,R&gt; combiner)</format></b>: terminal operation that 
performs a mutable reduction with explicit supplier, accumulator, and combiner functions.

```Java
List<String> words = Arrays.asList("hello", "world");
StringBuilder result = words.stream()
          .collect(StringBuilder::new, 
                   StringBuilder::append, 
                   StringBuilder::append);
System.out.println(result); // Output: helloworld
```
</li>

<li><b><format color="CornFlowerBlue">forEach(Consumer&lt;? super T&gt; action)</format></b>: 
terminal operation that performs an action for each element of the stream. The order is not 
guaranteed for parallel streams. 

```Java
List<String> names = 
                   Arrays.asList("Alice", "Bob", "Charlie");
names.stream()
     .forEach(name -> System.out.println("Hello, " + name));
// Output: Hello, Alice 
// Hello, Bob 
// Hello, Charlie
```
</li>

<li><b><format color="CornFlowerBlue">forEachOrdered(Consumer&lt;? super T&gt; action)
</format></b>: terminal operation similar to forEach but guarantees the encounter order of the 
stream if it has one, even in parallel streams.

```Java
List<String> names = 
             Arrays.asList("Alice", "Bob", "Charlie");
names.parallelStream()
             .forEachOrdered(name 
             -> System.out.println("Hello, " + name));
// Output: Hello, Alice 
// Hello, Bob 
// Hello, Charlie (in order)
```
</li>
<li><b><format color="CornFlowerBlue">toArray()</format></b>: terminal operation that returns an 
array containing the elements of the stream. The returned array type is Object.

```Java
List<String> words = Arrays.asList("hello", "world");
Object[] array = words.stream()
                    .toArray();
System.out.println(Arrays.toString(array)); 
// Output: [hello, world]
```
</li>
<li><b><format color="CornFlowerBlue">toArray(IntFunction&lt;A&gt; generator)</format></b>: 
terminal operation that returns an array containing the elements of the stream with the 
specified array type passed in, in the form of a generator.

```Java
List<String> words = Arrays.asList("hello", "world");
String[] array = words.stream()
        .toArray(String[]::new);
System.out.println(Arrays.toString(array)); 
// Output: [hello, world]
```
</li>

<li><b><format color="CornFlowerBlue">count()</format></b>: terminal operation that returns the 
count of elements in the stream.

```Java
List<String> words = 
        Arrays.asList("hello", "world", "java");
long count = words.stream()
       .count();
System.out.println(count); // Output: 3
```
</li>

<li><b><format color="CornFlowerBlue">max(Comparator&lt;? super T&gt; comparator)</format></b>: 
terminal operation that returns an Optional describing the maximum element according to the 
provided Comparator.

```Java
List<String> words = 
             Arrays.asList("hello", "world", "java");
Optional<String> longest = words.stream()
            .max(Comparator.comparing(String::length));
System.out.println(longest.get()); // Output: hello
```
</li>

<li><b><format color="CornFlowerBlue">min(Comparator&lt;? super T&gt; comparator)</format></b>: 
terminal operation that returns an Optional describing the minimum element according to the 
provided Comparator.

```Java
List<String> words = 
             Arrays.asList("hello", "world", "java");
Optional<String> shortest = words.stream()
            .min(Comparator.comparing(String::length));
System.out.println(shortest.get()); // Output: java
```
</li>
</list>
</procedure>
<procedure title="Terminal Operations | Short-circuiting Terminal Operations" collapsible="true">
<list>
<li><b><format color="CornFlowerBlue">boolean allMatch(Predicate &lt;? super T&gt; predicate)  </format></b>: 
Defined as a <code>terminal short-circuit operation</code>, that works by checking iteratively all 
elements in the stream to determine <code>if all satisfy the predicate (which si defined as a stateless 
non-interfering predicate)</code>.

In this sense, this method will short-circuit and return false if at least one element does not confort to the given 
predicate. 

<note>If the stream is empty, or all elements conform to the predicate, it will return <code>true</code></note>

An example of this would be.

```Java

List<Integer> numbers = 
     Arrays.asList(2, 4, 6, 8, 10);
boolean allEven = numbers.stream()
        .allMatch(n -> n % 2 == 0);
System.out.println(allEven); // Output: true
//! If we were to change the list to add a five, 
//! it would return false
```
</li> 
<li><b><format color="CornFlowerBlue">boolean noneMatch(Predicate &lt;? super T &gt; predicate)</format></b>: This 
is another <code>short-circuit terminal operation</code>, defined as a way to apply a predicate to a stream of 
elements and review if none match the condition.

As with the allMatch, the predicate should be a <code>non-interfering stateless predicate</code>.
<note><i>This function returns true if no elements match the predicate, false otherwise (even if empty)</i></note>

An example of this could be

```Java

List<Integer> numbers = Arrays.asList(1, 3, 5, 7, 9);
boolean noneEven = numbers.stream()
    .noneMatch(n -> n % 2 == 0);
System.out.println(noneEven); // Output: true
```
</li>
<li><b><format color="CornFlowerBlue">boolean anyMatch(Predicate&lt;? super T&gt; predicate)</format></b>:
Another method in the family of our predicate short-circuit terminal operations. This method works by 
<code>iterating over all elements, attempting to find at least one element conforts to the predicate sent in</code>

The idea then is that this method will not evaluate all elements if we know at least one that is true to the predicate.

<note><i>This implementation will return true if at least one element aligns with the predicate, if the stream 
is empty or no one aligns it will return false.</i></note>

An example would be this:

```Java

List<Integer> numbers = Arrays.asList(1, 3, 5, 6, 7);
boolean hasEven = numbers.stream()
    .anyMatch(n -> n % 2 == 0); 
    // short-circuits as soon as '6' is found
System.out.println(hasEven); // Output: true
```
</li>
<li><b><format color="CornFlowerBlue">Optional&lt;T&gt; findAny()</format></b>: This method is another short-circuit 
terminal operator that is specially used to find <code>any and all object in a Stream. It is not bound to 
rules of ordering, comparsison or even review of objects</code>, instead it basically takes a single instance of 
the Stream (from any thread if the stream is parallelized), and returns that.

As far as I see, this method might be useful if we are expecting a Stream of values to contain at least one value, 
and we need to gather that value rather than any specifics. Or when the stream is of equal elements, and we want to 
grab some for testing.
<note><i>This method is <code>non deterministic</code>, meaning on multiple calls it will not return the same value. 
Additionally, it will return an optional to indicate whether there are any values in the Stream
</i></note>

An example implementation of this method would be 

```Java
List<String> names = 
    Arrays
        .asList("Alice", "Bob", 
        "Charlie", "David", "Emma");

// Single-threaded stream example
Optional<String> anyNameInSingleThread = 
      names
      .stream()
      .findAny();
anyNameInSingleThread
    .ifPresent(name -> 
        System.out
        .println(
        "Found in single-threaded stream: " 
        + name));

// Parallel stream example
Optional<String> anyNameInParallelStream = 
          names.parallelStream().findAny();
anyNameInParallelStream.ifPresent(name -> System.out
        .println("Found in parallel stream: " 
                                     + name));
```
</li> 
</list>
</procedure>


##### Optional Values As Return Types — Specifications, and Generics
<p>In the Stream API, some terminal operators, not only on the <code>Stream interface, 
but also in the IntStream, DoubleStream, LongStream</code> interfaces, use the concept of 
Optinal values to prevent Streams from returning <code>null values</code></p>

## Side Effects in Stream Operations — A Closer look
<tldr><i>Side effects, be it on behavior parameters or on stateful operations should be 
<code>avoided at all costs</code>. More often than note, code that manipulates in some 
way external states can be fixed or ammended.
</i></tldr>
<p>One common source for bugs in parallel programming is the use of stateful operations and 
stateful behavior parameters. These two often include some level of side effects which end up 
causing problems with synchronization and output generation. 
<br/><br/>
It is generally agreed upon that <code>only methods that return void</code>, for example, 
<code>forEach(), forEachoOrdered(), and peek()</code>, are meant to produce side effects. ( peek 
should be used only for logging purposes)
<br/><br/>
The most important things to keep in mind are these.
</p>
<procedure title="Side Effects in Stream Operations | Guidelines" collapsible="true">
<list>
<li><b><format color="CornFlowerBlue">Non-Interference</format></b>: One of the most fundamental 
requirements when working with streams is that the data source should not be modified during 
stream operations. This means that while a stream pipeline is executing, the underlying 
collection or data source should remain constant. Modifying the source during stream processing 
can lead to concurrent modification exceptions, unpredictable results, or race conditions. This 
principle applies to both sequential and parallel streams, though the effects of violation are 
more immediately apparent in parallel streams.</li>

<li><b><format color="CornFlowerBlue">Stateless Operations</format></b>: Stream operations 
should not depend on any mutable state from outside the operation, nor should they update any 
state. Each operation should depend only on its input, not on external state that might change 
during execution. This is particularly important because the timing and order of operations are 
not guaranteed, especially in parallel streams. Stateless operations are predictable, 
reproducible, and easily parallelizable.</li>

<li><b><format color="CornFlowerBlue">Thread Safety in Parallel Streams</format></b>: When using 
parallel streams, all operations must be thread-safe. This means that any accumulations, 
reductions, or collections of results must be done in a way that safely handles concurrent 
modifications. This is especially important when dealing with shared resources or collections. 
The Stream API provides thread-safe methods and collectors specifically designed for parallel 
execution, and these should be preferred over manual accumulation.</li>

<li><b><format color="CornFlowerBlue">Acceptable Side Effects</format></b>: While side effects 
should generally be avoided, they are acceptable in certain terminal operations, particularly 
those designed for producing side effects (like forEach). However, even in these cases, the side 
effects should be contained and well-understood. Logging, printing, or adding elements to a 
thread-safe collection are examples of acceptable side effects when properly managed.</li>

<li><b><format color="CornFlowerBlue">Avoid Side Effects in Lambda Expressions</format></b>: 
Lambda expressions used in intermediate operations should be pure functions - they should only 
depend on their input parameters and should not modify any external state. This ensures that the 
stream operations can be safely reordered, parallelized, or optimized by the runtime. Side 
effects in lambda expressions can lead to race conditions and make the code harder to reason 
about and maintain.</li> 

<li><b><format color="CornFlowerBlue">Debugging Considerations</format></b>: When 
debugging stream operations, it's tempting to add side effects for logging or inspection 
purposes. While this is acceptable for debugging, it should be done carefully and preferably 
using the peek() operation. Debug-related side effects should not modify any state that affects 
the stream's execution, and they should be removed or disabled in production code.</li>

<li><b><format color="CornFlowerBlue">Concurrent Collections</format></b>: In situations where 
maintaining state is unavoidable, especially in parallel streams, use thread-safe concurrent 
collections. These collections are specifically designed to handle concurrent modifications 
safely. However, even with concurrent collections, be mindful of the performance implications 
and potential contention points that might negate the benefits of parallelization.</li>

<li><b><format color="CornFlowerBlue">Order-Based Operations</format></b>: Operations that 
depend on the encounter order of elements require special consideration in parallel streams. 
While sequential streams maintain encounter order, parallel streams may process elements in any 
order unless specifically constrained. If order is important, either use appropriate ordering 
operations or consider whether parallel processing is appropriate for your use case.</li>

<li><b><format color="CornFlowerBlue">Resource Management</format></b>: When streams involve 
external resources (such as file handles, network connections, or database cursors), proper 
resource management is crucial. Resources should be properly acquired and released, typically 
using try-with-resources constructs. This is particularly important for infinite streams or 
streams processing large amounts of data, where resource leaks could have significant impacts.</li>

<li><b><format color="CornFlowerBlue">Performance Considerations</format></b>: Side effects 
often introduce synchronization requirements that can significantly impact performance, 
especially in parallel streams. Before introducing any side effects, carefully consider whether 
the same result could be achieved using the Stream API's built-in operations. The cost of 
synchronization and coordination in parallel streams with side effects can often outweigh the 
benefits of parallelization.</li>
</list>
</procedure>
<p>Here are some examples of code that show each of these points in detail</p>
<procedure title="Side Effects in Stream Operations | Code Guidelines" collapsible="true">
<list>
<li><b><format color="CornFlowerBlue">Non-Interference</format></b>: Stream operations should 
not modify the source data structure during execution. 

```Java
// BAD PRACTICE - Modifying source during stream operation
List<Integer> numbers = 
          new ArrayList<>(Arrays.asList(1, 2, 3));
// Don't do this!
numbers.stream().forEach(n -> numbers.add(n + 1)); 

// GOOD PRACTICE - Create new collection for results
List<Integer> numbers = Arrays.asList(1, 2, 3);
List<Integer> result = numbers.stream()
            .map(n -> n + 1)
            .collect(Collectors.toList());
```
</li>

<li><b><format color="CornFlowerBlue">Stateless Operations</format></b>: Avoid operations that 
depend on any state that might change during execution.

```Java 
import java.util.stream.IntStream;
import java.util.List;
import java.util.stream.Collectors;

// BAD PRACTICE - Using external mutable state
int[] counter = {0};
IntStream.range(1, 5)
         .map(e -> e + counter[0]++) // Don't do this!
         .forEach(System.out::println);

// GOOD PRACTICE - Use stateless operations
List<Integer> result = IntStream.range(1, 5)
                           .map(e -> e + 1)
                           .boxed()
                           .collect(Collectors.toList());
System.out.println(result); // Output: [2, 3, 4, 5]
```
</li>

<li><b><format color="CornFlowerBlue">Thread Safety in Parallel Streams</format></b>: When using 
parallel streams, ensure thread-safe accumulation.

```Java
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// BAD PRACTICE - Not thread-safe
ArrayList<Integer> unsafeList = new ArrayList<>();
// Don't do this!
IntStream.range(1, 10).parallel().forEach(unsafeList::add); 
System.out.println("Unsafe List: " + unsafeList);

// GOOD PRACTICE - Use thread-safe collectors
List<Integer> safeList = IntStream.range(1, 10).parallel()
        .boxed()
        .collect(Collectors.toList());
System.out.println("Safe List: " + safeList);
```
</li>

<li><b><format color="CornFlowerBlue">Acceptable Side Effects</format></b>: Side effects are 
acceptable in certain terminal operations like forEach when properly contained.

```Java
import java.util.*;
import java.util.stream.*;

public class StreamExample {
    public static void main(String[] args) {
        // Example: Side effects in 
        // terminal operation
        Stream<String> stream = 
                  Stream.of("one", "two", "three");
        // Output: one two three (each on new line)
        stream.forEach(System.out::println); 

        // Example: Collecting results 
        // in thread-safe manner
        Stream<String> anotherStream = 
                Stream.of("apple", "banana", "cherry");
        Set<String> collected = 
                anotherStream.collect(Collectors.toSet());
        System.out.println(collected); 
        // Output: [apple, banana, cherry]
    }
}
```
</li>

<li><b><format color="CornFlowerBlue">Avoid Side Effects in Lambda Expressions</format></b>: 
Lambda expressions in intermediate operations should be pure functions.

```Java
// BAD PRACTICE - Side effects in map
Map<String, Integer> map = new HashMap<>();
Stream<String> stream = Stream.of("one", "two", "three");
stream.map(k -> map.put(k, 1)); // Don't do this!

// GOOD PRACTICE - Pure function in map
Stream<String> stream2 = Stream.of("one", "two", "three");
stream2.map(String::length);
```
</li>

<li><b><format color="CornFlowerBlue">Debugging Considerations</format></b>: Use peek() for 
debugging, but avoid side effects that modify state.

```Java
import java.util.*;
import java.util.stream.*;

public class StreamDebuggingExample {
    public static void main(String[] args) {
        // Example stream with debugging
        List<String> words = 
            Arrays.asList("apple", "banana", "cherry");
        
        List<Integer> lengths = words.stream()
              .peek(e -> 
                System.out.println("Processing: " + e))
              .map(String::length)
              .collect(Collectors.toList());
        
        System.out.println("Word lengths: " + lengths);
    }
}
```
</li>

<li><b><format color="CornFlowerBlue">Concurrent Collections</format></b>: When side effects are 
necessary, use concurrent collections for parallel streams.

```Java
// GOOD PRACTICE - Using concurrent collection
Stream<String> stream =
           Stream.of("apple", "banana", 
           "cherry", "apple", "banana");
        ConcurrentHashMap<String, Integer> concurrent = 
        stream.parallel()
                .collect(Collectors.toConcurrentMap(
                        (Function<String, String>) s -> s,
                        (Function<String,Integer>) i -> 1,
                        Integer::sum,
                        ConcurrentHashMap::new
                ));
        System.out.println(concurrent);
        // OUTPUT: {banana=2, cherry=1, apple=2}
```
</li>

<li><b><format color="CornFlowerBlue">Order-Based Operations</format></b>: Be cautious with 
operations that depend on encounter order in parallel streams.

```Java
// BAD PRACTICE - Order-dependent 
// operation in parallel stream
stream.parallel()
.forEach(System.out::println); // Order not guaranteed

// GOOD PRACTICE - Use forEachOrdered when order matters
stream.parallel()
.forEachOrdered(System.out::println);
```
</li>

<li><b><format color="CornFlowerBlue">Resource Management</format></b>: Properly manage 
resources when streams involve I/O operations.

```Java
// GOOD PRACTICE - Using try-with-resources
try (Stream<String> lines = Files.lines(path)) {
lines.forEach(System.out::println);
}
```
</li>
</list>
</procedure>

## Lazy Evaluation in Streams — A Closer Look
<tldr><i>Lazy evaluation in Java Streams determines that most operations (with the exception of 
parallel environments) will be executed not when they are declared, but rather when a 
<code>terminal operation is declared</code></i></tldr>
<p>Lazy evaluation in Java, allows us to separate declaration from invocation, by effectively 
chaining streams together in a pipeline, <code>where every resulting stream has an 
operation applied to it,specified, but not executed</code>. This <b>piping</b> operation then is 
only executed in an order style of <code>one-by-one</code> (specifically in sequential streams), 
once a terminal method is invoked.
<br/><br/>
While this is the same overarching style in parallel streams, the execution of intermediate 
operations <code>is not done one-by-one, rather simultaneously</code>.
<br/><br/>
Similarly to the code above, here are some pointers related to lazy evaluation both for single 
core and parallel environments
</p>
<procedure title="Lazy Evaluation in Stream Operations | Environment Guidelines" collapsible="true">
<tabs>
<tab title="Single-Threaded Environment">
<list>
<li><b><format color="CornFlowerBlue">Operation Declaration vs Execution</format></b>: In 
single-threaded environments, operations are not executed when they are declared. Instead, they 
are stored in the form of a pipeline, waiting for a terminal operation to trigger their 
execution. This allows for optimization and resource efficiency since unnecessary operations can 
be avoided.</li>
<li><b><format color="CornFlowerBlue">Sequential Processing</format></b>: When a terminal 
operation is called, the elements are processed one at a time through the entire pipeline. This 
means that for each element, all intermediate operations are completed before moving to the next 
element, ensuring predictable processing order and memory usage.</li>
<li><b><format color="CornFlowerBlue">Short-Circuiting Benefits</format></b>: Lazy evaluation 
particularly benefits short-circuiting operations in sequential streams. Once a condition is met 
(like finding a matching element), the stream can immediately stop processing without evaluating 
the remaining elements.</li>
<li><b><format color="CornFlowerBlue">Memory Efficiency</format></b>: Lazy evaluation in 
sequential streams can be highly memory-efficient, especially when dealing with large data sets, 
as only one element needs to be processed at a time through the pipeline.</li>
<li><b><format color="CornFlowerBlue">Predictable Optimization</format></b>: The Java runtime 
can optimize the execution of the pipeline more effectively in sequential streams because the 
order and timing of operations are predictable and consistent.</li>
<li><b><format color="CornFlowerBlue">Debugging Advantage</format></b>: The sequential nature of 
lazy evaluation makes debugging easier as the flow of data through the pipeline is deterministic 
and can be traced step by step.</li>
<li><b><format color="CornFlowerBlue">Resource Management</format></b>: Resources are managed 
more efficiently as they are only acquired when needed and can be released immediately after use,
following the sequential flow of the pipeline.</li>
<li><b><format color="CornFlowerBlue">Operation Fusion</format></b>: The runtime can potentially 
fuse multiple operations together in sequential streams, reducing the number of iterations 
needed over the data.</li>
</list>
</tab>
<tab title="Parallel Environment">
<list>
<li><b><format color="CornFlowerBlue">Concurrent Pipeline Building</format></b>: In parallel 
environments, while the operations are still lazily evaluated, the pipeline building process 
must account for potential concurrent access and execution patterns.</li>
<li><b><format color="CornFlowerBlue">Chunk-Based Processing</format></b>: Instead of processing 
one element at a time through the entire pipeline, parallel streams divide the data into chunks 
that can be processed independently. Each chunk goes through the complete pipeline on its own 
thread.</li>
<li><b><format color="CornFlowerBlue">Evaluation Timing Complexity</format></b>: The actual 
timing of operation execution becomes more complex in parallel streams, as different parts of 
the stream may be evaluated simultaneously on different threads.</li>
<li><b><format color="CornFlowerBlue">State Management Considerations</format></b>: Lazy 
evaluation in parallel environments requires careful consideration of state management, as 
multiple threads may attempt to access or modify shared state simultaneously.</li>
<li><b><format color="CornFlowerBlue">Resource Allocation Strategy</format></b>: Resources must 
be managed differently in parallel streams, potentially requiring thread-safe resource pools or 
multiple resource instances to handle concurrent access.</li>
<li><b><format color="CornFlowerBlue">Order Guarantees</format></b>: Lazy evaluation in parallel 
streams doesn't guarantee the order of execution, which can affect operations that depend on 
encounter order. Special consideration is needed for operations requiring specific ordering.</li>
<li><b><format color="CornFlowerBlue">Performance Implications</format></b>: While parallel 
processing can improve performance, the overhead of coordinating lazy evaluation across multiple 
threads must be considered. Not all operations benefit from parallelization.</li>
<li><b><format color="CornFlowerBlue">Short-Circuit Complexity</format></b>: Short-circuiting 
operations become more complex in parallel environments as they must coordinate across multiple 
threads to determine when to stop processing.</li>
<li><b><format color="CornFlowerBlue">Memory Considerations</format></b>: Memory usage patterns 
differ in parallel streams as multiple chunks of data may be processed simultaneously, 
potentially requiring more memory than sequential processing.</li>
<li><b><format color="CornFlowerBlue">Debugging Challenges</format></b>: The non-deterministic 
nature of parallel execution makes debugging more challenging, as the exact sequence of 
operations may vary between runs.</li>
<li><b><format color="CornFlowerBlue">Split-Friendly Sources</format></b>: The effectiveness of 
lazy evaluation in parallel streams heavily depends on how well the data source can be split 
into independent chunks. Some data sources are more amenable to parallel processing than others.
</li>
<li><b><format color="CornFlowerBlue">Merging Results</format></b>: Special consideration must 
be given to how results from different threads are merged, especially in reduction 
operations or when maintaining order is important.</li>
</list>
</tab>
</tabs>
</procedure>