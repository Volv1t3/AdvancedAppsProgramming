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
<procedure title="Java Stream API - Use Cases" collapsible="true">
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
<procedure title="Java Collectors API - Basics" collapsible="true">
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
<procedure title="Java Collection APi—Type parameters" collapsible="true">
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
            //? Step Three: Combiner for parallel streams
            Integer[] arr2 = {6,7,8,9,10};
            var list2 = supplierForIntArrayList.get();
            arrayListBiConsumer.accept(arr2, list2);
            BinaryOperator<ArrayList<Integer>> binaryOperator = 
            (list1, list3) -> {
                list1.addAll(list3);
                return list1;
            };
            var finalList = binaryOperator.apply(list, list2);
    }
}
```
</li>
<li><b><format color="CornFlowerBlue">Step 4: Final Transformation (finisher)</format></b>
After all elements are processed and combined, <code>finisher()</code> is called
Transforms the final accumulation container (type <code>A</code>) into the result (type <code>R</code>)
This step is optional—some collectors don't need transformation.<br/>
The following example shows how this works

```Java
//! Example implementation of a supplier method
import java.util.*;
import java.util.function.Supplier;
public class Example{

    public static void main(String[] args){
    
    //! Ejemplo de collector de un Integer[] hacia ArrayList<Integer>
            //? Step One: Supplier for the holder
            Supplier<ArrayList<Integer>> supplierForIntArrayList = 
                                                    ArrayList::new;
            ArrayList<Integer> list = supplierForIntArrayList.get();
            //?Step Two: Accumulator for transformation
            Integer[] arr = {1,2,3,4,5};
            BiConsumer<Integer[], ArrayList<Integer>> 
                arrayListBiConsumer = new 
                    BiConsumer<Integer[], ArrayList<Integer>>() {
                @Override
                public void accept(Integer[] integers, 
                        ArrayList<Integer> arrayList) {
                    arrayList.addAll(Arrays.asList(integers));
                }
            };
            arrayListBiConsumer.accept(arr, list);
            //? Step Three: Combiner for parallel streams
            Integer[] arr2 = {6,7,8,9,10};
            var list2 = supplierForIntArrayList.get();
            arrayListBiConsumer.accept(arr2, list2);
            BinaryOperator<ArrayList<Integer>> binaryOperator = 
                (list1, list3) -> {
                list1.addAll(list3);
                return list1;
            };
            var finalList = binaryOperator.apply(list, list2);
            //? Step Four: Finisher for transformation
            finalList.forEach(System.out::println);
    }
```
</li>
</list>
<p>Having reviewed all these sections code, the following example showcases the simplicity of doing the same 
thing, to transform an Array stream of two arrays into a single ArrayList</p>

<procedure>

```Java

public class Example{
    public static void main(String[] args){
        Integer[] arr1 = {1,2,3,4,5,};
        Integer[] arr2  ={6,7,8,9,10};

        ArrayList<Integer> finalList = Stream.builder()
                .add(arr1)
                .add(arr2)
                .build()
                .collect(new Supplier<ArrayList<Integer>>() {
                             @Override
                             public ArrayList<Integer> get() {
                                 return new ArrayList<>();
                             }
                         },
                        new BiConsumer<ArrayList<Integer>, Object>() {
                            @Override
                            public void accept(ArrayList<Integer> 
                                            integers, Object o) {
                                integers.addAll(
                                    Arrays.asList( (Integer[])o));
                            }
                        }
                        ,
                        new BiConsumer<ArrayList<Integer>, 
                                   ArrayList<Integer>>() {
                            @Override
                            public void accept(ArrayList<Integer> 
                                    integers, 
                                    ArrayList<Integer> integers2) {
                                integers.addAll(integers2);
                            }
                        });
        System.out.println("finalList = " + finalList);
    }
}
```
</procedure>
</tab>
</tabs>
</procedure>
<p>The Collector implementing class <code>Collectors</code> provides a series of already defined implementations for 
Collector methods. Methods such as <code>Collectors.toCollection(...), Collector.toList(...) or Collectors.joining(...)
</code>, are some of the example implementations that allow you to convert a stream to a collection, to a list or 
joining a string based on a separator.
<br/><br/>
The following section will provide an overlook of all the methods existing in the interface, with an implementation 
example based on anonymous classes rather than lambdas such that everyone can understand the way this works. </p>

<procedure title="Collectors Methods" id="collectors_methods" collapsible="true">
<list columns="1">
<li><b><format color="CornFlowerBlue">static {T} Collector{T,?,Double} averagingDouble(ToDoubleFunction{? super T} mapper)</format></b>: This method, as defined in the Java API specification for Java 21.
<br/>
<p><i>" Returns a Collector that produces the arithmetic mean of a double-valued function applied to the input 
elements.</i></p>
<br/>
There are three extra considerations to do here <list>
<li><b><format color="CornFlowerBlue">If no values are present:</format></b> This method returns <code>0</code>.</li>
<li><b><format color="CornFlowerBlue">Order dependency:</format></b> The average returned value can vary depending upon the order in which values are recorded (order them first).</li>
<li><b><format color="CornFlowerBlue">Handling NaN or large values:</format></b> If there is a NaN or more values than 2^53, the average is truncated to 2^53, leading to potential issues.</li>
</list>
<br/>
An example implementation of this method is the following snippet
<br/>

```Java
var doubleCollector = 
    Collectors.averagingDouble(
        new ToDoubleFunction<String>() {
            @Override
            public double applyAsDouble(String value) {
                System.out.println("value.length() = " 
                + value.length());
                System.out.println("(value.length() * 2.5) = " 
                + (value.length() * 2.5));
                return value.length() * 2.5;
            }
        }
    );
        String[] arr = {"a", "bb", "ccc"};
        //! Finding the average value of the array
        var average = Arrays.stream(arr)
                .collect(doubleCollector);
        System.out.println("average = " + average);
        // Imprime 5 en este caso
```
<br/>
<format color="OrangeRed">OJO:</format> any and all implementations can work with any and all data types, unlike 
explicit Collector definitions, these can be specialized for any type so long as there is a way to convert it to a 
Double.
</li> 
<li><b><format color="CornFlowerBlue">static {T} Collector{T,?,Double} averagingInt(ToIntFunction{? super T} mapper)
</format></b>: This function does <code>not</code> have the issues commonly associated with a double average 
function. Instead <code>it works by using a mapper function to transform inputs to integer values</code>, and then 
finding the average of their sum.
<br/>
An example of this method is defined here
<br/>

```Java
var integerCollector =
                Collectors.averagingInt(
                        new ToIntFunction<Double>() {
                            @Override
                            public int applyAsInt(Double value) {
                                return value.intValue();
                            }
                        }
                );
        Double[] arr3 = {1.0, 2.0, 3.0};
        average = Arrays.stream(arr3).collect(integerCollector);
        System.out.println("average = " + average);
        //! Imprime Dos
```
</li> 
<li><b><format color="CornFlowerBlue">static{T} Collector{T,?, Double} averagingLong(ToLongFunction{? super T} 
mapper)</format></b>: Similarly to its sister methods, the idea of this collector is to collect the data points of a 
stream and transform them into long values whose average can be calculated. <code>This method has no 
security or performance issues like Double averaging</code>

An example implementation could be
<br/>

```Java
var longCollector = 
                Collectors.averagingLong(
                        new ToLongFunction<Double>() {
                            @Override
                            public long applyAsLong(Double value) {
                                return value.longValue();
                            }
                        }
                );
        Double[] arr3 = {1.0, 2.0, 3.0};
        average = Arrays.stream(arr3).collect(longCollector);
        System.out.println("average = " + average);

```
</li> 
</list>
<p>The preceding methods were grouped together due to them being <code>averaging methods</code>, the following 
collectors methods will provide <code>max, min, joining, counting, reducing, filtering, and teeing</code> streams</p>
<code>maxBy, minBy, joining, counting, reducing, filtering, teeing </code>
<list>
<li><b><format color="CornFlowerBlue">static {T} Collector{T,?,Optional{T}} maxBy(Comparator{? super T} 
comparator)</format></b>: According to the documentation specified by the Java API, maxBy returns a Collector, 
that produce the <code>maximal element according to a given comparator</code>. This then means that the Collector 
iterates over the entire collection, reducing it to the single most largest value.

An example of this would be
<br/>

```Java
var maxCollector = 
                Collectors.maxBy(
                        new Comparator<Integer>() {
                            @Override
                            public int compare(Integer o1, Integer o2) {
                                return o1.compareTo(o2);
                            }
                        }
                );
        Integer[] arr = {5, 2, 8, 1, 9, 3, 7};
        Optional<Integer> maxValue = 
            Arrays.stream(arr).collect(maxCollector);
        
