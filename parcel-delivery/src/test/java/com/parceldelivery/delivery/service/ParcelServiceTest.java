package com.parceldelivery.delivery.service;

import com.parceldelivery.delivery.model.dto.CreateDeliveryRequest;
import com.parceldelivery.delivery.model.entity.ParcelEntity;
import com.parceldelivery.delivery.repository.ParcelRepository;
import com.parceldelivery.delivery.security.TokenHelper;
import com.parceldelivery.model.request.CreateParcelApiRequest;
import com.parceldelivery.model.response.CreateEntityResponse;
import com.parceldelivery.model.response.CreateParcelResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParcelServiceTest {

    private static final String TOKEN = String.valueOf(UUID.randomUUID());
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID DELIVERY_ID = UUID.randomUUID();
    private static final UUID PARCEL_ID = UUID.randomUUID();

    @Mock
    private ParcelRepository parcelRepository;
    @Mock
    private TokenHelper tokenHelper;
    @Mock
    private DeliveryService deliveryService;
    @Mock
    private CreateParcelApiRequest createParcelRequest;
    @Mock
    private ParcelEntity parcelEntity;
    @Mock
    private CreateEntityResponse createEntityResponse;
    @Captor
    private ArgumentCaptor<ParcelEntity> parcelCaptor;
    @Captor
    private ArgumentCaptor<CreateDeliveryRequest> deliveryCaptor;

    @InjectMocks
    private ParcelServiceImpl service;

    // region createParcel
    @Test
    public void test_createParcel_success() {
        // prepare
        when(tokenHelper.getUserId(TOKEN)).thenReturn(USER_ID);
        when(createParcelRequest.getWeight()).thenReturn(10);
        when(createParcelRequest.getDeliveryDate()).thenReturn(LocalDate.now().plusDays(10));
        when(parcelRepository.save(parcelCaptor.capture())).thenReturn(parcelEntity);
        when(deliveryService.createDelivery(deliveryCaptor.capture())).thenReturn(createEntityResponse);
        when(createEntityResponse.getId()).thenReturn(DELIVERY_ID);
        when(parcelEntity.getId()).thenReturn(PARCEL_ID);
        // execute
        CreateParcelResponse response = service.createParcel(createParcelRequest, TOKEN);
        // validate
        assertEquals(response.getDeliveryId(), DELIVERY_ID.toString());
        assertEquals(response.getParcelId(), PARCEL_ID.toString());
    }
    // endregion
}
