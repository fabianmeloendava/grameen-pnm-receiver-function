package com.grameen.repayment.queue;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.specialized.BlobInputStream;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.QueueServiceClientBuilder;
import com.azure.storage.queue.models.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grameen.repayment.pnm.model.PnmDateSegment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service to manage the access to Azure Storage Account, containers and Queues
 */
@Service
@Slf4j
public class PnmStorageService {


    static final String TIME_SPLITTER = ":";
    private static final String ERROR_SEND_PAYMENTS = " Error Sending payments to Queue. Message: ";
    private static final String ERROR_GET_DATES = " Error getting dates from json file in Azure Storage Container ";
    private static final String ERROR_STORAGE_FILE = " Error in storage Json File to Storage Container ";
    private static final String INFO_NEW_DATES = " Storing new Segment of Dates: ";
    @Value("${spring.cloud.azure.storage.queue.queue-name}")
    String queueName;
    @Value("${spring.cloud.azure.storage.connection-string}")
    private String connectionString;
    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;
    @Value("${spring.cloud.azure.storage.blob.blob-name}")
    private String blobName;
    @Value("${date.format}")
    private String dateFormat;


    /**
     * Send the payment to Azure Storage Queue
     *
     * @param msg Message to be stored
     * @return Queue Message ID, identify the message in the Queue
     */
    public String sendPaymentToQueue(String msg) {
        String messageId;
        try {
            QueueServiceClient queueServiceClient = new QueueServiceClientBuilder()
                    .connectionString(connectionString)
                    .buildClient();
            QueueClient queueClient = queueServiceClient.getQueueClient(queueName);
            SendMessageResult sendMessageResult = queueClient.sendMessage(msg);
            messageId = sendMessageResult.getMessageId();

        } catch (Exception e) {
            log.error(ERROR_SEND_PAYMENTS + msg, e);
            return "";
        }
        return messageId;
    }

    /**
     * Get the file from Azure Container, change to new date segment and stores the new JSON File in Container again
     *
     * @return Java Object with modified dates
     */
    public PnmDateSegment getDatesFromContainer() {
        PnmDateSegment dateSegment;
        BlobInputStream blobStream = null;
        BlobContainerClient blobContainerClient;
        try {
            blobContainerClient = new BlobContainerClientBuilder().connectionString(connectionString).containerName(containerName).buildClient();
            BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
            blobStream = blobClient.openInputStream();
            byte[] fileInBytes = blobStream.readAllBytes();
            dateSegment = getDatesFromFile(fileInBytes);
            setDatesToContainer(dateSegment, blobClient);
        } catch (JsonProcessingException e) {
            log.error(ERROR_STORAGE_FILE, e);
            return null;
        } catch (Exception e) {
            log.error(ERROR_GET_DATES, e);
            return null;
        } finally {
            if (blobStream != null)
                blobStream.close();
        }
        return dateSegment;
    }

    /**
     * Get the dates from JSON file, it is converted in a Java object. Then the dates are manipulated to change it to next segment to return the  modified Object
     *
     * @param file Json File in Byte Array
     * @return Jav Object with information from json File
     */
    private PnmDateSegment getDatesFromFile(byte[] file) throws IOException {
        PnmDateSegment dateSegment;

        ObjectMapper objectMapper = new ObjectMapper();
        dateSegment = objectMapper.readValue(file, PnmDateSegment.class);
        //Obtain date in LocalDateTime and add segment defined in json file
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime endDateInDate = LocalDateTime.parse(dateSegment.getEndDate(), formatter);
        String[] segmentSplit = dateSegment.getFragment().trim().split(TIME_SPLITTER);
        // LocalDateTime instance is immutable and unaffected by this method call.
        LocalDateTime newEndDateH = endDateInDate.plusHours(Long.parseLong(segmentSplit[0]));
        LocalDateTime newEndDateM = newEndDateH.plusMinutes(Long.parseLong(segmentSplit[1]));
        LocalDateTime newEndDateS = newEndDateM.plusSeconds(Long.parseLong(segmentSplit[2]));
        //Update start,end date in Java Object
        dateSegment.setStartDate(dateSegment.getEndDate());
        dateSegment.setEndDate(newEndDateS.format(formatter));
        return dateSegment;

    }

    /**
     * Store the modified JSON File to Azure Container
     *
     * @param dateSegment Java object with new segment dates
     * @param blobClient  Object with blob connection
     */
    public void setDatesToContainer(PnmDateSegment dateSegment, BlobClient blobClient) throws JsonProcessingException {

        //Convert Java Object to JSON
        ObjectMapper mapper = new ObjectMapper();
        String dateSegmentJson = mapper.writeValueAsString(dateSegment);
        // Convert the updated JSON string to a byte array
        byte[] updatedDateInBytes = dateSegmentJson.getBytes(StandardCharsets.UTF_8);

        // Get the BlockBlobClient for the file to update
        BlockBlobClient blockBlobClient = blobClient.getBlockBlobClient();

        // Upload the updated content to the Blob storage
        blockBlobClient.upload(new ByteArrayInputStream(updatedDateInBytes), updatedDateInBytes.length, true);
        log.info(INFO_NEW_DATES + dateSegmentJson);

    }
}
