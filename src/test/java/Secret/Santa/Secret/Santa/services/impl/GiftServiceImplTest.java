package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.mappers.GiftMapper;
import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftServiceImplTest {

    @Mock
    private IGiftRepo giftRepo;

    @Mock
    private GiftMapper giftMapper;

    @InjectMocks
    private GiftServiceImpl giftService;

    @Test
    void getAllGifts() {
        List<Gift> expectedGifts = new ArrayList<>();
        when(giftRepo.findAll()).thenReturn(expectedGifts);

        List<Gift> actualGifts = giftService.getAllGifts();
        assertSame(expectedGifts, actualGifts);
    }

    @Test
    void getGiftById() {
        int giftId = 1;
        Gift expectedGift = new Gift();
        when(giftRepo.findById(giftId)).thenReturn(Optional.of(expectedGift));

        Gift actualGift = giftService.getGiftById(giftId);

        assertSame(expectedGift, actualGift);
    }

    @Test
    void createGift() {
        GiftDTO giftDto = new GiftDTO();
        Gift expectedGift = new Gift();
        when(giftRepo.save(any(Gift.class))).thenReturn(expectedGift);

        Gift actualGift = giftService.createGift(giftDto);
        assertSame(expectedGift, actualGift);
    }

    @Test
    void updateGift_GiftNotFound() {
        int giftId = 1;
        GiftDTO updatedGiftDTO = new GiftDTO();

        when(giftRepo.existsById(giftId)).thenReturn(false);

        when(giftRepo.findById(giftId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> giftService.updateGift(giftId, updatedGiftDTO));

        verify(giftRepo).existsById(giftId);
        verify(giftRepo).findById(giftId); // Ensure findById is called
        verify(giftRepo, never()).save(any(Gift.class));
        GiftMapper.toGiftDTO(any(Gift.class));
    }

    @Test
    void deleteGift() {
        int giftId = 1;
        assertThrows(EntityNotFoundException.class, () -> giftService.deleteGift(giftId));
        verify(giftRepo, never()).deleteById(giftId);
    }


}