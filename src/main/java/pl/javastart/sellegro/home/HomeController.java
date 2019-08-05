package pl.javastart.sellegro.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.javastart.sellegro.auction.Auction;
import pl.javastart.sellegro.auction.AuctionRepository;

import java.util.Random;

@Controller
public class HomeController {

    private AuctionRepository auctionRepository;
    private static final String[] ADJECTIVES = {"Niesamowity", "Jedyny taki", "IGŁA", "HIT", "Jak nowy",
            "Perełka", "OKAZJA", "Wyjątkowy"};

    public HomeController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
        Random random = new Random();

        for(Auction auction : auctionRepository.findAll()){
            if(auction.getTitle() == null || auction.getTitle().equals("")) {
                auction.setTitle(ADJECTIVES[random.nextInt(ADJECTIVES.length)] + " " + auction.getCarMake() + " " + auction.getCarModel());
                auctionRepository.save(auction);
            }
        }
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("cars", auctionRepository.findMostExpensiveTop(4));
        return "home";
    }
}
