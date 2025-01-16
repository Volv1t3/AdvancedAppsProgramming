# Class #1 | Tuesday 14th | Lambdas and Streams

>The following file will contain information about Lambdas and Streams in 
> Java. This is a course based on the Contents of the Java Stream API implementation. This chapter will hold 
> definitions, examples, and implementations of basic Stream and Lambda snippets of code.


## Lambdas

### What are Lambdas?
<p>Lambdas, are segments of code, specifically implementations of functional interfaces, that do not have a 
definition in a file nor in an inner class or secondary class. Rather these are anonymous blocks of code whose usage 
is only in certain segments that expect a functional interface implementation.
<br/><br/>
These implementations can be defined in variables to be reused, or even us Functional Programming concepts to add 
ontop the functionality of Lambdas. However these are mostly used to declare single use implementations.
</p>

## Streams
### What are Streams?
<p>Streams are flows of data, most of the time in bits, that have an origin (be it on our computer or on another 
machine), that sends data over to our system (or another) in certain segments or chunks of information. 
<br/><br/>
Streams in general are collections of data, sequences of them in fact, whose data can be operated upon. In Java we 
have <code>intermediate and terminal operations</code> that are used to manipulate, collect, filter, and otherwise 
transform the data within.
<br/><br/>
The main characteristics for Streams that we will talk about is <code>their timed characteristic</code>, meaning 
that data can be lost or stored, or seen but not looped back to.</p>

### Functional Programming In Java
<p>Altough Java is written in such a way that its classes and their code is written in terms of OOP. The idea of the 
language, and specially everything added in Java 8 and Java 9 (Lambdas, Method References...; Better Streams..., 
respectively), is to allow for the programmer to use <code>Functional Programming</code>.
<br/><br/>
Functional programming is a programming model where we do not use classes or objects, rather we reduce our code to 
use functions, streams, parallelization, and concurrency to build code through <code>composition, extension, and 
Method reuse</code>, instead of <code>inheritance, polymorphism, and encapsulation</code>. <br/><br/>
In this sense, Streams, given through their functional programming style, we are capable of manipulating data using 
functional programming and <code>interfaces, Lambdas, method references, method chaining, etc.</code> All of which 
allow us to obscure the way our code works, reduce the complexity and minimize the probability of errors in our code.
<br/><br/>
The most basic example in terms of functional programming with streams is the following two pieces of code
</p>
<procedure>

```Java
packge example;

public class Example{

    public static void main(String[] args) {
        //! Complex Version: non functional
        int[] data =  {0,1,2,3,4,5,6,7,8,9};
        var sum = 0;
        for(int i = 0; i < data.length; i++){
            sum += data[i];
        }
        System.out.println("sum = " + sum);
        //! Streams API
        sum = 0;
        sum = Arrays.stream(data)
                .boxed()
                .reduce(Integer::sum)
                .get();
        System.out.println("sum = " + sum);

    }
}
```
</procedure>
<p>The output of the previous code is the same for both section 45. However, it i less lines overall and clearer to 
see and read when we use a functional programming style. The idea is to <code>reduce the probability of logic errors 
through abstraction and application of procedural programming.
</code>. Hence, our program in the second section for the stream API, is effectively using the array, boxing it to 
make them <code>Integers</code>, then reducing them by a method reference to sum integers together, and lastly we 
get the value from the <code>Optional Integer</code> that the reduce method returns. after which this value is 
assigned into the variable sum.
<br/><br/>
Java Streams have various methods and classes that allow you to work not only with numbers (<code>Integers, 
Doubles, Floats</code>), but also with classes, files, and even Strings.
<br/><br/>
Another example would be this.
</p>
<procedure>

```Java
package example;

public class Example{

  
    public static void main(String[] args){

        //! Using Java Stream API
        /*
         * Este ejemplo muestra como manipular
         * un Stream de Datos para generar una
         * serie de numeros del 1 al 100
         */
        Integer[] generatedNumbers = 
                IntStream.rangeClosed(1,100)
                .boxed()
                .toArray(Integer[]::new);

        /*
         * Este ejemplo muestra como manipular
         * los datos generados anteriormente
         * para filtrar los valores basados en
         * operaciones modulo
         */
        Integer[] filteredNumbers = 
            Arrays.stream(generatedNumbers)
            .filter(x -> x % 2 == 0)
            .toArray(Integer[]::new);

        /*
         * Este ejemplo muestra como manipular
         * los datos filtrados para revisar
         * divisibilidad para tres y hacer un
         * mapping adicional
         * Filtrar mayores de 50 y guardar
         */
        Integer[] filteredNumbersV2 = 
            Arrays.stream(filteredNumbers)
            .filter(x -> x % 3 == 0)
            .map(x -> x*4)
            .filter(x -> x > 50)
            .toArray(Integer[]::new);          
    }
}
```
</procedure>
<p>There are additional methods we can use to randomly declare numbers. The way we use this is through the 
<code>SecureRandom class defined in java.base.security.SecureRandom</code>, where methods like <code>ints()</code>, 
can be used to generate random integers within a range.
numbers </p>
<procedure>

