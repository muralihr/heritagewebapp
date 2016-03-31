package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.HeritageApp ;
import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.domain.HeritageLanguage;
import org.janastu.heritageapp.domain.HeritageMedia ;
import org.janastu.heritageapp.domain.HeritageRegionName;
import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.TextGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.util.RestReturnCodes;
import org.janastu.heritageapp.repository.HeritageAppRepository;
import org.janastu.heritageapp.repository.HeritageCategoryRepository;
import org.janastu.heritageapp.repository.HeritageLanguageRepository;
import org.janastu.heritageapp.service.AudioGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.HeritageAppService;
import org.janastu.heritageapp.service.HeritageMediaService;
 
import org.janastu.heritageapp.service.ImageGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.TextGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.VideoGeoTagHeritageEntityService;
import org.janastu.heritageapp.web.rest.util.HeaderUtil;
import org.janastu.heritageapp.web.rest.util.OSValidator;
import org.janastu.heritageapp.web.rest.util.PaginationUtil;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.janastu.heritageapp.web.rest.dto.AudioGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.dto.HeritageAppDTO;
import org.janastu.heritageapp.web.rest.dto.HeritageMediaDTO;
import org.janastu.heritageapp.web.rest.dto.ImageGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.dto.TextGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.dto.VideoGeoTagHeritageEntityDTO;
import org.janastu.heritageapp.web.rest.mapper.HeritageAppMapper;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geojson.Point;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
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
@CrossOrigin
public class RestNewResourceMapApp {

	private final Logger log = LoggerFactory.getLogger(GeoJsonHeritageSpotsResource.class);
	@Inject
	private VideoGeoTagHeritageEntityService videoGeoTagHeritageEntityService;
	
	@Inject
	private HeritageMediaService heritageMediaEntityService;

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
	@Inject 
	HeritageAppRepository heritageAppNameRepository;

	@Autowired
	private Environment environment;
	
	String errMessageException;
	
    
@Inject
private HeritageAppService heritageAppNameService;

 
	
	
	@CrossOrigin
	@RequestMapping(value = "/createNewMediaHeritageForm/app/{appId}/user/{userId}/group/{groupId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public @ResponseBody MediaResponse createAnyMediaGeoTagHeritageFromWebWitMap(
			@PathVariable("appId") String appId,
			@PathVariable("userId") Integer userId,
			@PathVariable("groupId") Integer groupId,			
			@RequestParam("title") String title,			
			@RequestParam("description") String description, @RequestParam("category") String category,
			@RequestParam("language") String language, @RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude, @RequestParam("mediatype") String mediaType,
			@RequestParam("uploadTime") String uploadTime,
			@RequestParam("userAgent") String userAgent,
			@RequestParam("fileOrURLLink") String fileOrURLLink,			
			@RequestParam("picture") MultipartFile file
			) 
	{
		
		log.debug("uploadPost called title" + title);	
		log.debug("mapId  " + appId);
		log.debug("userId  " + userId);
		log.debug("groupId  " + groupId);
		
		log.debug("description " + description);
		//request.getParameter("description");
		log.debug("category  " + category);// category
		log.debug("language  " + language);
		log.debug("latitude  " + latitude);
		log.debug("longitude  " + longitude);
		log.debug("mediatype  " + mediaType);
		int mediaTypeInt = Integer.parseInt(mediaType);
		MediaResponse retValue2 = new MediaResponse();
		String address = "na";
		String mediaServerUrl = AppConstants.MEDIA_SERVER_URL;
		String pataHome = environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV);

		String newFilename = file.getOriginalFilename();
		// String storageDirectory = fileUploadDirectory;
		// String audioStorageDirectory = pataHome + "//heritageaudio";
		String urlLinkToMedia = null;

		String storageDirectory = "";
		if (pataHome == null) {
			if (OSValidator.isUnix()) {
				pataHome = AppConstants.UPLOAD_FOLDER_LINUX;
				log.debug("UNIX ENV");
			}
			if (OSValidator.isWindows()) {
				pataHome = AppConstants.UPLOAD_FOLDER_WIN;
				log.debug("WINDOWS ENV");
			}

		}
		
		if (OSValidator.isUnix()) {
			mediaServerUrl = AppConstants.MEDIA_SERVER_URL_UBUNTU;
			log.debug("UNIX ENV");
		}
		if (OSValidator.isWindows()) {
			mediaServerUrl = AppConstants.MEDIA_SERVER_URL;
			log.debug("WINDOWS ENV");
		}
		
		
		try {
			switch (mediaTypeInt) {
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
		} catch (Exception e) {
			retValue2.setCode(RestReturnCodes.MEDIA_CREATION_EXCEPTION);
			retValue2.setMediaCreatedId(-1);
			retValue2.setMessage("Error creating media" + e);
			retValue2.setStatus("NOTOK");
			return retValue2;

		}
		
		String downLoadFileName = storageDirectory + "//" + file.getOriginalFilename();
		String contentType = file.getContentType();
		// directory exists - no create directory ;
		
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				// Creating the directory to store file
				// Create the file on server
				File serverFile = new File(downLoadFileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				log.debug("Server File Location=" + serverFile.getAbsolutePath());

			} catch (Exception e) {
				retValue2.setCode(440);
				retValue2.setMessage("You FAILED uploaded file");
				retValue2.setStatus("NOTOK");
				;
				return retValue2;
			}
		} else {
			retValue2.setCode(440);
			retValue2.setMessage("You FAILED uploaded file file emplty");
			retValue2.setStatus("NOTOK");
			;
			return retValue2;
		}
		
