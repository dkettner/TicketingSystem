package com.kett.TicketSystem.project.application;

import com.kett.TicketSystem.TicketSystemService;
import com.kett.TicketSystem.project.domain.Project;
import com.kett.TicketSystem.project.domain.ProjectException;
import com.kett.TicketSystem.project.domain.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final TicketSystemService ticketSystemService;

    @Autowired
    public ProjectController(TicketSystemService ticketSystemService) {
        this.ticketSystemService = ticketSystemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable UUID id) {
        Project project = ticketSystemService.getProjectById(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    // Exception Handlers

    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<String> handleProjectException(ProjectException projectException) {
        return new ResponseEntity<>(projectException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<String> handleProjectNotFoundException(ProjectNotFoundException projectNotFoundException) {
        return new ResponseEntity<>(projectNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
