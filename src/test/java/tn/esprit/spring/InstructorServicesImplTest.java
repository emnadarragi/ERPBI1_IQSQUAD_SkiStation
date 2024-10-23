package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InstructorServicesImplTest {

    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddInstructor() {
        // Initialize the object here
        Instructor instructor = new Instructor(1L, "John", "Doe", null, new HashSet<>());
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructor(instructor);

        assertNotNull(savedInstructor);
        assertEquals(instructor.getFirstName(), savedInstructor.getFirstName());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    public void testRetrieveAllInstructors() {
        // Initialize the object here
        Instructor instructor = new Instructor(1L, "John", "Doe", null, new HashSet<>());
        when(instructorRepository.findAll()).thenReturn(List.of(instructor));

        List<Instructor> instructorList = instructorServices.retrieveAllInstructors();

        assertNotNull(instructorList);
        assertEquals(1, instructorList.size());
        verify(instructorRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateInstructor() {
        // Initialize the object here
        Instructor instructor = new Instructor(1L, "John", "Doe", null, new HashSet<>());
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor updatedInstructor = instructorServices.updateInstructor(instructor);

        assertNotNull(updatedInstructor);
        assertEquals(instructor.getLastName(), updatedInstructor.getLastName());
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    public void testRetrieveInstructor() {
        // Initialize the object here
        Instructor instructor = new Instructor(1L, "John", "Doe", null, new HashSet<>());
        when(instructorRepository.findById(anyLong())).thenReturn(Optional.of(instructor));

        Instructor foundInstructor = instructorServices.retrieveInstructor(1L);

        assertNotNull(foundInstructor);
        assertEquals(instructor.getFirstName(), foundInstructor.getFirstName());
        verify(instructorRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddInstructorAndAssignToCourse() {
        // Initialize the objects here
        Instructor instructor = new Instructor(1L, "John", "Doe", null, new HashSet<>());
        Course course = new Course(1L, 1, null, null, 1000.0f, 10, null);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(instructorRepository.save(any(Instructor.class))).thenReturn(instructor);

        Instructor savedInstructor = instructorServices.addInstructorAndAssignToCourse(instructor, 1L);

        assertNotNull(savedInstructor);
        assertEquals(1, savedInstructor.getCourses().size());
        verify(courseRepository, times(1)).findById(1L);
        verify(instructorRepository, times(1)).save(instructor);
    }
}
