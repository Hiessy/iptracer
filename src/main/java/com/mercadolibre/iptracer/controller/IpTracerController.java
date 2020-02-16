package com.mercadolibre.iptracer.controller;

import com.mercadolibre.iptracer.exceptions.RestClientException;
import com.mercadolibre.iptracer.exceptions.ServiceException;
import com.mercadolibre.iptracer.model.ExceptionResponse;
import com.mercadolibre.iptracer.service.IpTracerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequestMapping("/v1/api")
public class IpTracerController {

    @Autowired
    private IpTracerService ipTracerService;

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "Application is running!";
    }

    @GetMapping("/information")
    public ResponseEntity<?> getIpAddressInformation(@RequestParam(name="ip") @NotBlank String queryIp, HttpServletRequest request) {
        String requestIp = getIpAddress(request);
        try {
            ipTracerService.logRequest(requestIp);
            log.info("Getting new query request for ip: " + queryIp);
            return ResponseEntity.status(HttpStatus.OK).body(ipTracerService.buildResponse(queryIp));
        } catch (RestClientException e) {
            log.error("Rest client exception" + e.getMessage(), e);
            return ResponseEntity.status(e.getHttpStatus()).body(new ExceptionResponse(e.getMessage(), e.getHttpStatus()));
        }catch (RuntimeException ex) {
            log.error("Unexpected error for ip: " + queryIp);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (ServiceException ex) {
            log.error("Unexpected error with an external service: " + queryIp);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatics() {
        try {
            log.info("Getting statistics.");
            return ResponseEntity.status(HttpStatus.OK).body(ipTracerService.getStatistics());
        } catch (RuntimeException ex) {
            log.error("Exception getting statistics: " + ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (RestClientException e) {
            log.error("Rest client exception" + e.getMessage(), e);
            return ResponseEntity.status(e.getHttpStatus()).body(new ExceptionResponse(e.getMessage(), e.getHttpStatus()));
        } catch (ServiceException e) {
            log.error("Unexpected error...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    private String getIpAddress(HttpServletRequest request) {
        String id = request.getHeader("X-FORWARDED-FOR");
        if (id == null) {
            id = request.getRemoteAddr();
          }
        log.info("Got remote address: " + id);
        return "181.229.225.218";
    }

}
