package com.eoe.osori.global.advice.config;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentAnalysisConfig {
    @Value("${azure.form-recognizer.endpoint}")
    private String endpoint;
    @Value("${azure.form-recognizer.key}")
    private String apiKey;

    @Bean
    public DocumentAnalysisClient getFormRecognizerClient() {
        AzureKeyCredential credential = new AzureKeyCredential(apiKey);
        return new DocumentAnalysisClientBuilder()
                .endpoint(endpoint)
                .credential(credential)
                .buildClient();
    }
}
