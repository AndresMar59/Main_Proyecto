package Reportes;

import producto.productoDAO; 
import com.itextpdf.text.*;    // LIBRERIA DE 5.5.13.2 Jar.
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.util.List;

public class reportes {  // clase reportes
    private productoDAO dao = new productoDAO();  // Para llamar los metodos de productoDAO para la info que ocupemos

    public void PrecioTotalCategoria(String ruta) {
        List<Object[]> datos = dao.PrecioTotal();

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            doc.add(new Paragraph("Reporte de Inventario por Categoría"));
            doc.add(new Paragraph(" ")); // espacio

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Categoría");
            tabla.addCell("Total Cantidad");
            tabla.addCell("Valor Total ($)");

            for (Object[] fila : datos) {
                tabla.addCell(fila[0].toString());
                tabla.addCell(fila[1].toString());
                tabla.addCell(String.format("%.2f", fila[2]));
            }

            doc.add(tabla);
            doc.close();

            System.out.println("Reporte generado en: " + ruta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public void AVG_Utilidad (String ruta) {
        List<Object[]> datos = dao.AVG_PromedioCategoria();

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            doc.add(new Paragraph("Reporte de Inventario por Categoría"));
            doc.add(new Paragraph(" ")); // espacio

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Categoría");
            tabla.addCell("Total Cantidad");
            tabla.addCell("Valor AVG Total Utilidad ($)");

            for (Object[] fila : datos) {
                tabla.addCell(fila[0].toString());
                tabla.addCell(fila[1].toString());
                tabla.addCell(String.format("%.2f", fila[2]));
            }

            doc.add(tabla);
            doc.close();

            System.out.println("Reporte generado en: " + ruta);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

