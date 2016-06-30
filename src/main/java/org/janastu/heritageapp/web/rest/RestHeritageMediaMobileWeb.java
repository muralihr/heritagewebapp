package org.janastu.heritageapp.web.rest;

 



import com.codahale.metrics.annotation.Timed;

import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.HeritageApp;
import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.domain.HeritageGroupUser;
import org.janastu.heritageapp.domain.HeritageLanguage;
import org.janastu.heritageapp.domain.HeritageMedia;
 
import org.janastu.heritageapp.domain.User;

import org.janastu.heritageapp.domain.util.RestReturnCodes;
import org.janastu.heritageapp.repository.HeritageAppRepository;
import org.janastu.heritageapp.repository.HeritageCategoryRepository;
import org.janastu.heritageapp.repository.HeritageGroupRepository;
import org.janastu.heritageapp.repository.HeritageGroupUserRepository;
import org.janastu.heritageapp.repository.HeritageLanguageRepository;
import org.janastu.heritageapp.repository.UserRepository;
import org.janastu.heritageapp.service.AudioGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.HeritageAppService;
import org.janastu.heritageapp.service.HeritageMediaService;

import org.janastu.heritageapp.service.ImageGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.TextGeoTagHeritageEntityService;
import org.janastu.heritageapp.service.UserService;
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
import java.net.URLDecoder;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
public class RestHeritageMediaMobileWeb {

	private final Logger log = LoggerFactory.getLogger(RestNewResourceMapApp.class);

	@Inject
	private HeritageMediaService heritageMediaEntityService;
	@Inject
	HeritageCategoryRepository heritageCategoryRepository;
	@Inject
	HeritageLanguageRepository heritageLanguageRepository;
	@Inject
	HeritageAppRepository heritageAppNameRepository;

	@Inject
	HeritageGroupUserRepository heritageGroupUserRepository;

	@Inject
	UserRepository userRepository;

	@Inject
	UserService userService;

	@Inject
	HeritageGroupRepository groupRepository;

	@Autowired
	private Environment environment;
	String errMessageException;

