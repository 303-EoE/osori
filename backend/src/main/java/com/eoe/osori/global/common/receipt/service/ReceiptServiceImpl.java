package com.eoe.osori.global.common.receipt.service;

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzedDocument;
import com.azure.ai.formrecognizer.documentanalysis.models.DocumentField;
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult;
import com.azure.core.util.BinaryData;
import com.azure.core.util.polling.SyncPoller;
import com.eoe.osori.global.advice.error.exception.ReceiptException;
import com.eoe.osori.global.advice.error.info.ReceiptErrorInfo;
import com.eoe.osori.global.common.receipt.dto.PostReceiptResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {

    private final DocumentAnalysisClient documentAnalysisClient;

    /**
     * Azure AI 모델에 영수증 스캔 요청
     *
     * @param multipartFile MultipartFile
     * @return PostReceiptResponseDto
     * @see DocumentAnalysisClient
     */
    @Override
    public PostReceiptResponseDto getReceiptInfo(MultipartFile multipartFile) {

        File sourceFile = convertMultipartFileToFile(multipartFile);

        Path filePath = sourceFile.toPath();

        BinaryData receiptData = BinaryData.fromFile(filePath, (int) sourceFile.length());

        AnalyzeResult receiptResults=null;

        try {
            SyncPoller<OperationResult, AnalyzeResult> analyzeReceiptPoller =
                    documentAnalysisClient.beginAnalyzeDocument("prebuilt-receipt", receiptData);

           receiptResults = analyzeReceiptPoller.getFinalResult();
        } catch (Exception e) {
            throw new ReceiptException(ReceiptErrorInfo.RECEIPT_SERVICE_ERROR);
        }

        deleteFile(sourceFile);

        LocalDate date = null;
        LocalTime time = null;
        int totalPrice = 0;

        for (int i = 0; i < receiptResults.getDocuments().size(); i++) {
            AnalyzedDocument analyzedReceipt = receiptResults.getDocuments().get(i);
            Map<String, DocumentField> receiptFields = analyzedReceipt.getFields();

            for (Map.Entry<String, DocumentField> entry : receiptFields.entrySet()) {
                String key = entry.getKey();
                DocumentField documentField = entry.getValue();
                if (key.equals("TransactionTime")) {
                    time = documentField.getValueAsTime();
                }
                if (key.equals("Total")) {
                    totalPrice = documentField.getValueAsDouble().intValue();
                }
                if (key.equals("TransactionDate")) {
                    date = documentField.getValueAsDate();
                }
            }
        }
        if (totalPrice == 0) {
            throw new ReceiptException(ReceiptErrorInfo.INVALID_RECEIPT_FILE);
        }
        LocalDateTime localDateTime;
        if (time == null || date == null) {
            localDateTime = LocalDateTime.now();
        } else {
            localDateTime = combineDateAndTime(date, time);
        }

        return PostReceiptResponseDto.of(totalPrice, localDateTime);
    }

    /**
     * MultipartFile을 File로 변환
     *
     * @param multipartFile MultipartFile
     * @return File
     */
    private File convertMultipartFileToFile(MultipartFile multipartFile) {
        String projectDirectory = System.getProperty("user.dir");
        String folderPath = projectDirectory + "/images/" + getFolderName();
        File folder = new File(folderPath);

        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                // 폴더 생성에 실패한 경우
                throw new ReceiptException(ReceiptErrorInfo.RECEIPT_FOLDER_CREATION_ERROR);
            }
        }

        File file = new File(folderPath + "/" + multipartFile.getOriginalFilename());

        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new ReceiptException(ReceiptErrorInfo.RECEIPT_FILE_IO_ERROR);
        }
        return file;
    }

    /**
     * 년/월 폴더명 반환
     */
    private String getFolderName() {
        // SimpleDateFormat을 사용하면 날짜를 원하는 형식으로 얻어 올 수 있다.
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);
        return str.replace("-", "/");
    }

    /**
     * LocalDateTime으로 합치는 메소드
     *
     * @param date LocalDate
     * @param time LocalTime
     * @return LocalDateTime
     */
    private LocalDateTime combineDateAndTime(LocalDate date, LocalTime time) {
        LocalDateTime combinedDateTime = date.atTime(time);
        return combinedDateTime;
    }

    /**
     * 파일 삭제 메소드
     *
     * @param file File
     */
    private void deleteFile(File file) {
        if (file.exists()) {
            if (file.delete()) {
                log.debug("파일 삭제에 성공했습니다.");
            } else {
                log.debug("파일 삭제에 실패했습니다.");
            }
        } else {
            log.debug("파일이 이미 존재하지 않습니다.");
        }
    }
}
