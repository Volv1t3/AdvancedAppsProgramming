package PracticeModule.Stream_API_Practice;

import java.security.SecureRandom;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class StreamGenerationPatterns {
    public static void main(String[] args) {
        //! Lets define an empty stream of type Integer
        Stream<Integer> emptyIntegerStream = Stream.<Integer>empty(); // Explicit Definition
        Stream<Integer> emptyIntegerStreamTwo = Stream.empty();

        //! Lets define a set of String streams of some values
        Stream<String> stringStreamTwo = Stream.<String>of("Hello", "World",
                                                           "Java", "Programming");
        Stream<String> stringStream = Stream.of("Hello", "World");
        Stream<String> stringStream1 = Stream.of("Hello");
        Stream<String> stringStream2 = Stream.ofNullable(null); // returns an empty Stream since
                                       //input is null

        //! Lets Define a set of Double Streams using the builder method
        Stream<Double> doubleStream = Stream.<Double>builder()
                .add(12.5) // These add methods allow us to imperatively add elements to a stream.
                .add(12.6) //The line Stream.<Double>builder() is used to tell the compiler
                .add(12.7) // to check for this type in the rest of the args.
                .build(); // Constructs the stream of said type with the added values.
        Stream.Builder<Double> doubleStreamTwo =
                Stream.<Double>builder(); // This declaration says that this stream is not yet build
        doubleStreamTwo.accept(12.5); //Accepting a value is not a chainable operation
        Stream<Double> actualStreamTwo = doubleStreamTwo.build();

        //! Lets build a series of Integer streams using the generate method
        Stream<Integer> integerStream = Stream.generate(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return (new SecureRandom().nextInt());
            }
        }); //This effectively creates an infinite random stream (unlike iterate this is actually
            // inifinite, although not allocated until operated upon

        //! Lets build an integer stream using the iterate methods
        Stream<Integer> integerStream1 = Stream.iterate(0, new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 1;
            }
        }); //This is an infinite stream starting from 0 and adding one to each consecutive element
        Stream<Integer> integerStream2 = Stream.iterate(0, new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer < 10;
            }
        }, new UnaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 2;
            }
        }); // This is a non infinite stream defined by all elements in steps of two that are less
            // than 10.

        //! Printing all elements sotred in the streams
        System.out.println("emptyIntegerStream = " + emptyIntegerStream.toList());
        System.out.println("emptyIntegerStreamTwo = " + emptyIntegerStreamTwo.toList());
        System.out.println("stringStreamTwo = " + stringStreamTwo.toList());
        System.out.println("stringStream = " + stringStream.toList());
        System.out.println("stringStream1 = " + stringStream1.toList());
        System.out.println("stringStream2 = " + stringStream2.toList());
        System.out.println("doubleStream = " + doubleStream.toList());
        System.out.println("actualStreamTwo = " + actualStreamTwo.toList());
        //! Notice how in here we had to use the limit operation as these would be far too large
        //! if not cut (as they are effectively infinite before allocation (although due to lazy
        //! generation we generally don't worry about that)
        System.out.println("integerStream = " + integerStream.limit(10).toList());
        System.out.println("integerStream1 = " + integerStream1.limit(10).toList());
        System.out.println("integerStream2 = " + integerStream2.toList());


    }
}
