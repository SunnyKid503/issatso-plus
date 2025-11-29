package com.university.forum.forummanagement.forums.services.implementations;

import com.university.forum.forummanagement.forums.dtos.requests.CreateAnnouncementRequestDto;
import com.university.forum.forummanagement.forums.dtos.responses.AnnouncementResponseDto;
import com.university.forum.forummanagement.forums.models.Announcement;
import com.university.forum.forummanagement.forums.models.Event;
import com.university.forum.forummanagement.forums.models.FileReference;
import com.university.forum.forummanagement.forums.repositories.AnnouncementRepository;
import com.university.forum.forummanagement.forums.repositories.FileReferenceRepository;
import com.university.forum.forummanagement.forums.repositories.PostCategoriesRepository;
import com.university.forum.forummanagement.forums.services.abstracts.PostServiceBase;
import com.university.forum.forummanagement.forums.services.interfaces.AnnouncementService;
import com.university.forum.forummanagement.membership.models.Administrator;
import com.university.forum.forummanagement.membership.models.Member;
import com.university.forum.forummanagement.membership.models.Professor;
import com.university.forum.forummanagement.membership.models.Student;
import com.university.forum.forummanagement.membership.models.asbtracts.Personnel;
import com.university.forum.forummanagement.membership.repositories.MemberRepository;
import com.university.forum.forummanagement.membership.repositories.PersonnelRepository;
import com.university.forum.forummanagement.membership.repositories.ProfessorRepository;
import com.university.forum.forummanagement.membership.repositories.StudentRepository;
import com.university.forum.forummanagement.shared.exceptions.ElementNotFoundException;
import com.university.forum.forummanagement.shared.exceptions.FileUploadException;
import com.university.forum.forummanagement.shared.exceptions.IllegalOperationException;
import com.university.forum.forummanagement.shared.services.FileManagementService;
import com.university.forum.forummanagement.structures.models.ClassGroup;
import com.university.forum.forummanagement.structures.models.TaggableObject;
import com.university.forum.forummanagement.structures.repositories.TaggableObjectRepository;
import com.university.forum.usermanagement.Dtos.Message.NotificationMessage;
import com.university.forum.usermanagement.Models.Enum.NotificationTopic;
import com.university.forum.usermanagement.Models.Enum.NotificationType;
import com.university.forum.usermanagement.Services.MemberNotificationProducer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl extends PostServiceBase implements AnnouncementService {
    protected final PersonnelRepository personnelRepository;
    protected final FileManagementService fileManagementService;
    protected final FileReferenceRepository fileReferenceRepository;
    protected final AnnouncementRepository announcementRepository;
    protected final MemberNotificationProducer memberNotificationProducer;
    protected final StudentRepository studentRepository;
    protected final ProfessorRepository professorRepository;

    public AnnouncementServiceImpl(
            MemberRepository memberRepository,
            PostCategoriesRepository categoriesRepository,
            TaggableObjectRepository taggableObjectRepository,
            PersonnelRepository personnelRepository,
            FileManagementService fileManagementService,
            FileReferenceRepository fileReferenceRepository,
            AnnouncementRepository announcementRepository,
            MemberNotificationProducer memberNotificationProducer, StudentRepository studentRepository, ProfessorRepository professorRepository) {
        super(memberRepository, categoriesRepository, taggableObjectRepository);
        this.personnelRepository = personnelRepository;
        this.fileManagementService = fileManagementService;
        this.fileReferenceRepository = fileReferenceRepository;
        this.announcementRepository = announcementRepository;
        this.memberNotificationProducer = memberNotificationProducer;
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public List<AnnouncementResponseDto> get() {
        return announcementRepository.findAll(Sort.by("updatedAt").descending())
                .stream()
                .map(AnnouncementResponseDto::toAnnouncementResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnouncementResponseDto> get(UUID userId, Set<String> roles) throws ElementNotFoundException, IllegalOperationException {

        List<TaggableObject> memberTags = loadMemberTags(userId, roles.iterator().next());
        return announcementRepository.findByAnyTag(memberTags.stream().map(TaggableObject::getId).toList())
                .stream()
                .map(AnnouncementResponseDto::toAnnouncementResponseDto)
                .collect(Collectors.toList());
    }

    protected List<TaggableObject> loadMemberTags(UUID memberId, String role) throws ElementNotFoundException, IllegalOperationException {
        List<TaggableObject> tags = new ArrayList<>();
        System.out.println(role);
        switch(role){
            case "ROLE_STUDENT": {
                Student student = studentRepository.findById(memberId)
                        .orElseThrow(() -> new ElementNotFoundException("Member does not exist."));
                return getByClassGroup(student.getClassGroup());
            }
            case "ROLE_PROFESSOR":{
                Professor professor = professorRepository.findById(memberId)
                        .orElseThrow(() -> new ElementNotFoundException("Member does not exist."));
                if(professor.getClassGroups() == null || professor.getClassGroups().isEmpty())
                    return taggableObjectRepository.findAll();
                for(ClassGroup classGroup : professor.getClassGroups())
                    tags.addAll(getByClassGroup(classGroup));
                return tags;
            }
            case "ROLE_ADMINISTRATOR": {
                return taggableObjectRepository.findAll();
            }

            default: throw new IllegalOperationException("Invalid Role "+role);
        }

    }



    protected List<TaggableObject> getByClassGroup(ClassGroup classGroup)
    {
        List<TaggableObject> tags = new ArrayList<>();
        if(classGroup == null)
            return tags;
        tags.add(classGroup);
        if(classGroup.getLevelOfStudy() == null)
            return tags;
        tags.add(classGroup.getLevelOfStudy());
        if(classGroup.getLevelOfStudy().getSpeciality() == null)
            return tags;
        tags.add(classGroup.getLevelOfStudy().getSpeciality());
        if(classGroup.getLevelOfStudy().getSpeciality().getBranch() == null)
            return tags;
        tags.add(classGroup.getLevelOfStudy().getSpeciality().getBranch());
        if(classGroup.getLevelOfStudy().getSpeciality().getBranch().getDepartment() == null)
            return tags;
        tags.add(classGroup.getLevelOfStudy().getSpeciality().getBranch().getDepartment());

        return tags;
    }

    @Override
    public AnnouncementResponseDto create(UUID memberId, CreateAnnouncementRequestDto dto) throws ElementNotFoundException, FileUploadException {
        Announcement announcement = new Announcement();
        announcement.setTitle(dto.getTitle());
        announcement.setCreatedAt(dto.getCreatedAt());
        announcement.setContent(dto.getContent());
        announcement.setTags(loadTaggedObjects(dto.getTagIds()));
        announcement.setAuthor(loadAuthor(memberId));
        announcement.setLongDescription(dto.getLongDescription());
        announcement.setCreatedAt(dto.getCreatedAt());

        List<FileReference> uploadedFiles = fileManagementService.uploadAll(dto.getFiles());

        if(uploadedFiles != null && !uploadedFiles.isEmpty()) {
            fileReferenceRepository.saveAll(uploadedFiles);
            announcement.setFiles(uploadedFiles);
        }

        announcementRepository.save(announcement);
        memberNotificationProducer.sendNotification(buildAnnouncementNotification(announcement));
        return AnnouncementResponseDto.toAnnouncementResponseDto(announcement);
    }

    @Override
    protected Personnel loadAuthor(UUID memberId) throws ElementNotFoundException {
        return personnelRepository.findById(memberId)
                .orElseThrow(() -> new ElementNotFoundException("Member does not exist."));
    }

    protected NotificationMessage buildAnnouncementNotification(Announcement announcement)
    {
        String imageUrl = null;
        if(!announcement.getFiles().isEmpty())
            imageUrl = announcement.getFiles().get(0).getImageUrl();

        return new NotificationMessage(
                String.format("Event %s", announcement.getTitle()),
                String.format("This announcement has been created by %s %s \uD83D\uDCA1",
                        announcement.getAuthor().getFirstName(),
                        announcement.getAuthor().getLastName()
                ),
                NotificationType.NEW_ACTUALITY,
                imageUrl,
                "",
                NotificationTopic.ACTUALITY_UPDATE,
                memberRepository.findAll().stream().map(Member::getId).toList());
    }

}
