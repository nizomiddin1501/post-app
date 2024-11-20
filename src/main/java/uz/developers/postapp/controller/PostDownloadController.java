package uz.developers.postapp.controller;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.developers.postapp.payload.CustomApiResponse;
import uz.developers.postapp.service.PostDownloadService;

import java.io.IOException;

/**
 * Controller for handling file download operations.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/downloads")
public class PostDownloadController {


    private final PostDownloadService postDownloadService;


    /**
     * Download a PDF file.
     *
     * @param postId   the ID of the post for the report
     * @param response to write the PDF file to
     * @return ResponseEntity with the operation status
     * @throws IOException if an error occurs during PDF generation
     */
    @GetMapping("/pdf/{postId}")
    public ResponseEntity<CustomApiResponse<Void>> downloadPDF(
            @PathVariable Long postId,
            HttpServletResponse response) throws IOException, DocumentException {
        postDownloadService.generatePDF(postId, response);
        return ResponseEntity.ok(new CustomApiResponse<>(
                "PDF file generated successfully.",
                true,
                null));
    }

    /**
     * Download an Excel file.
     *
     * @param postId   the ID of the post for the report
     * @param response to write the Excel file to
     * @return ResponseEntity with the operation status
     */
    @GetMapping("/excel/{postId}")
    public ResponseEntity<CustomApiResponse<Void>> downloadExcel(
            @PathVariable Long postId,
            HttpServletResponse response) throws IOException {
        postDownloadService.generateExcel(postId, response);
        return ResponseEntity.ok(new CustomApiResponse<>(
                "Excel file generated successfully.",
                true,
                null));
    }

    /**
     * Download a CSV file.
     *
     * @param postId   the ID of the post for the report
     * @param response to write the CSV file to
     * @return ResponseEntity with the operation status
     */
    @GetMapping("/csv/{postId}")
    public ResponseEntity<CustomApiResponse<Void>> downloadCSV(
            @PathVariable Long postId,
            HttpServletResponse response) throws IOException {
        postDownloadService.generateCSV(postId, response);
        return ResponseEntity.ok(new CustomApiResponse<>(
                "CSV file generated successfully.",
                true,
                null));
    }
}
