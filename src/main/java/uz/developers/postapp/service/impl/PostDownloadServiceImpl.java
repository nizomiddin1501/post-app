package uz.developers.postapp.service.impl;

import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.developers.postapp.service.PostDownloadService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostDownloadServiceImpl implements PostDownloadService {


    @Override
    public void generatePDF(Long postId, HttpServletResponse response) throws IOException, DocumentException {

    }

    @Override
    public void generateExcel(Long postId, HttpServletResponse response) throws IOException {

    }

    @Override
    public void generateCSV(Long postId, HttpServletResponse response) throws IOException {

    }
}
