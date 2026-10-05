package com.employeemanagementsystem.employee.service.impl;

import com.employeemanagementsystem.department.entity.DepartmentEntity;
import com.employeemanagementsystem.department.repository.DepartmentRepository;
import com.employeemanagementsystem.employee.dto.request.EmployeeRequestDTO;
import com.employeemanagementsystem.employee.dto.request.EmployeeStatusRequestDTO;
import com.employeemanagementsystem.employee.dto.response.EmployeeResponseDTO;
import com.employeemanagementsystem.employee.dto.response.PageResponseDTO;
import com.employeemanagementsystem.employee.entity.EmployeeEntity;
import com.employeemanagementsystem.employee.enums.EmployeeGenderEnums;
import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import com.employeemanagementsystem.employee.mapper.EmployeeMapper;
import com.employeemanagementsystem.employee.repository.EmployeeRepository;
import com.employeemanagementsystem.employee.service.EmployeeService;
import com.employeemanagementsystem.employee.specification.EmployeeSpecification;
import com.employeemanagementsystem.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final int MINIMUM_AGE = 18;
    private static final int MAX_PAGE_SIZE = 50;

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    private void validateMinimumAge(LocalDate dateOfBirth) {
        if (Period.between(dateOfBirth, LocalDate.now()).getYears() < MINIMUM_AGE) {
            throw new InvalidAgeException(ErrorCode.EMPLOYEE_BELOW_MINIMUM_AGE,
                    "Employee must be at least " + MINIMUM_AGE + " years old");
        }
    }

    @Override
    @Transactional
    //@Transactional = "treat this whole method as one unit: save everything, or save nothing"
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {

        DepartmentEntity departmentEntity = departmentRepository
                .findById(employeeRequestDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.DEPARTMENT_NOT_FOUND,
                        "Department not found with id: "
                                + employeeRequestDTO.getDepartmentId()
                ));

        if (employeeRepository.existsByEmployeeEmail(
                employeeRequestDTO.getEmployeeEmail())) {

            throw new DuplicateResourceException(
                    ErrorCode.EMPLOYEE_EMAIL_ALREADY_EXISTS,
                    "Employee email already exists: "
                            + employeeRequestDTO.getEmployeeEmail()
            );
        }

        validateMinimumAge(employeeRequestDTO.getDateOfBirth());
        EmployeeEntity employeeEntity = employeeMapper.toEntity(employeeRequestDTO);
        employeeEntity.setDepartmentEntity(departmentEntity);
        employeeEntity.setEmployeeStatusEnums(EmployeeStatusEnums.ACTIVE);
        EmployeeEntity createEmployeeEntity = employeeRepository.save(employeeEntity);
        return employeeMapper.toResponse(createEmployeeEntity);
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {

        EmployeeEntity employeeEntity = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.EMPLOYEE_NOT_FOUND,
                        "Employee not found with id: " + id
                ));

        return employeeMapper.toResponse(employeeEntity);
    }


    @Override
    @Transactional
    public List<EmployeeResponseDTO> createEmployees(List<EmployeeRequestDTO> employeeRequestDTO) {
        /*
        RequestDTO ──toEntity()──> Entity
        Entity ──toResponse()──> ResponseDTO
        List<Entity> ──toResponseList()──> List<ResponseDTO>
        */

        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        Set<String> emails = new HashSet<>();
        for (EmployeeRequestDTO dto : employeeRequestDTO) {
            if (!emails.add(dto.getEmployeeEmail())) {
                throw new DuplicateResourceException(
                        ErrorCode.EMPLOYEE_EMAIL_ALREADY_EXISTS,
                        "Duplicate email in request: "
                                + dto.getEmployeeEmail()
                );
            }

            if (employeeRepository.existsByEmployeeEmail(
                    dto.getEmployeeEmail())) {

                throw new DuplicateResourceException(
                        ErrorCode.EMPLOYEE_EMAIL_ALREADY_EXISTS,
                        "Employee email already exists: "
                                + dto.getEmployeeEmail()
                );
            }

            validateMinimumAge(dto.getDateOfBirth());
            DepartmentEntity departmentEntity = departmentRepository
                    .findById(dto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            ErrorCode.DEPARTMENT_NOT_FOUND,
                            "Department not found with id: "
                                    + dto.getDepartmentId()
                    ));

            EmployeeEntity employeeEntity = employeeMapper.toEntity(dto);
            employeeEntity.setDepartmentEntity(departmentEntity);
            employeeEntity.setEmployeeStatusEnums(EmployeeStatusEnums.ACTIVE);
            employeeEntityList.add(employeeEntity);
        }
        List<EmployeeEntity> createEmployeeEntities = employeeRepository.saveAll(employeeEntityList);
        return employeeMapper.toResponseList(createEmployeeEntities);
    }

