package pl.ca.recruitment.shoppingregistry.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ca.recruitment.shoppingregistry.notification.model.PurchaseNotificationDTO;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Slf4j
class NotificationController {
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseNotification(@RequestBody PurchaseNotificationDTO purchaseNotification) {
        log.info("Received notification:" + purchaseNotification);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
