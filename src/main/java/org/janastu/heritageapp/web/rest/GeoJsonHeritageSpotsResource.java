package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.domain.HeritageLanguage;
import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.TextGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;
import org.janastu.heritageapp.repository.HeritageCategoryRepository;
import org.janastu.heritageapp.repository.HeritageLanguageRepository;
import org.janastu.heritageapp.service.AudioGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.ImageGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.TextGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.VideoGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.OSValidator;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.janastu.heritageapp.web.rest.dto.AudioGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.dto.ImageGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.dto.TextGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.dto.VideoGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.VideoGeoTagHeritageEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing VideoGeoTagHeritageEntity.
 */
@RestController
@RequestMapping("/api")

public class GeoJsonHeritageSpotsResource {

	private final Logger log = LoggerFactory.getLogger(GeoJsonHeritageSpotsResource.class);
	@Inject
	private VideoGeoTagHeritageEntityService videoGeoTagHeritageEntityService;

	@Inject
	private ImageGeoTagHeritageEntityService imageGeoTagHeritageEntityService;

	@Inject
	private AudioGeoTagHeritageEntityService audioGeoTagHeritageEntityService;

	@Inject
	private TextGeoTagHeritageEntityService textGeoTagHeritageEntityService;

	@Inject
	HeritageCategoryRepository heritageCategoryRepository;

	@Inject
	HeritageLanguageRepository heritageLanguageRepository;

	@Autowired
	private Environment environment;

