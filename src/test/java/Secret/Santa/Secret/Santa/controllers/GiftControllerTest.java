package Secret.Santa.Secret.Santa.controllers;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.services.IGiftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftControllerTest {
    private MockMvc mockMvc;

    @Mock
    private IGiftService giftService;

    @InjectMocks
    private GiftController giftController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(giftController).build();
    }

    @Test
    void getAllGifts() throws Exception {
        List<Gift> gifts = Arrays.asList(new Gift(), new Gift());
        when(giftService.getAllGifts()).thenReturn(gifts);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gifts"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(gifts.size()));
    }

    @Test
    void getGiftById() throws Exception {
        int giftId = 1;
        Gift gift = new Gift();
        when(giftService.getGiftById(giftId)).thenReturn(gift);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gifts/{giftId}", giftId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(gift.getName()));
    }

    @Test
    void createGift() throws Exception {
//        GiftDTO giftDto = new GiftDTO();
//        Gift createdGift = new Gift();
//
//        when(giftService.createGift(giftDto)).thenReturn(createdGift);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/gifts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(giftDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(createdGift.getName()));
//
//        verify(giftService, times(1)).createGift(giftDto);
    }


    @Test
    void updateGift() throws Exception {
//        int giftId = 1;
//        GiftDTO updatedGiftDto = new GiftDTO();
//        Gift updatedGift = new Gift();
//
//        when(giftService.updateGift(eq(giftId), eq(updatedGiftDto))).thenReturn(updatedGift);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/gifts/{giftId}", giftId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(updatedGiftDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedGift.getName()));
//
//        verify(giftService, times(1)).updateGift(giftId, updatedGiftDto);
    }


    @Test
    void deleteGift() throws Exception {
        int giftId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/gifts/{giftId}", giftId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(giftService, times(1)).deleteGift(giftId);
    }
}