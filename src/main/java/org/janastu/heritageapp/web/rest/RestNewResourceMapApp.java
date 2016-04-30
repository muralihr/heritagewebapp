package org.janastu.heritageapp.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.janastu.heritageapp.domain.AudioGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.HeritageApp;
import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.domain.HeritageGroupUser;
import org.janastu.heritageapp.domain.HeritageLanguage;
import org.janastu.heritageapp.domain.HeritageMedia;
import org.janastu.heritageapp.domain.HeritageRegionName;
import org.janastu.heritageapp.domain.ImageGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.TextGeoTagHeritageEntity;
import org.janastu.heritageapp.domain.User;
import org.janastu.heritageapp.domain.VideoGeoTagHeritageEntity;
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

	private final Logger log = LoggerFactory.getLogger(RestNewResourceMapApp.class);
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

	@RequestMapping(value = "/createAnyMediaGeoTagHeritageFromMobile2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@CrossOrigin
	public MediaResponse createAnyMediaGeoTagHeritageFromMobile(MultipartHttpServletRequest request,
			HttpServletResponse response) throws URISyntaxException {

		MediaResponse retValue2 = new MediaResponse();
		long resultVal = 0;
		log.debug("uploadPost called title" + request.getParameter("title"));
		log.debug("description  " + request.getParameter("description"));
		log.debug("category  " + request.getParameter("category")); // category
		log.debug("language  " + request.getParameter("language"));
		log.debug("latitude  " + request.getParameter("latitude"));
		log.debug("longitude  " + request.getParameter("longitude"));
		log.debug("mediatype  " + request.getParameter("mediatype"));
		log.debug("appName  " + request.getParameter("appName"));
		log.debug("groupName  " + request.getParameter("groupName"));
		log.debug("userName  " + request.getParameter("userName"));
		log.debug("uploadTime  " + request.getParameter("uploadTime"));
		log.debug("userAgent  " + request.getParameter("userAgent"));

		// appName
		ResponseEntity<ImageGeoTagHeritageEntityDTO> retValue = null;
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String language = request.getParameter("language");
		String category = request.getParameter("category");
		String address = request.getParameter("address");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String mediatypeStr = request.getParameter("mediatype");
		Long appId;
		String newFilenameBase = null;
		Integer dataStored = 0;
		// add following additional data -
		// user/group/useragent/uploadtime
		String groupName = request.getParameter("groupName");
		String appName = request.getParameter("appName");
		String userName = request.getParameter("userName");
		String uploadTime = request.getParameter("uploadTime");
		String userAgent = request.getParameter("userAgent");
		//
		Optional<HeritageGroup> g = groupRepository.findOneByName(groupName);
		Long groupId;
		Long userId;
		String downLoadFileName;
		String newFilename = null;
		String contentType = null;
		if (g.isPresent() == false) {

			Optional<HeritageGroup> g2 = groupRepository.findOneByName("Default");
			groupId = g2.get().getId();
		} else {
			groupId = g.get().getId();
		}

		Optional<User> u = userRepository.findOneByLogin(userName);

		if (u.isPresent() == false) {
			retValue2.setCode(RestReturnCodes.USER_NOT_FOUND_EXCEPTION);
			retValue2.setMediaCreatedId(-1);
			retValue2.setMessage("Create  Media FAILED USER_NOT_FOUND_EXCEPTION");
			retValue2.setStatus("NOTOK");
			return retValue2;
		} else {
			userId = u.get().getId();
			log.debug("userId found :: " + userId);

			dataStored = u.get().getDataStored();

			/**/

		}

		HeritageAppDTO happ = heritageAppNameService.findByName(appName);

		if (happ != null) {
			appId = happ.getId();
		}

		///
		int mediaType = Integer.parseInt(mediatypeStr);
		String mediaServerUrl = AppConstants.MEDIA_SERVER_URL;
		 

		log.debug("DIR PATA HOME" + environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV));
		String pataHome = environment.getProperty(AppConstants.UPLOAD_FOLDER_ENV);
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

		log.debug("UPLOAD  FOLDER LOCATION " + pataHome);
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		byte[] photo = null;
		String urlLinkToMedia = "";
		try 
		{
			File newFile = null;
			while (itr.hasNext()) 
			{
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

				if (mediaType != AppConstants.TEXTTYPE)
				{
					mpf = request.getFile(itr.next());
					log.debug("Uploading  ---", mpf.getOriginalFilename());

					mpf.getOriginalFilename();
					String originalFileExtension = mpf.getOriginalFilename()
							.substring(mpf.getOriginalFilename().lastIndexOf("."));
					// String newFilename = newFilenameBase + originalFileExtension;
					newFilename = mpf.getOriginalFilename();	
					
					log.debug("heritageMediaEntityDTO NOT AppConstants.TEXTTYPE" );
					downLoadFileName = storageDirectory + "//" + mpf.getOriginalFilename();
					contentType = mpf.getContentType();
					log.debug(" getContentType() -- ", mpf.getContentType());

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

						Integer currentSize = (int) (dataStored + (int) newFile.length());
						if (currentSize.compareTo(AppConstants.MAX_CAPACITY) > 0) {
							log.debug("Create  Media FAILED USER_STORAGE_CAPACITY_EXCEEDED");
							retValue2.setCode(RestReturnCodes.USER_STORAGE_CAPACITY_EXCEEDED);
							retValue2.setMediaCreatedId(-1);
							retValue2.setMessage("Create  Media FAILED USER_STORAGE_CAPACITY_EXCEEDED");
							retValue2.setStatus("NOTOK");
							newFile.delete();
							return retValue2;
						}
						userService.changeDataStoredMobile(userName, (int) newFile.length());
					} catch (FileNotFoundException e) {
						log.error("Could not upload file " + newFile, e);

					} catch (IOException e) {
						log.error("Could not upload file " + mpf.getOriginalFilename(), e);
					}
				} //mediaa type check 
			}//while end
			/// check

			//

		} catch (Exception e) {

			log.error("Error while save and  upload file " + e);
		}
 
		MediaResponse retValue3 = createMediaData2(title, description, address, category, language, latitude, longitude,
				newFilename, groupId, appName, userId, contentType, mediaType, urlLinkToMedia, userAgent, userName);

		retValue3.setCode(RestReturnCodes.SUCCESS);
		retValue3.setMediaCreatedId(resultVal);
		retValue3.setMessage("Created Media Successfully");
		retValue3.setStatus("OK");

		return retValue3;

	}

	private MediaResponse createMediaData2(String title, String description, String address, String category,
			String language, String latitude, String longitude, String newFilenameBase, Long groupId, String appName,
			Long userId, String contentType, int mediaType, String urlLinkToMedia, String userAgent, String userLogin) {
		// TODO Auto-generated method stub
		HeritageMediaDTO heritageMediaEntityDTO = null;
		heritageMediaEntityDTO = new HeritageMediaDTO();

		heritageMediaEntityDTO.setAddress(address);
		heritageMediaEntityDTO.setLatitude(Double.valueOf(latitude));
		heritageMediaEntityDTO.setLongitude(Double.valueOf(longitude));
		heritageMediaEntityDTO.setTitle(title);
		heritageMediaEntityDTO.setDescription("  " + description);

		heritageMediaEntityDTO.setGroupId(groupId);

		// contentType
		log.debug("createMediaData2 contentType: " + contentType);
		if (contentType == null) {
			heritageMediaEntityDTO.setMediaFileContentType("image/png");
		}

		heritageMediaEntityDTO.setUrlOrfileLink(urlLinkToMedia + "/" + newFilenameBase);
		heritageMediaEntityDTO.setMediaType(mediaType);
		heritageMediaEntityDTO.setUserAgent(userAgent);

		MediaResponse retValue = new MediaResponse();

		HeritageApp heritageAppNameId = heritageAppNameRepository.findByName(appName);
		if (heritageAppNameId != null) {
			heritageMediaEntityDTO.setHeritageAppId(heritageAppNameId.getId());
		} else {
			
			retValue.setCode(441);
			log.debug("setting contentType: " + contentType);
			log.debug("Media Creation failed - App nam"+ appName );
			retValue.setMessage("Media Creation failed - App name - " + appName + " - INVALIDE");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		// userIdLongVar

		HeritageCategory hCategory = heritageCategoryRepository.findByCategoryName(category);
		if (hCategory != null) {
			heritageMediaEntityDTO.setCategoryId(hCategory.getId());
		} else {
			retValue.setCode(441);
			log.debug("setting contentType: " + contentType);
			
			retValue.setMessage("Media Creation failed HeritageCategory -  " + category + " -INVALID CATEGORY");
			retValue.setStatus("NOTOK");
			return retValue;
		}
		HeritageLanguage hLanguage = heritageLanguageRepository.findByHeritageLanguage(language);

		if (hLanguage != null) {
			heritageMediaEntityDTO.setLanguageId(hLanguage.getId());
		} else {
			log.debug("setting contentType: " + contentType);
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

		HeritageMediaDTO result = null;
		String s = "Asia/Kolkata";
		LocalDateTime dt = LocalDateTime.now();
		ZoneId zone = ZoneId.of(s);
		ZonedDateTime uploadTime = dt.atZone(zone);
		heritageMediaEntityDTO.setUploadTime(uploadTime);

		try {
			byte[] mediaFile = new byte[1];
			heritageMediaEntityDTO.setMediaFile(mediaFile);
			log.debug("heritageMediaEntityDTO userId set" + userId);
			heritageMediaEntityDTO.setUserId(userId);
			log.debug("heritageMediaEntityDTO userLogin set" + userLogin);
			heritageMediaEntityDTO.setUserLogin(userLogin);
			log.debug("heritageMediaEntityDTO setUserLogin: " + userLogin);
			result = heritageMediaEntityService.saveMobile(heritageMediaEntityDTO);
			log.debug("heritageMediaEntityDTO result.getId(): " + result.getId());
		 
			result.getId();
		} catch (Exception e) {
			errMessageException = e.getMessage();
			log.debug("heritageMediaEntityDTO  errMessageException e.getMessage(): " +  e.getMessage());
			retValue.setCode(441);
			retValue.setMessage("Media Creation failed" + errMessageException);
			retValue.setStatus("NOTOK");
			return retValue;

		}
		log.debug("heritageMediaEntityDTO SUCCESS  " + result.getId() );
		retValue.setCode(RestReturnCodes.SUCCESS);
		retValue.setMediaCreatedId(result.getId());
		retValue.setMessage("Created Media Successfully");
		retValue.setStatus("OK");
		return retValue;
	}
//createAnyMediaGeoTagHeritageFromMobile3
	
	
	@CrossOrigin
	@RequestMapping(value = "/createNewMediaHeritageForm/app/{appId}/user/{userId}/group/{groupId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public @ResponseBody MediaResponse createAnyMediaGeoTagHeritageFromWebWithMap(@PathVariable("appId") String appId,
			@PathVariable("userId") Integer userId, @PathVariable("groupId") Integer groupId,
			@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("category") String category, @RequestParam("language") String language,
			@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude,
			@RequestParam("mediatype") String mediaType, @RequestParam("uploadTime") String uploadTime,
			@RequestParam("userAgent") String userAgent, @RequestParam("fileOrURLLink") String fileOrURLLink,
			@RequestParam("picture") MultipartFile file) {

		log.debug("uploadPost called title" + title);
		log.debug("mapId  " + appId);
		log.debug("userId  " + userId);
		log.debug("groupId  " + groupId);

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
		log.debug("createMediaData called");
		retValue = createMediaData(title, description, address, category, language, latitude, longitude,
				urlLinkToMedia + "//" + file.getOriginalFilename(), groupId, appId, userId, contentType, mediaTypeInt,
				urlLinkToMedia, userAgent);

		return retValue;
	}

	
	@CrossOrigin //createNewMediaHeritageForm2
	@RequestMapping(value = "/createNewMediaHeritageForm2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public @ResponseBody MediaResponse createAnyMediaGeoTagHeritageFromWebWithMapFix(@RequestParam("appId") String appId,
			@RequestParam("userName") String userName, @RequestParam("groupId") Integer groupId,
			@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("category") String category, @RequestParam("language") String language,
			@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude,
			@RequestParam("mediatype") String mediaType, @RequestParam("uploadTime") String uploadTime,
			@RequestParam("userAgent") String userAgent, @RequestParam("fileOrURLLink") String fileOrURLLink,
			@RequestParam("picture") MultipartFile file) {

		log.debug("uploadPost called title" + title);
		log.debug("mapId  " + appId);
		log.debug("userName  " + userName);
		log.debug("groupId  " + groupId);

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
				 
				return retValue2;
			}
		} else {
			retValue2.setCode(440);
			retValue2.setMessage("You FAILED uploaded file file emplty");
			retValue2.setStatus("NOTOK");
			 
			return retValue2;
		}

		
		Integer userId = -1;
		//userName
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
		
		MediaResponse retValue = new MediaResponse();
		log.debug("createMediaData called");
		retValue = createMediaData(title, description, address, category, language, latitude, longitude,
				urlLinkToMedia + "//" + file.getOriginalFilename(), groupId, appId, userId, contentType, mediaTypeInt,
				urlLinkToMedia, userAgent);

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
			retValue.setMessage("Media Creation failed - App name - " + appName + " - INVALIDE");
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
	@CrossOrigin
	@RequestMapping(value = "/getAppConfigInfo/app/{appId}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public @ResponseBody ResponseEntity<HeritageAppDTO> getConfigInfoForAppId(@PathVariable("appId") String appId

	) {

		log.debug("REST request to get getConfigInfoForAppId : {}", appId);
		HeritageAppDTO heritageAppNameDTO = heritageAppNameService.findByName(appId);

		Set<HeritageCategory> categories = heritageAppNameDTO.getCategorys();
		Set<HeritageGroup> groups = heritageAppNameDTO.getGroups();

		Set<HeritageCategory> newcategories = new HashSet<HeritageCategory>();
		Set<HeritageGroup> newgroups = new HashSet<HeritageGroup>();
		for (HeritageCategory t : categories) {
			t.setCategoryIcon(null);
			newcategories.add(t);
		}
		heritageAppNameDTO.setCategorys(newcategories);

		for (HeritageGroup u : groups) {
			u.setIcon(null);
			newgroups.add(u);
		}

		heritageAppNameDTO.setGroups(newgroups);
		return Optional.ofNullable(heritageAppNameDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	// mapp/
	@CrossOrigin
	@RequestMapping(value = "/mapp", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public FeatureCollection getAllHeritageMediaEntitysAsGeoJSON() throws URISyntaxException {

		List<HeritageMedia> heritageList = heritageMediaEntityService.findAllAsAList();
		;
		FeatureCollection totalCollection = convertListToFeatures(heritageList);

		return totalCollection;

	}

	private FeatureCollection convertListToFeatures(List<HeritageMedia> heritageList) {
		FeatureCollection totalCollection = new FeatureCollection();

		for (HeritageMedia item : heritageList) {

			LngLatAlt coordinates = new LngLatAlt(item.getLongitude(), item.getLatitude());

			Point c = new Point(coordinates);
			Feature f = new Feature();
			f.setGeometry(c);
			f.setId(item.getId().toString());
			Map<String, Object> properties = new HashMap<String, Object>();

			properties.put("title", item.getTitle());
			properties.put("description", item.getDescription());

			properties.put("marker-size", "small");
			properties.put("url", item.getUrlOrfileLink());

			Integer m = item.getMediaType();

			if (m != null) {
				switch (m)

				{

				case AppConstants.IMAGETYPE:
					properties.put("marker-color", "#ff8888"); // ma#FFECB3rker-color
					properties.put("mediatype", "IMAGE");
					properties.put("marker-symbol", "camera"); // #FFC107 //
					break;

				case AppConstants.AUDIOTYPE:
					properties.put("marker-color", "#FFC107"); // marker-color
					properties.put("mediatype", "AUDIO");
					properties.put("marker-symbol", "music"); // #FFC107 //
					break;
				case AppConstants.VIDEOTYPE:
					properties.put("mediatype", "VIDEO");
					properties.put("marker-color", "#FFECB3"); // ma#FFECB3rker-color
					properties.put("marker-symbol", "cinema"); // #FFC107 //
					break;

				case AppConstants.TEXTTYPE:
					properties.put("marker-color", "#727272"); // ma#FFECB3rker-color
					properties.put("mediatype", "TEXT");
					properties.put("marker-symbol", "golf"); // #FFC107 //

					break;

				}
			}

			if (item.getCategory() != null) {
				log.debug("getCategoryName   name " + item.getCategory().getCategoryName());

				properties.put("category", item.getCategory().getCategoryName());
			}
			if (item.getLanguage() != null) {
				// log.debug("getHeritageLanguage name " +
				// item.getHeritageLanguage().get.getHeritageLanguage());

				// properties.put("language",item.getHeritageLanguage().getHeritageLanguage());
			}

			if (item.getGroup() != null) {
				log.debug("getHeritageLanguage   groupname " + item.getGroup().getName());

				properties.put("groupname", item.getGroup().getName());
			}
			if (item.getUser() != null) {
				log.debug("getHeritageLanguage   name " + item.getUser().getLogin());

				properties.put("user", item.getUser().getLogin());

			}
			if (item.getHeritageApp() != null) {
				log.debug("getHeritageLanguage   name " + item.getHeritageApp().getName());

				properties.put("appname", item.getHeritageApp().getName());
			}
			if (item.getUploadTime() != null) {

				final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
				String dateStr2 = item.getUploadTime().format(DATETIME_FORMATTER);
				properties.put("uploadTime", dateStr2);
			}

			f.setProperties(properties);
			totalCollection.add(f);
		}

		return totalCollection;

	}

	////

	@CrossOrigin
	@RequestMapping(value = "/getUserGroups/user/{userId}", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public @ResponseBody List<HeritageGroup> getUserGroups(@PathVariable String userId) {
		List<HeritageGroup> hGroupList = new ArrayList<HeritageGroup>();
		Optional<User> u = userRepository.findOneByLogin(userId);
		if (u.isPresent()) {

			List<HeritageGroupUser> lusers = heritageGroupUserRepository.findAll();
			for (HeritageGroupUser g : lusers) {

				User user = g.getMember();
				if (user.getLogin().compareTo(userId) == 0 && g.getStatus() == 2) {
					HeritageGroup h = g.getGroup();
					h.setIcon(null);
					hGroupList.add(h);
				}
			}
		}

		return hGroupList;

	}
	///

}