        if (maxValue.isPresent()) {
            System.out.println("Maximum value = " + maxValue.get());
        } else {
            System.out.println("No value present");
        }

```
</li> 
<li><b><format color="CornFlowerBlue">static {T} Collector{T,?,Optional{T} minBy(Comparator{? super T} comparator)
</format></b>: As with its counterpart for finding the largest value, the idea of <code>reducing the input 
stream until it finds (through a Collector operation), the smallest value</code>, remains the same in this method. 
Of course, it is important to note that in general, these methods return an Optional, given that they are optimized 
for safe empty iteration.

An example of this would be
<br/>

```Java
var minCollector = 
                Collectors.minBy(
                        new Comparator<Integer>() {
                            @Override
                            public int compare(Integer o1, Integer o2) {
                                return o1.compareTo(o2);
                            }
                        }
                );
        Integer[] arr = {5, 2, 8, 1, 9, 3, 7};
        Optional<Integer> minValue = Arrays.stream(arr).collect(minCollector);
        
        if (minValue.isPresent()) {
            System.out.println("Minimum value = " + minValue.get());
        } else {
            System.out.println("No value present");
        }

```
</li> 
<li><b><format color="CornFlowerBlue">Joining Methods</format></b>: This method, is quite different to the others we 
have studied before, as it has an internal listing of <code>other methods</code> that do effectively, the same, but 
modify the contents differently. These series of methods are meant to <code>concatenate input elements into a 
String in encounter order</code>.

However, the main difference between them is the way they handle delimiters, and prefix and suffix definitions. For 
this reason this listing shows each of them in a general approach and presents a full example of their use in the end.

<list>
<li><format color="Orange">static Collector{CharSequence, ?, String} joining()</format>: Is a method that allows 
the user to <code>join input elements together (calling their toString() methods effectively) in input order, with 
no delimiter</code></li>

<li><format color="Orange">static Collector{CharSequence, ?, String} joining(CharSequence delimiter)</format>: 
Joins elements together with the specified <code>delimiter inserted between each element</code>. For example, 
joining with "," would result in "element1,element2,element3"</li>

<li><format color="Orange">static Collector{CharSequence, ?, String} joining(CharSequence delimiter, 
CharSequence prefix, CharSequence suffix)</format>: Joins elements together with the specified <code>delimiter 
between elements, and wraps the entire result with the given prefix and suffix</code>. For example, with 
delimiter ",", prefix "[" and suffix "]" would result in "[element1,element2,element3]"</li>
</list>
An example usage of these would then be
<br/>

```Java
var joiningSimple = 
                Collectors.joining();
        
var joiningDelimiter = 
                Collectors.joining(
                        "-|-"
                );

var joiningDelimiterPrefixSuffix = 
                Collectors.joining(
                        "-|-",
                        "[-",
                        "-]"
                );

String[] strings = {"String1", "String2", "String3"};
        
String resultSimple = Arrays.stream(strings)
                    .collect(joiningSimple);
String resultDelimiter = Arrays
                         .stream(strings)
                         .collect(joiningDelimiter);
String resultComplete = Arrays
                        .stream(strings)
                        .collect(
                        joiningDelimiterPrefixSuffix);

System.out.println("Simple joining: " + resultSimple);
System.out.println("With delimiter: " + resultDelimiter);
System.out.println("With delimiter, prefix and suffix: " 
                                       + resultComplete);

```
</li> 
<li><b><format color="CornFlowerBlue">static {T} Collector{T,?, Long} counting()</format></b>: is a simple method 
which in the end returns a Collector that accepts elements of type <code>T</code>, and <code>counts the number 
of input elements in the Stream</code>.

An example implementation would be
<br/>

```Java
var simpleCounter = 
                Collectors.counting();

var groupedCounter = 
                Collectors.groupingBy(
                        new Function<String, Integer>() {
                            @Override
                            public String apply(String str) {
                                return str.length() + "";
                            }
                        },
                        Collectors.counting()
                );
String[] words = {"cat", "dog", "elephant", "bird", 
                 "lion", "tiger", "bear"};
        
Long totalCount = Arrays.stream(words)
                               .collect(simpleCounter);                             
Map<String, Long> countByLength = Arrays.stream(words)
                                  .collect(groupedCounter);
System.out.println("Simple count of all elements: " 
                                    + totalCount);
System.out.println("Count grouped by string length: " 
                                    + countByLength);

```
</li> 
<li><b><format color="CornFlowerBlue">Reducing Methods</format></b>: Another family of methods that we can find in 
our Collectors class is those that reduce streams of elements at the end (as terminal operations). These methods 
have three main implementations that are to be discussed below 
<br/>
<list>
<li><format color="Orange">static {T} Collector{T,?, Optional{T}} reducing(BinaryOperator{T} op)</format>: is a 
method that performs <code>a reduction on its input elements under a specified BinaryOperator</code>. The aim of this 
class is then to for example be used in <code>grouping operations</code>, such that they can be used as reductions 
in SQL-like group by operations. In general, these can be used anywhere, but for simple reductions, it is 
<code>recommended to use Stream.reduce(BinaryOperator) instead</code>.</li>

<li><format color="Orange">static {T} Collector{T,?,T} reducing(T identity, BinaryOperator{T} op)</format>: 
performs <code>a reduction operation using the provided identity value as the initial value and the BinaryOperator 
for combining elements</code>. Unlike the previous version, this returns a direct value of type T instead of an 
Optional since the identity value ensures a result will always exist.</li>

<li><format color="Orange">static {T,U} Collector{T,?,U} reducing(U identity, Function{? super T,? extends U} mapper, 
BinaryOperator{U} op)</format>: performs the most complex reduction, where it <code>first maps the input elements 
to a different type U using the mapper function, then performs the reduction using the provided identity value and 
BinaryOperator</code>. This is useful when you need to transform elements before reducing them, combining mapping 
and reduction into a single operation.</li>
</list>

An example implementation of these methods would be the following

```Java
var simpleReducer = 
                Collectors.reducing(
                        new BinaryOperator<String>() {
                            @Override
                            public String apply(String s1, 
                                             String s2) {
                                return s1.length() > 
                                       s2.length() ? s1 : s2;
                            }
                        }
                );

var identityReducer = 
                Collectors.reducing(
                        "DEFAULT",
                        new BinaryOperator<String>() {
                            @Override
                            public String apply(String s1, 
                                                String s2) {
                                return s1.length() 
                                       > s2.length() ? s1 : s2;
                            }
                        }
                );

var mappingReducer = 
                Collectors.reducing(
                        0,
                        new Function<String, Integer>() {
                            @Override
                            public Integer apply(String s) {
                                return s.length();
                            }
                        },
                        new BinaryOperator<Integer>() {
                            @Override
                            public Integer apply(Integer i1, 
                                               Integer i2) {
                                return Math.max(i1, i2);
                            }
                        }
                );

// Complex scenario: Group words by their first letter and 
// find the longest word in each group
String[] words = {"apple", "banana", "ant", 
                   "bear", "cat", "cherry"};
        
Map<Character, Optional<String>> groupedLongestWordsOptional = 
        Arrays.stream(words)
             .collect(Collectors.groupingBy(
                     str -> str.charAt(0),
                     simpleReducer
             ));

Map<Character, String> groupedLongestWordsWithDefault = 
        Arrays.stream(words)
             .collect(Collectors.groupingBy(
                       str -> str.charAt(0),
                       identityReducer
                  ));

Map<Character, Integer> groupedMaxLengths =     
        Arrays.stream(words)
             .collect(Collectors.groupingBy(
                      str -> str.charAt(0),
                       mappingReducer
             ));

System.out.println("Longest words by letter (Optional): " 
                            + groupedLongestWordsOptional);
System.out.println("Longest words by letter (with default): " 
                            + groupedLongestWordsWithDefault);
System.out.println("Max word length by letter: " 
                                         + groupedMaxLengths);

```
Which would output 
```Markdown
Longest words by letter (Optional): {a=Optional[apple], b=Optional[banana], c=Optional[cherry]}
Longest words by letter (with default): {a=apple, b=banana, c=cherry}
Max word length by letter: {a=5, b=6, c=6}

```
</li> 
<li><b><format color="CornFlowerBlue">static {T,A,R}> Collector{T,?,R} filtering (Predicate{? super T} 
predicate, Collector{? super T, A, R} downstream)</format></b>: is a method that allows you to <code>adapt 
a Collector to one accepting elements of the same type T by applying the predicate to each input element, 
and only accumulating if the predicate returns true.</code> This then means that this method can be used to further 
filter a set of data points passed into a stream. For example, we can use it to filter and then aggregate 
employees based on gender, salary, department, etc.

An example of this method would be.
```Java
static class Employee {
        private String name;
        private String department;
        private double salary;

