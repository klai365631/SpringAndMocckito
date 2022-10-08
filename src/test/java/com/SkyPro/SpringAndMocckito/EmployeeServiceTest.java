package com.SkyPro.SpringAndMocckito;

import com.SkyPro.SpringAndMocckito.Service.EmployeeService;
import com.SkyPro.SpringAndMocckito.Service.ValidatorService;
import com.SkyPro.SpringAndMocckito.exeption.*;
import com.SkyPro.SpringAndMocckito.model.Employee;
import org.assertj.core.api.AssertProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());

    @ParameterizedTest
    @MethodSource("params")
    public void addNegativeTest1(String name,
                                 String surname,
                                 int departament,
                                 double salary) {
        Employee expected = new Employee(name, surname, departament, salary);
        assertThat(employeeService.addEmployee(name, surname, departament, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeAlreadyAddedException.class)
                .isThrownBy(() -> employeeService.addEmployee(name, surname, departament, salary));


    }

    @ParameterizedTest
    @MethodSource("params")
    public void addNegativeTest2(String name,
                                 String surname,
                                 int departament,
                                 double salary) {
        List<Employee> employees = generateEmployees(10);
        employees.forEach(employee ->
                assertThat(
                        employeeService.addEmployee(employee.getName(), employee.getSurname(), employee.getDepartment(),
                                employee.getSalary())).isEqualTo(employee)
        );
        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.addEmployee(name, surname, departament, salary));
    }
    @Test
    public void addNegativeTest3(){
        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(() -> employeeService.addEmployee("Иван", "Ivanoв", 1, 56000));

        assertThatExceptionOfType(IncorrectSurnameException.class)
                .isThrownBy(() -> employeeService.addEmployee("Petr", "!Петров", 1, 65000));

        assertThatExceptionOfType(IncorrectNameException.class)
                .isThrownBy(() -> employeeService.addEmployee(null, "Иванова", 2, 75000));
    }
    @ParameterizedTest
    @MethodSource("params")
    public void removeNegativeTest(String name,
                                 String surname,
                                 int departament,
                                 double salary){
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->employeeService.removeEmployee("test","test"));

        Employee expected = new Employee(name, surname, departament, salary);
        assertThat(employeeService.addEmployee(name, surname, departament, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.removeEmployee("test", "test"));


    }
    @ParameterizedTest
    @MethodSource("params")
    public void removePositiveTest(String name,
                                   String surname,
                                   int departament,
                                   double salary){

        Employee expected = new Employee(name, surname, departament, salary);
        assertThat(employeeService.addEmployee(name, surname, departament, salary)).isEqualTo(expected);
        assertThat(employeeService.getAll()).hasSize(1).contains(expected);
        assertThat(employeeService.removeEmployee(name, surname)).isEqualTo(expected);
        assertThat(employeeService.getAll()).isEmpty();

    }
    @ParameterizedTest
    @MethodSource("params")
    public void findNegativeTest(String name,
                                   String surname,
                                   int departament,
                                   double salary){
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->employeeService.findEmployee("test","test"));

        Employee expected = new Employee(name, surname, departament, salary);
        assertThat(employeeService.addEmployee(name, surname, departament, salary)).isEqualTo(expected);
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> employeeService.findEmployee("test", "test"));


    }
    @ParameterizedTest
    @MethodSource("params")
    public void findPositiveTest(String name,
                                 String surname,
                                 int departament,
                                 double salary){
        Employee expected = new Employee(name, surname, departament, salary);
        assertThat(employeeService.addEmployee(name, surname, departament, salary)).isEqualTo(expected);

        assertThat(employeeService.findEmployee(name, surname)).isEqualTo(expected);
        assertThat(employeeService.getAll()).hasSize(1);


    }









    private List<Employee> generateEmployees(int saze) {
        return Stream.iterate(1, i -> i + 1)
                .limit(saze)
                .map(i -> new Employee("Name" + (char) ((int) 'a' + i), "Surname" + (char) ((int) 'a' + i),
                        i, 10_000 + i))
                .collect(Collectors.toList());
    }

    public static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of("Ivan", "Ivanov", 1, 50000),
                Arguments.of("Ivan", "Petrov", 1, 55000),
                Arguments.of("Ivan", "Sidorov", 1, 40000)
        );
    }
}
