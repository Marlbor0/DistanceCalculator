package controllers;

import com.sun.jersey.multipart.FormDataParam;
import entity.City;
import entity.Distance;
import org.apache.commons.io.IOUtils;
import services.CityService;
import services.DistanceService;
import xml.Deserialization;
import xml.Model;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.List;

/**
 * Created by Александр on 13.06.2016.
 */
@Path("upload")
public class LoadDataToDatabase {
    /**
     * Max file size, what user can upload
     */
    private final int MAX_SIZE = 100 * 1024;
    @Inject
    private CityService cityService;
    @Inject
    private DistanceService distanceService;

    @POST
//    @Path("/fileupload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response loadToDatabase(
            @FormDataParam("file") InputStream uploadedInputStream) {
        File xmlFile = streamTofile(uploadedInputStream);
        if (xmlFile.length() > MAX_SIZE) {
            return Response.status(Response.Status.CONFLICT).entity("Too large file").build();
        }
        File clearFile = null;
        try {
            clearFile = clearFileFromHeader(xmlFile);
        } catch (IOException e) {
            return Response.status(Response.Status.CONFLICT).entity("Bad file").build();
        }
        Model m = null;
        try {
            m = Deserialization.XMLToObject(clearFile);
        } catch (JAXBException e) {
            return Response.status(Response.Status.CONFLICT).entity("Bad file").build();
        }
        List<City> cities = m.getCities();
        for (City c : cities) {
            cityService.save(c);
        }
        List<Distance> distances = m.getDistances();
        for (Distance d : distances) {
            distanceService.save(d);
        }
        return Response.status(200).build();

    }

    /**
     * Clear file from hearer and footer
     */
    public File clearFileFromHeader(File file) throws IOException {
        File fileToWrite = null;
        fileToWrite = File.createTempFile("tempFile1", ".tmp");
        fileToWrite.deleteOnExit();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedReader readerCount = new BufferedReader(new FileReader(file));
             PrintWriter writer = new PrintWriter(new FileWriter(fileToWrite));
        ) {
            int current = 0;
            String line = null;
            int countOfLine = 0;
            while (readerCount.readLine() != null) {
                countOfLine++;
            }
            readerCount.close();
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if ((!line.startsWith("<?xml") && isHeader) || current == countOfLine - 1) {
                    current++;
                    continue;
                }
                isHeader = false;
                writer.println(line);
                current++;
            }
        }
        return fileToWrite;
    }

    /**
     *
     * Convert input stream to temp file
     */
    public File streamTofile(InputStream in) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tempFile", ".tmp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        tempFile.deleteOnExit();
        try {
            OutputStream out = null;
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(tempFile);
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return tempFile;
    }

}
