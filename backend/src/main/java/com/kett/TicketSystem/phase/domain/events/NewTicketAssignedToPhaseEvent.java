package com.kett.TicketSystem.phase.domain.events;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class NewTicketAssignedToPhaseEvent {
    private final UUID id;
    private final LocalDateTime timeStamp;
    private final UUID phaseId;
    private final UUID ticketId;
    private final UUID projectId;

    public NewTicketAssignedToPhaseEvent(UUID phaseId, UUID ticketId, UUID projectId) {
        this.id = UUID.randomUUID();
        this.timeStamp = LocalDateTime.now();
        this.phaseId = phaseId;
        this.ticketId = ticketId;
        this.projectId = projectId;
    }
}