```Java
package example;

public class Example{

    public static void main(String[] args){
    
    SecureRandom random = new SecureRandom();
    //! Using Stream APIP
    /*
     * El presente ejemplo genera un stream de 
     * valores enteros efecivamente infinito
    */
    IntStream random = random.ints();
    
    /*
     * El presente ejemplo genera un stream de
     * valores definiendo la cantidad de valores
     * aleatorios requeridos
    */
    IntStream random2 = random.ints(100L);
    
    /*
     * El presente ejemplo genera un stream 
     * de valores aleatorios en un rango
     * y tamano definido.
     * El ultimo valor no esta incluido
     */
     IntStream random3 = 
             random.ints(10L, 1, 101);
    }
}
```
</procedure>
<p>While these are examples that cover mostly the Streams API, which in turn is the basis for functional programming 
in Java. The following sections will touch deeper into the APIs that make this all possible. </p>

### Java Stream API Overview
<p>The Java Stream API was added initially in java 8 in an effort to reduce the complexity that came from handling 
streams of data and in an effort to reduce readability issues with large loop and long nested operations with flow 
control structures.<br/><br/>
Adittionaly, the way it works has been interfaced with both <code>data structures, input streams and lambdas</code>, 
such that blocks of code that use the Stream API are more concise and simpler to understand.
<br/><br/>
Here are some features this API both in terms of collections, input data, and online information 
streams.
</p>
<procedure>
<tabs>
<tab title="Stream APIs On Collections">
<p>The Stream API was connected early on with the Collection Framework such that most implementations and iterators 
have access to this. However, it is important to note these aspects about Streams and Collections
</p>
<list>
<li><b><format color="CornFlowerBlue">Key Interfaces and Methods</format></b>: While the Stream API connects to the 
Java Collections Framework directly using he <code>stream()</code> method provided in the <code>Collection 
interface</code>, some collections, specially those that hold internal structures that are more complex than a 
simple Array or ArrayList, require extra work to handle streaming of their internal structures.
<br/>
Internally, streams operated on top of the Collection Framework <code>work over the data stored in said 
collections, they modify them or use them for other purposes on the collection. </code>However, they create new 
collections <code>on the fly</code>, rather than using the base Collection structure. This ensures the 
<b>immutability of the sources</b>
</li> 
<li><b><format color="CornFlowerBlue">Opened And Closed Principle</format></b>: Streams based on any source, 
including data sources like Collections, gather information, operate on them <code>after encountering a 
terminal operation</code>, are closed and cannot be reused in any other sections of code. However, <b>you can 
open other streams with the same data sources, so long as they are not null</b></li>
<li><b><format color="CornFlowerBlue">Parallelization and Concurrent Streams</format></b>: While Streams in Java 
already provide ways to handle parallelization of data sources for management and operation these have to be 
carefuly managed such that race conditions are not incurred. Moreover <code>streams that are used in 
parallelized environments, may or may not be parallel underneath</code>, this behavior depends on the JVM, and it 
is important to note the <b>computational overhead added when attempting to parallelize a section of code.</b> 
</li> 
</list>
<p>Having discussed some pointers on the usage of Streams over data structures, the following examples will 
illustrate how to handle these in a simple manner, and the pitfalls of Streams over Collections.</p>
<procedure>

```Java
package example;

public class Example{

    public static void main(String[] args){
        // All collections support the stream() method
        List<String> list = 
                   Arrays.asList("a", "b", "c");
        Stream<String> stream = 
                   list.stream();
        
        // Parallel streams are also supported
        Stream<String> parallelStream = 
                        list.parallelStream();
                        
        // Lists
        ArrayList<Integer> arrayList = 
                        new ArrayList<>();
        Stream<Integer> listStream = 
                        arrayList.stream();

        // Sets
        HashSet<String> set = 
                        new HashSet<>();
        Stream<String> setStream = 
                       set.stream();

        // Maps (require special handling)
        HashMap<String, Integer> map = 
                         new HashMap<>();
        Stream<Map
               .Entry<String, Integer>> 
               entryStream = 
                    map.entrySet().stream();
        Stream<String> keyStream = 
               map.keySet().stream();
        Stream<Integer> valueStream = 
               map.values().stream();

        List<String> items = 
            Arrays.asList("apple", "banana", "orange");

        // Filtering
        List<String> filtered = items.stream()
            .filter(item -> item.startsWith("a"))
            .collect(Collectors.toList());
        
        // Mapping
        List<Integer> lengths = items.stream()
            .map(String::length)
            .collect(Collectors.toList());
        
        // Reducing
        int totalLength = items.stream()
            .mapToInt(String::length)
            .sum();
        
        // Working with arrays
        String[] array = 
                {"a", "b", "c"};
        Stream<String> arrayStream = 
                Arrays.stream(array);
        
        // Creating streams from individual elements
        Stream<Integer> stream = 
                Stream.of(1, 2, 3, 4, 5);
        
        // Converting streams back to collections
        List<String> list = 
                stream.collect(Collectors.toList());
        Set<String> set = 
                stream.collect(Collectors.toSet());
        Map<String, Integer> map = 
                stream.collect(
                Collectors
                .toMap(key -> key, String::length));

    }
}

```
</procedure>
<p>As can be seen through this example, various data structures can be handled either simply or with some additions 
such that they work on the internals of said collections. In general, the collections that <b>support streams 
directly are</b> <code>ArrayLists, LinkedLists, HashSet, TreeSet, LinkedHashSet, Vector, Stack, PriorityQueue, 
ArrayDeque, ConcurrentHashMap, CopyOnWriteArrayList, CopyOnWriteArraySet
</code>. While collections that are <code>legacy collections, Arrays or custom collections</code> <b>do not 
support Collections</b></p>
</tab>
<tab title="Stream APIs on Files and Other Data Sources">
<p>Java Streams APIs can work both on files and on other data sources, like our own objects, generation patterns, 
and even iteration patterns. Specifically, <code>Java Stream APIs support: .empty(), .of(), .builder(), .generate(), .
iterate().
As 
well as streaming Strings, and Files.
</code>
<br/><br/>
Given the number of methods to cover, this section will focus on discussing key topics about each method, each 
framework for iteration and then a simple example of each (this example will come from the Baeldung On Java page 
about streams)</p>
<tabs>
<tab title="Stream.empty()">
<list>
<li><b><format color="CornFlowerBlue">Stream.empty()</format></b>:
The idea behind this method is to allow the programmer, to define correct behavior that avoids 
<code>NullPointerExceptions or errors during data access</code>, since it returns an empty Stream rather than a 
null reference.
<br/>
It can be used when it comes to methods that are meant to stream data from the user that may or may not be empty. 
One key point to remember is that this stream is parametrized by a parameter of type <code> T </code>, that defines 
the original Stream of values. 
<br/>
<procedure>

