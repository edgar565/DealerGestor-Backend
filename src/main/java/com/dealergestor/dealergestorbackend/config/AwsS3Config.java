/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.dealergestor.dealergestorbackend.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.dealergestor.dealergestorbackend.storage.S3FileStorageService;
import com.dealergestor.dealergestorbackend.storage.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

   /* @Value("${aws.accessKeyId}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;
    @Value("${aws.region}")
    private String region;
    @Value("${app.aws.s3.bucket}")
    private String bucketName;

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    /**
     * Servicio de almacenamiento de archivos.
     * Inyecta el cliente S3 y el nombre de bucket.
     */
    /*@Bean
    public FileStorageService fileStorageService(AmazonS3 amazonS3) {
        return new S3FileStorageService(amazonS3, bucketName);
    }*/
}
