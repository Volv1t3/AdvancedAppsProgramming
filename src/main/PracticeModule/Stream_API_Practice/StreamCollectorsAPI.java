package PracticeModule.Stream_API_Practice;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamCollectorsAPI {

    public static void main(String[] args) {
        /*?
         ? Of the Stream API we have seen only a handful of methods, just like we have done for
         ? the Collectors API, however here I will take a look at those examples that we have
         ? done and attempt to create a concise explanation using an Employee Class
         */

        //! Suppose we have a database, consisting of ten employees of our company
        ArrayList<Employee> employees = new ArrayList<>();
        extracted(employees);

        /*
         * Let's say your boss asks you to find the average value of their salary, to do that, 
         * you might think of a for loop and extraction, but we can do that easily with streams 
         * and the Collectors API
         */
        Double resultOfAverage = employees
                .stream()
                .collect(Collectors.averagingDouble(new ToDoubleFunction<Employee>() {
                    @Override
                    public double applyAsDouble(Employee value) {
                        return value.getSalary();
                    }
                }));
        System.out.println("resultOfAverage = " + resultOfAverage);
        /*
         * You can see how this method is much simpler, and we can reduce it further if we used 
         * lambdas (but I don't like those too much). Now what if your boss asks you for a summary 
         * statistics of their salaries, excel now? nah
         */
        DoubleSummaryStatistics resultOfStatistics = employees
                .stream()
                .collect(Collectors.summarizingDouble(new ToDoubleFunction<Employee>() {
                    @Override
                    public double applyAsDouble(Employee value) {
                        return value.getSalary();
                    }
                }));
        System.out.println("resultOfStatistics.toString() = " + resultOfStatistics.toString());
        /*
         * Let's say HR wants to know, for a get together party how long each of their members
         * names are, summarized as the average and also the summary statistic
         */
        Map<String, Object> summaryOfData = employees
                .stream()
                .collect(Collectors.teeing(
                        Collectors.averagingDouble(new ToDoubleFunction<Employee>() {
                            @Override
                            public double applyAsDouble(Employee value) {
                                return value.getSalary();
                            }
                        }), Collectors.summarizingDouble(new ToDoubleFunction<Employee>() {
                            @Override
                            public double applyAsDouble(Employee value) {
                                return value.getSalary();
                            }
                        }), new BiFunction<Double, DoubleSummaryStatistics, Map<String, Object>>() {
                            HashMap<String, Object> result = new HashMap<>();
                            @Override
                            public Map<String, Object> apply(Double aDouble,
                                                             DoubleSummaryStatistics
                                                                     doubleSummaryStatistics) {
                                result.put("average", aDouble);
                                result.put("stats", doubleSummaryStatistics);
                                return result;
                            }
                        }));
        System.out.println("Average : " + (Double) summaryOfData.get("average"));
        System.out.println("Stats : " + ((DoubleSummaryStatistics)summaryOfData.get("stats")).toString());
        /*Sure this is wasteful but it shows the power of teeing. Teeing allow us to combine two
        COllectors outputs into a single structure. I could've summed both averages,but I chose
        to return a map!*/

        /*
         * Now, assume your HR pal wants you to find the average of each employees name length in
         *  terms of ints, and last names in terms of longs, weird right? but it can be done!
         */
        Double averageNameLength = employees
                .stream()
                .collect(Collectors.averagingInt(new ToIntFunction<Employee>() {
                    @Override
                    public int applyAsInt(Employee value) {
                        return value.getName().length();
                    }
                }));
        System.out.println("averageNameLength = " + averageNameLength);
        Double averageLNameLength = employees
                .<Employee>stream()
                .collect(Collectors.averagingLong(new ToLongFunction<Employee>() {
                    @Override
                    public long applyAsLong(Employee value) {
                        return (long) value.getLastName().length();
                    }
                }));
        System.out.println("averageLNameLength = " + averageLNameLength);
        /*
         * Once she has finished asking you to do this, you boss comes back around and asks for
         * you to find the specific max and min length of each persons cars name!
         */
        Map<String, Employee> mapOfResults = employees
                .<Employee>stream()
                .collect(Collectors.teeing(
                        Collectors.minBy(new Comparator<Employee>() {
                            @Override
                            public int compare(Employee o1, Employee o2) {
                                return o1.getCarBrand().length() - o2.getCarBrand().length();
                            }
                        }),
                        Collectors.maxBy(Comparator.comparing(new Function<Employee, String>() {
                            @Override
                            public String apply(Employee t) {
                                return t.getCarBrand();
                            }
                        })), new BiFunction<Optional<Employee>, Optional<Employee>, Map<String, Employee>>() {
                            final HashMap<String, Employee> result = new HashMap<>();
                            @Override
                            public Map<String, Employee> apply(Optional<Employee> employee, Optional<Employee> employee2) {
                                employee.ifPresent(value -> result.put("minByComparator", value));
                                employee2.ifPresent(new Consumer<Employee>() {
                                    @Override
                                    public void accept(Employee employee) {
                                        result.put("maxByComparator", employee);
                                    }
                                });
                                return result;
                            }
                        }));
        System.out.println("mapOfResults = " + mapOfResults);

        /*
         * Now lets assume that your boss wants a long String containing all the car brands his
         * employees drive for a slide presentation (for some bizarre reason)
         */
        String joinedStringWithSimpleDelimiter = employees
                .<Employee>stream()
                .collect(Collectors.mapping(
                        new Function<Employee, String>() {
                            @Override
                            public String apply(Employee employee) {
                                return employee.getCarBrand();
                            }
                        }, Collectors.joining()));
        System.out.println("joinedStringWithSimpleDelimiter = " + joinedStringWithSimpleDelimiter);
        /*Alternatively, we can do this with simpler intermediate operations in Streams*/
        joinedStringWithSimpleDelimiter =
                employees.stream().map(Employee::getCarBrand).collect(Collectors.joining());
        System.out.println("joinedStringWithSimpleDelimiter = " + joinedStringWithSimpleDelimiter);

        /*
         * Most of the operations that are done with collectors, can to some extent be replicated
         *  with basic intermediate operations, however it is much more prolific to use teeing or
         *  grouping than normal operations.
         */

        /*
         * WHat if your boss wants a listing of their employees cars with a cute delimiter, or
         * with a cute format?
         */
        String joinedVersionTwo = employees
                .stream()
                .collect(
                        Collectors.mapping(
                                (employee -> employee.getCarBrand()),
                                            Collectors.joining("-+-")));
        System.out.println("joinedVersionTwo = " + joinedVersionTwo);
        joinedVersionTwo = employees
                .stream()
                .collect(Collectors.mapping(new Function<Employee, String>() {
                    @Override
                    public String apply(Employee employee) {
                        return employee.getCarBrand();
                    }
                }, Collectors.joining(",","[","]")));
        System.out.println("joinedVersionTwo = " + joinedVersionTwo);

        /*
         * Lets do something much more complicated now, lets say your boss wants to reduce (i.e.
         * consume the database and output a single number, the total salary of their employees.
         * We can do this both through Collectors and normal intermediate and terminal operations
         */
        Double sumOfSalaryVerOne = employees
                .stream()
                .collect(Collectors.mapping(new Function<Employee, Double>() {
                    @Override
                    public Double apply(Employee employee) {
                        return employee.getSalary();
                    }
                },Collectors.reducing(0.0, new BinaryOperator<Double>() {
                    @Override
                    public Double apply(Double aDouble, Double aDouble2) {
                        return aDouble + aDouble2;
                    }
                })));
        System.out.println("sumOfSalaryVerOne = " + sumOfSalaryVerOne);
        /*Of course this is somewhat convoluted, but this shows how to use the reduce and mapping
         operations in the collectors API, now lets use simple operations*/
        sumOfSalaryVerOne = employees
                .stream()
                .map(Employee::getSalary)
                .reduce(0.0, new BiFunction<Double, Double, Double>() {
                    @Override
                    public Double apply(Double aDouble, Double aDouble2) {
                        return aDouble + aDouble2;
                    }
                }, new BinaryOperator<Double>() {
                    @Override
                    public Double apply(Double aDouble, Double aDouble2) {
                        return aDouble +  aDouble2;
                    }
                });
        System.out.println("sumOfSalaryVerOne = " + sumOfSalaryVerOne);
        /*
         * There are some versions of the reduce version, both simple, or non so simple. THe
         * difference is that the one where we supply an identity value, we always have a value
         * even if the stream is empty. If we use the version without one we get an Optional.
         *
         * If we use DoubleStream, IntStream or LongStream, we will always get a OptionalDouble,
         * OptionalInt, OptionalLong. These are specific to these classes although we can use
         * them outside if we want to.
         */
         /*Lets say now our boss wants us to transform our employees into something else,
         employees to a number based on their salary, and employees to a number based on their
         name's length*/
        List<Double> resultofMapping = employees
                .stream()
                .collect(Collectors.mapping(new Function<Employee, Double>() {
                    @Override
                    public Double apply(Employee employee) {
                        return employee.getSalary();
                    }
                }, Collectors.toList()));
        System.out.println("resultofMapping = " + resultofMapping);
        List<Integer> resultOfMappingTwo = employees.stream().mapMulti(new BiConsumer<Employee,
                Consumer<Integer>>() {
            @Override
            public void accept(Employee employee, Consumer<Integer> objectConsumer) {
                objectConsumer.accept(employee.getName().length());
                objectConsumer.accept(employee.getLastName().length());
            }
        }).toList();
        System.out.println("resultOfMappingTwo = " + resultOfMappingTwo);
        resultOfMappingTwo = employees.stream().mapMultiToInt(new BiConsumer<Employee, IntConsumer>() {
            @Override
            public void accept(Employee employee, IntConsumer intConsumer) {
                intConsumer.accept(employee.getName().length());
                intConsumer.accept(employee.getLastName().length());
            }
        }).boxed().toList();
        System.out.println("resultOfMappingTwo = " + resultOfMappingTwo);

        ArrayList<List<Employee>> poorlyMaintainedDatabase = new ArrayList<>() {{
            add(List.of(new Employee(50000, "John", "Doe", "IT", "Toyota"),
                        new Employee(60000, "Jane", "Smith", "HR", "Honda"),
                        new Employee(55000, "Alice", "Brown", "Sales", "Tesla")));
        }};
        System.out.println("poorlyMaintainedDatabase = " + poorlyMaintainedDatabase);
        System.out.println(employees.size());
        employees.addAll(poorlyMaintainedDatabase.stream().flatMap(new Function<List<Employee>,
                Stream<Employee>>() {
            @Override
            public Stream<Employee> apply(List<Employee> employees) {
                return employees.stream();
            }
        }).toList());
        System.out.println(employees.size());
        /*
         * Lets assume that our boss wants to combine all of the names into a string builder and
         * from there extract the length of their names
         */
        System.out.println("Length of String of names = " + employees.stream()
                .map(Employee::getName)
                .collect(StringBuilder::new,
                         new BiConsumer<StringBuilder, String>() {
                             @Override
                             public void accept(StringBuilder stringBuilder, String s) {
                                 stringBuilder.append(s);
                             }
                         },
                         new BiConsumer<StringBuilder, StringBuilder>() {
                             @Override
                             public void accept(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
                                 stringBuilder.append(stringBuilder2.toString());
                             }
                         }).length());

        /*
          * Lets say now your boss wants you to group your employees based on a salary range,
          * this might be useful for some cases, but how will you define the steps to do this?
          *
          * We can begin by thinking of the max and min salaries
         */
        resultOfStatistics.getMax(); //Max Salary
        resultOfStatistics.getMin(); //Min Salary
        Map<String, List<Employee>> resultingPartition =
                employees.stream().collect(
                        Collectors.teeing(
                        Collectors.teeing(
                                Collectors.partitioningBy(new Predicate<Employee>() {
                                    @Override
                                    public boolean test(Employee t) {
                                        return 30_000 <= t.getSalary() && t.getSalary() < 40_000;
                                    }
                                }, Collectors.toList()),
                                Collectors.partitioningBy(new Predicate<Employee>() {
                                    @Override
                                    public boolean test(Employee t) {
                                        return 40_000 <= t.getSalary() && t.getSalary() < 50_000;
                                    }
                                }, Collectors.toList()),
                                new BiFunction<Map<Boolean, List<Employee>>, Map<Boolean, List<Employee>>
                                        , Map<String, List<Employee>>>() {
                                    @Override
                                    public Map<String, List<Employee>> apply(Map<Boolean, List<Employee>> booleanListMap,
                                                                             Map<Boolean, List<Employee>> booleanListMap2) {
                                        return Map.of("30_000-40_000", booleanListMap.get(Boolean.TRUE),
                                                      "40_000-50_000", booleanListMap2.get(Boolean.TRUE));
                                    }
                                }),
                        Collectors.teeing(
                                Collectors.partitioningBy(new Predicate<Employee>() {
                                    @Override
                                    public boolean test(Employee employee) {
                                        return 50_000 <= employee.getSalary() && employee.getSalary() < 60_000;
                                    }
                                }, Collectors.toList()),
                                Collectors.partitioningBy(new Predicate<Employee>() {
                                    @Override
                                    public boolean test(Employee employee) {
                                        return 60_000 <= employee.getSalary() && employee.getSalary() < 70_000;
                                    }
                                }), new BiFunction<Map<Boolean, List<Employee>>, Map<Boolean, List<Employee>>, Map<String, List<Employee>>>() {
                                    @Override
                                    public Map<String, List<Employee>> apply(Map<Boolean, List<Employee>> booleanListMap, Map<Boolean,
                                            List<Employee>> booleanListMap2) {
                                        return Map.of("50_000-60_000", booleanListMap.get(Boolean.TRUE),
                                                      "60_000-70_000", booleanListMap2.get(Boolean.TRUE));
                                    }
                                }), new BiFunction<Map<String, List<Employee>>, Map<String, List<Employee>>, Map<String, List<Employee>>>() {
                            @Override
                            public Map<String, List<Employee>> apply(Map<String, List<Employee>> stringListMap, Map<String, List<Employee>> o) {
                                return new HashMap<>() {{
                                    putAll(stringListMap);
                                    putAll(o);
                                }};
                            }
                        }));
        resultingPartition.entrySet().forEach(new Consumer<Map.Entry<String, List<Employee>>>() {
            @Override
            public void accept(Map.Entry<String, List<Employee>> stringListEntry) {
                System.out.println(stringListEntry.getKey() + "\n");
                stringListEntry.getValue().forEach(value -> {
                    System.out.println("\t" + value);
                });
            }
        });

        /*
         * Lets say we want to group these employees by their car brand, and additionally we only
         * want to store their department. Here is where both grouping by and mapping from
         * collectors shine.
         */
        Map<String, List<String>> resultOfGroupingByOperation = employees
                .stream()
                .collect(Collectors.groupingBy(
                        new Function<Employee, String>() {
                            @Override
                            public String apply(Employee employee) {
                                return employee.getCarBrand();
                            }
                        }, Collectors.mapping(new Function<Employee, String>() {
                            @Override
                            public String apply(Employee employee) {
                                return employee.getDepartment();
                            }
                        }, Collectors.toList())));
        resultOfGroupingByOperation.entrySet().forEach(new Consumer<Map.Entry<String,
                List<String>>>() {
            @Override
            public void accept(Map.Entry<String, List<String>> stringListEntry) {
                System.out.println(stringListEntry.getKey() + "\n");
                stringListEntry.getValue().forEach(value -> {
                    System.out.println("\t" + value);
                });
            }
        });

        /*
         * Now lets say that your boss wants you to classify the names of those employees based
         * on their last name initial!
         */
        Map<Character, List<Employee>> resultOfAggregation = employees
                .stream()
                .collect(Collectors.groupingBy(new Function<Employee, Character>() {
                    @Override
                    public Character apply(Employee employee) {
                        return employee.getLastName().charAt(0);
                    }
                }));
        resultOfAggregation.entrySet().forEach(new Consumer<Map.Entry<Character, List<Employee>>>() {
            @Override
            public void accept(Map.Entry<Character, List<Employee>> characterListEntry) {
                System.out.println(characterListEntry.getKey() + "\n");
                characterListEntry.getValue().forEach(value -> {
                    System.out.println("\t" + value);
                });
            }
        });
        /*
         * Lets say your boss now wants you to group their names based on the initial of their
         * last name, grouping internally based on the car's  brand and counting the amount of each
         */
        HashMap<Character, Map<String, Long>> resultOfSQLOperation =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                new Function<Employee, Character>() {
                                    @Override
                                    public Character apply(Employee employee) {
                                        return employee.getLastName().charAt(0);
                                    }
                                }, HashMap::new, Collectors.groupingBy(
                                        new Function<Employee, String>() {
                                            @Override
                                            public String apply(Employee employee) {
                                                return employee.getCarBrand();
                                            }
                                        }, Collectors.counting())));
        resultOfSQLOperation.entrySet().forEach(new Consumer<Map.Entry<Character, Map<String, Long>>>() {
            @Override
            public void accept(Map.Entry<Character, Map<String, Long>> characterListEntry) {
                System.out.println(characterListEntry.getKey() + "\n");
                characterListEntry.getValue().entrySet().forEach(new Consumer<Map.Entry<String, Long>>() {
                    @Override
                    public void accept(Map.Entry<String, Long> stringLongEntry) {
                        System.out.println("\t" + stringLongEntry.getKey() + " " + stringLongEntry.getValue());
                    }
                });
            }
        });
        /*
         * Lets say your boss wants you to perform an aggregation on the salary again, just like
         * before, but this time he wants you to filter those who drive a Honda since they are
         * managerial positions
         */

        resultingPartition = employees
                .stream()
                .collect(Collectors.teeing(
                        Collectors.teeing(
                                Collectors.partitioningBy(
                                        (Employee employee) -> {
                                            return 40_000 <= employee.getSalary() && employee.getSalary() < 50_000;
                                        }
                                        ,
                                        Collectors.filtering((Employee employee) -> {
                                            return !employee.getCarBrand().equalsIgnoreCase(
                                                    "HONDA");
                                        }, Collectors.toList())),
                                Collectors.partitioningBy(new Predicate<Employee>() {
                                    @Override
                                    public boolean test(Employee employee) {
                                        return 50_000 <= employee.getSalary() && employee.getSalary() < 60_000;
                                    }
                                }, Collectors.filtering((Employee employee) -> {
                                    return !employee.getCarBrand().equalsIgnoreCase(
                                            "HONDA");
                                }, Collectors.toList())),
                                new BiFunction<Map<Boolean, List<Employee>>, Map<Boolean,
                                        List<Employee>>, Map<String, List<Employee>>>() {
                                    @Override
                                    public Map<String, List<Employee>> apply(Map<Boolean, List<Employee>> booleanListMap,
                                                                             Map<Boolean, List<Employee>> booleanListMap2) {
                                        return Map.of("40_000-50_000",
                                                      booleanListMap.get(Boolean.TRUE),
                                                      "50_000-60_000",
                                                      booleanListMap2.get(Boolean.TRUE));
                                    }
                                }),
                        Collectors.teeing(
                                Collectors.partitioningBy((Employee employee) -> {
                                    return 60_000 <= employee.getSalary() && employee.getSalary() < 70_000;
                                }, Collectors.filtering(new Predicate<Employee>() {
                                    @Override
                                    public boolean test(Employee t) {
                                        return !t.getCarBrand().equalsIgnoreCase("HONDA");
                                    }
                                }, Collectors.toList())),
                                Collectors.partitioningBy(new Predicate<Employee>() {
                                    @Override
                                    public boolean test(Employee employee) {
                                        return 70_000 <= employee.getSalary() && employee.getSalary() < 80_000;
                                    }
                                }, Collectors.filtering((Employee employee) -> {
                                    return !employee.getCarBrand().equalsIgnoreCase(
                                            "HONDA");
                                }, Collectors.toList())),
                                new BiFunction<Map<Boolean, List<Employee>>, Map<Boolean,
                                        List<Employee>>,
                                        Map<String, List<Employee>>>() {
                                    @Override
                                    public Map<String, List<Employee>> apply(Map<Boolean,
                                                                                     List<Employee>> booleanListMap,
                                                                             Map<Boolean,
                                                                                     List<Employee>> booleanListMap2) {
                                        return Map.of("60_000-70_000",
                                                      booleanListMap.get(Boolean.TRUE),
                                                      "70_000-80_000",
                                                      booleanListMap2.get(Boolean.TRUE));
                                    }
                                }),
                        new BiFunction<Map<String, List<Employee>>, Map<String, List<Employee>>, Map<String, List<Employee>>>() {
                            @Override
                            public Map<String, List<Employee>> apply(Map<String, List<Employee>> stringListMap, Map<String, List<Employee>> stringListMap2) {
                                return new TreeMap<>() {{
                                    putAll(stringListMap);
                                    putAll(stringListMap2);
                                }};
                            }
                        }));
        resultingPartition.entrySet().forEach(new Consumer<Map.Entry<String, List<Employee>>>() {
            @Override
            public void accept(Map.Entry<String, List<Employee>> stringListEntry) {
                System.out.println(stringListEntry.getKey() + "\n");
                stringListEntry.getValue().forEach(value -> {
                    System.out.println("\t" + value);
                });
            }
        });


        /*
         * We have now reviewed most of the methods we can extract information, group, tee
         * queries, etc. We are now ready to move on to another file to discuss Stream API
         * intermediate, and terminal operations.
         */
    }


    public static void extracted(ArrayList<Employee> employees) {
        List<String> names = Arrays.asList("John", "Jane", "Alice", "Bob", "Charlie");
        List<String> lastNames = Arrays.asList("Smith", "Johnson", "Williams", "Brown", "Jones");
        List<String> departments = Arrays.asList("IT", "Law", "Sales", "Marketing", "HR");
        List<String> carBrands = Arrays.asList("Toyota", "Ford", "BMW", "Tesla", "Honda");

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String name = names.get(random.nextInt(names.size()));
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            String department = departments.get(random.nextInt(departments.size()));
            String carBrand = carBrands.get(random.nextInt(carBrands.size()));
            double salary = 50000 + random.nextInt(50000); // Overlapping salaries
            employees.add(new Employee(salary, name, lastName, department, carBrand));
        }
    }

}

class Employee{
    /*
     * This class must hold parameters: salary, name, last name, department, car brand they drive
     */
    private double salary;
    private String name;
    private String lastName;
    private String department;
    private String carBrand;

    public Employee(double salary, String name, String lastName, String department, String carBrand) {
        this.salary = salary;
        this.name = name;
        this.lastName = lastName;
        this.department = department;
        this.carBrand = carBrand;
    }
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "salary=" + salary +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", department='" + department + '\'' +
                ", carBrand='" + carBrand + '\'' +
                '}';

    }
}