```Java
package example;
//... on RecipeManager.java
public class RecipeManager {
    private List<Recipe> recipes = new ArrayList<>();

    pubilc static class Recipe {
        private String name;
        private int servings;

        public Recipe(String name, int servings) {
            this.name = name;
            this.servings = servings;
        }

        public String getName() {
            return name;
        }

        public int getServings() {
            return servings;
        }
    }

    public void addRecipe(Recipe extRecipe){
            if (extRecipe != null){
                var exists = this
                        .recipes
                        .stream()
                        .anyMatch(recipe
                                -> {
                            return recipe
                                    .getName()
                                    .equals(extRecipe
                                            .getName());
                        });
                if (!exists){
                    this.recipes.add(extRecipe);
                }
            }
        }

    // Method demonstrating 
    // Stream.empty() as a fallback
    public Stream<Recipe> 
        findRecipesByServings(int servings) {
        if (servings <= 0) {
           // Return empty stream 
           // for invalid input
           return Stream.empty();  
        }
        
        return recipes
                    .stream()
                    .filter(recipe -> 
                    {recipe.getServings() == servings;});
    }

    // Method showing Stream.empty() 
    // in conditional logic
    public Stream<Recipe> 
           searchRecipes(String searchTerm) {
        if (searchTerm == null 
            || searchTerm.trim().isEmpty()) {
            // Return empty stream 
            // for null/empty search
            return Stream.empty();  
        }

        return recipes.stream()
               .filter(recipe -> recipe.getName()
               .toLowerCase()
               .contains(searchTerm.toLowerCase()));
    }

    // Method to demonstrate safe 
    // operations with empty streams
    public void 
          displayRecipes(Stream<Recipe> recipeStream) {
        recipeStream
         .map(Recipe::getName)
         // Safe even with empty stream
         .forEach(System.out::println);  
    }

    // Example usage
    public static void main(String[] args) {
        RecipeManager manager = new RecipeManager();
        
        // Using empty stream as fallback
        Stream<Recipe> emptyResult = 
                manager.findRecipesByServings(-1);
        // Prints 0
        System.out.println("Count of recipes: " 
                        + emptyResult.count()); 
        
        // Safe operation with empty stream
        manager.displayRecipes(Stream.empty()); 
        // No output, no errors
        
        // Using in conditional search
        // No output, safe execution
        manager
            .displayRecipes(
            manager.searchRecipes("")); 
    }
}

```
</procedure>
</li>

</list>
</tab>
<tab title="Stream.of()">
<list>
<li><b><format color="CornFlowerBlue">Stream.of()</format></b>: The Stream.of method allows you to create a stream 
based on a variadic number of arguments. The idea of this method, which is also parametrized, is to provide the 
programmer the ability to create variadic parametrized streams of a size of their choosing.
<br/>
In addition to this, the method is overloaded to provide <code>single argument non-Empty() returning stream, of 
empty-returning single argument stream, and a variadic @SafeVarargs stream of values.</code>
<br/>
The examples presented below will showcase how to use this method generally
<procedure>

