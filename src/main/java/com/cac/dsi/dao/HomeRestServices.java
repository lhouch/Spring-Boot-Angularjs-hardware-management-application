package com.cac.dsi.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cac.dsi.entites.Demande;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@RestController
public class HomeRestServices {	
	
	@Autowired ServletContext servletContext=null;
		
	  private static String FILE_PATH ="";
	    private static final String APPLICATION_PDF = "application/pdf";
	 private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
	 private static Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL);
	 private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	@Autowired
	DemandeRepository demandeRepository;
	
	@RequestMapping(value = "/printdemande/{id}", method = RequestMethod.GET, produces = "application/pdf")
	public  @ResponseBody void  printdemande(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
		
		deltefile();
		 File temp = File.createTempFile("terminal", ".pdf" );
		 FILE_PATH = temp.getAbsolutePath();
		Demande demande = demandeRepository.getOne(id);
		Document document = new Document();
	    try {
	        PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH));
	        document.open();
	       Image logo = Image.getInstance(servletContext.getRealPath("/WEB-INF/classes/static/modules/img/logo/logo.jpg"));
	       
	       logo.scaleAbsoluteWidth(200);
	       logo.scaleAbsoluteHeight(100);
	       logo.setAbsolutePosition(200,750);
	        document.add(logo);
	        addMetaData(document);
	        addTitlePage(document, demande);
	        
	        document.close();
	    } catch(Exception e){
	      e.printStackTrace();
	    }
		File file = getFile();
        InputStream in = new FileInputStream(file);
        response.setContentType(APPLICATION_PDF);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(in, response.getOutputStream());
        }
	 private static void addMetaData(Document document) {
	        document.addTitle("OCTROI DU TERMINAL");
	        document.addSubject("OCTROI DU TERMINAL");
	        document.addKeywords("CAC, OCTROI");
	        document.addAuthor("CAC");
	        document.addCreator("CAC");
	    }
	 
	 private static void addTitlePage(Document document, Demande demande)
	            throws DocumentException {
	        Paragraph preface = new Paragraph();
	        addEmptyLine(preface, 5);
	        preface.add(new Paragraph("OCTROI DU TERMINAL "+demande.getProd().getType().getLabel().toUpperCase(), catFont));
	        addEmptyLine(preface, 1);
	         preface.add(new Paragraph( padRight("Nom et prénom du bénéficiare: ",40) + demande.getDemandeur().getNom(),smallNormal));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph( padRight("Département: ",53)+demande.getDemandeur().getDepartement().getNom(),smallNormal));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph(padRight("Terminal actuel: ",52)+demande.getProd().getLabel(),smallNormal));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph(padRight("Type terminal: ",53)+demande.getProd().getType().getLabel(),smallNormal));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph(padRight("Marque et model: ",50)+demande.getProd().getMarque() +" "+demande.getProd().getModel(),smallNormal));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph(padRight("Numéro de série de terminal: ",43)+demande.getProd().getNumeros(),smallNormal));
	        addEmptyLine(preface, 1);
	        preface.add(new Paragraph(padRight("Autre information: ",50)+demande.getProd().getDescription(),smallNormal));
	        addEmptyLine(preface, 10); 
	        preface.add(new Paragraph("Direction informatique:                      Responsable RH:                             Bénéficiaire:", smallBold));
	        document.add(preface);
	        document.newPage();
	    }
	 
	 public static String padRight(String s, int n) {
		    return String.format("%1$-" + n + "s", s);
		  }

	 private static void addEmptyLine(Paragraph paragraph, int number) {
	        for (int i = 0; i < number; i++) {
	            paragraph.add(new Paragraph(" "));
	        }
	    }
	 
	 private File getFile() throws FileNotFoundException {
         File file = new File(FILE_PATH);
         if (!file.exists()){
             throw new FileNotFoundException("file with path: " + FILE_PATH + " was not found.");
         }
         return file;
     }
        
	 private void deltefile(){
		 try{
		      
		        File fileTemp = new File(FILE_PATH);
		          if (fileTemp.exists()){
		             fileTemp.delete();
		          }   
		      }catch(Exception e){
		         // if any error occurs
		         e.printStackTrace();
		      }
	 }
		       

}