        public Employee(String name, String 
                department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
    }


    public static void method2(){
        List<Employee> employees = Arrays.asList(
                new Employee("John", "IT", 60000),
                new Employee("Alice", "IT", 45000),
                new Employee("Bob", "HR", 55000),
                new Employee("Carol", "HR", 40000),
                new Employee("Dave", "Finance", 70000),
                new Employee("Eve", "Finance", 48000)
        );

        Map<String, List<String>> highestSalaryByDepartment =
                employees.stream()
                        .collect(Collectors.filtering(
                            new Predicate<Employee>() {
                            @Override
                            public boolean test(Employee employee) {
                                return employee.getSalary() 
                                                    > 50_000;
                            }
                        },
                        Collectors.groupingBy(
                            new Function<Employee, String>() {
                            @Override
                            public String apply(Employee employee) {
                                return employee.getDepartment();
                            }
                        }, Collectors.mapping(new 
                            Function<Employee, String>() {
                            @Override
                            public String apply(Employee employee) {
                                return employee.getName() + "($" + 
                                employee.getSalary() + ")";
                            }
                        }, Collectors.toList()))));
        System.out.println("highestSalaryByDepartment = " 
                            + highestSalaryByDepartment);
    }
```
<p>The previous example, not only showed a clear way on how to implement this filtering method in a complete way, it 
additionally showed various downstream functions of the stream classes. Specifically those related to grouping and 
mapping, methods which will be discussed in this section too.
</p>
</li> 
<li><b><format color="CornFlowerBlue">static {T,R1,R2,R} Collector{T,?,R} teeing (Collector{? supr T,?,R1} 
downstream1, Collector{? super T, ?, R2} downstream2, BiFunction{? super R1, ? super R2, R} merger) </format></b>: 
Based on the signature of this method, it would be first beneficial for us to define the type parameters that it 
takes, what these parameters it takes represent and what is the expected output of this method. For this the 
following listing is presented
<br/>
<list>
<li><format color="Orange">{T}</format>: parameter type that represents the type of the input items</li>
<li><format color="Orange">{R1}</format>: parameter that represents the result type of the first Collector</li>
<li><format color="Orange">{R2}</format>: parameter that represents the result type of the second Collector</li>
<li><format color="Orange">{R}</format>: parameter that represents the final result type after merging R1 and R2</li>
</list>
<br/>
This method, as defined in the Java doc <code>returns a Colector that is a composite of two downstream collectors. 
Every element is passed to the resulting collecotr is processed by both downstream collectors, then their results 
are merged using he specified merge function into the final result
</code>
<br/>
<p>The idea of this method follows these sequential steps:</p>

<list>
<li><b>Step 1—Supplier:</b> <code>Creates a result container</code> that contains the result containers from both collectors</li>

<li><b>Step 2—Accumulator:</b> <code>Processes each input element</code> by:
    <list>
    <li>Calling the first collector's accumulator with its container and the input</li>
    <li>Calling the second collector's accumulator with its container and the input</li>
    </list>
</li>

<li><b>Step 3—Combiner:</b> <code>Merges partial results</code> by:
    <list>
    <li>Calling each collector's combiner with their respective result containers</li>
    <li>Combining the partial results from parallel processing</li>
    </list>
</li>

<li><b>Step 4—Finisher:</b> <code>Produces the final result</code> by:
    <list>
    <li>Calling each collector's finisher with their result containers</li>
    <li>Using the merger function to combine both results into the final output</li>
    </list>
</li>
</list>
<p>Based on this explanation then it is straightforward to see that this is simply a double application of Collectors 
on a 
single input data frame and then manipulating them to combine these values into a single result.<br/><br/>
The following example showcases the use of this method</p>
<br/>

```Java
List<Employee> employees = Arrays.asList(
                new Employee("Aaron", "IT", 60000),
                new Employee("Alice", "IT", 45000),
                new Employee("Bob", "HR", 55000),
                new Employee("Carol", "HR", 40000),
                new Employee("Dave", "Finance", 70000),
                new Employee("Eve", "Finance", 48000)
        );

