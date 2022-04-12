package ru.geekbrains.march.market.endpoints;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.geekbrains.march.market.services.CategoryService;
import ru.geekbrains.march.market.soap.categories.GetCategoryByTitleRequest;
import ru.geekbrains.march.market.soap.categories.GetCategoryByTitleResponse;

@Endpoint
@RequiredArgsConstructor
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://www.postlife.com/spring/ws/categories";
    private final CategoryService categoryService;

    /*
        Пример запроса: POST http://localhost:8189/market/ws

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:cat="http://www.postlife.com/spring/ws/categories">
   <soapenv:Header/>
   <soapenv:Body>
      <cat:getCategoryByTitleRequest>
         <cat:title>Food</cat:title>
      </cat:getCategoryByTitleRequest>
   </soapenv:Body>
</soapenv:Envelope>
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByTitleResponse getCategoryByTitle(@RequestPayload GetCategoryByTitleRequest request) {
        GetCategoryByTitleResponse response = new GetCategoryByTitleResponse();
        response.setCategory(categoryService.getByTitle(request.getTitle()));
        return response;
    }
}
