package ftn.xws.contentservice.controller;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ftn.xws.contentservice.dto.AlbumDTO;
import ftn.xws.contentservice.dto.AllCommentDTO;
import ftn.xws.contentservice.dto.AllReactionsDTO;
import ftn.xws.contentservice.dto.CommentDTO;
import ftn.xws.contentservice.dto.IdDTO;
import ftn.xws.contentservice.dto.PostDTO;
import ftn.xws.contentservice.dto.RatingDTO;
import ftn.xws.contentservice.dto.ReactionsNumberDTO;
import ftn.xws.contentservice.exception.PostDoesNotExistException;
import ftn.xws.contentservice.exception.ProfileBlockedException;
import ftn.xws.contentservice.exception.ProfilePrivateException;
import ftn.xws.contentservice.service.MediaService;
import ftn.xws.contentservice.service.UserService;
import ftn.xws.contentservice.util.TokenUtils;

@RestController
@RequestMapping(value = "/media")
public class MediaController {
	
	
	@Autowired
	private MediaService mediaService;

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/testAuth")
	public ResponseEntity<String> testAuth(@RequestHeader("Authorization") String auth) {
		if(!userService.verify(auth, "USER"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	@PostMapping(value="createPost",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> createPost(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "post", required = false) String model, @RequestHeader("Authorization") String auth) throws JsonMappingException, JsonProcessingException{
		
		if(!userService.verify(auth, "USER"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		String token = TokenUtils.getToken(auth);
		String username = userService.getUsernameFromToken(token);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	PostDTO postDTO = mapper.readValue(model, PostDTO.class);
    	System.out.println(postDTO.getDescription());
		try {
			mediaService.createPost(data, postDTO, username);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(value="createStory",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   	public ResponseEntity<Void> creatStory(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "story", required = false) String model,  @RequestHeader("Authorization") String auth) throws JsonMappingException, JsonProcessingException{
		if(!userService.verify(auth, "USER"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = userService.getUsernameFromToken(token);
    	
    	boolean close = model.equals("true") ? true : false;
		try {
			mediaService.createStory(data, close, username);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PostMapping(value="createAlbum")
    public ResponseEntity<Long> uploadFiles(MultipartHttpServletRequest request, @RequestHeader("Authorization") String auth) throws IOException {
		if(!userService.verify(auth, "USER") )
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = userService.getUsernameFromToken(token);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	AlbumDTO albumDTO = mapper.readValue(request.getParameter("album"), AlbumDTO.class);
    	
    	List<MultipartFile> files = new ArrayList<MultipartFile>();
    	Iterator<String> iterator = request.getFileNames();
        while (iterator.hasNext()) {
            MultipartFile file = request.getFile(iterator.next());
            files.add(file);
        }

        if(albumDTO.isPostSelected() == true) {
			return new ResponseEntity<>(mediaService.createAlbumAsPost(files, albumDTO, username), HttpStatus.OK);
        } else {
			return new ResponseEntity<>(mediaService.createAlbumAsStory(files, albumDTO, username), HttpStatus.OK);
        }
    }
	
	@PostMapping(value = "reactOnPost")
	public ResponseEntity<String> reactOnPost(@RequestBody RatingDTO dto, @RequestHeader("Authorization") String auth)
	{
		if(!userService.verify(auth, "USER"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = userService.getUsernameFromToken(token);
		
		try {
			mediaService.reactOnPost(dto, username);
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}
	
	@PostMapping(value = "allReactions")
	public ResponseEntity<AllReactionsDTO> getReactions(@RequestBody IdDTO id,  @RequestHeader("Authorization") String auth)
	{
		if(!userService.verify(auth, "USER") )
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = userService.getUsernameFromToken(token);
		
		
		AllReactionsDTO dto;
		try {
			mediaService.checkProfile(id.getId(), myUsername);
			dto = mediaService.getAllReactions(id.getId());
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (ProfilePrivateException e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
		} catch (ProfileBlockedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping(value = "getReactionsNumber")
	public ResponseEntity<ReactionsNumberDTO> getReactionsNumber(@RequestBody IdDTO id,  @RequestHeader("Authorization") String auth)
	{
		if(!userService.verify(auth, "USER") )
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = userService.getUsernameFromToken(token);
		
		ReactionsNumberDTO dto;
		try {
			mediaService.checkProfile(id.getId(), myUsername);
			dto = mediaService.getReactionsNumber(id.getId());
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		} catch (ProfilePrivateException e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
		} catch (ProfileBlockedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
		}
		return new ResponseEntity<>(dto,HttpStatus.OK);	
	}
	
	@PostMapping(value = "postComment")
	public ResponseEntity<String> postComment(@RequestBody CommentDTO dto, @RequestHeader("Authorization") String auth)
	{
		if(!userService.verify(auth, "USER") )
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = userService.getUsernameFromToken(token);
		
		try {
			mediaService.postComment(dto, username);
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}
	
	@PostMapping(value = "allComments")
	public ResponseEntity<List<AllCommentDTO>> getAllComments(@RequestBody IdDTO id,  @RequestHeader("Authorization") String auth)
	{
		if(!userService.verify(auth, "USER"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = userService.getUsernameFromToken(token);
		List<AllCommentDTO>  comments;
		try {
			mediaService.checkProfile(id.getId(), myUsername);
			comments = mediaService.getAllComments(id.getId());
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (ProfilePrivateException e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
		} catch (ProfileBlockedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
		}
		 
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
	
	@GetMapping(value = "content/{contentName:.+}/")
	public @ResponseBody ResponseEntity<UrlResource> getContent(@PathVariable(name = "contentName") String fileName) {
		try {
			UrlResource resource = mediaService.getContent(fileName);
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
					.contentType(MediaTypeFactory
							.getMediaType(resource)
							.orElse(MediaType.APPLICATION_OCTET_STREAM))
							.body(this.mediaService.getContent(fileName));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	

}