        Map<String, List<String>> salariesByLetter =
                employees.stream()
                        .collect(
                                Collectors.teeing(
                                        Collectors.groupingBy(
                                                new Function<Employee, String>() {
                                                    @Override
                                                    public String apply(Employee employee) {
                                                        return String.valueOf(employee.getName().charAt(0));
                                                    }
                                                }
                                        ),
                                        Collectors.groupingBy(
                                                new Function<Employee, String>() {
                                                    @Override
                                                    public String apply(Employee employee) {
                                                        return String.valueOf(employee.getName().charAt(0));
                                                    }
                                                },
                                                Collectors.mapping(
                                                        new Function<Employee, String>() {
                                                            @Override
                                                            public String apply(Employee employee) {
                                                                return "Department=" + employee.getDepartment() + ", Salary=" + employee.getSalary();
                                                            }
                                                        },
                                                        Collectors.toList()
                                                )
                                        ),
                                        (groupedByLetter, mappedSalaries) -> {
                                            Map<String, List<String>> result = new HashMap<>();

                                            groupedByLetter.forEach((key, employeesInGroup)
                                                    -> {
                                                result.put(key, mappedSalaries.get(key));
                                            });

                                            return result;
                                        }));
        System.out.println("salariesByLetter = " + salariesByLetter);

```
</li>  
</list>
<code>GroupingBy Methods</code>
<p>The grouping by methods are some of the most common methods used in the Stream API, they have also been scattered 
widely around the examples presented above. These have included mentions of the groupingBy methods in some simple 
and yet verbose ways such that they can be easily understood, however now is their turn for an in depth analysis.
</p>
<list>
<li><b><format color="CornFlowerBlue">GroupingBy Methods</format></b>: 
<list>
<li><format color="Orange">static {T,K} Collector {T, ? ConcurrentMap{K, List{T}}} groupingByConcurrent
(Function{? super T, ? extends K} classifier)</format>: <code>returns a concurrent Collection implementing a 
cascade "group by" operation on input elements of type T, grouping elements according to a classification 
function, and then performing a reduction operation on the values associated with a given key using the 
specified downstream Collector.</code>
<br/>
This the means that this method concurrently applies a classifier to group elements based on a criteria. This 
criteria takes the input type of the stream, <code>(e.g., an Employee class abstraction)</code>, and extracts 
content out of it through an anoymous class or lambda to classify by. 
<br/>
The resulting output of this group by method is a ConcurrentMap implementation, that can be taken and returned as a 
normal collector operation, or operated further downstream.
<br/>
An example of this would be.

<procedure collapsible="true" title="Simple GroupByConcurrent Examples">

```Java

