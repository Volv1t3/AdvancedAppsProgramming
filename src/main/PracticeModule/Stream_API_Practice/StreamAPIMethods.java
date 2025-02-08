package PracticeModule.Stream_API_Practice;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class StreamAPIMethods {

    public static void main(String[] args) {
        /*For this class we will be using the employee class defined in the StreamCollectorsAPI
        clas*/
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        StreamCollectorsAPI.extracted(employeeArrayList);
        employeeArrayList
                .forEach(System.out::println);

        /*
         * With that out of the way, welcome to our lesson on operations. We are going to take a
         * look at how to manipulate data, transform data and see some of the stateful and
         * stateless operations that we have seen in Java
         */

        /*
         * Assume that now your boss wants a simple thing to be done, he wants to know how many
         * employees he has that are specifically in the Law department, to do this we use normal
         * stream intermediate operations
         */
        Long countOfEmployeesInLaw =  employeeArrayList.stream()
                .filter(new Predicate<Employee>() {
                    @Override
                    public boolean test(Employee employee) {
                        return employee.getDepartment().equalsIgnoreCase("LAW");
                    }
                }).count();
        System.out.println("countOfEmployeesInLaw = " + countOfEmployeesInLaw);
        /*
         * How about those that are either in law or in IT
         */
        countOfEmployeesInLaw = employeeArrayList.stream()
                .filter((Employee employee) -> {return employee.getDepartment().equalsIgnoreCase(
                        "LAW") || employee.getDepartment().equalsIgnoreCase("IT");})
                .count();
        System.out.println("countOfEmployeesInLaw = " + countOfEmployeesInLaw);

        record nameAndSalary(String name, Double salary){}
        /*
         * What if he now wants you to store both their name and their salaries based on those
         * employees that are in the previous count
         */
        List<nameAndSalary> recordsList = employeeArrayList.stream()
                .filter((Employee employee) -> {return employee.getDepartment().equalsIgnoreCase(
                        "LAW") || employee.getDepartment().equalsIgnoreCase("IT");})
                .map((Employee employee) -> {
                    return new nameAndSalary(employee.getName(), employee.getSalary());
                }).toList();
        System.out.println("recordsList = " + recordsList);
        /*
         * Good, now, lets say your boss wants you to modify the strings in each of these
         * elements, effectively iterating over the contents of the original list to produce an
         * all caps list
         */
        List<Employee> allCapsList = employeeArrayList
                .stream()
                .map(new Function<Employee, Employee>() {
                    @Override
                    public Employee apply(Employee employee) {
                        Employee employee1 = new Employee(employee.getSalary(),
                        (employee.getName().toUpperCase(Locale.ROOT)),
                        (employee.getLastName().toUpperCase(Locale.ROOT)),
                        (employee.getDepartment().toUpperCase(Locale.ROOT)),
                        (employee.getCarBrand().toUpperCase(Locale.ROOT)));
                        return employee1;
                    }
                }).toList();
        System.out.println("allCapsList = " + allCapsList);

        /*
         * Lets assume that your boss wants you to use some intermediate operations, we simply want
         * to grab the employees, find any match that has a salary above 90_000, extract its
         * salary, and convert it to a string!
         */
        Optional<Employee> employeesSalaryAsString = employeeArrayList
                .stream()
                .sorted(new Comparator<Employee>() {
                    @Override
                    public int compare(Employee o1, Employee o2) {
                        return Double.valueOf(o1.getSalary()).compareTo(Double.valueOf(o2.getSalary()));
                    }
                })
                .dropWhile(new Predicate<Employee>() {
                    @Override
                    public boolean test(Employee employee) {
                        return employee.getSalary() < 90_000.0;
                    }
                }).findFirst();
        System.out.println("employeesSalaryAsString = " + (employeesSalaryAsString.isPresent() ?
     employeesSalaryAsString.get() : ""));
        /*
         * Lets say now that your boss wants you to sort his employees by name, and then reduce
         * them by the sum of the values on each character making up their name
         */
        Long employeesNamesAsNumber = employeeArrayList
                .stream()
                .sorted(Comparator.comparing(Employee::getName))
                .peek(System.out::println)
                .reduce(0, new BiFunction<Integer, Employee, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Employee employee) {
                        return integer +
                                Arrays.stream(employee.getName().chars().toArray())
                                        .reduce(0, Integer::sum);
                    }
                },Integer::sum).longValue();
        System.out.println("employeesNamesAsNumber = " + employeesNamesAsNumber);
        /*
         * Lets assume now that your boss wants you to filter all of those values that include a
         * BMW in their brand's car,grab those values, sort them in reverse (by salary), then
         * extract those
         * values whose salary's are higher than 60_000, and then group by department?
         */
        Map<String, List<Employee>> employeesGroupedByDepartmentOne =
                employeeArrayList
                        .stream()
                        .filter((Employee employee) -> {return employee.getCarBrand().equalsIgnoreCase(
                                "BMW");})
                        //.peek(System.out::println)
                        .sorted(Comparator.comparing(Employee::getSalary).reversed())
                        //.peek(System.out::println)
                        .filter((Employee employee) -> {return employee.getSalary() >= 60_000.0;})
                        //.peek(System.out::println)
                        .collect(Collectors.groupingBy(Employee::getDepartment));
        System.out.println("employeesGroupedByDepartmentOne = " + employeesGroupedByDepartmentOne);
        /*As you can see, this is one of the simplest ways to do this, it is quite fun to combine
         these operations in a way that produces meaningful results. The peek operation however,
         that we can remove as it is only here for debugging operations (this is meant to be a
         stateless intermediate operation that can add some stateful behavior but it is not
         recommended.*/
        /*
         * Lets say now that your boss tells you to filter based on department, grab all those
         * that are part of the IT department or the Sales department, grab only their names,
         * sort them based on length and in reverse, and then group them, first by the first
         * letter of their name, and then internally sort the list of names from longest to
         * shortest
         */
        Map<Character, List<String>> employeesGroupedByDepartmentTwo =
                employeeArrayList
                        .stream()
                        .filter((Employee emp) -> { return emp.getDepartment().equalsIgnoreCase(
                                "IT") || emp.getDepartment().equalsIgnoreCase("SALES");})
                        .map(Employee::getName)
                        .sorted(Comparator.comparing(new Function<String, Integer>() {
                            @Override
                            public Integer apply(String s) {
                                return s.length();
                            }
                        }).reversed())
                        .collect(Collectors.groupingBy(new Function<String, Character>() {
                            @Override
                            public Character apply(String s) {
                                return s.charAt(0);
                            }
                        }, Collectors.toList()));
        /*Here we had to break down the query into two. Using normal collectors API methods it is
         possible to create these lists, but it would require discarding half of the output of
         the first groupingBy method as it returns a Map<Character, List<String>> on its own, and
          the second part of the teeing would need to use a toList method to make this work.
          Effectively, the merger method would help us here but I do not think it is worth it. It
           is much better visibly and for education purposes to see both collectors, Stream and
           collection Streams working together*/
        employeesGroupedByDepartmentTwo
                .values()
                .forEach(list -> list.sort(Comparator.reverseOrder()));
        employeesGroupedByDepartmentTwo.entrySet()
                .stream().forEachOrdered(new Consumer<Map.Entry<Character, List<String>>>() {
            @Override
            public void accept(Map.Entry<Character, List<String>> characterListEntry) {
                System.out.println(characterListEntry.getKey() + "\n");
                characterListEntry.getValue().forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println("\t" + s);
                    }
                });
            }
        });

        /*
         * Lets say that your boss now wants you to introduce some skipping logic, he wants you
         * to sort his employees by their department, ascending, and then skip three elements
         * before picking it and transforming it into a String representing its name and surname
         * concatenated. Once you have this, he wants you to store it and print it to the screen.
         */
        String employeeConcatented = employeeArrayList
                .stream()
                .sorted(Comparator.comparing(Employee::getDepartment))
                .skip(3)
                .limit(1) //This limit operation reduces our output to one singular employee
                .map(new Function<Employee, String>() {
                    @Override
                    public String apply(Employee employee) {
                        return employee.getName() + employee.getLastName();
                    }
                })
                // We do this little trick since we know we have a single employee form the list
                .toList().getFirst();
        System.out.println("employeeConcatented = " + employeeConcatented);

        /*
         * Consider now that your boss wants you to grab each employee, reduce them to a number
         * based on the length of their name times the value of their salary, and them using an
         * DoubleStream to gather summary statistics information
         */
        DoubleSummaryStatistics statistics = employeeArrayList
                .stream()
                .mapToDouble(new ToDoubleFunction<Employee>() {
                    @Override
                    public double applyAsDouble(Employee value) {
                        return value.getName().length() * value.getSalary();
                    }
                })
                .summaryStatistics();
        System.out.println("statistics = " + statistics);

        /*
         * Consider now that your boss wants the same thing, but this time he wants you to
         * concatenate their name twice and the last name thrice and create a new list of five
         * employees with this method. Additionally, he wants you to create a second list where
         * you do the transformations and then transform back to longs based on their lengths
         */
        List<String> randomizedConcatenations = employeeArrayList
                .stream()
                .limit(5)
                .mapMulti(new BiConsumer<Employee, Consumer<String>>() {
                    @Override
                    public void accept(Employee employee, Consumer<String> objectConsumer) {
                        objectConsumer.accept(employee.getName());
                        objectConsumer.accept(employee.getName().repeat(2));
                        objectConsumer.accept(employee.getLastName());
                        objectConsumer.accept(employee.getLastName().repeat(3));
                    }
                }).toList();
        List<Long> longLengthConcatenations = randomizedConcatenations
                .stream()
                .mapToLong(new ToLongFunction<String>() {
                    @Override
                    public long applyAsLong(String value) {
                        return value.length();
                    }
                }).boxed().toList();
        System.out.println("randomizedConcatenations = " + randomizedConcatenations);
        System.out.println("longLengthConcatenations = " + longLengthConcatenations);

        /*
         * Lets say now that your boss wants, for this final section of his analysis to drop any
         * non unique names, then drop all non unique elements based on their last names (in a
         * different list), concat these two into a single stream and combine them using some
         * combination method, creating a personalized String
         */
        List<String> filteredNamesAndSurnames = employeeArrayList
                .stream()
                .collect(
                        Collectors.groupingBy(new Function<Employee, String>() {
                            @Override
                            public String apply(Employee employee) {
                                return employee.getName();
                            }
                        }, Collectors.mapping(Employee::getLastName,
                                              Collectors.toList())))
                .entrySet()
                .stream()
                .mapMulti(new BiConsumer<Map.Entry<String, List<String>>, Consumer<String>>() {
                    @Override
                    public void accept(Map.Entry<String, List<String>> stringListEntry,
                                       Consumer<String> objectConsumer) {
                                stringListEntry
                                        .getValue()
                                        .stream()
                                        .distinct()
                                        .forEach(string -> {
                                    objectConsumer.accept("Hello " + stringListEntry.getKey() +
                                                          " " + string + " happy to have you " +
                                                                  "here!");
                                });
                     }
                   }).toList();
        System.out.println("filteredNamesAndSurnames = " + filteredNamesAndSurnames);
        /*This example had me thinking for a little bit, thinking of the ways we could implement
        such a complicated logic without having a large memory footprint. I settled for working
        with a grouping by method and effectively having a list of last names. The first section
        groups by the names, since even if the names are repeated the map filters these
        duplicates, I preferred it over using a ton of distinct() calls. While it is possible to
        extract both independently, the issue lies with the combination of the streams, as
        concatenation would produce a string in the style of name, lname, lname, lname, .,...
        name and so on. Rather the collectors.grouping by allowed me to specifically hide the
        implementation details of how we extract the names and last names by simply holding a
        list of last names.

        To get the specialized message, what I did was continue with the map approach and stream
        over the entry set of the map, using it to create a map multi (although it could've
        worked I think with a map version too), to create a one to many transformation of the
        same name to all the distinct last names in the original list.*/

        /*
         * Lets say your boss now wants you to grab these employees and find all matches of those
         * whose name starts with the letter A, and whose salary is above 50_000 for a random
         * raise!
         */
         List<Employee> luckyRaise = employeeArrayList
                 .stream()
                 .filter((Employee employee) -> employee.getName().startsWith("A"))
                 .filter((Employee employee) -> employee.getSalary() > 50_000)
                 .toList();
         luckyRaise.forEach(employee -> employee.setSalary(employee.getSalary() + 100.0));
        System.out.println("luckyRaise = " + luckyRaise);

        /*
         * Let us say now that your boss wants to know a bit about his employee, he wants to know
         *  the first employee whose name starts with A, he wants to know if any employee is
         * named John, and if any is named Santiago. He wants to know if all match to HR, or if
         * any match to HR.
         */
         Optional<Employee> firstWithNameA = employeeArrayList
                 .stream()
                 .filter((Employee employee) -> employee.getName().startsWith("A"))
                 .findFirst();
         firstWithNameA.ifPresent((empl) -> System.out.println( "We have the first " +
                                                                        "one with an A " +
                                                                        "starting name " + empl));
         Boolean someoneNamedJohn = employeeArrayList
                 .stream()
                 .anyMatch((Employee employee) ->
                                   employee.getName().equalsIgnoreCase("John"));
         System.out.println("someoneNamedJohn = " + someoneNamedJohn);
         Boolean someoneNamedSantiago = employeeArrayList
                 .stream()
                 .anyMatch((Employee employee) ->
                                   employee.getName().equalsIgnoreCase("Santiago"));
         System.out.println("someoneNamedSantiago = " + someoneNamedSantiago);
         Boolean allMatchHR = employeeArrayList
                 .stream()
                 .allMatch((Employee employee) ->
                                   employee.getDepartment().equalsIgnoreCase("HR"));
         System.out.println("allMatchHR = " + allMatchHR);
         Boolean anyMatchHR = employeeArrayList
                 .stream()
                 .anyMatch((Employee employee) ->
                                   employee.getDepartment().equalsIgnoreCase("HR"));
         System.out.println("anyMatchHR = " + anyMatchHR);

         /*For now let us work with the final reduce methods*/
        /*
         * Consider now that your employer wants you to reduce the names into a single string
         * isntance, concatenating everyone's name
         */
        String concatenatedNames  = employeeArrayList
                .stream()
                .reduce("", new BiFunction<String, Employee, String>() {
                    @Override
                    public String apply(String s, Employee employee) {
                        return s.concat(employee.getName());
                    }
                }, new BinaryOperator<String>() {
                    @Override
                    public String apply(String s, String s2) {
                        return s.concat(s2);
                    }
                });
        System.out.println("concatenatedNames = " + concatenatedNames);

        /*
         * Consider now that he wants you to reduce these employees name into a StringBuilder,
         * now seriously into a StringBuilder, forcing duplicates off
         */
        StringBuilder concatedNamesV2 = employeeArrayList
                .stream()
                .map(Employee::getName)
                .distinct()
                .reduce(new StringBuilder(), new BiFunction<StringBuilder, String, StringBuilder>() {
                            @Override
                            public StringBuilder apply(StringBuilder stringBuilder, String s) {
                                return stringBuilder.append(s);
                            }
                        },new BinaryOperator<StringBuilder>() {
                            @Override
                            public StringBuilder apply(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
                                return stringBuilder.append(stringBuilder2);
                            }
                        });
        System.out.println("concatedNamesV2 = " + concatedNamesV2);


        /*! For the rest of the file we will use normal Streams like IntStream, DoubleStream or
        LongStream to practice these too*/
        /*
         * Suppose you are working on a big project and you need some sample data for a
         * particular simlation. The instructions are simple, generate a series of random numbers
         *  between 1 and 100, multiply them by a random double between 100 and 200, then divide
         * them by some double in the range of 1000 to 2000 and return the decimal value to a List.
         */

        IntStream originalValues = IntStream.rangeClosed(1, 101).limit(20);
        DoubleStream originalValuesMultiplied = originalValues.mapToDouble(new IntToDoubleFunction() {
            @Override
            public double applyAsDouble(int value) {
                return value * (new SecureRandom()).nextDouble(100.0,201.0);
            }
        }).map(new DoubleUnaryOperator() {
            @Override
            public double applyAsDouble(double operand) {
                return operand / (new SecureRandom()).nextDouble(1000.0,2000.0);
            }
        });
        List<Double> outputList = originalValuesMultiplied.boxed().toList();
        System.out.println("outputList = " + outputList);

        /*
        * Lets say we want to create a series of random numbers from 200 to 400, then I would
        * like to filter all those that are even, once filtered I want to map them to multiple
        * multiples of themselves, finally I would like to filter again for divisisibility this
        * time for 5 and finally store as a list (no more than 15 items)
         */

        List<Integer> integersFound = IntStream.rangeClosed(200,400)
                .filter(integer -> {
                    return !(integer % 2 == 0);
                })
                .mapMulti(new IntStream.IntMapMultiConsumer() {
                    @Override
                    public void accept(int value, IntConsumer ic) {
                        ic.accept(value * 3);
                        ic.accept(value * 4);
                        ic.accept(value*6);
                    }
                })
                .filter(new IntPredicate() {
                    @Override
                    public boolean test(int value) {
                        return !(value % 5 == 0);
                    }
                })
                .boxed().toList();
        System.out.println("integersFound = " + integersFound);

        /*
         * Using the previous list, and another one created on the fly, I would like to concat
         * two streams and produce a singular sumary statistics
         */
        IntSummaryStatistics statistics1 =
                IntStream.concat(integersFound.stream().mapToInt(Integer::intValue),
                                                            IntStream.rangeClosed(1,10))
                        .summaryStatistics();
        System.out.println("statistics1 = " + statistics1);

    }
}
