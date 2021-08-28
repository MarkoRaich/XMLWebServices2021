package ftn.xws.contentservice.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import ftn.xws.contentservice.dto.AlbumDTO;
import ftn.xws.contentservice.dto.AllCommentDTO;
import ftn.xws.contentservice.dto.AllReactionsDTO;
import ftn.xws.contentservice.dto.CommentDTO;
import ftn.xws.contentservice.dto.PostDTO;
import ftn.xws.contentservice.dto.RatingDTO;
import ftn.xws.contentservice.dto.ReactionsNumberDTO;
import ftn.xws.contentservice.exception.PostDoesNotExistException;
import ftn.xws.contentservice.exception.ProfileBlockedException;
import ftn.xws.contentservice.exception.ProfilePrivateException;
import ftn.xws.contentservice.model.Media;

public interface MediaService {
	
	void createPost(MultipartFile file, PostDTO postDTO, String username) throws IOException;

	Media save(Media media);

	void createStory(MultipartFile file, boolean closeFriends, String username) throws  IOException;
	
	long createAlbumAsPost(List<MultipartFile> files, AlbumDTO albumDTO, String username) throws  IOException;

	long createAlbumAsStory(List<MultipartFile> files, AlbumDTO albumDTO, String username) throws  IOException;
	
	UrlResource getContent(String contentName) throws MalformedURLException;
	
	void reactOnPost(RatingDTO dto, String username) throws PostDoesNotExistException;
	
	AllReactionsDTO getAllReactions(long postId) throws PostDoesNotExistException;

	void checkProfile(long postId, String myUsername) throws PostDoesNotExistException, ProfilePrivateException, ProfileBlockedException;
	
	ReactionsNumberDTO getReactionsNumber(long id) throws PostDoesNotExistException;
	
	void postComment(CommentDTO dto, String username) throws PostDoesNotExistException;

	List<AllCommentDTO> getAllComments(long postId) throws PostDoesNotExistException;

}
