package guru.springframework.recipes.services;

import guru.springframework.recipes.commands.UnitOfMeasureCommand;
import guru.springframework.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipes.domain.UnitOfMeasure;
import guru.springframework.recipes.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;
    UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, converter);
    }

    @Test
    void listAllUoms() {
        Set<UnitOfMeasure> uoms = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        UnitOfMeasure uom2 = new UnitOfMeasure();

        uom1.setId(1L);
        uom2.setId(2L);

        uoms.add(uom1);
        uoms.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(uoms);

        Set<UnitOfMeasureCommand> uomCommands = service.listAllUoms();

        assertEquals(2, uomCommands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}