```Java

// Method demonstrating usage of Stream.of() variants
public Stream<Recipe> findRecipesByNames(String... names) {
    if (names == null || names.length == 0) {
        // Return empty stream if no names are provided
        return Stream.empty();
    }

    // Create a stream of names using Stream.of() and
            // filter the recipes
            /*
             * Este metodo genera un stream de los nombres ingresados.
             * Dentro de este stream, se modifica los valores con un 
             * mapper que toma todos los nombres y filtra la lista
             * interna en base a aquellas recetas con el mismo nombre
             */
            return Stream.of(names)
                    .parallel()
                    .flatMap(nameToSearch
                            -> recipes.stream()
                            .filter(recipe -> recipe.getName()
                                    .equalsIgnoreCase(nameToSearch)));
        }

// Method demonstrating single argument non-empty stream
public Stream<Recipe> findRecipeBySingleName(String name) {
    if (name == null || name.trim().isEmpty()) {
        // Return empty stream for null/empty name
        return Stream.empty();
    }

    // Use Stream.of() with a single argument
    return Stream.of(name)
            .flatMap(nameToSearch -> recipes.stream()
                    .filter(recipe -> recipe.getName()
                            .equalsIgnoreCase(nameToSearch)));
}

// Example usage
public static void main(String[] args) {
    RecipeManager manager = new RecipeManager();

    // Add sample data
    manager.addRecipe(new Recipe("Pasta", 4));
    manager.addRecipe(new Recipe("Pizza", 2));
    manager.addRecipe(new Recipe("Salad", 3));

    // Using Stream.of() for multiple names
    Stream<Recipe> recipesByNames = manager.findRecipesByNames("Pasta", "Pizza");
    manager.displayRecipes(recipesByNames); // Output: Pasta, Pizza

    // Using Stream.of() with a single name
    Stream<Recipe> recipeBySingleName = manager.findRecipeBySingleName("Salad");
    manager.displayRecipes(recipeBySingleName); // Output: Salad

    // Example with no names provided
    Stream<Recipe> noRecipes = manager.findRecipesByNames();
    System.out.println("Count of recipes: " + noRecipes.count()); // Output: 0
}
```
</procedure>
</li>
</list>
</tab>
<tab title="Stream.builder()">
<list>
<li><b><format color="CornFlowerBlue">Stream.builder()</format></b>: This is a method that allows the user to 
construct a modifiable stream and append values to it using chained  methods. While in Java, most streams come from 
a data source on their own, or are themselves created from an empty source, this method allows you to 
<code>programmatically create your own stream</code>, using add operations and accept operations that allow you to 
add elements in a chain, and add an element with a void return, respectively, to it.
<br/>
The main benefits of this method in Streams are listed below
<list>
<li><b><format color="CornFlowerBlue">Dynamic Stream Construction</format></b>: allows for conditional, complex and 
manual, element addition. Controlling each element as they come in making sure they agree to certain programmatic, 
runtime, or use defined conditions.
</li> 
<li><b><format color="CornFlowerBlue">Custom Stream Assembly</format></b>: Its a more manual method that allows you to create a Stream based on multiple sources of data, gathering them in a controlled and ordered manner.</li> 
<li><b><format color="CornFlowerBlue">Streams without Collections</format></b>: This method allows you to build 
streams without the explicit use of a collection or other data source underneath, being extremely helpful 
<code>to cases in which you need to manually control contents for testing and prototyping</code></li>
</list>
<br/>
Internally, the JVM works in the following way
<procedure>
<p>" Internally, a Stream.build() command returns the implementation of a StreamBuilder, or a 
<code>StreamBuilderImpl</code> instance which is <b>package privae</b> within the java.util.stream package. 
Internally this implementation uses a <code>SpinedBuffer</code> data structure, which has a n-ary array of arrays to 
store data. 
<br/><br/>
The documentation for this package private class states that <i>[A SpinedBuffer is] an ordered collection of 
elements. Elements can be added, but not removed. [It] Goes through a building phase, during which elements can be 
added, and a traversal phase, during which elements can be traversed in order but no further modifications are 
possible</i>
<br/><br/>
For this reason, these are useful to concatenate a nearly infinite number of items (much more than even an 
ArrayList supports)"
</p>
</procedure>
<br/>
Lastly, we present a segment of example code
<procedure>

```Java
public class RecipeManager {
    private static class Recipe {
        private String name;
        private int servings;
        private String difficulty;

        public Recipe(String name, 
                int servings, 
                String difficulty) {
            this.name = name;
            this.servings = servings;
            this.difficulty = difficulty;
        }

        @Override
        public String toString() {
            return "Recipe{" +
              "name='" + name + '\'' +
              ", servings=" + servings +
              ", difficulty='" + difficulty + '\'' +
              '}';
        }
    }

    // Method that builds a stream 
    // of recipes based on difficulty level
    public Stream<Recipe> 
        buildRecipesByDifficulty(
                    String targetDifficulty, 
                    List<Recipe> allRecipes) {
            Stream.Builder<Recipe> 
                builder = Stream.builder();
        
            for (Recipe recipe : allRecipes) {
                if (recipe.difficulty
                          .equalsIgnoreCase(
                          targetDifficulty)) {
                    builder.add(recipe);
                }
            }
            
            return builder.build();
    }

    // Method that builds a stream 
    // of recipes based on serving size
    public Stream<Recipe> 
        buildRecipesByServings(
               int minServings, 
               int maxServings, 
               List<Recipe> allRecipes) {
            Stream.Builder<Recipe> 
                    builder = Stream.builder();
        
            for (Recipe recipe : allRecipes) {
                if (recipe.servings >= minServings 
                    && recipe.servings <= maxServings) {
                    builder.add(recipe);
                }
            }
        
        return builder.build();
    }

    // Method that combines recipes 
    // from multiple sources using builder
    public Stream<Recipe> 
           combineRecipeSources(
                List<Recipe> source1, 
                List<Recipe> source2) {
        Stream.Builder<Recipe> 
                builder = Stream.builder();
        
        // Add recipes from first source 
        // that serve more than 2 people
        source1.stream()
                .filter(recipe -> recipe.servings > 2)
                .forEach(builder::add);
        
        // Add recipes from second source 
        // that are easy difficulty
        source2.stream()
                .filter(recipe -> recipe
                    .difficulty.equalsIgnoreCase("easy"))
                .forEachOrdered(builder::add);
        
        return builder.build();
    }

    public static void main(String[] args) {
        RecipeManager manager = new RecipeManager();
        
        // Create test data
        List<Recipe> mainRecipes = Arrays.asList(
            new Recipe("Pasta Carbonara", 4, "Medium"),
            new Recipe("Simple Salad", 2, "Easy"),
            new Recipe("Beef Wellington", 6, "Hard"),
            new Recipe("Scrambled Eggs", 1, "Easy")
        );

        List<Recipe> alternativeRecipes = Arrays.asList(
            new Recipe("Vegetarian Curry", 4, "Easy"),
            new Recipe("Grilled Cheese", 1, "Easy"),
            new Recipe("Soufflé", 2, "Hard")
        );

        // Test buildRecipesByDifficulty
        System.out.println("Easy Recipes:");
        manager.buildRecipesByDifficulty("Easy", mainRecipes)
                .forEach(System.out::println);

        // Test buildRecipesByServings
        System.out.println("\nRecipes for 2-4 people:");
        manager.buildRecipesByServings(2, 4, mainRecipes)
                .forEach(System.out::println);

        // Test combineRecipeSources
        System.out.println(
        "\nCombined recipes "+
        "(serves >2 from main list"+
        "+ easy from alternative list):");
        manager.combineRecipeSources(
                mainRecipes, 
                alternativeRecipes)
                .forEach(System.out::println);
    }
}

```
</procedure>
</li>
</list>
</tab>
<tab title="Stream.generate()">
<list>
<li><b><format color="CornFlowerBlue">Stream.generate()</format></b>: This method allows the user to create a random 
(infinite by nature) stream of elements by lazily evaluating a Supplier sent into the method call. This then creates 
a <code>sequence of elements (which are generated on-demand) based on a Supplier which can be of any nature based on 
a programmer-defined behavior.
</code>
<br/>
This method, then, allows the programmer to define the way these objects are to be created, and of course, they in no way 
require to have as inputs, simple Java objects, they can also be our own Java objects. 
<br/>
In the following listing, I propose some of the benefits of using this method when working with Streams
<list>
<li><b><format color="CornFlowerBlue">Flexible Custom Object Generation</format></b>: the <code>Stream.generate()</code> 
method allows the user to have a factory to create any type of objects that they desire, <code>defining 
their own generation patterns</code>, with no limitation to base java objects</li>
<li><b><format color="CornFlowerBlue">Constgant Value or Random Data Generation</format></b>: This method allows the 
user to define streams that consist of the same value (like copying over and over), or random values. Two 
behaviors that are useful for <code>testing environments, simulations or sequence generation</code></li>
<li><b><format color="CornFlowerBlue">Lazy Evaluation and On-Demand Creation</format></b>: This method creates 
objects only when they are needed rather than all the time, saving memory and processing power. They use the concept of 
JIT creation.</li> 
</list>
<br/>
Lastly this next example will show the way we should work with this method on a complete class example
<procedure>

