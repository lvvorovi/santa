package Secret.Santa.Secret.Santa.services.impl;

import Secret.Santa.Secret.Santa.models.Gift;
import Secret.Santa.Secret.Santa.repos.IGiftRepo;
import Secret.Santa.Secret.Santa.services.IGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftServiceImpl implements IGiftService {
    @Autowired
    IGiftRepo iGiftRepo;

    @Override
    public List<Gift> getAllGifts() {
        return iGiftRepo.findAll();
    }

    @Override
    public Gift getGiftById(int giftId) {
        Optional<Gift> optionalGift = iGiftRepo.findById(giftId);
        return optionalGift.orElse(null);    }

    @Override
    public Gift createGift(Gift gift) {
        return iGiftRepo.save(gift);
    }

    @Override
    public Gift updateGift(int giftId, Gift updatedGift) {
        Gift existingGift = getGiftById(giftId);

        if (existingGift != null) {
            existingGift.setName(updatedGift.getName());
            existingGift.setDescription(updatedGift.getDescription());
            existingGift.setLink(updatedGift.getLink());
            existingGift.setPrice(updatedGift.getPrice());
            existingGift.setGroup(updatedGift.getGroup());

            return iGiftRepo.save(existingGift);
        }
        return null;
    }

    @Override
    public void deleteGift(int giftId) {
        iGiftRepo.deleteById(giftId);
    }
}
