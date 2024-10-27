package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.NewsService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Tuple;
import javax.servlet.ServletContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/land")
public class LandController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    LandRepository landRepository;

    @RequestMapping("/land-list")
    public String listNews(ModelMap model,
                           @RequestParam(value = "s", required = false ) String s) {

        List<Tuple> results = landRepository.findAuctionDetailsWithImages();
        List<LandDTO> landDTOs = new ArrayList<>();

        for (Tuple tuple : results) {
            LandDTO landDTO = new LandDTO(
                    tuple.get(0, Integer.class), // auction_id
                    tuple.get(1, Integer.class), // statusid
                    tuple.get(2, Timestamp.class).toLocalDateTime(),     // start_time
                    tuple.get(3, Timestamp.class).toLocalDateTime(),     // end_time
                    tuple.get(4, Integer.class),   // land_id
                    tuple.get(5, String.class),    // description
                    tuple.get(6, Double.class),     // price
                    tuple.get(7, String.class),     // location
                    tuple.get(8, String.class),     // imageUrls
                    tuple.get(9, String.class),      // imageNames
                    tuple.get(10, String.class)      // imageNames
            );
            landDTOs.add(landDTO);
        }
        if(s != null) {
            landDTOs = landDTOs.stream()
//                    .map(LandDTO::getName) // Lấy ra giá trị name
                    .filter(e -> e.getName() != null && e.getName().contains(s)) // Lọc những name chứa chuỗi search
                    .collect(Collectors.toList());
        }
        model.addAttribute("LIST_LAND", landDTOs);
        return "home";
    }


}