package PracticeModule.Stream_API_Practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamComparatorAPI {
    public static void main(String[] args) {
        /*
        * This chapter is going to be shorter than the others, we will mostly explore how to sort
        *  using Comparator instances, and Comparator static methods, both in normal collections
        * and Streams*/

        ArrayList<Employee> employees = new ArrayList<>();
        StreamCollectorsAPI.extracted(employees);
        employees.forEach(System.out::println);

        /*
         * Lets say after all those arduous rounds of code with your boss, now he wants you to
         * sort the employee list by their name, and then by their salary
         */
        List<Employee> sortedAttemptOne =
                employees.stream().sorted(Comparator.comparing(Employee::getName)
                                          .thenComparing(Employee::getSalary)).toList();
        System.out.println("SortedAttemptOne");
        sortedAttemptOne.forEach(System.out::println);
        /*As we can see in the print, this correctly sorts first name and then salary, lets mix
        it up with salary and then name and then department*/
        sortedAttemptOne =
                employees.stream().sorted(Comparator.comparingDouble(Employee::getSalary)
                                                  .thenComparing(Employee::getName)
                                                  .thenComparing(Employee::getDepartment)).toList();
        System.out.println("SortedAttemptTwo");
        sortedAttemptOne.forEach(System.out::println);
        /*Funny now we can see that the salary is correct, but the name and the department are
        off the charts, they are not sorted, they are bubble sorted. This often happens when
        using more than one comparison field, values go out of the window.*/
        /*
         * Frustrated, your boss wants you to sort these values based on department, group them
         * by car and sort those repeated lists by name
         */
        Map<String, List<String>> extractedValues = employees
                .stream()
                .sorted(Comparator.comparing(Employee::getDepartment))
                .collect(Collectors.groupingBy(Employee::getCarBrand,
                         Collectors.mapping(Employee::getName,
                                            Collectors.toList())));
        for(List<String> listString : extractedValues.values()) {
                listString.sort(Comparator.naturalOrder());
        }
        System.out.println("extractedValues");
        extractedValues.forEach((key, value) -> System.out.println(key + " " + value));
        /*
         * Lets think of nesting comparators. Your boss now wants a sorting based on last name
         * and then on department
         */
        sortedAttemptOne =
                employees.stream().sorted(Comparator.comparing(Employee::getDepartment).thenComparing(Employee::getLastName)).toList();
        System.out.println("SortedAttemptThree");
        sortedAttemptOne.forEach(System.out::println);
        /*Notice here that we wrote first the comparison for what the boss wanted second. This is
         because in general these comparator nesting applications apply a f o g rule, so first
         they sort by last name but it is defined in the then comparing segment, therefore the
         get Department is executed second*/
        /*
         * Lets say now that the boss wants you to get the reversed order of the car brand key,
         * and sort by that.
         */
        sortedAttemptOne = employees
                .stream()
                .sorted(Comparator.comparing(Employee::getCarBrand)
                                            .reversed())
                .toList();
        System.out.println("SortedAttemptFour");
        sortedAttemptOne.forEach(System.out::println);
        

    }
}

