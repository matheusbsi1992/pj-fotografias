///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.com.teste.projeto;
//
//import br.com.fotografias.bo.AlterarEventoBO;
//import br.com.fotografias.dao.CadastrarEventoDAO;
//import br.com.fotografias.dao.EventoDAO;
//import br.com.fotografias.dao.PessoaDAO;
//import br.com.fotografias.transfer.EventoTransfer;
//import br.com.fotografias.transfer.GaleriaTransfer;
//import br.com.fotografias.transfer.PessoaTransfer;
//import java.awt.AlphaComposite;
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.FontMetrics;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.RenderingHints;
//import java.awt.geom.Rectangle2D;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//
///**
// *
// * @author Jandisson
// */
//public class ClassedeTeste {
//
//    private byte[] byte_array = null;
//
//    public void setByte_array(byte[] byte_array) {
//        this.byte_array = byte_array;
//    }
//
//    public byte[] getByte_array() {
//        return byte_array;
//    }
//
//    //path do caminho para a mar
//    public byte[] pathImageWatermark() throws IOException {
//        File file;
//        file = new File("C:\\Users\\Jandisson\\Documents\\NetBeansProjects\\template-admin-faces-master\\pjfotografias\\src\\main\\webapp\\imagemarcadagua\\teste03.png");
//        byte[] fileContent = Files.readAllBytes(file.toPath());
//
//        return fileContent;
//    }
//
//    //fazer a transformacao de uma array de bytes das imagens e de um array de bytes de uma marca d'agua
//    public byte[] processImage(byte[] imageData, byte[] waterMarkData) throws IOException {
//        BufferedImage img1 = ImageIO.read(new ByteArrayInputStream(imageData));
//        BufferedImage img2 = ImageIO.read(new ByteArrayInputStream(waterMarkData));
//        Graphics2D g2d = img1.createGraphics();
//        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
//        g2d.setComposite(alpha);
//        g2d.drawImage(img2, 0, 0, null);
//        g2d.dispose();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ImageIO.write(img1, "png", bos);
//
//        return bos.toByteArray();
//    }
//
//    public byte[] read(String file) throws FileNotFoundException, IOException {
//        byte[] buffer = new byte[1024];
//
//        InputStream is = new FileInputStream(file);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        while (is.read(buffer) != -1) {
//            out.write(buffer);
//        }
//        try {
//            return out.toByteArray();
//        } finally {
//            is.close();
//            out.close();
//        }
//
//    }
//
//    public byte[] read(String file, double requiredWidth, double requiredHeight) throws IOException {
//
//        BufferedImage imagem = ImageIO.read(new File(file));
//
//        double originalWidth = imagem.getWidth();
//        double originalHeight = imagem.getHeight();
//        double newWidth = 0;
//        double newHeight = 0;
//        double diff = 0;
//
//        if (requiredHeight == 0) {
//            requiredHeight = requiredWidth;
//        }
//
//        if (requiredWidth == 0) {
//            requiredWidth = requiredHeight;
//        }
//
//        if (originalWidth < requiredWidth && originalHeight < requiredHeight) {
//            return read(file);
//        }
//
//        if (requiredWidth == 0 && requiredHeight == 0) {
//            return read(file);
//        }
//
//        if (originalWidth > originalHeight) {
//            diff = originalWidth - originalHeight;
//            newWidth = requiredWidth;
//            diff = diff / originalWidth;
//            newHeight = newWidth - (newWidth * diff);
//        } else if (originalWidth < originalHeight) {
//            diff = originalHeight - originalWidth;
//            newHeight = requiredHeight;
//            diff = diff / originalHeight;
//            newWidth = newHeight - (newHeight * diff);
//        } else {
//
//            if (requiredHeight > requiredWidth) {
//                requiredHeight = requiredWidth;
//            } else if (requiredHeight < requiredWidth) {
//                requiredWidth = requiredHeight;
//            }
//
//            newHeight = requiredHeight;
//            newWidth = requiredWidth;
//        }
//
//        int type = BufferedImage.TYPE_INT_RGB;
//        boolean isPng = file.toUpperCase().endsWith("PNG");
//
//        if (isPng) {
//            type = BufferedImage.BITMASK;
//        }
//
//        BufferedImage new_img = new BufferedImage((int) newWidth, (int) newHeight, type);
//        Graphics2D g = new_img.createGraphics();
//        g.setComposite(AlphaComposite.Src);
//        g.drawImage(imagem, 0, 0, (int) newWidth, (int) newHeight, null);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        if (isPng) {
//            ImageIO.write(new_img, "PNG", out);
//        } else {
//            ImageIO.write(new_img, "JPG", out);
//        }
//
//        GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
//
//        try {
//
//            galeriaTransfer.setFotosgaleria(out.toByteArray());
//
//            setByte_array(processImage(pathImageWatermark(), galeriaTransfer.getFotosgaleria()));
//            //saida de um array de bytes para imagem
//            ByteArrayInputStream input_stream = new ByteArrayInputStream(getByte_array());
//            BufferedImage final_buffered_image = ImageIO.read(input_stream);
//            ImageIO.write(final_buffered_image, "PNG", new File("C:\\Users\\Jandisson\\Documents\\NetBeansProjects\\template-admin-faces-master\\pjfotografias\\src\\main\\webapp\\imagemarcadagua\\marcadagua04.png"));
//            System.out.println("Converted Successfully!");
//        } finally {
//            out.close();
//        }
//        return galeriaTransfer.getFotosgaleria();
//    }
//
////    public static void main(String[] args) throws Exception {
//////        PessoaDAO pessoaDAO = new PessoaDAO();
////        ClassedeTeste cls = new ClassedeTeste();
////        GaleriaTransfer galeriaTransfer = new GaleriaTransfer();
////        cls.read("C:\\Users\\Jandisson\\Documents\\NetBeansProjects\\template-admin-faces-master\\pjfotografias\\src\\main\\webapp\\imagemarcadagua\\teste01.png", 600, 600);
//////        EventoDAO eventoDAO = new EventoDAO();
////////        AlterarEventoBO alterarEventoBO = new AlterarEventoBO();
//////        List<GaleriaTransfer> galeriaevento = new ArrayList<GaleriaTransfer>();
//////
//////        galeriaevento = eventoDAO.ListarTodos();
//////
//////        for (GaleriaTransfer galeriaTransfer : galeriaevento) {
//////            System.out.println("[id evento]:" + galeriaTransfer.getId());
//////            System.out.println("nome pessoa:" + galeriaTransfer.getNomepessoa());
//////            System.out.println("nome evento:" + galeriaTransfer.getNomeevento());
//////        }
////
////        // CadastrarEventoDAO cadastrarEventoDAO = new CadastrarEventoDAO();
////        //cadastrarEventoDAO.listar();
////    }
////}
////        EventoTransfer eventoTransfer = new EventoTransfer();
//////        for (EventoTransfer eventoTransfer : evento) {
//////            System.out.println(eventoTransfer.getNomeevento());
//////            System.out.println(eventoTransfer.getDataevento());
//////            System.out.println(eventoTransfer.getNomepessoa());
//////        }
////
////        eventoTransfer.setNomepessoa("teste");
////        eventoTransfer.setUsuariopessoa("Maria");
////        
////        //evento.add(eventoTransfer);
////
////
////        eventoTransfer.setNomepessoa("Elena");
////        eventoTransfer.setUsuariopessoa("MariaElena");
////        //evento.add(eventoTransfer);
////
////
////        eventoTransfer.setNomeevento("teste-evento");
////        eventoTransfer.setObservacaoevento("testehoje-26-20-2018");
////
////        evento.add(eventoTransfer);
////
////        for (int i = 0; i < evento.size(); i++) {
////            System.out.println(evento.get(i).getNomepessoa());
////            System.out.println(evento.get(i).getUsuariopessoa());
////            System.out.println(evento.get(i).getObservacaoevento());
////        }
////
////        GaleriaTransfer ga = new GaleriaTransfer();
////        List<GaleriaTransfer> gal = new ArrayList<GaleriaTransfer>();
////
////        ga.setDescricaofotosgaleria("teste");
////        ga.setDescricaofotosgaleria("teste2");
////        ga.setDescricaofotosgaleria("teste3");
////
////        gal.add(ga);
////
////
////        for (int i = 0; i < gal.size(); i++) {
////            System.out.println(gal.get(i).getDescricaofotosgaleria());
////        }
////
////    }
////        byte a[] = cadastrarEventoDAO.getImage(61);
////        String v=cadastrarEventoDAO.byteToStringHexa(a);
////        System.out.println("["+v+"]");
////        System.out.println("[" + org.postgresql.util.Base64.encodeBytes(a) + "]");
////
////        System.out.println((cadastrarEventoDAO.getImage(61)));
////        List<PessoaTransfer> lista = pessoaDAO.pessoaEventoID();
////        for (PessoaTransfer pessoaTransfer : lista) {
////            System.out.println(pessoaTransfer.getId());
////            System.out.println(pessoaTransfer.getUsuariopessoa());
////
////        }
////      EventoDAO eventoDAO = new EventoDAO();
////        eventoDAO.listarFotos();
////        ImageIcon icon = null;
////        Rectangle2D rect = null;
////        int imageWidth = 800;
////        int imageHeight = 640;
////
////        WaterMark wtmk = new WaterMark();
////
////        //wtmk.makeWaterMark("\\Users\\Jandisson\\Pictures\\rosa.jpg", "C:");
////        try {
////            File file = new File("C:\\Users\\Jandisson\\Pictures\\rosa.jpg");
////            if (!file.exists()) {
////                System.out.println("File not Found");
////            }
////            icon = new ImageIcon(file.getPath());
////            BufferedImage bufferedImage = new BufferedImage(
////                    icon.getIconWidth(), icon.getIconHeight(),
////                    BufferedImage.TYPE_INT_RGB);
////            Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
////            g2d.drawImage(icon.getImage(), 0, 0, null);
////            AlphaComposite alpha = AlphaComposite.getInstance(
////                    AlphaComposite.SRC_OVER, 0.5f);
////            g2d.setComposite(alpha);
////            g2d.setColor(Color.black);
////            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
////                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
////            g2d.setFont(new Font("Arial", Font.ITALIC, 75));
////            StringBuffer buffer = new StringBuffer();
////
////            buffer.append("PJFOTOGRAFIAS Copyright © 2018 ");
////
////            FontMetrics fontMetrics = g2d.getFontMetrics();
////            rect = fontMetrics.getStringBounds(buffer.toString(), g2d);
////            //topo
////            g2d.drawString(buffer.toString(), (icon.getIconWidth() - (int) rect.getWidth()) / 2, (icon.getIconHeight() - (int) rect.getHeight()) / 8);
////            //centro
////            g2d.drawString(buffer.toString(), (icon.getIconWidth() - (int) rect.getWidth()) / 2, (icon.getIconHeight() - (int) rect.getHeight()) / 2);
////            //rodape
////            g2d.drawString(buffer.toString(), (icon.getIconWidth() - (int) rect.getWidth()) / 2, (icon.getIconHeight() - (int) rect.getHeight()) / 1);
////
////            g2d.dispose();
////            File fileout = new File("C:\\Users\\Jandisson\\Pictures\\watermarkedImage.jpg");
////            ImageIO.write(bufferedImage, "png", fileout);
////
////        } catch (IOException ioe) {
////        }
////    public static void main(String... args) throws IOException {
////
////      
////        // overlay settings
////        String text = "\u00a9 © 2018 PJFOTOGRAFIAS LTDA-ME";
////        File input = new File("C:\\Users\\Jandisson\\Documents\\NetBeansProjects\\pjfotografias\\src\\main\\webapp\\imagemarcadagua\\meupingo.jpg");
////        File output = new File("C:\\Users\\Jandisson\\Documents\\NetBeansProjects\\pjfotografias\\src\\main\\webapp\\imagemarcadagua\\pj01.png");
////
////        // adding text as overlay to an image
////        addTextWatermark(text, "jpg", input, output);
////    }
////
////    private static void addTextWatermark(String text, String type, File source, File destination) throws IOException {
////        BufferedImage image = ImageIO.read(source);
////
////        // determine image type and handle correct transparency
////        int imageType = "png".equalsIgnoreCase(type) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
////        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), imageType);
////
////        // initializes necessary graphic properties
////        Graphics2D w = (Graphics2D) watermarked.getGraphics();
////        w.drawImage(image, 0, 0, null);
////        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
////        w.setComposite(alphaChannel);
////        w.setColor(Color.GRAY);
////        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
////        FontMetrics fontMetrics = w.getFontMetrics();
////        Rectangle2D rect = fontMetrics.getStringBounds(text, w);
////
////        // calculate center of the image
////        //int centerX = (image.getWidth() - (int) rect.getWidth()) / 2;
////        //int centerY = image.getHeight() / 2;
////
////        //topo
////        w.drawString(text, (image.getWidth() - (int) rect.getWidth()) / 2, (image.getHeight() - (int) rect.getHeight()) / 8);
////        //centro
////        w.drawString(text, (image.getWidth() - (int) rect.getWidth()) / 2, (image.getHeight() - (int) rect.getHeight()) / 2);
////        //rodape
////        ///w.drawString(text, (image.getWidth() - (int) rect.getWidth()) / 2, (image.getHeight() - (int) rect.getHeight()) / 1);
////        w.drawString(text, (image.getWidth() - (int) rect.getWidth()) / 2, (int) ((image.getHeight() - (Double) rect.getHeight()) / 2.5));
////        // add text overlay to the image
////        //w.drawString(text, centerX, centerY);
////        ImageIO.write(watermarked, type, destination);
////        w.dispose();
////    }
//    
//    public static void main(String... args) throws IOException {
//        
//        
//        BufferedImage img1 = ImageIO.read(new ByteArrayInputStream(imageData));
//        
//        Graphics2D g2d = img1.createGraphics();
//        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
//        
//        String watermark = "PJFOTOGRAFIAS"; 
//  
//        // Add the watermark text at (width/5, height/3) 
//        // location 
//        
//        g2d.setComposite(alpha);
//        g2d.drawString(watermark, img1.getWidth(), 
//                                   img1.getHeight()); 
//        g2d.drawImage(img1, 50,50, null);
//        g2d.dispose();
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ImageIO.write(img1, "PNG", bos);
//
//        //return bos.toByteArray();
//        
//    BufferedImage img = null; 
//        File f = null; 
//  
//        // Read image 
//        try
//        { 
//            f = new File("input.png"); 
//            img = ImageIO.read(f); 
//        } 
//        catch(IOException e) 
//        { 
//            System.out.println(e); 
//        } 
//  
//        // create BufferedImage object of same width and 
//        // height as of input image 
//        BufferedImage temp = new BufferedImage(img.getWidth(), 
//                    img.getHeight(), BufferedImage.TYPE_INT_RGB); 
//  
//        // Create graphics object and add original 
//        // image to it 
//        Graphics graphics = temp.getGraphics(); 
//        graphics.drawImage(img, 0, 0, null); 
//  
//        // Set font for the watermark text 
//        graphics.setFont(new Font("Arial", Font.PLAIN, 80)); 
//        graphics.setColor(new Color(255, 0, 0, 40)); 
//  
//        // Setting watermark text 
//        String watermark = "WaterMark generated"; 
//  
//        // Add the watermark text at (width/5, height/3) 
//        // location 
//        graphics.drawString(watermark, img.getWidth()/5, 
//                                   img.getHeight()/3); 
//  
//        // releases any system resources that it is using 
//        graphics.dispose(); 
//  
//        f = new File("output.png"); 
//        try
//        { 
//            ImageIO.write(temp, "png", f); 
//        } 
//        catch (IOException e) 
//        { 
//            System.out.println(e); 
//        } 
//    } 
//    
//
//}
