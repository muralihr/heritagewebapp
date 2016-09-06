package org.janastu.heritageapp.web.rest.util;

import java.io.File;

import org.janastu.heritageapp.service.impl.VideoGeoTagHeritageEntityServiceImpl;
import org.janastu.heritageapp.web.rest.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Utility class for http header creation.
 *
 */
public class HeritageFileUtil {
	private final static Logger log = LoggerFactory.getLogger(HeritageFileUtil.class);


	String pataHome;
	public   String getRootFolderName(String pataHomeV)
	{
		
		
		pataHome = pataHomeV;
		if(pataHomeV == null)
		{
			if(OSValidator.isUnix())
			{
				pataHome = AppConstants.UPLOAD_FOLDER_LINUX;
				log.debug("UNIX ENV");
			}
			if(OSValidator.isWindows())
			{
				pataHome = AppConstants.UPLOAD_FOLDER_WIN;
				log.debug("WINDOWS ENV");
			}
				
		}
		
		return pataHome;
	}
	public   void deleteFile(String type,String fileName) {
		try {
			
			String pathToFile = "";
			//AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_AUDIO;
			if(type.startsWith("AUDIO"))
			{
			  pathToFile =  pataHome + "/" + AppConstants.UPLOAD_FOLDER_AUDIO+"/"+fileName;
			}
			if(type.startsWith("IMAGE"))
			{
			  pathToFile =  pataHome + "/" + AppConstants.UPLOAD_FOLDER_IMAGES+"/"+fileName;
			}
			if(type.startsWith("VIDEO"))
			{
			  pathToFile =  pataHome + "/" + AppConstants.UPLOAD_FOLDER_VIDEO+"/"+fileName;
			}
			
			File file = new File(pathToFile);

			if (file.delete()) {
				// Log.(file.getName() + " is deleted!");
					log.debug(file.getName() + " is deleted!");
			} else {
				log.debug( "Delete operation   failed for "+ file.getName() );
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

	public static void deleteAudioFile(String fileName) {

	}

	public static void deleteImageFile(String fileName) {

	}

}
