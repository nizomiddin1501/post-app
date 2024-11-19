package uz.developers.postapp.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.developers.postapp.entity.Post;
import uz.developers.postapp.repository.PostRepository;
import uz.developers.postapp.service.PostDownloadService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostDownloadServiceImpl implements PostDownloadService {

    private final PostRepository postRepository;


    @Override
    public void generatePDF(Long postId, HttpServletResponse response) throws IOException, DocumentException {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
            return;
        }
        Post post = postOpt.get();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"post_" + postId + ".pdf\"");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph("Post Details"));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Title: " + post.getTitle()));
        document.add(new Paragraph("Category: " + post.getCategory().getTitle()));
        document.add(new Paragraph("User: " + post.getUser().getName()));
        document.add(new Paragraph("Content: " + post.getContent()));
        document.add(new Paragraph("Date: " + post.getDate()));

        document.close();
    }

    @Override
    public void generateExcel(Long postId, HttpServletResponse response) throws IOException {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
            return;
        }
        Post post = postOpt.get();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Post Details");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Title");
        header.createCell(1).setCellValue("Category");
        header.createCell(2).setCellValue("User");
        header.createCell(3).setCellValue("Content");
        header.createCell(4).setCellValue("Date");

        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue(post.getTitle());
        row.createCell(1).setCellValue(post.getCategory().getTitle());
        row.createCell(2).setCellValue(post.getUser().getName());
        row.createCell(3).setCellValue(post.getContent());
        row.createCell(4).setCellValue(post.getDate().toString());

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"post_" + postId + ".xlsx\"");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @Override
    public void generateCSV(Long postId, HttpServletResponse response) throws IOException {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Post not found");
            return;
        }
        Post post = postOpt.get();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"post_" + postId + ".csv\"");

        PrintWriter writer = response.getWriter();
        writer.println("Title, Category, User, Content, Date");
        writer.println(post.getTitle() + ", " + post.getCategory().getTitle() + ", " +
                post.getUser().getName() + ", " + post.getContent() + ", " + post.getDate());
        writer.flush();
        writer.close();
    }
}
