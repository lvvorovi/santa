package Secret.Santa.Secret.Santa.services;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;

import java.util.List;

public interface IGiftService {

    List<Gift> getAllGifts();

    Gift getGiftById(int giftId);

    Gift createGift(Integer userId, GiftDTO giftDTO);

    GiftDTO updateGift(int giftId, GiftDTO giftDTO);

    boolean deleteGift(int giftId);

    List<Gift> getGiftsCreatedBy(int userId);


}