        class Employee {
            private String name;
            private String department;
            private double salary;

            public Employee(String name,
                            String department,
                            double salary) {
                this.name = name;
                this.department = department;
                this.salary = salary;
            }

            // Getters
            public String getName()
                         { return name; }
            public String getDepartment()
                         { return department; }
            public double getSalary()
                         { return salary; }
        }

                List<Employee> employees = Arrays.asList(
                        new Employee("Alice", "HR", 51000),
                        new Employee("Bob", "IT", 60000),
                        new Employee("Charlie", "HR", 45000),
                        new Employee("David", "IT", 65000),
                        new Employee("Eve", "Finance", 55000)
                );

                // Example 1: Simple groupingByConcurrent by department
                ConcurrentMap<String, List<Employee>> byDepartment = employees.parallelStream()
                        .collect(Collectors.groupingByConcurrent(Employee::getDepartment));

                // Example 2: Complex grouping using teeing
                record DepartmentStats(List<String> highPaidEmployees, Double averageSalary) {}

                ConcurrentMap<String, DepartmentStats> departmentStats = employees.parallelStream()
                        .collect(Collectors.groupingByConcurrent(
                                new Function<Employee, String>() {
                                    @Override
                                    public String apply(Employee employee) {
                                        return employee.getDepartment();
                                    }
                                },
                                Collectors.teeing(
                                        Collectors.filtering(
                                                new Predicate<Employee>() {
                                                    @Override
                                                    public boolean test(Employee employee) {
                                                        return employee.getSalary() > 50_000;
                                                    }
                                                },
                                                Collectors.mapping(
                                                        new Function<Employee, String>() {
                                                            @Override
                                                            public String apply(Employee employee) {
                                                                return employee.getName();
                                                            }
                                                        }, Collectors.toList())
                                        ),
                                        Collectors.averagingDouble(new ToDoubleFunction<Employee>() {
                                            @Override
                                            public double applyAsDouble(Employee value) {
                                                return value.getSalary();
                                            }
                                        }),
                                        DepartmentStats::new
                                )
                        ));

