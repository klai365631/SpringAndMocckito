package com.SkyPro.SpringAndMocckito;

import com.SkyPro.SpringAndMocckito.Service.DepartmentService;
import com.SkyPro.SpringAndMocckito.Service.EmployeeService;
import com.SkyPro.SpringAndMocckito.exeption.EmployeeNotFoundException;
import com.SkyPro.SpringAndMocckito.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Василий", "Васильев", 1, 55000),
                new Employee("Андрей", "Андреев", 1, 65000),
                new Employee("Иван", "Иванов", 2, 45000),
                new Employee("Мария", "Иванова", 2, 50000),
                new Employee("Ирина", "Андреевна", 2, 47000)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }

    @Test
    public void setEmployeesByDepartmentNegativeTest() {
        assertThat(departmentService.findEmployeeFromDepartment(4)).isEmpty();
    }

    @Test
    public void withNoEmployees() {
        when(employeeService.getAll()).thenReturn(Collections.emptyList());
        assertThat(departmentService.findEmployees()).isEmpty();
        assertThat(departmentService.findEmployeeFromDepartment(1)).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("employeeWithMaxSalaryParams")
    public void employeeWithMaxSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.findEmployeeWithMaxSalaryFromDepartment(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMaxSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMaxSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryParams")
    public void employeeWithMinSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.findEmployeeWithMinSalaryFromDepartment(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMinSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWithMinSalaryFromDepartment(3));
    }

    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    public void employeeFromDepartmentPositiveTest(int departmentId, List<Employee> expected) {
        assertThat(departmentService.findEmployeeFromDepartment(departmentId)).containsExactlyElementsOf(
                expected);
    }

    @Test
    public void employeeGroupedByDepartmentTest() {
        assertThat(departmentService.findEmployees()).containsAllEntriesOf(
                Map.of(
                        1, List.of(new Employee("Василий", "Васильев", 1, 55000),
                                new Employee("Андрей", "Андреев", 1, 65000)),
                        2, List.of(new Employee("Иван", "Иванов", 2, 45000),
                                new Employee("Мария", "Иванова", 2, 50000),
                                new Employee("Ирина", "Андреевна", 2, 47000))
                )
        );
    }

    public static Stream<Arguments> employeeWithMaxSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Андрей", "Андреев", 1, 65000)),
                Arguments.of(2, new Employee("Мария", "Иванова", 2, 50000))
        );
    }

    public static Stream<Arguments> employeeWithMinSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Василий", "Васильев", 1, 55000)),
                Arguments.of(2, new Employee("Иван", "Иванов", 2, 45000))
        );
    }

    public static Stream<Arguments> employeesFromDepartmentParams() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Василий", "Васильев", 1, 55000),
                        new Employee("Андрей", "Андреев", 1, 65000))),
                Arguments.of(2, List.of(new Employee("Иван", "Иванов", 2, 45000),
                        new Employee("Мария", "Иванова", 2, 50000),
                        new Employee("Ирина", "Андреевна", 2, 47000))),
                Arguments.of(3, Collections.emptyList())
        );
    }

}



