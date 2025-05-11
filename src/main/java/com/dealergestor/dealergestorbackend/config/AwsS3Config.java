/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.dealergestor.dealergestorbackend.storage.S3FileStorageService;
import com.dealergestor.dealergestorbackend.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

    @Value("${app.aws.s3.bucket}")
    private String bucketName;

    @Value("${app.aws.region}")
    private String region;

    /**
     * Cliente AmazonS3 configurado para la región indicada.
     * Como tu EC2 ya tiene el Role asignado, no hace falta
     * pasar credenciales aquí.
     */
    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    /**
     * Servicio de almacenamiento de archivos.
     * Inyecta el cliente S3 y el nombre de bucket.
     */
    @Bean
    public FileStorageService fileStorageService(AmazonS3 amazonS3) {
        return new S3FileStorageService(amazonS3, bucketName);
    }
}