	@RequestMapping(value = "/allGeoTagHeritageEntitysGeoJson", method = { RequestMethod.GET, RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public FeatureCollection getAllVideoGeoTagHeritageEntitysAsGeoJSON() throws URISyntaxException {

		FeatureCollection totalCollection = new FeatureCollection();

		List<VideoGeoTagHeritageEntity> videoList = videoGeoTagHeritageEntityService.findAllAsAList();
		List<ImageGeoTagHeritageEntity> imageList = imageGeoTagHeritageEntityService.findAllAsAList();
		List<AudioGeoTagHeritageEntity> audioList = audioGeoTagHeritageEntityService.findAllAsAList();
	//	List<TextGeoTagHeritageEntity> textList = textGeoTagHeritageEntityService.findAllAsAList();


		for (ImageGeoTagHeritageEntity item : imageList) {

			LngLatAlt coordinates = new LngLatAlt(item.getLongitude(), item.getLatitude());
			Point c = new Point(coordinates);
			Feature f = new Feature();
			f.setGeometry(c);
			f.setId(item.getId().toString());
			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put("title", item.getTitle());
			properties.put("description", item.getDescription());
			properties.put("url", item.getUrlOrfileLink());
			properties.put("marker-color", "#ff8888");

			properties.put("marker-size", "small");
			properties.put("mediatype", "IMAGE");
			properties.put("marker-symbol", "square");
			// marker-size
			// "marker-color": "#ff8888",

			f.setProperties(properties);
			totalCollection.add(f);

		}

		for (VideoGeoTagHeritageEntity item : videoList) {

			LngLatAlt coordinates = new LngLatAlt(item.getLongitude(), item.getLatitude());
			Point c = new Point(coordinates);
			Feature f = new Feature();
			f.setGeometry(c);
			f.setId(item.getId().toString());
			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put("title", item.getTitle());
			properties.put("description", item.getDescription());
			properties.put("url", item.getUrlOrfileLink());
			properties.put("marker-color", "#FFC107"); // marker-color
			properties.put("marker-size", "small");
			properties.put("mediatype", "VIDEO");
			properties.put("marker-symbol", "rocket");
			// #FFC107
			// marker-size
			// "marker-color": "#ff8888",

			f.setProperties(properties);
			totalCollection.add(f);
		}
		
		for (AudioGeoTagHeritageEntity item : audioList) {

			LngLatAlt coordinates = new LngLatAlt(item.getLongitude(), item.getLatitude());
			Point c = new Point(coordinates);
			Feature f = new Feature();
			f.setGeometry(c);
			f.setId(item.getId().toString());
			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put("title", item.getTitle());
			properties.put("description", item.getDescription());
			properties.put("url", item.getUrlOrfileLink());
			properties.put("marker-color", "#FFECB3"); // ma#FFECB3rker-color
			properties.put("marker-size", "small");
			properties.put("mediatype", "AUDIO");
			properties.put("marker-symbol", "circle");
			// #FFC107
			// marker-size
			// "marker-color": "#ff8888",

			f.setProperties(properties);
			totalCollection.add(f);
		}
		
		/*for (TextGeoTagHeritageEntity item : textList) {

			LngLatAlt coordinates = new LngLatAlt(item.getLongitude(), item.getLatitude());
			Point c = new Point(coordinates);
			Feature f = new Feature();
			f.setGeometry(c);
			f.setId(item.getId().toString());
			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put("title", item.getTitle());
			properties.put("description", item.getDescription());
			
			properties.put("marker-color", "#727272"); // ma#FFECB3rker-color
			properties.put("marker-size", "small");
			properties.put("mediatype", "TEXT");
			properties.put("marker-symbol", "circle");
			// #FFC107
			// marker-size
			// "marker-color": "#ff8888",

			f.setProperties(properties);
			totalCollection.add(f);
		}*/

		return totalCollection;

	}
	
	
	

	// put the api to store the info here ;

	// test file upload and creation of the entity

	// collect the media type

	// depending on teh same - crate a entity and store;

	// imageGeoTagHeritageFromMobile
	@RequestMapping(value = "/createAnyMediaGeoTagHeritageFromMobile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public  MediaResponse  createAnyMediaGeoTagHeritageFromMobile(
			MultipartHttpServletRequest request, HttpServletResponse response) throws URISyntaxException {
		log.debug("uploadPost called title" + request.getParameter("title"));
		log.debug("description  " + request.getParameter("description"));
		log.debug("category  " + request.getParameter("category"));//category
		log.debug("language  " + request.getParameter("language"));
		log.debug("latitude  " + request.getParameter("latitude"));
		log.debug("longitude  " + request.getParameter("longitude"));
		log.debug("mediatype  " + request.getParameter("mediatype"));
		ResponseEntity<ImageGeoTagHeritageEntityDTO> retValue = null;
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String language = request.getParameter("language");
		String category = request.getParameter("category");
		String address = request.getParameter("address");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String mediatypeStr = request.getParameter("mediatype");
		int mediaType = Integer.parseInt(mediatypeStr);
		String mediaServerUrl = AppConstants.MEDIA_SERVER_URL;

		log.debug("DIR PATA HOME" + environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV));
		String pataHome = environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV);
		if(pataHome == null)
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
		
		if(OSValidator.isUnix())
		{
			mediaServerUrl = AppConstants.MEDIA_SERVER_URL_UBUNTU;
			log.debug("UNIX ENV");
		}
		if(OSValidator.isWindows())
		{
			mediaServerUrl = AppConstants.MEDIA_SERVER_URL;
			log.debug("WINDOWS ENV");
		}
		
		
		log.debug("UPLOAD  FOLDER LOCATION "+pataHome);
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		byte[] photo = null;
		String urlLinkToMedia = "";
		try {
			File newFile = null;
			while (itr.hasNext()) {

				mpf = request.getFile(itr.next());
				log.debug("Uploading {}", mpf.getOriginalFilename());

				String newFilenameBase = mpf.getOriginalFilename();
				String originalFileExtension = mpf.getOriginalFilename()
						.substring(mpf.getOriginalFilename().lastIndexOf("."));
				// String newFilename = newFilenameBase + originalFileExtension;
				String newFilename = mpf.getOriginalFilename();
				// String storageDirectory = fileUploadDirectory;
				String audioStorageDirectory = pataHome + "//heritageaudio";

			
				String storageDirectory = "";

				switch (mediaType) {
				case AppConstants.AUDIOTYPE:
					storageDirectory = pataHome + "//" + AppConstants.UPLOAD_FOLDER_AUDIO;
					urlLinkToMedia = mediaServerUrl + "/" + AppConstants.MEDIA_APP_NAME + "/"
							+ AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_AUDIO;
					break;
				case AppConstants.IMAGETYPE:
					storageDirectory = pataHome + "//" + AppConstants.UPLOAD_FOLDER_IMAGES;
					urlLinkToMedia = mediaServerUrl + "/" + AppConstants.MEDIA_APP_NAME + "/"
							+ AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_IMAGES;
					break;
				case AppConstants.TEXTTYPE:
					// No upload just store ;
					break;
				case AppConstants.VIDEOTYPE:
					storageDirectory = pataHome + "//" + AppConstants.UPLOAD_FOLDER_VIDEO;
					urlLinkToMedia = mediaServerUrl + "/" + AppConstants.MEDIA_APP_NAME + "/"
							+ AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_VIDEO;
					break;
				}

				String downLoadFileName = storageDirectory + "//" + mpf.getOriginalFilename();
				String contentType = mpf.getContentType();
				// directory exists - no create directory ;
				File theDir = new File(storageDirectory);

				// if the directory does not exist, create it
				if (!theDir.exists()) {
					log.debug("creating directory: " + storageDirectory);
					boolean result = false;

					try {
						theDir.mkdir();
						result = true;
					} catch (SecurityException se) {
						// handle it
					}
					if (result) {
						log.debug("DIR created" + storageDirectory);
					}
				}

				try {
					newFile = new File(downLoadFileName);

					log.debug("uploaded file to PATH" + newFile.getAbsolutePath());
					mpf.transferTo(newFile);
					photo = FileUtils.readFileToByteArray(newFile);
				} catch (FileNotFoundException e) {
					log.error("Could not upload file " + newFile, e);

				} catch (IOException e) {
					log.error("Could not upload file " + mpf.getOriginalFilename(), e);
				}
			}
			/// check

			//
		
		

		} catch (Exception e) {

			log.error("Error while save and  upload file " + e);
		}
		
		MediaResponse retValue2 = new MediaResponse();
		
		
		switch (mediaType) {
		case AppConstants.AUDIOTYPE:
		 
			break;
		case AppConstants.IMAGETYPE:
			
			createImageData(  title,   description,  address,   category,   language,    latitude,   longitude ,   urlLinkToMedia +"//"+mpf.getOriginalFilename() );
			 
			break;
		case AppConstants.TEXTTYPE:
			// No upload just store ;
			break;
		case AppConstants.VIDEOTYPE:
			createVideoData(  title,   description,  address,   category,   language,    latitude,   longitude ,   urlLinkToMedia +"//"+mpf.getOriginalFilename() );
			break;
		}

		return retValue2;

	}
	 

