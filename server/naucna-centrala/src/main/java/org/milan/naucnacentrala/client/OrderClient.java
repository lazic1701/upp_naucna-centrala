package org.milan.naucnacentrala.client;

import org.milan.naucnacentrala.model.Porudzbina;
import org.milan.naucnacentrala.model.dto.InitOrderRequestDTO;
import org.milan.naucnacentrala.model.dto.InitOrderResponseDTO;
import org.milan.naucnacentrala.model.enums.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderClient {

    private final static String returnUrl = "https://192.168.43.124:8602/api/porudzbine/kupovina/finalize";
    private final static String initOrderKPEndpoint = "https://192.168.43.124:8500/sellers/active-order/init";


    @Autowired
    RestTemplate restTemplate;

    public InitOrderResponseDTO initOrder(Porudzbina p, long sellerId) {
        InitOrderRequestDTO initOrderRequestDTO = new InitOrderRequestDTO();

        initOrderRequestDTO.setNcOrderId(p.getId());
        initOrderRequestDTO.setAmount(p.getCena());
        initOrderRequestDTO.setCurrency("USD");
        initOrderRequestDTO.setOrderStatus(Enums.OrderStatus.PENDING);
        initOrderRequestDTO.setSellerId(sellerId);

        completeElse(initOrderRequestDTO, p);

        initOrderRequestDTO.setReturnUrl(this.returnUrl);
        initOrderRequestDTO.setUsername(p.getKupac().getUsername());

        HttpEntity<InitOrderRequestDTO> httpEntity = new HttpEntity<>(initOrderRequestDTO);
        ResponseEntity<InitOrderResponseDTO> responseEntity = restTemplate.postForEntity(this.initOrderKPEndpoint, httpEntity,
                InitOrderResponseDTO.class);
        return responseEntity.getBody();
    }

    private void completeElse(InitOrderRequestDTO iorDTO, Porudzbina p) {
        if (p.getTipPorudzbine() == Enums.OrderType.ORDER_CASOPIS) {
            iorDTO.setTitle(p.getCasopis().getNaziv());
            iorDTO.setOrderType(p.getTipPorudzbine());
        } else if (p.getTipPorudzbine() == Enums.OrderType.ORDER_RAD) {
            iorDTO.setTitle(p.getNaucniRad().getNaslov());
            iorDTO.setOrderType(p.getTipPorudzbine());
        } else if (p.getTipPorudzbine() == Enums.OrderType.ORDER_SUBSCRIPTION) {
            iorDTO.setTitle("Subscription for " + p.getClanarina().getCasopis().getNaziv());
            iorDTO.setOrderType(p.getTipPorudzbine());
        }
    }



}