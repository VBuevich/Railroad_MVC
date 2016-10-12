package railroad.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Chunk;
import com.lowagie.text.Paragraph;
import org.springframework.web.servlet.view.document.AbstractPdfView;
import com.lowagie.text.Document;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import railroad.persistence.entity.Statistics;

public class PdfStatisticsView extends AbstractPdfView{

    @Override
    protected void buildPdfDocument(Map model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        List<Statistics> statistics = (ArrayList<Statistics>) model.get("statistics");

        Paragraph head = new Paragraph("RailRoad site: Statistics for ticketing");
        head.setAlignment("Center");
        document.add(head);

        for (Statistics s : statistics) {
        Table table = new Table(2);

            table.addCell("When bought");
            table.addCell(s.getDatetime().toString());

            table.addCell("Name");
            table.addCell(s.getPassengerName());

            table.addCell("Surname");
            table.addCell(s.getPassengerSurname());

            table.addCell("Date of birth");
            table.addCell(s.getPassengerDob().toString());

            table.addCell("Email");
            table.addCell(s.getPassengerEmail());

            table.addCell("Train number");
            table.addCell(Integer.toString(s.getTrainNumber()));

            table.addCell("Type");
            table.addCell(s.getTrainType());

            table.addCell("From");
            table.addCell(s.getDepartureStation());

            table.addCell("To");
            table.addCell(s.getArrivalStation());

            table.addCell("Seat");
            table.addCell(s.getSeat());

            table.addCell("OneWay");
            table.addCell(s.getIsOneWay().toString());

            table.addCell("Departure date");
            table.addCell(s.getDepartureDate().toString());

            table.addCell("Departure time");
            table.addCell(s.getDepartureTime().toString());

            document.add(table);
            document.add(Chunk.NEWLINE);
        }
    }
}