	@Inject
	private HeritageAppService heritageAppNameService;

		
	

	
	@CrossOrigin //editNewMediaHeritageForm2
	@RequestMapping(value = "/editNewMediaHeritageForm2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public @ResponseBody MediaResponse editAnyMediaGeoTagHeritageFromWebWithMapFix(@RequestParam("id") Integer id,
			@RequestParam("appId") String appId,
			@RequestParam("userName") String userName, @RequestParam("groupId") Integer groupId,
			@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("category") String category, @RequestParam("language") String language,
			@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude,
			@RequestParam("mediatype") String mediaType, @RequestParam("uploadTime") String uploadTime,
			@RequestParam("userAgent") String userAgent, @RequestParam("fileOrURLLink") String fileOrURLLink,
			@RequestParam("picture") MultipartFile file) {

		log.debug("uploadPost called title" + title);
		log.debug("uploadPost called id" + id);
		log.debug("mapId  " + appId);
		log.debug("userName  " + userName);
		log.debug("groupId  " + groupId);
		boolean isTextData = false;
		boolean shouldWeUploadFile = false;

		log.debug("description " + description);
		// request.getParameter("description");
		log.debug("category  " + category); // category
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
		Integer userId = -1;
		
		
		
		//userName
		Long idLong =  Long.valueOf(id.longValue());		
		HeritageMediaDTO mediaDTO = heritageMediaEntityService.findOne(idLong);
		if(mediaDTO == null)
		{
			retValue2.setCode(440);
			retValue2.setMessage("ID not found");
			retValue2.setStatus("NOTOK");		 
			return retValue2;
		}
		Optional<User>  u = userRepository.findOneByLogin(userName);
		if(u.isPresent() )
		{
			User user = u.get();
			Long userIdLong = user.getId();
			userId = userIdLong.intValue();
			
		}
		else
		{
			retValue2.setCode(440);
			retValue2.setMessage("username not found");
			retValue2.setStatus("NOTOK");		 
			return retValue2;
		}
		String contentType = "";
		
		log.debug("fileOrURLLink." +fileOrURLLink );
		String decodeUrl="";
		//decode before assigning;
		try
		{
			decodeUrl  = URLDecoder.decode(fileOrURLLink,"UTF-8");
			log.debug("decodeUrl "  + decodeUrl);
			log.debug("fileOrURLLink "  + fileOrURLLink);
		}catch(Exception e)
		{
			retValue2.setCode(440);
			retValue2.setMessage("Decoding of URL failed ::" +decodeUrl +"fileOrURLLink"+fileOrURLLink);
			retValue2.setStatus("NOTOK");
			 
			return retValue2;
		}
		String time = uploadTime();
		if (decodeUrl.startsWith("http:") == true || decodeUrl.startsWith("https:") == true)
		{
			log.debug("fileOrURLLink   start with -http:-"  );
			shouldWeUploadFile = false;
		}
		else
		{
			
			log.debug("fileOrURLLink. does not .startsWith http"  );			
			shouldWeUploadFile = true;
		}
		log.debug("mediaTypeInt." +mediaTypeInt );
		if(mediaTypeInt == AppConstants.TEXTTYPE)
		{
			log.debug("mediaTypeInt. TEXTTYPE" +mediaTypeInt );
			isTextData = true;
			shouldWeUploadFile = false;
		}
		else
		{
			log.debug("mediaTypeInt. NOT TEXTTYPE"  );
		}
		if( shouldWeUploadFile )
		{
			log.debug("Uploading file info" );
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
					shouldWeUploadFile = true;
					urlLinkToMedia = urlLinkToMedia + "//" + time+userName+file.getOriginalFilename();
	
					break;
				case AppConstants.IMAGETYPE:
	
					storageDirectory = pataHome + "//" + AppConstants.UPLOAD_FOLDER_IMAGES;
					urlLinkToMedia = mediaServerUrl + "/" + AppConstants.MEDIA_APP_NAME + "/"
							+ AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_IMAGES;
					shouldWeUploadFile = true;
					urlLinkToMedia = urlLinkToMedia + "//" + time+userName+file.getOriginalFilename();
	
					break;
				case AppConstants.TEXTTYPE:
					// No upload just store ;
					shouldWeUploadFile = false;
					
	
					break;
				case AppConstants.VIDEOTYPE:
					storageDirectory = pataHome + "//" + AppConstants.UPLOAD_FOLDER_VIDEO;
					urlLinkToMedia = mediaServerUrl + "/" + AppConstants.MEDIA_APP_NAME + "/"
							+ AppConstants.MEDIA_ROOT_FOLDER_NAME + "/" + AppConstants.UPLOAD_FOLDER_VIDEO;
					shouldWeUploadFile = true;
					urlLinkToMedia = urlLinkToMedia + "//" + time+userName+file.getOriginalFilename();
	
					break;
				}
			} catch (Exception e) {
				retValue2.setCode(RestReturnCodes.MEDIA_CREATION_EXCEPTION);
				retValue2.setMediaCreatedId(-1);
				retValue2.setMessage("Error creating media" + e);
				retValue2.setStatus("NOTOK");
				return retValue2;
	
			}
			
			log.debug("UPload time=" + time);
			String downLoadFileName = storageDirectory + "//" + time+userName+file.getOriginalFilename();
			 contentType = file.getContentType();
			// directory exists - no create directory ;
	
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					
					log.debug("UPload File Size=" + bytes.length);
					log.debug("UPload File Name=" + file.getOriginalFilename());
					// Creating the directory to store file
					// Create the file on server
					
					
					 
