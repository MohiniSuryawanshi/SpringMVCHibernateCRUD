package com.employee.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.employee.bean.Employee;
import com.employee.service.EmployeeService;

@RestController
public class EmployeeController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger
		.getLogger(EmployeeController.class);

	public EmployeeController() {
		System.out.println("EmployeeController()");
	}

	@Autowired
	private EmployeeService employeeService;
	
	
	
	@RequestMapping(value = "/")
	public ModelAndView listEmployee(ModelAndView model) throws IOException
	{
		List<Employee> listEmployee = employeeService.getAllEmployees();
		model.addObject("listEmployee", listEmployee);
		model.setViewName("home");
		return model;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
    public  List<Employee> listAllUsers() {
        List<Employee> Employeelist = employeeService.getAllEmployees();
        if(Employeelist.isEmpty())
        {
            return null;
        }
        return Employeelist;
    }
	@RequestMapping(value = "/newEmployee", method = RequestMethod.GET)
	public ModelAndView newContact(ModelAndView model) {
		Employee employee = new Employee();
		model.addObject("employee", employee);
		model.setViewName("EmployeeForm");
		return model;
	}

	@RequestMapping(value = "/saveEmployee", method = RequestMethod.POST)
	public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
		if (employee.getId() == 0) { // if employee id is 0 then creating the
			// employee other updating the employee
			employeeService.addEmployee(employee);
		} else {
			employeeService.updateEmployee(employee);
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(HttpServletRequest request) {
		int employeeId = Integer.parseInt(request.getParameter("id"));
		employeeService.deleteEmployee(employeeId);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
	public ModelAndView editContact(HttpServletRequest request) {
		int employeeId = Integer.parseInt(request.getParameter("id"));
		Employee employee = employeeService.getEmployee(employeeId);
		ModelAndView model = new ModelAndView("EmployeeForm");
		model.addObject("employee", employee);

		return model;
	}

	/* @RequestMapping(value = "/new", method = RequestMethod.GET)
	    public ResponseEntity<List<Employee>> listAllUsers() {
	        List<Employee> users = employeeService.getAllEmployees();
	        if(users.isEmpty()){
	            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
	        }
	        return new ResponseEntity<List<Employee>>(users, HttpStatus.OK);
	    }*/
	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	 public ResponseEntity<Iterable<Employee>> employees() {
System.out.println("hhjh");
	  //HttpHeaders headers = new HttpHeaders();
	  Iterable<Employee> employees = employeeService.getAllEmployees();

	  if (employees == null) {
	   return new ResponseEntity<Iterable<Employee>>(HttpStatus.NOT_FOUND);
	  }
	  //headers.add("Number Of Records Found", String.valueOf(employees.size()));
	  return new ResponseEntity<Iterable<Employee>>(employees, HttpStatus.OK);
	 }
}