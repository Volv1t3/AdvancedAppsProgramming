package PracticeModule.Stream_API_Practice;

import java.util.Arrays;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * @author : Santiago Arellano
 * @Date : 18th Jan, 2025
 * @Email: sarellanoj@estud.usfq.edu.ec
 * @Description : This file contains information about Java Stream API basics, such as stream
 * creating and manipulation
 * from the basic generator classes and interfaces defined for primitives
 */
public class StreamAPI_Basics {

    private static final int[] arrayOfIntegers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final long[] arrayOfLongs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static final double[] arrayOfDoubles = {1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5, 10.5};


    public static void main(String[] args) {
        IntStream streamOneThroughTen = generateIntStreamInRange(10, (byte) 1);
        streamOneThroughTen.forEach(System.out::println);
        //! Lets create some more objects, one from 127 to 1200
        IntStream streamOneHundredThroughOneThousand = generateIntStreamInRange(1000, (byte) 127);
        streamOneHundredThroughOneThousand
                .limit(100)
                .filter(new IntPredicate() {
                        @Override
                        public boolean test(int value) {
                            return value % 3 == 0;
                        }
                    }
                )/*! Printing conditional without accessing
                /*! external state (Stateless pretty printing)*/
                .forEachOrdered(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        System.out.print(value+ " ");
                        if (value % 10 == 0){
                            System.out.println();
                        }
                    }
                }
                );
        //! Lets create another one, upper bound, lowerbound and conditional of only those that
        // are higher than 300 can go in
        IntStream streamConditionedOne = generateIntStreamWithComplexIteration(100, 500, new IntPredicate() {
            @Override
            public boolean test(int value) {
                return value < 300;
            }
        });
        streamConditionedOne.limit(10).forEachOrdered(System.out::println);

    }

    /**
     * This method takes in an upperbound for IntStream generation, and a seed to be taken as a
     * starting value. The idea is that the seed parameter determines the base value and it
     * should be smaller than the upperBound param. If this rule is not held <code>an
     * exception</code> will be thrown.
     * @param upperBound: the upperbound for the IntStream generation
     * @param seed: the seed to be taken as a starting value
     * @return : an IntStream object holding elements beginning from seed all the way to upperBound.
     * @throws IllegalStateException if the seed value is bigger than the upper bound
     */
    private static IntStream generateIntStreamInRange(Integer upperBound, Byte seed) throws
            IllegalStateException {

        if (seed.intValue() > upperBound){
            throw new IllegalStateException("The seed value is bigger than the upper bound");
        }
        return IntStream.iterate( seed,
                /*! educing seed byte[] to an integer value*/
                                  operand -> operand+ 1
                                ).limit(upperBound);
    }

    /**
     * The following method allows the user to create an IntStream object based on a lower bound
     * and an upper bound, as well as an IntPredicate to control the values of the generated ones
     * that are allowed to go through
     * @param lowerBound: the lower bound for the IntStream generation
     * @param upperBound: the upper bound for the IntStream generation
     * @param conditionalCreation: the IntPredicate to control the values of the generated ones
     *                             that are allowed to go through
     * @return : an IntStream object holding elements beginning from seed all the way to upperBound.
     * @throws IllegalStateException: if the seed value is bigger than the upper bound
     */
    private static IntStream generateIntStreamWithComplexIteration(Integer lowerBound,
                                                           Integer upperBound,
                                                           IntPredicate conditionalCreation)
    throws IllegalStateException{
        if (upperBound < lowerBound){
            throw new IllegalStateException("The upperBound value is bigger than the lowerBound");
        }
        return IntStream.iterate(lowerBound, operand -> operand + 1)
                .limit(upperBound).filter(conditionalCreation);

    }

    public static LongStream generateLongSequenceWithRange(Integer lowerBound, Integer upperBound
            , LongPredicate conditionalCreation) throws IllegalStateException{
        if (upperBound < lowerBound){
            throw new IllegalStateException("The upperBound value is bigger than the lowerBound");
        }

        return LongStream.rangeClosed(lowerBound, upperBound).filter(conditionalCreation);
    }
}