					File serverFile = new File(downLoadFileName);
					if(serverFile.exists())
					{
						serverFile = new File(downLoadFileName);
					}
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					log.debug("Server File Location=" + serverFile.getAbsolutePath());
	
				} catch (Exception e) {
					retValue2.setCode(440);
					retValue2.setMessage("Exception while copying file "+e);
					retValue2.setStatus("NOTOK");
					 
					return retValue2;
				}
			} else {
				retValue2.setCode(440);
				retValue2.setMessage("File is empty");
				retValue2.setStatus("NOTOK");
				 
				return retValue2;
			}
		}
		else
		{
			
			
			urlLinkToMedia = decodeUrl;
		}		
		 
		MediaResponse retValue = new MediaResponse();
		log.debug("updateMediaData called");
		retValue = updateMediaData(idLong, title, description, address, category, language, latitude, longitude,
				urlLinkToMedia , groupId, appId, userId, contentType, mediaTypeInt,
				urlLinkToMedia, userAgent, mediaDTO);

		return retValue;
	}

	private MediaResponse updateMediaData(Long idLong,String title, String description, String address, String category,
			String language, String latitude, String longitude, String fileurl, Integer groupId, String appName,
			Integer userId, String contentType, Integer mediaType, String fileLink, String userAgent, HeritageMediaDTO  heritageMediaEntityDTO   ) 
	{	
		
		heritageMediaEntityDTO.setAddress(address);
		heritageMediaEntityDTO.setLatitude(Double.valueOf(latitude));
		heritageMediaEntityDTO.setLongitude(Double.valueOf(longitude));
		heritageMediaEntityDTO.setTitle(title);
		heritageMediaEntityDTO.setDescription("" + description);
		Long groupIdlongVar = groupId.longValue();
		heritageMediaEntityDTO.setGroupId(groupIdlongVar);
		heritageMediaEntityDTO.setMediaFileContentType(contentType);
		heritageMediaEntityDTO.setUrlOrfileLink(fileurl);
		heritageMediaEntityDTO.setMediaType(mediaType);
		heritageMediaEntityDTO.setUserAgent(userAgent);
		Long userIdLongVar = userId.longValue();
		heritageMediaEntityDTO.setUserId(userIdLongVar);
		MediaResponse retValue = new MediaResponse();
		HeritageApp heritageAppNameId = heritageAppNameRepository.findByName(appName);
		if (heritageAppNameId != null) {
			heritageMediaEntityDTO.setHeritageAppId(heritageAppNameId.getId());
		} else {
			retValue.setCode(441);
			log.debug("Media Creation failed - App name -  ");
			retValue.setMessage("Media Creation failed - App name - " + appName + " - INVALID");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		// userIdLongVar

		HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
		if (hCategory != null) {
			heritageMediaEntityDTO.setCategoryId(hCategory.getId());
		} else {
			log.debug("Media Creation failed - HeritageCategory name -  "+category);
			retValue.setCode(441);
			retValue.setMessage("Media Creation failed HeritageCategory -  " + category + " -INVALID CATEGORY");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language);

		if (hLanguage != null) {
			heritageMediaEntityDTO.setLanguageId(hLanguage.getId());
		} else {
			log.debug("Media Creation failed - HeritageLanguage name -  "+language);
			retValue.setCode(441);
			retValue.setMessage("Media Creation failed -  HeritageLanguage - " + language + "- INVALID LANGUAGE");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		// group repo
		// user repo
		// app repo

		// set the time ;

		String inputIso = "2014-03-19T21:00:00+05:30";
		DateTimeZone timeZoneIndia = DateTimeZone.forID("Asia/Kolkata");
		DateTime dateTimeIndia = new DateTime(inputIso, timeZoneIndia);

		String s = "Asia/Kolkata";
		LocalDateTime dt = LocalDateTime.now();
		ZoneId zone = ZoneId.of(s);
		ZonedDateTime uploadTime = dt.atZone(zone);
		heritageMediaEntityDTO.setUploadTime(uploadTime);
		HeritageMediaDTO result = null;
		try {
			byte[] mediaFile = new byte[1];
			heritageMediaEntityDTO.setMediaFile(mediaFile);
			result = heritageMediaEntityService.save(heritageMediaEntityDTO);
			result.getId();
		} catch (Exception e) {
			errMessageException = e.getMessage();

			retValue.setCode(441);
			retValue.setMessage("Media Creation failed" + errMessageException);
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
	// app/{appId}/user/{userId}/group/{groupId
	private MediaResponse createMediaData(String title, String description, String address, String category,
			String language, String latitude, String longitude, String fileurl, Integer groupId, String appName,
			Integer userId, String contentType, Integer mediaType, String fileLink, String userAgent) {

		HeritageMediaDTO heritageMediaEntityDTO = null;
		heritageMediaEntityDTO = new HeritageMediaDTO();

		heritageMediaEntityDTO.setAddress(address);
		heritageMediaEntityDTO.setLatitude(Double.valueOf(latitude));
		heritageMediaEntityDTO.setLongitude(Double.valueOf(longitude));
		heritageMediaEntityDTO.setTitle(title);
		heritageMediaEntityDTO.setDescription("  " + description);
		Long groupIdlongVar = groupId.longValue();
		heritageMediaEntityDTO.setGroupId(groupIdlongVar);
		// contentType
		heritageMediaEntityDTO.setMediaFileContentType(contentType);
		heritageMediaEntityDTO.setUrlOrfileLink(fileurl);
		heritageMediaEntityDTO.setMediaType(mediaType);
		heritageMediaEntityDTO.setUserAgent(userAgent);

		Long userIdLongVar = userId.longValue();
		heritageMediaEntityDTO.setUserId(userIdLongVar);
		MediaResponse retValue = new MediaResponse();

		HeritageApp heritageAppNameId = heritageAppNameRepository.findByName(appName);
		if (heritageAppNameId != null) {
			heritageMediaEntityDTO.setHeritageAppId(heritageAppNameId.getId());
		} else {
			retValue.setCode(441);
			log.debug("Media Creation failed - App name -  ");
			retValue.setMessage("Media Creation failed - App name - " + appName + " - INVALID");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		// userIdLongVar

		HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
		if (hCategory != null) {
			heritageMediaEntityDTO.setCategoryId(hCategory.getId());
		} else {
			log.debug("Media Creation failed - HeritageCategory name -  "+category);
			retValue.setCode(441);
			retValue.setMessage("Media Creation failed HeritageCategory -  " + category + " -INVALID CATEGORY");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language);

		if (hLanguage != null) {
			heritageMediaEntityDTO.setLanguageId(hLanguage.getId());
		} else {
			log.debug("Media Creation failed - HeritageLanguage name -  "+language);
			retValue.setCode(441);
			retValue.setMessage("Media Creation failed -  HeritageLanguage - " + language + "- INVALID LANGUAGE");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		// group repo
		// user repo
		// app repo

		// set the time ;

		String inputIso = "2014-03-19T21:00:00+05:30";
		DateTimeZone timeZoneIndia = DateTimeZone.forID("Asia/Kolkata");
		DateTime dateTimeIndia = new DateTime(inputIso, timeZoneIndia);

		String s = "Asia/Kolkata";
		LocalDateTime dt = LocalDateTime.now();
		ZoneId zone = ZoneId.of(s);
		ZonedDateTime uploadTime = dt.atZone(zone);
		heritageMediaEntityDTO.setUploadTime(uploadTime);
		HeritageMediaDTO result = null;
		try {
			byte[] mediaFile = new byte[1];
			heritageMediaEntityDTO.setMediaFile(mediaFile);
			result = heritageMediaEntityService.save(heritageMediaEntityDTO);
			result.getId();
		} catch (Exception e) {
			errMessageException = e.getMessage();

			retValue.setCode(441);
			retValue.setMessage("Media Creation failed" + errMessageException);
			retValue.setStatus("NOTOK");
			return retValue;

		}

		retValue.setCode(RestReturnCodes.SUCCESS);
		retValue.setMediaCreatedId(result.getId());
		retValue.setMessage("Created Media Successfully");
		retValue.setStatus("OK");
		return retValue;
	}
	
	private String uploadTime()
	{
		
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String data = df.format(new Date());
		return data;
	}

	///
	
}
