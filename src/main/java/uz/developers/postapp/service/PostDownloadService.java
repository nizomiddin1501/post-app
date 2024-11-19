package uz.developers.postapp.service;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface PostDownloadService {

    void generatePDF(Long postId, HttpServletResponse response) throws IOException, DocumentException;

    void generateExcel(Long postId, HttpServletResponse response) throws IOException;

    void generateCSV(Long postId, HttpServletResponse response) throws IOException;

}
