package com.university.forum.forummanagement.forums.services.implementations;

import com.university.forum.forummanagement.forums.dtos.requests.CreateEventRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.EventResponseDto;
import com.university.forum.forummanagement.forums.models.Event;
import com.university.forum.forummanagement.forums.models.FileReference;
import com.university.forum.forummanagement.forums.repositories.EventRepository;
import com.university.forum.forummanagement.forums.repositories.FileReferenceRepository;
import com.university.forum.forummanagement.forums.repositories.PostCategoriesRepository;
import com.university.forum.forummanagement.forums.services.abstracts.PostServiceBase;
import com.university.forum.forummanagement.forums.services.interfaces.EventService;
import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.repositories.MemberRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;
import com.university.forum.forummanagement.shared.services.FileManagementService;
import com.university.forum.forummanagement.structures.repositories.TaggableObjectRepository;
import com.university.forum.usermanagement.Dtos.Message.NotificationMessage;
import com.university.forum.usermanagement.Models.Enum.NotificationTopic;
import com.university.forum.usermanagement.Models.Enum.NotificationType;
import com.university.forum.usermanagement.Services.MemberNotificationProducer;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends PostServiceBase implements EventService{
    private final EventRepository eventRepository;
    private final FileManagementService fileManagementService;
    private final FileReferenceRepository fileReferenceRepository;
    private final MemberNotificationProducer memberNotificationProducer;

    public EventServiceImpl(MemberRepository memberRepository,
                            PostCategoriesRepository categoriesRepository,
                            TaggableObjectRepository taggableObjectRepository,
                            EventRepository eventRepository,
                            FileManagementService fileManagementService, FileReferenceRepository fileReferenceRepository, MemberNotificationProducer memberNotificationProducer) {
        super(memberRepository, categoriesRepository, taggableObjectRepository);
        this.eventRepository = eventRepository;
        this.fileManagementService = fileManagementService;
        this.fileReferenceRepository = fileReferenceRepository;
        this.memberNotificationProducer = memberNotificationProducer;
    }

    @Override
    public List<EventResponseDto> get() {
        return eventRepository.findAll()
                .stream().map(EventResponseDto::toEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDto create(UUID userId, CreateEventRequestDto dto) throws ElementNotFoundException, FileUploadException {
        if(dto.getFiles() == null || dto.getFiles().length == 0)
            throw new IllegalArgumentException("An image file is required for the banner.");

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setCreatedAt(dto.getCreatedAt());
        event.setContent(dto.getContent());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());

        event.setCategories(loadCategories(dto.getCategoryIds()));
        event.setTags(loadTaggedObjects(dto.getTagIds()));
        event.setAuthor(loadAuthor(userId));
        event.setAttendees(0);
        event.setCapacity(dto.getCapacity());

        List<FileReference> uploadedFiles = fileManagementService
                .uploadAll(dto.getFiles());
        if(uploadedFiles == null || uploadedFiles.isEmpty())
            throw new FileUploadException("Failed to upload files.");

        fileReferenceRepository.saveAll(uploadedFiles);
        FileReference banner =  uploadedFiles.remove(0);
        event.setBanner(banner);
        event.setFiles(uploadedFiles);

        eventRepository.save(event);
        memberNotificationProducer.sendNotification(buildEventNotification(event));
        return EventResponseDto.toEventResponseDto(event);
    }

    protected NotificationMessage buildEventNotification(Event event)
    {
        return new NotificationMessage(
                String.format("Event %s", event.getTitle()),
                String.format("This event has been created by %s %s \uD83D\uDCA1",
                        event.getAuthor().getFirstName(),
                        event.getAuthor().getLastName()
                ),
                NotificationType.EVENT,
                event.getBanner().getImageUrl(),
                "",
                NotificationTopic.NEWS_UPDATE,
                memberRepository.findAll().stream().map(Member::getId).toList());
    }
}