```Java
import java.util.Random;
import java.util.stream.Stream;

public class StreamGenerateDemo {
    // Custom class to demonstrate flexible object generation
    static class Product {
        private String name;
        private double price;
        private String id;

        public Product(String name, double price, String id) {
            this.name = name;
            this.price = price;
            this.id = id;
        }

        @Override
        public String toString() {
            return "Product{name='"
             + name + 
             "', price=" 
             + price + 
             ", id='" 
             + id + "'}";
        }
    }

    public static void main(String[] args) {
        // Benefit 1: Flexible 
        // Custom Object Generation
        System.out.println(
            "=== Custom Product Generation ===");
        Stream.generate(() -> new Product(
                "Product-" + new Random().nextInt(100),
                Math.round(
                    new Random().nextDouble() * 100.0) / 100.0,
                "ID-" + System.currentTimeMillis()
        ))
        .limit(3)
        .forEach(System.out::println);

        // Benefit 2: 
        // Constant Value Generation
        System.out.println(
            "\n=== Constant Value Generation ===");
        Stream.generate(() -> "Standard Product")
        .limit(3)
        .forEach(System.out::println);

        // Benefit 3: 
        // Lazy Evaluation Demonstration
        System.out.println(
            "\n=== Lazy Evaluation Demo ===");
        Stream<Product> productStream = Stream.generate(() -> {
            // Shows when product is actually created
            System.out.println("Creating new product..."); 
            return new Product(
                    "Lazy-" + new Random().nextInt(100),
                    99.99,
                    "LAZY-" + System.currentTimeMillis()
            );
        });

        // Products are only created when 
        // we start consuming the stream
        System.out.println(
            "Stream created but no products generated yet...");
        productStream
            .limit(2)
            .forEach(product -> {
                System.out.println("Consuming: " + product);
            });
    }
}


```
</procedure>

</li> 


