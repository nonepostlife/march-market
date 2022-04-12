package ru.geekbrains.march.market.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.geekbrains.march.market.services.ProductService;
import ru.geekbrains.march.market.soap.products.GetAllProductsRequest;
import ru.geekbrains.march.market.soap.products.GetAllProductsResponse;
import ru.geekbrains.march.market.soap.products.GetProductByIdRequest;
import ru.geekbrains.march.market.soap.products.GetProductByIdResponse;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.postlife.com/spring/ws/products";
    private final ProductService productService;

    /*
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:prod="http://www.postlife.com/spring/ws/products">
   <soapenv:Header/>
   <soapenv:Body>
      <prod:getProductByIdRequest>
         <prod:id>4</prod:id>
      </prod:getProductByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productService.getProductById(request.getId()));
        return response;
    }

    /*
        Пример запроса: POST http://localhost:8080/ws
        Header -> Content-Type: text/xml

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:prod="http://www.postlife.com/spring/ws/products">
    <soapenv:Header/>
        <soapenv:Body>
            <prod:getAllProductsRequest/>
    </soapenv:Body>
</soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllProducts().forEach(response.getProducts()::add);
        return response;
    }
}
