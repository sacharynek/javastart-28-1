package pl.javastart.sellegro.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

            Sort columnSort = Sort.by(Sort.Direction.ASC, sort);
            Pageable page = PageRequest.of(0,1,columnSort);

            Page<Auction> auctionsPage = auctionRepository.findAll(page);

        } else{
            auctions = auctionRepository.findAll();
        }

        auctions = auctions
                .stream()
                .filter(auction -> auctionFilters.getTitle() == null || auction.getTitle().toUpperCase().contains(auctionFilters.getTitle().toUpperCase()))
                .filter(auction -> auctionFilters.getCarMaker()== null || auction.getCarMake().toUpperCase().contains(auctionFilters.getCarMaker().toUpperCase()))
                .filter(auction -> auctionFilters.getCarModel() == null || auction.getCarModel().toUpperCase().contains(auctionFilters.getCarModel().toUpperCase()))
                .filter(auction -> auctionFilters.getColor() == null || auction.getColor().toUpperCase().contains(auctionFilters.getColor().toUpperCase()))
                .collect(Collectors.toList());

        for (Auction auction : auctions) {
            System.out.println(auction);
        }
        model.addAttribute("cars", auctions);
        model.addAttribute("filters", auctionFilters);
        return "auctions";
    }
}
