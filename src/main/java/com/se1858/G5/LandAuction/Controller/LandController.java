package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.NewsService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
//
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "s", required = false ) String s) {


        int pageSize = 6; // Số phần tử hiển thị mỗi trang

        // Tạo đối tượng Pageable với trang hiện tại và số lượng phần tử trên mỗi trang
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Tuple> pagedResults = landRepository.findAuctionDetailsWithImages(pageable);

        List<LandDTO> landDTOs = new ArrayList<>();

        for (Tuple tuple : pagedResults.getContent()) {
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
        model.addAttribute("currentPage", page); // Trang hiện tại
        model.addAttribute("totalPages", pagedResults.getTotalPages()); // Tổng số trang
        return "home";
    }


}