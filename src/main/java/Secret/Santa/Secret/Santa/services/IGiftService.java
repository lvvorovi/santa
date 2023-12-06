package Secret.Santa.Secret.Santa.services;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;

import java.util.List;

public interface IGiftService {

    List<GiftDTO> getAllGifts();

    GiftDTO getGiftById(int giftId);

    GiftDTO createGift(GiftDTO giftDTO);

    GiftDTO updateGift(GiftDTO giftDTO);

    boolean deleteGift(int giftId);

}