                // Example 3: GroupingByConcurrent with downstream collector
                ConcurrentMap<String, Double> departmentTotalSalary = employees.parallelStream()
                        .collect(Collectors.groupingByConcurrent(
                                Employee::getDepartment,
                                Collectors.collectingAndThen(
                                        Collectors.summingDouble(Employee::getSalary),
                                        total -> Math.round(total * 100.0) / 100.0
                                )
                        ));

                // Print results

                System.out.println("Simple Grouping by Department:");
                byDepartment.forEach((dept, emps) ->
                        System.out.println(dept + ": " +
                                emps.stream().map(Employee::getName)
                                        .collect(Collectors.toList())));

                System.out.println("\nComplex Grouping with Stats:");
                departmentStats.forEach((dept, stats) ->
                        System.out.println(dept + " -> High paid: " + stats.highPaidEmployees() +
                                ", Avg Salary: " + stats.averageSalary()));

                System.out.println("\nGrouping with Total Salaries:");
                departmentTotalSalary.forEach((dept, total) ->
                        System.out.println(dept + ": $" + total));

```
</procedure>
</li>
<li><format color="Orange">static {T,K} Collector {T, ?, Map{K, List{T}}} groupingBy
(Function{? super T, ? extends K} classifier)</format>: <code>returns a Collection implementing a 
cascade "group by" operation on input elements of type T, grouping these elements into a Map where the 
keys are derived using the classification function.</code>
<br/>
This means that this method applies a classifier to group elements based on a criteria. 
This criteria takes the input type of the stream, <code>(e.g., an Employee class abstraction)</code>, and extracts 
content out of it through an anonymous class or lambda to classify by.  
<br/>
The resulting output of this `groupingBy` method is a `Map` implementation, where the grouped elements can be 
collected into lists, manipulated further using downstream collectors, or transformed to derive additional information.
<br/>
An example of this would be:

<procedure collapsible="true" title="Simple GroupingBy Examples">

```Java

