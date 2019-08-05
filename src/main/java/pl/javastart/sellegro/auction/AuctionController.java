package pl.javastart.sellegro.auction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuctionController {

    private AuctionRepository auctionRepository;

    public AuctionController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @GetMapping("/auctions")
    public String auctions(Model model,
            @RequestParam(required = false) String sort,
            AuctionFilters auctionFilters) {

        List<Auction> auctions;

        if (sort != null) {
            auctions = auctionRepository.findAllOrderedBy(sort);
        } else {
            auctions = auctionRepository.findAll();
            auctions = auctions.stream().filter(auction -> auctionFilters.getTitle() == null || auction.getTitle().toUpperCase().contains(auctionFilters.getTitle().toUpperCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("cars", auctions);
        model.addAttribute("filters", auctionFilters);
        return "auctions";
    }
}
