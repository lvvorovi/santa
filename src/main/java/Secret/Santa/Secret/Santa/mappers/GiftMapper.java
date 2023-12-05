package Secret.Santa.Secret.Santa.mappers;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import org.springframework.stereotype.Component;

@Component
public class GiftMapper {
    public static Gift toGift(GiftDTO giftDTO, Gift gift) {
        if (gift == null){
            gift = new Gift();
        }
        gift.setName(giftDTO.getName());
        gift.setDescription(giftDTO.getDescription());
        gift.setLink(giftDTO.getLink());
        gift.setPrice(giftDTO.getPrice());
        gift.setCreated_by(giftDTO.getCreated_by());
        gift.setGroup(giftDTO.getGroup());
        return gift;
    }

    public static GiftDTO toGiftDTO(Gift gift) {

        GiftDTO giftDTO = new GiftDTO();

        giftDTO.setName(gift.getName());
        giftDTO.setDescription(gift.getDescription());
        giftDTO.setLink(gift.getLink());
        giftDTO.setPrice(gift.getPrice());
        giftDTO.setCreated_by(gift.getCreated_by());
        giftDTO.setGroup(gift.getGroup());

        return giftDTO;
    }


}