//    @Override
//    public List<EmployeeResponseDTO> getAllEmployees() {
//        List<Employee> employee = employeeRepository.findAll();
//        return employeeMapper.toResponseList(employee);
//    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployeeById(Long id, EmployeeRequestDTO employeeRequestDTO) {
        EmployeeEntity employeeEntity = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.EMPLOYEE_NOT_FOUND,
                        "Employee not found with id: " + id
                ));

        DepartmentEntity departmentEntity = departmentRepository
                .findById(employeeRequestDTO.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.DEPARTMENT_NOT_FOUND,
                        "Department not found with id: "
                                + employeeRequestDTO.getDepartmentId()
                ));

        if (employeeRepository.existsByEmployeeEmailAndEmployeeIdNot(
                employeeRequestDTO.getEmployeeEmail(), id)) {

            throw new DuplicateResourceException(
                    ErrorCode.EMPLOYEE_EMAIL_ALREADY_EXISTS,
                    "Employee email already exists: "
                            + employeeRequestDTO.getEmployeeEmail()
            );
        }

        validateMinimumAge(employeeRequestDTO.getDateOfBirth());

        employeeEntity.setEmployeeName(employeeRequestDTO.getEmployeeName());
        employeeEntity.setEmployeeEmail(employeeRequestDTO.getEmployeeEmail());
        employeeEntity.setEmployeePhone(employeeRequestDTO.getEmployeePhone());
        employeeEntity.setEmployeeAddress(employeeRequestDTO.getEmployeeAddress());
        employeeEntity.setEmployeeGenderEnums(employeeRequestDTO.getEmployeeGenderEnums());
        employeeEntity.setDateOfBirth(employeeRequestDTO.getDateOfBirth());
        employeeEntity.setEmployeeTypeEnums(employeeRequestDTO.getEmployeeTypeEnums());
        employeeEntity.setDepartmentEntity(departmentEntity);
        employeeEntity.setBaseSalary(employeeRequestDTO.getBaseSalary());
        EmployeeEntity updateEmployeeEntity = employeeRepository.save(employeeEntity);
        return employeeMapper.toResponse(updateEmployeeEntity);
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {

        EmployeeEntity employeeEntity = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.EMPLOYEE_NOT_FOUND,
                        "Employee not found with id: " + id
                ));

        employeeRepository.delete(employeeEntity);
    }


    @Override
    @Transactional
    public EmployeeResponseDTO changeEmployeeStatus(Long id, EmployeeStatusRequestDTO employeeStatusRequestDTO) {
        EmployeeEntity employeeEntity = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.EMPLOYEE_NOT_FOUND,
                        "Employee not found with id: " + id
                ));

        EmployeeStatusEnums currentStatus = employeeEntity.getEmployeeStatusEnums();
        EmployeeStatusEnums newStatus = employeeStatusRequestDTO.getEmployeeStatusEnums();

        if (currentStatus == newStatus) {
            throw new BusinessRuleException(ErrorCode.INVALID_EMPLOYEE_STATUS_TRANSITION,
                    "Employee is already " + currentStatus);
        }
        if (!currentStatus.canChangeTo(newStatus)) {
            throw new BusinessRuleException(ErrorCode.INVALID_EMPLOYEE_STATUS_TRANSITION,
                    "Employee status cannot change from " + currentStatus + " to " + newStatus);
        }
        employeeEntity.setEmployeeStatusEnums(newStatus);
        EmployeeEntity updatedStatus = employeeRepository.save(employeeEntity);
        return employeeMapper.toResponse(updatedStatus);
    }

//    @Override
//    public List<EmployeeResponseDTO> getAllEmployees(String keyword) {
//        Specification<Employee> employeeSpecification = EmployeeSpecification.hasKeyword(keyword);
//        List<Employee> employeeList = employeeRepository.findAll(employeeSpecification);
//        return employeeMapper.toResponseList(employeeList);
//    }

//    @Override
//    public List<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatus employeeStatus) {
//        Specification<Employee> employeeSpecification = EmployeeSpecification.hasKeyword(keyword);
//        employeeSpecification = employeeSpecification.and(EmployeeSpecification.hasStatus(employeeStatus));
//        List<Employee> employeeList = employeeRepository.findAll(employeeSpecification);
//        return employeeMapper.toResponseList(employeeList);
//    }

//    @Override
//    public List<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatus employeeStatus, Long departmentId) {
//        Specification<Employee> employeeSpecification = EmployeeSpecification.hasKeyword(keyword);
//        employeeSpecification = employeeSpecification.and(EmployeeSpecification.hasStatus(employeeStatus));
//        employeeSpecification = employeeSpecification.and(EmployeeSpecification.hasDepartment(departmentId));
//        List<Employee> employeeList = employeeRepository.findAll(employeeSpecification);
//        return employeeMapper.toResponseList(employeeList);
//    }

//    @Override
//    public List<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatusEnums employeeStatusEnums, Long departmentId, EmployeeGenderEnums employeeGenderEnums) {
//
//        EmployeeStatusEnums statusToFilter = employeeStatusEnums;
//        if (statusToFilter == null) {
//            statusToFilter = EmployeeStatusEnums.ACTIVE;
//        }
//        Specification<EmployeeEntity> employeeSpecification = EmployeeSpecification.hasKeyword(keyword);
//        employeeSpecification = employeeSpecification.and(EmployeeSpecification.hasStatus(statusToFilter));
//        employeeSpecification = employeeSpecification.and(EmployeeSpecification.hasDepartment(departmentId));
//        employeeSpecification = employeeSpecification.and(EmployeeSpecification.hasGender(employeeGenderEnums));
//        List<EmployeeEntity> employeeEntityList = employeeRepository.findAll(employeeSpecification);
//        return employeeMapper.toResponseList(employeeEntityList);
//    }