</list>
</tab>
<tab title="Stream.iterate()">
<list>
<li><b><format color="CornFlowerBlue">Stream.iterate()</format></b>: Is a method that allows the user to " 
<i>produce a squential ordered Stream produced by iterative application of the given next function to an 
initial element conditioned on satisfying the hasNext() predicate. The stream terminates as soon as the 
hasNext predicate returns false</i>"
<br/>
Based on the above description, it is straightforward to understand that this method is a simple generator with the 
generate-while-true pattern embedded into it. For the sake of completeness, in contrast to the other methods, the 
signature of this method is required right away. <code>Static {T} Stream{T} iterate(T seed, Predicate{? super T} 
hasNext, UnaryOperator{T} next).</code> 
<br/>
As can be noted by the signature, what we are doing is generate based on a seed, the initial value we are going to 
use for generation, whilst the hasNext predicate determines <code>the stopping condition that must be followed 
for generation</code>, and lastly the next method determines <code>the how to generate these values</code>. 
Therefore, both the hasNext and next lambda references here or anonymous classes if prefered, must at some point be 
proven to cross each other such that a stopping condition is achieved.
<br/>
An example of this would be generation of integer numbers from seed 0, all the way to 9 if the hasNext method 
requires the input to be less than 10, and the next method returns the input plus one.
<br/>
All in all, while this method appears complicated, it is simply an abstraction of a generative while, or for loop. 
Hence, we shall move on to the benefits of using this method.
<list>
<li><b><format color="CornFlowerBlue">Predictable Sequence Generation</format></b>: Unlike a the 
<code>Stream.generae() method</code>, this method creates a predictable sequence, step by step. This makes it 
perfect for <code>matheamtical sequences, date ranges, or any pattern that follows specific progression</code>.</li>
<li><b><format color="CornFlowerBlue">Built-in Termination Control</format></b>: This method, based on its 
parameter list, allows you to define a stopping condition, making your code stop at exactly where you want during 
generation. This in turn <code>prevents infinite loops without an explicit limit() method call</code></li>
<li><b><format color="CornFlowerBlue">Stateful Element Generation</format></b>: This method <code>keeps 
previous state for the next iteration  
</code>by virtue of its <code>next parameter</code> depending on the previous value, which can be useful to 
validate sequences of elements or track progression.</li> 
</list>
<br/>
Lastly, we define a single example class of its usage here.
<procedure>

```Java
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamIterateDemo {
    // Custom class to demonstrate financial calculations
    static class Investment {
        private double amount;
        private LocalDate date;
        private int month;

        public Investment(double amount, LocalDate date, int month) {
            this.amount = amount;
            this.date = date;
            this.month = month;
        }

        @Override
        public String toString() {
            return String.format("Month %d [%s]: $%.2f", 
                month, date, amount);
        }
    }

    public static void main(String[] args) {
        // Benefit 1: Predictable Sequence Generation
        System.out.println("=== Fibonacci Sequence ===");
        record FibPair(long prev, long current) {}
        
        List<Long> fibonacciNumbers = 
            Stream.iterate(
                new FibPair(0, 1),
                pair -> pair.current() < 100,
                pair -> new FibPair(
                    pair.current(), 
                    pair.prev() + pair.current())
            )
            .map(FibPair::current)
            .collect(Collectors.toList());
        
        System.out.println("Fibonacci numbers under 100: " 
            + fibonacciNumbers);

        // Benefit 2: Built-in Termination Control
        System.out.println(
            "\n=== Power of 2 Sequence ===");
        Stream.iterate(1, n -> n <= 64, n -> n * 2)
              .forEach(n -> System.out.print(n + " "));

        // Benefit 3: Stateful Element Generation
        System.out.println(
        "\n\n=== Investment Growth Projection ===");
        double initialInvestment = 1000.00;
        // 0.5% monthly interest
        double monthlyInterestRate = 0.005; 

        List<Investment> investmentProgress = 
            Stream.iterate(
                new Investment(
                    initialInvestment, 
                    LocalDate.now(), 
                    1
                ),
                inv -> inv.month <= 12,
                inv -> new Investment(
                    inv.amount * (1 + monthlyInterestRate),
                    inv.date.plusMonths(1),
                    inv.month + 1
                )
            )
            .collect(Collectors.toList());

        System.out.println("Monthly Investment Progress:");
        investmentProgress.forEach(System.out::println);

        // Calculate total growth
        double totalGrowth = investmentProgress
            .get(investmentProgress.size() - 1)
            .amount 
                - initialInvestment;
        System.out.printf("\nTotal growth: $%.2f",
                                    totalGrowth);
    }
}

```
</procedure>
</li> 
</list>
</tab>
</tabs>
</tab>
</tabs>
</procedure>
<p>While the previous examples showcased various parts of the Java Stream API, there are still various methods to be 
covered. For example, the <code>mapping methods, collecting methods, find methods (short-circuit operations), reduce 
operations, peek, sorted, distinct, filter, etc. </code>. These methods will be covered briefly in the next smaller 
sections within Streams.
</p>


