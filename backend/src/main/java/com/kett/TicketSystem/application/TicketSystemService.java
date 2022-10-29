package com.kett.TicketSystem.application;

import com.kett.TicketSystem.project.application.ProjectService;
import com.kett.TicketSystem.project.application.dto.*;
import com.kett.TicketSystem.project.domain.Project;
import com.kett.TicketSystem.ticket.application.dto.TicketPatchDto;
import com.kett.TicketSystem.ticket.application.dto.TicketPostDto;
import com.kett.TicketSystem.ticket.application.dto.TicketResponseDto;
import com.kett.TicketSystem.ticket.domain.Ticket;
import com.kett.TicketSystem.user.application.UserService;
import com.kett.TicketSystem.user.application.dto.UserResponseDto;
import com.kett.TicketSystem.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketSystemService {
    private final ProjectService projectService;
    private final UserService userService;
    private final DtoMapper dtoMapper;

    @Autowired
    public TicketSystemService (ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
        this.dtoMapper = new DtoMapper();
    }

    public ProjectResponseDto fetchProjectById(UUID id) {
        Project project = projectService.getProjectById(id);
        return dtoMapper.mapProjectToProjectResponseDto(project);
    }

    public List<TicketResponseDto> fetchTicketsByProjectId(UUID id) {
        List<Ticket> tickets = projectService.getTicketsByProjectId(id);
        return dtoMapper.mapTicketListToTicketResponseDtoList(tickets);
    }

    public TicketResponseDto fetchTicketByProjectIdAndTicketNumber(UUID id, UUID ticketNumber) {
        Ticket ticket = projectService.getTicketByProjectIdAndTicketNumber(id, ticketNumber);
        return dtoMapper.mapTicketToTicketResponseDto(ticket);
    }

    public ProjectResponseDto addProject(ProjectPostDto projectPostDto) {
        Project project = projectService.addProject(
                dtoMapper.mapProjectPostDtoToProject(projectPostDto)
        );
        return dtoMapper.mapProjectToProjectResponseDto(project);
    }

    public void deleteProjectById(UUID id) {
        projectService.deleteProjectById(id);
    }

    // TODO: clean this up
    public void patchProjectById(UUID id, ProjectPatchDto projectPatchDto) {
        projectService.patchProjectById(
                id, projectPatchDto.getName(),
                projectPatchDto.getDescription(),
                projectPatchDto.getMemberIds());
    }

    public TicketResponseDto addTicketToProject(UUID id, TicketPostDto ticketPostDto) {
        Ticket ticket = projectService.addTicketToProject(id, dtoMapper.mapTicketPostDtoToTicket(ticketPostDto));
        return dtoMapper.mapTicketToTicketResponseDto(ticket);
    }

    public void deleteTicketByProjectIdAndTicketNumber(UUID id, UUID ticketNumber) {
        projectService.deleteTicketByProjectIdAndTicketNumber(id, ticketNumber);
    }

    // TODO: clean this up
    public void patchTicket(UUID id, UUID ticketNumber, TicketPatchDto ticketPatchDto) {
        projectService.patchTicket(
                id,
                ticketNumber,
                ticketPatchDto.getTitle(),
                ticketPatchDto.getDescription(),
                ticketPatchDto.getDueTime(),
                ticketPatchDto.getTicketStatus(),
                ticketPatchDto.getAssigneeIds()
        );
    }

    public List<ProjectResponseDto> fetchAllProjects() {
        List<Project> allProjects = projectService.getAllProjects();
        return allProjects
                .stream()
                .map(dtoMapper::mapProjectToProjectResponseDto)
                .toList();
    }

    public UserResponseDto getUserById(UUID id) {
        User user = userService.getUserById(id);
        return dtoMapper.mapUserToUserResponseDto(user);
    }
}