//    @Override
//    public PageResponseDTO<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatusEnums employeeStatusEnums, Long departmentId, EmployeeGenderEnums employeeGenderEnums, int page, int size) {
//
//        if (page < 0) {
//            throw new InvalidParameterException("Page must not be negative");
//        }
//        if (size < 1 || size > 50) {
//            throw new InvalidParameterException("Size must be between 1 to 50");
//        }
//        EmployeeStatusEnums statusToFilter = employeeStatusEnums;
//        if (statusToFilter == null) {
//            statusToFilter = EmployeeStatusEnums.ACTIVE;
//        }
//        Pageable pageable = PageRequest.of(page, size, Sort.by("employeeId"));
//        Specification<EmployeeEntity> employeeEntitySpecification = EmployeeSpecification.hasKeyword(keyword);
//        employeeEntitySpecification = employeeEntitySpecification.and(EmployeeSpecification.hasStatus(statusToFilter));
//        employeeEntitySpecification = employeeEntitySpecification.and(EmployeeSpecification.hasDepartment(departmentId));
//        employeeEntitySpecification = employeeEntitySpecification.and(EmployeeSpecification.hasGender(employeeGenderEnums));
//        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(employeeEntitySpecification, pageable);
//        List<EmployeeResponseDTO> content = employeeMapper.toResponseList(employeeEntityPage.getContent());
//        return new PageResponseDTO<>(
//                content,
//                employeeEntityPage.getNumber(),
//                employeeEntityPage.getSize(),
//                employeeEntityPage.getTotalElements(),
//                employeeEntityPage.getTotalPages(),
//                employeeEntityPage.isFirst(),
//                employeeEntityPage.isLast()
//
//        );
//    }


    //for sorting

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("employeeName", "baseSalary", "hireDate");

    private Sort buildSort(String sort) {

        if (sort == null || sort.isBlank()) {
            return Sort.by("employeeId").ascending();
        }

        String[] sortParts = sort.split(",");
        String field = sortParts[0].trim();

        String direction = "asc";
        if (sortParts.length > 1) {
            direction = sortParts[1].trim();
        }

        if (!ALLOWED_SORT_FIELDS.contains(field)) {
            throw new InvalidParameterException(ErrorCode.INVALID_SORTING_PARAMETER,
                    "Sort field must be one of: employeeName, baseSalary, hireDate");
        }
        Sort.Direction sortDirection;
        if (direction.equalsIgnoreCase("asc")) {
            sortDirection = Sort.Direction.ASC;
        } else if (direction.equalsIgnoreCase("desc")) {
            sortDirection = Sort.Direction.DESC;
        } else {
            throw new InvalidParameterException(ErrorCode.INVALID_SORTING_PARAMETER,
                    "Sort direction must be asc or desc");
        }

        return Sort.by(sortDirection, field).and(Sort.by("employeeId").ascending());
    }


    @Override
    public PageResponseDTO<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatusEnums employeeStatusEnums, Long departmentId, EmployeeGenderEnums employeeGenderEnums, int page, int size, String sort) {
        if (page < 0) {
            throw new InvalidParameterException(ErrorCode.INVALID_PAGINATION_PARAMETER,
                    "Page must not be negative");
        }
        if (size < 1 || size > MAX_PAGE_SIZE) {
            throw new InvalidParameterException(ErrorCode.INVALID_PAGINATION_PARAMETER,
                    "Size must be between 1 and " + MAX_PAGE_SIZE);
        }
        EmployeeStatusEnums statusToFilter = employeeStatusEnums;
        if (statusToFilter == null) {
            statusToFilter = EmployeeStatusEnums.ACTIVE;
        }
        Pageable pageable = PageRequest.of(page, size, buildSort(sort));
        Specification<EmployeeEntity> employeeEntitySpecification = EmployeeSpecification.hasKeyword(keyword);
        employeeEntitySpecification = employeeEntitySpecification.and(EmployeeSpecification.hasStatus(statusToFilter));
        employeeEntitySpecification = employeeEntitySpecification.and(EmployeeSpecification.hasDepartment(departmentId));
        employeeEntitySpecification = employeeEntitySpecification.and(EmployeeSpecification.hasGender(employeeGenderEnums));
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(employeeEntitySpecification, pageable);
        List<EmployeeResponseDTO> content = employeeMapper.toResponseList(employeeEntityPage.getContent());
        return new PageResponseDTO<>(content, employeeEntityPage.getNumber(), employeeEntityPage.getSize(), employeeEntityPage.getTotalElements(), employeeEntityPage.getTotalPages(), employeeEntityPage.isFirst(), employeeEntityPage.isLast()
        );
    }


}