        class Employee {
            private String name;
            private String department;
            private double salary;

            public Employee(String name, String department, double salary) {
                this.name = name;
                this.department = department;
                this.salary = salary;
            }

            // Getters
            public String getName() {
                return name;
            }

            public String getDepartment() {
                return department;
            }

            public double getSalary() {
                return salary;
            }
        }

        public class GroupingExample {
            public static void main(String[] args) {
                List<Employee> employees = Arrays.asList(
                        new Employee("Alice", "HR", 51000),
                        new Employee("Bob", "IT", 60000),
                        new Employee("Charlie", "HR", 45000),
                        new Employee("David", "IT", 65000),
                        new Employee("Eve", "Finance", 55000)
                );

                // Example 1: Simple groupingBy by department
                Map<String, List<Employee>> byDepartment = employees.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment));

                System.out.println("Grouping by Department:");
                byDepartment.forEach((dept, emps) ->
                        System.out.println(dept + ": " +
                                emps.stream().map(Employee::getName).collect(Collectors.toList())));
            }
        }

```
</procedure>

<procedure collapsible="true" title="Grouping With Additional Transformations">

```Java
        // Example 2: Grouping by department and collecting employee names
        Map<String, List<String>> employeesByName = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.mapping(Employee::getName, Collectors.toList())
                ));

        System.out.println("\nGrouping employee names by department:");
        employeesByName.forEach((dept, names) ->
                System.out.println(dept + ": " + names));
```
**Output:**
Grouping employee names by department: HR: [Alice, Charlie] IT: [Bob, David] Finance: [Eve]

</procedure>

<procedure collapsible="true" title="Group by with Aggregated Statistics">

```Java

        // Example 3: Group by department and calculate total salary for each group
        Map<String, Double> departmentTotalSalary = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.summingDouble(Employee::getSalary)
                ));

        System.out.println("\nGrouping by Department with Total Salaries:");
        departmentTotalSalary.forEach((dept, total) ->
                System.out.println(dept + ": $" + total));
```
**Output:**
Grouping by Department with Total Salaries: HR: 96000.0 IT:125000.0 Finance: $55000.0
</procedure>

<procedure collapsible="true" title="Grouping with Custom Statistics">

```Java

        // Custom object for storing statistics
        class DepartmentStats {
            private List<String> employeeNames;
            private double averageSalary;

            public DepartmentStats(List<String> employeeNames, double averageSalary) {
                this.employeeNames = employeeNames;
                this.averageSalary = averageSalary;
            }

            @Override
            public String toString() {
                return "Employees: " + employeeNames + ", Avg Salary: $" + averageSalary;
            }
        }

        // Example 4: Group by department and calculate custom statistics
        Map<String, DepartmentStats> departmentStats = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                emps -> new DepartmentStats(
                                        emps.stream().map(Employee::getName).collect(Collectors.toList()),
                                        emps.stream().mapToDouble(Employee::getSalary).average().orElse(0)
                                )
                        )
                ));

        System.out.println("\nComplex Grouping with Custom Stats:");
        departmentStats.forEach((dept, stats) ->
                System.out.println(dept + " -> " + stats));