	private void createImageData(String title, String description,String address, String category, String language, String latitude, String longitude,   String urlLinkToMedia   )
	{
		
		ImageGeoTagHeritageEntityDTO imageGeoTagHeritageEntityDTO = null;
		imageGeoTagHeritageEntityDTO = new ImageGeoTagHeritageEntityDTO();
		 
		imageGeoTagHeritageEntityDTO.setAddress(address);
		imageGeoTagHeritageEntityDTO.setLatitude(Double.valueOf(latitude));
		imageGeoTagHeritageEntityDTO.setLongitude(Double.valueOf(longitude));
		imageGeoTagHeritageEntityDTO.setTitle(title);
		imageGeoTagHeritageEntityDTO.setDescription("      " + description);
		HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
		if (hCategory != null)
			imageGeoTagHeritageEntityDTO.setHeritageCategoryId(hCategory.getId());
		HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language);
		if (hLanguage != null)
			imageGeoTagHeritageEntityDTO.setHeritageLanguageId(hLanguage.getId()); 
		imageGeoTagHeritageEntityDTO.setUrlOrfileLink(urlLinkToMedia);
		byte[] array = new byte[1];
		imageGeoTagHeritageEntityDTO.setPhoto(array);
		ImageGeoTagHeritageEntityDTO result = imageGeoTagHeritageEntityService.save(imageGeoTagHeritageEntityDTO);

		
	}
	private void createAudioData(String title, String description, String category, String language,  File mpictureFile  , String latitude, String longitude, String urlLinkToMedia  )
	{
		
		AudioGeoTagHeritageEntityDTO audioGeoTagHeritageEntityDTO = null;
		audioGeoTagHeritageEntityDTO = new AudioGeoTagHeritageEntityDTO();
		 
		//audioGeoTagHeritageEntityDTO.setAddress(address);
		audioGeoTagHeritageEntityDTO.setLatitude(Double.valueOf(latitude));
		audioGeoTagHeritageEntityDTO.setLongitude(Double.valueOf(longitude));
		audioGeoTagHeritageEntityDTO.setTitle(title);
		audioGeoTagHeritageEntityDTO.setDescription("      " + description);
		HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
		if (hCategory != null)
			audioGeoTagHeritageEntityDTO.setHeritageCategoryId(hCategory.getId());
		HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language);
		if (hLanguage != null)
			audioGeoTagHeritageEntityDTO.setHeritageLanguageId(hLanguage.getId()); 
		audioGeoTagHeritageEntityDTO.setUrlOrfileLink(urlLinkToMedia);
		byte[] array = new byte[1];
		audioGeoTagHeritageEntityDTO.setAudioFile( array);
		AudioGeoTagHeritageEntityDTO result = audioGeoTagHeritageEntityService.save(audioGeoTagHeritageEntityDTO);

		
	}
	private void createVideoData(String title, String description,String address, String category, String language, String latitude, String longitude,   String urlLinkToMedia   )
	{
		   VideoGeoTagHeritageEntityDTO videoGeoTagHeritageEntityDTO = null;
			videoGeoTagHeritageEntityDTO = new VideoGeoTagHeritageEntityDTO();
			 
			videoGeoTagHeritageEntityDTO.setAddress(address);
			videoGeoTagHeritageEntityDTO.setLatitude(Double.valueOf(latitude));
			videoGeoTagHeritageEntityDTO.setLongitude(Double.valueOf(longitude));
			videoGeoTagHeritageEntityDTO.setTitle(title);
			videoGeoTagHeritageEntityDTO.setVideoFileContentType("video/mp4");
			videoGeoTagHeritageEntityDTO.setDescription("      " + description);
			HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
			if (hCategory != null)
				videoGeoTagHeritageEntityDTO.setHeritageCategoryId(hCategory.getId());
			HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language);
			if (hLanguage != null)
				videoGeoTagHeritageEntityDTO.setHeritageLanguageId(hLanguage.getId()); 
			videoGeoTagHeritageEntityDTO.setUrlOrfileLink(urlLinkToMedia);
			byte[] array = new byte[1];
			videoGeoTagHeritageEntityDTO.setVideoFile(array);
			VideoGeoTagHeritageEntityDTO result = videoGeoTagHeritageEntityService.save(videoGeoTagHeritageEntityDTO);
		
	}
	private void createTextData(String title, String description, String category, String language,  File mpictureFile  , String latitude, String longitude, String mediatype   )
	{
		
		TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = null;
		textGeoTagHeritageEntityDTO = new TextGeoTagHeritageEntityDTO();
		 
	//	textGeoTagHeritageEntityDTO.setAddress(address);
		textGeoTagHeritageEntityDTO.setLatitude(Double.valueOf(latitude));
		textGeoTagHeritageEntityDTO.setLongitude(Double.valueOf(longitude));
		textGeoTagHeritageEntityDTO.setTitle(title);
		textGeoTagHeritageEntityDTO.setDescription("      " + description);
		HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
		if (hCategory != null)
			textGeoTagHeritageEntityDTO.setHeritageCategoryId(hCategory.getId());
		HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language);
		if (hLanguage != null)
			textGeoTagHeritageEntityDTO.setHeritageLanguageId(hLanguage.getId()); 
		 
 
		TextGeoTagHeritageEntityDTO result = textGeoTagHeritageEntityService.save(textGeoTagHeritageEntityDTO);
		
	}
	
		
	

}