### Java Collectors API Overview
<p>The Java Collectors API is defined as a <code>Collection of overloaded terminal methods for streams, 
designed for data transformations, and reductions, particularly in the context of Streams in Java.</code>. The 
following is a definition provided by Amazon Q, based on content that I have reviewed for correctness</p>
<procedure>
<p><i>" The Java Collectors API is a sophisticated and comprehensive collection of terminal operations specifically 
designed for Stream processing, providing a unified interface for performing various reduction, transformation, and 
accumulation operations on stream elements through the Collector interface. It serves as a powerful abstraction 
layer that encapsulates mutable reduction operations where stream elements can be accumulated into collections, 
summarized into numerical results, grouped by specific criteria, or transformed into complex data structures, all 
while maintaining thread-safety through concurrent reduction operations when needed. The API offers both 
</i> <format style="bold">out-of-the-box implementations through the Collectors utility class (such as toList(), 
groupingBy(),mapping(),and reducing()) </format> <i> and the flexibility to create </i><format style="bold"> custom 
collectors, 
enabling 
developers to perform complex data 
manipulations</format><i> ranging from simple collection aggregation to intricate multi-level grouping operations, 
string 
joining, statistical computations, and specialized reduction strategies, making it an indispensable tool for 
processing and transforming streams of data in a declarative and functional programming style within the Java 
ecosystem</i></p>
</procedure>
<p>Based on this definition, we can extract various important concepts and key definitions that will become more 
aparent as we play around with the Collectors methods.</p>
<procedure>
<tabs>
<tab title="Java Collectors API | Key Characteristics">
<list>
<li><b><format color="CornFlowerBlue">Mutable Reduction Operations</format></b>: This API provides a framework for 
<code>accumulating objects into mutable containers</code>, supporting <code>concurrent reduction for parallel
processing</code>, while maintaining thread safety during collection operations
</li>
<li><b><format color="CornFlowerBlue">Terminal Operation Design</format></b>: Functions as <code>terminal operations in Stream pipelines</code>,
<code>processes all elements to produce a final result</code>, and <code>concludes stream processing with a concrete result</code>
</li>
<li><b><format color="CornFlowerBlue">Composable Architecture</format></b>: Enables <code>chaining of multiple collectors</code>,
<code>supports nested collection operations</code>, and <code>facilitates complex data transformations through composition</code>
</li>
<li><b><format color="CornFlowerBlue">Functional Programming Integration</format></b>: Implements <code>declarative programming paradigms</code>,
<code>provides immutable operation results</code>, and <code>seamlessly integrates with Java's functional interfaces</code>
</li>
<li><b><format color="CornFlowerBlue">Flexible Data Transformation</format></b>: Offers <code>support for various collection types</code>,
<code>enables custom collection strategies</code>, and <code>handles diverse data aggregation patterns</code>
</li>
</list>
</tab>
<tab title="Java Collectors API | Pros">
<list>
<li><b><format color="CornflowerBlue">Comprehensive Built-in Operations</format></b>: Provides a <code>rich set of predefined 
collectors</code>,
<code>common collection operations readily available</code>, and <code>standardized approach to data collection</code>
</li>
<li><b><format color="CornflowerBlue">Thread Safety Guarantees</format></b>: Offers <code>built-in synchronization for parallel operations</code>,
<code>safe concurrent collection operations</code>, and <code>automatic handling of thread coordination</code>
</li>
<li><b><format color="CornflowerBlue">High-Level Abstraction</format></b>: Successfully <code>hides complex implementation details</code>,
<code>reduces boilerplate code</code>, and <code>provides clean, readable syntax</code>
</li>
<li><b><format color="CornflowerBlue">Flexibility and Extensibility</format></b>: Enables <code>custom collector implementation</code>,
<code>allows for specialized collection strategies</code>, and <code>adapts to various use cases</code>
</li>
<li><b><format color="CornflowerBlue">Integration with Stream API</format></b>: Provides <code>seamless workflow with stream operations</code>,
<code>natural terminal operations for streams</code>, and <code>consistency with functional programming model</code>
</li>
</list>
</tab>
<tab title="Java Collectors API | Cons">
<list>
<li><b><format color="CornflowerBlue">Performance Overhead</format></b>: Introduces <code>additional abstraction layer impacting performance</code>,
<code>memory overhead for intermediate operations</code>, and <code>potential boxing/unboxing costs for primitives</code>
</li>
<li><b><format color="CornflowerBlue">Learning Curve</format></b>: Presents a <code>complex API with many options</code>,
<code>requires understanding of functional concepts</code>, and <code>challenging debugging for complex operations</code>
</li>
<li><b><format color="CornflowerBlue">Memory Consumption</format></b>: Can be <code>memory-intensive for large datasets</code>,
<code>intermediate collection creation overhead</code>, and <code>potential memory leaks if not used properly</code>
</li>
<li><b><format color="CornflowerBlue">Debugging Complexity</format></b>: Produces <code>stack traces difficult to interpret</code>,
<code>complex operation chains hard to troubleshoot</code>, and <code>limited visibility into internal operations</code>
</li>
<li><b><format color="CornflowerBlue">Limited Implementation Control</format></b>: Has <code>internal behavior not always transparent</code>,
<code>limited optimization opportunities</code>, and <code>fixed implementation strategies for built-in collectors</code>
</li>
</list>
</tab>
</tabs>
</procedure>
<p>Having defined a set of characteristic concepts about the Collectors API, its pros and cons, it is time now to 
analyze how it works internally, and for this I will turn to the Java API Documentation Version 21, specifically to 
the <code>Interface Collector{T,A,R} reference guide</code></p>
<procedure>
<tabs>
<tab title="Type Parameters | What do they Mean?">
<p>The Java Collectors API, and underneath the interface Collector is defined by a trio of parameter types which 
define most of the generic functions, functional interfaces, or expected methods and return types that each of its 
implementated methods abide to.
<br/><br/>
Internally, these three parameters are used as the base for various generic methods, providing for a clean and 
overarching encapsulation of allowed types through generic bounding. Now then, the question that comes into mind is, 
what are these types?, what do they represent?, and where these are used?.
</p>
<br/>
<code>What are these types?</code>
<list>
<li><b><format color="CornFlowerBlue">Type param</format> <format color="Orange">T</format></b>: this generic type 
is not bounded by default, and <code>represents the type of input elements supplied to the reduction operation</code>.
</li>
<li><b><format color="CornFlowerBlue">Type param</format> <format color="Orange">A</format></b>: this generic type 
is also not bounded by default, and <code>represents the mutable accumulation type of the reduction operation</code>,
basically the kind of result container for the output of the reduction operation.
</li> 
<li><b><format color="CornFlowerBlue">Type Param</format> <format color="Orange">R</format></b>: this generic type 
is not bound by default too, and <code>represents the result type of the reduction operation</code>
</li> 
</list>
<br/>
<p>A Collector is usually specified by four functions that work together to accumulate entries into a mutable result 
container, and optionally perform a final transformation on the result. These four functions are <code>supplier(), 
accumulator(), combiner(), and an  optional finisher() </code>. These functions are mapped to a type parameter of 
the general interface of Collector, therefore, our next listing will show the connections between these methods and 
their typed parameters
</p>
<br/>
<code>What do they represent?</code>
<list>
<li><b><format color="CornFlowerBlue">Input processing</format> <format color="Orange">(T)</format></b>: The type 
parameter <code>T</code>, which was before listed as the type element of the <code>supplied elements of the 
reduction</code> is naturally mapped to the <code>accumulator() function of the Collector interface</code>. This 
is because there it is used alongside type param A <b>as the original input element type</b></li>
<li><b><format color="CornFlowerBlue">Internal accumulation</format> <format color="Orange">(A)</format></b>: The 
type parameter <code>A</code>, which was defined earlier as the <code>mutable data type of the reduction 
operation</code>, finds itself being used in three different parts of the Collector interface usual workings. It is 
used in <code>the supplier(), accumulator() and combiner() as the mutable accumulation type</code></li>
<li><b><format color="CornFlowerBlue">Final Result Production</format> <format color="Orange">(R)</format></b>: 
The type parameter <code>R</code>, which previously was defined as th <code>result type of type reduction 
operation</code>, finds itself being used in a single method, <code>the finisher() method </code>, an optional 
extra operation to perform more actions on the output before finishing</li> 
</list>
</tab>
<tab title="Type Parameters | Ordered Methodology of Method Calling">
<p>Despite the previous tab's work on this interface's type parameters, we are still not seeing the whole 
process as it is meant to happen. We have seen its components and dissected where each type parameter is used, however, 
we have not checked the order in which these are used. For this reason, this next listing will be ordered, showing 
the methodology behind the methods we have discussed</p>
<br/>
<code>Chain of Method Calling for any Collector Implementation</code>
<list>
<li><b><format color="CornFlowerBlue">Step 1: Initialization (supplier)</format></b>: The collector begins by calling 
<code>supplier()</code> to create a new mutable result container of type <code>A</code>. This is the first method 
called when stream processing begins.
<br/>

