package com.GamingHub.util;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.http.Part;

public class ImageUtil {

    /**
     * Extracts the file name from the given {@link Part} object based on the
     * "content-disposition" header.
     */
    public String getImageNameFromPart(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        String imageName = null;

        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                imageName = s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }

        if (imageName == null || imageName.isEmpty()) {
            imageName = "download.avif"; // Default file name
        }

        return imageName;
    }

    /**
     * Uploads the image file from the given {@link Part} object to a specified
     * directory on the server.
     */
    public boolean uploadImage(Part part, String rootPath, String saveFolder) {
        String savePath = getSavePath(rootPath, saveFolder);
        File fileSaveDir = new File(savePath);

        // Ensure the directory exists
        if (!fileSaveDir.exists()) {
            if (!fileSaveDir.mkdirs()) {
                System.out.println("Failed to create directories: " + savePath);
                return false;
            }
        }

        try {
            // Get the image name
            String imageName = getImageNameFromPart(part);
            System.out.println("Image name extracted: " + imageName); // Log image name
            
            // Create the file path
            String filePath = savePath + "/" + imageName;
            System.out.println("Attempting to save image at: " + filePath);

            // Write the file to the server
            part.write(filePath);
            System.out.println("Image uploaded successfully.");
            return true; // Upload successful
        } catch (IOException e) {
            e.printStackTrace(); // Log the exception
            System.out.println("Image upload failed due to error: " + e.getMessage());
            return false; // Upload failed
        }
    }

    /**
     * Gets the path for saving images.
     * Uses the root path provided to calculate the correct directory.
     */
    public String getSavePath(String rootPath, String saveFolder) {
        // Use a relative path to save images in the resources folder under 'customer'
        String realPath = "C:\\Users\\ACER\\eclipse-workspace\\GamingHub\\src\\main\\webapp\\resources\\" + saveFolder;
        return realPath;
    }
}
