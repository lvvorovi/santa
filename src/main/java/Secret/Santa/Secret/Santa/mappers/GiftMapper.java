package Secret.Santa.Secret.Santa.mappers;

import Secret.Santa.Secret.Santa.models.DTO.GiftDTO;
import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.models.Group;
import Secret.Santa.Secret.Santa.validationUnits.GroupUtils;
import org.springframework.stereotype.Component;

@Component
public class GiftMapper {
    private static GroupUtils groupUtils;

    public GiftMapper(GroupUtils groupUtils) {
        this.groupUtils = groupUtils;
    }

    public static Gift toGift(GiftDTO giftDTO) {
        Gift gift = new Gift();
        gift.setName(giftDTO.getName());
        gift.setDescription(giftDTO.getDescription());
        gift.setLink(giftDTO.getLink());
        gift.setPrice(giftDTO.getPrice());
        gift.setCreatedBy(giftDTO.getCreatedBy());

        // Assuming you have a GroupRepository to fetch the Group based on groupId
        Group group = groupUtils.getGroupById(giftDTO.getGroupId());
        gift.setGroup(group);

        return gift;
    }

    public static GiftDTO toGiftDTO(Gift gift) {
        GiftDTO giftDTO = new GiftDTO();
        giftDTO.setName(gift.getName());
        giftDTO.setDescription(gift.getDescription());
        giftDTO.setLink(gift.getLink());
        giftDTO.setPrice(gift.getPrice());
        giftDTO.setCreatedBy(gift.getCreatedBy());

        if (gift.getGroup() != null) {
            giftDTO.setGroupId(gift.getGroup().getGroupId()); // Assuming groupId is accessible
        }

        return giftDTO;
    }
}