```Java
//! Example implementation of a supplier method
import java.util.*;
import java.util.function.Supplier;
public class Example{

    public static void main(String[] args){
        Supplier<ArrayList<Integer>> supplierForIntArrayList = 
                                                ArrayList::new;
        ArrayList<Integer> list = supplierForIntArrayList.get();   
    }
}
```
</li>
<li><b><format color="CornFlowerBlue">Step 2: Element Processing (accumulator)</format></b>: For each element in the 
stream of type <code>T</code>, the <code>accumulator()</code> method is called
Adds/processes each element into the mutable container created in Step 1. This step repeats for every element in the 
stream.
<br/>

```Java
//! Example implementation of a supplier method
import java.util.*;
import java.util.function.Supplier;
public class Example{

    public static void main(String[] args){
        //! Ejemplo de collector de un Integer[] 
        //! hacia ArrayList<Integer>
            //? Step One: Supplier for the holder
            Supplier<ArrayList<Integer>> supplierForIntArrayList = 
                                                    ArrayList::new;
            ArrayList<Integer> list = supplierForIntArrayList.get();
            //?Step Two: Accumulator for transformation
            Integer[] arr = {1,2,3,4,5};
            BiConsumer<Integer[], ArrayList<Integer>> 
                arrayListBiConsumer = new BiConsumer<Integer[], 
                                        ArrayList<Integer>>() {
                @Override
                public void accept(Integer[] integers, 
                        ArrayList<Integer> arrayList) {
                    arrayList.addAll(Arrays.asList(integers));
                }
            };
            arrayListBiConsumer.accept(arr, list);
    }
}
```
</li>
<li><b><format color="CornFlowerBlue">Step 3: Parallel Processing (combiner)</format></b>
If the stream is parallel, multiple containers from Step 1 & 2 are created. The <code>combiner()</code> method merges 
these containers. This method is called repeatedly until all partial results are merged
<br/>

```Java
//! Example implementation of a supplier method
import java.util.*;
import java.util.function.Supplier;
public class Example{

    public static void main(String[] args){
        //! Ejemplo de collector de un Integer[] 
        //! hacia ArrayList<Integer>
            //? Step One: Supplier for the holder
            Supplier<ArrayList<Integer>> supplierForIntArrayList = 
                                                    ArrayList::new;
            ArrayList<Integer> list = supplierForIntArrayList.get();
            //?Step Two: Accumulator for transformation
            Integer[] arr = {1,2,3,4,5};
            BiConsumer<Integer[], ArrayList<Integer>> 
                arrayListBiConsumer = new BiConsumer<Integer[], 
                                        ArrayList<Integer>>() {
                @Override
                public void accept(Integer[] integers, 
                        ArrayList<Integer> arrayList) {
                    arrayList.addAll(Arrays.asList(integers));
                }
            };
            arrayListBiConsumer.accept(arr, list);
            //? Step Two: Collect Lists
            
    }
}
```
</li>
<li><b><format color="CornFlowerBlue">Step 4: Final Transformation (finisher)</format></b>
After all elements are processed and combined, <code>finisher()</code> is called
Transforms the final accumulation container (type <code>A</code>) into the result (type <code>R</code>)
This step is optional - some collectors don't need transformation
Example: <code>R result = finisher.apply(container);</code> </li> </list>
</tab>
</tabs>
</procedure>


### Procedural Programming