		MediaResponse retValue = new MediaResponse();
		retValue =  createMediaData(title, description, address, category, language, latitude, longitude,
				urlLinkToMedia + "//" + file.getOriginalFilename(),groupId, appId,userId,contentType, mediaTypeInt,urlLinkToMedia,userAgent );
		
		
		return retValue;
	}
	///
	//app/{appId}/user/{userId}/group/{groupId
	private MediaResponse createMediaData(String title, String description, String address, String category, String language,
			String latitude, String longitude, String fileurl, Integer groupId, String appName, Integer userId, String contentType, Integer mediaType, String fileLink, String userAgent )
	{
		
		HeritageMediaDTO heritageMediaEntityDTO = null;
		heritageMediaEntityDTO = new HeritageMediaDTO();

		heritageMediaEntityDTO.setAddress(address);
		heritageMediaEntityDTO.setLatitude(Double.valueOf(latitude));
		heritageMediaEntityDTO.setLongitude(Double.valueOf(longitude));
		heritageMediaEntityDTO.setTitle(title);
		heritageMediaEntityDTO.setDescription("  " + description);
		Long groupIdlongVar =  groupId.longValue();
		heritageMediaEntityDTO.setGroupId(groupIdlongVar);
		//contentType
		heritageMediaEntityDTO.setMediaFileContentType(contentType);
		heritageMediaEntityDTO.setUrlOrfileLink(fileurl);
		heritageMediaEntityDTO.setMediaType(mediaType);
		heritageMediaEntityDTO.setUserAgent(userAgent);	
		
		Long userIdLongVar = userId.longValue();
		heritageMediaEntityDTO.setUserId( userIdLongVar);
		MediaResponse retValue = new MediaResponse();
		 
		
		HeritageApp  heritageAppNameId = heritageAppNameRepository.findByName(appName);
		if(heritageAppNameId != null)
		{
			heritageMediaEntityDTO.setHeritageAppId( heritageAppNameId.getId());
		}
		else
		{
			retValue.setCode(441);
			retValue.setMessage("Media Creation failed - App name - "+appName + " - INVALIDE");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		//userIdLongVar
		 
		
		
		HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
		if (hCategory != null)
		{
			heritageMediaEntityDTO.setCategoryId(hCategory.getId());
		}
		else
		{
			retValue.setCode(441);
			retValue.setMessage("Media Creation failed HeritageCategory -  "  + category+ " -INVALID CATEGORY");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language); 
		
		if (hLanguage != null)
		{
			heritageMediaEntityDTO.setLanguageId(hLanguage.getId());
		}
		else
		{
			retValue.setCode(441);
			retValue.setMessage("Media Creation failed -  HeritageLanguage - "  + language+ "- INVALID LANGUAGE");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		 // group repo
		//  user repo 
		//  app repo
		
		 //set the time ;
		 
		
		String inputIso = "2014-03-19T21:00:00+05:30";
		DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
		DateTime dateTimeIndia = new DateTime( inputIso, timeZoneIndia );
		

	    String s ="Asia/Kolkata";
	    LocalDateTime dt = LocalDateTime.now();
		ZoneId zone = ZoneId.of(s);
	    ZonedDateTime uploadTime = dt.atZone(zone);	    
	    heritageMediaEntityDTO.setUploadTime(uploadTime);
	    HeritageMediaDTO result = null;
	    try
	    {
	    	byte[] mediaFile = new byte[1];
	    	heritageMediaEntityDTO.setMediaFile(mediaFile);
	    	result  = heritageMediaEntityService.save(heritageMediaEntityDTO);
	    	result.getId();
	    }catch(Exception e)
	    {
	    	errMessageException = e.getMessage();
	    	
	    	retValue.setCode(441);
			retValue.setMessage("Media Creation failed"+errMessageException);
			retValue.setStatus("NOTOK");
			return retValue;
	    	
	    }
	    
	    
	    retValue.setCode(RestReturnCodes.SUCCESS);
		retValue.setMediaCreatedId(result.getId());
		retValue.setMessage("Created Media Successfully");
		retValue.setStatus("OK");
		return retValue;
	}
	
	///
	@CrossOrigin
	@RequestMapping(value = "/getAppConfigInfo/app/{appId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public @ResponseBody ResponseEntity<HeritageAppDTO> getConfigInfoForAppId(
			@PathVariable("appId") String appId 
			 
			)
	{
		
 
		  log.debug("REST request to get getConfigInfoForAppId : {}", appId);
		  HeritageAppDTO  heritageAppNameDTO = heritageAppNameService.findByName(appId);
	        
	       Set<HeritageCategory> categories =  heritageAppNameDTO.getCategorys();
	       Set<HeritageGroup> groups =   heritageAppNameDTO.getGroups();
	       
	       
	       Set<HeritageCategory> newcategories =  new HashSet<HeritageCategory> ();
	       Set<HeritageGroup> newgroups =   new  HashSet<HeritageGroup>();
	       for(HeritageCategory t : categories)
	       {
	    	   t.setCategoryIcon(null);
	    	   newcategories.add(t);	    	   
	       }
	       heritageAppNameDTO.setCategorys(newcategories);
	       
	       
	       for(HeritageGroup u : groups)
	       {
	    	   u.setIcon( null);
	    	   newgroups.add(u);	    	   
	       }
	       
	       heritageAppNameDTO.setGroups(newgroups); 
	       return Optional.ofNullable(heritageAppNameDTO)
	            .map(result -> new ResponseEntity<>(
	                result,
	                HttpStatus.OK))
	            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	//mapp/
	@CrossOrigin	
	@RequestMapping(value = "/mapp", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public FeatureCollection getAllHeritageMediaEntitysAsGeoJSON() throws URISyntaxException {
		
		List<HeritageMedia> heritageList  = heritageMediaEntityService.findAllAsAList();;
		FeatureCollection totalCollection = convertListToFeatures(heritageList);

		return 	totalCollection;

		
	}	
	private FeatureCollection convertListToFeatures(List<HeritageMedia > heritageList )
	{
		FeatureCollection totalCollection = new FeatureCollection(); 
		
		 for (HeritageMedia  item : heritageList) {
			  
			  LngLatAlt coordinates = new LngLatAlt(item.getLongitude(),
			  item.getLatitude()); Point c = new Point(coordinates); 
			  Feature f =			  new Feature(); 
			  f.setGeometry(c);
			  f.setId(item.getId().toString());
			  Map<String, Object> properties = new HashMap<String, Object>();
			  
			  properties.put("title", item.getTitle());
			  properties.put("description", item.getDescription());
			  
			  
			  properties.put("marker-size", "small"); 
			  properties.put("url", item.getUrlOrfileLink());
			  
			  
			  Integer m = item.getMediaType();
		 
			  if(m != null)
			  {
				  switch(m)
				  
				  {
				  
				  case AppConstants.IMAGETYPE:
					  properties.put("marker-color", "#ff8888"); // ma#FFECB3rker-color
					  properties.put("mediatype",	  "IMAGE"); 
					  properties.put("marker-symbol", "camera"); // #FFC107 //
					  break;
					  
				  case AppConstants.AUDIOTYPE:
					  properties.put("marker-color", "#FFC107"); // marker-color
					  properties.put("mediatype",	  "AUDIO"); 
					  properties.put("marker-symbol", "music"); // #FFC107 //
					  break;
				  case AppConstants.VIDEOTYPE:
					  properties.put("mediatype",	  "VIDEO"); 
					  properties.put("marker-color", "#FFECB3"); // ma#FFECB3rker-color
					  properties.put("marker-symbol", "cinema"); // #FFC107 //
					  break;
					  
				  case AppConstants.TEXTTYPE:
					  properties.put("marker-color", "#727272"); // ma#FFECB3rker-color
					  properties.put("mediatype",	  "TEXT"); 
					  properties.put("marker-symbol", "golf"); // #FFC107 //
					  
					  break;
					  
					  
				  
				  }
			  }
			  
			  if(item.getCategory()!= null)
			  {
					log.debug("getCategoryName   name " + item.getCategory().getCategoryName() );
					
					properties.put("category",item.getCategory().getCategoryName() );
			  }
			  if(item.getLanguage()!= null)
				{
			//		log.debug("getHeritageLanguage   name " + item.getHeritageLanguage().get.getHeritageLanguage());
					
				//	properties.put("language",item.getHeritageLanguage().getHeritageLanguage());
				}
			  
			  if(item.getGroup()!= null)
				{
					log.debug("getHeritageLanguage   groupname " + item.getGroup().getName());
					
					properties.put("groupname", item.getGroup().getName());
				}
			  if(item.getUser() != null)
				{
					log.debug("getHeritageLanguage   name " + item.getUser().getLogin());
					
					properties.put("user",item.getUser().getLogin());
					
					 
					
				}
			  if(item.getHeritageApp()  != null)
				{
					log.debug("getHeritageLanguage   name " + item.getHeritageApp().getName());
					
					properties.put("appname", item.getHeritageApp().getName());
				}
			  if(item.getUploadTime() != null)
				{
					 
					
				  
				    final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); 
				    String dateStr2 = item.getUploadTime().format(DATETIME_FORMATTER);
				    properties.put("uploadTime",dateStr2 );
				}
			  
			  
			  f.setProperties(properties);
			  totalCollection.add(f); 
			 }
		 
		 return 	totalCollection;

	}
	
	
	
	
		 
}