```
**Output:**
Complex Grouping with Custom Stats: HR -> Employees: [Alice, Charlie], Avg Salary: 48000.0 IT -> Employees: [Bob, David], Avg Salary:62500.0 Finance -> Employees: [Eve], Avg Salary: $55000.0

</procedure>
</li>
<li><b><format color="Orange">static {T,K, D, A,M} Collector{T, ?, Map/ConcurrentMap{K, D}} 
groupingBy/groupingByConcurrent(Function{? super T, ? extends K} classifier, Supplier{M} mapFactory, Collector{? 
super T,A,D} downstream)</format></b>: Although the title of this section was long, it is in an effort of reducing 
the complexity of handling this section. These two methods have the same signature and the same innerworking, only 
that their difference is that <code>one is used in sequential data streaming and the other on concurrent 
data streaming</code>.
<br/>
Particularly, these two are defined a returning a collector that implements a <code>cascade "group by" operation 
on input elements of type T</code>, on which it groups according to a classifier, performing reductions on the 
values given a key specified by the downstream collector.
<br/>
Effectively, this means that these methods are like our normal grouping by, only that these explicitly allow us to 
define the type of map we would like as well as the type of downstream operation that we would like to do.
<br/>
An example of this would be.
<br/>
<procedure title="groupingBy With Three Parameters" collapsible="true">

```Java
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

class Employee {
    private String name;
    private String department;
    private double salary;
    private int yearsOfService;

    public Employee(String name, String department, double salary, int yearsOfService) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.yearsOfService = yearsOfService;
    }

    // Getters
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public int getYearsOfService() { return yearsOfService; }
}

class DepartmentSummary {
    private double totalSalary;
    private int employeeCount;
    private double avgYearsOfService;

    public DepartmentSummary(double totalSalary, int employeeCount, double avgYearsOfService) {
        this.totalSalary = totalSalary;
        this.employeeCount = employeeCount;
        this.avgYearsOfService = avgYearsOfService;
    }

    @Override
    public String toString() {
        return String.format("Total Salary: $%.2f, Employees: %d, Avg Years: %.1f",
                totalSalary, employeeCount, avgYearsOfService);
    }
}

public class AdvancedGroupingExample {
    public static void main(String[] args) {
        // Create a large sample dataset
        List<Employee> employees = Arrays.asList(
            new Employee("Alice", "HR", 50000, 5),
            new Employee("Bob", "IT", 60000, 3),
            new Employee("Charlie", "HR", 45000, 2),
            new Employee("David", "IT", 65000, 7),
            new Employee("Eve", "Finance", 55000, 4),
            new Employee("Frank", "IT", 70000, 6),
            new Employee("Grace", "HR", 52000, 3),
            new Employee("Henry", "Finance", 58000, 5)
        );

        // Example 1: groupingBy with three parameters
        Map<String, Set<Employee>> departmentEmployees = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,                // classifier function
                LinkedHashMap::new,                     // map factory
                Collectors.filtering(                   // downstream collector
                    e -> e.getSalary() > 50000,
                    Collectors.toSet()
                )
            ));

        // Example 2: groupingByConcurrent with three parameters
        ConcurrentMap<String, DepartmentSummary> departmentStats = employees.parallelStream()
            .collect(Collectors.groupingByConcurrent(
                Employee::getDepartment,                // classifier function
                ConcurrentHashMap::new,                // concurrent map factory
                Collectors.collectingAndThen(          // downstream collector
                    Collectors.teeing(
                        Collectors.teeing(
                            Collectors.summingDouble(Employee::getSalary),
                            Collectors.counting(),
                            (salary, count) -> new Pair<>(salary, count)
                        ),
                        Collectors.averagingDouble(Employee::getYearsOfService),
                        (pair, avgYears) -> new DepartmentSummary(
                            pair.first(),
                            (int) pair.second().longValue(),
                            avgYears
                        )
                    ),
                    summary -> summary
                )
            ));

        // Print results
        System.out.println("Grouped Employees (salary > 50000) by Department:");
        departmentEmployees.forEach((dept, emps) -> {
            System.out.println(dept + ":");
            emps.forEach(emp -> System.out.printf("  - %s ($%.2f)%n", 
                emp.getName(), emp.getSalary()));
        });

        System.out.println("\nDepartment Statistics (Concurrent):");
        departmentStats.forEach((dept, stats) -> 
            System.out.println(dept + ": " + stats));
    }
}

// Helper class for paired values
class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T first() { return first; }
    public U second() { return second; }
}

```
</procedure>      
</li> 
</list>

</li> 
</list>
</procedure>


### Procedural Programming